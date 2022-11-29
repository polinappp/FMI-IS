import java.util.Scanner;

public class TicTacToe {
    private static final char EMPTY_PLACE = '_';
    private final char PLAYER_SYMBOL;
    private final char COMPUTER_SYMBOL;
    private static final int MAX_SCORE = 10;
    private static final int MIN_SCORE = -10;
    private char[][] board;
    private boolean isPlayerTurn;
    public TicTacToe(boolean isPlayerTurn, char playerSymbol) {
        this.isPlayerTurn = isPlayerTurn;

        if(playerSymbol == 'o') {
            PLAYER_SYMBOL = 'o';
            COMPUTER_SYMBOL = 'x';
        } else {
            PLAYER_SYMBOL = 'x';
            COMPUTER_SYMBOL = 'o';
        }

        board = new char[][]{{EMPTY_PLACE, EMPTY_PLACE, EMPTY_PLACE},
                {EMPTY_PLACE, EMPTY_PLACE, EMPTY_PLACE},
                {EMPTY_PLACE, EMPTY_PLACE, EMPTY_PLACE}};
    }

    private void print() {
        System.out.println("=========\n");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(" ");
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    private int evaluate(int takenTurns) {
        int currValue = checkRows(takenTurns);

        if (currValue == 0) {
            currValue = checkColumns(takenTurns);
        }

        if (currValue == 0) {
            currValue = checkMainDiagonal(takenTurns);
        }

        if (currValue == 0) {
            currValue = checkSecondDiagonal(takenTurns);
        }
        return currValue;
    }

    private int checkRows(int takenTurns) {
        for (int row = 0; row < 3; row++) {
            if (board[row][0] == board[row][1] && board[row][1] == board[row][2]) {
                if (board[row][0] == COMPUTER_SYMBOL) {
                    return MAX_SCORE - takenTurns;
                } else if (board[row][0] == PLAYER_SYMBOL) {
                    return MIN_SCORE + takenTurns;
                }
            }
        }
        return 0;
    }

    private int checkColumns(int takenTurns) {
        for (int col = 0; col < 3; col++) {
            if (board[0][col] == board[1][col] && board[1][col] == board[2][col]) {
                if (board[0][col] == COMPUTER_SYMBOL) {
                    return MAX_SCORE - takenTurns;
                } else if (board[0][col] == PLAYER_SYMBOL) {
                    return MIN_SCORE + takenTurns;
                }
            }
        }
        return 0;
    }

    private int checkMainDiagonal(int takenTurns) {
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] == COMPUTER_SYMBOL) {
                return MAX_SCORE - takenTurns;
            } else if (board[0][0] == PLAYER_SYMBOL) {
                return MIN_SCORE + takenTurns;
            }
        }
        return 0;
    }

    private int checkSecondDiagonal(int takenTurns) {
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            if (board[0][2] == COMPUTER_SYMBOL) {
                return MAX_SCORE - takenTurns;
            } else if (board[0][2] == PLAYER_SYMBOL) {
                return MIN_SCORE + takenTurns;
            }
        }
        return 0;
    }

    private boolean hasMoreTurns() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY_PLACE) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasWinner() {
            return evaluate(0) != 0;
    }

    private int maximizer(int alpha, int beta, int takenTurns) {
        int currScore = evaluate(takenTurns);

        if (currScore != 0) {
            return currScore;
        }

        if (!hasMoreTurns()) {
            return 0;
        }

        int bestScore = Integer.MIN_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY_PLACE) {
                    board[i][j] = COMPUTER_SYMBOL;

                    bestScore = Integer.max(bestScore, minimizer(alpha, beta, takenTurns + 1));

                    board[i][j] = EMPTY_PLACE;

                    if (bestScore >= beta) {
                        return bestScore;
                    }
                    alpha = Integer.max(alpha, bestScore);
                }
            }
        }
        return bestScore;
    }

    private int minimizer(int alpha, int beta, int takenTurns) {
        int currScore = evaluate(takenTurns);

        if (currScore != 0) {
            return currScore;
        }

        if (!hasMoreTurns()) {
            return 0;
        }

        int bestScore = Integer.MAX_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY_PLACE) {
                    board[i][j] = PLAYER_SYMBOL;

                    bestScore = Integer.min(bestScore, maximizer(alpha, beta, takenTurns + 1));

                    board[i][j] = EMPTY_PLACE;

                    if (bestScore <= alpha) {
                        return bestScore;
                    }
                    beta = Integer.min(beta, bestScore);
                }
            }
        }
        return bestScore;
    }

    private int[] findBestTurn() {
        int bestValue = Integer.MIN_VALUE;
        int[] bestNextTurn = new int[]{-1, -1};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY_PLACE) {
                    board[i][j] = COMPUTER_SYMBOL;

                    int currTurnValue = minimizer(Integer.MIN_VALUE, Integer.MAX_VALUE, 0);

                    board[i][j] = EMPTY_PLACE;

                    if (currTurnValue > bestValue) {
                        bestNextTurn[0] = i;
                        bestNextTurn[1] = j;
                        bestValue = currTurnValue;
                    }
                }
            }
        }
        return bestNextTurn;
    }

    private boolean makeTurn(int i, int j) {
        if (board[i][j] == EMPTY_PLACE) {
            if (isPlayerTurn) {
                board[i][j] = PLAYER_SYMBOL;
            } else {
                board[i][j] = COMPUTER_SYMBOL;
            }
            print();
            return true;
        }

        System.out.println("This place is already taken! Choose again.");
        return false;
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        while (hasMoreTurns() && !hasWinner()) {
            int row, col;

            if (this.isPlayerTurn) {
                do {
                    System.out.println("Your turn!");
                    System.out.println("Choose the place of your turn:");
                    System.out.println("Row:");
                    row = scanner.nextInt();
                    System.out.println("Column:");
                    col = scanner.nextInt();
                } while (!makeTurn(row - 1, col - 1));

                this.isPlayerTurn = !this.isPlayerTurn;
                continue;
            }

            int[] bestTurn = findBestTurn();
            makeTurn(bestTurn[0], bestTurn[1]);
            this.isPlayerTurn = !this.isPlayerTurn;
        }

        System.out.println("The game has ended!");
        if (hasWinner()) {
            if (!isPlayerTurn) {
                System.out.println("You won :)");
            } else {
                System.out.println("You lost :(");
            }
        } else {
            System.out.println("No winner... It's a draw :/");
        }
    }
}
