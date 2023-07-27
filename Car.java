import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Car {
    private String registrationNumber;
    private String ownerName;
    private LocalDateTime parkedTime; // New field for parked time
    private boolean isStaff;

    public Car(String registrationNumber, String ownerName, boolean isStaff) {
        this.registrationNumber = registrationNumber;
        this.ownerName = ownerName;
        this.isStaff = isStaff;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public boolean isStaff() {
        return isStaff;
    }

    public void setStaff(boolean isStaff) {
        this.isStaff = isStaff;
    }
    
    public String getParkedTime() { 
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return parkedTime.format(formatter);
    }

    public void setParkedTime(LocalDateTime parkedTime) {
        this.parkedTime = parkedTime;
    }
    
    // To get parking time
    public String getParkingTimeLength() {
        Duration duration = Duration.between(this.parkedTime, LocalDateTime.now());
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();
        return String.format("%d hours %d minutes and %d seconds", hours, minutes, seconds);
    }
}
