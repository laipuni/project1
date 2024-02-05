package project.project1.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import project.project1.role.MemberRole;


@Order(0)
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
                 .successHandler(loginSuccessHandler)
                 .failureHandler(loginFailHandler)
                 .permitAll()

                 .and()
                 .logout()
                 .logoutUrl("/logout")
                 .logoutSuccessUrl("/")
                 .deleteCookies()
                 .invalidateHttpSession(true)

                 .and()
                 .rememberMe()
                 .rememberMeParameter("remember")
                 .tokenValiditySeconds(3600)
                 .userDetailsService(customUserDetailsService);


         return http.build();
    }

}
