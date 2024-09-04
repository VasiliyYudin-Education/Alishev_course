package ru.alishev.springcourse.FirstSecurityApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) //for using @PreAuthorized
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

//    using userDetailsService without authentication provider
    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

//    using authentication provider
//    private  final AuthenticationProvider authenticationProvider;
//
//    @Autowired
//    public SecurityConfig(AuthenticationProvider authenticationProvider) {
//        this.authenticationProvider = authenticationProvider;
//    }


//    using authentication provider
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//        return http.getSharedObject(AuthenticationManagerBuilder.class)
//                .authenticationProvider(authenticationProvider)
//                .build();
//    }


//    using userDetailsService without authentication provider
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(getPasswordEncoder());
        return authManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
//                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/auth/login", "/error").permitAll()
                        .requestMatchers("/auth/registration").permitAll()
//                        .requestMatchers("/admin").hasRole("ADMIN")  constraint is relocated to annotation in controller
                        .anyRequest().authenticated()
                )
//                .formLogin(withDefaults())
                .formLogin(form -> form
                        .loginPage("/auth/login") // Указываем кастомную страницу входа
                        .loginProcessingUrl("/process_login") //post from login page with password and username fields
                        .defaultSuccessUrl("/hello",true)
                        .failureUrl("/auth/login?error=true")
                        .permitAll())
                .logout(logout -> logout.logoutUrl("/auth/logout")
                        .permitAll() // Разрешаем доступ к странице выхода всем
                )
                .httpBasic(withDefaults());

        return http.build();
    }
}


