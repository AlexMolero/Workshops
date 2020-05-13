import java.io.File;
import java.net.StandardSocketOptions;
import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        // write your code here
        System.out.println("_-_-_- WorkshopScheduler -_-_-_ ");
        Scanner scanner = new Scanner(System.in);
        Menu menu = new Menu(scanner);
        FileReader file = new FileReader(scanner);
        file.show();
        String fileToRead = file.getOption();

        //Operations operation = new Operations();
        menu.show();
        int option = menu.getOption();
    }
}
