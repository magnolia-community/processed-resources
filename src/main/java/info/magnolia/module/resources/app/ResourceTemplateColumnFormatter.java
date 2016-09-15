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

import info.magnolia.i18nsystem.SimpleTranslator;
import info.magnolia.jcr.util.NodeTypes;
import info.magnolia.jcr.util.NodeTypes.Renderable;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.module.resources.ResourceTypes;
import info.magnolia.ui.workbench.column.AbstractColumnFormatter;

import javax.inject.Inject;
import javax.jcr.Item;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.ui.Table;

/**
 * Column formatter that displays either the type of resource or a folder name.
 *
 * @see ResourceColumnDefinition
 * @deprecated Since Resources 2.4 (Magnolia 5.4), the JCR-based resources app is deprecated.
 * Instead, the new resources-app lists all resources across classpath, file-system and the JCR 'resources' workspace.
 */
@Deprecated
public class ResourceTemplateColumnFormatter extends AbstractColumnFormatter<ResourceColumnDefinition> {

    private static final long serialVersionUID = 4761675760647565415L;
    private static final Logger log = LoggerFactory.getLogger(ResourceTemplateColumnFormatter.class);
    private SimpleTranslator simpleTranslator;

    @Inject
    public ResourceTemplateColumnFormatter(ResourceColumnDefinition definition, SimpleTranslator simpleTranslator) {
        super(definition);
        this.simpleTranslator = simpleTranslator;
    }

    /**
     * Uses an i18n key to get the label for a given resource template. I.e. given a template <code>resources:foo</code> it will look up for a key in the
     * module's i18n properties file named <code>resources.content.mgnl-template.options.foo</code>.
     */
    @Override
    public Object generateCell(Table source, Object itemId, Object columnId) {
        final Item jcrItem = getJcrItem(source, itemId);
        if (jcrItem != null && jcrItem.isNode()) {
            Node node = (Node) jcrItem;
            try {
                if (NodeUtil.isNodeType(node, NodeTypes.Content.NAME)) {
                    String resourceType = StringUtils.substringAfter(Renderable.getTemplate(node), ResourceTypes.RESOURCES_PREFIX);
                    if (NodeUtil.hasMixin(node, NodeTypes.Deleted.NAME)) {
                        return simpleTranslator.translate("processed-resources.content.mgnl-template.options.deleted");
                    }
                    if (resourceType == null) {
                        return simpleTranslator.translate("processed-resources.content.mgnl-template.options.noTemplate");
                    }
                    if (ResourceTypes.BINARY_SUFFIX.equals(resourceType) && node.hasNode(ResourceTypes.BINARY_SUFFIX) && node.getNode(ResourceTypes.BINARY_SUFFIX).hasProperty("extension")) {
                        return simpleTranslator.translate("processed-resources.content.mgnl-template.options.binary.extension", node.getNode(ResourceTypes.BINARY_SUFFIX).getProperty("extension").getString());
                    }
                    return simpleTranslator.translate("processed-resources.content.mgnl-template.options." + resourceType);
                }
            } catch (RepositoryException e) {
                log.warn("Unable to get name of resource template for column", e);
            }
        }
        return "";
    }
}
