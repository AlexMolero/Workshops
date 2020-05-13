import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        // write your code here
        Scanner scanner = new Scanner(System.in);
        Menu menu = new Menu(scanner);
        //Operations operation = new Operations();
        menu.show();
        int option = menu.getOption();
    }
}
