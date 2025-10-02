package demo.shop.Configuration;

import demo.shop.Entity.User;
import demo.shop.Repository.UserRepository;
import java.util.HashSet;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfig {
  PasswordEncoder passwordEncoder;

  // using when the first time run project to create account admin
  @Bean
  ApplicationRunner applicationRunner(UserRepository userRepository){
    return args -> {
      if (userRepository.findByUsername("admin").isEmpty()) {

        var roles = new HashSet<String>();
        roles.add("ADMIN");

        User admin = User.builder()
            .username("admin")
            .password(passwordEncoder.encode("admin"))
            .roles(roles)
            .build();
        userRepository.save(admin);
      }
      log.warn("admin user has been created with default password: admin, please change it");
    };
  }
}
