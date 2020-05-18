import java.util.ArrayList;
import java.util.List;

public class Backtracking{
    public int[][] incompatibilidad;
    public int Num_Workshops;
    public int numSoluciones;
    private int[] configuracion = new int[Num_Workshops];
    private int k;
    private int Vmejor;
    private int[] Xmejor = new int[Num_Workshops];
    List<Workshop> workshop = new ArrayList<>();
    List<Marcaje> marcaje = new ArrayList<>();


    public void Backtracking1SinMejora(int[] x, int k){
        x[k] = -1;
        while (x[k] < 1){
            x[k]++;
            if (k == Num_Workshops){
                if (buenaSinMejora(x, k)) {
                    numSoluciones++;
                }
            } else if (k < Num_Workshops){
                if (buenaSinMejora(x, k)) {
                    Backtracking1SinMejora(x, k);
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
        if (x[k] == 0){
            return true;
        }
        for (int i = 0; i < k-1; i++) {
            if (x[i] == 1){
                if (incompatibilidad[k][i] == 0){
                    return false;
                }
                for (int j = 0; j < workshop.get(i).getNumHorarios(); j++) {
                    for (int l = 0; l < workshop.get(k).getNumHorarios(); l++) {
                        if (workshop.get(i).getDay() == workshop.get(k).getDay() && workshop.get(i).getHour() == workshop.get(k).getHour()){
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

    public void Backtracking1ConMejora(int[] x, int k){
        x[k] = -1;
        while (x[k] < 1){
            x[k]++;
            if (k == Num_Workshops){
                if (buenaConMejora(x, k)) {
                    numSoluciones++;
                }
            } else if (k < Num_Workshops){
                if (buenaConMejora(x, k)) {
                    Backtracking1SinMejora(x, k);
                }
            }
        }
    }

    public void Backtracking2ConMejora(int[] x, int k){
        x[k] = -1;
        while (x[k] < 1){
            x[k]++;
            if (k == Num_Workshops){
                if (buenaConMejora(x, k)) {
                    tratarSolucion2SinMejora(x, k);
                }
            } else if (k < Num_Workshops){
                if (buenaConMejora(x, k)) {
                    Backtracking2SinMejora(x, k);
                }
            }
        }
    }

    public void Backtracking3ConMejora(int[] x, int k, float presupuestoMaximo){
        x[k] = -1;
        while (x[k] < 1){
            x[k]++;
            if (k == Num_Workshops){
                if (buenaConMejora(x, k)) {
                    tratarSolucion3SinMejora(x, k, presupuestoMaximo);
                }
            } else if (k < Num_Workshops){
                if (buenaConMejora(x, k)) {
                    Backtracking3SinMejora(x, k, presupuestoMaximo);
                }
            }
        }
    }

    public boolean buenaConMejora(int[] x, int k){
        if (x[k] == 0){
            return true;
        }
        for (int i = 0; i < k-1; i++) {
            if (x[i] == 1){
                if (incompatibilidad[k][i] == 0){
                    return false;
                }
                for (int j = 0; j < workshop.get(i).getNumHorarios(); j++) {
                    for (int l = 0; l < workshop.get(k).getNumHorarios(); l++) {
                        if (workshop.get(i).getDay() == workshop.get(k).getDay() && workshop.get(i).getHour() == workshop.get(k).getHour()){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public void tratarSolucion2ConMejora(int[] x, int k){
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

    public void tratarSolucion3ConMejora(int[] x, int k, float presupuestoMaximo){
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



}
