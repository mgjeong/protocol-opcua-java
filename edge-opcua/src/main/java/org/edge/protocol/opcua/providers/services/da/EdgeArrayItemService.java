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

package org.edge.protocol.opcua.providers.services.da;

import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.eclipse.milo.opcua.sdk.client.model.nodes.variables.ArrayItemNode;
import org.eclipse.milo.opcua.sdk.client.model.nodes.variables.PropertyNode;
import org.eclipse.milo.opcua.stack.core.types.builtin.ExtensionObject;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.eclipse.milo.opcua.stack.core.types.structured.EUInformation;
import org.eclipse.milo.opcua.stack.core.types.structured.Range;
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
import org.edge.protocol.opcua.api.common.EdgeRequest;
import org.edge.protocol.opcua.api.common.EdgeResult;
import org.edge.protocol.opcua.api.common.EdgeStatusCode;
import org.edge.protocol.opcua.api.common.EdgeVersatility;
import org.edge.protocol.opcua.queue.ErrorHandler;
import org.edge.protocol.opcua.session.EdgeSessionManager;
import static com.google.common.collect.Lists.newArrayList;

public class EdgeArrayItemService extends EdgeDataItemService {
  private ArrayItemNode node = null;
  private EdgeMapper mapper = null;

  private final int nameSpace;
  private final String endpointUri;

  private static Object lock = new Object();

  /**
   * constructor of EdgeArrayItemService class
   */
  public EdgeArrayItemService() {
    nameSpace = EdgeOpcUaCommon.DEFAULT_NAMESPACE_INDEX;
    endpointUri = null;
  }

