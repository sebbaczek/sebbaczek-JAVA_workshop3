import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
4. Napisz program, który w podanym przez Ciebie katalogu znajdzie dowolny plik, z najstarszą i
najmłodszą datą modyfikacji. Przeszukaj wszystkie pliki w katalogu, również te zagłębione.
 */
public class Exc4 {
        public static void main(String[] args) {
                Path path = Paths.get("./../workshop2/src");
                find(path);
        }
        
        public static void find(final Path path) {
                
                //Files.walk działa rekursywnie
                List<Path> collect = null;
                try {
                        collect = Files.walk(path)
                                                     .filter(Files::isRegularFile)
                                                     .sorted(Comparator.comparing(p -> {
                                                             try {
                                                                     return Files.getLastModifiedTime(p);
                                                             } catch (IOException e) {
                                                                     throw new RuntimeException(e);
                                                             }
                                                     }))
                                                     .collect(Collectors.toList());
                } catch (IOException e) {
                        throw new RuntimeException(e);
                }
                System.out.println(collect.get(0)+"\n"+collect.get(collect.size()-1)
                
                );
                
        
        };
//                                                      .collect(Collectors.toMap(
//                                                              p -> p.getFileName(),
//                                                              p -> {
//                                                                      try {
//                                                                              return Files.getLastModifiedTime(p);
//                                                                      } catch (IOException e) {
//                                                                              throw new RuntimeException(e);
//                                                                      }
//                                                              }));
//                collect.entrySet().stream()
////                        .min((a,b)-> Comparator.comparing(b.getValue(), FileTime::toInstant);
//
//                        .sorted(Comparator.comparing(p->Files.getLastModifiedTime(p)
        


//                        .forEach(System.out::println);



}
