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

package org.edge.protocol.opcua.providers.services;

import java.util.concurrent.CompletableFuture;
import org.eclipse.milo.opcua.sdk.client.api.nodes.VariableNode;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.enumerated.ServerState;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.edge.protocol.mapper.api.EdgeMapper;
import org.edge.protocol.mapper.api.EdgeMapperCommon;
import org.edge.protocol.opcua.api.ProtocolManager;
import org.edge.protocol.opcua.api.client.EdgeResponse;
import org.edge.protocol.opcua.api.common.EdgeNodeInfo;
import org.edge.protocol.opcua.api.common.EdgeEndpointInfo;
import org.edge.protocol.opcua.api.common.EdgeIdentifier;
import org.edge.protocol.opcua.api.common.EdgeMessage;
import org.edge.protocol.opcua.api.common.EdgeMessageType;
import org.edge.protocol.opcua.api.common.EdgeNodeId;
import org.edge.protocol.opcua.api.common.EdgeNodeIdentifier;
import org.edge.protocol.opcua.api.common.EdgeOpcUaCommon;
import org.edge.protocol.opcua.api.common.EdgeResult;
import org.edge.protocol.opcua.api.common.EdgeStatusCode;
import org.edge.protocol.opcua.api.common.EdgeVersatility;
import org.edge.protocol.opcua.providers.services.da.EdgeAttributeService;
import org.edge.protocol.opcua.queue.ErrorHandler;
import org.edge.protocol.opcua.session.EdgeSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.google.common.collect.Lists.newArrayList;

public class EdgeServerService implements EdgeAttributeService {
  private final Logger logger = LoggerFactory.getLogger(getClass());
  private static EdgeServerService service = null;
  private static Object lock = new Object();
  private int readAccessLevel = 1;
  private EdgeMapper mapper = null;

  public static EdgeServerService getInstance() {
    synchronized (lock) {
      if (null == service) {
        service = new EdgeServerService();
      }
      return service;
    }
  }

  /**
   * get EdgeNodeInfo with the parameter to make nodeId of OPCUA library(Milo).
   * @param  valueAilas service provider key
   * @return EdgeNodeInfo
   */
  @Override
  public EdgeNodeInfo getNodeInfo(String valueAilas) {
    return new EdgeNodeInfo.Builder()
        .setEdgeNodeId(new EdgeNodeId.Builder(EdgeOpcUaCommon.SYSTEM_NAMESPACE_INDEX,
            EdgeNodeIdentifier.Server_ServerStatus_BuildInfo).build())
        .setValueAlias(valueAilas).build();
  }

  /**
   * read data from target Node in server (sync method) and response will be checked in
   *        onResonseMessage Callback with EdgeMessageType.SERVER_INFO and error message will be
   *        checked in onErrorMessage Callback.
   * @param  msg message
   * @return result of read request
   */
  @Override
  public EdgeResult readSync(EdgeMessage msg) throws Exception {
    EdgeNodeInfo ep = msg.getRequest().getEdgeNodeInfo();
    logger.info("ReadServerState(id={})", new NodeId(ep.getEdgeNodeID().getNameSpace(),
        ep.getEdgeNodeID().getEdgeNodeIdentifier().value()));

    boolean isGood = true;
    try {
      VariableNode vNode = EdgeSessionManager.getInstance()
          .getSession(msg.getEdgeEndpointInfo().getEndpointUri()).getClientInstance()
          .getAddressSpace().createVariableNode(new NodeId(ep.getEdgeNodeID().getNameSpace(),
              ep.getEdgeNodeID().getEdgeNodeIdentifier().value()));
      logger.info("ServerState : id={}, value={}", vNode.getNodeId().get().getIdentifier(),
          vNode.getValue().get());

      EdgeEndpointInfo epInfo =
          new EdgeEndpointInfo.Builder(msg.getEdgeEndpointInfo().getEndpointUri())
              .setFuture(msg.getEdgeEndpointInfo().getFuture()).build();
      EdgeMessage inputData = new EdgeMessage.Builder(epInfo)
          .setResponses(newArrayList(new EdgeResponse.Builder(ep, msg.getRequest().getRequestId())
              .setMessage(new EdgeVersatility.Builder(vNode.getValue().get()).build()).build()))
          .setMessageType(EdgeMessageType.SERVER_INFO).build();
      ProtocolManager.getProtocolManagerInstance().getRecvDispatcher().putQ(inputData);

    } catch (Exception ex) {
      isGood = false;
      logger.info("exception throw={}", ex.getMessage());
      ErrorHandler.getInstance().addErrorMessage(ep,
          new EdgeResult.Builder(EdgeStatusCode.STATUS_ERROR).build(),
          msg.getRequest().getRequestId());
    }
    return new EdgeResult.Builder(isGood ? EdgeStatusCode.STATUS_OK : EdgeStatusCode.STATUS_ERROR)
        .build();
  }

