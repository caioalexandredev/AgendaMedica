package br.edu.ifto.sistemaconsulta.model.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //TODO Criar perfis Médico, Paciente, Secretário e Administrador
        //TODO Definifir permissões especificas (Paciente pode se cadastrar e Médico pode gerar consultas, Secretário pode Gerar Paciente e Gerar Agenda)
        //TODO Admnistrador tudo liberado
        http.authorizeHttpRequests(
        customizer ->
                        customizer
                            .requestMatchers("/css/**", "/js/**", "/images/**","/favicon/**").permitAll()
                            .anyRequest()
                            .authenticated()
                )
                .formLogin(customizer ->
                    customizer
                        .loginPage("/login")
                        .defaultSuccessUrl("/pacientes/listar", true)
                        .permitAll()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/**") // CSRF desabilitado aqui
                )
                .httpBasic(withDefaults())
                .logout(LogoutConfigurer::permitAll)
                .rememberMe(withDefaults());
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user1 = User.withUsername("user")
                .password(passwordEncoder().encode("123"))
                .roles("USER")
                .build();
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user1, admin);
    }

    /**
     * Com o método, instanciamos uma instância do encoder BCrypt e deixando o controle dessa instância como responsabilidade do Spring.
     * Agora, sempre que o Spring Security necessitar condificar um senha, ele já terá o que precisa configurado.
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
