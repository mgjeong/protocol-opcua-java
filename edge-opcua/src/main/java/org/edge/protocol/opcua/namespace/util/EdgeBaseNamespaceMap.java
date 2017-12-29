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

package org.edge.protocol.opcua.namespace.util;

import java.util.Optional;
import java.util.concurrent.ConcurrentMap;
import org.edge.protocol.opcua.namespace.EdgeNamespace;

/**
 * This interface provide function for handle EdgeBaseNamespaceMap
 */
public interface EdgeBaseNamespaceMap extends ConcurrentMap<String, EdgeNamespace> {

  /**
   * add EdgeNamespace
   * @param namespace value of namespace
   */
  default void addNode(EdgeNamespace namespace) {
    put(namespace.getNamespaceUri(), namespace);
  }

  /**
   * check contains sessions
   * @param  node
   * @return contains session or not
   */
  default boolean containsSession(EdgeNamespace node) {
    return containsEndpoint(node.getNamespaceUri());
  }

  /**
   * check contains endpoint
   * @param  id
   * @return contains endpoint or not
   */
  default boolean containsEndpoint(String id) {
    return containsKey(id);
  }

  /**
   * @param  id
   * @return EdgeNamespace instance if there is EdgeNamespace 
   */
  default Optional<EdgeNamespace> getNode(String id) {
    return Optional.ofNullable(get(id));
  }

  /**
   * @param  id
   * @return EdgeNamespace instance if there is EdgeNamespace 
   */
  default Optional<EdgeNamespace> removeNode(String id) {
    return Optional.ofNullable(remove(id));
  }

}
