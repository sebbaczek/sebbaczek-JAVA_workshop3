package project;
/*
Załóżmy, że dostaliśmy plik client-car-purchase-spreadsheet.csv ze statystykami o sprzedaży
samochodów przez Internet. Klient może dokonać zakupu auta przez Internet, wybierając markę, model
oraz kolor. Na podstawie takich zakupów zbieramy dane, które zostały zbiorczo zapisane w pliku
client-car-purchase-spreadsheet.csv. Plik client-car-purchase-spreadsheet.csv został umieszczony w
poście z projektem. Plik został stworzony za pomocą strony https://www.mockaroo.com/ i zawiera
losowo wygenerowane dane.
Plik taki będzie zawierał informacje takie jak:
• numer porządkowy zakupu (id),
• imię i nazwisko kupującego (first_name i last_name),
• email kupującego (email),
• adres IP z którego zakup został dokonany (ip_address),
• kolor kupionego auta (color),
• numer VIN kupionego samochodu, czyli taki PESEL samochodu (car_vin),
• marka kupionego samochodu (car_company),
• model kupionego samochodu (car_model),
• rok modelowy kupionego samochodu (car_model_year),
• cena w jakiej samochód został sprzedany, ceny są losowe, więc załóżmy, że klienci dobrze się targują
co do eurocenta, ceny są w euro (car_price)
• kraj, z którego dokonano zakupu auta (country),
• miasto, z którego dokonano zakupu auta (city),
• kiedy dokonano zakupu (date)
Wyobraź sobie teraz, że dostajemy taki plik od kogoś i na tej podstawie mamy wykonać kilka
poleceń/operacji. Polecenia są wypisane w taki sposób, że aby wykonać kolejny krok, trzeba wykonać
kroki poprzednie, ale niekoniecznie wszystkie ὠ.

1. Wczytaj dane z pliku i przemapuj je na obiekty, gdzie klasy tych obiektów będą odpowiadały
schematowi poniżej. Zwróć uwagę na kodowanie Stringów, gdyż w plikach specjalnie są podane
znaki diaktryczne dla różnych języków.

klasa Purchase {
  BigDecimal id_porzadkowe_zakupu;
  Osoba osoba;
  Samochod samochod;
  Lokalizacja lokalizacja;
  DateTime data_zakupu;
}
klasa Osoba {
  String imie;
  String nazwisko;
  String email;
  String ip;
}
klasa Samochod {
  String color;
  String vin;
  String company;
  String model;
  String model_year;
  BigDecimal price;
}
klasa Lokalizacja {
  String country
  String city
}


2. Zapisz dane do oddzielnych plików w taki sposób, żeby zostały one rozdzielone per marka
samochodu. Oznacza to, że otrzymamy oddzielne pliki np. dla BMW, Mercedesa, Lexusa itp. Każdy z
tych plików będzie zawierał dane dla jednej marki.
Następnie wydrukuj na ekranie rozmiar każdegoz plików po zapisie. Wydrukuj te dane posortowane malejąco po marce
auta, np. Lexus:152, BMW:134 itp. W ten sposób jesteśmy w stanie zgrubnie oszacować wielkość sprzedaży dla danej marki
(zaznaczam, że jest to zgrubne/niedokładne i nie robilibyśmy tego w ten sposób w praktyce ὠ).

3. Wygeneruj raport do oddzielnego pliku zawierający poniższe informacje. Cel tego raportu to
pokazanie jaka była średnia cena sprzedaży auta przy podziale na markę oraz model.
◦ id (id porządkowe wpisu),
◦ marka_auta
◦ model_auta
◦ średnia_cena_sprzedaży_auta
◦ ilość sprzedanych sztuk

4. Wygeneruj raport do oddzielnego pliku zawierający poniższe informacje. Nie zapisuj do pliku dni, w
których nie sprzedało się żadne auto. Wygeneruj dwa warianty raportu. W pierwszym wariancie
posortuj dane rosnąco po datach. W drugim wariancie posortuj dane malejąco po ilości sprzedanych
aut w konkretnym dniu.
◦ id (id porządkowe wpisu),
◦ data sprzedaży
◦ ilość sprzedanych aut
 */

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;



