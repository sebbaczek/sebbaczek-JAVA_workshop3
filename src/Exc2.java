import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/*
2. Wypisz na ekranie ścieżki wszystkich plików które zostały znalezione w podanej przez Ciebie
ścieżce. Uwzględnij tylko te pliki tekstowe, które zawierają w swojej treści słowo zajavka. Dodaj
możliwość podania rozszerzeń plików do pominięcia, gdyż niektóre z nich, np. .docx powodują błędy
przy odczycie. Uwzględnij wszystkie pliki w katalogach zagnieżdżonych
 */
public class Exc2 {
        public static void main(String[] args) {
        
//                metoda1
                Path path2 = Paths.get("./../workshop3/src");
                String txtExtension = ".java";
                String javaExtension = ".txt";
                runByWalk(path2, txtExtension, javaExtension);
        
                //                metoda2
                Path src = Paths.get("./../workshop3/src");
                String requiredContent = "c5";
                List<String> extensionsToSkip =
                        List.of(".docx", ".jpg", ".png", ".pdf", ".pptx", ".properties", ".zip", ".class", ".jar");
                System.out.println("metoda 2");
                printDirectoriesWithName(src, requiredContent, extensionsToSkip);
                //                metoda2 end
        }
        private static void runByWalk(final Path path2, final String extension, String extension2) {
                try {
                        System.out.printf("Printing: %s%n", extension,extension2);
                        printFilesWithExtensionByWalk(path2, extension, extension2);
                } catch (Exception e) {
                        System.err.printf("Error reading: %s, message: %s%n", path2, e.getMessage());
                }
        }
        public static void printFilesWithExtensionByWalk(final Path path2, final String extension,
                                                         final String extension2) throws IOException {
                
                //Files.walk działa rekursywnie
                Files.walk(path2)
                        .filter(p -> p.toString().endsWith(extension))
                        .filter(p -> !p.toString().endsWith(extension2))
                        .filter(p -> p.toString().contains("5"))
                        .forEach(System.out::println);
        }
        
        
        
//        metoda2
//private static void run() {
//
//}
        public static void printDirectoriesWithName(Path path, String requiredContent, List<String> extensionsToSkip) {
                try {
                        Files.walk(path)
                                .filter(Files::isRegularFile)
                                .filter(p -> !extensionsToSkip.contains(findFileExtension(p)))
                                .filter(p -> checkIfFileContainsRequiredContent(p, requiredContent))
                                .forEach(System.out::println);
                } catch (Exception e) {
                        System.err.printf("Error reading: %s, message: %s%n", path, e.getMessage());
                }
        }
        private static boolean checkIfFileContainsRequiredContent(Path path, String requiredContent) {
                try {
                        return Files.lines(path)
                                       .anyMatch(line -> line.contains(requiredContent));
                } catch (IOException e) {
                        System.err.printf("Error reading: %s, message: %s%n", path, e.getMessage());
                }
                return false;
        }
        private static String findFileExtension(Path path) {
                String name = path.getFileName().toString();
                int lastIndexOf = name.lastIndexOf(".");
                if (lastIndexOf == -1) {
                        return path.getFileName().toString();
                }
                return name.substring(lastIndexOf);
        }
        
}
