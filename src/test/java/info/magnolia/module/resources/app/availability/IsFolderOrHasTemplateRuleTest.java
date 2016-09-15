/**
 * This file Copyright (c) 2016 Magnolia International
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
package info.magnolia.module.resources.app.availability;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import info.magnolia.context.MgnlContext;
import info.magnolia.jcr.util.NodeTypes;
import info.magnolia.repository.RepositoryConstants;
import info.magnolia.test.RepositoryTestCase;
import info.magnolia.ui.vaadin.integration.jcr.JcrItemUtil;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.junit.Before;
import org.junit.Test;

public class IsFolderOrHasTemplateRuleTest extends RepositoryTestCase {

    private IsFolderOrHasTemplateRule rule;
    private Session session;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        rule = new IsFolderOrHasTemplateRule();
        session = MgnlContext.getJCRSession(RepositoryConstants.WEBSITE);
    }

    @Test
    public void availableWhenNodeHasTemplateProperty() throws RepositoryException {
        // GIVEN
        Node node = session.getRootNode().addNode("foo", NodeTypes.Resource.NAME);
        node.setProperty(NodeTypes.Renderable.TEMPLATE, "css");

        // WHEN
        Object itemId = JcrItemUtil.getItemId(node);
        boolean isAvailable = rule.isAvailableForItem(itemId);

        // THEN
        assertThat(isAvailable, is(true));
    }

    @Test
    public void availableWhenNodeIsAFolder() throws RepositoryException {
        // GIVEN
        Node node = session.getRootNode().addNode("foo", NodeTypes.Folder.NAME);

        // WHEN
        Object itemId = JcrItemUtil.getItemId(node);
        boolean isAvailable = rule.isAvailableForItem(itemId);

        // THEN
        assertThat(isAvailable, is(true));
    }

    @Test
    public void notAvailableForOthers() throws RepositoryException {
        // GIVEN
        Node node = session.getRootNode().addNode("foo", NodeTypes.Resource.NAME);

        // WHEN
        Object itemId = JcrItemUtil.getItemId(node);
        boolean isAvailable = rule.isAvailableForItem(itemId);

        // THEN
        assertThat(isAvailable, is(false));
    }

}
