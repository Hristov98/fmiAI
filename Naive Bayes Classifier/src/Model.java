import java.util.ArrayList;
import java.util.List;

public class Model {
    private static final int ATTRIBUTE_NUMBER = 16;
    private final List<String> learningData;
    private final List<String> testData;
    private final int[] attributeYesCounterForRepublicans;
    private final int[] attributeYesCounterForDemocrats;
    private final int[] attributeNoCountersForRepublicans;
    private final int[] attributeNoCountersForDemocrats;
    private int republicanCounter;
    private int democratCounter;

    public Model(List<String> learningData, List<String> testData) {
        this.learningData = new ArrayList<>(learningData);
        this.testData = new ArrayList<>(testData);

        attributeYesCounterForRepublicans = new int[ATTRIBUTE_NUMBER];
        attributeYesCounterForDemocrats = new int[ATTRIBUTE_NUMBER];
        attributeNoCountersForRepublicans = new int[ATTRIBUTE_NUMBER];
        attributeNoCountersForDemocrats = new int[ATTRIBUTE_NUMBER];
        republicanCounter = 0;
        democratCounter = 0;
    }

    public void trainModel() {
        for (String entry : learningData) {
            String[] splitByAttribute = entry.split(",");
            boolean isRepublican = true;

            if (splitByAttribute[0].equals("democrat")) {
                democratCounter++;
                isRepublican = false;
            } else if (splitByAttribute[0].equals("republican")) {
                republicanCounter++;
            }

            for (int i = 1; i < splitByAttribute.length; i++) {
                if (splitByAttribute[i].equals("y") && isRepublican) {
                    attributeYesCounterForRepublicans[i - 1]++;
                } else if (splitByAttribute[i].equals("y") && !isRepublican) {
                    attributeYesCounterForDemocrats[i - 1]++;
                } else if (splitByAttribute[i].equals("n") && isRepublican) {
                    attributeNoCountersForRepublicans[i - 1]++;
                } else if (splitByAttribute[i].equals("n") && !isRepublican) {
                    attributeNoCountersForDemocrats[i - 1]++;
                }
            }
        }
    }

    public double evaluateTestData() {
        int correctCounter = 0;

        for (String entry : testData) {
            double probabilityRepublican = calculateProbabilityOfAttributeForRepublican(entry);
            double probabilityDemocrat = calculateProbabilityOfAttributeForDemocrat(entry);

            if (probabilityRepublican > probabilityDemocrat && entry.startsWith("republican")) {
                correctCounter++;
            } else if (probabilityRepublican < probabilityDemocrat && entry.startsWith("democrat")) {
                correctCounter++;
            }
        }

        return ((double) correctCounter / testData.size()) * 100;
    }

    private double calculateProbabilityOfAttributeForRepublican(String entry) {
        double logOfProbability = Math.log((double) republicanCounter / learningData.size());
        String[] attributes = entry.split(",");

        for (int i = 0; i < attributeYesCounterForRepublicans.length; i++) {
            String attributeValue = attributes[i];
            logOfProbability += Math.log(getProbabilityOfAttributeRepublican(i, attributeValue));
        }

        return logOfProbability;
    }

    private double getProbabilityOfAttributeRepublican(int attributeIndex, String attributeValue) {
        if (attributeValue.equals("y")) {
            return (double) (attributeYesCounterForRepublicans[attributeIndex] + 1) / (republicanCounter + 2);
        } else {
            return (double) (attributeNoCountersForRepublicans[attributeIndex] + 1) / (republicanCounter + 2);
        }
    }

    private double calculateProbabilityOfAttributeForDemocrat(String entry) {
        double logOfProbability = Math.log((double) democratCounter / learningData.size());
        String[] attributes = entry.split(",");

        for (int i = 0; i < attributeNoCountersForDemocrats.length; i++) {
            String attributeValue = attributes[i];
            logOfProbability += Math.log(getProbabilityOfAttributeDemocrat(i, attributeValue));
        }

        return logOfProbability;
    }

    private double getProbabilityOfAttributeDemocrat(int attributeIndex, String attributeValue) {
        if (attributeValue.equals("y")) {
            return (double) (attributeYesCounterForDemocrats[attributeIndex] + 1) / (democratCounter + 2);
        } else {
            return (double) (attributeNoCountersForDemocrats[attributeIndex] + 1) / (democratCounter + 2);
        }
    }

    public void printData() {
        System.out.println(learningData.size());
        for (String entry : learningData) {
            System.out.println(entry);
        }

        System.out.println(testData.size());
        for (String entry : testData) {
            System.out.println(entry);
        }
    }

    public void printCalculatedData() {
        System.out.println(learningData.size());
        System.out.println(republicanCounter + " " + democratCounter);

        for (int attributeYesCounter : attributeYesCounterForRepublicans) {
            System.out.print(attributeYesCounter + " ");
        }
        System.out.println();
        for (int attributeYesCounter : attributeYesCounterForDemocrats) {
            System.out.print(attributeYesCounter + " ");
        }
        System.out.println();

        for (int attributeNoCounter : attributeNoCountersForRepublicans) {
            System.out.print(attributeNoCounter + " ");
        }
        System.out.println();
        for (int attributeNoCounter : attributeNoCountersForDemocrats) {
            System.out.print(attributeNoCounter + " ");
        }
        System.out.println();
        System.out.println();
    }
}
