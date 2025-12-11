Burn Plan Evaluation Program
-
This program, titled Burn Plan Evaluation Algorithm, is designed to take in a variety of factors, such as temperature, 
humidity, fuel type, time, etc. in order to determine whether it would be safe (and legal) to perform a controlled burn.

Made by Riley Lingenfelter, Evan Wiles, Drew Churchich, Owen Phillips, & Abdel Ahmed

CURRENT STATUS: COMPLETE, BurnPlanEvaluationAlgorithm has 100% line coverage, all tests pass.

Specification Assumptions
-
Based on the Requirements given, we have assumed that...
1. We are assuming that the user of the application will be performing a burn within the state of Tennessee,
considering that the document that we were supplied with in order to pull data from

Dependencies
-
The Burn Plan Evaluation Algorithm application depends on the OpenWeatherConnector API and the data that it feeds the app.
The OpenWeatherConnector API must be located in the build path of the application in order to run properly and retrieve
the data needed.

The Burn Plan Evaluation Algorithm requires the import of LocalDate in order to effectively retrieve future forecast data
from the OpenWeatherConnector.

For development, you will require JUnit 4 in order to run the automated tests.

Building
-
The Burn Plan Evaluation Algorithm is built using imported Maven scripts, which are located in the capstone-group12/plan_evaluator/src
directory.

Testing
-
All tests are located in the folder titled "test". Of the tests it contains, there is BurnDateTest, which tests the BurnDate
class, BurnPlanEvaluationAlgorithmTest, which tests the class BurnPlanEvaluationAlgorithm, CheckSupplyTest, which tests
the CheckSupply class, CheckWeatherTest, which tests the CheckWeather class, RedFlagTest tests the class RedFlag, and
finally TempWindHumidityTest, which tests the TempWindHumidity class. 

Our testing methodology was not exhaustive, but rather one-way in order to acheieve 100% coverage.

All tests require JUnit 4.

Manual .json tests are located in the folder forecast, which is a subfolder of resources.

Currently, tests all pass with 100% code coverage of the BurnPlanEvaluationAlgorithm class.

Software Architecture
-
The Burn Plan Evaluation Algorithm application is designed in a way that feeds different objects (most of which are defined
using data provided by the OpenWeatherConnector) to the BurnPlanEvaluationAlgorithm class,
which uses the data in order to return one of seven enumerated types, located in the file BurnDetermination.
Among these seven types is:
1. ACCEPTABLE,  // conditions are somewhat favorable and supplies meet minimum requirements
2. DESIRED,  // conditions are highly favorable and supplies meet minimum requirements
3. BURNING_PROHIBITED, // red flag conditions prohibit burning
4. NOT_RECOMMENDED_TEMPERATURE, // conditions are unfavorable due to temperature forecast
5. NOT_RECOMMENDED_WIND, // conditions are unfavorable due to wind forecast
6. NOT_RECOMMENDED_OTHER, // conditions or supplies do not meet minimum requirements
7. INDETERMINATE, // too few data points to make a determination

Acceptable conditions imply that the data provided is sufficient and that a controlled burn is safe to perform on the date
that the user wants.

Desired works just like the latter enum, though, instead, the conditions are deemed to be ideal, or even perfect, on the date
that the user wants.

A prohibited burning enum states that there is a red-flag warning for burning, likely due to one of six items:
1. WIND GUSTS GREATER THAN 20MPH.
2. RELATIVE HUMIDITY BELOW 20 PERCENT.
3. AIR TEMPERATURE ABOVE 80 DEGREES.
4. COLD FRONT TO PASS WITHIN 12 HOURS.
5. UNFAVORABLE FIRE WEATHER FORECAST.
6. "BAN ON OUTDOOR BURNING” DECLARED FROM TENNESSEE
   GOVERNOR.

Burning on the date that the user wants will not be allowed.

A warning for a not recommended temperature states that a user is allowed to burn, but it is not recommended due to a
temperature that could result in safety issues for the user.

A warning for not recommended wind states that the user is allowed to burn, but it is not recommended due to potentially
unsafe wind speeds.

A warning due to not recommended "other" factors implies that the date the user input has improper humidity, or the quantity/
type of tools that the user input do not meet the safety criteria.

Lastly, a warning for indeterminate data means that there is not enough data for the BurnPlanEvaluation Algorithm to properly
make a decision.

===

A large portion of the files within the source directory are objects. These objects are as follows:

1. BurnPlan: This file creates a BurnPlan object, which is directly fed to the BurnPlanEvaluationAlgorithm file in order to
determine whether a burn plan is acceptable or not, returning a single enumerated type from the aforementioned BurnDetermination
class.
2. CheckLand: This file creates a CheckLand object, which can be accessed using getCheckLand(). It is used to, unsurprisingly,
check the land type (i.e. vegetation (which draws an enumerated type from LandTypes), land size.).
3. CheckSupplies: This file creates a CheckSupplies object, which feeds its data into BurnPlan, which is then accessed by
BurnPlanEvaluationAlgorithm.
4. CheckWeather: This file creates a CheckWeather object based directly off of the data that it pulls from the OpenWeatherConnector
API. From there, it is accessed by BurnPlan, and further accessed by BurnPlanEvaluationAlgorithm.
5. Location: Location is an object which gets data directly from user input via the main method, usually in the form of
latitude & longitude coordinates. From there, the OpenWeatherConnector accesses the Location object's data and utilizes
the input coordinates to determine the precise weather data for the location of the proposed burn.
6. RedFlag: RedFlag is a class that, when given the weather data that was retrieved via the OpenWeatherConnector, compares
certain criteria in order to determine whether a red flag warning is in effect. If it determines that a red flag warning
is the case, it will return a value indicating a warning, and the burn plan will be denied.
7. Supply: Supply is an object that creates a single supply item based on what the user inputs. This object is accessed
by CheckSupplies in order to check that the supplies given are the required items.
8. TempWindHumidity: TempWindHumidity is a class that determines if the temperature, wind, and humidity data retrieved
from the OpenWeatherConnector are within safe bounds. If not, it returns and enumerated type from BurnDetermination
corresponding with the data.

===

There are four files that create enumerated types to be used throughout the application's files:
1. BurnDetermination: This file creates the enums that are used to determine the outcome of the evaluation.
2. FireTypes: This file contains the types of controlled burns that the user is allowed to perform. The usage of any specific
enum from this file will directly determine the tolerances for the data.
3. FuelCondition: This file describes whether the fuel is volatile or non-volatile, which makes the burn more or less dangerous,
and, as such, changes the tolerances for the data.
4. LandTypes: This file describes the density of the vegetation within the site of the burn. If the land is determined
 to be "heavy", there are more grasses and shrubs available. the opposite is true for "light" land.
 