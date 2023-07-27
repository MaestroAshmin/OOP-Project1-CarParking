import java.util.ArrayList;
import java.util.List;

public class CarPark {

    private int totalStaffSlot = 0;
    private int totalVisitorSlot = 0;
    private List<ParkingSlot> parkingSlots;
    private List<ParkingSlot> visitorParkingSlots;

    // To set the total slots for staff and for visitors
    public void setTotalSlots(int totalStaffSlot, int totalVisitorSlot) {
        this.totalStaffSlot = totalStaffSlot;
        this.totalVisitorSlot = totalVisitorSlot;

        parkingSlots = new ArrayList<>(totalStaffSlot);
        visitorParkingSlots = new ArrayList<>(totalVisitorSlot);
    }

    // To get all parking slot including both visitors and staffs
    public List<ParkingSlot> getParkingSlots() {
        List<ParkingSlot> allParkingSlots = new ArrayList<>();
        allParkingSlots.addAll(parkingSlots);
        allParkingSlots.addAll(visitorParkingSlots);

        return allParkingSlots;
    }

    // Get first avaialble slot according for staff or for visitor
    public ParkingSlot getFirstAvailableSlot(boolean isStaff) {
        List<ParkingSlot> allParkingSlots = getParkingSlots();
        for (ParkingSlot slot : allParkingSlots) {
            if (isStaff) {
                if (!slot.isOccupied() && slot.isStaff()) {
                    return slot;
                }
            } else {
                if (!slot.isOccupied() && !slot.isStaff()) {
                    return slot;
                }
            }
        }
        return null;
    }

    // To check if the slot is full 
    public boolean isFull(String toWhom) {
        if (toWhom == "staff") {
            return parkingSlots.size() == totalStaffSlot;
        } else {
            return visitorParkingSlots.size() == totalVisitorSlot;
        }
    }

    // To add new slots for visitor or for staff
    public void addSlot(ParkingSlot slot, String toWhom) {
        if (toWhom == "staff") {
            parkingSlots.add(slot);
        } else {
            visitorParkingSlots.add(slot);
        }
    }

    // To delete slot by slot name
    public String deleteParkingSlot(String identifier) {
        String whatHappened = "notfound";
        for (ParkingSlot parkingSlot : parkingSlots) {
            if (parkingSlot.getIdentifier().equals(identifier)) {
                if (!parkingSlot.isOccupied()) {
                    parkingSlots.remove(parkingSlot);
                    whatHappened = "deleted";
                } else {
                    whatHappened = "occupied";
                }
                break;
            }
        }
        for (ParkingSlot parkingSlot : visitorParkingSlots) {
            if (parkingSlot.getIdentifier().equals(identifier)) {
                if (!parkingSlot.isOccupied()) {
                    visitorParkingSlots.remove(parkingSlot);
                    whatHappened = "deleted";
                } else {
                    whatHappened = "occupied";
                }
                break;

            }
        }
        return whatHappened;
    }

    // Returns a list of all unoccupied parking slots in the car park
    public List<ParkingSlot> getAllUnoccupiedSlots() {
        List<ParkingSlot> unoccupiedSlots = new ArrayList<ParkingSlot>();
        List<ParkingSlot> allParkingSlots = getParkingSlots();
        for (ParkingSlot slot : allParkingSlots) {
            if (!slot.isOccupied()) {
                unoccupiedSlots.add(slot);
            }
        }
        return unoccupiedSlots;
    }

    // To unpark a car
    public boolean unparkCar(String registrationNumber) {
        List<ParkingSlot> allParkingSlots = getParkingSlots();
        for (ParkingSlot slot : allParkingSlots) {
            if (slot.isOccupied() && slot.getParkedCar().getRegistrationNumber().equals(registrationNumber)) {
                slot.parkCar(null);
                return true;
            }
        }
        return false;
    }

    // To get car by registration number with slot is already parked in
    public ParkingSlot findCarByRegistrationNumber(String registrationNumber) {
        List<ParkingSlot> allParkingSlots = getParkingSlots();
        for (ParkingSlot slot : allParkingSlots) {
            if (slot.isOccupied() && slot.getParkedCar().getRegistrationNumber().equals(registrationNumber)) {
                return slot;
            }
        }
        return null;
    }

    // To remove/delete car by registration number
    public boolean removeCarByRegNumber(String regNumber) {
        List<ParkingSlot> allParkingSlots = getParkingSlots();
        for (ParkingSlot slot : allParkingSlots) {
            if (slot.isOccupied() && slot.getParkedCar().getRegistrationNumber().equals(regNumber)) {
                slot.parkCar(null);
                return true;
            }
        }
        return false;
    }
}
