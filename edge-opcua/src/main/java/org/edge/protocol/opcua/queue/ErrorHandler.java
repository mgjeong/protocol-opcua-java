/******************************************************************
 *
 * Copyright 2017 Samsung Electronics All Rights Reserved.
 *
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 ******************************************************************/

package org.edge.protocol.opcua.queue;

import org.edge.protocol.opcua.api.ProtocolManager;
import org.edge.protocol.opcua.api.client.EdgeResponse;
import org.edge.protocol.opcua.api.common.EdgeNodeInfo;
import org.edge.protocol.opcua.api.common.EdgeOpcUaCommon;
import org.edge.protocol.opcua.api.common.EdgeEndpointInfo;
import org.edge.protocol.opcua.api.common.EdgeMessage;
import org.edge.protocol.opcua.api.common.EdgeMessageType;
import org.edge.protocol.opcua.api.common.EdgeResult;
import org.edge.protocol.opcua.api.common.EdgeVersatility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.google.common.collect.Lists.newArrayList;

public class ErrorHandler {
  private final Logger logger = LoggerFactory.getLogger(getClass());
  private static ErrorHandler errorHandler;

  /**
   * get error handler instance
   * @return error handler instance
   */
  public static ErrorHandler getInstance() {

    if (errorHandler == null) {
      errorHandler = new ErrorHandler();
    }

    return errorHandler;
  }

  /**
   * add error message into receive queue
   * @param  epInfo endpoint
   * @param  nodeInfo node Information
   * @param  ret error result
   * @param  value reason
   */
  public void addErrorMessage(EdgeEndpointInfo epInfo, EdgeNodeInfo nodeInfo, EdgeResult ret,
      EdgeVersatility value, int requestId) {
    logger.debug("message={} provider={}, value={}", ret.getStatusCode(),
        nodeInfo.getValueAlias(), value);
    EdgeMessage errorData = new EdgeMessage.Builder(epInfo)
        .setResponses(
            newArrayList(new EdgeResponse.Builder(nodeInfo, requestId).setMessage(value).build()))
        .setMessageType(EdgeMessageType.ERROR).setResult(ret).build();
    ProtocolManager.getProtocolManagerInstance().getRecvDispatcher().putQ(errorData);
  }

  /**
   * add error message without no reason into receive queue
   * @param  epInfo endpoint
   * @param  nodeInfo node Information
   * @param  ret error result
   */
  public void addErrorMessage(EdgeEndpointInfo epInfo, EdgeNodeInfo nodeInfo, EdgeResult ret,
      int requestId) {
    logger.debug("message={} provider={}", ret.getStatusCode(),
        nodeInfo.getValueAlias());
    EdgeMessage errorData = new EdgeMessage.Builder(epInfo).setMessageType(EdgeMessageType.ERROR)
        .setResult(ret).build();
    ProtocolManager.getProtocolManagerInstance().getRecvDispatcher().putQ(errorData);
  }

  /**
   * add error message without no reason into receive queue
   * @param  nodeInfo node Information
   * @param  ret error result
   * @param  requestId request ID
   */
  public void addErrorMessage(EdgeNodeInfo nodeInfo, EdgeResult ret, int requestId) {
    logger.debug("message={} provider={}", ret.getStatusCode(),
        nodeInfo.getValueAlias());
    EdgeEndpointInfo epInfo =
        new EdgeEndpointInfo.Builder(EdgeOpcUaCommon.WELL_KNOWN_LOCALHOST_URI.getValue()).build();
    EdgeMessage errorData = new EdgeMessage.Builder(epInfo).setMessageType(EdgeMessageType.ERROR)
        .setResult(ret).build();
    ProtocolManager.getProtocolManagerInstance().getRecvDispatcher().putQ(errorData);
  }

  /**
   * add error message without no reason into receive queue
   * @param  ret error result
   * @param  requestId request ID
   */
  public void addErrorMessage(EdgeResult ret, int requestId) {
    logger.debug("message={} provider={}", ret.getStatusCode());
    EdgeEndpointInfo epInfo =
        new EdgeEndpointInfo.Builder(EdgeOpcUaCommon.WELL_KNOWN_LOCALHOST_URI.getValue()).build();
    EdgeMessage errorData = new EdgeMessage.Builder(epInfo).setMessageType(EdgeMessageType.ERROR)
        .setResult(ret).build();
    ProtocolManager.getProtocolManagerInstance().getRecvDispatcher().putQ(errorData);
  }

  /**
   * add error message into receive queue
   * @param  nodeInfo node Information
   * @param  ret error result
   * @param  value reason
   */
  public void addErrorMessage(EdgeNodeInfo nodeInfo, EdgeResult ret, EdgeVersatility value,
      int requestId) {
    logger.debug("message={} provider={}, value={}", ret.getStatusCode(),
        nodeInfo.getValueAlias(), value);
    EdgeEndpointInfo epInfo =
        new EdgeEndpointInfo.Builder(EdgeOpcUaCommon.WELL_KNOWN_LOCALHOST_URI.getValue()).build();
    EdgeMessage errorData = new EdgeMessage.Builder(epInfo)
        .setResponses(
            newArrayList(new EdgeResponse.Builder(nodeInfo, requestId).setMessage(value).build()))
        .setMessageType(EdgeMessageType.ERROR).setResult(ret).build();
    ProtocolManager.getProtocolManagerInstance().getRecvDispatcher().putQ(errorData);
  }
}
