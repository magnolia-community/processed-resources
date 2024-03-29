/**
 * This file Copyright (c) 2016-2018 Magnolia International
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

import info.magnolia.module.DefaultModuleVersionHandler;
import info.magnolia.module.delta.ArrayDelegateTask;
import info.magnolia.module.delta.DeltaBuilder;
import info.magnolia.module.delta.NodeExistsDelegateTask;
import info.magnolia.module.delta.RemoveNodeTask;

/**
 * Version handler for Processed resources app module.
 */
public class ProcessedResourcesAppVersionHandler extends DefaultModuleVersionHandler {

    public ProcessedResourcesAppVersionHandler() {
        register(DeltaBuilder.update("1.0.2", "")
                .addTask(new ArrayDelegateTask("Add HasPermissionAvailabilityRule to related actions",
                        new AddIsFolderOrHasTemplateAvailabilityRuleTask("", "", "addResource"),
                        new AddIsFolderOrHasTemplateAvailabilityRuleTask("", "", "editResource"),
                        new AddIsFolderOrHasTemplateAvailabilityRuleTask("", "", "editTemplate"),
                        new AddIsFolderOrHasTemplateAvailabilityRuleTask("", "", "showVersions")))
                .addTask(new NodeExistsDelegateTask("", "/modules/processed-resources-app/apps/processed-resources/subApps/generic/base/editor/form/tabs/content/fields/mgnl-template/options/yaml",
                        new RemoveNodeTask("Remove YAML option from the app",
                                "/modules/processed-resources-app/apps/processed-resources/subApps/generic/base/editor/form/tabs/content/fields/mgnl-template/options/yaml")))
                .addTask(new NodeExistsDelegateTask("", "/modules/processed-resources-app/dialogs/newResource/form/tabs/content/fields/mgnl-template/options/yaml",
                        new RemoveNodeTask("Remove YAML from newResource dialog",
                                "/modules/processed-resources-app/dialogs/newResource/form/tabs/content/fields/mgnl-template/options/yaml")))
        );
    }
}
