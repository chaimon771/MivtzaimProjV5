package example.haim.mivtzaimprojv5.moduls;

/**
 * Created by DELL e7440 on 07/08/2017.
 */

public class Action {

    //properties:

    private int numOfHanachot;
    private String hour;
    private String minutes;
    private String date;
    private int periodMinutes;
    private int periodHours;

    //Ctors
    public Action() {
    }

    public Action(int numOfHanachot, String hour, String minutes, String date, int periodMinutes, int periodHours) {
        this.numOfHanachot = numOfHanachot;
        this.hour = hour;
        this.minutes = minutes;
        this.date = date;
        this.periodMinutes = periodMinutes;
        this.periodHours = periodHours;
    }


    //Getters n Setters
    public int getNumOfHanachot() {
        return numOfHanachot;
    }

    public void setNumOfHanachot(int numOfHanachot) {
        this.numOfHanachot = numOfHanachot;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPeriodMinutes() {
        return periodMinutes;
    }

    public void setPeriodMinutes(int periodMinutes) {
        this.periodMinutes = periodMinutes;
    }

    public int getPeriodHours() {
        return periodHours;
    }

    public void setPeriodHours(int periodHours) {
        this.periodHours = periodHours;
    }

    //tostring
    @Override
    public String toString() {
        return "Action{" +
                "numOfHanachot=" + numOfHanachot +
                ", hour='" + hour + '\'' +
                ", minutes='" + minutes + '\'' +
                ", date=" + date +
                ", periodMinutes='" + periodMinutes + '\'' +
                ", periodHours='" + periodHours + '\'' +
                '}';
    }
}