  private CompletableFuture<DataValue> readData(EdgeNodeInfo nodeInfo, EdgeEndpointInfo epInfo) {
    logger.info("readData to server={}, ={}",
        nodeInfo.getEdgeNodeID().getEdgeNodeIdentifier().value(),
        new NodeId(nodeInfo.getEdgeNodeID().getNameSpace(),
            nodeInfo.getEdgeNodeID().getEdgeNodeIdentifier().value()));
    return EdgeSessionManager.getInstance().getSession(epInfo.getEndpointUri()).getClientInstance()
        .readValue(0.0, TimestampsToReturn.Both, new NodeId(nodeInfo.getEdgeNodeID().getNameSpace(),
            nodeInfo.getEdgeNodeID().getEdgeNodeIdentifier().value()));
  }

  /**
   * it is not supported
   * @param  msg write value
   * @return result
   */
  @Override
  public EdgeResult write(EdgeMessage msg) throws Exception {
    return new EdgeResult.Builder(EdgeStatusCode.STATUS_OK).build();
  }

  /**
   * get edge service type related attribute service. Node Type is including
   *        Edge_Node_Class_Type, Edge_Node_ServerInfo_Type, Edge_Node_Custom_Type in
   *        EdgeNodeIdentifier
   * @return service node type (EdgeNodeIdentifier.Edge_Node_ServerInfo_Type)
   */
  @Override
  public EdgeNodeIdentifier getNodeType() throws Exception {
    return EdgeNodeIdentifier.Edge_Node_ServerInfo_Type;
  }

  /**
   * read data from target Node in server (async method) this function is worked on single
   *        target node. multi access is supported on EdgeGroupService. and response will be checked
   *        in onResonseMessage Callback with EdgeMessageType.SERVER_INFO and error message will be
   *        checked in onErrorMessage Callback.
   * @param  msg message
   * @return result
   */
  @Override
  public EdgeResult readAsync(EdgeMessage msg) throws Exception {
    // TODO Auto-generated method stub
    CompletableFuture<Void> future = new CompletableFuture<>();
    EdgeNodeInfo nodeInfo = msg.getRequest().getEdgeNodeInfo();

    if (nodeInfo == null) {
      EdgeResult ret = new EdgeResult.Builder(EdgeStatusCode.STATUS_PARAM_INVALID).build();
      ErrorHandler.getInstance().addErrorMessage(ret, msg.getRequest().getRequestId());
      return ret;
    }

    readData(nodeInfo, msg.getEdgeEndpointInfo()).thenAccept(values -> {
      logger.info("response server={} from={}",
          ServerState.from((Integer) values.getValue().getValue()),
          msg.getEdgeEndpointInfo().getEndpointUri());

      EdgeEndpointInfo epInfo =
          new EdgeEndpointInfo.Builder(msg.getEdgeEndpointInfo().getEndpointUri())
              .setFuture(msg.getEdgeEndpointInfo().getFuture()).build();
      EdgeMessage inputData =
          new EdgeMessage.Builder(epInfo)
              .setResponses(
                  newArrayList(new EdgeResponse.Builder(nodeInfo, msg.getRequest().getRequestId())
                      .setMessage(new EdgeVersatility.Builder(
                          ServerState.from((Integer) values.getValue().getValue())).build())
                      .build()))
              .setMessageType(EdgeMessageType.SERVER_INFO).build();
      ProtocolManager.getProtocolManagerInstance().getRecvDispatcher().putQ(inputData);

      future.complete(null);
    });
    logger.info("readAsyc is called");
    return new EdgeResult.Builder(EdgeStatusCode.STATUS_OK).build();
  }

  /**
   * get mapper instance this function is provided metadata such as access-level, data-type.
   * @return mapper instance
   */
  public EdgeMapper getMapper() {
    if (mapper == null) {
      mapper = new EdgeMapper();
      try {
        mapper.addMappingData(EdgeMapperCommon.PROPERTYVALUE_READWRITE.name(),
            EdgeIdentifier.convertAccessLevel(this.readAccessLevel));
      } catch (Exception e) {
        // TODO Auto-generated catch block
        ErrorHandler.getInstance().addErrorMessage(new EdgeNodeInfo.Builder().build(),
            new EdgeResult.Builder(EdgeStatusCode.STATUS_INTERNAL_ERROR).build(),
            EdgeOpcUaCommon.DEFAULT_REQUEST_ID);
        e.printStackTrace();
      }
    }
    return mapper;
  }

  /**
   * set Property Data for Mapper
   * @param v a node for which user wan to set property
   */
  @Override
  public void setProperty(VariableNode v) throws Exception {
    // TODO Auto-generated method stub
    if (v == null)
      return;
    readAccessLevel = Integer.parseInt(v.readAccessLevel().get().getValue().getValue().toString());
  }

  @Override
  public NodeId getNodeId() {
    return null;
  }
}
