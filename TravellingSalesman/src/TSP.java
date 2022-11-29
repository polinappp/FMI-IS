import java.util.Scanner;

public class TSP {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int cityCount = scanner.nextInt();

        Population population = new Population(cityCount);

        for (int age = 1; age <= 200; age++) {
            if (age < 20) {
                System.out.println(age + " : " + population.getBest());
            }

            population.reproduce();
        }

    }
}
