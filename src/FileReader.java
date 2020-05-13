import java.util.Scanner;

public class FileReader {
    private Scanner scanner;

    public FileReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public void show() {
        System.out.println("Introduce la ubicación del fichero: ");
    }

    public String getOption() {
        String strOption = scanner.nextLine();
        return strOption;
    }
}
