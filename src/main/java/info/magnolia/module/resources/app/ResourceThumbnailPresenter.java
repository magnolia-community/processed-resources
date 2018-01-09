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

import info.magnolia.event.EventBus;
import info.magnolia.objectfactory.ComponentProvider;
import info.magnolia.ui.imageprovider.ImageProvider;
import info.magnolia.ui.vaadin.integration.contentconnector.ContentConnector;
import info.magnolia.ui.vaadin.integration.contentconnector.JcrContentConnector;
import info.magnolia.ui.workbench.AbstractContentPresenter;
import info.magnolia.ui.workbench.ContentView;
import info.magnolia.ui.workbench.definition.WorkbenchDefinition;
import info.magnolia.ui.workbench.thumbnail.ThumbnailContainer;
import info.magnolia.ui.workbench.thumbnail.ThumbnailView;

import java.util.List;

import javax.inject.Inject;

/**
 * The ThumbnailPresenter is responsible for creating, configuring and updating a thumbnail grid of items according to the workbench definition.
 *
 * @deprecated Since Resources 2.4 (Magnolia 5.4), the JCR-based resources app is deprecated.
 * Instead, the new resources-app lists all resources across classpath, file-system and the JCR 'resources' workspace.
 */
@Deprecated
public class ResourceThumbnailPresenter extends AbstractContentPresenter implements ThumbnailView.Listener {

    private final ThumbnailView view;

    private final ImageProvider imageProvider;

    private ThumbnailContainer container;

    @Inject
    public ResourceThumbnailPresenter(final ThumbnailView view, final ImageProvider imageProvider, ComponentProvider componentProvider) {
        super(componentProvider);
        this.view = view;
        this.imageProvider = imageProvider;
    }

    @Override
    public ContentView start(WorkbenchDefinition workbench, EventBus eventBus, String viewTypeName, ContentConnector contentConnector) {
        super.start(workbench, eventBus, viewTypeName, contentConnector);

        container = initializeContainer();

        view.setListener(this);
        view.setContainer(container);
        view.setThumbnailSize(73, 73);

        return view;
    }

    @Override
    protected ThumbnailContainer initializeContainer() {
        container = new ResourceThumbnailContainer(((JcrContentConnector)contentConnector).getContentConnectorDefinition(), imageProvider);
        container.setThumbnailHeight(73);
        container.setThumbnailWidth(73);

        return container;
    }

    @Override
    public void refresh() {
        container.refresh();
        view.refresh();
    }

    @Override
    public void select(List<Object> itemIds) {
        view.select(itemIds);
    }
}
