import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
7. Napisz program, który zliczy ilość słów występujących w pliku. Do wygenerowania zawartości pliku
wykorzystaj https://pl.lipsum.com/.

 */
public class Exc7 {
        public static void main(String[] args) {
                Path path = Paths.get("./../workshop3/src/text.txt");
                try {
                        method(path);
                } catch (IOException e) {
                        throw new RuntimeException(e);
                }
        
        }
        
        private static void method(Path path) throws IOException {
                long count = Files.lines(path)

//                           metoda1
//                                     .map(p->p.split("\s").length)
//                                     .reduce(Integer::sum);

//                         metoda2
                                     .flatMap(p -> Stream.of(p.split("\s")))
                                     .count();

                System.out.println(count);
        }
        
}
