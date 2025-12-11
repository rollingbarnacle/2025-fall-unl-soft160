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

4.  Type 'high_wind_day.json' into the prompt that asks for the JSON file name and press enter.

5. type '40.8206' for the latitude

6. type '-96.6928' for longitude

7. type '2025-12-13' for the burn date

8. type '12:00' for the burn time

9. type 'false' for the cold front

10. type '100' for the acres of land to burn

11. type 'backfires' for the fire type

12. type 'light' for the land fuel type

13. type '10' for the pumpers

14. type '80' for fire starting fuel

15. type '8' for drip torches

16. type '80' for tools

17. type '1' for backpack pumps

18. type '1' for dozers



