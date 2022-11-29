import java.util.*;

public class Population {
    private static final int MAX_X = 50;
    private static final int MIN_X = -50;
    private static final int POPULATION_SIZE = 20;
    private static final double MUTATION_RATE = 0.3;


    private int citiesCount;
    private PriorityQueue<State> states;
    private List<City> cities;
    private Random rand = new Random();

    public Population(int citiesCount) {
        cities = new ArrayList<>();
        this.citiesCount = citiesCount;

        for (int i = 0; i < citiesCount; i ++) {
            double x = getRandomValue();
            double y = getRandomValue();
            cities.add(new City(x, y));
        }

        states = new PriorityQueue<>();
        for (int i = 0; i < POPULATION_SIZE; i++) {
            states.add(new State(cities));
        }

    }

    private State crossover(State parent1, State parent2) {
        Random random = new Random();
        int breakpoint = random.nextInt(citiesCount);

        List<City> parent1Genome = new ArrayList<>(parent1.getPath());
        List<City> parent2Genome = new ArrayList<>(parent2.getPath());

        for (int i = 0; i < breakpoint; i++) {
            City newVal = parent2Genome.get(i);
            Collections.swap(parent1Genome, parent1Genome.indexOf(newVal), i);
        }

        State child = new State(parent1Genome);

        if (rand.nextDouble() < MUTATION_RATE) {
            child.mutate();
        }

        return child;
    }
    public void reproduce() {
        State child1, child2;

        PriorityQueue<State> newStates = new PriorityQueue<>();

        for (int i = 0; i < POPULATION_SIZE; i+=2) {
            List<State> parents = new ArrayList<>();
            parents.add(states.poll());
            parents.add(states.poll());

            child1 = crossover(parents.get(0), parents.get(1));
            child2 = crossover(parents.get(1), parents.get(0));

            newStates.add(child1);
            newStates.add(child2);
        }

        for (int i = 0; i < POPULATION_SIZE; i++) {
            states.add(newStates.poll());
        }
    }

//    private List<State> pickNRandomElements(List<State> states, int n) {
//        int length = states.size();
//
//        if (length < n) return null;
//
//        for (int i = length - 1; i >= length - n; i--) {
//            Collections.swap(states, i , new Random().nextInt(i + 1));
//        }
//
//        return states.subList(length - n, length);
//    }

//    public State optimize() {
//        List<State> population = new ArrayList<>(states);
//        State globalBestGenome = population.get(0);
//        for (int i = 0; i < MAX_ITERATIONS; i++) {
//            List<State> selected = selection(population);
//            population = reproduce(selected);
//            globalBestGenome = Collections.min(population);
//            if (globalBestGenome.getDistance() < TARGET_PATH)
//                break;
//        }
//        return globalBestGenome;
//    }

//    private List<State> selection(List<State> population) {
//        List<State> selected = new ArrayList<>();
//
//        for (int i=0; i < CROSSOVER_RATE; i++) {
//                selected.add(tournamentSelection(population));
//        }
//
//        return selected;
//    }

//    private State tournamentSelection(List<State> population) {
//        List<State> selected = pickNRandomElements(population, TOURNAMENT_SIZE);
//        return Collections.min(selected);
//    }

    private double getRandomValue() {
        return MIN_X + (MAX_X - MIN_X) * rand.nextDouble();
    }

    public double getBest() {
        return Collections.min(states).getDistance();
    }


}
