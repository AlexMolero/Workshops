import java.util.ArrayList;
import java.util.List;

public class Backtracking{
    private int[][] incompatibilidad;
    private int Num_Workshops;
    private int numSoluciones = 0;
    private int Vmejor = 0;
    private int[] Xmejor = new int[Num_Workshops];
    private List<Workshop> workshop = new ArrayList<>();
    private int[] configuracion = new int[Num_Workshops];
    private long tiempo_proceso=0;
    private int[] arr = new int[50];
    private float presupuesto_final;
    private float presupuesto_limite;

    public Backtracking(int[][] incompatibilidad, List<Workshop> workshop) {
        this.incompatibilidad = incompatibilidad;
        this.workshop = workshop;
        Num_Workshops = workshop.size();
    }

    public float getPresupuesto() {
        return presupuesto_final;
    }

    public float getPresupuesto_limite() {
        return presupuesto_limite;
    }

    public void setPresupuesto_limite(float presupuesto_limite) {
        this.presupuesto_limite = presupuesto_limite;
    }

    public void setPresupuesto(float presupuesto) {
        this.presupuesto_final = presupuesto;
    }

    public int getVmejor() {
        return Vmejor;
    }

    public long getTiempo_proceso() {
        return tiempo_proceso;
    }

    public void setTiempo_proceso(long tiempo_proceso) {
        this.tiempo_proceso = tiempo_proceso;
    }

    public void setVmejor(int vmejor) {
        Vmejor = vmejor;
    }

    public int[] getArr() {
        return arr;
    }

    public void setArr(int[] arr) {
        this.arr = arr;
    }

    public int[] getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(int[] configuracion) {
        this.configuracion = configuracion;
    }

