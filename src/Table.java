import view.ScheduleView;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

class Table {
    Table() {
    }
    void mostrar(Backtracking backtracking, List<Workshop> workshop, int opcion){

        final ScheduleView view = new ScheduleView();

        //Show view
        SwingUtilities.invokeLater(() -> view.setVisible(true));

        //Set cells content
        int[] categories = new int[5];
        float cost = 0;
        int count=0;
        for (int i = 0; i<backtracking.getConfiguracion().length; i++){
            if (backtracking.getConfiguracion()[i]==1){
                count++;
                cost+=workshop.get(i).getPrice();
                    switch (workshop.get(i).getCategory()){
                        case 1:
                            categories[0]++;
                            break;
                        case 2:
                            categories[1]++;
                            break;
                        case 3:
                            categories[2]++;
                            break;
                        case 4:
                            categories[3]++;
                            break;
                        case 5:
                            categories[4]++;
                            break;
                    }

                for(int j=0; j<workshop.get(i).getDate().size();j++){
                    Color color = new Color(workshop.get(i).getRgb().get(0),workshop.get(i).getRgb().get(1),workshop.get(i).getRgb().get(2));
                    view.setCellContent(workshop.get(i).getAcronym(), workshop.get(i).getCategory(),
                            workshop.get(i).getPrice(), color, workshop.get(i).getDate().get(j).getHour(), workshop.get(i).getDate().get(j).getDay());
                 }
            }
        }

        //Set generic information
        view.setStartDateContent(LocalDateTime.now());
        view.setFinishDateContent(LocalDateTime.now());
        view.setDurationContent(Duration.ofNanos(backtracking.getTiempo_proceso()));

        if(opcion==1){
            view.setSolutionsContent(backtracking.getNumSoluciones());

        }
        //Set time optimization information
        if(opcion==2){
            view.setSolutionsContent(1);
            view.setTotalWorkshopsContent(count);
            view.setTotalHoursContent(backtracking.getVmejor());
        }

        //Set cost information
        if(opcion==3){
            view.setSolutionsContent(1);
            view.setLimitCostContent(backtracking.getPresupuesto_limite());
            view.setBaseCostContent(cost);
            view.setFinalCostContent(backtracking.getPresupuesto_final());
            for(int i = 0; i < categories.length; i++) {
                view.setCategoryContent(i + 1, categories[i]);
            }
            int num_total_categorias=0;
            for (int i = 0; i < 5; i++) {
                if (categories[i] > 0){
                    num_total_categorias++;
                }
            }
           if(num_total_categorias>2){
                view.setDiscountContent(15);
           }else if(num_total_categorias==2){
                view.setDiscountContent(5);
           }
        }
    }
}

