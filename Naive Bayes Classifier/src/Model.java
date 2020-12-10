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

    public void calculateDataPerAttribute() {
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
            //  System.out.println(entry);
            double probabilityRepublican = Math.log(calculateProbabilityOfAttributeForRepublican(entry));
            double probabilityDemocrat = Math.log(calculateProbabilityOfAttributeForDemocrat(entry));
            // System.out.println("Probability of republican: " + probabilityRepublican);
            //  System.out.println("Probability of democrat: " + probabilityDemocrat);

            if (probabilityRepublican > probabilityDemocrat && entry.startsWith("republican")) {
                correctCounter++;
                //     System.out.println("Correct");
            } else if (probabilityRepublican < probabilityDemocrat && entry.startsWith("democrat")) {
                correctCounter++;
                //    System.out.println("Correct");
            } else {
                //    System.out.println("Wrong");
            }

        }

        return (double) correctCounter / testData.size() * 100;
    }

    public double calculateProbabilityOfAttributeForRepublican(String entry) {
        double probability = 1;
        String[] attributes = entry.split(",");

        for (int i = 1; i < attributeYesCounterForRepublicans.length; i++) {
            String attributeValue = attributes[i];
            probability *= getProbabilityOfAttributeForClass(i, attributeValue, "republican");
        }

        return probability;
    }

    public double calculateProbabilityOfAttributeForDemocrat(String entry) {
        double probability = 1;
        String[] attributes = entry.split(",");

        for (int i = 1; i < attributeYesCounterForRepublicans.length; i++) {
            String attributeValue = attributes[i];
            probability *= getProbabilityOfAttributeForClass(i, attributeValue, "democrat");
        }

        return probability;
    }

    private double getProbabilityOfAttributeForClass(int attributeIndex, String attributeValue, String classValue) {
        if (attributeValue.equals("y") && classValue.equals("republican")) {
            return (double) (attributeYesCounterForRepublicans[attributeIndex] + 1) / (republicanCounter + 1);
        }
        if (attributeValue.equals("y") && classValue.equals("democrat")) {
            return (double) (attributeYesCounterForDemocrats[attributeIndex] + 1) / (democratCounter + 1);
        }
        if (attributeValue.equals("n") && classValue.equals("republican")) {
            return (double) (attributeNoCountersForRepublicans[attributeIndex] + 1) / (republicanCounter + 1);
        } else {
            return (double) (attributeNoCountersForDemocrats[attributeIndex] + 1) / (democratCounter + 1);
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
