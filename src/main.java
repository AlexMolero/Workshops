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
        List<Workshop> WorkshopConfig = new ArrayList<>();
        int[][] compatibilityArray = new int[50][50];

        System.out.println("_-_-_- WorkshopScheduler -_-_-_ ");
        Scanner scanner = new Scanner(System.in);
        Menu menu = new Menu(scanner);
        Marcaje marcaje = new Marcaje();
        ReadFile file = new ReadFile(scanner);
        file.show();
        String fileToRead = file.getOption();
        File archivo = new File(path + fileToRead);
        String extension = file.getFileExtension(archivo);
        if (extension.equals(".json")) {
            //READ JSON
            WorkshopConfig = file.readJsonWorkshop(path + fileToRead);
            compatibilityArray = file.readJsonCompatibilities(path + fileToRead);
            Num_Workshops = WorkshopConfig.size();
        } else {
            System.out.println("Solo es valido un fichero json");
        }
        //Operations operation = new Operations();
        boolean mejora;
        int option, presupuestoMaximo;
        int[] configuracion = new int[Num_Workshops];
        int k = 0;
        Table table = new Table();
        long startTime;
        long endTime;
        Backtracking backtracking = new Backtracking(compatibilityArray, WorkshopConfig);
        do {
            menu.show();
            option = menu.getOption();
            switch (option) {
                case 1:
                    mejora = menu.mejora();
                    startTime = System.nanoTime();
                    if (!mejora){
                        backtracking.Backtracking1SinMejora(configuracion, k);
                    } else {
                        backtracking.Backtracking1ConMejora(configuracion, k, marcaje);
                    }
                    endTime = System.nanoTime();
                    backtracking.setTiempo_proceso((endTime-startTime));
                    table.mostrar(backtracking, WorkshopConfig,1);
                    break;
                case 2:
                    mejora = menu.mejora();
                    startTime = System.nanoTime();
                    if (!mejora) {
                        backtracking.Backtracking2SinMejora(configuracion, k);
                    } else {
                        backtracking.Backtracking2ConMejora(configuracion, k, marcaje);
                    }
                    endTime = System.nanoTime();
                    backtracking.setTiempo_proceso((endTime-startTime));
                    table.mostrar(backtracking, WorkshopConfig,2);
                    break;
                case 3:
                    mejora = menu.mejora();
                    System.out.print("Introduce el presupuesto maximo: ");
                    String strPresupuesto = scanner.nextLine();
                    presupuestoMaximo = Integer.parseInt(strPresupuesto);
                    startTime = System.nanoTime();
                    if (!mejora) {
                        backtracking.Backtracking3SinMejora(configuracion, k, presupuestoMaximo);
                    } else {
                        backtracking.Backtracking3ConMejora(configuracion, k, presupuestoMaximo, marcaje);
                    }
                    endTime = System.nanoTime();
                    backtracking.setTiempo_proceso((endTime-startTime));
                    table.mostrar(backtracking, WorkshopConfig,3);
                    break;
                default:
                    System.out.print("Error. Opcion no valida.");
                    break;
            }
        } while (true);
    }
}