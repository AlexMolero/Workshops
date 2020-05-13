import java.util.Scanner;

public class Menu {

    private Scanner scanner;

    public Menu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void show() {

        System.out.println("1-Todas las configuraciones posibles.");
        System.out.println("2-Maximizar horas.");
        System.out.println("3-Maximizar presupuesto disponible.");
    }

    public int getOption() {

        System.out.print("Objetivo: ");
        String strOption = scanner.nextLine();

        return Integer.parseInt(strOption);
    }
}
