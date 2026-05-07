import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Scanner;

public class ConvertUtf8ToWin1251 {
    
    public static void main(String[] args) {
        System.out.print("Enter the input .txt file: ");
        Scanner scanner = new Scanner(System.in);
        String inputFileName = scanner.nextLine().trim();

        if (!inputFileName.toLowerCase().endsWith(".txt")) {
            System.err.println("Invalid format");
            return;
        }
        
        String workingDirectory = System.getProperty("user.dir");
        File inputFile = new File(workingDirectory, inputFileName);

        if (!inputFile.exists()) {
            System.err.println("File not found in working directory");
            return;
        }
        
        //Формирую имя выходного файла
        int dotIndex = inputFileName.lastIndexOf('.');
        String newName = inputFileName.substring(0, dotIndex) + "_Win1251" + inputFileName.substring(dotIndex);
        File outputFile = new File(workingDirectory, newName);

        //Создаю заранее файл
        try {
            if (outputFile.createNewFile()) {
                System.out.println("File created successfully.");
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        Charset utf8 = StandardCharsets.UTF_8;
        Charset win1251 = Charset.forName("windows-1251");
        
        //Построчно перекодирую
        try (BufferedReader reader = Files.newBufferedReader(inputFile.toPath(), utf8);
            BufferedWriter writer = Files.newBufferedWriter(outputFile.toPath(), win1251)) {
            
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }

            System.out.println("The file has been converted successfully.: " + outputFile);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }    
}
