package gb.lesson2;

import gb.lesson2.impl.CartImpl;
import gb.lesson2.impl.ProductImpl;
import gb.lesson2.impl.ProductRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AppConfig {

    @Bean
    Product product() {
        return new ProductImpl();
    }

    @Bean
    ProductRepository productRepository() {
        return new ProductRepositoryImpl();
    }

    @Bean
    Cart cart() {
        return new CartImpl();
    }
}
