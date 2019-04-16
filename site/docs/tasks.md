# Tasks

## Creating gerrit commit to Eclipse Simrel aggregation build repo
* Requires signed ECA, and Eclipse user account
* Resources: https://wiki.eclipse.org/Simrel/Contributing_to_Simrel_Aggregation_Build, 
 https://wiki.eclipse.org/Gerrit
* Git repo address: git clone https://git.eclipse.org/r/simrel/org.eclipse.simrel.build
* Add repo locally into IDE via Git perspective
* Open simrel repo properties - Configuration -> setup those branch.master.remote=origin, branch.master.merge=refs/heads/master and branch.master.rebase=true
* (Optional) Install the CBI p2Repo Aggregator - http://download.eclipse.org/cbi/updates/aggregator/ide/4.8 
* Make changes to particular aggrcon file (reddeer.aggrcon)
* Test build locally: Aggregation (only) from Eclipse IDE and CBI aggregator
From the aggregation editor, right-click on the aggregation model and run Clean then Build Aggregation. See CBI/aggregator/manual#Global_actions OR in the repo cli, run mvn clean verify
* Stage changes, create commit message with change ID attached (signoff as well)
* Commit and push...
* When pushing commit into gerrit repo over HTTPS, use passphrase that can be get while being logged here: https://git.eclipse.org/r/#/settings/http-password
* Gerrit commit created, validation jenkins job started: https://ci.eclipse.org/simrel/job/simrel.photon.runaggregator.VALIDATE.gerrit/
* If all good, job will add +1 to the gerrit commit, one needs to get another +2 from someone who has right to push into repo
* Push to repo

## Create and push github tag
* Create an annotated (ideally) tag aiming proper Eclipse Milestone where RedDeer milestone bits will be included
* `git tag -a <tagname>`
* Write a brief description
* Check tag's head commit `git show <tagname>`
* Push tag into origin `git push --tags`
* Push tag into upstream `git push upstream --tags`

## Creating a milestone
* Run https://ci.eclipse.org/reddeer/job/reddeer.milestone/ and set REDDEER_TAG to `<tagname>`
* Pass empty string into BUILD_ALIAS as we stopped using build aliases
* Create a gerrit commit on org.eclipse.simrel.build repo, see another subtask
