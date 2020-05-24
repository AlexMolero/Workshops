public class Date {
    private int hour;
    private int day;

    Date(){

    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "Date{" +
                "hour=" + hour +
                ", day=" + day +
                '}';
    }

}
