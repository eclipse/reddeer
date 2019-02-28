This issue is a form of an epic task consisting of all steps that are required to undertake in order to release RedDeer final bits under Eclipse Simultaneous Release 2019-03. 

Eclipse Project plan 2019-03 [link].

Release | Date | Span | Notes
-- | -- | -- | --
2019-03 M1 | Friday, January 18, 2019 | 01/11 to 01/18 | 3 weeks from 2018-12 GA
2019-03 M2 | Friday, February 8, 2019 | 02/01 to 02/08 | 3 weeks from M1
2019-03 M3 | Friday, March 1, 2019 | 02/22 to 03/01 | 3 weeks from M2
2019-03 RC1 | Friday, March 8, 2019 | 03/01 to 03/08 | 1 week from M3
2019-03 RC2 | Friday, March 15, 2019 | 03/08 to 03/15 | 1 week from RC1
Quiet week | 03/15 to 03/19 | No builds during "quiet period". It is assumed all code is done by the end of RC2.
2019-03 GA | Wednesday, March 20, 2019 |   | 5 days from RC2

- [ ] Plan work for new release: [issues]
- [ ] Upversion to proper snapshot version (2.5.0-SNAPSHOT) if not done yet
 * mvn org.eclipse.tycho:tycho-versions-plugin:set-version -DnewVersion=2.3.0.qualifier
* In addition, these files were modified manually:
* archetype/src/main/resources/archetype-resources/pom.xml
* archetype/pom.xml
- [ ] share the intent to participate in SimRel (should be done at latest at M1, should be valid for one year until June release 2019) 
- [ ] Create new project release 
* https://projects.eclipse.org/projects/technology.reddeer -> Commiter Tools -> Create a new Release
* - [ ] Create a milestone(s) in release plan
* - [ ] Schedule Release Review after project release is created (1st of 3rd Wed. on each month)
* - [ ] This review is pending; contact The Eclipse Management Organization (emo@eclipse.org) to make it public. (can be found under https://projects.eclipse.org/projects/technology.reddeer/releases/2.5.0 -> Review: -> 2.5.0 Release Review link)
* - [ ] Request for PMC approval (Committer Tools › Communication › Send Email to the PMC)
* - [ ] Get +1 on technology-pmc mailing list request
* - [ ] Create IP Log - https://dev.eclipse.org/ipzilla
* - [ ] Wait for release review is done on planned date after all above steps are fulfilled
* - [ ] Bugzilla for release review with approvals: https://bugs.eclipse.org/bugs/show_bug.cgi?id=544040

- [ ] Jump in SimRel Milestone release with offset +3
this is probably M2 +3 as reddeer cannot be built unless we got full stack of packages in some update site and this is usually happening at the end of M1.
- [ ] RedDeer milestone releasing - MX, RCX, Final, includes:
* Implement changes, fixes, etc. to RD
* Release proper milestone via https://ci.eclipse.org/reddeer CCI
* Create Eclipse SimRel aggregation build repo gerrit commit, validate - https://ci.eclipse.org/simrel/job/simrel.photon.runaggregator.VALIDATE.gerrit/, push, see sub-tasks.

- [ ] Spread release note to proper mailing lists (reddeer-dev)

[link]: https://wiki.eclipse.org/SimRel/2019-03/Simultaneous_Release_Plan
[issues]: https://github.com/eclipse/reddeer/issues?q=is%3Aopen+is%3Aissue+milestone%3A2.5.0
planurl=http://www.eclipse.org/eclipse/development/plans/eclipse_project_plan_4_11.xml
