package controller;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
public class MainController {
    private ConfigurableApplicationContext springContext;

    public  void  init() throws Exception {
        System.setProperty("server.port", "8082");
        springContext = SpringApplication.run(MainController.class);
    }

    public void stop() throws Exception {
        springContext.close();
    }

    public static void main(String args[]) {
        try {
            new MainController().init();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}