package gb.SpringRest_2;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class HomeworkApplication {

    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(HomeworkApplication.class, args);
    }

}