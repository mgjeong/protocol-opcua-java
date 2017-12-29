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

import java.util.List;
import org.edge.protocol.opcua.api.client.EdgeBrowseParameter;
import org.edge.protocol.opcua.api.client.EdgeResponse;

public class EdgeMessage {
  private EdgeMessageType type;
  private EdgeCommandType command;
  private EdgeEndpointInfo endpointInfo;
  private EdgeRequest request;
  private List<EdgeRequest> requests;
  private List<EdgeResponse> responses;
  private EdgeResult result;
  private EdgeBrowseParameter browseMsg;
  private List<EdgeBrowseResult> browseResult;

  public static class Builder {
    private EdgeMessageType type = EdgeMessageType.SEND_REQUEST;
    private EdgeCommandType command = EdgeCommandType.CMD_READ;
    private EdgeEndpointInfo endpointInfo = null;
    private EdgeRequest request = null;
    private List<EdgeRequest> requests = null;
    private List<EdgeResponse> responses = null;
    private EdgeResult result = null;
    private EdgeBrowseParameter browseMsg = null;
    private List<EdgeBrowseResult> browseResult = null;

    public Builder(EdgeEndpointInfo endpointInfo) {
      this.endpointInfo = endpointInfo;
    }

    /**
     * set single request information
     * @param  request send request
     * @return this
     */
    public Builder setRequest(EdgeRequest request) {
      this.request = request;
      return this;
    }

    /**
     * set multiple request information ( Max Request Size is 10 in Browse )
     * @param  requests send request list
     * @return this
     */
    public Builder setRequests(List<EdgeRequest> requests) {
      this.requests = requests;
      return this;
    }


    /**
     * set browse message
     * @param  req browseMsg
     * @return this
     */
    public Builder setBrowseParameter(EdgeBrowseParameter req) {
      this.browseMsg = req;
      return this;
    }

    /**
     * set browse result
     * @param  browseResult
     * @return this
     */
    public Builder setBrowseResult(List<EdgeBrowseResult> browseResult) {
      this.browseResult = browseResult;
      return this;
    }

    /**
     * set multiple response information
     * @param  responses received response information list
     * @return this
     */
    public Builder setResponses(List<EdgeResponse> responses) {
      this.responses = responses;
      return this;
    }

    /**
     * set result you can find detailed result data in EdgeStatusCode
     * @param  result edgeResult
     * @return this
     */
    public Builder setResult(EdgeResult result) {
      this.result = result;
      return this;
    }

    /**
     * set message type it appears that message type such as single request, multiple
     *        request, response message, event message, etc
     * @param  type message type
     * @return this
     */
    public Builder setMessageType(EdgeMessageType type) {
      this.type = type;
      return this;
    }

    /**
     * set command(operation) you can find detailed command information as CMD_XXX in
     *        EdgeOpcUaCommon enum class
     * @param  cmd command
     * @return this
     */
    public Builder setCommand(EdgeCommandType cmd) {
      this.command = cmd;
      return this;
    }

    /**
     * EdgeMesssage instance creator
     * @return EdgeMessage instance
     */
    public EdgeMessage build() {
      return new EdgeMessage(this);
    }
  }

  /**
   * constructor
   * @param  builder EdgeMessage Builder
   */
  private EdgeMessage(Builder builder) {
    request = builder.request;
    requests = builder.requests;
    responses = builder.responses;
    type = builder.type;
    command = builder.command;
    result = builder.result;
    browseMsg = builder.browseMsg;
    browseResult = builder.browseResult;
    endpointInfo = builder.endpointInfo;
  }

  /**
   * get request
   * @return single request
   */
  public EdgeRequest getRequest() {
    return request;
  }

  /**
   * get request list
   * @return multiple request
   */
  public List<EdgeRequest> getRequests() {
    return requests;
  }

  /**
   * get response list
   * @return multiple response
   */
  public List<EdgeResponse> getResponses() {
    return responses;
  }

  /**
   * get browse configuration
   * @return configuration
   */
  public EdgeBrowseParameter getBrowseParameter() {
    return browseMsg;
  }

  /**
   * get browse results
   * @return browse results
   */
  public List<EdgeBrowseResult> getBrowseResults() {
    return browseResult;
  }

  /**
   * get message type it can be found in EdgeMessageType
   * @return message type
   */
  public EdgeMessageType getMessageType() {
    return type;
  }

  /**
   * get message type it can be found in CMD_XXX of EdgeOpcUaCommon
   * @return command string
   */
  public EdgeCommandType getCommand() {
    return command;
  }

  /**
   * get endpointInfo
   * @return endpointInfo
   */
  public EdgeEndpointInfo getEdgeEndpointInfo() {
    return endpointInfo;
  }

  /**
   * get result you can find detailed result as status code in EdgeStatusCode
   * @return result
   */
  public EdgeResult getResult() {
    return result;
  }
}
