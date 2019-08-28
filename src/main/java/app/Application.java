package app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Bean
    public FilterRegistrationBean<CorsFilter> simpleCorsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

    @Override
    public void run(String... args) {

//        System.out.println(jpaUserRepository.save(User.builder().emailAddress("gorkofi@gmail.com").password("1234643").build()));
//        System.out.println(userService.registerUser(User.createUserWithExistingId("gore", "2343")).getUserId());
//        System.out.println(userService.registerUser(User.createUserWithExistingId("rfsre", "2342")).getUserId());
//        System.out.println(userService.registerUser(User.createUserWithExistingId("red", "2342")).getUserId());
//        System.out.println(userService.registerUser(User.createUserWithExistingId("wer", "2342")).getUserId());
//        System.out.println(userService.registerUser(User.createUserWithExistingId("qwe", "2342")).getUserId());
//        System.out.println(userService.registerUser(User.createUserWithExistingId("lope", "2342")).getUserId());
//        System.out.println(userService.registerUser(User.createUserWithExistingId("qasd", "2342")).getUserId());
//        System.out.println(userService.registerUser(User.createUserWithExistingId("azxc", "2342")).getUserId());
//        userService.registerUser(User.createUserWithExistingId("mkjlj", "2342"));

    }
}
