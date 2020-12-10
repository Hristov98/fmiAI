import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProblemSolution {
    private static final int ATTRIBUTE_NUMBER = 16;
    private static final String DATA_FILE_NAME = "house-votes-84.data";
    private final List<String> dataEntries;
    private final int[] attributeYesCounters;
    private final int[] attributeNoCounters;

    public ProblemSolution() {
        dataEntries = loadDataIntoList();

        attributeYesCounters = new int[ATTRIBUTE_NUMBER];
        attributeNoCounters = new int[ATTRIBUTE_NUMBER];
    }

    private List<String> loadDataIntoList() {
        List<String> entries = new ArrayList<>();
        try (var reader = Files.newBufferedReader(Path.of(DATA_FILE_NAME))) {
            String entry = reader.readLine();

            while (entry != null) {
                entries.add(entry);
                entry = reader.readLine();
            }

        } catch (IOException e) {
            System.out.println("Error while reading data file");
            e.printStackTrace();
        }

        return entries;
    }

    public void calculateDataPerAttribute() {
        for (String entry : dataEntries) {
            String[] splitByAttribute = entry.split(",");

            for (int i = 1; i < splitByAttribute.length; i++) {
                if (splitByAttribute[i].equals("y")) {
                    attributeYesCounters[i - 1]++;
                } else if (splitByAttribute[i].equals("n")) {
                    attributeNoCounters[i - 1]++;
                }
            }
        }
    }

    public void fillInMissingDataValues() {
        for (int entryNumber = 0; entryNumber < dataEntries.size(); entryNumber++) {
            String[] splitByAttribute = dataEntries.get(entryNumber).split(",");

            for (int i = 1; i < splitByAttribute.length; i++) {
                if (splitByAttribute[i].equals("?")) {
                    if (attributeYesCounters[i - 1] > attributeNoCounters[i - 1]) {
                        splitByAttribute[i] = "y";
                        attributeYesCounters[i - 1]++;
                    } else {
                        splitByAttribute[i] = "n";
                        attributeNoCounters[i - 1]++;
                    }
                }
            }

            String modifiedEntry = String.join(",", splitByAttribute);
            dataEntries.set(entryNumber, modifiedEntry);
            //  System.out.println(modifiedEntry);
        }
    }

    public void executeTenfoldCrossValidation() {
        Collections.shuffle(dataEntries);

        List<ArrayList<String>> dataSets = new ArrayList<>();
        int splitPoint = dataEntries.size() / 10;
        //  System.out.println(splitPoint);

        for (int setNumber = 0; setNumber < 10; setNumber++) {
            dataSets.add(new ArrayList<>());
            for (int i = 0; i < splitPoint; i++) {
                dataSets.get(setNumber).add(dataEntries.get(setNumber * splitPoint + i));
            }
        }

//        for (int setNumber = 0; setNumber < 10; setNumber++) {
//            System.out.println(dataSets.get(setNumber).size());
//            for (String entry : dataSets.get(setNumber)) {
//                System.out.println(entry);
//            }
//        }

        double averageAccuracy = 0;
        for (int setNumber = 0; setNumber < 10; setNumber++) {
            List<String> learningData = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                if (i == setNumber) {
                    continue;
                }

                learningData.addAll(dataSets.get(i));
            }

            Model trainingModel = new Model(learningData, dataSets.get(setNumber));
            trainingModel.calculateDataPerAttribute();
            double accuracy = trainingModel.evaluateTestData();
            System.out.printf("The accuracy of Model %d is: %.2f%%\n", setNumber + 1, accuracy);
            averageAccuracy += accuracy;
        }

        System.out.printf("The average accuracy of all models is: %.2f%%\n", averageAccuracy / 10);
    }

    public void printDataEntries() {
        for (String entry : dataEntries) {
            System.out.println(entry);
        }
    }
}
