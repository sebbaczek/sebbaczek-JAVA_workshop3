import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
5. Napisz program, który znajdzie drugie najdłuższe słowo i drugie słowo o długości 3 w podanym
przez Ciebie pliku. Do wygenerowania zawartości pliku wykorzystaj https://pl.lipsum.com/.
 */
public class Exc5 {
        public static void main(String[] args) {

                exercise5();
        }
        private static void exercise5() {
                Path src = Paths.get("./../workshop3/src/text.txt");
                List<String> lines = readFile(src);
                String secondLongest = findSecondLongestWord(lines);
                System.out.println("Second longest: " + secondLongest);
                String secondThreeLength = findSecondThreeLength(lines);
                System.out.println("Second three length: " + secondThreeLength);
        }
        private static List<String> readFile(Path path) {
                try {
                        return Files.readAllLines(path);
                } catch (Exception e) {
                        System.err.printf("Error in readAllLines(), path: %s, message: %s%n", path, e.getMessage());
                }
                return Collections.emptyList();
        }
        // Można to rozwiązać na kilka sposobów.
// Przy szukaniu najdłuższych wyrazów pokażemy jeden sposób.
// Inny zostanie pokazany przy szukaniu drugiego słowa o długości 3.
        private static String findSecondLongestWord(final List<String> lines) {
                Integer longestWordSize = findLongestWordSize(lines);
                List<String> longestWords = findLongestWords(longestWordSize, lines);
                System.out.printf("Longest words: %s%n", longestWords);
// Pomijamy słowo na indeksie 0 i wyświetlamy drugie.
// Musimy przy tym założyć,
// że słowa są podane w liście w kolejności ich wystąpienia w pliku tekstowym.
                return longestWords.get(1);
        }
        private static Integer findLongestWordSize(final List<String> lines) {
                Integer longestSize = null;
                for (String line : lines) {
                        if (!line.isEmpty()) {
                                var words = line.split(" ");
                                for (String word : words) {
// Pozbywamy się przecinków i kropek ze słów.
// Można również pozbyć się innych znaków niż litery.
                                        String toCompare = word.replace(",", "").replace(".", "");
                                        if (longestSize == null) {
                                                longestSize = toCompare.length();
                                        }
                                        if (toCompare.length() > longestSize) {
                                                longestSize = toCompare.length();
                                        }
                                }
                        }
                }
                return longestSize;
        }
        private static List<String> findLongestWords(final Integer longestWordSize, final List<String> lines) {
                List<String> longestWords = new ArrayList<>();
                for (String line : lines) {
                        if (!line.isEmpty()) {
                                var words = line.split(" ");
                                for (String word : words) {
                                        String toCompare = word.replace(",", "").replace(".", "");
                                        if (toCompare.length() == longestWordSize) {
                                                longestWords.add(toCompare);
                                        }
                                }
                        }
                }
                return longestWords;
        }
        private static String findSecondThreeLength(final List<String> lines) {
                TreeMap<Integer, List<String>> groupedByLength = lines.stream()
                                                                         .filter(line -> !line.isEmpty())
                                                                         .flatMap(line -> Stream.of(line.split(" ")))
                                                                         .map(word -> word.replace(",", "").replace(".", ""))
                                                                         .collect(Collectors.groupingBy(String::length, TreeMap::new, Collectors.toList()));
                groupedByLength
                        .forEach((k, v) -> System.out.printf("Words with length: %s, words: %s%n", k, v));
// Słowa zostały zgrupowane po ich długości.
// Zatem z grupy słów o długości 3 pobieramy słowo na pozycji 1
// (tak jak w poprzednim przypadku).
                return groupedByLength.get(3).get(1);
        }
}
