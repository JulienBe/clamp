/*-
 * ============LICENSE_START=======================================================
 * ONAP CLAMP
 * ================================================================================
 * Copyright (C) 2018 AT&T Intellectual Property. All rights
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
 * ECOMP is a trademark and service mark of AT&T Intellectual Property.
 */

package org.onap.clamp.clds.it.sdc.controller.installer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.att.aft.dme2.internal.apache.commons.io.IOUtils;
import com.att.aft.dme2.internal.apache.commons.lang.RandomStringUtils;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.onap.clamp.clds.dao.CldsDao;
import org.onap.clamp.clds.exception.sdc.controller.CsarHandlerException;
import org.onap.clamp.clds.exception.sdc.controller.SdcArtifactInstallerException;
import org.onap.clamp.clds.model.CldsModel;
import org.onap.clamp.clds.model.CldsTemplate;
import org.onap.clamp.clds.sdc.controller.installer.CsarHandler;
import org.onap.clamp.clds.sdc.controller.installer.CsarInstaller;
import org.onap.clamp.clds.sdc.controller.installer.CsarInstallerImpl;
import org.onap.clamp.clds.util.ResourceFileUtil;
import org.openecomp.sdc.tosca.parser.api.ISdcCsarHelper;
import org.openecomp.sdc.tosca.parser.exceptions.SdcToscaParserException;
import org.openecomp.sdc.toscaparser.api.elements.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CsarInstallerItCase {

    private static final String CSAR_ARTIFACT_NAME = "testArtifact.csar";
    private static final String SERVICE_UUID = "serviceUUID";
    private static final String RESOURCE1_UUID = "resource1UUID";
    @Autowired
    private CsarInstaller csarInstaller;
    @Autowired
    private CldsDao cldsDao;

    @Test(expected = SdcArtifactInstallerException.class)
    public void testInstallTheCsarFail()
            throws SdcArtifactInstallerException, SdcToscaParserException, CsarHandlerException, IOException {
        CsarHandler csarHandler = Mockito.mock(CsarHandler.class);
        Mockito.when(csarHandler.getDcaeBlueprint()).thenReturn(IOUtils
                .toString(ResourceFileUtil.getResourceAsStream("example/sdc/blueprint-dcae/not-recognized.yaml")));
        csarInstaller.installTheCsar(csarHandler);
        fail("Should have raised an SdcArtifactInstallerException");
    }

    private CsarHandler buildFakeCsarHandler(String generatedName) throws IOException {
        CsarHandler csarHandler = Mockito.mock(CsarHandler.class);
        Mockito.when(csarHandler.getDcaeBlueprint())
                .thenReturn(ResourceFileUtil.getResourceAsString("example/sdc/blueprint-dcae/tca.yaml"));
        ISdcCsarHelper csarHelper = Mockito.mock(ISdcCsarHelper.class);
        Metadata data = Mockito.mock(Metadata.class);
        Mockito.when(data.getValue("name")).thenReturn(generatedName);
        Mockito.when(csarHelper.getServiceMetadata()).thenReturn(data);
        Mockito.when(csarHandler.getSdcCsarHelper()).thenReturn(csarHelper);
        Mockito.when(csarHandler.getBlueprintArtifactName()).thenReturn(CSAR_ARTIFACT_NAME);
        Mockito.when(csarHandler.getBlueprintInvariantServiceUuid()).thenReturn(SERVICE_UUID);
        Mockito.when(csarHandler.getBlueprintInvariantResourceUuid()).thenReturn(RESOURCE1_UUID);
        return csarHandler;
    }

    @Test
    public void testIsCsarAlreadyDeployedTca()
            throws SdcArtifactInstallerException, SdcToscaParserException, CsarHandlerException, IOException {
        String generatedName = RandomStringUtils.randomAlphanumeric(5);
        CsarHandler csarHandler = buildFakeCsarHandler(generatedName);
        csarInstaller.installTheCsar(csarHandler);
        assertTrue(csarInstaller.isCsarAlreadyDeployed(csarHandler));
    }

    @Test
    public void testInstallTheCsarTca()
            throws SdcArtifactInstallerException, SdcToscaParserException, CsarHandlerException, IOException {
        String generatedName = RandomStringUtils.randomAlphanumeric(5);
        csarInstaller.installTheCsar(buildFakeCsarHandler(generatedName));
        // Get the template back from DB
        CldsTemplate templateFromDB = CldsTemplate.retrieve(cldsDao,
                CsarInstallerImpl.TEMPLATE_NAME_PREFIX + generatedName, false);
        assertNotNull(templateFromDB);
        assertNotNull(templateFromDB.getBpmnText());
        assertNotNull(templateFromDB.getImageText());
        assertNotNull(templateFromDB.getPropText());
        assertTrue(templateFromDB.getPropText().contains("global")
                && templateFromDB.getPropText().contains("node_templates:"));
        assertEquals(templateFromDB.getName(), CsarInstallerImpl.TEMPLATE_NAME_PREFIX + generatedName);
        // Get the Model back from DB
        CldsModel modelFromDB = CldsModel.retrieve(cldsDao, generatedName, true);
        assertNotNull(modelFromDB);
        assertNotNull(modelFromDB.getBpmnText());
        assertNotNull(modelFromDB.getImageText());
        assertNotNull(modelFromDB.getPropText());
        assertEquals(modelFromDB.getName(), generatedName);
        assertEquals(CsarInstallerImpl.MODEL_NAME_PREFIX, modelFromDB.getControlNamePrefix());
    }
}
