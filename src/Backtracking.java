import java.util.ArrayList;
import java.util.List;

public class Backtracking{
    private int[][] incompatibilidad;
    private int Num_Workshops;
    private int numSoluciones;
    private int Vmejor = 0;
    private int[] Xmejor = new int[Num_Workshops];
    private List<Workshop> workshop = new ArrayList<>();

    public Backtracking(int[][] incompatibilidad, List<Workshop> workshop) {
        this.incompatibilidad = incompatibilidad;
        this.workshop = workshop;
        Num_Workshops = workshop.size();
    }

    public void Backtracking1SinMejora(int[] x, int k){
        x[k] = 2;
        while (x[k] >=0){
            x[k]--;
            if (k == (Num_Workshops-1)){
                if (buenaSinMejora(x, k)) {
                    System.out.println("NUEVA SOLUCION" + numSoluciones);

                    numSoluciones++;
                }
            } else if (k < (Num_Workshops-1)){
                if (buenaSinMejora(x, k)) {
                    Backtracking1SinMejora(x, (k+1));
                }
            }
        }

    }

    public void Backtracking2SinMejora(int[] x, int k){
        x[k] = -1;
        while (x[k] < 1){
            x[k]++;
            if (k == Num_Workshops){
                if (buenaSinMejora(x, k)) {
                    tratarSolucion2SinMejora(x, k);
                }
            } else if (k < Num_Workshops){
                if (buenaSinMejora(x, k)) {
                    Backtracking2SinMejora(x, k);
                }
            }
        }
    }

    public void Backtracking3SinMejora(int[] x, int k, float presupuestoMaximo){
        x[k] = -1;
        while (x[k] < 1){
            x[k]++;
            if (k == Num_Workshops){
                if (buenaSinMejora(x, k)) {
                    tratarSolucion3SinMejora(x, k, presupuestoMaximo);
                }
            } else if (k < Num_Workshops){
                if (buenaSinMejora(x, k)) {
                    Backtracking3SinMejora(x, k, presupuestoMaximo);
                }
            }
        }
    }

