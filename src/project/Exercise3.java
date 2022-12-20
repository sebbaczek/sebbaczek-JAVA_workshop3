package project;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
public class Exercise3 {
        public static final String EXERCISE_3_GENERATION_ROOT = MainRunner.PROJECT_ROOT + "exported/ex3/";
        public static void run(Map<String, List<Purchase>> groupedByCompany) {
                Map<String, Map<String, List<Purchase>>> groupedByCompanyAndModel = groupedByCompany.entrySet().stream()
                                                                                            .collect(Collectors.toMap(
                                                                                                    Map.Entry::getKey,
                                                                                                    entry -> entry.getValue().stream()
                                                                                                                     .collect(Collectors.groupingBy(p -> p.getCar().getModel()))
                                                                                            ));
                Map<String, Map<String, Pair<BigDecimal, Long>>> reportMap = groupedByCompanyAndModel.entrySet().stream()
                                                                                     .collect(Collectors.toMap(
                                                                                             Map.Entry::getKey,
                                                                                             entry -> entry.getValue().entrySet().stream()
                                                                                                              .collect(Collectors.toMap(
                                                                                                                      Map.Entry::getKey,
                                                                                                                      innerEntry -> buildPair(innerEntry.getValue())
                                                                                                              ))
                                                                                     ));
                AtomicInteger counter = new AtomicInteger(1);
                List<String> reportData = reportMap.entrySet().stream()
                                                  .flatMap(entry -> entry.getValue().entrySet().stream()
                                                                            .map(entryInternal -> getRawRow(
                                                                                    counter.getAndIncrement(),
                                                                                    entry.getKey(),
                                                                                    entryInternal.getKey(),
                                                                                    entryInternal.getValue())))
                                                  .toList();
                generateReport(reportData);
        }
        private static Pair<BigDecimal, Long> buildPair(List<Purchase> purchases) {
                long count = purchases.size();
                BigDecimal averagePrice = purchases.stream()
                                                  .map(p -> p.getCar().getPrice())
                                                  .reduce(BigDecimal.ZERO, BigDecimal::add)
                                                  .divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP);
                return new Pair<>(averagePrice, count);
        }
        private static String getRawRow(int counter, String company, String model, Pair<BigDecimal, Long> pair) {
                return String.format("%s,%s,%s,%s,%s", counter, company, model, pair.getP1(), pair.getP2());
        }
        private static void generateReport(List<String> data) {
                Path path = Paths.get(EXERCISE_3_GENERATION_ROOT, "report", MainRunner.CSV);
                FileService.saveToFile(path, data, "id,company,model,average_price,count");
        }
}