import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class nQueens {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter number of Queens: ");
        int n = input.nextInt();

        if (n < 4) {
            System.out.println("Number of Queens should be bigger than 3!");
            return;
        }

        long startTime = System.nanoTime();

        Board board = new Board(n);
        board.solve();

        while (!board.isSolved()) {
            System.out.println("Random restart!");
            board = new Board(n);
            board.solve();
        }

        long stopTime = System.nanoTime();

        if (n <= 100) {
            board.print();
        } else {
            System.out.println("\nQueens' positions: ");
            board.getQueens();
        }

        System.out.println("\nExecution time: " +
                TimeUnit.MILLISECONDS.convert(stopTime-startTime, TimeUnit.NANOSECONDS) + " milliseconds");
    }
}
