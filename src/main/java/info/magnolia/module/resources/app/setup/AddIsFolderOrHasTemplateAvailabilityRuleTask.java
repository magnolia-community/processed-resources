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

import info.magnolia.jcr.util.NodeTypes;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.module.InstallContext;
import info.magnolia.module.delta.AbstractRepositoryTask;
import info.magnolia.module.delta.TaskExecutionException;
import info.magnolia.repository.RepositoryConstants;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

/**
 * Adds {@link info.magnolia.module.resources.app.availability.IsFolderOrHasTemplateRule} to given {@code #actionName}.
 */
public class AddIsFolderOrHasTemplateAvailabilityRuleTask extends AbstractRepositoryTask {

    private final String actionName;

    public AddIsFolderOrHasTemplateAvailabilityRuleTask(String name, String description, String actionName) {
        super(name, description);
        this.actionName = actionName;
    }

    @Override
    protected void doExecute(InstallContext installContext) throws RepositoryException, TaskExecutionException {
        Session session = installContext.getJCRSession(RepositoryConstants.CONFIG);
        Node rules = NodeUtil.createPath(session.getRootNode(),
                "/modules/processed-resources-app/apps/processed-resources/subApps/browser/actions/" + actionName + "/availability/rules", NodeTypes.ContentNode.NAME);
        if (rules.hasNode("IsFolderOrHasTemplateRule")) {
            return;
        }
        Node isFolderOrHasTemplateRuleNode = rules.addNode("IsFolderOrHasTemplateRule", NodeTypes.ContentNode.NAME);
        isFolderOrHasTemplateRuleNode.setProperty("implementationClass", "info.magnolia.module.resources.app.availability.IsFolderOrHasTemplateRule");
    }
}