    public int getNumSoluciones() {
        return numSoluciones;
    }
    public void rellenarConfiguracion(int[] x, int[] arr){
        for(int i=0;i<x.length;i++){
            arr[i] = x[i];
        }
    }
    public void Backtracking1SinMejora(int[] x, int k){
        x[k] = -1;
        while (x[k] < 1){
            x[k]++;
            if (k == (Num_Workshops-1)){
                if (buenaSinMejora(x, k)) {
                    numSoluciones++;
                    rellenarConfiguracion(x,arr);
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
            if (k == Num_Workshops-1){
                if (buenaSinMejora(x, k)) {
                    tratarSolucion2SinMejora(x, k);
                }
            } else if (k < Num_Workshops-1){
                if (buenaSinMejora(x, k)) {
                    Backtracking2SinMejora(x, (k+1));
                }
            }
        }
    }

    public void Backtracking3SinMejora(int[] x, int k, float presupuestoMaximo){
        x[k] = -1;
        while (x[k] < 1){
            x[k]++;
            if (k == Num_Workshops-1){
                if (buenaSinMejora(x, k)) {
                    tratarSolucion3SinMejora(x, k, presupuestoMaximo);
                }
            } else if (k < Num_Workshops-1){
                if (buenaSinMejora(x, k)) {
                    Backtracking3SinMejora(x, (k+1), presupuestoMaximo);
                }
            }
        }
    }

    public boolean buenaSinMejora(int[] x, int k){
        if (x[k] == 0){
            return true;
        }
        for (int i = 0; i < k; i++) {
            if (x[i] == 1){
                if (incompatibilidad[k][i] == 0){
                    return false;
                }
                for (int j = 0; j < workshop.get(i).getNumHorarios(); j++) {
                    for (int l = 0; l < workshop.get(k).getNumHorarios(); l++) {
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
            rellenarConfiguracion(x,arr);

            Vmejor = num_total_horas;
            //System.out.println("Numero total de horas: " + num_total_horas);
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
        if (presupuesto < presupuestoMaximo && presupuesto > presupuesto_final){
            Xmejor = x;
            rellenarConfiguracion(x,arr);
            setPresupuesto(presupuesto);
            //Vmejor = presupuesto;
            System.out.println("Presupuesto: " + presupuesto);
        }
    }

    //Empiezan las funciones con mejoras.

    public void Backtracking1ConMejora(int[] x, int k, Marcaje m){
        x[k] = -1;
        while (x[k] < 1){
            x[k]++;
            if (x[k] == 1){
                for (int i = 0; i < workshop.get(k).getNumHorarios(); i++) {
                    m.horarios.add(workshop.get(k).getDate().get(i));
                }
            }
            if (k == Num_Workshops-1){
                if (buenaConMejora(x, k, m)) {
                    numSoluciones++;
                    System.out.println("Nueva solucion: " + numSoluciones + "Numero horarios" + m.horarios.size());
                }
            } else if (k < Num_Workshops-1){
                if (buenaConMejora(x, k, m)) {
                    Backtracking1ConMejora(x, (k+1), m);
                }
            }
            if (x[k] == 1){
                for (int i = 0; i < workshop.get(k).getNumHorarios(); i++) {
                    m.horarios.remove(m.horarios.size()-1);
                }
            }
        }
    }

    public void Backtracking2ConMejora(int[] x, int k, Marcaje m){
        x[k] = -1;
        while (x[k] < 1){
            x[k]++;
            if (x[k] == 1){
                for (int i = 0; i < workshop.get(k).getNumHorarios(); i++) {
                    m.horarios.add(workshop.get(k).getDate().get(i));
                }
                m.num_horas_totales += workshop.get(k).numHorarios;
            }
            if (k == Num_Workshops-1){
                if (buenaConMejora(x, k, m)) {
                    tratarSolucion2ConMejora(x, k, m);
                }
            } else if (k < Num_Workshops-1){
                if (buenaConMejora(x, k, m)) {
                    Backtracking2ConMejora(x, (k+1), m);
                }
            }
            if (x[k] == 1){
                m.num_horas_totales -= workshop.get(k).numHorarios;
                for (int i = 0; i < workshop.get(k).getNumHorarios(); i++) {
                    m.horarios.remove(m.horarios.size()-1);
                }
            }
        }
    }

    public void Backtracking3ConMejora(int[] x, int k, float presupuestoMaximo, Marcaje m){
        x[k] = -1;
        while (x[k] < 1){
            x[k]++;
            if (x[k] == 1){
                for (int i = 0; i < workshop.get(k).getNumHorarios(); i++) {
                    m.horarios.add(workshop.get(k).getDate().get(i));
                }
                m.categorias[workshop.get(k).getCategory()]++;
                m.presupuesto += workshop.get(k).getPrice();
            }
            if (k == Num_Workshops-1){
                if (buenaConMejora(x, k, m)) {
                    tratarSolucion3ConMejora(x, k, presupuestoMaximo, m);
                }
            } else if (k < Num_Workshops-1){
                if (buenaConMejora(x, k, m)) {
                    Backtracking3ConMejora(x, (k+1), presupuestoMaximo, m);
                }
            }
            if (x[k] == 1) {
                m.categorias[workshop.get(k).getCategory()]--;
                m.presupuesto -= workshop.get(k).getPrice();
                for (int i = 0; i < workshop.get(k).getNumHorarios(); i++) {
                    m.horarios.remove(m.horarios.size()-1);
                }
            }
        }

    }

    public boolean buenaConMejora(int[] x, int k, Marcaje m){
        if (x[k] == 0){
            return true;
        }
        for (int i = 0; i < k; i++) {
            if (x[i] == 1){
                if (incompatibilidad[k][i] == 0){
                    return false;
                }
            }
        }
        for (int i = 0; i < workshop.get(k).getNumHorarios(); i++) {
            for (int j = 0; j < m.horarios.size()-workshop.get(k).getNumHorarios(); j++) {
                if (workshop.get(k).getDate().get(i).getDay() == m.horarios.get(j).getDay() && workshop.get(k).getDate().get(i).getHour() == m.horarios.get(j).getHour()){
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
            System.out.println("Horas totales: " + Vmejor);
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
        if (m.presupuesto < presupuestoMaximo && m.presupuesto > presupuesto_final){
            Xmejor = x;
            presupuesto_final = m.presupuesto;
            System.out.println("Preupuesto: " + m.presupuesto);
        }
    }
}
