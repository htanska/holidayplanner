# HolidayPlanner class

- Take a time span as an input (for example 1.7.2020 - 29.7.2020) and return how many holiday days a person has to use to be able to be on holiday during that period. 
- Take into account national holidays which do not consume holiday days 
- Take into account that Saturdays consume holiday days. 
- Take into account that Sundays do no consume holiday days.
- Accept only time spans that fit within the current holiday period.


## Planning
First I thought the problem through, what things should be consider in planner. 
Period must be iterated through day by day and count all days that consume holiday days.
I chose Calendar class with GregorianCalendar extension so it would be easier to take into consideration length of the months, leap years and sundays.
For checking valid holiday period we first compare years and if equal then check if start is before april then end cannot be april or after.
For national holidays there is a setter method. Comparing national holiday days is done simply by checking if day is in list (String)

### Requirements
- The maximum length of the time span is 50 days.
- The whole time span has to be within the same holiday period that begins on the 1st of April and ends on the 31st of March. 
For example: 
    - 1.12.2020 - 2.1.2021 is a valid time span for a holiday 
    - 1.3.2020 - 1.4.2020 is not a valid time span for a holiday.
- The dates for the time span must be in chronological order.



## Compiling and running
Preliminaries: Java8 and Maven.

Cd to project root folder
Use 
```
mvn package
```
to build project with tests.
Use
```
mvn package -DskipTests=true 
```
to build project without tests. 
Run main method with arguments (period start date, period end date), for example
```
java -jar target/holiday-planner-1.jar 1.5.2020 30.5.2020
```

Write test cases in src/test/java/TestHolidayPlanner.java. 
Run tests by typing
```
mvn test
```

## Challenges and improvement ( ToDo )
It took around 0.5h for planning and around 3h for coding and testing.
Checking days that do not consume holiday days could be more accurate, since now it is just comaring string values and also maybe performance should be better at least on that part.
There is also lack of validation, so the form of the dates is very strict and considered to be done mostly in front end.