public class Main {
        public static void main(String[] args) throws IOException {
                Path path = Paths.get("./../workshop3/src/project/data.csv");

//                Ad.1
                
                Stream<List<Object>> collect =
                        Files.lines(path)
                                .skip(1)
                                .map(p -> Arrays.stream(p.split(","))
                                                  .collect(Collectors.toList()));
                
                List<Purchase> purchaseList = collect
                                                      .map(p -> addVariables((ArrayList) p))
//                                                .forEach(p -> System.out.println(p));
                                                      .toList();
                
                System.out.println(purchaseList);
                //###########

//                Ad 2.
                Map<String, List<Purchase>> grouped = purchaseList.stream()
                                                              .collect(Collectors.groupingBy(purchase -> purchase.getCar().getCompany()));
                
                for (Map.Entry<String, List<Purchase>> entry : grouped.entrySet())
                {
                        Path path1 = Paths.get("./../workshop3/src/project/cars");
                       
                        try (BufferedWriter bufferedWriter =
                                     Files.newBufferedWriter(Path.of(path1 + "/" + entry.getKey() +".txt")))
                        {
                                for (Purchase purchase : entry.getValue())
                                {
                                        bufferedWriter.write(purchase.toString());
                                        bufferedWriter.newLine();
                                }
                                
                        }
                }
                PrintingUtility.printMap(grouped);
                
                Path path2 = Paths.get("./../workshop3/src/project/cars");
                Map<Path, Long> collect1 = Files.walk(path2)
                                                   .collect(Collectors.toMap(
                                                           p -> p.getFileName(),
                        
                                                           p -> {
                                                    
                                                                   try {
                                                                           return Files.size(p);
                                                                   } catch (IOException e) {
                                                                           throw new RuntimeException(e);
                                                                   }
                                                           }
                                                   ));
                List<Map.Entry<Path, Long>> sorted = collect1.entrySet().stream()
//                                                               .sorted(Map.Entry.comparingByValue());
                                                               .sorted(Comparator.comparing((Map.Entry<Path, Long> v) -> v.getValue()).reversed())
//                                                             .sorted(Comparator.naturalOrder().reversed())
                                                             
//                                                               .sorted()
                                                               .collect(Collectors.toList());
                System.out.println("\nAd 2.");
                for (Map.Entry<Path, Long> pathLongEntry : sorted) {
                        System.out.println(pathLongEntry);
//                        System.out.println();
                }
                //###########

//                Ad 3. - niezrobione do końca
                Path path3 = Paths.get("./../workshop3/src/project/report.txt");
        
        
                var grouped2 = purchaseList.stream()
                                                               .collect(Collectors.groupingBy(n->n.getCar().getCompany(),
                                                                       Collectors.toCollection(
                                                                               ArrayList::new
                                                                               
)));
                Map<String, List<Object>> map1 = new HashMap<String, List<Object>>();
                for (Map.Entry<String, ArrayList<Purchase>> entry : grouped2.entrySet()) {
//                        map1.put(
//                                entry.getKey(),Map.of(
//                                        "amount",  entry.getValue().size()
//                                ));
                        map1.put(
                                entry.getKey(), mapValues(entry,grouped2)
                          );
                }
//                Map<String, List<Integer,List<Map<String,String>>>> map = (Map<String, List<Integer,List<Map<String,String>>>>) map1;
                
                List<ArrayList> collect2 = map1.entrySet().stream()
                                                   .map(e -> (ArrayList) e.getValue().get(1))
//                                                   .peek(e -> System.out.println(e))
//                                                             .map(e -> e.stream())
                
                                                   .collect(Collectors.toList());
        
//                System.out.println("\nAd 3.");
//                System.out.println(collect2);
                PrintingUtility.printMap(map1);
        
                Path path5 = Paths.get("./../workshop3/src/project/ad3.csv");
            
                try (
                        BufferedWriter bufferedWriter = Files.newBufferedWriter(path5)
                ) {
//                        bufferedWriter.write(PurchaseMappingService.CSV_HEADER)
                                map1.forEach((key, value) -> {
                                        try {
                                                bufferedWriter.write("COMPANY: " + key + ", \nAMOUNT: " + value.get(0)+
                                                                             "\n"+ value.get(1)+  "\n\n");
                                        } catch (IOException e) {
                                                throw new RuntimeException(e);
                                        }
                                });
                    
                }
  

//                        map1.put(entry.getKey(), Map.of(
//                                "amount", entry.getValue().size(),
//                                "model", entry.getValue()
//
//                        ));


//                System.out.println("\nAd 3.");
//                PrintingUtility.printMap(map1);
        
//                map1.entrySet().stream()
//                                                   .flatmap(e -> (ArrayList) e.getValue().get(1))

                
//                                                   .collect(Collectors.toList());
        
 
 
                
 /*               //castowanie Object na HashMap
                for (ArrayList list : collect2) {
                        for (Object o : list) {
                                //castowanie Object na HashMap
                                Map<String, String> map = (Map<String, String>) o;
                                for (Map.Entry<String, String> s : map.entrySet()) {
                                        if("model".equals(s.getKey())){
                                                System.out.println(s.getValue());
                }} } }
*/






//               grouped2.entrySet().stream()
//                                                        .map(p -> p.getValue().size());

//                                             ))
//                           .collect(Collectors.groupingBy(purchase -> purchase.getValue()));
//                   .collect(Collectors.toMap(
//                           p -> p.getKey(),
//
//                           p -> {
//
//
//                           }
//                   ));
//                System.out.println(sorted);


                
                
                
                
                
                
//                PrintingUtility.printMap(collect2);

//                             .forEach(f-> {
//                                     try {
//                                             System.out.println(Files.size(f));
//                                     } catch (IOException e) {
//                                             throw new RuntimeException(e);
//                                     }
//                             });

//                grouped.entrySet().stream()
//                                          .map(Map.Entry::getKey)
//                                                  Paths.get("./../workshop3/src/project/")
//
//
//                                          );

//                var purchaseMapPerCompany = grouped.entrySet().stream()
//                                                    .collect(Collectors
//                                                                     .toMap(t -> t.getKey().getCompany(),
//                                                                             List::of,
//
//                                                                             (List<Purchase> cL, List<Purchase> nL) -> Stream.concat(cL.stream(), nL.stream())
//                                                                                                                               .collect(Collectors.toList()))
//                                                                                                     entry -> entry.getValue().stream()
//                                                                                                                      .map(Purchase::toString)
//                                                    ));
                
                
                //####################

//                Ad 4.
        
           
        
                TreeMap<LocalDate, Long> mapByDate = purchaseList.stream()
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
              Path path6 = Paths.get("./../workshop3/src/project/ad4_"+suffix+".csv");
                FileService.saveToFile(path6, data, "id,date,count");
        }
        
