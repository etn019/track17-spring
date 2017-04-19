package track.container;

import track.container.beans.Car;
import track.container.config.ConfigReader;

import java.io.File;

/**
 *
 */
public class Main {


    public static class MyClass {
        private String theField;
    }

    public static void main(String[] args) {

        ConfigReader reader = new JsonConfigReader();
        try {
            Container container = new Container(reader.parseBeans(new File("src/main/resources/config.json")));

            Car car = (Car) container.getByClass("track.container.beans.Car");
            System.out.println(car);
            Car car1 = (Car) container.getById("carBean");
            System.out.println(car1);
            container.printMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

