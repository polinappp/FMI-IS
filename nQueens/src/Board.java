import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Board {
    private static final int RANDOM_RESTART = 100;
    private static final Random R = new Random();
    private int n;
    private int[] queens;
    private int[] columnConflicts;
    private int[] diagonal1Conflicts;
    private int[] diagonal2Conflicts;
    private boolean hasConflicts;

    public Board(int n) {
        this.n = n;
        this.queens = new int[n];
        this.columnConflicts = new int[n];
        this.diagonal1Conflicts = new int[2 * n - 1];
        this.diagonal2Conflicts = new int[2 * n - 1];
        this.hasConflicts = true;

        fillArrays();
    }

    private void fillArrays() {
        Arrays.fill(this.queens, -1);
        Arrays.fill(this.columnConflicts, 0);
        Arrays.fill(this.diagonal1Conflicts, 0);
        Arrays.fill(this.diagonal2Conflicts, 0);
    }

    private void distributeQueens() {
        for (int row = 0; row < this.n; row++) {
            int lowestConflict = getLowestConflicts(row);

            this.queens[row] = lowestConflict;
            updateConflicts(row, lowestConflict, 1);
        }
    }

    private void updateConflicts(int row, int column, int conflict) {
        this.columnConflicts[column] += conflict;
        this.diagonal1Conflicts[column - row + this.n - 1] += conflict;
        this.diagonal2Conflicts[column + row] += conflict;
    }

    private int getLowestConflicts(int row) {
        int minConflicts = this.n + 1;
        List<Integer> minConflictColumns = new ArrayList<>();

        for (int column = 0; column < this.n; column++) {
            int currConflicts = this.columnConflicts[column] + this.diagonal1Conflicts[column - row + this.n - 1]
                    + this.diagonal2Conflicts[row + column];

            if (this.queens[row] == column) {
                currConflicts -= 3;
            }

            if (currConflicts == minConflicts) {
                minConflictColumns.add(column);
            } else if (currConflicts < minConflicts) {
                minConflicts = currConflicts;
                minConflictColumns.clear();
                minConflictColumns.add(column);
            }
        }

        int index = R.nextInt(minConflictColumns.size());
        return minConflictColumns.get(index);
    }

    private int getQueenWithMostConflicts() {
        int maxConflicts = -1;
        List<Integer> queensWithMostConflicts = new ArrayList<>();

        for (int row = 0; row < n; row++) {
            int column = this.queens[row];
            int currConflicts = this.columnConflicts[column] + this.diagonal1Conflicts[column - row + this.n - 1]
                    + this.diagonal2Conflicts[row + column] - 3;

            if (currConflicts == maxConflicts) {
                queensWithMostConflicts.add(row);
            } else if (currConflicts > maxConflicts) {
                maxConflicts = currConflicts;
                queensWithMostConflicts.clear();
                queensWithMostConflicts.add(row);
            }
        }

        if (maxConflicts == 0) {
            this.hasConflicts = false;
        }

        int randomIndex = R.nextInt(queensWithMostConflicts.size());
        return queensWithMostConflicts.get(randomIndex);
    }

    public void solve() {
        distributeQueens();

        int steps = 0;

        while (steps <= RANDOM_RESTART) {
            int row = getQueenWithMostConflicts();

            if (!hasConflicts) {
                System.out.println("\nSteps: " + steps);
                break;
            }

            int column = getLowestConflicts(row);
            updateConflicts(row, this.queens[row], -1);
            updateConflicts(row, column, 1);
            this.queens[row] = column;

            steps++;
        }
    }

    public void print(){
        for (int row = 0; row < this.n; row++) {
            for (int column = 0; column < this.n; column++) {
                if (this.queens[row] == column) {
                    System.out.print("* ");
                }
                else {
                    System.out.print("_ ");
                }
            }
            System.out.println();
        }
    }

    public boolean isSolved() {
        return !hasConflicts;
    }

    public void getQueens() {
        for (int i = 0; i < queens.length; i++) {
            if(i == queens.length-1) {
                System.out.print((queens[i]+1) + "\n");
                break;
            }
            System.out.print((queens[i]+1) + ", ");
        }
    }
}