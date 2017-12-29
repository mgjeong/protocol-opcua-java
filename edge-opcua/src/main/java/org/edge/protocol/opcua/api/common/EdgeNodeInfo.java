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

package org.edge.protocol.opcua.api.common;

public class EdgeNodeInfo {
  private String methodName;
  private String valueAlias;
  private EdgeNodeId nodeId;

  public static class Builder {
    private String methodName = null;
    private EdgeNodeId nodeId = null;
    private String valueAlias = null;

    public Builder() {}

    /**
     * set EdgeNodeId
     * @param  nodeId node Id
     * @return this
     */
    public Builder setEdgeNodeId(EdgeNodeId nodeId) {
      this.nodeId = nodeId;
      return this;
    }

    /**
     * set Method Name
     * @param  methodName Method Name
     * @return this
     */
    public Builder setMethodName(String methodName) {
      this.methodName = methodName;
      return this;
    }

    /**
     * set the Alias of Target Node
     * @param  name Method Name
     * @return this
     */
    public Builder setValueAlias(String name) {
      this.valueAlias = name;
      return this;
    }

    /**
     * create EdgeEndpoint instance (builder)
     * @return EdgeEndpoint instance
     */
    public EdgeNodeInfo build() {
      return new EdgeNodeInfo(this);
    }
  }

  /**
   * constructor
   * @param  builder EdgeEndpoint Builder
   */
  private EdgeNodeInfo(Builder builder) {
    methodName = builder.methodName;
    valueAlias = builder.valueAlias;
    nodeId = builder.nodeId;
  }

  /**
   * get method name
   * @return methodName
   */
  public String getMethodName() {
    return methodName;
  }

  /**
   * get EdgeNodeId
   * @return nodeId
   */
  public EdgeNodeId getEdgeNodeID() {
    return nodeId;
  }

  /**
   * get the valueAlias of the targetNode
   * @return valueAlias
   */
  public String getValueAlias() {
    return valueAlias;
  }
}
