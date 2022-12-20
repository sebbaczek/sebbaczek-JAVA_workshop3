package project;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import static project.PurchaseMappingService.CSV_HEADER;

public class FileService {
        static final String EXERCISE_2_GENERATION_ROOT = MainRunner.PROJECT_ROOT + "exported/ex2/";
        static final String PURCHASES_OF = "purchases-of-";
        public static List<Purchase> loadData(Path path) {

                
                try {
                        return Files.lines(path, Charset.defaultCharset())
                                       .skip(1)
                                       .map(PurchaseMappingService::mapPurchase)
                                       .toList();
                } catch (IOException e) {
                        System.err.printf("Error for path: %s, message: %s%n", path, e.getMessage());
                        return Collections.emptyList();
                }
        }
        public static void saveToFile(Path path, List<String> data) {
                saveToFile(path, data, CSV_HEADER);
        }
        public static void saveToFile(Path path, List<String> data, String header) {
                try {
                        Files.createDirectories(path.subpath(0, path.getNameCount() - 1));
                } catch (IOException e) {
                        System.err.printf("Error for path: %s, message: %s%n", path, e.getMessage());
                }
                try (BufferedWriter writer = Files.newBufferedWriter(path, Charset.defaultCharset())) {
                        writer.write(header);
                        writer.newLine();
                        for (String row : data) {
                                writer.write(row);
                                writer.newLine();
                                writer.flush();
                        }
                } catch (IOException e) {
                        System.err.printf("Error for path: %s, message: %s%n", path, e.getMessage());
                }
        }
}
