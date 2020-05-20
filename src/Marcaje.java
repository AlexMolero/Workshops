import java.util.List;

public class Marcaje {
    float presupuesto;
    int[] categorias;
    int num_horas_totales;
    List<Date> horarios;

    public Marcaje(float presupuesto, int[] categorias, int num_horas_totales, List<Date> horarios) {
        this.presupuesto = presupuesto;
        this.categorias = categorias;
        this.num_horas_totales = num_horas_totales;
        this.horarios = horarios;
    }

    public float getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(float presupuesto) {
        this.presupuesto = presupuesto;
    }

    public int[] getCategorias() {
        return categorias;
    }

    public void setCategorias(int[] categorias) {
        this.categorias = categorias;
    }

    public int getNum_horas_totales() {
        return num_horas_totales;
    }

    public void setNum_horas_totales(int num_horas_totales) {
        this.num_horas_totales = num_horas_totales;
    }

    public List<Date> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<Date> horarios) {
        this.horarios = horarios;
    }
}
