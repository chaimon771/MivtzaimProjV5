package example.haim.mivtzaimprojv5.moduls;

/**
 * Created by DELL e7440 on 10/07/2017.
 */

public class Profile  {
    //Properties
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String weekDay;
    private String hour;
    private boolean profileApplied = false;

    //Empty Constructor
    public Profile() {
    }
    //Full Constructor:
    public Profile(String firstName, String lastName, String phoneNumber,
                   String address, String weekDay, String hour, boolean profileApplied) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.weekDay = weekDay;
        this.hour = hour;
        this.profileApplied = profileApplied;
    }


    //Getters n Setters:
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getWeekDay() {
        return weekDay;
    }
    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }
    public String getHour() {
        return hour;
    }
    public void setHour(String hour) {
        this.hour = hour;
    }
    public boolean isProfileApplied() {
        return profileApplied;
    }
    public void setProfileApplied(boolean profileApplied) {
        this.profileApplied = profileApplied;
    }

    //toString
    @Override
    public String toString() {
        return "Profile{" +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", weekDay='" + weekDay + '\'' +
                ", hour='" + hour + '\'' +
                ", profileApplied=" + profileApplied +
                '}';
    }

}
