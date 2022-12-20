package project;

import java.math.BigDecimal;

public class Car {
        private String color;
        private String vin;
        private String company;
        private String model;
        private String model_year;
        private BigDecimal price;
        
        @Override
        public String toString() {
                return "Car{" +
                               "color='" + color + '\'' +
                               ", vin='" + vin + '\'' +
                               ", company='" + company + '\'' +
                               ", model='" + model + '\'' +
                               ", model_year='" + model_year + '\'' +
                               ", price=" + price +
                               '}';
        }
        
        public String getColor() {
                return color;
        }
        
        public String getVin() {
                return vin;
        }
        
        public String getCompany() {
                return company;
        }
        
        public String getModel() {
                return model;
        }
        
        public String getModel_year() {
                return model_year;
        }
        
        public BigDecimal getPrice() {
                return price;
        }
        
        public Car(String color, String vin, String company, String model, String model_year, BigDecimal price) {
                this.color = color;
                this.vin = vin;
                this.company = company;
                this.model = model;
                this.model_year = model_year;
                this.price = price;
        }
}
