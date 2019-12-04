# Eclipse RedDeer Testing Framework

Eclipse [RedDeer](http://www.eclipse.org/reddeer) project is an extensible framework used for development of automated SWT/Eclipse tests which interacts with application’s user interface. RedDeer provides the PageObjects API for comfortable testing of standard SWT (Buttons, Trees..), JFace (UIForms), Workbench (Views, Editors, ..) and Eclipse (Wizards, Preferences,...) components and also allows creating and extending your own components. RedDeer also provides capabilities to work with graphical editors based on GEF or Graphiti.

Eclipse RedDeer is extensively tested on Linux/CentOS platform. Examination of test results in CI environment is easier thanks to capturing screenshots on test failures and collecting Eclipse Platform log.

# Get the code

The easiest way to get started with the code is to [create your own fork](https://help.github.com/en/articles/fork-a-repo), 
then clone your fork and finally add upstream:

    $ git clone git@github.com:<you>/reddeer.git
    $ cd reddeer
    $ git remote add upstream http://github.com/eclipse/reddeer.git
    
# Build RedDeer locally

In case that you have the git repo cloned locally, you can build it using maven:

    $ mvn clean install
    
If you just want to build the base and not to run tests, use this:

    $ mvn clean install -DskipTests=true

# Installation

## Using RedDeer eclipse update site

Copy-Paste this URL to Eclipse Help -> Install New Software...:
```
http://download.eclipse.org/reddeer/releases/latest
```
Or latest nightly build:
```
http://download.eclipse.org/reddeer/snapshots
```
Finish the installation & restart IDE.

## Using locally built artifacts

Search your repo for path-to-your-git/reddeer/site/repository and paste this path to Eclipse Help -> Install New Software...

# Getting Started

Go through [Getting Started guide](https://github.com/eclipse/reddeer/wiki/Getting-Started).

# Documentation

See [RedDeer Documentation](https://github.com/eclipse/reddeer/wiki).

# Contributing

See [how to contribute](https://github.com/eclipse/reddeer/blob/master/CONTRIBUTING.md) to the project.

# Contact us

* Chat on [Eclipse Mattermost RedDeer channel](https://mattermost.eclipse.org/eclipse/channels/reddeer)
* Contact us on [Mailing List](https://dev.eclipse.org/mailman/listinfo/reddeer-dev)
* Our [Homepage](http://www.eclipse.org/reddeer)
