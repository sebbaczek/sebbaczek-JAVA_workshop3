package project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
public class Exercise2 {
        private static final String EXERCISE_2_GENERATION_ROOT = MainRunner.PROJECT_ROOT + "exported/ex2/";
        public static final String PURCHASES_OF = "purchases-of-";
        public static void run(Map<String, List<Purchase>> groupedByCompany) {
                createFilesGroupedByCompany(groupedByCompany);
                printSortedByCompany();
        }
        private static void createFilesGroupedByCompany(Map<String, List<Purchase>> groupedByCompany) {
                for (Map.Entry<String, List<Purchase>> entry : groupedByCompany.entrySet()) {
                        Path companyPath = Paths.get(EXERCISE_2_GENERATION_ROOT, PURCHASES_OF, entry.getKey(), MainRunner.CSV);
                        List<String> data = entry.getValue().stream()
                                                    .map(PurchaseMappingService::toCsvRow)
                                                    .toList();
                        FileService.saveToFile(companyPath, data);
                }
        }
        private static void printSortedByCompany() {
                Path path = Paths.get(EXERCISE_2_GENERATION_ROOT);
                try {
                        var mapSizeByCompany = Files.list(path)
                                                       .collect(Collectors.toMap(
                                                               Exercise2::getCompanyFromFileName,
                                                               Exercise2::getFileSize,
                                                               (a, b) -> a,
                                                               Exercise2::reverseMap));
                        mapSizeByCompany.keySet()
                                .forEach(company -> System.out.printf("%s:%s%n", company, mapSizeByCompany.get(company)));
                } catch (IOException e) {
                        System.err.printf("Error for path: %s, message: %s%n", path, e.getMessage());
                }
        }
        private static String getCompanyFromFileName(Path path) {
                return path.getFileName()
                               .toString()
                               .substring(PURCHASES_OF.length(), path.getFileName().toString().indexOf('.'));
        }
        private static long getFileSize(Path path) {
                try {
                        return Files.size(path);
                } catch (IOException e) {
                        System.err.printf("Error for path: %s, message: %s%n", path, e.getMessage());
                        return 0;}
        }
        private static TreeMap<String, Long> reverseMap() {
                return new TreeMap<>(Comparator.reverseOrder());
        }
}
