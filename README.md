## Project Description

This project is a console-based Java application designed to retrieve information about train tickets from https://rail.ninja/.
The user inputs route parameters, and the application fetches available ticket data from the site’s API and saves the results into a CSV file.

---

## Run Info

Application requares input data: Departure city; Arrival city; Travel date (in YYYY-MM-DD format, e.g., 2025-06-30); Number of passengers: 1 (adult);
In that order.

Application can be runned through your IDE compilation tools with given parameters or wia creating and using jar file.
Example of parameters: Edinburgh London 2025-06-15 1
To compile project manually use:\
*mvn clean package*\
*java -jar target/PathNinja-1.0-SNAPSHOT.jar [your parameters]* from the same directory\
In the root directory result.csv will be generated.

## Extra Info

The information found is pulled from the website https://rail.ninja/ wia using requests to the API endpoints. 2 calls to get IDs of cities the user had entered and the 1 post request to retrieve all required info.

---

## Examples and Snapshots

Parameters: Edinburgh London 2025-06-15 1
Result: ![example1](pics/example1.png)

Parameters: Amsterdam Paris 2025-08-20 1
Result: ![example2](pics/example2.png)

Parameters: sadasdas Paris 2025-08-20 1
Result: ![example3](pics/example3.png)
