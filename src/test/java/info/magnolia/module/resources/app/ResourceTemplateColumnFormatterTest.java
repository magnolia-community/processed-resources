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
import static org.mockito.Mockito.*;

import info.magnolia.context.MgnlContext;
import info.magnolia.i18nsystem.SimpleTranslator;
import info.magnolia.jcr.util.NodeTypes;
import info.magnolia.jcr.util.NodeTypes.Renderable;
import info.magnolia.module.resources.ResourceTypes;
import info.magnolia.test.MgnlTestCase;
import info.magnolia.test.mock.MockWebContext;
import info.magnolia.test.mock.jcr.MockSession;
import info.magnolia.ui.vaadin.integration.jcr.JcrNodeAdapter;

import javax.jcr.Node;
import javax.jcr.Session;

import org.junit.Before;
import org.junit.Test;

import com.vaadin.data.Item;
import com.vaadin.ui.Table;

public class ResourceTemplateColumnFormatterTest extends MgnlTestCase {

    private Session session;
    private SimpleTranslator simpleTranslator;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        MockWebContext ctx = (MockWebContext) MgnlContext.getInstance();
        session = new MockSession("resources");
        ctx.addSession("resources", session);
        simpleTranslator = mock(SimpleTranslator.class);
    }

    @Test
    public void testGenerateCell() throws Exception {
        // GIVEN
        ResourceTemplateColumnFormatter resourceNameColumnFormatter = new ResourceTemplateColumnFormatter(new ResourceColumnDefinition(), simpleTranslator);
        Table table = mock(Table.class);
        Node jcrNode = session.getRootNode().addNode("foo", NodeTypes.Content.NAME);
        Renderable.set(jcrNode, "resources:bar");

        String expectedValue = "Bar";
        when(simpleTranslator.translate("resources.content.mgnl-template.options.bar")).thenReturn(expectedValue);

        Item item = new JcrNodeAdapter(jcrNode);
        when(table.getItem("itemId")).thenReturn(item);

        // WHEN
        String name = (String) resourceNameColumnFormatter.generateCell(table, "itemId", "columnId");

        // THEN
        assertEquals(expectedValue, name);
    }

    @Test
    public void testGenerateCellForBinaryResource() throws Exception {
        // GIVEN
        ResourceTemplateColumnFormatter resourceNameColumnFormatter = new ResourceTemplateColumnFormatter(new ResourceColumnDefinition(), simpleTranslator);
        Table table = mock(Table.class);
        Node jcrNode = session.getRootNode().addNode("image", NodeTypes.Content.NAME);
        Renderable.set(jcrNode, "resources:binary");
        jcrNode.addNode(ResourceTypes.BINARY_SUFFIX).setProperty("extension", "png");

        String expectedValue = "Binary/png";
        when(simpleTranslator.translate("resources.content.mgnl-template.options.binary.extension", "png")).thenReturn(expectedValue);

        Item item = new JcrNodeAdapter(jcrNode);
        when(table.getItem("itemId")).thenReturn(item);

        // WHEN
        String name = (String) resourceNameColumnFormatter.generateCell(table, "itemId", "columnId");

        // THEN
        assertEquals(expectedValue, name);
    }

    @Test
    public void testGenerateCellDeleted() throws Exception {
        // GIVEN
        ResourceTemplateColumnFormatter resourceNameColumnFormatter = new ResourceTemplateColumnFormatter(new ResourceColumnDefinition(), simpleTranslator);
        Table table = mock(Table.class);
        Node jcrNode = session.getRootNode().addNode("foo", NodeTypes.Content.NAME);
        jcrNode.addMixin("mgnl:deleted");

        Item item = new JcrNodeAdapter(jcrNode);
        when(table.getItem("itemId")).thenReturn(item);

        // WHEN
        resourceNameColumnFormatter.generateCell(table, "itemId", "columnId");

        // THEN
        verify(simpleTranslator).translate("resources.content.mgnl-template.options.deleted");
    }

}
