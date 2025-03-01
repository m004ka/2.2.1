package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.CarService;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.GenerationType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        CarService carService = context.getBean(CarService.class);
        List<Car> cars = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Car car = (Car.builder()
                    .series(i)
                    .model("BMW")
                    .build());
            carService.add(car);
            cars.add(car);
        }

        UserService userService = context.getBean(UserService.class);

        for (int i = 0; i < 5; i++) {
            userService.add(User.builder()
                    .car(cars.get(i))
                    .email("Email" + i)
                    .firstName("FirstName" + i)
                    .lastName("LastName")
                    .build());
        }

        List<User> users = userService.listUsers();
        for (User user : users) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println("Car" + user.getCar());
        }

        System.out.println();
        System.out.println("Достаем пользователя по машине"  + userService.findUserByCar(cars.get(2)));


        context.close();
    }
}
