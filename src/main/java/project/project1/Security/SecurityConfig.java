package project.project1.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)//어노테이션 방식 인가 방식 허용
public class SecurityConfig {

    @Autowired
    private LoginFailHandler loginFailHandler;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private CustomAuthenticationProvider authenticationProvider;

    @Bean
    public AuthenticationProvider authenticationProvider(){
        return new CustomAuthenticationProvider();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations()));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
         http
                 .authenticationProvider(authenticationProvider)
                 .userDetailsService(customUserDetailsService)
                 .authorizeHttpRequests()
                 .antMatchers("/members/add","/","/home","/login*").permitAll()
                 .antMatchers("/admin/**").hasRole(MemberRole.ADMIN.getName())
                 .anyRequest().hasRole(MemberRole.USER.getName())

                 .and()
                 .formLogin()
                 .loginPage("/login")
                 .usernameParameter("loginId")
                 .passwordParameter("password")
                 .loginProcessingUrl("/login")
                 .defaultSuccessUrl("/")
//                 .authenticationDetailsSource()
                 .successHandler(loginSuccessHandler)
                 .failureHandler(loginFailHandler)
                 .permitAll()

                 .and()
                 .logout()
                 .logoutUrl("/logout")
                 .logoutSuccessUrl("/")
                 .invalidateHttpSession(true);


         return http.build();
    }

}
