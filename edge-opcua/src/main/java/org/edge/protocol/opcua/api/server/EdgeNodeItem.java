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

package org.edge.protocol.opcua.api.server;

import org.edge.protocol.opcua.api.common.EdgeIdentifier;
import org.edge.protocol.opcua.api.common.EdgeNodeId;
import org.edge.protocol.opcua.api.common.EdgeNodeIdentifier;

public class EdgeNodeItem {
  private final String browseName;
  private EdgeIdentifier nodeType;
  private EdgeNodeIdentifier daNodeIdentifier;
  private int accessLevel;
  private int userAccessLevel;
  private int writeMask;
  private int userWriteMask;
  private boolean forward;
  private Object[][] variableItemSet;
  private EdgeNodeId sourceNodeId;

  public static class Builder {
    private final String browseName;
    private EdgeIdentifier nodeType = EdgeIdentifier.VARIABLE_NODE;
    private EdgeNodeIdentifier daNodeIdentifier = EdgeNodeIdentifier.VariableNode;
    private int accessLevel = EdgeIdentifier.getAccessLevel(EdgeIdentifier.READ_WRITE);
    private int userAccessLevel = EdgeIdentifier.getAccessLevel(EdgeIdentifier.READ_WRITE);
    private int writeMask = 0;
    private int userWriteMask = 0;
    private EdgeNodeId sourceNodeId;
    private boolean forward = true;
    private Object[][] variableItemSet = null;

    public Builder(String browseName) {
      this.browseName = browseName;
    }

    /**
     * set setEdgeNodeType
     * @param  nodeType such as VARIABLE_NODE, VARIABLE_TYPE_NODE, METHOD_NODE, OBJECT_NODE,
     *        ARRAY_NODE, OBJECT_TYPE_NODE, REFERENCE_TYPE_NODE, VIEW_NODE, DATA_TYPE_NODE
     * @return Builder
     */
    public Builder setEdgeNodeType(EdgeIdentifier nodeType) {
      this.nodeType = nodeType;
      return this;
    }

    /**
     * set Node Identifier - this function is available in Data Access Node
     * @param  id EdgeNodeIdentifier related Data Access
     * @return Builder
     */
    public Builder setDataAccessNodeId(EdgeNodeIdentifier id) {
      this.daNodeIdentifier = id;
      return this;
    }

    /**
     * set Parameter related Variable type such as VARIABLE_NODE, VARIABLE_TYPE_NODE,
     *        ARRAY_NODE, other type will be ignored.
     * @param  variableItems object set related variable (please refer EdgeSampleCommon.java)
     * @return Builder
     */
    public Builder setVariableItemSet(Object[][] variableItems) {
      this.variableItemSet = variableItems;
      return this;
    }

    public Builder setAccessLevel(int level) {
      this.accessLevel = level;
      return this;
    }

    public Builder setUserAccessLevel(int level) {
      this.userAccessLevel = level;
      return this;
    }

    public Builder setWriteMask(int mask) {
      this.writeMask = mask;
      return this;
    }

    public Builder setUserWriteMask(int mask) {
      this.userWriteMask = mask;
      return this;
    }

    /**
     * set Forward(reference direction) default detection is forward
     * @param  forward flag of forward or not
     * @return Builder
     */
    public Builder setForward(boolean forward) {
      this.forward = forward;
      return this;
    }

    /**
     * set source node which has HasComponent. this component will be Target Node if there is
     *        no set in EdgeNodeItem. source node will be root node.
     * @param  nodeId source nodeId
     * @return Builder
     */
    public Builder setSourceNode(EdgeNodeId nodeId) {
      this.sourceNodeId = nodeId;
      return this;
    }

    public EdgeNodeItem build() {
      return new EdgeNodeItem(this);
    }
  }

  private EdgeNodeItem(Builder builder) {
    browseName = builder.browseName;
    nodeType = builder.nodeType;
    daNodeIdentifier = builder.daNodeIdentifier;
    variableItemSet = builder.variableItemSet;
    accessLevel = builder.accessLevel;
    userAccessLevel = builder.userAccessLevel;
    writeMask = builder.writeMask;
    userWriteMask = builder.userWriteMask;
    forward = builder.forward;
    sourceNodeId = builder.sourceNodeId;
  }

  /**
   * get nodeName
   * @return nodeName
   */
  public String getBrowseName() {
    return browseName;
  }

  /**
   * get EdgeNodeType such as VARIABLE_NODE, VARIABLE_TYPE_NODE, METHOD_NODE, OBJECT_NODE,
   *        ARRAY_NODE, OBJECT_TYPE_NODE, REFERENCE_TYPE_NODE, VIEW_NODE, DATA_TYPE_NODE
   * @return EdgeIdentifier nodeType
   */
  public EdgeIdentifier getEdgeNodeType() {
    return nodeType;
  }

  /**
   * get Node Identifier - this function is available in Data Access Node
   * @return EdgeNodeIdentifier data node
   */
  public EdgeNodeIdentifier getDataAccessNodeId() {
    return daNodeIdentifier;
  }

  /**
   * get AccessLevel
   * @return accessLevel
   */
  public Object[][] getVariableItemSet() {
    return variableItemSet;
  }

  /**
   * get AccessLevel
   * @return accessLevel
   */
  public int getAccessLevel() {
    return accessLevel;
  }

  /**
   * get UserAccessLevel
   * @return userAccessLevel
   */
  public int getUserAccessLevel() {
    return userAccessLevel;
  }

  /**
   * get WriteMask
   * @return writeMask
   */
  public int getWriteMask() {
    return writeMask;
  }

  /**
   * get UserWriteMask
   * @return userWriteMask
   */
  public int getUserWriteMask() {
    return userWriteMask;
  }

  /**
   * get Forward(reference direction) default detection is forward
   * @return boolean forward
   */
  public boolean getForward() {
    return forward;
  }

  /**
   * get Source NodeId
   * @return EdgeNodeId sourceNodeId
   */
  public EdgeNodeId getSourceNode() {
    return sourceNodeId;
  }
}
