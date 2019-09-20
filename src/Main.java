import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        Scanner scanner = new Scanner(System.in);
        properties.load(new FileInputStream(scanner.nextLine()));

        List<Class<?>> list = new ArrayList<>();
        list.add(Student.class);
        list.add(Employee.class);
        GeneratorOfClasses generatorOfClasses = new GeneratorOfClasses(list, MyAnnotation.class, "D:\\my files\\softserve\\Task4\\src\\pr.properties");
        try {
            List<Object> objects = generatorOfClasses.instantiateClasses();
            System.out.println(objects);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
