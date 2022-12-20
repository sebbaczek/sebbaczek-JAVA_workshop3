package project;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
public class MainRunner {
        public static final String PROJECT_ROOT = "./src/pl/zajavka/project/";
        public static final String ORIGINAL_SPREADSHEET = PROJECT_ROOT + "resources/client-car-purchase-spreadsheet.csv";
        public static final String CSV = ".csv";
        public static void main(String[] args) {
                Path path = Paths.get(ORIGINAL_SPREADSHEET);
// ex1
                List<Purchase> purchases = FileService.loadData(path);
// ex2
                Map<String, List<Purchase>> groupedByCompany = purchases.stream()
                                                                       .collect(Collectors.groupingBy(p -> p.getCar().getCompany()));
                Exercise2.run(groupedByCompany);
// ex3
                Exercise3.run(groupedByCompany);
// ex4
                Exercise4.run(purchases);
        }
}
