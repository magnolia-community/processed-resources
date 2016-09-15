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
