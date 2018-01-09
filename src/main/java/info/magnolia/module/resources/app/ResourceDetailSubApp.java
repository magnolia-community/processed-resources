/**
 * This file Copyright (c) 2013-2018 Magnolia International
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

import info.magnolia.cms.core.version.VersionManager;
import info.magnolia.context.MgnlContext;
import info.magnolia.event.EventBus;
import info.magnolia.i18nsystem.SimpleTranslator;
import info.magnolia.ui.api.app.SubAppContext;
import info.magnolia.ui.api.event.AdmincentralEventBus;
import info.magnolia.ui.contentapp.ContentSubAppView;
import info.magnolia.ui.contentapp.detail.DetailEditorPresenter;
import info.magnolia.ui.contentapp.detail.DetailLocation;
import info.magnolia.ui.contentapp.detail.DetailSubApp;
import info.magnolia.ui.vaadin.integration.contentconnector.ContentConnector;
import info.magnolia.ui.vaadin.integration.contentconnector.JcrContentConnector;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Resource detail sub-app - just sets the proper caption.
 *
 * @deprecated Since Resources 2.4 (Magnolia 5.4), the JCR-based resources app is deprecated.
 * Instead, the new resources-app lists all resources across classpath, file-system and the JCR 'resources' workspace.
 */
@Deprecated
public class ResourceDetailSubApp extends DetailSubApp {

    private static final Logger log = LoggerFactory.getLogger(ResourceDetailSubApp.class);

    private final VersionManager versionManager;

    private JcrContentConnector contentConnector;

    @Inject
    protected ResourceDetailSubApp(final SubAppContext subAppContext, final ContentSubAppView view, @Named(AdmincentralEventBus.NAME) EventBus adminCentralEventBus,
            DetailEditorPresenter workbench, VersionManager versionManager, SimpleTranslator i18n, ContentConnector contentConnector) {
        super(subAppContext, view, adminCentralEventBus, workbench, i18n, contentConnector);
        this.versionManager = versionManager;
        this.contentConnector = (JcrContentConnector) contentConnector;
    }

    @Override
    protected String getBaseCaption(DetailLocation location) {
        String baseCaption = super.getBaseCaption(location);
        try {
            String workspace = contentConnector.getContentConnectorDefinition().getWorkspace();
            if (MgnlContext.getJCRSession(workspace).nodeExists(location.getNodePath())) {

                Node node = MgnlContext.getJCRSession(workspace).getNode(location.getNodePath());
                // Get specific Node version if needed
                if (StringUtils.isNotBlank(location.getVersion())) {
                    node = versionManager.getVersion(node, location.getVersion());
                }
                baseCaption = node.getName() + "/" + location.getVersion();
            } else {
                baseCaption = "resourcesApp.createNew.label";
            }
        } catch (RepositoryException e) {
            log.warn("Could not set Sub App Tab Caption for item : {}", location.getNodePath(), e);
        }
        return baseCaption;
    }
}
