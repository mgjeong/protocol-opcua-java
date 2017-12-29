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

package org.edge.protocol.opcua.providers.services.sub;

import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscription;
import org.edge.protocol.opcua.api.client.EdgeSubRequest;
import static com.google.common.collect.Lists.newArrayList;

public class EdgeSubscription {
  private EdgeSubRequest subReq;
  private UaSubscription uaSubscription;

  public static class Builder {
    private EdgeSubRequest subReq = null;
    private final UaSubscription uaSubscription;

    /**
     * constructor of Builder class
     * @param  UaSubscription uaSubscription
     */
    public Builder(UaSubscription uaSubscription) {
      this.uaSubscription = uaSubscription;
    }

    /**
     * set subscription request to builder instance
     * @param  subReq request of subscription
     * @return Builder
     */
    public Builder setSubRequest(EdgeSubRequest subReq) {
      this.subReq = subReq;
      return this;
    }

    /**
     * request build to get a subscription
     * @return EdgeSubscription
     */
    public EdgeSubscription build() {
      return new EdgeSubscription(this);
    }
  }

  /**
   * constructor of EdgeSubscription class
   */
  private EdgeSubscription(Builder builder) {
    uaSubscription = builder.uaSubscription;
    subReq = builder.subReq;
  }

  /**
   * get uaSubscription
   * @return UaSubscription
   */
  public UaSubscription getUaSubscription() {
    return uaSubscription;
  }

  /**
   * get subscription request
   * @return EdgeSubRequest
   */
  public EdgeSubRequest getSubRequest() {
    return subReq;
  }
}
