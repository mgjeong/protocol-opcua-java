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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.edge.protocol.opcua.providers.services.browse.EdgeBrowseService;
import org.edge.protocol.opcua.providers.services.browse.EdgeViewService;
import org.edge.protocol.opcua.providers.services.sub.EdgeMonitoredItemService;

public class EdgeViewProvider extends EdgeBaseProvider {
  private Map<String, EdgeViewService> viewServices;

  /**
   * constructor
   * @param  monitor EdgeMonitoredItemService instance
   * @param  browse EdgeBrowseService instance
   */
  public EdgeViewProvider(EdgeMonitoredItemService monitor, EdgeBrowseService browse) {
    super(monitor, browse);
    this.viewServices = null;
  }

  /**
   * register View Service
   * @param  name provider key value
   * @param  view EdgeViewService instance
   */
  public EdgeViewProvider registerViewService(String name, EdgeViewService view) {
    if (viewServices == null)
      viewServices = new ConcurrentHashMap<String, EdgeViewService>();
    viewServices.put(name, view);
    return this;
  }

  /**
   * get EdgeViewService instance
   * @param  name provider key value
   * @return EdgeViewService instance
   */
  public EdgeViewService getViewService(String name) {
    EdgeViewService view = null;
    try {
      view = viewServices.get(name);
    } catch (Exception e) {
      throw new IllegalArgumentException("no service registered with name: " + name);
    }
    return view;
  }
}
