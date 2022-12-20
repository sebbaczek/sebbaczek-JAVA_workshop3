package project;

import java.math.BigDecimal;
import java.time.LocalDate;
public class PurchaseMappingService {
        public static final String CSV_HEADER = "id,first_name,last_name,email,ip_address,color,car_vin,car_company,car_model,car_model_year,car_price,country,city,date";
        public static Purchase mapPurchase(String inputData) {
                String[] row = inputData.split(",");
                long id = Long.parseLong(row[0]);
                String firstName = row[1];
                String lastName = row[2];
                String email = row[3];
                String ipAddress = row[4];
                String color = row[5];
                String vin = row[6];
                String company = row[7];
                String model = row[8];
                String modelYear = row[9];
                BigDecimal price = new BigDecimal(row[10].replace("\"", "").substring(1));
                String country = row[11];
                String city = row[12];
                LocalDate date = LocalDate.parse(row[13]);
                return new Purchase(
                        id,
                        new Person(firstName, lastName, email, ipAddress),
                        new Car(color, vin, company, model, modelYear, price),
                        new Location(country, city),
                        date
                );
        }
        public static String toCsvRow(Purchase purchase) {
                return String.format(
                        "%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                        purchase.getId(),
                        purchase.getPerson().getName(),
                        purchase.getPerson().getSurname(),
                        purchase.getPerson().getEmail(),
                        purchase.getPerson().getIp(),
                        purchase.getCar().getColor(),
                        purchase.getCar().getVin(),
                        purchase.getCar().getCompany(),
                        purchase.getCar().getModel(),
                        purchase.getCar().getModel_year(),
                        purchase.getCar().getPrice(),
                        purchase.getLocation().getCountry(),
                        purchase.getLocation().getCity(),
                        purchase.getDate()
                );
        }
}
