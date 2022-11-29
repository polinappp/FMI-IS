import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class State implements Comparable<State>{
    private List<City> path;
    private double distance = 0;

    public State(List<City> cities) {
        generateRandomPath(cities);
        calculateDistance(cities);
    }

    private void generateRandomPath(List<City> cities) {
        path = new ArrayList<>();
        for (int i = 0; i < cities.size(); i++) {
            path.add(cities.get(i));
        }

        for (int i = 0; i < cities.size(); i++) {
            int randomIndexToSwap = new Random().nextInt(path.size());
            Collections.swap(path, i, randomIndexToSwap);
        }
    }

    private void calculateDistance(List<City> cities) {
        for (int i = 0; i < path.size() - 1; i++) {
            City a = path.get(i);
            City b = path.get(i + 1);
            distance += distance(a.getX(), a.getY(), b.getX(), b.getY());
        }
    }

    public void mutate() {
        int randCity1 = new Random().nextInt(path.size());
        int randCity2 = new Random().nextInt(path.size());

        Collections.swap(path, randCity1, randCity2);
    }
    private double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    public double getDistance() {
        return distance;
    }

    public List<City> getPath() {
        return path;
    }

    @Override
    public int compareTo(State o) {
        return Double.compare(this.distance, o.distance);
    }


}
