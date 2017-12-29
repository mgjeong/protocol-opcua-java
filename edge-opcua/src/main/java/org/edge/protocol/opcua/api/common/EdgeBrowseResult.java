/******************************************************************
 *
 * Copyright 2017 Samsung Electronics All Rights Reserved.
 *
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************/

package org.edge.protocol.opcua.api.common;

public class EdgeBrowseResult {
  String browseName;
  
  public static class Builder {
    String browseName = null;
    
    public Builder() {}

    /**
     * set browse name
     * @param  browseName browse name
     * @return this
     */
    public Builder setBrowseName(String browseName) {
      this.browseName = browseName;
      return this;
    }

    /**
     * create EdgeBrowseResult instance (builder)
     * @return EdgeBrowseResult instance
     */
    public EdgeBrowseResult build() {
      return new EdgeBrowseResult(this);
    }
  }

  /**
   * constructor
   * @param  builder EdgeBrowseResult Builder
   */
  private EdgeBrowseResult(Builder builder) {
    browseName = builder.browseName;
  }
  
  /**
   * get Browse Name
   * @return browseName
   */
  public String getBrowseName() {
    return browseName;
  }
}
