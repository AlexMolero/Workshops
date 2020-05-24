import view.ScheduleView;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class Table {
    public Table() {
    }
    public void mostrar(Backtracking backtracking, List<Workshop> workshop, int opcion){

        final ScheduleView view = new ScheduleView();

        //Show view
        SwingUtilities.invokeLater(() -> view.setVisible(true));

        /*****************
         SAMPLE CODE
         ****************/
        //Set cells content
        int[] categories = new int[5];
        float cost = 0;
        int count=0;
        for (int i = 0; i<backtracking.getConfiguracion().length; i++){
            if (backtracking.getConfiguracion()[i]==1){
                count++;
                for(int j=0; j<workshop.get(i).getDate().size();j++){
                    view.setCellContent(workshop.get(i).getAcronym(), workshop.get(i).getCategory(),
                            workshop.get(i).getPrice(), Color.ORANGE, workshop.get(i).getDate().get(j).getHour(), workshop.get(i).getDate().get(j).getDay());
                 }
            }
        }
       /* for(int i = 0; i < 12; i++) {
            for(int j = 0; j < 5; j++) {
                if (i % 2 == 0) {
                    if(j % 2 == 0) {
                        view.setCellContent(String.format("Row: %d - Col: %d", i, j), 1,
                                i * j, Color.ORANGE, i, j);
                    } else {
                        view.setCellContent(String.format("Row: %d - Col: %d", i, j), 1,
                                i * j, Color.PINK, i, j);
                    }
                    categories[1 - 1]++;
                } else {
                    if(j % 2 == 0) {
                        view.setCellContent(String.format("Row: %d - Col: %d", i, j), 2,
                                i * j, Color.CYAN, i, j);
                    } else {
                        view.setCellContent(String.format("Row: %d - Col: %d", i, j), 2,
                                i * j, Color.GREEN, i, j);
                    }
                    categories[2 - 1]++;
                }
                cost += i * j;
            }
        }*/

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
            view.setLimitCostContent(10000);
            view.setBaseCostContent(cost);
            view.setDiscountContent(15);
            view.setFinalCostContent(cost * 0.85f);
            for(int i = 0; i < categories.length; i++) {
                view.setCategoryContent(i + 1, categories[i]);
            }
        }

    }
}

