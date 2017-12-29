/******************************************************************
 *
 * Copyright 2017 Samsung Electronics All Rights Reserved.
 *
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************/

package org.edge.protocol.opcua.providers;

import org.edge.protocol.opcua.providers.services.EdgeGroupService;
import org.edge.protocol.opcua.providers.services.browse.EdgeBrowseService;
import org.edge.protocol.opcua.providers.services.sub.EdgeMonitoredItemService;

public class EdgeBaseProvider {
  protected final EdgeMonitoredItemService monitorService;
  protected final EdgeBrowseService browseService;
  protected final EdgeGroupService groupService;

  /**
   * constructor for base provider for other provider such as EdgeMethodProvider,
   *        EdgeAttributeProvider
   * @param  monitor EdgeMonitoredItemService instance
   * @param  browse EdgeBrowseService instance
   * @return void
   */
  EdgeBaseProvider(EdgeMonitoredItemService monitor, EdgeBrowseService browse) {
    this.monitorService = monitor;
    this.browseService = browse;
    this.groupService = null;
  }

  /**
   * constructor for EdgeGroupService
   * @param  group EdgeGroupService instance
   */
  EdgeBaseProvider(EdgeGroupService group) {
    this.monitorService = null;
    this.browseService = null;
    this.groupService = group;
  }

  /**
   * get MonitoredItemService instance
   * @return MonitoredItemService instance
   */
  public EdgeMonitoredItemService getMonitoredItemService() {
    return monitorService;
  }

  /**
   * get EdgeBrowseService instance
   * @return EdgeBrowseService instance
   */
  public EdgeBrowseService getBrowseService() {
    return browseService;
  }

  /**
   * get EdgeGroupService instance
   * @return EdgeGroupService instance
   */
  public EdgeGroupService getGroupService() {
    return groupService;
  }
}
