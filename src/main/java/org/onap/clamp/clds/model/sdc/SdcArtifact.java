/*-
 * ============LICENSE_START=======================================================
 * ONAP CLAMP
 * ================================================================================
 * Copyright (C) 2017-2018 AT&T Intellectual Property. All rights
 *                             reserved.
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END============================================
 * ===================================================================
 * 
 */

package org.onap.clamp.clds.model.sdc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SdcArtifact {

    private String artifactName;
    private String artifactType;
    private String artifactUrl;
    private String artifactDescription;
    private String artifactTimeout;
    private String artifactChecksum;
    private String artifactUuid;
    private String artifactVersion;
    private String generatedFromUuid;

    public String getArtifactName() {
        return artifactName;
    }

    public void setArtifactName(String artifactName) {
        this.artifactName = artifactName;
    }

    public String getArtifactType() {
        return artifactType;
    }

    public void setArtifactType(String artifactType) {
        this.artifactType = artifactType;
    }

    public String getArtifactURL() {
        return artifactUrl;
    }

    public void setArtifactURL(String artifactURL) {
        this.artifactUrl = artifactURL;
    }

    public String getArtifactDescription() {
        return artifactDescription;
    }

    public void setArtifactDescription(String artifactDescription) {
        this.artifactDescription = artifactDescription;
    }

    public String getArtifactTimeout() {
        return artifactTimeout;
    }

    public void setArtifactTimeout(String artifactTimeout) {
        this.artifactTimeout = artifactTimeout;
    }

    public String getArtifactChecksum() {
        return artifactChecksum;
    }

    public void setArtifactChecksum(String artifactChecksum) {
        this.artifactChecksum = artifactChecksum;
    }

    public String getArtifactUUID() {
        return artifactUuid;
    }

    public void setArtifactUUID(String artifactUUID) {
        this.artifactUuid = artifactUUID;
    }

    public String getArtifactVersion() {
        return artifactVersion;
    }

    public void setArtifactVersion(String artifactVersion) {
        this.artifactVersion = artifactVersion;
    }

    public String getGeneratedFromUUID() {
        return generatedFromUuid;
    }

    public void setGeneratedFromUUID(String generatedFromUUID) {
        this.generatedFromUuid = generatedFromUUID;
    }
}