  /**
   * constructor of EdgeArrayItemService class
   * @param nameSpace a value of namespace
   * @param endpointUri uri of endpoint
   */
  public EdgeArrayItemService(int nameSpace, String endpointUri) {
    this.nameSpace = nameSpace;
    this.endpointUri = endpointUri;

    try {
      this.setMapper();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * get array item node instance
   * @return ArrayItemNode
   */
  private ArrayItemNode getNodeInstance() {
    synchronized (lock) {
      if (null == node)
        node = new ArrayItemNode(
            EdgeSessionManager.getInstance().getSession(endpointUri).getClientInstance(),
            new NodeId(nameSpace, EdgeNodeIdentifier.ArrayItemType.value()));
    }
    return node;
  }

  /**
   * get EdgeNodeInfo with the parameter to make nodeId of OPCUA library(Milo).
   * @param  valueAilas service provider key
   * @return EdgeNodeInfo
   */
  @Override
  public EdgeNodeInfo getNodeInfo(String valueAilas) {
    return new EdgeNodeInfo.Builder()
        .setEdgeNodeId(new EdgeNodeId.Builder(nameSpace, EdgeNodeIdentifier.ArrayItemType).build())
        .setValueAlias(valueAilas).build();
  }

  /**
   * read node data synchronously
   * @param  msg message
   * @return result
   */
  @Override
  public EdgeResult readSync(EdgeMessage msg) throws Exception {
    Variant ret = null;
    EdgeNodeInfo ep = msg.getRequest().getEdgeNodeInfo();
    EdgeNodeIdentifier id = EdgeNodeIdentifier.ReadValueId;
    if (ep.getEdgeNodeID() != null) {
      id = ep.getEdgeNodeID().getEdgeNodeIdentifier();
    }

    if (EdgeNodeIdentifier.ReadValueId == id) {
      ret = readValue(getNodeInstance().getValue().get());
    } else if (EdgeNodeIdentifier.ArrayItemType_Definition == id) {
      ret = readDefinition(getNodeInstance().getDefinition().get());
    } else if (EdgeNodeIdentifier.ArrayItemType_EURange == id) {
      ret = readEURange(getNodeInstance().eURange().get().getValue().get());
    } else if (EdgeNodeIdentifier.ArrayItemType_InstrumentRange == id) {
      ret = readInstrumentRange(getNodeInstance().instrumentRange().get().getValue().get());
    } else if (EdgeNodeIdentifier.ArrayItemType_EngineeringUnits == id) {
      ret = readEngineeringUnits(getNodeInstance().engineeringUnits().get().getValue().get());
    } else if (EdgeNodeIdentifier.ArrayItemType_ValuePrecision == id) {
      ret = readValuePrecision(getNodeInstance().getValuePrecision().get());
    } else if (EdgeNodeIdentifier.ArrayItemType_Title == id) {
      ret = readTitle(getNodeInstance().getTitle().get().getText());
    } else if (EdgeNodeIdentifier.ArrayItemType_AxisScaleType == id) {
      ret = readAxisScaleType(getNodeInstance().axisScaleType().get().getValue().get());
    } else {
      return new EdgeResult.Builder(EdgeStatusCode.STATUS_PARAM_INVALID).build();
    }

    if (ret != null && ret.isNotNull()) {
      EdgeEndpointInfo epInfo =
          new EdgeEndpointInfo.Builder(msg.getEdgeEndpointInfo().getEndpointUri())
              .setFuture(msg.getEdgeEndpointInfo().getFuture()).build();
      EdgeMessage inputData = new EdgeMessage.Builder(epInfo)
          .setMessageType(EdgeMessageType.GENERAL_RESPONSE)
          .setResponses(newArrayList(new EdgeResponse.Builder(ep, msg.getRequest().getRequestId())
              .setMessage(new EdgeVersatility.Builder(ret.getValue()).build()).build()))
          .build();
      ProtocolManager.getProtocolManagerInstance().getRecvDispatcher().putQ(inputData);
      return new EdgeResult.Builder(EdgeStatusCode.STATUS_OK).build();
    }
    return new EdgeResult.Builder(EdgeStatusCode.STATUS_ERROR).build();
  }

  /**
   * read title
   * @param  title title of arayItemNode
   * @return variant which has title
   */
  protected Variant readTitle(String title) throws InterruptedException, ExecutionException {
    return new Variant(title);
  }

  /**
   * read title asynchronously
   * @param  aNode arrayItemNode 
   * @param  msg message
   * @return variant which has title
   */
  protected CompletableFuture<String> readAsyncTitle(ArrayItemNode aNode, EdgeMessage msg) {
    return aNode.getTitle().thenApply(values -> {
      return values.getText();
    }).exceptionally(e -> {
      Optional.ofNullable(msg.getRequest().getEdgeNodeInfo()).ifPresent(endpoint -> {
        ErrorHandler.getInstance().addErrorMessage(endpoint,
            new EdgeResult.Builder(EdgeStatusCode.STATUS_ERROR).build(),
            new EdgeVersatility.Builder(e.getMessage()).build(), msg.getRequest().getRequestId());
      });
      return null;
    });
  }

  /**
   * read AxisScaleType
   * @param  axisScaleType axisScaleType
   * @return variant which has AxisScaleType
   */
  protected Variant readAxisScaleType(Object axisScaleType)
      throws InterruptedException, ExecutionException {
    return new Variant(axisScaleType);
  }

  /**
   * read AxisScaleType
   * @param  aNode aNode
   * @param  msg message
   * @return variant which has AxisScaleType
   */
  protected CompletableFuture<PropertyNode> readAsyncAxisScaleType(ArrayItemNode aNode,
      EdgeMessage msg) {
    return aNode.axisScaleType().thenApply(value -> {
      return value;
    }).exceptionally(e -> {
      Optional.ofNullable(msg.getRequest().getEdgeNodeInfo()).ifPresent(endpoint -> {
        ErrorHandler.getInstance().addErrorMessage(endpoint,
            new EdgeResult.Builder(EdgeStatusCode.STATUS_ERROR).build(),
            new EdgeVersatility.Builder(e.getMessage()).build(), msg.getRequest().getRequestId());
      });
      return null;
    });
  }

  /**
   * read instrument range
   * @param  instrumentRange instrumentRage 
   * @return variant which has instrumentRange
   */
  protected Variant readInstrumentRange(Object instrumentRange)
      throws InterruptedException, ExecutionException {
    return new Variant(convertToRangeInfo(instrumentRange));
  }

  /**
   * read engineering units
   * @param  engineeringUnit engineeringUnit
   * @return variant which has engineeringUnit
   */
  protected Variant readEngineeringUnits(Object engineeringUnit)
      throws InterruptedException, ExecutionException {
    return new Variant(convertToEUInfo(engineeringUnit));
  }

  /**
   * read EU range
   * @param  Object euRange
   * @return Variant
   */
  protected Variant readEURange(Object euRange) throws InterruptedException, ExecutionException {
    return new Variant(convertToRangeInfo(euRange));
  }

  /**
   * read instrument range (Async)
   * @param  aNode arrayItemNode
   * @param  msg message
   * @return instrumentRage
   */
  protected CompletableFuture<PropertyNode> readAsyncInstrumentRange(ArrayItemNode aNode,
      EdgeMessage msg) {
    return aNode.instrumentRange().thenApply(property -> {
      return property;
    }).exceptionally(e -> {
      Optional.ofNullable(msg.getRequest().getEdgeNodeInfo()).ifPresent(endpoint -> {
        ErrorHandler.getInstance().addErrorMessage(endpoint,
            new EdgeResult.Builder(EdgeStatusCode.STATUS_ERROR).build(),
            new EdgeVersatility.Builder(e.getMessage()).build(), msg.getRequest().getRequestId());
      });
      return null;
    });
  }

  /**
   * read engineering units (Async)
   * @param  aNode arrayItemNode
   * @param  msg message
   * @return EngineeringUnits
   */
  protected CompletableFuture<PropertyNode> readAsyncEngineeringUnits(ArrayItemNode aNode,
      EdgeMessage msg) {
    return aNode.engineeringUnits().thenApply(property -> {
      return property;
    }).exceptionally(e -> {
      Optional.ofNullable(msg.getRequest().getEdgeNodeInfo()).ifPresent(endpoint -> {
        ErrorHandler.getInstance().addErrorMessage(endpoint,
            new EdgeResult.Builder(EdgeStatusCode.STATUS_ERROR).build(),
            new EdgeVersatility.Builder(e.getMessage()).build(), msg.getRequest().getRequestId());
      });
      return null;
    });
  }

  /**
   * read EU range (Async)
   * @param  ArrayItemNode aNode
   * @param  EdgeMessage msg
   * @return CompletableFuture<PropertyNode>
   */
  protected CompletableFuture<PropertyNode> readAsyncEURange(ArrayItemNode aNode, EdgeMessage msg) {
    return aNode.eURange().thenApply(property -> {
      return property;
    }).exceptionally(e -> {
      Optional.ofNullable(msg.getRequest().getEdgeNodeInfo()).ifPresent(endpoint -> {
        ErrorHandler.getInstance().addErrorMessage(endpoint,
            new EdgeResult.Builder(EdgeStatusCode.STATUS_ERROR).build(),
            new EdgeVersatility.Builder(e.getMessage()).build(), msg.getRequest().getRequestId());
      });
      return null;
    });
  }

  /**
   * convert property of node
   * @param  property property
   * @param  id NodeId
   * @return HashMap of property
   */
  protected CompletableFuture<HashMap<String, String>> convertProperty(PropertyNode property,
      EdgeNodeIdentifier id) {
    CompletableFuture<HashMap<String, String>> future = null;
    if (EdgeNodeIdentifier.Range == id) {
      future = property.getValue().thenApply(value -> {
        return convertToRangeInfo(value);
      });
    } else if (EdgeNodeIdentifier.EUInformation == id) {
      future = property.getValue().thenApply(value -> {
        return convertToEUInfo(value);
      });
    }
    return future;
  }

  /**
   * convert object to range information
   * @param  obj obj
   * @return HashMap of range information
   */
  protected HashMap<String, String> convertToRangeInfo(Object obj) {
    ExtensionObject extensionObj = (ExtensionObject) obj;
    Range range = extensionObj.decode();
    HashMap<String, String> info = new HashMap<String, String>();
    info.put("High", range.getHigh().toString());
    info.put("Low", range.getLow().toString());

    return info;
  }

  /**
   * convert object to EU information
   * @param  Object obj
   * @return HashMap<String, String>
   */
  protected HashMap<String, String> convertToEUInfo(Object opj) {
    ExtensionObject extensionObj = (ExtensionObject) opj;
    EUInformation euInfo = extensionObj.decode();
    HashMap<String, String> info = new HashMap<String, String>();
    info.put("Description", euInfo.getDescription().getText());
    info.put("DisplayName", euInfo.getDisplayName().getText());

    return info;
  }

  /**
   * read node data asynchronously
   * @param  msg edge message
   * @return result
   */
  @Override
  public EdgeResult readAsync(EdgeMessage msg) throws Exception {
    EdgeNodeInfo ep = msg.getRequest().getEdgeNodeInfo();
    EdgeNodeIdentifier id = EdgeNodeIdentifier.ReadValueId;
    if (ep.getEdgeNodeID() != null) {
      id = ep.getEdgeNodeID().getEdgeNodeIdentifier();
    }

    if (EdgeNodeIdentifier.ReadValueId == id) {
      readAsyncValue(getNodeInstance(), msg).thenAccept(values -> {
        Optional.ofNullable(values).ifPresent(value -> {
          addResponse(value, ep, msg);
        });
      });
    } else if (EdgeNodeIdentifier.ArrayItemType_Definition == id) {
      readAsyncDefinition(getNodeInstance(), msg).thenAccept(values -> {
        Optional.ofNullable(values).ifPresent(value -> {
          addResponse(value, ep, msg);
        });
      });
    } else if (EdgeNodeIdentifier.ArrayItemType_EURange == id) {
      readAsyncEURange(getNodeInstance(), msg).thenAccept(property -> {
        Optional.ofNullable(property).ifPresent(value -> {
          convertProperty(property, EdgeNodeIdentifier.Range).thenAccept(info -> {
            addResponse(value, ep, msg);
          });
        });
      });
    } else if (EdgeNodeIdentifier.ArrayItemType_InstrumentRange == id) {
      readAsyncInstrumentRange(getNodeInstance(), msg).thenAccept(property -> {
        Optional.ofNullable(property).ifPresent(value -> {
          convertProperty(property, EdgeNodeIdentifier.Range).thenAccept(info -> {
            addResponse(value, ep, msg);
          });
        });
      });
    } else if (EdgeNodeIdentifier.ArrayItemType_EngineeringUnits == id) {
      readAsyncEngineeringUnits(getNodeInstance(), msg).thenAccept(property -> {
        Optional.ofNullable(property).ifPresent(value -> {
          convertProperty(property, EdgeNodeIdentifier.EUInformation).thenAccept(info -> {
            addResponse(value, ep, msg);
          });
        });
      });
    } else if (EdgeNodeIdentifier.ArrayItemType_ValuePrecision == id) {
      readAsyncValuePrecision(getNodeInstance(), msg).thenAccept(values -> {
        Optional.ofNullable(values).ifPresent(value -> {
          addResponse(value, ep, msg);
        });
      });
    } else if (EdgeNodeIdentifier.ArrayItemType_Title == id) {
      readAsyncTitle(getNodeInstance(), msg).thenAccept(values -> {
        Optional.ofNullable(values).ifPresent(value -> {
          addResponse(value, ep, msg);
        });
      });
    } else if (EdgeNodeIdentifier.ArrayItemType_AxisScaleType == id) {
      readAsyncAxisScaleType(getNodeInstance(), msg).thenAccept(values -> {
        Optional.ofNullable(values).ifPresent(value -> {

        });
      });
    } else {
      ErrorHandler.getInstance().addErrorMessage(ep,
          new EdgeResult.Builder(EdgeStatusCode.STATUS_PARAM_INVALID).build(),
          msg.getRequest().getRequestId());
      return new EdgeResult.Builder(EdgeStatusCode.STATUS_ERROR).build();
    }

    return new EdgeResult.Builder(EdgeStatusCode.STATUS_OK).build();
  }

  private void addResponse(Object value, EdgeNodeInfo nodeInfo, EdgeMessage msg) {
    EdgeEndpointInfo epInfo =
        new EdgeEndpointInfo.Builder(msg.getEdgeEndpointInfo().getEndpointUri())
            .setFuture(msg.getEdgeEndpointInfo().getFuture()).build();
    EdgeMessage inputData =
        new EdgeMessage.Builder(epInfo).setMessageType(EdgeMessageType.GENERAL_RESPONSE)
            .setResponses(
                newArrayList(new EdgeResponse.Builder(nodeInfo, msg.getRequest().getRequestId())
                    .setMessage(new EdgeVersatility.Builder(value).build()).build()))
            .build();
    ProtocolManager.getProtocolManagerInstance().getRecvDispatcher().putQ(inputData);
  }

  /**
   * write edge message
   * @param  msg edge message
   * @return result
   */
  @Override
  public EdgeResult write(EdgeMessage msg) throws Exception {
    writeAsyncValue(getNodeInstance(), msg).thenAccept(status -> {
      Optional.ofNullable(status).ifPresent(value -> {
        EdgeEndpointInfo epInfo =
            new EdgeEndpointInfo.Builder(msg.getEdgeEndpointInfo().getEndpointUri())
                .setFuture(msg.getEdgeEndpointInfo().getFuture()).build();
        EdgeMessage inputData = new EdgeMessage.Builder(epInfo)
            .setMessageType(EdgeMessageType.GENERAL_RESPONSE)
            .setResponses(newArrayList(new EdgeResponse.Builder(msg.getRequest().getEdgeNodeInfo(),
                msg.getRequest().getRequestId())
                    .setMessage(new EdgeVersatility.Builder(status).build()).build()))
            .build();
        ProtocolManager.getProtocolManagerInstance().getRecvDispatcher().putQ(inputData);
      });
    });
    return new EdgeResult.Builder(EdgeStatusCode.STATUS_OK).build();
  }

  /**
   * get node type of array item
   * @return EdgeNodeIdentifier
   */
  @Override
  public EdgeNodeIdentifier getNodeType() throws Exception {
    return EdgeNodeIdentifier.ArrayItemType;
  }

  /**
   * set mapper
   */
  @Override
  public void setMapper() throws Exception {
    mapper = new EdgeMapper();

    if (null != dataType) {
      mapper.addMappingData(EdgeMapperCommon.DEVICEOBJECT_ATTRIBUTE_DATATYPE.name(), dataType);
    }
    mapper.addMappingData(EdgeMapperCommon.PROPERTYVALUE_TYPE.name(),
        EdgeNodeIdentifier.BaseDataType.name().toString());
    mapper.addMappingData(EdgeMapperCommon.PROPERTYVALUE_READWRITE.name(),
        EdgeIdentifier.convertAccessLevel(readAccessLevel));

    EdgeEndpointInfo epInfo =
        new EdgeEndpointInfo.Builder(EdgeOpcUaCommon.WELL_KNOWN_LOCALHOST_URI.getValue()).build();
    EdgeMessage msg = new EdgeMessage.Builder(epInfo)
        .setRequest(new EdgeRequest.Builder(new EdgeNodeInfo.Builder().build()).build()).build();

    readAsyncDescription(getNodeInstance()).thenAccept(values -> {
      Optional.ofNullable(values).ifPresent(value -> {
        try {
          mapper.addMappingData(EdgeMapperCommon.DEVICEOBJECT_DESCRIPTION.name(), values.getText());
        } catch (Exception e) {
          e.printStackTrace();
        }
      });
    });
    readAsyncDefinition(getNodeInstance(), msg).thenAccept(values -> {
      Optional.ofNullable(values).ifPresent(value -> {
        try {
          mapper.addMappingData(EdgeMapperCommon.PROPERTYVALUE_ASSERTION.name(), values);
        } catch (Exception e) {
          e.printStackTrace();
        }
      });
    });
    readAsyncValuePrecision(getNodeInstance(), msg).thenAccept(values -> {
      Optional.ofNullable(values).ifPresent(value -> {
        try {
          mapper.addMappingData(EdgeMapperCommon.PROPERTYVALUE_PRECISION.name(), value.toString());
        } catch (Exception e) {
          e.printStackTrace();
        }
      });
    });
    readAsyncEngineeringUnits(getNodeInstance(), msg).thenAccept(property -> {
      Optional.ofNullable(property).ifPresent(value -> {
        convertProperty(property, EdgeNodeIdentifier.EUInformation).thenAccept(values -> {
          try {
            mapper.addMappingData(EdgeMapperCommon.UNITS_TYPES.name(), values.get("DisplayName"));
          } catch (Exception e) {
            e.printStackTrace();
          }
        });
      });
    });
    readAsyncEURange(getNodeInstance(), msg).thenAccept(property -> {
      Optional.ofNullable(property).ifPresent(value -> {
        convertProperty(property, EdgeNodeIdentifier.Range).thenAccept(values -> {
          try {
            mapper.addMappingData(EdgeMapperCommon.PROPERTYVALUE_MAX.name(), values.get("High"));
            mapper.addMappingData(EdgeMapperCommon.PROPERTYVALUE_MIN.name(), values.get("Low"));
          } catch (Exception e) {
            e.printStackTrace();
          }
        });
      });
    });
    readAsyncAxisScaleType(getNodeInstance(), msg).thenAccept(property -> {
      Optional.ofNullable(property).ifPresent(value -> {
        property.getValue().thenAccept(values -> {
          try {
            mapper.addMappingData(EdgeMapperCommon.PROPERTYVALUE_SCALE.name(), values.toString());
          } catch (Exception e) {
            e.printStackTrace();
          }
        });
      });
    });
  }

  /**
   * get mapper
   * @return EdgeMapper
   */
  public EdgeMapper getMapper() {
    return mapper;
  }
}
