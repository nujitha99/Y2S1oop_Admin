package com.nujitha.project.classes;

import com.nujitha.project.firebaseUtils.FirebaseUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;


public class WestminsterRentalVehicleManager implements RentalVehicleManager {
    private static final int maxLots = 50; //specifies the maximum allowance in the vehicleCollection arrayList
    private static Scanner sc = new Scanner(System.in);
    private boolean availability = true; //default status of a vehicle if not mentioned
    private List<Vehicle> vehicleCollection = new ArrayList<>(); //stores all the vehicles store in the database
    private List<Schedule> bookedVehicles = new ArrayList<>();  //stores all the details of bookings made
    private List<Object> bookingReferenceList = new ArrayList<>();  //reference numbers of the bookings currently made

    public WestminsterRentalVehicleManager() {
        try {
            FirebaseUtil.setupFirebase();   //setting-up database at the beginning
            setVehicleCollection(FirebaseUtil.getToArray());    //retrieve all vehicle information
            setBookedVehicles(FirebaseUtil.bookingObjects());   //retrieve all bookings
            setBookingReferenceList(FirebaseUtil.bookingReferences());  //retrieve all booking reference numbers
            //booting-up the angular server
            File file = new File("/personal/IIT/year02/OOP/coursework01/CustomerInterface/customer-gui");
            Process pb = new ProcessBuilder("ng", "serve").directory(file).start();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Object> getBookingReferenceList() {
        return bookingReferenceList;
    }

    public void setBookingReferenceList(List bookingReferenceList) {
        this.bookingReferenceList = bookingReferenceList;
    }

    public List<Schedule> getBookedVehicles() {
        return bookedVehicles;
    }

    public void setBookedVehicles(List bookedVehicles) {
        this.bookedVehicles = bookedVehicles;
    }

    public List<Vehicle> getVehicleCollection() {
        return vehicleCollection;
    }

    public void setVehicleCollection(List<Vehicle> vehicleCollection) {
        this.vehicleCollection = vehicleCollection;
    }

    //method to add vehicle objects to the store
    @Override
    public void addVehicle() throws ExecutionException, InterruptedException, IOException {
        int option; //preference on cases
        if (isCapacityAvailable()) {    //validating that the maximum lot capacity allowance will not be exceeded
            do {
                System.out.println("Select your preference : " +
                        "\n 1) Add a Car" +
                        "\n 2) Add a Motorbike");
                isIntegerInput();
                option = sc.nextInt();
            } while (option > 2 || option < 1);

            //prompting for user inputs

            System.out.println("Please enter following information : ");
            System.out.println("Plate Number => ");
            String plateNo = sc.next();

            System.out.println("Make => ");
            String make = sc.next();

            System.out.println("Model => ");
            String model = sc.next();

            System.out.println("ODO meter(km) => ");
            isIntegerInput();
            int odo = sc.nextInt();

            switch (option) {
                case 1: //case of cars
                    System.out.println("Number of doors => ");
                    isIntegerInput();
                    int doors = sc.nextInt();

                    System.out.println("Is it hybrid (true/false) => ");
                    isBooleanInput();
                    boolean isHybrid = sc.nextBoolean();

                    //creating the object
                    Car car = new Car(plateNo, make, model, odo, availability, doors, isHybrid);
                    //check on duplicate entries
                    checkDuplicates(car);

                    //adding details to the stores
                    vehicleCollection.add(car);
                    FirebaseUtil.addVehicle(car);

                    System.out.println("\nVehicle added successfully..!!\n");

                    break;

                case 2: //case of motorbikes
                    System.out.println("Is the helmet given? (true/false) => ");
                    isBooleanInput();
                    boolean isHelmetGiven = sc.nextBoolean();

                    System.out.println("Type of bike (scooter/regular) => ");
                    String bikeType = sc.next();

                    //creating the object
                    Motorbike motorbike = new Motorbike(plateNo, make, model, odo, availability, isHelmetGiven, bikeType);
                    //check on duplicate entries
                    checkDuplicates(motorbike);

                    //adding details to the stores
                    vehicleCollection.add(motorbike);
                    FirebaseUtil.addVehicle(motorbike);

                    System.out.println("\nVehicle added successfully..!!\n");

                    break;
            }
        } else {
            runMenu();
        }

    }

    //method to remove data from the store
    @Override
    public void removeVehicle() throws ExecutionException, InterruptedException {
        boolean existFlag = false;  //to confirm that the vehicle exitst after iterating through the list
        Vehicle obj = null;
        System.out.println("Enter the Plate Number of the vehicle you want to delete : ");  //prompting for plate number
        String removePlateNo = sc.next();
        //iterating through the list and finding the matching vehicle with the plate number
        for (Vehicle currVehicle : vehicleCollection) {
            if (currVehicle.getPlateNo().equals(removePlateNo)) {
                obj = currVehicle;
                existFlag = true;
                break;
            }
        }
        //if vehicle exists
        if (existFlag) {
            //display details of the vehicle before deleting
            searchVehicle(removePlateNo);
            //removing vehicle from the store
            vehicleCollection.remove(obj);
            FirebaseUtil.deleteFirestore(removePlateNo);
            System.out.println("Vehicle removed successfully..!!");
        } else {
            System.out.println("Sorry, couldn't find a vehicle with that plate number..!\n");
        }

        //notifies the remaining number of lots that can be added
        isCapacityAvailable();

    }

    //method to print the list of all the vehicles in the store
    @Override
    public void printList() throws ExecutionException, InterruptedException {
        int prefer; //viewing preference case by the user as in filtered
        do {
            System.out.println(" 1) View cars only" +
                    "\n 2) View Motorbikes only" +
                    "\n 3) View all");

            isIntegerInput();
            prefer = sc.nextInt();
        } while (prefer > 3 || prefer < 1); //validating input range
        Collections.sort(vehicleCollection);    //sorting accordingly to the 'make' of the vehicle
        //do case of preference
        switch (prefer) {
            case 1: //case of viewing only cars
                System.out.println("PlateNo   Make\n______________");
                for (Vehicle currVehicle : vehicleCollection) {
                    if (currVehicle.getVehicleType().equals("Car")) {
                        System.out.println(currVehicle.getPlateNo() + "   " + currVehicle.getMake());
                    }
                }
                System.out.println();
                break;

            case 2: //case of viewing only motorbikes
                System.out.println("PlateNo   Make\n______________");
                for (Vehicle currVehicle : vehicleCollection) {
                    if (currVehicle.getVehicleType().equals("Motorbike")) {
                        System.out.println(currVehicle.getPlateNo() + "   " + currVehicle.getMake());
                    }
                }
                System.out.println();
                break;

            case 3: //case of viewing all the vehicles regardless of any filtering
                System.out.println("PlateNo   Make    Type\n______________________");
                for (Vehicle currVehicle : vehicleCollection) {
                    System.out.println(currVehicle.getPlateNo() + "   " + currVehicle.getMake() + "   " + currVehicle.getVehicleType());
                }
                System.out.println();
        }
    }

    //initial menu to be run
    @Override
    public void runMenu() throws ExecutionException, InterruptedException, IOException {
        int choice; //choice selection from the menu
        do {
            System.out.println("\n________Welcome to Westminster Vehicle Rental________\n" +
                    "\n 1) Add a vehicle" +
                    "\n 2) Delete a vehicle" +
                    "\n 3) View list of vehicles" +
                    "\n 4) View booked vehicles" +
                    "\n 5) Search a vehicle" +
                    "\n 6) Save" +
                    "\n 7) Delete booking" +
                    "\n 8) Open GUI" +
                    "\n 9) Exit the program" +
                    "\nEnter here : ");

            isIntegerInput();
            choice = sc.nextInt();
        } while (choice > 9 || choice < 1); //validating input ranges

        switch (choice) {
            case 1: //case of adding a vehicle
                addVehicle();
                runMenu();
            case 2: //case of deleting a vehicle
                removeVehicle();
                runMenu();
            case 3: //case of printing the vehicle
                printList();
                runMenu();
            case 4: //case of viewing all the booked details
                bookedVehicles();
                runMenu();
            case 5: //case of searching and viewing the particular vehicle details
                System.out.println("Please enter plate number : ");
                String findThisPlateNo = sc.next();
                searchVehicle(findThisPlateNo);
                runMenu();
            case 6: //case of writing of the vehicle datils into a text file
                save();
                runMenu();
            case 7: //case of deleting a booking
                removeBooking();
                runMenu();
            case 8: //case of opening the angular GUI
                openGUI();
                System.out.println("Web page opened in browser");
                runMenu();
            case 9: //case of ending the project application
                System.out.println("[[ Shutting down the servers ]]");
                System.out.println("\n ________Thank You________\n");
                System.exit(0);
                break;
            default:
                System.out.println("Please check again..!!");
        }
    }

    //method to open the GUI in the browser
    private void openGUI() throws IOException {
        Runtime.getRuntime().exec(new String[]{"/usr/bin/open", "-a", "/Applications/Google Chrome.app", "http://localhost:4200/"});

    }

    //method to write the vehicle information into a txt file
    @Override
    public void save() {
        try {
            FileWriter fileWriter = new FileWriter("Vehicles.txt");
            //iterating through the vehicleCollection list and printing each object in the file line by line
            for (Vehicle vehicle :
                    vehicleCollection) {
                fileWriter.write(vehicle.toString() + "\n");
            }
            System.out.println("\nSaved Successfully..!");
            //closing the file
            fileWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //method to view all the booking details
    private void bookedVehicles() throws ExecutionException, InterruptedException {
        System.out.println("PlateNo   Pick-up       Drop-off      Customer_Name\n___________________________________________________");
        for (Schedule currVehicle : bookedVehicles) {
            System.out.println(currVehicle.toString());
        }
        System.out.println();

    }

    //method to remove a booking with the matching reference number
    public void removeBooking() throws ExecutionException, InterruptedException {
        boolean hasBooking = false;     //validate if a matching booking exists with the reference
        Vehicle vehicleObj = null;
        Schedule bookingObj = null;
        System.out.println("Enter the reference number of the vehicle you want to delete : ");
        String removeRefNo = sc.next();
        System.out.println("Enter the Plate Number of the vehicle : ");
        String updatePlateNo = sc.next();
        //searching for the reference number
        for (Object currRef : bookingReferenceList) {
            if (currRef.equals(removeRefNo)) {
                hasBooking = true;
                break;
            }
        }
        //searching for the vehicle
        for (Vehicle currVehicle : vehicleCollection) {
            if (currVehicle.getPlateNo().equals(updatePlateNo)) {
                vehicleObj = currVehicle;
                break;
            }
        }
        for (Schedule currBooking : bookedVehicles) {
            if (currBooking.getPlateNo().equals(updatePlateNo)) {
                bookingObj = currBooking;
                break;
            }
        }
        if (hasBooking) {
            //update vehicle status
            int vehicleIndex = vehicleCollection.indexOf(vehicleObj);
            vehicleCollection.get(vehicleIndex).setAvailability(true);
            //remove booking from the stores
            bookedVehicles.remove(bookingObj);
            bookingReferenceList.remove(removeRefNo);
            FirebaseUtil.deleteBooking(removeRefNo, updatePlateNo);
            System.out.println("Booking removed successfully..!");
        } else {
            System.out.println("Sorry, couldn't find a reference matching..!\n");
        }
    }

    //method to search a particular vehicle
    public boolean searchVehicle(String findPlateNo) throws ExecutionException, InterruptedException {
        boolean foundFlag = false;  //flag if the vehicle do exists
        for (Vehicle currVehicle : vehicleCollection) {
            if (currVehicle.getPlateNo().equals(findPlateNo)) {
                //printing vehicle object's details
                System.out.println("Plate number    -> " + currVehicle.getPlateNo());
                System.out.println("Make            -> " + currVehicle.getMake());
                System.out.println("Model           -> " + currVehicle.getModel());
                System.out.println("ODO meter(km)   -> " + currVehicle.getOdoMeter());
                System.out.println("Type            -> " + currVehicle.getVehicleType());
                System.out.println();
                foundFlag = true;
                break;
            }
        }
        //in case if couldn't find the vehicle
        if (!foundFlag) {
            System.out.println("Sorry, the vehicle doesn't exist..!");
        }
        return foundFlag;
    }

    //to ensure that the store capacity will not be exceeded to the given limit
    public boolean isCapacityAvailable() {
        int dbCount = vehicleCollection.size();
        if (dbCount >= maxLots) {
            System.out.println("Sorry, the lots are almost full..!!");
            return false;
        } else {
            System.out.println("\nYou can add " + (maxLots - dbCount) + " more vehicles.\n");
            return true;
        }
    }

    //to check the arrayList for duplicate entries before adding it
    public boolean checkDuplicates(Vehicle obj) throws ExecutionException, InterruptedException, IOException {
        boolean duplicateFlag = false;
        if (this.vehicleCollection.contains(obj)) {
            System.out.println("The vehicle already exists that you have added earlier. Please add another.");
            duplicateFlag = true;
        }
        return duplicateFlag;
    }

    //to ensure the user input is an integer input, else re-prompt
    public void isIntegerInput() {
        while (!sc.hasNextInt()) {
            System.out.println("Wrong input, please enter a valid integer : ");
            sc.next();
        }
    }

    //to ensure the user input is a boolean true/ false input, else re-prompt
    public void isBooleanInput() {
        while (!sc.hasNextBoolean()) {
            System.out.println("Wrong input, please enter true / false : ");
            sc.next();
        }
    }
}
