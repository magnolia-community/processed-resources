# Legacy (processed) resources app info

Since Resources 2.4 (Magnolia 5.4), the JCR-based resources app is deprecated.
This deprecation affects all classes within package **info.magnolia.module.resources.app**.

Instead, the new resources-app lists all resources across classpath, file-system and the JCR 'resources' workspace.
This emphasizes the unified loading cascade, simplified structure of magnolia modules, including e.g. template scripts and configuration files.
The new app resides within package **info.magnolia.resources.app** in magnolia-resources module.

For further information:

* Resources: https://documentation.magnolia-cms.com/display/DOCS/Resources
* Modules: https://documentation.magnolia-cms.com/display/DOCS/Modules