    public boolean buenaSinMejora(int[] x, int k){
        if (x[k] == 0){ //Siempre entrará aqui
            return true;
        }
        for (int i = 0; i < k; i++) {
            if (x[i] == 1){
                if (incompatibilidad[k][i] == 0){
                    return false;
                }
                for (int j = 0; j < workshop.get(i).getNumHorarios(); j++) {
                    for (int l = 0; l < workshop.get(k).getNumHorarios(); l++) {
                        //Todo Esto esta mal!!!!!
                        if (workshop.get(k).getDate().get(l).getDay() == workshop.get(i).getDate().get(j).getDay() && workshop.get(k).getDate().get(l).getHour() == workshop.get(i).getDate().get(j).getHour()){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public void tratarSolucion2SinMejora(int[] x, int k){
        int num_total_horas = 0;
        for (int i = 0; i < k; i++) {
            if (x[i] == 1){
                num_total_horas += workshop.get(i).getNumHorarios();
            }
        }
        if (num_total_horas > Vmejor){
            Xmejor = x;
            Vmejor = num_total_horas;
        }
    }

    public void tratarSolucion3SinMejora(int[] x, int k, float presupuestoMaximo){
        int num_total_categorias = 0;
        float presupuesto = 0;
        int[] numCategorias = new int[5];
        for (int i = 0; i < k; i++) {
            if (x[i] == 1){
                switch (workshop.get(i).getCategory()){
                    case 1:
                        numCategorias[0]++;
                        break;
                    case 2:
                        numCategorias[1]++;
                        break;
                    case 3:
                        numCategorias[2]++;
                        break;
                    case 4:
                        numCategorias[3]++;
                        break;
                    case 5:
                        numCategorias[4]++;
                        break;
                }
                presupuesto += workshop.get(i).getPrice();
            }
        }
        for (int i = 0; i < 5; i++) {
            if (numCategorias[i] > 0){
                num_total_categorias++;
            }
        }
        if (num_total_categorias == 2){
            presupuesto -= presupuesto*0.05;
        }
        if (num_total_categorias > 2){
            presupuesto -= presupuesto*0.15;
        }
        if (presupuesto < presupuestoMaximo && presupuesto > Vmejor){
            Xmejor = x;
            Vmejor = (int)presupuesto;
        }
    }

    //Empiezan las funciones con mejoras.

    public void Backtracking1ConMejora(int[] x, int k, Marcaje m){
        x[k] = -1;
        while (x[k] < 1){
            x[k]++;
            for (int i = 0; i < workshop.get(k).getNumHorarios(); i++) {
                m.horarios.add(workshop.get(k).date.get(i));
            }
            if (k == Num_Workshops){
                if (buenaConMejora(x, k, m)) {
                    numSoluciones++;
                }
            } else if (k < Num_Workshops){
                if (buenaConMejora(x, k, m)) {
                    Backtracking1ConMejora(x, k, m);
                }
            }
            for (int i = 0; i < m.horarios.size(); i++) {
                m.horarios.remove(m.horarios.get(i));
            }
        }
    }

    public void Backtracking2ConMejora(int[] x, int k, Marcaje m){
        x[k] = -1;
        while (x[k] < 1){
            x[k]++;
            for (int i = 0; i < workshop.get(k).getNumHorarios(); i++) {
                m.horarios.add(workshop.get(k).date.get(i));
            }
            m.num_horas_totales += workshop.get(k).numHorarios;
            if (k == Num_Workshops){
                if (buenaConMejora(x, k, m)) {
                    tratarSolucion2ConMejora(x, k, m);
                }
            } else if (k < Num_Workshops){
                if (buenaConMejora(x, k, m)) {
                    Backtracking2ConMejora(x, k, m);
                }
            }
            for (int i = 0; i < m.horarios.size(); i++) {
                m.horarios.remove(m.horarios.get(i));
            }
            m.num_horas_totales -= workshop.get(k).numHorarios;
        }
    }

    public void Backtracking3ConMejora(int[] x, int k, float presupuestoMaximo, Marcaje m){
        x[k] = -1;
        while (x[k] < 1){
            x[k]++;
            for (int i = 0; i < workshop.get(k).getNumHorarios(); i++) {
                m.horarios.add(workshop.get(k).date.get(i));
            }
            m.categorias[workshop.get(k).getCategory()]++;
            m.presupuesto += workshop.get(k).getPrice();
            if (k == Num_Workshops){
                if (buenaConMejora(x, k, m)) {
                    tratarSolucion3ConMejora(x, k, presupuestoMaximo, m);
                }
            } else if (k < Num_Workshops){
                if (buenaConMejora(x, k, m)) {
                    Backtracking3ConMejora(x, k, presupuestoMaximo, m);
                }
            }
            for (int i = 0; i < m.horarios.size(); i++) {
                m.horarios.remove(m.horarios.get(i));
            }
            m.categorias[workshop.get(k).getCategory()]--;
            m.presupuesto -= workshop.get(k).getPrice();
        }

    }

    public boolean buenaConMejora(int[] x, int k, Marcaje m){
        if (x[k] == 0){
            return true;
        }
        for (int i = 0; i < k-1; i++) {
            if (x[i] == 1){
                if (incompatibilidad[k][i] == 0){
                    return false;
                }
            }
        }
        for (int j = 0; j < workshop.get(k).getNumHorarios(); j++) {
            for (int l = 0; l < m.horarios.size(); l++) {
                //Todo Esto esta mal!!!!!
                if (workshop.get(k).getDay() == workshop.get(l).getDay() && workshop.get(k).getHour() == workshop.get(l).getHour()){
                    return false;
                }
            }
        }
        return true;
    }

    public void tratarSolucion2ConMejora(int[] x, int k, Marcaje m){
        if (m.num_horas_totales > Vmejor){
            Xmejor = x;
            Vmejor = m.num_horas_totales;
        }
    }

    public void tratarSolucion3ConMejora(int[] x, int k, float presupuestoMaximo, Marcaje m){
        int num_total_categorias = 0;
        for (int i = 0; i < 5; i++) {
            if (m.categorias[i] > 0){
                num_total_categorias++;
            }
        }
        if (num_total_categorias == 2){
            m.presupuesto -= m.presupuesto*0.05;
        }
        if (num_total_categorias > 2){
            m.presupuesto -= m.presupuesto*0.15;
        }
        if (m.presupuesto < presupuestoMaximo && m.presupuesto > Vmejor){
            Xmejor = x;
            Vmejor = (int)m.presupuesto;
        }
    }
}
