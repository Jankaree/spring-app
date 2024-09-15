package com.example.spring_secure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
@EnableWebSecurity
@Configuration
public class SpringConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {



            http.authorizeHttpRequests(
                    authorizeRequests -> authorizeRequests
                            .requestMatchers("/register").hasRole("ADMIN")
                            .requestMatchers("/admin").hasRole("ADMIN")
                            .requestMatchers("/loggedout").permitAll()
                            .requestMatchers("/divide").permitAll()

                            .anyRequest().authenticated()).formLogin(formLogin -> formLogin
                    .defaultSuccessUrl("/",true)
                    .failureUrl("/login?error=true")
                    .permitAll());
            http.logout(logout ->
                    logout
                            .logoutUrl("/perform_logout")
                            .logoutSuccessUrl("/loggedout")
                            .permitAll()).
                    csrf(AbstractHttpConfigurer::disable)

                    .httpBasic(Customizer.withDefaults());;



            return http.build();

        }

        @Bean
        public InMemoryUserDetailsManager userDetailsService() {

            InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
            var user = User.builder()
                    .username("user")
                    .password(passwordEncoder().encode("password"))
                    .roles("USER")
                    .build();
            inMemoryUserDetailsManager.createUser(user);

            var user2 = User.builder()
                    .username("Martin")
                    .password(passwordEncoder().encode("Partin"))
                    .roles("ADMIN")
                    .build();
            inMemoryUserDetailsManager.createUser(user2);
            return inMemoryUserDetailsManager;

        }
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }


    }



