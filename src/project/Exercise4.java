package project;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
public class Exercise4 {
        private static final String EXERCISE_4_GENERATION_ROOT = MainRunner.PROJECT_ROOT + "exported/ex4/";
        public static void run(List<Purchase> purchases) {
                TreeMap<LocalDate, Long> mapByDate = purchases.stream()
                                                             .collect(Collectors.groupingBy(
                                                                     Purchase::getDate,
                                                                     TreeMap::new,
                                                                     Collectors.counting()
                                                             ));
                AtomicInteger byDateCounter = new AtomicInteger(1);
                List<String> dataByDate = mapByDate.entrySet().stream()
                                                  .map(e -> String.format("%s,%s,%s", byDateCounter.getAndIncrement(), e.getKey(), e.getValue()))
                                                  .toList();
                generateReport(dataByDate, "ByDate");
                AtomicInteger byCountCounter = new AtomicInteger(1);
                List<String> dataByCount = mapByDate.entrySet().stream()
                                                   .map(e -> new Pair<>(e.getKey(), e.getValue()))
                                                   .sorted(Comparator.comparing((Pair<LocalDate, Long> p) -> p.getP2()).reversed())
                                                   .map(p -> String.format("%s,%s,%s", byCountCounter.getAndIncrement(), p.getP1(), p.getP2()))
                                                   .toList();
                generateReport(dataByCount, "ByCount");
        }
        private static void generateReport(List<String> data, String suffix) {
                Path path = Paths.get(EXERCISE_4_GENERATION_ROOT, "report", suffix, MainRunner.CSV);
                FileService.saveToFile(path, data, "id,date,count");
        }
}