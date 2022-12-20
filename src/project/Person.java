package project;

public class Person {
        private String name;
        private String surname;
        private String email;
        private String ip;
        
        @Override
        public String toString() {
                return "Person{" +
                               "name='" + name + '\'' +
                               ", surname='" + surname + '\'' +
                               ", email='" + email + '\'' +
                               ", ip='" + ip + '\'' +
                               '}';
        }
        
        public Person(String name, String surname, String email, String ip) {
                this.name = name;
                this.surname = surname;
                this.email = email;
                this.ip = ip;
        }
        
        public String getName() {
                return name;
        }
        
        public String getSurname() {
                return surname;
        }
        
        public String getEmail() {
                return email;
        }
        
        public String getIp() {
                return ip;
        }
}
