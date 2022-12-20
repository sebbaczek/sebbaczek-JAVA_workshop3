package project;

import java.util.Map;

public class PrintingUtility {
        static void printMap(final Map<?, ?> map) {
                map.forEach((key, value) -> System.out.println("KEY: " + key + ", VALUE: " + value));
        }
}