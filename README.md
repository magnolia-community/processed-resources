# Processed resources

[![Build Status](https://travis-ci.com/magnolia-community/processed-resources.svg?branch=master)](https://travis-ci.com/magnolia-community/processed-resources) [![Magnolia compatibility](https://img.shields.io/badge/magnolia-5.6-brightgreen.svg)](https://www.magnolia-cms.com)

This module lets you store and maintain resources in JCR in the old, pre-Magnolia 5.4 way, which enables you to run templating processing on CSS,JS or other text-based resources, typically with freemarker (FTL).

> For new projects, Magnolia encourages you to rather use the new resources system - but we provide this module as an assistance for maintaining existing projects.

This resource system can co-exist in parallel with the new Resource Cascade system introduced in 5.4 with light development, where resources can be provided by file as well as in JCR. But the two resource systems do not interact.

An overview of the two resource systems:

| Name                         | Availability in Bundles   | Access URL  | Features
| -----------------------------|:-------------------------:|:-----------:|-------------------------
| Resource Cascade             | 5.4 and greater           | /.resources | Supply resoures via classpath, file, or JCR
| Processed Resources (legacy) | 5.3 and earlier           | /resources  | Template processing of resources

## Usage

This module provides and installs a new 'Processed Resources' app which allows you to view and optionally edit resource files.
For full documentation on this feature, please consult the Magnolia 5.3 Resources documentation: https://documentation.magnolia-cms.com/display/DOCS53/Resources

Processed Resources App
![Processed Resources App](https://wiki.magnolia-cms.com/download/attachments/176799623/proc-rec-app-browser.png?version=1&modificationDate=1553510490000&api=v2)


Processed Resources App - Editor
![Processed Resources App - Editor](https://wiki.magnolia-cms.com/download/attachments/176799623/proc-rec-app-editor.png?version=1&modificationDate=1553510508000&api=v2)


Example of a resource that has been processed
![Processed Image](https://wiki.magnolia-cms.com/download/attachments/176799623/proc-rec-rendered.png?version=1&modificationDate=1553510533000&api=v2)



## Installation

Add the following dependency to your Maven project:

    <dependency>
      <groupId>info.magnolia.resources</groupId>
      <artifactId>magnolia-processed-resources-app</artifactId>
      <version>1.1</version>
    </dependency>

## Configuration

This module installs itself, and adds itself to the App Launcher Layout.
No configuration is required.

## Versions and compatibility table

### Compatiblity

| Module Version | Magnolia Bundle Version   
| ---------------|:-------------------------
| 1.1.x          | 5.6.x         

### History

* Version 1.1: first public release for Magnolia 5.6.x

## Contributions, Questions, Bugs

Please report bugs, feature requests, or pose questions using the Github Issues feature.

For any contributions, please submit a pull request.

## License

This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

GPL v3 (https://www.gnu.org/licenses/)

MNA (http://www.magnolia-cms.com/mna.html)
