public class ParkingSlot {

    private String identifier;
    private boolean isStaff;
    private Car parkedCar;

    public ParkingSlot(String identifier, boolean isStaff) {
        this.identifier = identifier;
        this.isStaff = isStaff;
        parkedCar = null;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public boolean isStaff() {
        return isStaff;
    }

    public void setStaff(boolean isStaff) {
        this.isStaff = isStaff;
    }

    public Car getParkedCar() {
        return parkedCar;
    }

    public void parkCar(Car car) {
        parkedCar = car;
    }

    public void removeCar() {
        parkedCar = null;
    }

    public boolean isOccupied() {
        return parkedCar != null;
    }
}
