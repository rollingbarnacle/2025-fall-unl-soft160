## System Test: Placing a new file in the remote repository

### Preconditions

- Repository exists on <git.unl.edu>.
- The repository has been cloned on the local file system.
- The local copy of the repository is up-to-date with the remote repository.
- The tester has access to the repository and has an SSH public key associated
  with their account on <git.unl.edu>.

### Test

1.  Open capstone-group12/plan_evaluator/src/main/java/
    edu.unl.cse.soft160.xx.burnplan.evaluator.BurnPlanEvaluationApp

2.  Run the program

The console will print out a message asking you to choose between a single burn
plan, multiple burn plans, a test burn plan, or to exit the program. You will
be choosing the test burn plan.

3.  Type '3' in the console and press enter.

4.  Type 'red_flag_day.json' into the prompt that asks for the JSON file name and press enter.

5. type 