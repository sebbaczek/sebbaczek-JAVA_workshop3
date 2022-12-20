import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
3. Napisz program, który na podstawie podanego pliku wydrukuje jego rozmiar w bajtach, kb, mb.
 */
public class Exc3{
        public static void main(String[] args) {
                File file = new File("./../workshop3/src/Exc1.java").getAbsoluteFile();
//                System.out.println(file.getAbsoluteFile());
                method(file);
                run();
        }
//           metoda2
                private static void run() {
                        Path src = Paths.get("./../workshop3/src/Exc1.java");
                        fileSize(src);
                }
                public static void fileSize(Path path) {
                        try {
                                BigDecimal divisor = BigDecimal.valueOf(1000);
                                BigDecimal sizeInBytes = BigDecimal.valueOf(Files.size(path));
                                BigDecimal sizeInKb = sizeInBytes.divide(divisor, 2, RoundingMode.HALF_UP);
                                BigDecimal sizeInMb = sizeInKb.divide(divisor, 2, RoundingMode.HALF_UP);
                                System.out.printf("File: [%s] has size of: [%s] b / [%s] kb / [%s] mb",
                                        path.getFileName(), sizeInBytes, sizeInKb, sizeInMb);
                        } catch (IOException e) {
                                System.err.printf("Error reading: [%s], message: [%s]", path, e.getMessage());
                        }
                }
        
        
        
        
        private static void method(File file)  {
                System.out.println(file.getUsableSpace()); //źle
                
                try {
                        System.out.println( Files.size(file.toPath()));
                } catch (IOException e) {
                        System.err.printf("Error reading: [%s], message: [%s]", file, e.getMessage());
                }
        }
}
