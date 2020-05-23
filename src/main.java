import view.ScheduleView;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class main {
    private static String path = "Dataset/";
    public static void main(String[] args) {
        int Num_Workshops = 0;
        List<Workshop> WorkshopConfig = new ArrayList<Workshop>();
        int[][] compatibilityArray = new int[50][50];

        System.out.println("_-_-_- WorkshopScheduler -_-_-_ ");
        Scanner scanner = new Scanner(System.in);
        Menu menu = new Menu(scanner);
        Marcaje marcaje = new Marcaje();
        ReadFile file = new ReadFile(scanner);
        file.show();
        String fileToRead = file.getOption();
        File archivo = new File(path+fileToRead);
        String extension = file.getFileExtension(archivo);
        if(extension.equals(".json")){
            //READ JSON
            WorkshopConfig =   file.readJsonWorkshop(path+fileToRead);
            compatibilityArray = file.readJsonCompatibilities(path+fileToRead);
            Num_Workshops = WorkshopConfig.size();
        }else{
            System.out.println("Solo es valido un fichero json");
        }
        Backtracking backtracking = new Backtracking(compatibilityArray,WorkshopConfig);

        //Operations operation = new Operations();
        boolean mejora;
        int option, presupuestoMaximo;
        int[] configuracion = new int[Num_Workshops];
        int k = 0;
        do{
            menu.show();
            option = menu.getOption();
            switch (option){
                case 1:
                    mejora = menu.mejora();
                    if (!mejora){
                        backtracking.Backtracking1SinMejora(configuracion, k);
                    } else {
                        backtracking.Backtracking1ConMejora(configuracion, k, marcaje);
                    }
                    break;
                case 2:
                    mejora = menu.mejora();
                    if (!mejora){
                        backtracking.Backtracking2SinMejora(configuracion, k);
                    } else {
                        backtracking.Backtracking2ConMejora(configuracion, k, marcaje);
                    }
                    break;
                case 3:
                    mejora = menu.mejora();
                    System.out.print("Introduce el presupuesto maximo: ");
                    String strPresupuesto = scanner.nextLine();
                    presupuestoMaximo =  Integer.parseInt(strPresupuesto);
                    if (!mejora){
                        backtracking.Backtracking3SinMejora(configuracion, k, presupuestoMaximo);
                    } else {
                        backtracking.Backtracking3ConMejora(configuracion, k, presupuestoMaximo, marcaje);
                    }
                    break;
                default:
                    System.out.print("Error. Opcion no valida.");
                    break;
            }
            System.out.println("Fin del programa");

        } while (true);
    }

    public void mostrar(){
       /* menu.show();
        int option = menu.getOption();
        menu.actions(option);*/
        //Prepare view

        final ScheduleView view = new ScheduleView();

        //Show view
        SwingUtilities.invokeLater(() -> view.setVisible(true));

        /*****************
            SAMPLE CODE
         ****************/
        //Set cells content
        int[] categories = new int[5];
        float cost = 0;
        for(int i = 0; i < 12; i++) {
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
        }

        //Erase some cells
        view.resetCellContent(4, 0);
        categories[1 - 1]--;
        view.resetCellContent(11, 1);
        categories[2 - 1]--;
        cost -= 11.0f;
        view.resetCellContent(0, 1);
        categories[1 - 1]--;

        //Set generic information
        view.setStartDateContent(LocalDateTime.now());
        view.setFinishDateContent(LocalDateTime.now());
        view.setDurationContent(Duration.ZERO);
        view.setSolutionsContent(10);

        //Set time optimization information
        view.setTotalWorkshopsContent(57);
        view.setTotalHoursContent(57);

        //Set cost information
        view.setLimitCostContent(10000);
        view.setBaseCostContent(cost);
        view.setDiscountContent(15);
        view.setFinalCostContent(cost * 0.85f);
        for(int i = 0; i < categories.length; i++) {
            view.setCategoryContent(i + 1, categories[i]);
        }
    }
}
