# Contributing

First, if you find any bugs or areas to improve, please open [issue on GitHub](https://github.com/eclipse/reddeer/issues).

In case you would like to implement a patch, note that RedDeer is Eclipse project and you have to have created an account under [Eclipse Foundation](https://accounts.eclipse.org/) and signed [Eclipse Contributor Agreement](https://www.eclipse.org/legal/ECA.php). Go through the following section for the details.

### Eclipse Contributor Agreement

Before your contribution can be accepted by the project team contributors must
electronically sign the Eclipse Contributor Agreement (ECA).

* http://www.eclipse.org/legal/ECA.php

Commits that are provided by non-committers must have a Signed-off-by field in
the footer indicating that the author is aware of the terms by which the
contribution has been provided to the project. The non-committer must
additionally have an Eclipse Foundation account and must have a signed Eclipse
Contributor Agreement (ECA) on file.

For more information, please see the Eclipse Committer Handbook:
https://www.eclipse.org/projects/handbook/#resources-commit

Moreover, if you are making a contributinon within your work position, you should read [Eclipse Foundation Legal FAQ](https://www.eclipse.org/legal/legalfaq.php).

### Steps
Start with forking the repo and/or clone it locally to your git folder.

    $ git clone https://github.com/eclipse/reddeer.git
    $ git pull upstream master # in case you want actual state of project
    $ git checkout -b name-of-our-branch 
    
Ideally, branch name should corresponds to reddeer github issue, ie. rd-1699

Implement your changes/patch or bug fix...
In any case, it is a best way to verify your changes that you run build with tests before you actually make a pull request.

    $ git push origin name-of-your-branch
    
Then you can [generate a pull-request](https://help.github.com/en/articles/about-pull-requests) and we will make review, discuss, etc. If code is good enough, it can be merged into master.

### Contact

Contact the project developers via the project's "dev" list.

* https://dev.eclipse.org/mailman/listinfo/reddeer-dev
