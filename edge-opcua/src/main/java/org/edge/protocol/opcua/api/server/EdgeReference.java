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

package org.edge.protocol.opcua.api.server;

import org.edge.protocol.opcua.api.common.EdgeNodeIdentifier;

public class EdgeReference {
  private String sourcePath;
  private String sourceNamespace;
  private String targetPath;
  private String targetNamespace;
  private EdgeNodeIdentifier referenceId = EdgeNodeIdentifier.Organizes;
  private boolean forward = true;

  public static class Builder {
    private final String sourcePath;
    private final String sourceNamespace;
    private final String targetPath;
    private final String targetNamespace;
    private EdgeNodeIdentifier referenceId = EdgeNodeIdentifier.Organizes;
    private boolean forward = true;

    public Builder(String sourcePath, String sourceNamespace, String targetPath,
        String targetNamespace) {
      this.sourcePath = sourcePath;
      this.sourceNamespace = sourceNamespace;
      this.targetPath = targetPath;
      this.targetNamespace = targetNamespace;
    }

    public Builder setReferenceId(EdgeNodeIdentifier referenceId) {
      this.referenceId = referenceId;
      return this;
    }

    public Builder setForward(boolean forward) {
      this.forward = forward;
      return this;
    }

    public EdgeReference build() {
      return new EdgeReference(this);
    }
  }

  private EdgeReference(Builder builder) {
    sourcePath = builder.sourcePath;
    sourceNamespace = builder.sourceNamespace;
    targetPath = builder.targetPath;
    targetNamespace = builder.targetNamespace;
    referenceId = builder.referenceId;
    forward = builder.forward;
  }

  public String getSourcePath() {
    return sourcePath;
  }

  public String getSourceNamespace() {
    return sourceNamespace;
  }

  public String getTargetPath() {
    return targetPath;
  }

  public String getTargetNamespace() {
    return targetNamespace;
  }

  public EdgeNodeIdentifier getReference() {
    return referenceId;
  }

  public boolean getForward() {
    return forward;
  }
}
