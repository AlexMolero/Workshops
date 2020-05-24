import java.util.List;

public class Workshop{
    private String acronym;
    private float price;
    private int category;
    int numHorarios;
    private List<Date> date;
    private List<Integer> rgb;

    Workshop(String acronym, float price, int category, int numHorarios, List<Date> date, List<Integer> rgb) {
        this.acronym = acronym;
        this.price = price;
        this.category = category;
        this.numHorarios = numHorarios;
        this.date = date;
        this.rgb = rgb;
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

    int getNumHorarios() {
        return numHorarios;
    }

    List<Date> getDate() {
        return date;
    }

    List<Integer> getRgb() {
        return rgb;
    }
}
