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
import org.edge.protocol.opcua.providers.services.method.EdgeMethodService;
import org.edge.protocol.opcua.providers.services.sub.EdgeMonitoredItemService;

public class EdgeMethodProvider extends EdgeBaseProvider {
  private Map<String, EdgeMethodService> methodServices;

  /**
   * constructor
   * @param  monitor EdgeMonitoredItemService instance
   * @param  browse EdgeBrowseService instance
   */
  public EdgeMethodProvider(EdgeMonitoredItemService monitor, EdgeBrowseService browse) {
    super(monitor, browse);
    this.methodServices = null;
  }

  /**
   * register Method Service
   * @param  name provider key value
   * @param  method EdgeMethodService instance
   */
  public EdgeMethodProvider registerMethodService(String name, EdgeMethodService method) {
    if (methodServices == null)
      methodServices = new ConcurrentHashMap<String, EdgeMethodService>();
    methodServices.put(name, method);
    return this;
  }

  /**
   * get EdgeMethodService instance
   * @param  name provider key value
   * @return EdgeMethodService instance
   */
  public EdgeMethodService getMethodService(String name) {
    EdgeMethodService method = null;
    try {
      method = methodServices.get(name);
    } catch (Exception e) {
      throw new IllegalArgumentException("no service registered with name: " + name);
    }
    return method;
  }
}
