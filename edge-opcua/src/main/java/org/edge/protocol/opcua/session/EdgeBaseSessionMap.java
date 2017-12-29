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

package org.edge.protocol.opcua.session;

import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

public interface EdgeBaseSessionMap extends ConcurrentMap<String, EdgeOpcUaClient> {

  /**
   * add value into Map
   * @param  session EdgeOpcUaClient instance
   */
  default void addNode(EdgeOpcUaClient session) {
    put(session.getEndpoint(), session);
  }

  /**
   * check whether the session is contained or not
   * @param  session EdgeOpcUaClient instance
   * @return true or false
   */
  default boolean containsSession(EdgeOpcUaClient session) {
    return containsEndpoint(session.getEndpoint());
  }

  /**
   * check whether the session is contained or not
   * @param  endpoint endpoint uri
   * @return true or false
   */
  default boolean containsEndpoint(String endpoint) {
    return containsKey(endpoint);
  }

  /**
   * get node
   * @param  endpoint endpoint uri
   * @return EdgeOpcUaClient if there is EdgeOpcUaClient
   */
  default Optional<EdgeOpcUaClient> getNode(String endpoint) {
    return Optional.ofNullable(get(endpoint));
  }

  /**
   * remove node
   * @param  endpoint endpoint uri
   * @return EdgeOpcUaClient if there is EdgeOpcUaClient
   */
  default Optional<EdgeOpcUaClient> removeNode(String endpoint) {
    return Optional.ofNullable(remove(endpoint));
  }

}