        private static List<Object> mapValues(Map.Entry<String, ArrayList<Purchase>> entry, Map<String, ArrayList<Purchase>> grouped2) {
     
                List<Object> list = new ArrayList<>();
                List<Object> list2 = new ArrayList<>();
                for (Purchase purchase : entry.getValue()) {
                        Map<String, Object> map3 = new HashMap<String, Object>();
               
                            map3.putAll(
                                     Map.of   (
//                                        "amount",  entry.getValue().size(),
                                        "model",  purchase.getCar().getModel(),
                                        "price",  purchase.getCar().getPrice(),
                                        "id",  purchase.getId()
                                )
                            );
                        list.add( map3);
                }
                list2.add( entry.getValue().size());
                list2.add(list);
                return list2;
        }
        
        private static Purchase addVariables(List<ArrayList> p) {
//                System.out.println(p.getClass());
//                System.out.println(p.get(1));
//                for (List<String> list : p) {
                Long id = Long.parseLong(String.valueOf(p.get(0)));
                String name = String.valueOf(p.get(1));
                String surname = String.valueOf(p.get(2));
                String email = String.valueOf(p.get(3));
                String ip = String.valueOf(p.get(4));
                String color = String.valueOf(p.get(5));
                String vin = String.valueOf(p.get(6));
                String company = String.valueOf(p.get(7));
                String model = String.valueOf(p.get(8));
                String model_year = String.valueOf(p.get(9));
                BigDecimal price =
                        new BigDecimal(String.valueOf(p.get(10)).replace("\"", "").substring(1));
                String country = String.valueOf(p.get(11));
                String city = String.valueOf(p.get(12));
                LocalDate date = LocalDate.parse((CharSequence) p.get(13));
                return new Purchase(id, new Person(name, surname, email, ip), new Car(color, vin, company, model,
                        model_year, price), new Location(country, city), date);

//                System.out.println(p.get(0));
        
        
        }
}
