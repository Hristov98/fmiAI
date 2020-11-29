import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose who goes first by entering the specific number:");
        System.out.println("1) Player");
        System.out.println("2) AI");

        int choice = scanner.nextInt();

        if (choice == 1) {
            game.playGamePlayerGoesFirst();
        } else {
            //TBD
        }
    }
}
