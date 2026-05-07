import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class ConvertUtf8ToWin1251 {
    
    public static void main(String[] args) {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select the input text file");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(null);

        if (result != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(null, "Cancel");
            return;
        }

        File inputFile = fileChooser.getSelectedFile();

        String fileName = inputFile.getName();
        if (!fileName.toLowerCase().endsWith(".txt")) {
            JOptionPane.showMessageDialog(null,
                    "Select a file in .txt format",
                    "Invalid format",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        //Формирую имя выходного файла
        String name = inputFile.getName();
        int dotIndex = name.lastIndexOf('.');
        String newName = name.substring(0, dotIndex) + "_Win1251" + name.substring(dotIndex);
        File outputFile = new File(newName);

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
