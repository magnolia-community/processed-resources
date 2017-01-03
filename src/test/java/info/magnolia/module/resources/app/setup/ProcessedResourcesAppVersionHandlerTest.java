/**
 * This file Copyright (c) 2016-2017 Magnolia International
 * Ltd.  (http://www.magnolia-cms.com). All rights reserved.
 *
 *
 * This file is dual-licensed under both the Magnolia
 * Network Agreement and the GNU General Public License.
 * You may elect to use one or the other of these licenses.
 *
 * This file is distributed in the hope that it will be
 * useful, but AS-IS and WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE, TITLE, or NONINFRINGEMENT.
 * Redistribution, except as permitted by whichever of the GPL
 * or MNA you select, is prohibited.
 *
 * 1. For the GPL license (GPL), you can redistribute and/or
 * modify this file under the terms of the GNU General
 * Public License, Version 3, as published by the Free Software
 * Foundation.  You should have received a copy of the GNU
 * General Public License, Version 3 along with this program;
 * if not, write to the Free Software Foundation, Inc., 51
 * Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * 2. For the Magnolia Network Agreement (MNA), this file
 * and the accompanying materials are made available under the
 * terms of the MNA which accompanies this distribution, and
 * is available at http://www.magnolia-cms.com/mna.html
 *
 * Any modifications to this file must keep this entire header
 * intact.
 *
 */
package info.magnolia.module.resources.app.setup;

import static info.magnolia.test.hamcrest.NodeMatchers.hasNode;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import info.magnolia.context.MgnlContext;
import info.magnolia.jcr.util.NodeTypes;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.module.ModuleVersionHandler;
import info.magnolia.module.ModuleVersionHandlerTestCase;
import info.magnolia.module.model.Version;
import info.magnolia.repository.RepositoryConstants;

import java.util.List;

import javax.jcr.Node;
import javax.jcr.Session;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

public class ProcessedResourcesAppVersionHandlerTest extends ModuleVersionHandlerTestCase {

    private Session configSession;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        configSession = MgnlContext.getJCRSession(RepositoryConstants.CONFIG);
    }

    @Override
    protected String getModuleDescriptorPath() {
        return "/META-INF/magnolia/processed-resources-app.xml";
    }

    @Override
    protected List<String> getModuleDescriptorPathsForTests() {
        return Lists.newArrayList("/META-INF/magnolia/core.xml");
    }

    @Override
    protected ModuleVersionHandler newModuleVersionHandlerForTests() {
        return new ProcessedResourcesAppVersionHandler();
    }

    @Test
    public void cleanInstallBootstrapActionsWithHasPermissionRule() throws Exception {
        // GIVEN
        // WHEN
        executeUpdatesAsIfTheCurrentlyInstalledVersionWas(null);
        Node actions = configSession.getNode("/modules/processed-resources-app/apps/processed-resources/subApps/browser/actions");
        Node options = configSession.getNode("/modules/processed-resources-app/dialogs/newResource/form/tabs/content/fields/mgnl-template/options");

        // THEN
        assertThat(actions, hasNode("addResource/availability/rules/IsFolderOrHasTemplateRule"));
        assertThat(actions, hasNode("editResource/availability/rules/IsFolderOrHasTemplateRule"));
        assertThat(actions, hasNode("editTemplate/availability/rules/IsFolderOrHasTemplateRule"));
        assertThat(actions, hasNode("showVersions/availability/rules/IsFolderOrHasTemplateRule"));
        assertThat(options, not(hasNode("yaml")));
    }

    @Test
    public void updateFrom101UpdatesActionsAvailabilityRulesWithHasPermissionRule() throws Exception {
        // GIVEN
        Node options = NodeUtil.createPath(configSession.getRootNode(), "/modules/processed-resources-app/dialogs/newResource/form/tabs/content/fields/mgnl-template/options", NodeTypes.ContentNode.NAME, true);
        options.addNode("yaml", NodeTypes.ContentNode.NAME);
        options.save();

        // WHEN
        executeUpdatesAsIfTheCurrentlyInstalledVersionWas(Version.parseVersion("1.0.1"));
        Node actions = configSession.getNode("/modules/processed-resources-app/apps/processed-resources/subApps/browser/actions");

        // THEN
        assertThat(actions, hasNode("addResource/availability/rules/IsFolderOrHasTemplateRule"));
        assertThat(actions, hasNode("editResource/availability/rules/IsFolderOrHasTemplateRule"));
        assertThat(actions, hasNode("editTemplate/availability/rules/IsFolderOrHasTemplateRule"));
        assertThat(actions, hasNode("showVersions/availability/rules/IsFolderOrHasTemplateRule"));
        assertThat(options, not(hasNode("yaml")));
    }
}
