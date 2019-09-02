package com.alibaba.otter.manager.biz.common.alarm;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.manager.biz.common.util.EncryptUtil;
import com.alibaba.otter.manager.biz.monitor.AlarmParameter;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 将告警服务推送到falcon agent
 */
public class FalconAgentAlarmService {

    private static final Logger logger = LoggerFactory.getLogger(FalconAgentAlarmService.class);
    public final static String RFC1123_PATTERN = "EEE, dd MMM yyyy HH:mm:ss z";
    private static final CloseableHttpClient httpClient = HttpClients
            .custom()
            .setDefaultRequestConfig(RequestConfig
                    .custom()
                    .setConnectTimeout(10000)
                    .setSocketTimeout(10000)
                    .setConnectionRequestTimeout(10000)
                    .build())
            .build();
    private static final String metric = "otter-alarm";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(RFC1123_PATTERN, Locale.US);
    static {
        simpleDateFormat.setTimeZone(new SimpleTimeZone(0, "GMT"));
    }

    private boolean urlset;
    private String url;
    private String user;
    private String key;

    public void setUser(String user) {
        this.user = user;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public FalconAgentAlarmService(String url) {
        this.url = url;
        if (StringUtils.isBlank(url)){
            logger.warn("do not assign a url for falcon-agent alarm");
            urlset = false;
            return;
        }
        logger.info("init falcon-agent alarm : {}",url);
        urlset = true;
    }

    public void send(AlarmMessage message, AlarmParameter parameter) {
        if (!urlset){
            return;
        }
        HttpPost post = null;
        try {
            post = buildRequest(message,parameter);
            CloseableHttpResponse response = httpClient.execute(post);
            int responseStatusCode = response.getStatusLine().getStatusCode();
            if (responseStatusCode != HttpStatus.SC_OK) {
                String responseMessage = EntityUtils.toString(response.getEntity());
                logger.warn("falcon api response status code {} error.message:{}",responseStatusCode, responseMessage);
            }else {
                logger.info("call falcon api success");
            }
        } catch (ClientProtocolException e) {
            logger.error("call falcon api error!{}",e);
        } catch (IOException e) {
            logger.error("call falcon api error!{}",e);
        } catch (ParseException e) {
            logger.error("falcon api response parse error!{}",e);
        } catch (Exception e){
            logger.error("exception when do falcon-agent api call! {}",e);
        }finally {
            if (post != null) {
                post.releaseConnection();
            }
        }
    }

    /**
     * 构建报警请求
     * @param message
     * @param parameter
     * @return
     */
    private HttpPost buildRequest(AlarmMessage message, AlarmParameter parameter) throws Exception{
        HttpPost post = new HttpPost(url);
        fillHeader(post);
        List<FalconAlertData> data = generateAlertData(message,parameter);
        String jsonStr = JSON.toJSONString(data);
        logger.info("data to send to falcon agent: {}",jsonStr);
        StringEntity entity = new StringEntity(jsonStr, Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        post.setEntity(entity);
        return post;
    }

    private void fillHeader(HttpPost request) throws Exception{
        request.addHeader(HttpHeaders.CONTENT_TYPE,"application/json; charset=utf-8");
        request.setHeader(HttpHeaders.CONNECTION,"Close");
        // user和key都非空的时候才添加请求头
        if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(user)) {
            String formatDate = "";
            Date now = new Date();
            synchronized (simpleDateFormat) {
                formatDate = simpleDateFormat.format(now);
            }
            String password = EncryptUtil.signHmacSHA1(key,formatDate);
            String authorizationString = EncryptUtil.toBase64String((user + ":" + password).getBytes(Charset.forName("UTF-8")));
            request.addHeader(HttpHeaders.DATE,formatDate);
            request.addHeader(HttpHeaders.AUTHORIZATION,"Basic " + authorizationString);
        }
    }

    /**
     * 生成报警数据
     * @param message
     * @param parameter
     * @return
     */
    private List<FalconAlertData> generateAlertData(AlarmMessage message, AlarmParameter parameter) {
        List<FalconAlertData> list = new ArrayList<FalconAlertData>();
        FalconAlertData result = new FalconAlertData();
        result.setMetric(metric);
        result.setValue(parameter.getSecondTimes());
        result.setTimestamp(System.currentTimeMillis()/1000);
        try {
            result.setEndpoint(InetAddress.getLocalHost().getHostName());
        }catch (Exception e){
            logger.warn("get local host name error.{}",e);
        }
        result.setCounterType("GAUGE");
        result.setStep(60);
        StringBuilder tagstr = new StringBuilder("DataType=alarmData,");
        tagstr.append("Type=").append(parameter.getType()).append(",");
        Map<String,String> tagmap = parameter.getTags();
        if (tagmap != null && tagmap.keySet().size()>0) {
            tagstr.append("Param=");
            for (String key:tagmap.keySet()) {
                tagstr.append(key).append(":").append(tagmap.get(key)).append("|");
            }
            tagstr.deleteCharAt(tagstr.length()-1);
        }
        result.setTags(tagstr.toString());
        list.add(result);
        return list;
    }

    private static class FalconAlertData {

        private String Metric;
        private String Tags;
        private Object Value;
        private long Timestamp;
        private String Endpoint;
        private String CounterType;
        private int Step;

        public String getMetric() {
            return Metric;
        }

        public void setMetric(String metric) {
            Metric = metric;
        }

        public String getTags() {
            return Tags;
        }

        public void setTags(String tags) {
            Tags = tags;
        }

        public Object getValue() {
            return Value;
        }

        public void setValue(Object value) {
            Value = value;
        }

        public long getTimestamp() {
            return Timestamp;
        }

        public void setTimestamp(long timestamp) {
            Timestamp = timestamp;
        }

        public String getEndpoint() {
            return Endpoint;
        }

        public void setEndpoint(String endpoint) {
            Endpoint = endpoint;
        }

        public String getCounterType() {
            return CounterType;
        }

        public void setCounterType(String counterType) {
            CounterType = counterType;
        }

        public int getStep() {
            return Step;
        }

        public void setStep(int step) {
            Step = step;
        }
    }
}
