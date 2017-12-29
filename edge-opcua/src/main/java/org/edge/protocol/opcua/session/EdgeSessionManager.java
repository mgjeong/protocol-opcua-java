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

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import org.eclipse.milo.opcua.stack.client.UaTcpStackClient;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;
import org.edge.protocol.opcua.api.common.EdgeEndpointConfig;
import org.edge.protocol.opcua.api.common.EdgeEndpointInfo;
import org.edge.protocol.opcua.api.common.EdgeMessage;
import org.edge.protocol.opcua.api.common.EdgeNodeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EdgeSessionManager {
    private final Logger logger = LoggerFactory.getLogger(getClass());
  private final EdgeBaseSessionMap sessionMap = new EdgeSessionMap();
  private static EdgeSessionManager session = null;
  private static Object lock = new Object();

  private EdgeSessionManager() {}

  /**
   * get session manager instance
   * @return EdgeSessionManager instance
   */
  public static EdgeSessionManager getInstance() {
    synchronized (lock) {
      if (null == session) {
        session = new EdgeSessionManager();
      }
      return session;
    }
  }

  /**
   * close session manager instance
   */
  public void close() {
    session = null;
  }

  /**
   * insert EdgeOpcUaClient instance with endpoint uri(key)
   * @param  ep EdgeEndpoint which has configuration information
   */
  public void configure(EdgeEndpointInfo ep) throws Exception {
    if (false == sessionMap.containsKey(ep.getEndpointUri())) {
      sessionMap.put(ep.getEndpointUri(), new EdgeOpcUaClient(ep));
    }
  }

  /**
   * connect to endpoint (endpoint should be contained in sessionMap)
   * @param  endpoint endpoint uri
   */
  public void connect(String endpoint, CompletableFuture<String> future) {
    EdgeOpcUaClient client = sessionMap.getNode(endpoint).get();

    try {
      client.connect(endpoint, future);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * disconnect to endpoint (endpoint should be contained in sessionMap)
   * @param  endpoint endpoint uri
   */
  public void disconnect(String endpoint) {
    EdgeOpcUaClient client = sessionMap.getNode(endpoint).get();

    try {
      client.disconnect();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * get EdgeOpcUaClient instance from sessionMap
   * @param  endpoint endpoint uri
   * @return EdgeOpcUaClient instance
   */
  public EdgeOpcUaClient getSession(String endpoint) {
    if (sessionMap.getNode(endpoint).isPresent() == true) {
      return sessionMap.getNode(endpoint).get();
    }
    return null;
  }


  /**
   * get endpoint list from server
   * @param  msg
   * @return The list of EdgeEndpoint(include endpointUri target endpoint uri (e.g. opc.tcp://localhost:12686/edge-opc-server)
   */
  public ArrayList<EdgeEndpointInfo> getEndpoints(EdgeMessage msg) throws Exception {
    String endpointUri = msg.getEdgeEndpointInfo().getEndpointUri();
    EndpointDescription[] endpoints = UaTcpStackClient.getEndpoints(endpointUri).get();
    ArrayList<EdgeEndpointInfo> endpointList = new ArrayList<EdgeEndpointInfo>();
    for (int i = 0; i < endpoints.length; i++) {
      logger.info("endpoint={}, {}, {}", endpoints[i].getEndpointUrl(),
          endpoints[i].getSecurityLevel(), endpoints[i].getSecurityPolicyUri());
      logger.info("    > {}, {}, {}", endpoints[i].getSecurityMode(),
          endpoints[i].getTransportProfileUri(), endpoints[i].getTypeId());
      endpointList.add(new EdgeEndpointInfo.Builder(endpoints[i].getEndpointUrl())
          .setConfig(new EdgeEndpointConfig.Builder()
              .setSecurityPolicyUri(endpoints[i].getSecurityPolicyUri()).build())
          .setFuture(msg.getEdgeEndpointInfo().getFuture())
          .build());
    }
    return endpointList;
  }
  
  private static class EdgeSessionMap extends EdgeAbstractSessionMap {
    // implement edge session map class
  }
}
