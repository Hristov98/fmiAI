import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Individual implements Comparable<Individual> {
    private List<Integer> path;
    private int fitness;

    public Individual(int pathSize) {
        generatePath(pathSize);
        calculatePathScore();
    }

    private void generatePath(int pathSize) {
        path = new ArrayList<>();
        path.add(0);

        List<Integer> nextPositions = new ArrayList<>();
        for (int i = 1; i < pathSize; i++) {
            nextPositions.add(i);
        }

        Collections.shuffle(nextPositions);
        path.addAll(nextPositions);
    }

    public Individual(List<Integer> path) {
        this.path = new ArrayList<>(path);
        calculatePathScore();
    }

    private void calculatePathScore() {
        fitness = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            fitness += DistanceTable.pathCosts[path.get(i)][path.get(i + 1)];
        }
    }

    public List<Integer> getPath() {
        return path;
    }

    public void mutateBySwappingTwoCities() {
        Random random = new Random();

        int firstIndex = random.nextInt(path.size() - 1) + 1;
        int secondIndex = random.nextInt(path.size() - 1) + 1;

        if (firstIndex != secondIndex) {
            swapCities(firstIndex, secondIndex);
            calculatePathScore();
        }
    }

    private void swapCities(int firstIndex, int secondIndex) {
        int first = path.get(firstIndex);
        int second = path.get(secondIndex);
        path.set(firstIndex, second);
        path.set(secondIndex, first);
    }

    @Override
    public String toString() {
        return String.format("Cost %d for path %s", fitness, path);
    }

    @Override
    public int compareTo(Individual individual) {
        return fitness - individual.fitness;
    }
}
