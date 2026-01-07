/**
 * Interface.java
 *
 * COMP 3380 SECTION A02
 * INSTRUCTOR    Dr. Heather Matheson
 * ASSIGNMENT    Final Project
 * @author       Hoang Huy Truong, 7960938
 * @author       Aamir Sangey. 7960129
 * @author       Deep Patel. 7957389
 * @version      December 6, 2024
 *
 * REMARKS: This program is interface used to interact with the crash database and run its queries
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.io.FileReader;
import java.io.BufferedReader;

public class Interface {
    public static void main(String[] args) {
        CrashDatabase db = new CrashDatabase();
        runConsole(db); //Run the interface

        System.out.println("Exiting...");
    }

    //Prints interface to console
    public static void runConsole(CrashDatabase db) {
        try {
            printIntroduction(); //Prints a brief statement to introduce the database to the user
            Scanner console = new Scanner(System.in);
            System.out.print("> ");
            String input = console.nextLine();

            if (input.equals("C")) {
                System.out.print("Welcome to the Chicago Crashes Database!\n");
                printHelp();
                System.out.print("> ");
                input = console.nextLine();
                String[] parts;
                String arg = "";

                //Run until user inputs q to quit
                while (input != null && !input.equals("q")) {
                    parts = input.split("\\s+");
                    if (input.indexOf(" ") > 0)
                        arg = input.substring(input.indexOf(" ")).trim();

                    System.out.println();

                    //Help menu
                    if (parts[0].equals("h")) {
                        printHelp();
                    }

                    
                    //Populate database
                    else if (parts[0].equals("pop")) {
                        db.loadData();
                    }

                    //Delete all tables in database
                    else if (parts[0].equals("del")) {
                        db.dropAllTables();
                    }

                    //Get personID
                    else if (parts[0].equals("p")) {
                        try {
                            if (parts.length == 2) {
                                int top = Integer.parseInt(parts[1]);
                                if((top > 0) && (top < 2147483647)){
                                    db.getPersonID(top);
                                }else{
                                    System.out.println("Not a valid number.");
                                }
                            } else {
                                System.out.println("Invalid number of arguments for this command");
                            }
                        } catch (Exception e) {
                            System.out.println("Must be numeric value!");
                        }
                        
                    }

                    //Search by personID
                    else if (parts[0].equals("s")) {
                        if (parts.length == 2) {
                            db.searchByPersonID(parts[1]);
                        } else {
                            System.out.println("Invalid number of arguments for this command");
                        }
                    }

                    //Crashes by month
                    else if (parts[0].equals("c")) {
                        try {
                            if (parts.length == 3) {
                                int month = Integer.parseInt(parts[1]);
                                int year = Integer.parseInt(parts[2]);
                                    if (year >= 2022 && year <= 2024 && month >= 1 && month <= 12) {
                                        db.crashByMonth(month, year);
                                    } else {
                                        System.out.println("Invalid year or month!");
                                        System.out.println("Please enter a month from 1-12 and year from 2022-2024");
                                    }
                            } else {
                                System.out.println("Invalid number of arguments for this command");
                            }
                        } catch (Exception e) {
                            System.out.println("Must be numeric value!");
                        }
                    }

                    //Total damages per year
                    else if (parts[0].equals("d")) {
                        try {
                            if (parts.length == 2) {
                                int year = Integer.parseInt(parts[1]);
                                if (year >= 2022 && year <= 2024) {
                                    db.totalDamage(year);
                                } else {
                                    System.out.println("Invalid year! Please enter year from 2022 to 2024");
                                }
                            } else {
                                System.out.println("Invalid number of arguments for this command");
                            }

                        } catch (Exception e) {
                            System.out.println("Must be numeric value!");
                        }
                    }

                    //Total Crashes per year
                    else if (parts[0].equals("sc")) {
                        try {
                            if (parts.length == 2) {
                                int year = Integer.parseInt(parts[1]);
                                if (year >= 2022 && year <= 2024) {
                                    db.totalCrashes(year);
                                } else {
                                    System.out.println("Invalid year! Please enter year from 2022 to 2024");
                                }
                            } else {
                                System.out.println("Invalid number of arguments for this command");
                            }
                        } catch (Exception e) {
                            System.out.println("Must be numeric value!");
                        }
                    }

                    //Leading causes of crashes
                    else if (parts[0].equals("lc")) {
                        try {
                            if (parts.length == 2) {
                                int top = Integer.parseInt(parts[1]);
                                if((top > 0) && (top < 2147483647)){
                                    db.leadingCauses(top);
                                }else{
                                    System.out.println("Not a valid number.");
                                }
                            } else {
                                System.out.println("Invalid number of arguments for this command");
                            }
                        } catch (Exception e) {
                            System.out.println("Must be numeric value!");
                        }
                    }

                    //Number of crashes 
                    else if (parts[0].equals("nc")) {
                        if (parts.length == 2) {
                            db.numCrashes(parts[1]);
                        } else {
                            System.out.println("Invalid number of arguments for this command");
                        }
                    }

                    //Crashes that occur at different lighting conditions
                    else if (parts[0].equals("l")) {
                        db.lightingCrashes();
                    }

                    //Crashes that occur at night time
                    else if (parts[0].equals("ntc")) {
                        db.nightTimeCrashes();
                    }

                    //Number of crashes at different beat of occurence
                    else if (parts[0].equals("boo")) {
                        try {
                            if (parts.length == 2) {
                                int top = Integer.parseInt(parts[1]);
                                if((top > 0) && (top < 2147483647)){
                                    db.beatOfOccurence(top);
                                }else{
                                    System.out.println("Not a valid number.");
                                }
                            } else {
                                System.out.println("Invalid number of arguments for this command");
                            }
                        } catch (Exception e) {
                            System.out.println("Must be numeric value!");
                        }
                    }

                    //Street with most crashes
                    else if (parts[0].equals("str")) {
                        try {
                            if (parts.length == 2) {
                                int top = Integer.parseInt(parts[1]);
                                if((top > 0) && (top < 2147483647)){
                                    db.mostCrashesStreets(top);
                                }else{
                                    System.out.println("Not a valid number.");
                                }
                            } else {
                                System.out.println("Invalid number of arguments for this command");
                            }

                        } catch (Exception e) {
                            System.out.println("Must be numeric value!");
                        }
                    }

                    //Severity of injuries compared to age
                    else if (parts[0].equals("msa")) {
                        try {
                            if (parts.length == 2) {
                                int top = Integer.parseInt(parts[1]);
                                if((top > 0) && (top < 2147483647)){
                                    db.mostSevereAge(top);
                                }else{
                                    System.out.println("Not a valid number.");
                                }
                            } else {
                                System.out.println("Invalid number of arguments for this command");
                            }

                        } catch (Exception e) {
                            System.out.println("Must be numeric value!");
                        }
                    }

                    //Crashes in different traffic conditions
                    else if (parts[0].equals("tc")) {
                        db.trafficCrash();
                    }

                    //Crashes related to car defects
                    else if (parts[0].equals("cdi")) {
                        db.carDefectToInjury();
                    }

                    //Crashes related to road defects
                    else if (parts[0].equals("rdc")) {
                        db.roadDefectToCrash();
                    }   

                    //Months with crashes above the average number of crashes
                    else if (parts[0].equals("avg")) {
                        db.crashesAboveAverage();
                    }

                    //Crashes with more than numPeople and numVehicles
                    else if (parts[0].equals("sa")) {
                        try {
                            if (parts.length == 3) {
                                int numPeople = Integer.parseInt(parts[1]);
                                int numVehicles = Integer.parseInt(parts[2]);
                                if(numPeople > 0 && numVehicles > 0 && numPeople < 2147483647 && numVehicles < 2147483647){
                                    db.severeCrashes(numPeople, numVehicles);
                                }else{
                                    System.out.println("Valid numerics not entered");
                                }
                            } else {
                                System.out.println("Invalid number of arguments for this command");
                            }

                        } catch (Exception e) {
                            System.out.println("Must be numeric value!");
                        }
                    }

                    //Locations with top crashes
                    else if (parts[0].equals("lo")) {
                        try {
                            if (parts.length == 2) {
                                int top = Integer.parseInt(parts[1]);
                                if((top > 0) && (top < 2147483647)){
                                    db.locationsWithTopCrashes(top);
                                }else{
                                    System.out.println("Not a valid number.");
                                }
                            } else {
                                System.out.println("Invalid number of arguments for this command");
                            }
                        } catch (Exception e) {
                            System.out.println("Must be numeric value!");
                        }
                    }

                    //Get people involved in a crash
                    else if (parts[0].equals("gpi")) {
                        if(parts.length == 2){
                            db.getPeopleInvolved(parts[1]);
                        }else{
                            System.out.println("Invalid number of arguments.");
                        }
                    }
                    
                    //Get vehicles involved in a crash
                    else if (parts[0].equals("gvi")) {
                        if(parts.length == 2){
                            db.getVehiclesInvolved(parts[1]);
                        }else{
                            System.out.println("Invalid number of arguments.");
                        }
                    }
                    
                    else {
                        System.out.println("Invalid command. Read the help menu by typing 'h'.");
                    }
                    System.out.println();
                    System.out.print("> ");
                    input = console.nextLine();

                }
                console.close();
            }
        } catch (Exception e) {
            System.out.println("Unable to execute");
        }

    }

    //print introduction for program
    private static void printIntroduction() {
        System.out.println(
                "**************************************** Welcome To Chicago Crash Database ***********************************************\n\n"
                + "This database manages detailed information regarding car accidents reported within the city of Chicago, aiming to support analysis and reporting.\n\n"
                + "Data has been obtained from the United States Open Government Data and has been published by The City Of Chicago. \n\n"
                + "Data includes information on vehicles, people involved in crashes, locations of the crashes and various other factors contributing to the crash. \n\n" 
                + "You can explore Crashes from the year 2022 until 2024 inclusive.\n\n"
                + "The data has been collected from an electronic crash reporting system at the Chicago Police Department and does not include any personal information.\n\n"
                + "**************************************************************************************************************************\n\n"
                + "Press “C” to continue.\nPress any other key to exit\n");
    }


    //Print the help interface with keywords to run queries
    private static void printHelp() {
        System.out.println("******************************* Commands ************************************");
        System.out.println("  Key                           Description");
        System.out.println("> h                             List all the query keywords and their description");
        System.out.println("> pop                           Populate the Chicago crashes database");
        System.out.println("> del                           Delete all data from database");
        System.out.println("> p <int N>                     List information of the first N people");
        System.out.println("> s <personID>                  Search what crashes a person is involved in");
        System.out.println("> nc <personID>                 Number of crashes a person has been in");
        System.out.println("> c <month> <year>              Crashes that happened in a particular month of a year");
        System.out.println("> gpi <crashID>                 Gives all the people involved in a specific crash");
        System.out.println("> gvi <crashID>                 Gives all the vehicles involved in a specific crash");
        System.out.println("> d <year>                      Total damage per month in a specific year");
        System.out.println("> sc <year>                     Sum of crashes that happen per month in an year");
        System.out.println("> lc <int N>                    Top N Leading causes of crashes");
        System.out.println("> l                             Number of crashes that occur in different Lighting contions");
        System.out.println("> ntc                           Streets with the most nighttime crashes");
        System.out.println("> boo <int N>                   List the top N beat of occurence with the most crashes");
        System.out.println("> str <int N>                   Top N streets with the most crashes");
        System.out.println("> msa <int N>                   Top N age of drivers involved in each type of crash");
        System.out.println("> tc                            Crashes that occurred in the presence of traffic control devices");
        System.out.println("> cdi                           Vehicle defects and the number injured in the vehicle");
        System.out.println("> rdc                           Frequency of crashes as compared to road defect");
        System.out.println("> avg                           List of months with crashes higher than the overall average");
        System.out.println("> sa <numPeople> <numVehicles>  Crashes with more than <numPeople> and <numvehicles> involved");
        System.out.println("> lo <top N>                    Top N locations with most crashes");
        System.out.println("> q                             Exit the program");
        System.out.println("---- end help ----- ");
    }
}

class CrashDatabase {
    //Connect to Uranium
    private Connection connection;

    public CrashDatabase() {
        Properties prop = new Properties();
        String fileName = "auth.cfg";
        try {
            FileInputStream configFile = new FileInputStream(fileName);
            prop.load(configFile);
            configFile.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Could not find config file.");
            System.exit(1);
        } catch (IOException ex) {
            System.out.println("Error reading config file.");
            System.exit(1);
        }
        String username = (prop.getProperty("username"));
        String password = (prop.getProperty("password"));

        if (username == null || password == null) {
            System.out.println("Username or password not provided.");
            System.exit(1);
        }

        String connectionUrl = "jdbc:sqlserver://uranium.cs.umanitoba.ca:1433;"
                + "database=cs3380;"
                + "user=" + username + ";"
                + "password=" + password + ";"
                + "encrypt=false;"
                + "trustServerCertificate=false;"
                + "loginTimeout=30;";
        try {
            connection = DriverManager.getConnection(connectionUrl);
        } catch (SQLException e) {
            System.out.println("Unable To Execute: Please Populate Database First");
            
        }
    }

    // Retrieves all people involved in a specific crash.
    // Executes a UNION query to collect information about drivers, passengers,
    // pedestrians, and cyclists involved in the given crash ID.
    public void getPeopleInvolved(String crashID) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                "SELECT 'Driver' AS personType, personID, sex, age, injuryClassification " +
                "FROM driver WHERE crashID = ? " +
                "UNION ALL " +
                "SELECT 'Passenger' AS personType, personID, sex, age, injuryClassification " +
                "FROM passenger WHERE crashID = ? " +
                "UNION ALL " +
                "SELECT 'Pedestrian' AS personType, personID, sex, age, injuryClassification " +
                "FROM pedestrian WHERE crashID = ? " +
                "UNION ALL " +
                "SELECT 'Cyclist' AS personType, personID, sex, age, injuryClassification " +
                "FROM cyclist WHERE crashID = ?;"
            );
    
            
            statement.setString(1, crashID);
            statement.setString(2, crashID);
            statement.setString(3, crashID);
            statement.setString(4, crashID);
    
            ResultSet resultSet = statement.executeQuery();
    
            System.out.println("People involved in crash ID: " + crashID);
            while (resultSet.next()) {
                System.out.println("Role: " + resultSet.getString("personType"));
                System.out.println("Person ID: " + resultSet.getString("personID"));
                System.out.println("Sex: " + resultSet.getString("sex"));
                System.out.println("Age: " + resultSet.getString("age"));
                System.out.println("Injury Classification: " + resultSet.getString("injuryClassification"));
                System.out.println("------------------------------------------------------------");
            }
    
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Unable To Execute: Please Populate Database First");
        }
    }
    
    // this function retrieves information about a specified number of people.
    // Uses a UNION query to fetch details from multiple roles (drivers, passengers,pedestrians, and cyclists)
    // the number of people is limited by the input provided by user.
    public void getPersonID(int limit) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                "SELECT TOP (?) personID, role, sex, age, crashID, injuryClassification, date " +
                "FROM ( " +
                "SELECT personID, 'Driver' AS role, sex, age, crashID, injuryClassification, date FROM driver " +
                "UNION " +
                "SELECT personID, 'Passenger' AS role, sex, age, crashID, injuryClassification, date FROM passenger " +
                "UNION " +
                "SELECT personID, 'Pedestrian' AS role, sex, age, crashID, injuryClassification, date FROM pedestrian " +
                "UNION " +
                "SELECT personID, 'Cyclist' AS role, sex, age, crashID, injuryClassification, date FROM cyclist " +
                ") AS AllPersons;"
                
            );
            statement.setInt(1, limit);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("Information for the first " + limit + " people in the table:");
            int rowNum = 1;
            while (resultSet.next()) {
                System.out.printf("%-2d. PersonID: %-9s Role: %-11s Sex: %-4s CrashID: %-13s Injury: %-25s Date: %-11s",
                rowNum, resultSet.getString("personID"), resultSet.getString("role"), resultSet.getString("sex")
                , resultSet.getString("crashID"), resultSet.getString("injuryClassification"),  resultSet.getString("date"));
                
                if ((resultSet.getInt("age")) != 0) {
                    System.out.print(" Age: " + resultSet.getString("age"));
                }
                System.out.println();
                rowNum++;
                }
        } catch (SQLException e) {
            System.out.println("Unable To Execute: Please Populate Database First");
        }

    }

    // Searches for crashes associated with a specific person ID.
    // Executes a UNION query joining crashes, injuries, locations, and crash factors for each type of person. 
    public void searchByPersonID(String personID) {
        try {

            PreparedStatement statement = connection.prepareStatement(
                "SELECT crashes.crashID, personType AS role, crashMonth, crashYear, streetNumber, streetName, primaryCause " +
                "FROM driver " +
                "JOIN crashes ON driver.crashID = crashes.crashID " +
                "JOIN injury ON crashes.crashID = injury.crashID " +
                "JOIN location ON crashes.locationID = location.locationID " +
                "JOIN crashFactor ON crashes.crashID = crashFactor.crashID " +
                "WHERE driver.personID = ? " +
                "UNION " +
                "SELECT crashes.crashID, personType AS role, crashMonth, crashYear, streetNumber, streetName, primaryCause " +
                "FROM passenger " +
                "JOIN crashes ON passenger.crashID = crashes.crashID " +
                "JOIN injury ON crashes.crashID = injury.crashID " +
                "JOIN location ON crashes.locationID = location.locationID " +
                "JOIN crashFactor ON crashes.crashID = crashFactor.crashID " +
                "WHERE passenger.personID = ? " +
                "UNION " +
                "SELECT crashes.crashID, personType AS role, crashMonth, crashYear, streetNumber, streetName, primaryCause " +
                "FROM pedestrian " +
                "JOIN crashes ON pedestrian.crashID = crashes.crashID " +
                "JOIN injury ON crashes.crashID = injury.crashID " +
                "JOIN location ON crashes.locationID = location.locationID " +
                "JOIN crashFactor ON crashes.crashID = crashFactor.crashID " +
                "WHERE pedestrian.personID = ? " +
                "UNION " +
                "SELECT crashes.crashID, personType AS role, crashMonth, crashYear, streetNumber, streetName, primaryCause " +
                "FROM cyclist " +
                "JOIN crashes ON cyclist.crashID = crashes.crashID " +
                "JOIN injury ON crashes.crashID = injury.crashID " +
                "JOIN location ON crashes.locationID = location.locationID " +
                "JOIN crashFactor ON crashes.crashID = crashFactor.crashID " +
                "WHERE cyclist.personID = ?;"
            );

            statement.setString(1, personID);
            statement.setString(2, personID);
            statement.setString(3, personID);
            statement.setString(4, personID);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("Information regarding person " + personID);
            while (resultSet.next()) {
                System.out.print("crashID: " + resultSet.getString("crashID") + 
                "\nRole: " + resultSet.getString("role") + 
                "\nMonth: " + resultSet.getString("crashMonth") + 
                "\nYear: " + resultSet.getString("crashYear") + 
                "\nStreet: " + resultSet.getString("streetNumber") +
                 " " + resultSet.getString("streetName") + 
                 "\nCause of Crash: " + resultSet.getString("primaryCause") + "\n");
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Unable To Execute: Please Populate Database First");
        }
    }

    // Retrieves crash data for a specific month and year.
    // Executes a query to fetch crashes, injuries, and damages filtered by the given
    // month and year.
    public void crashByMonth(int month, int year) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                "SELECT crashes.crashID, reportType, totalInjuries, numUnits, cost FROM crashes " +
                "JOIN injury ON injury.crashID = crashes.crashID " +
                "JOIN damages ON damages.crashID = injury.crashID " +
                "WHERE crashes.crashMonth = ? AND crashes.crashYear = ?;\n");

            statement.setInt(1, month);
            statement.setInt(2, year);
            ResultSet resultSet = statement.executeQuery();
            System.out.printf("All the crashes that occured in %d/%d: \n", month, year);
            int rowNum = 1;
            while (resultSet.next()) {
                System.out.printf("%-2d. crashID: %-2s Reported: %-27s Vehicles Involved: %-2d Total Injured: %-2d Cost: $%d.00%n",
                rowNum, resultSet.getString("crashID"), resultSet.getString("reportType"), resultSet.getInt("numUnits"),
                resultSet.getInt("totalInjuries"), resultSet.getInt("cost"));
                rowNum++;
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Unable To Execute: Please Populate Database First");
        }
    }

    // Retrieves details about vehicles involved in a specific crash.
    // Executes a query to collect vehicle attributes such as make, model, type, and usage.
    /// takes in the user input which will be crashID.
    public void getVehiclesInvolved(String crashID) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                "SELECT vehicleID, unitNumber, vehicleMake, vehicleModel, vehicleYear, " +
                "vehicleType, vehicleUse, " +
                "numPassengers " +
                "FROM vehicles  " +
                "WHERE crashID = ?;"
            );
            statement.setString(1, crashID); 
            ResultSet resultSet = statement.executeQuery();
    
            System.out.println("Vehicles involved in Crash ID: " + crashID);
            while (resultSet.next()) {
                int vehicleID = resultSet.getInt("vehicleID");
                int unitNumber = resultSet.getInt("unitNumber");
                String vehicleMake = resultSet.getString("vehicleMake");
                String vehicleModel = resultSet.getString("vehicleModel");
                int vehicleYear = resultSet.getInt("vehicleYear");
                String vehicleType = resultSet.getString("vehicleType");
                String vehicleUse = resultSet.getString("vehicleUse");
                int numPassengers = resultSet.getInt("numPassengers");
                
                System.out.println("\nVehicle ID: " + vehicleID);
                System.out.println("Unit Number: " + unitNumber);
                System.out.println("Make: " + vehicleMake);
                System.out.println("Model: " + vehicleModel);
                System.out.println("Year: " + vehicleYear);
                System.out.println("Type: " + vehicleType);
                System.out.println("Use: " + vehicleUse );
                System.out.println("Passengers: " + numPassengers); 
                System.out.println("-----------------------------------------");
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Unable To Execute: Please Populate Database First");
        }
    }

    // Calculates total damages for each month in a given year.
    // Executes a query grouping by month and summing the damage costs.
    public void totalDamage(int year) {
        try {
            PreparedStatement statement = connection.prepareStatement(
            "SELECT crashes.crashYear, crashes.crashMonth, sum(damages.cost) as totalCost FROM crashes " +
            "JOIN damages on crashes.crashID = damages.crashID " +
            "WHERE crashes.crashYear = ? " +
            "GROUP BY crashes.crashYear, crashes.crashMonth " +
            "ORDER BY crashes.crashYear, crashes.crashMonth;");

            statement.setInt(1, year);
            ResultSet resultSet = statement.executeQuery();

            System.out.printf("Total damages in each month of %d: %n", year);
            while (resultSet.next()) {
                System.out.printf("Month: %-5d Total Damage Cost: $%d.00%n", resultSet.getInt("crashMonth"),
                resultSet.getInt("totalCost"));
                }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Unable To Execute: Please Populate Database First");
        }
    }

    // Counts the total number of crashes for each month in a given year.
    // Groups crashes by month and sorts them by the number of accidents.
    public void totalCrashes(int year) {
        try {

            PreparedStatement statement = connection.prepareStatement(
            "SELECT crashes.crashYear, crashes.crashMonth, count(crashes.crashID) as totalAccidents " +
            "FROM crashes " + 
            "WHERE crashes.crashYear = ? " +
            "GROUP BY crashes.crashYear, crashes.crashMonth " + 
            "ORDER BY totalAccidents DESC;");

            statement.setInt(1, year);
            ResultSet resultSet = statement.executeQuery();
            System.out.printf("Month with the most accidents in %d: \n", year);
            while (resultSet.next()) {
                System.out.printf("Month: %-5d Total Crashes: %d%n", resultSet.getInt("crashMonth"),
                resultSet.getInt("totalAccidents"));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Unable To Execute: Please Populate Database First");
        }
    }

    // Identifies the leading causes of accidents, excluding undefined causes.
    // Executes a query grouping by primary cause and limits the results to the top N causes.
    public void leadingCauses(int top) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                "SELECT TOP (?) crashFactor.primaryCause, COUNT(primaryCause) AS num " +
                "FROM crashes " + 
                "JOIN crashFactor ON crashes.crashID = crashFactor.crashID " + 
                "WHERE crashes.crashID NOT IN( " +
                "SELECT crashes.crashID " +
                "FROM crashes " + 
                "JOIN crashFactor ON crashes.crashID = crashFactor.crashID " + 
                "WHERE primaryCause = 'Unable To Determine' OR primaryCause = 'Not Applicable') " +
                "GROUP BY crashFactor.primaryCause " + 
                "ORDER BY num DESC;"
            );

            statement.setInt(1, top);
            ResultSet resultSet = statement.executeQuery();
            System.out.printf("Top %d leading causes of accidents\n", top);
            int rowNum = 1;
            while (resultSet.next()) {
                System.out.print(rowNum + ". " + resultSet.getString("primaryCause") + 
                " - " + resultSet.getInt("num") + "\n");
                rowNum++;
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Unable To Execute: Please Populate Database First");
        }
    }

    // Counts the number of crashes a person has been involved in as different roles.
    // Executes a UNION query grouping by role and counting unique crashes.
    public void numCrashes(String personID) {
        try {

            PreparedStatement statement = connection.prepareStatement(
                "SELECT 'Driver' AS role, COUNT(DISTINCT Crashes.crashID) AS NumAccidents " + 
                "FROM driver " + 
                "JOIN crashes ON Driver.crashID = Crashes.crashID " + 
                "WHERE Driver.personID = ? " + 
                "UNION ALL " + 
                "SELECT 'Passenger' AS role, COUNT(DISTINCT Crashes.crashID) AS NumAccidents " + 
                "FROM passenger " + 
                "JOIN crashes ON passenger.crashID = crashes.crashID " + 
                "WHERE passenger.personID = ? " + 
                "UNION ALL " + 
                "SELECT 'Pedestrian' AS role, COUNT(DISTINCT Crashes.crashID) AS NumAccidents " + 
                "FROM pedestrian " + 
                "JOIN crashes ON pedestrian.crashID = crashes.crashID " + 
                "WHERE pedestrian.personID = ? " + 
                "UNION ALL " + 
                "SELECT 'Cyclist' AS role, COUNT(DISTINCT Crashes.crashID) AS NumAccidents " + 
                "FROM cyclist " + 
                "JOIN crashes ON cyclist.crashID = crashes.crashID " +
                "WHERE cyclist.personID = ?;"
            );
            statement.setString(1, personID);
            statement.setString(2, personID);
            statement.setString(3, personID);
            statement.setString(4, personID);

            ResultSet resultSet = statement.executeQuery();
            System.out.printf("Number of accidents this person has been involved in as different roles\n");
            while (resultSet.next()) {
                System.out.print(" Role: "+ resultSet.getString("role") +
                 ", Total Accidents: " + resultSet.getInt("NumAccidents") + "\n");
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Unable To Execute: Please Populate Database First");
        }
    }

    // Counts crashes occurring under different lighting conditions.
    // Groups crashes by lighting condition and excludes unknown data.
    public void lightingCrashes() {
        try {

            PreparedStatement statement = connection.prepareStatement(
                "SELECT lightingCondition, count(crashes.crashID) as numCrashes FROM crashes " + 
                "JOIN crashFactor on crashes.crashID = crashFactor.crashID " +
                "JOIN climateCondition on climateCondition.climateID = crashFactor.climateID " + 
                "WHERE lightingCondition != 'UNKNOWN'" + 
                "GROUP BY lightingCondition;"
            ); 
            ResultSet resultSet = statement.executeQuery();
            System.out.printf("Numbers of crashes in different lighting conditions\n");
            while (resultSet.next()) {
                System.out.printf("Lighting Condition: %-23s Total Crashes: %s%n", resultSet.getString("lightingCondition")
                , resultSet.getString("numCrashes"));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Unable To Execute: Please Populate Database First");
        }
    }

    // Lists streets with the most nighttime crashes.
    // Filters crashes by lighting conditions and groups results by street names.
    public void nightTimeCrashes() {
        try {

            PreparedStatement statement = connection.prepareStatement(
                "SELECT streetName, count(*) as numAccidents " + 
                "FROM crashes " + 
                "JOIN location on crashes.locationID = location.locationID " + 
                "JOIN crashFactor ON crashes.crashID = crashFactor.crashID " + 
                "JOIN climateCondition ON climateCondition.climateID = crashFactor.climateID " + 
                "WHERE lightingCondition like 'DARKNESS' " + 
                "GROUP BY location.streetName " +
                "ORDER BY numAccidents DESC; "
            );
            ResultSet resultSet = statement.executeQuery();
            System.out.printf(" Streets with the most night time crashes\n");
            int rowNum = 1;
            while (resultSet.next()) {
                System.out.printf("%-2d. Street Name: %-28s Total Accidents: %d%n",rowNum
                ,resultSet.getString("streetName")
                ,resultSet.getInt("numAccidents"));
                rowNum++;
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Unable To Execute: Please Populate Database First");
        }
    }

    // Lists top locations with the most crashes and related injuries.
    // Utilizes a CTE (Common Table Expression) and a ranking query.
    public void beatOfOccurence(int top) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                "SELECT TOP (?) beatOfOccurence, count(crashes.crashID) as numAccidents " + 
                "FROM location " +
                "JOIN crashes on crashes.locationID = location.locationID " +
                "GROUP BY beatOfOccurence " +
                "ORDER BY numAccidents DESC; "
            );
            statement.setInt(1, top);
            ResultSet resultSet = statement.executeQuery();
            System.out.printf("Beat of occurrence with the most crashes:\n");
            int rowNum = 1;
            while (resultSet.next()) {
                System.out.printf("%d. Beat of Occurence: %-5d Total Accidents: %d%n", rowNum,
                resultSet.getInt("beatOfOccurence"), resultSet.getInt("numAccidents"));
                rowNum++;
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Unable To Execute: Please Populate Database First");
        }
    }

    // Finds streets with the most crashes and highlights the leading cause.
    // Uses partitioning to rank causes of crashes on each street.
    public void mostCrashesStreets(int top) {
        try {

            PreparedStatement statement = connection.prepareStatement(
                "SELECT TOP (?) location.streetName, count(*) as numAccidents " +
                "FROM crashes " +
                "JOIN location ON crashes.locationID = location.locationID " +
                "GROUP BY location.streetName " +
                "ORDER BY numAccidents DESC;"
            );
            statement.setInt(1, top);
            ResultSet resultSet = statement.executeQuery();
            System.out.printf(" Top %d streets with the most crashes\n", top);
            int rowNum = 1;
            while (resultSet.next()) {
                System.out.printf("%d. Street: %-28s Total Crashes: %d%n", rowNum, resultSet.getString("streetName"),
                resultSet.getInt("numAccidents"));
                rowNum++;
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Unable To Execute: Please Populate Database First");
        }
    }

    // Lists the most common driver ages by severity of accidents.
    // Utilizes a query with partitioning to rank results by severity and count.
    public void mostSevereAge(int top) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                "WITH RankedAges AS ( " +
                "SELECT " +
                "injury.mostSevereInjury AS severity, " +
                "driver.age, " +
                "COUNT(*) AS numAccidents, " +
                "ROW_NUMBER() OVER (PARTITION BY injury.mostSevereInjury ORDER BY COUNT(*) DESC) AS rank " +
                "FROM crashes " +
                "JOIN vehicles ON crashes.crashID = vehicles.crashID " +
                "JOIN driver ON vehicles.crashID = driver.crashID " +
                "JOIN injury ON crashes.crashID = injury.crashID " +
                "WHERE driver.age > 0 " +
                "GROUP BY injury.mostSevereInjury, driver.age " +
                ") " +
                "SELECT severity, age, numAccidents " +
                "FROM RankedAges " +
                "WHERE rank <= ? " +
                "ORDER BY severity, rank;"
            );
            statement.setInt(1, top);
            ResultSet resultSet = statement.executeQuery();
            System.out.printf("Top %d common driver age by severity of accidents: %n", top);
            while (resultSet.next()) {
                String severity = resultSet.getString("severity");
                int age = resultSet.getInt("age");
                int numAccidents = resultSet.getInt("numAccidents");
                System.out.printf("Severity: %-25s Total Crashes: %-3d Age: %d%n", severity, numAccidents, age);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Unable To Execute: Please Populate Database First");
        }
    }

    
    /**
     * Retrieves the number of crashes associated with each type of traffic control device.
     * This method executes a SQL query that joins the `Crashes` table with the
     * trafficCondition table to identify the presence of traffic control devices
     * in crashes. The query groups the crashes by trafficControlDevice and
     * counts the number of crashes for each type of device. The results are
     * ordered in descending order of crash counts.
     *
     * The method prints the name of each traffic control device and the total
     * number of crashes where it was present
     */
    public void trafficCrash() {
        try {

            PreparedStatement statement = connection.prepareStatement(
                "SELECT trafficControlDevice, COUNT(*) AS numCrashes " + 
                "FROM Crashes " + 
                "JOIN trafficCondition ON Crashes.crashID = trafficCondition.crashID " + 
                "GROUP BY trafficControlDevice " + 
                "ORDER BY numCrashes DESC;"        
            );
            ResultSet resultSet = statement.executeQuery();
            System.out.printf("Number of crashes occured by the presence of each traffic control device:\n");
            int rowNum = 1;
            while (resultSet.next()) {
                System.out.printf("%-2d. Traffic Control Device: %-22s Total Crashes: %s%n",rowNum,
                resultSet.getString("trafficControlDevice"), resultSet.getString("numCrashes"));
                rowNum++;  
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Unable To Execute: Please Populate Database First");
        }
    }

    /*Retrieve number of injuries associated with vehicle defect in all accidents 
    * This method executes a SQL query that joins the `vehicles` table with the
    * crashes table to identify the presence of vehicles in the crashes
    * Then merging with injury to identify injuries occured in the crashes 
    * The query finally groups the merge by vehicle defect and
    * counts the number of injuries for each type of vehicle defect. The results are
    * ordered in descending order of injury total.
    */
    public void carDefectToInjury() {
        try {
            PreparedStatement statement = connection.prepareStatement(
                "SELECT vehicleDefect, SUM(injury.totalInjuries) AS totalInjured " + 
                "FROM Vehicles " + 
                "JOIN Crashes ON Vehicles.crashID = Crashes.crashID " + 
                "JOIN Injury ON Crashes.crashID= Injury.crashID " +
                "WHERE vehicleDefect IS NOT NULL " + 
                "GROUP BY vehicleDefect " + 
                "ORDER BY totalInjured DESC;"  
            );
            ResultSet resultSet = statement.executeQuery();
            System.out.printf("Vehicle defects and the total number of injuries in the accidents\n");
            int rowNum = 1;
            while (resultSet.next()) {
                System.out.printf("%d. Defect: %-17s Total Injuries: %d%n",rowNum,
                resultSet.getString("vehicleDefect"), resultSet.getInt("totalInjured"));
                rowNum++;
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Unable To Execute: Please Populate Database First");
        }
    }

    // This method executes a SQL query that joins the crashes table with the
    // roadCondition table to connect crashes with road defects. The query
    //  groups results by roadDefect and counts the number of crashes for each
    //  defect. The results are then ordered in descending order of crash count.
    public void roadDefectToCrash() {
        try {
            PreparedStatement statement = connection.prepareStatement(
                "SELECT roadDefect, COUNT(*) AS NumCrashes " + 
                "FROM crashes " + 
                "JOIN roadCondition ON crashes.crashID = roadCondition.crashID " + 
                "GROUP BY roadDefect " + 
                "ORDER BY numCrashes DESC;"
            );
            ResultSet resultSet = statement.executeQuery();
            System.out.printf("Frequency of crashes as compared to road defects\n");
            int rowNum = 1;
            while (resultSet.next()) {
                System.out.printf("%d. Defect: %-17s Total Crashes: %d%n",rowNum,
                resultSet.getString("roadDefect"), resultSet.getInt("NumCrashes"));
                rowNum++;
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Unable To Execute: Please Populate Database First");
        }
    }

    // Lists months with crashes exceeding the overall average.
    // Calculates the average crashes using a CTE and filters months by the threshold.
    public void crashesAboveAverage() {
        try {
            // Calculate the average crashes
            String avgQuery = 
                "WITH MonthlyTotalCrash AS ( " +
                "SELECT crashYear, crashMonth, COUNT(crashID) AS TotalCrashes " +
                "FROM Crashes " +
                "GROUP BY crashYear, crashMonth " +
                ") " +
                "SELECT AVG(TotalCrashes) AS AverageCrashes FROM MonthlyTotalCrash;";
        
            PreparedStatement avgStmt = connection.prepareStatement(avgQuery);
            double avg = 0;
        
            ResultSet avgResult = avgStmt.executeQuery();
            if (avgResult.next()) {
                avg = avgResult.getDouble("AverageCrashes");
            }
            avgResult.close();
            avgStmt.close();
        
            // Getmonths with total crashes greater than the average
            String mainQuery = 
                "WITH MonthlyTotalCrash AS ( " +
                "SELECT crashYear, crashMonth, COUNT(crashID) AS TotalCrashes " +
                "FROM Crashes " +
                "GROUP BY crashYear, crashMonth " +
                ") " +
                "SELECT crashYear, crashMonth, TotalCrashes " + 
                "FROM MonthlyTotalCrash " +
                "WHERE TotalCrashes > ? " + 
                "ORDER BY TotalCrashes DESC;";
        
            PreparedStatement mainStmt = connection.prepareStatement(mainQuery);
            mainStmt.setDouble(1, avg); 
        
            ResultSet resultSet = mainStmt.executeQuery();
            System.out.printf("Months with total crashes greater than %.0f average crashes:\n", avg);
            while (resultSet.next()) {
                System.out.printf(
                    "Year: %d   Month: %-2d   Total Crashes: %d\n",
                    resultSet.getInt("crashYear"),
                    resultSet.getInt("crashMonth"),
                    resultSet.getInt("TotalCrashes")
                );
            }
            resultSet.close();
            mainStmt.close();
        } catch (SQLException e) {
            System.out.println("Unable To Execute: Please Populate Database First");
        }     
    }

    // Lists crashes with more than a specified number of people and vehicles involved.
    // Joins people and vehicle counts using CTEs and filters by input thresholds.
    public void severeCrashes(int numPeople, int numVehicles) {
        try {

            PreparedStatement statement = connection.prepareStatement(
                "WITH numPeopleInCrash AS (" +
                "SELECT crashID, COUNT(*) AS numPeople FROM (" +
                "SELECT crashID, personID FROM driver " +
                "UNION ALL " +
                "SELECT crashID, personID FROM passenger " +
                "UNION ALL " +
                "SELECT crashID, personID FROM pedestrian " +
                "UNION ALL " +
                "SELECT crashID, personID FROM cyclist" +
                ") AS allPeople " +
                "GROUP BY crashID " +
                "HAVING COUNT(*) >= ?" +
                "), " +
                "numVehiclesInCrash AS (" +
                "SELECT crashes.crashID, COUNT(*) AS numVehicles " +
                "FROM crashes " +
                "JOIN vehicles ON crashes.crashID = vehicles.crashID " +
                "GROUP BY crashes.crashID " +
                "HAVING COUNT(*) >= ?" +
                ") " +
                "SELECT npc.crashID, npc.numPeople, nvc.numVehicles " +
                "FROM numPeopleInCrash npc " +
                "JOIN numVehiclesInCrash nvc ON npc.crashID = nvc.crashID;"
            );
            statement.setInt(1, numPeople);
            statement.setInt(2, numVehicles);
            ResultSet resultSet = statement.executeQuery();
            System.out.printf("Crashes having more than %d people and more than %d vehicles\n", numPeople, numVehicles);
            int rowNum = 1;
            while (resultSet.next()) {
                String crashID = resultSet.getString("crashID");
                int noPeople = resultSet.getInt("numPeople");
                int noVehicles = resultSet.getInt("numVehicles");
                System.out.printf("%d. Crash ID: %s, Number of People: %d, Number of Vehicles: %d\n", rowNum, crashID, noPeople, noVehicles);
                rowNum++;
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Unable To Execute: Please Populate Database First");
        }
    }

    // Lists top locations with the most crashes, related injuries and leading cause.
    // Utilizes a CTE (Common Table Expression) and a ranking query.
    public void locationsWithTopCrashes(int top) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                "WITH a AS ( " +
                "SELECT Location.streetName, " +
                "COUNT(*) AS numCrashes, " +
                "COUNT(totalInjuries) AS numInjuries " + 
                "FROM Crashes " +
                "JOIN Location ON Crashes.locationID = Location.locationID " +
                "JOIN Injury ON Injury.crashID = Crashes.crashID " +
                "GROUP BY Location.streetName), " +
                "causeCount AS ( " +
                "SELECT Location.streetName, " +
                "crashFactor.primaryCause, " +
                "COUNT(*) AS num " +
                "FROM Crashes " +
                "JOIN Location ON Crashes.locationID = Location.locationID " +
                "JOIN crashFactor ON Crashes.crashID = crashFactor.crashID " +
                "WHERE crashFactor.primaryCause NOT IN ('NOT APPLICABLE', 'UNABLE TO DETERMINE') " +
                "GROUP BY Location.streetName, crashFactor.primaryCause), " +
                "leadingCause AS ( " +
                "SELECT streetName, " +
                "primaryCause, " +
                "num, " +
                "ROW_NUMBER() OVER (PARTITION BY streetName ORDER BY num DESC) AS rank " +
                "FROM causeCount) " +
                "SELECT TOP (?) a.streetName, a.numCrashes, a.numInjuries, d.primaryCause " +
                "FROM a " +
                "JOIN (SELECT streetName, primaryCause FROM leadingCause WHERE rank = 1) d " +
                "ON a.streetName = d.streetName " +
                "ORDER BY a.numCrashes DESC, a.numInjuries DESC, a.streetName;"
            );
            statement.setInt(1, top);
            ResultSet resultSet = statement.executeQuery();
            System.out.printf("Top %d street names with the most crashes, most injuries, most leading cause\n", top);
            int rowNum = 1;
            while (resultSet.next()) {
                System.out.printf("%-2d. Street: %-27s Num Crashes: %-1d Num Injuries: %-1s Main Cause: %s%n",rowNum,
                resultSet.getString("streetName"), resultSet.getInt("numCrashes"),
                resultSet.getString("numInjuries"), resultSet.getString("primaryCause"));
                rowNum++;
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Unable To Execute: Please Populate Database First");
        }
    }

    // Populates the database by executing SQL statements from a file.
    // Reads, executes SQL commands and roll back to initial state to ensure data consistency.
    // Used code idea from: https://www.geeksforgeeks.org/how-to-execute-a-sql-script-file-using-jdbc/
    public void loadData() throws IOException, SQLException {
        try {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
           
            //SQL file name
            String filePath = "chicago_crashes.sql";
            BufferedReader br = new BufferedReader(new FileReader(filePath));

            StringBuilder query = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                //Ignore comments in the file
                if (line.trim().startsWith("-- ")) {
                    continue;
                }

                // Append the line into the query string and add a space after that
                query.append(line).append(" ");

                if (line.trim().endsWith(";")) {
                    //Execute appended query until semicolon
                    statement.execute(query.toString().trim());
                    query = new StringBuilder();
                }
            }

            connection.setAutoCommit(true);
            System.out.println("Database is ready!");
            System.out.println("----------------------------------------");
        } catch (SQLException e) {
            System.out.println("Transaction failed");
            try {
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println("Failed to rollback");
            }
        }
    }

    
    // Drops all tables in the database.
    // Executes SQL statements to remove each table and rolls back if any issue occurs.
    public void dropAllTables() {
        try {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.executeUpdate("drop table if exists pedestrian;");
            statement.executeUpdate("drop table if exists passenger;");
            statement.executeUpdate("drop table if exists cyclist;");
            statement.executeUpdate("drop table if exists driver;");
            statement.executeUpdate("drop table if exists vehicles;");
            statement.executeUpdate("drop table if exists damages;");
            statement.executeUpdate("drop table if exists crashFactor;");
            statement.executeUpdate("drop table if exists climateCondition;");
            statement.executeUpdate("drop table if exists trafficCondition;");
            statement.executeUpdate("drop table if exists roadCondition;");
            statement.executeUpdate("drop table if exists injury;");
            statement.executeUpdate("drop table if exists crashes;");
            statement.executeUpdate("drop table if exists location;");
            connection.setAutoCommit(true);
            System.out.println("All tables have been removed from database");
        } catch (SQLException e) {
            System.out.println("Transaction failed");
            try {
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println("Failed to rollback");
            }
        }
    }
}
