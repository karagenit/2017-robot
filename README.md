# FRC 868 - 2017 Robot "Ratchet"

![Travis CI Status](https://travis-ci.com/frc868/2017-robot.svg?token=2H69xuvDWYHku9YjLRkf&branch=master)

### Classpath Issues

To get the CTRE Library Javadocs, you have to manually add the directory to your `.classpath` file. The directory it is located in is user-specific. You may need to run the following command:

```
git update-index --skip-worktree .classpath
```

so that your changes to the `.classpath` file aren't committed. If you commit and push these changes, it will screw up everyone else's Javadocs, so please don't. I don't *think* you'll have to run this command, you should be able to just add the Javadoc yourself and the `.classpath` should already be skipped in the worktree, though I'm really not sure.

This was mainly an oversight by the CTRE dev team, as their installer didn't include an environment variable pointing to the Javadoc location.
