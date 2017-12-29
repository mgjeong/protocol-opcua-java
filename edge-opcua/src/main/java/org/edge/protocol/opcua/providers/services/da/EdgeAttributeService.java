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

import org.eclipse.milo.opcua.sdk.client.api.nodes.VariableNode;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.edge.protocol.mapper.api.EdgeMapper;
import org.edge.protocol.opcua.api.common.EdgeNodeInfo;
import org.edge.protocol.opcua.api.common.EdgeMessage;
import org.edge.protocol.opcua.api.common.EdgeNodeIdentifier;
import org.edge.protocol.opcua.api.common.EdgeResult;
import org.edge.protocol.opcua.providers.EdgeBaseService;

public interface EdgeAttributeService extends EdgeBaseService {

  /**
   * read node data synchronously
   * @param  EdgeMessage msg
   * @return result
   * @throws excepiton
   */
  public EdgeResult readSync(EdgeMessage msg) throws Exception;

  /**
   * write edge message
   * @param  EdgeMessage msg
   * @return result
   * @throws excepiton
   */
  public EdgeResult write(EdgeMessage msg) throws Exception;

  /**
   * get node type
   * @return EdgeNodeIdentifier
   * @throws excepiton
   */
  public EdgeNodeIdentifier getNodeType() throws Exception;

  /**
   * read node data asynchronously
   * @param  EdgeMessage msg
   * @return result
   * @throws excepiton
   */
  public EdgeResult readAsync(EdgeMessage msg) throws Exception;

  /**
   * set property of node
   * @param  {@link}VariableNode node
   * @throws excepiton
   */
  public void setProperty(VariableNode node) throws Exception;

  /**
   * get mapper
   * @return EdgeMapper
   */
  public EdgeMapper getMapper();

  /**
   * get NodeInfo
   * @param  String valueAilas
   * @return EdgeNodeInfo
   */
  public EdgeNodeInfo getNodeInfo(String valueAilas);

  /**
   * get node id
   * @return NodeId
   */
  public NodeId getNodeId();
}
