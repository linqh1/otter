/*
 * Copyright (C) 2010-2101 Alibaba Group Holding Limited.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.otter.manager.web.home.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.otter.manager.biz.config.autokeeper.dal.AutoKeeperClusterDAO;
import com.alibaba.otter.manager.biz.config.channel.ChannelService;
import com.alibaba.otter.manager.biz.config.datamediasource.dal.DataMediaSourceDAO;
import com.alibaba.otter.manager.biz.config.node.dal.NodeDAO;

import javax.annotation.Resource;

public class AddQuickChannel {

    @Resource(name = "channelService")
    private ChannelService channelService;
    @Resource(name = "autoKeeperClusterDao")
    private AutoKeeperClusterDAO autoKeeperClusterDao;
    @Resource(name = "dataMediaSourceDao")
    private DataMediaSourceDAO dataMediaSourceDao;
    @Resource(name = "nodeDao")
    private NodeDAO nodeDao;

    public void execute(Context context, Navigator nav) throws Exception {
        context.put("sourceList", dataMediaSourceDao.listAll());
        context.put("zkList", autoKeeperClusterDao.listAutoKeeperClusters());
        context.put("nodeList", nodeDao.listAll());
    }

}