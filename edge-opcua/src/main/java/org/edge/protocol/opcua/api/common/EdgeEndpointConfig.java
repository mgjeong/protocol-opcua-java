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

public class EdgeEndpointConfig {
  private int requestTimeout;
  private String applicationName;
  private String applicationUri;
  private String productUri;
  private String securityPolicyUri;
  private String serverName;
  private String bindAddress;
  private int bindPort;
  private boolean viewNodeFlag;
  private int mode;

  public static class Builder {
    private int requestTimeout = 60000;
    private int mode = EdgeOpcUaCommon.BOTH_MODE;
    private String applicationName = EdgeOpcUaCommon.DEFAULT_SERVER_APP_NAME.getValue();
    private String applicationUri = EdgeOpcUaCommon.DEFAULT_SERVER_APP_URI.getValue();
    private String productUri = EdgeOpcUaCommon.DEFAULT_PRODUCT_URI.getValue();
    private String securityPolicyUri = null;
    private String serverName = EdgeOpcUaCommon.DEFAULT_SERVER_NAME.getValue();
    private String bindAddress = EdgeOpcUaCommon.WELL_KNOWN_LOCALHOST_ADDRESS.getValue();
    private int bindPort = 12686;
    private boolean viewNodeFlag = false;

    public Builder() {}

    /**
     * set application name
     *
     * @param  val application name
     * @return this
     */
    public Builder setApplicationName(String val) {
      applicationName = val;
      return this;
    }

    /**
     * set application uri
     *
     * @param  val application uri
     * @return this
     */
    public Builder setApplicationUri(String val) {
      applicationUri = val;
      return this;
    }

    /**
     * set product uri
     *
     * @param  val product uri
     * @return this
     */
    public Builder setProductUri(String val) {
      productUri = val;
      return this;
    }

    /**
     * set security policy uri
     *
     * @param  val security policy uri
     * @return this
     */
    public Builder setSecurityPolicyUri(String val) {
      securityPolicyUri = val;
      return this;
    }

    /**
     * set Server Name
     *
     * @param  val server Name
     * @return this
     */
    public Builder setServerName(String val) {
      serverName = val;
      return this;
    }

    /**
     * set bind address
     *
     * @param  addr address
     * @return this
     */
    public Builder setbindAddress(String addr) {
      bindAddress = addr;
      return this;
    }

    /**
     * set bind port
     *
     * @param  port port
     * @return this
     */
    public Builder setbindPort(int port) {
      bindPort = port;
      return this;
    }

    /**
     * set configure which initialize provider with only view node
     *
     * @param  flag View Node flag is set
     * @return this
     */
    public Builder setViewNodeFlag(boolean flag) {
      viewNodeFlag = flag;
      return this;
    }

    /**
     * set configure which initialize mode(server/client/both)
     *
     * @param  mode of stack
     * @return this
     */
    public Builder setMode(int mode) {
      this.mode = mode;
      return this;
    }

    /**
     * create EdgeEndpointConfig instance (builder)
     *
     * @return EdgeEndpointConfig
     */
    public EdgeEndpointConfig build() {
      return new EdgeEndpointConfig(this);
    }
  }

  /**
   * constructor
   */
  private EdgeEndpointConfig(Builder builder) {
    requestTimeout = builder.requestTimeout;
    applicationName = builder.applicationName;
    applicationUri = builder.applicationUri;
    productUri = builder.productUri;
    securityPolicyUri = builder.securityPolicyUri;
    serverName = builder.serverName;
    bindAddress = builder.bindAddress;
    bindPort = builder.bindPort;
    viewNodeFlag = builder.viewNodeFlag;
    mode = builder.mode;
  }

  /**
   * get request time-out
   *
   * @return time-out
   */
  public int getRequestTimeout() {
    return requestTimeout;
  }

  /**
   * get Application name
   *
   * @return application name
   */
  public String getApplicationName() {
    return applicationName;
  }

  /**
   * get Application uri
   *
   * @return application uri
   */
  public String getApplicationUri() {
    return applicationUri;
  }

  /**
   * get product uri
   *
   * @return productUri
   */
  public String getProductUri() {
    return productUri;
  }

  /**
   * get Security Policy Uri
   *
   * @return Security Policy Uri
   */
  public String getSecurityPolicyUri() {
    return securityPolicyUri;
  }

  /**
   * get Server Name
   *
   * @return serverName
   */
  public String getServerName() {
    return serverName;
  }

  /**
   * get bindaddress
   *
   * @return bind address
   */
  public String getBindAddress() {
    return bindAddress;
  }

  /**
   * get bindPort
   *
   * @return bindPort
   */
  public int getBindPort() {
    return bindPort;
  }

  /**
   * get viweNodeFlag
   *
   * @return viweNodeFlag
   */
  public boolean getViewNodeFlag() {
    return viewNodeFlag;
  }

  /**
   * get Mode of stack
   *
   * @return mode
   */
  public int getMode() {
    return mode;
  }
}
