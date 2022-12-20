import java.awt.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
6. Napisz program, który zastąpi zawartość pliku tym samym tekstem ale w całości napisanym wielką
literą. Do wygenerowania zawartości pliku wykorzystaj https://pl.lipsum.com/.
 */
public class Exc6 {
        public static void main(String[] args) {
                Path path = Paths.get("./../workshop3/src/text.txt");
                try {
                        changeCase(path);
                } catch (IOException e) {
                        throw new RuntimeException(e);
                }
        
        
        }
        
        private static void changeCase(Path path) throws IOException {
        
                List<String> collect = Files.lines(path)
                                               .map(p -> p.toUpperCase())
                                               .collect(Collectors.toList());
        
        
                try (
                        BufferedWriter bufferedWriter = Files.newBufferedWriter(path)
                ) {
                        for (String s : collect) {
                        bufferedWriter.write(s);
                        }
                }
        
        
        }
}
