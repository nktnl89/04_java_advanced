import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Formatter {

    public void startFormat(String filename, String fileOutputName) {
        String sample = getSampleFromFile(filename);
        ArrayList<String> tmpSampleNumbers = getSampleNumbers(sample);
        ArrayList<String> tmpNumbers = formatSampleNumbers(tmpSampleNumbers);
        writeNumbersToFile(tmpNumbers, fileOutputName);
    }

    private void writeNumbersToFile(ArrayList<String> numbers, String filename) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename))) {
            for (String number : numbers) {
                bufferedWriter.write(number + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("done!");
    }


    private ArrayList<String> formatSampleNumbers(ArrayList<String> sampleNumbers) {
        ArrayList<String> formattedNumbers = new ArrayList<>();
        for (String sampleNum : sampleNumbers) {
            formattedNumbers.add(sampleNum.replaceAll("[^\\d]", ""));
        }
        return formattedNumbers;
    }

    private ArrayList<String> getSampleNumbers(String sample) {
        ArrayList<String> resultList = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\+\\d\\(\\d{3}\\)\\s\\d{3}\\s\\d{2}\\s\\d{2}");
        Matcher matcher = pattern.matcher(sample);
        while (matcher.find()) {
            resultList.add(matcher.group());
        }
        return resultList;
    }

    private String getSampleFromFile(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Files.lines(Paths.get(fileName), StandardCharsets.UTF_8).forEach(stringBuilder::append);
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
