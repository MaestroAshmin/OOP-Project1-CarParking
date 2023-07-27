import java.util.Scanner;
import java.time.LocalDateTime;

public class Application {

    //Creating the carPark and initialize it with new instance of CarPark class
    private static CarPark carPark = new CarPark();

    public static void main(String[] args) {
        System.out.println("Welcome to the Car Park Management System!");
        Scanner scanner = new Scanner(System.in);

        // Variables to store the no of slots for the staff and for the visitors
        int staffSlots = 0;
        int visitorSlots = 0;

        // Taking user input to set the total no of parking slot for staff
        while (true) {
            System.out.print("Enter the number of staff parking slots: ");
            String input = scanner.nextLine();

            try {
                int slots = Integer.parseInt(input);
                if (slots > 0) {
                    staffSlots = slots;
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a number greater than 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        // Taking user input to set the total no of parking slot for visitor
        while (true) {
            System.out.print("Enter the number of visitor parking slots: ");
            String input = scanner.nextLine();

            try {
                int slots = Integer.parseInt(input);
                if (slots > 0) {
                    visitorSlots = slots;
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a number greater than 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        // To set total slots for staffs and for visitors
        carPark.setTotalSlots(staffSlots, visitorSlots);

        //To display the menu
        while (true) {
            System.out.println();
            System.out.println("***************** MENU *****************");
            System.out.println("Enter 1 to list all parking slots.");
            System.out.println("Enter 2 to add a parking slot.");
            System.out.println("Enter 3 to delete a parking slot.");
            System.out.println("Enter 4 to get all unoccupied slots");
            System.out.println("Enter 5 to delete all unoccupied slots");
            System.out.println("Enter 6 to park a car to a parking slot.");
            System.out.println("Enter 7 to get car by registration number.");
            System.out.println("Enter 8 to remove a car by registration number.");
            System.out.println("Enter 0 to exit.");

            try {
                int option = Integer.parseInt(scanner.next());

                switch (option) {
                    case 1:
                        getAllParkingSlots();
                        break;
                    case 2:
                        addSlot();
                        break;
                    case 3:
                        deleteSlot();
                        break;
                    case 4:
                        getAllUnoccupiedSpace();
                        break;
                    case 5:
                        deleteAllUnoccupiedSpace();
                        break;
                    case 6:
                        parkCar();
                        break;
                    case 7:
                        getCar();
                        break;
                    case 8:
                        removeCar();
                        break;
                    case 0:
                        System.out.println("Goodbye!");
                        System.exit(0);
                    default:
                        System.out.println("Invalid command.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    // To get all the parking slots
    private static void getAllParkingSlots() {
        System.out.println("Slot ID\tSlot Type\tOccupied\tCar Reg No.\tOwner\tParked Time");

        for (ParkingSlot slot : carPark.getParkingSlots()) {
            String slotType = slot.isStaff() ? "Staff" : "Visitor";
            String occupied = (slot.isOccupied()) ? "Yes" : "No";
            String carRegNo = "-";
            String owner = "-";
            String parkedTime = "-";

            if (slot.isOccupied()) {
                Car car = slot.getParkedCar();
                carRegNo = car.getRegistrationNumber();
                owner = car.getOwnerName();
                parkedTime = car.getParkingTimeLength();
            }
            System.out.printf("%s\t%s\t\t%s\t\t%s\t\t%s\t%s%n", slot.getIdentifier(), slotType, occupied, carRegNo, owner, parkedTime);
        }
    }

    // To add new parking slot    
    private static void addSlot() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter slot identifier: ");
        String identifier = scanner.nextLine();

        try {
            // validate that identifier starts with a capital letter followed by a two-digit number
            if (!identifier.matches("^[A-Z]\\d{2}$")) {
                throw new IllegalArgumentException("Invalid identifier. Identifier should start with a capital letter followed by a two-digit number.e.g,'D01', 'E01'");
            }

            boolean exists = carPark.getParkingSlots().stream().anyMatch(slot -> slot.getIdentifier().equals(identifier));
            if (exists) {
                throw new IllegalArgumentException("Slot identifier already exists. Please provide a unique identifier.");
            }

            System.out.print("Enter slot type (visitor/staff): ");
            String type = scanner.nextLine();

            if (type == null) {
                throw new IllegalArgumentException("Slot cannot be null or have empty identifier.");
            }

            if (type.equals("visitor")) {
                if (carPark.isFull("visitor")) {
                    System.out.println("Maximum slot capacity reached for visitor");
                } else {
                    ParkingSlot slot = new ParkingSlot(identifier, false);
                    carPark.addSlot(slot, "visitor");
                    System.out.println("Visitor slot " + identifier + " added for visitor");
                }
            } else if (type.equals("staff")) {
                if (carPark.isFull("staff")) {
                    System.out.println("Maximum slot capacity reached for staff");
                } else {
                    ParkingSlot slot = new ParkingSlot(identifier, true);
                    carPark.addSlot(slot, "staff");
                    System.out.println("Staff slot " + identifier + " added for staff");
                }
            } else {
                System.out.println("Invalid slot type.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // To remove parking slot    
    private static void deleteSlot() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter slot identifier: ");
        String identifier = scanner.nextLine();

        if (carPark.deleteParkingSlot(identifier) == "deleted") {
            System.out.println("Slot of identifier " + identifier + " is deleted.");
        } else if (carPark.deleteParkingSlot(identifier) == "occupied") {
            System.out.println("Slot of identifier " + identifier + " is occupied.");
        } else {
            System.out.println("Slot of identifier " + identifier + " not found.");
        }
    }

    // To get all the unoccupied parking slots
    private static void getAllUnoccupiedSpace() {
        System.out.println("Slot ID\tSlot Type\t");
        for (ParkingSlot slot : carPark.getAllUnoccupiedSlots()) {
            String slotType = slot.isStaff() ? "Staff" : "Visitor";
            System.out.printf("%s\t%s\t%n", slot.getIdentifier(), slotType);
        }
    }

    // To delete all unoccupied parking slots
    private static void deleteAllUnoccupiedSpace() {
        int total=0;
        for (ParkingSlot slot : carPark.getAllUnoccupiedSlots()) {
            String whatHappened=carPark.deleteParkingSlot(slot.getIdentifier());
            if(whatHappened =="deleted")
                total++;
        }
        if(total>0)
            System.out.println(total+" unoccupied slot removed successfully.");
        else
            System.out.println("No unoccupied slot available to removed.");
    }

    // To part a car
    private static void parkCar() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter car registration number: ");
        String registrationNumber = scanner.nextLine().toUpperCase();

        try {
            // validate that identifier starts with a capital letter followed by a two-digit number
            if (!registrationNumber.matches("^[A-Z]\\d{4}$")) {
                throw new IllegalArgumentException("Invalid identifier. Identifier should start with a capital letter followed by a four-digit number. e.g. “T2345”, “G2345”");
            }
            System.out.print("Is the car owned by a staff member? (y/n): ");
            boolean isStaff = scanner.nextLine().equals("y");

            Car car;
            System.out.print("Enter car owner name: ");
            String staffName = scanner.nextLine();
            
            //Checking If car has already been parked in a slot
            ParkingSlot parkedSlot = carPark.findCarByRegistrationNumber(registrationNumber);
            if (parkedSlot == null) {
                if (isStaff) {
                car = new Car(registrationNumber, staffName, true);
                } else {
                 car = new Car(registrationNumber, staffName, false);
                }

                // Set the parked time to the current time
                LocalDateTime parkedTime = LocalDateTime.now();
                car.setParkedTime(parkedTime);
    
                ParkingSlot slot = carPark.getFirstAvailableSlot(isStaff);
                if (slot == null) {
                    System.out.println("Sorry, there are no available slots.");
                } else {
                    slot.parkCar(car);
                    System.out.println("Car parked in slot " + slot.getIdentifier() + " at " + car.getParkedTime());
                }                
            } else {
                System.out.println("Car with registration number " + registrationNumber + " is already parked in slot " + parkedSlot.getIdentifier() + " and is owned by " + parkedSlot.getParkedCar().getOwnerName());
                System.out.println("Parking time: " + parkedSlot.getParkedCar().getParkingTimeLength());
            }

            
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // To get a car by registration number
    public static void getCar() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter registration number of car: ");
        String registrationNumber = scanner.next().toUpperCase();

        ParkingSlot slot = carPark.findCarByRegistrationNumber(registrationNumber);

        if (slot == null) {
            System.out.println("Car with registration number " + registrationNumber + " is not parked in the CarPark.");
        } else {
            System.out.println("Car with registration number " + registrationNumber + " is parked in slot " + slot.getIdentifier() + " and is owned by " + slot.getParkedCar().getOwnerName());
            System.out.println("Parking time: " + slot.getParkedCar().getParkingTimeLength());
        }
    }

    // Remove car by registation number 
    private static void removeCar() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the registration number of the car to remove:");
        String registrationNumber = scanner.nextLine();
        boolean success = carPark.removeCarByRegNumber(registrationNumber);
        if (success) {
            System.out.println("Car with registration number " + registrationNumber + " has been removed.");
        } else {
            System.out.println("No car with registration number " + registrationNumber + " found in the car park.");
        }
    }
}
