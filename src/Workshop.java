import java.util.ArrayList;
import java.util.List;

public class Workshop extends Date{

    private String acronym;
    private float price;
    private int category;
    public int numHorarios;
    List<Date> date = new ArrayList<>();

    public Workshop(int hour, int day, String acronym, float price, int category, int numHorarios, List<Date> date) {
        super(hour, day);
        this.acronym = acronym;
        this.price = price;
        this.category = category;
        this.numHorarios = numHorarios;

        Date date_new = new Date();
        date_new.setDay(day);
        date_new.setHour(hour);
        this.date.add(date_new);
    }

    public Workshop() {

    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getNumHorarios() {
        return numHorarios;
    }

    public void setNumHorarios(int numHorarios) {
        this.numHorarios = numHorarios;
    }

    public List<Date> getDate() {
        return date;
    }

    public void setDate(List<Date> date) {
        this.date = date;
    }
}
