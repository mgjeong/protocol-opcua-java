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

package org.edge.protocol.opcua.api.client;

import org.eclipse.milo.opcua.stack.core.types.builtin.DateTime;
import org.edge.protocol.opcua.api.common.EdgeNodeInfo;
import org.edge.protocol.opcua.api.common.EdgeResult;
import org.edge.protocol.opcua.api.common.EdgeVersatility;

public class EdgeResponse {
  private EdgeVersatility value;
  private final EdgeNodeInfo endpoint;
  private EdgeResult result;
  private final int requestId;
  private EdgeDiagnosticInfo diagnosticInfo;
  private DateTime timeStamp;

  public static class Builder {
    private EdgeVersatility value = null;
    private final EdgeNodeInfo endpoint;
    private EdgeResult result = null;
    private final int requestId;
    private EdgeDiagnosticInfo diagnosticInfo = null;
    private DateTime timeStamp = null;

    public Builder(EdgeNodeInfo endpoint, int requestId) {
      this.endpoint = endpoint;
      this.requestId = requestId;
    }

    /**
     * set EdgeVersatility
     * 
     * @param value the response value of the EdgeVersatility type
     * @return this
     */
    public Builder setMessage(EdgeVersatility value) {
      this.value = value;
      return this;
    }

    /**
     * set EdgeResult
     * 
     * @param result result
     * @return this
     */
    public Builder setResult(EdgeResult result) {
      this.result = result;
      return this;
    }

    /**
     * set EdgeDiagnosticInfo
     * 
     * @param info EdgeDiagnosticInfo
     * @return this
     */
    public Builder setDiagnosticInfo(EdgeDiagnosticInfo info) {
      this.diagnosticInfo = info;
      return this;
    }

    /**
     * set DateTime
     * 
     * @param time of response
     * @return this
     */
    public Builder setDateTime(DateTime value) {
      this.timeStamp = value;
      return this;
    }

    /**
     * create EdgeResponse instance (builder)
     * 
     * @return EdgeResponse instance
     */
    public EdgeResponse build() {
      return new EdgeResponse(this);
    }
  }

  /**
   * constructor
   * 
   * @param builder EdgeResponse Builder
   */
  private EdgeResponse(Builder builder) {
    value = builder.value;
    endpoint = builder.endpoint;
    result = builder.result;
    requestId = builder.requestId;
    diagnosticInfo = builder.diagnosticInfo;
    timeStamp = builder.timeStamp;
  }

  /**
   * get message to respond
   * 
   * @return value
   */
  public EdgeVersatility getMessage() {
    return value;
  }

  /**
   * get endpoint
   * 
   * @return endpoint
   */
  public EdgeNodeInfo getEdgeNodeInfo() {
    return endpoint;
  }

  /**
   * get result
   * 
   * @return result
   */
  public EdgeResult getResult() {
    return result;
  }

  /**
   * get request Id
   * 
   * @return requestId
   */
  public int getRequestId() {
    return requestId;
  }

  /**
   * get timestamp of response;
   * 
   * @return requestId
   */
  public DateTime getDateTime() {
    return timeStamp;
  }


  /**
   * get diagnostic Information
   * 
   * @return diagnosticInfo
   */
  public EdgeDiagnosticInfo getEdgeDiagnosticInfo() {
    return diagnosticInfo;
  }
}
