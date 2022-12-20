import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
1. Znajdź i wypisz na ekranie wszystkie pliki, które znajdują się w podanej przez Ciebie ścieżce oraz
mają rozszerzenie zgodne z podanym przez Ciebie rozszerzeniem. Uwzględnij wszystkie pliki w
katalogach zagnieżdżonych. Zaznaczę, że w zadaniu umieściliśmy pewien easter egg. Zadanie to
może zostać rozwiązane przy wykorzystaniu metody Files.walk(), która nie została poruszona w
materiałach. Żeby nie psuć sobie zabawy, spróbuj najpierw rozwiązać to zagadnienie samodzielnie,
korzystając z metod poruszonych w materiałach - bez używania metody Files.walk() ὠ. Następnie
zobacz przykładowe rozwiązanie i wykorzystanie metody Files.walk() zanim przejdziesz do
kolejnych zadań
 */
public class Exc1 {
        public static void main(String[] args) {
        
        
//      metoda 1 nierekursywna
                File file = new File("./../workshop3/src");
                System.out.println(file.getAbsolutePath());
                String[] list = file.list();
                for (String s : list) {
                       if( s.contains(".java")){
                               System.out.println(s);
                       }
                }
                
//      metoda 2 Files.list
                
                Path path = Paths.get("./../workshop3/src");
                String txtExtension = ".txt";
                String javaExtension = ".java";
                run(path, txtExtension);
                run(path, javaExtension);
        }
        private static void run(final Path path, final String extension) {
                try {
                        System.out.printf("Printing: %s%n", extension);
                        printFilesWithExtension(path, extension);
                } catch (Exception e) {
                        System.err.printf("Error reading: %s, message: %s%n", path, e.getMessage());
                }
        }
        public static void printFilesWithExtension(final Path path, final String extension) throws IOException {
                if (Files.isDirectory(path)) {
                        for (Path nextPath : Files.list(path).collect(Collectors.toList())) {
                                printFilesWithExtension(nextPath, extension);
                        }
                } else {
/*
Czyli metoda Path.endsWith() sprawdza czy końcowym sub-elementem ścieżki jest podany przez nas tekst.
Przykładowo możemy tak sprawdzić czy otatni folder na ścieżce jest taki jak oczekujemy.
Nie sprawdzi się to natomiast do sprawdzenia rozszerzenia pliku, stąd wołamy najpierw toString().
*/
                        if (path.toString().endsWith(extension)) {
                                System.out.println(path);
                        }
                }
                
                
//      metoda 3   Files.walk
        
                Path path2 = Paths.get("./../workshop3/src");
                String txtExtension = ".txt";
                String javaExtension = ".java";
                runByWalk(path2, txtExtension);
                runByWalk(path2, javaExtension);
        }
        private static void runByWalk(final Path path2, final String extension) {
                try {
                        System.out.printf("Printing: %s%n", extension);
                        printFilesWithExtensionByWalk(path2, extension);
                } catch (Exception e) {
                        System.err.printf("Error reading: %s, message: %s%n", path2, e.getMessage());
                }
        }
        public static void printFilesWithExtensionByWalk(final Path path2, final String extension) throws IOException {
                Files.walk(path2)
                        .filter(p -> p.toString().endsWith(extension))
                        .forEach(System.out::println);
        
        }
}
