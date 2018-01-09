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
package info.magnolia.module.resources.app.action;

import info.magnolia.i18nsystem.SimpleTranslator;
import info.magnolia.jcr.util.NodeTypes.Renderable;
import info.magnolia.ui.api.action.ActionExecutionException;
import info.magnolia.ui.api.app.AppContext;
import info.magnolia.ui.api.context.UiContext;
import info.magnolia.ui.api.location.Location;
import info.magnolia.ui.api.location.LocationController;
import info.magnolia.ui.contentapp.browser.action.ShowVersionsAction;
import info.magnolia.ui.contentapp.detail.DetailLocation;
import info.magnolia.ui.contentapp.detail.DetailView;
import info.magnolia.ui.dialog.formdialog.FormDialogPresenter;
import info.magnolia.ui.vaadin.integration.jcr.AbstractJcrNodeAdapter;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;

/**
 * Opens a dialog with list of versions.
 *
 * @see ShowResourceVersionsActionDefinition
 * @deprecated Since Resources 2.4 (Magnolia 5.4), the JCR-based resources app is deprecated.
 * Instead, the new resources-app lists all resources across classpath, file-system and the JCR 'resources' workspace.
 */
@Deprecated
public class ShowResourceVersionsAction extends ShowVersionsAction<ShowResourceVersionsActionDefinition> {

    private final AppContext appContext;

    @Inject
    public ShowResourceVersionsAction(ShowResourceVersionsActionDefinition definition, AppContext appContext, LocationController locationController, UiContext uiContext,
                                      FormDialogPresenter formDialogPresenter, AbstractJcrNodeAdapter nodeAdapter, SimpleTranslator i18n) {
        super(definition, appContext, locationController, uiContext, formDialogPresenter, nodeAdapter, i18n);
        this.appContext = appContext;
    }

    @Override
    protected Location getLocation() throws ActionExecutionException {
        try {
            final Node node = getNode();
            final String path = node.getPath();
            final String appName = appContext.getName();
            final String template = StringUtils.split(Renderable.getTemplate(nodeAdapter.getJcrItem()), ":")[1];
            final String subAppId = getDefinition().getSubAppMapping().get(template);

            return new DetailLocation(appName, subAppId, DetailView.ViewType.VIEW, path, (String) getItem().getItemProperty("versionName").getValue());
        } catch (RepositoryException e) {
            throw new ActionExecutionException("Could not get target location for '{}'" + nodeAdapter.getItemId());
        }
    }

}
