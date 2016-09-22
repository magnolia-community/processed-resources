/**
 * This file Copyright (c) 2013-2015 Magnolia International
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
package info.magnolia.module.resources.app;

import static org.junit.Assert.assertEquals;

import info.magnolia.context.MgnlContext;
import info.magnolia.event.EventBus;
import info.magnolia.jcr.util.NodeTypes;
import info.magnolia.jcr.util.NodeTypes.Renderable;
import info.magnolia.module.resources.ResourceTypes;
import info.magnolia.module.resources.app.action.EditResourceAction;
import info.magnolia.module.resources.app.action.EditResourceActionDefinition;
import info.magnolia.test.MgnlTestCase;
import info.magnolia.test.mock.MockWebContext;
import info.magnolia.test.mock.jcr.MockSession;
import info.magnolia.ui.api.location.Location;
import info.magnolia.ui.api.location.LocationController;
import info.magnolia.ui.api.shell.Shell;
import info.magnolia.ui.vaadin.integration.jcr.JcrNodeAdapter;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.Session;

import org.junit.Before;
import org.junit.Test;

public class EditResourceActionTest extends MgnlTestCase {

    private Session session;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        MockWebContext ctx = (MockWebContext) MgnlContext.getInstance();
        session = new MockSession("resources");
        ctx.addSession("resources", session);
    }

    @Test
    public void openSubAppForTemplateName() throws Exception {
        // GIVEN
        MockLocationController locationController = new MockLocationController(null, null);
        Node jcrNode = session.getRootNode().addNode("image", NodeTypes.Content.NAME);
        Renderable.set(jcrNode, ResourceTypes.BINARY);
        JcrNodeAdapter nodeItemToEdit = new JcrNodeAdapter(jcrNode);
        EditResourceActionDefinition definition = new EditResourceActionDefinition();
        Map<String, String> subAppMappings = new HashMap<String,String>();
        subAppMappings.put(ResourceTypes.BINARY_SUFFIX, ResourceTypes.BINARY_SUFFIX);
        definition.setAppName("resources");
        definition.setSubAppMapping(subAppMappings);

        EditResourceAction resourceEditItemAction = new EditResourceAction(definition, nodeItemToEdit, locationController);

        // WHEN
        resourceEditItemAction.execute();

        // THEN
        assertEquals(ResourceTypes.BINARY_SUFFIX, locationController.newLocation.getSubAppId());
    }

    private class MockLocationController extends LocationController {

        public Location newLocation;

        public MockLocationController(EventBus eventBus, Shell shell) {
            super(eventBus, shell);
        }

        @Override
        public void goTo(final Location newLocation) {
            this.newLocation = newLocation;
        }
    }

}
