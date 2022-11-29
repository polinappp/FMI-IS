import java.util.Scanner;

public class PlayGame {
    public static void main(String[] args) {

        System.out.println("Would you want to play first? (yes (y) / no (n))");
        Scanner scanner = new Scanner(System.in);
        String response = scanner.nextLine();

        char playerSymbol;

        do {
            System.out.println("Choose your symbol (x or o)");
            playerSymbol = scanner.next().charAt(0);
        } while (playerSymbol != 'x' && playerSymbol != 'o');


        boolean isPlayerFirst = (response.equals("yes") || response.equals("y"));

        TicTacToe board = new TicTacToe(isPlayerFirst, playerSymbol);
        board.play();
    }
}
