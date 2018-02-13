package example.haim.mivtzaimprojv5.moduls;

/**
 * Created by DELL e7440 on 07/08/2017.
 */

public class Person {
    private String name;
    private String phoneNumber;
    private String PersonImage;

    //Ctors
    public Person() {
    }

    public Person(String name, String phoneNumber, String personImage) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        PersonImage = personImage;
    }

    //Getters And Setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getPersonImage() {
        return PersonImage;
    }
    public void setPersonImage(String personImage) {
        PersonImage = personImage;
    }

    //toString
    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", PersonImage='" + PersonImage + '\'' +
                '}';
    }
}
