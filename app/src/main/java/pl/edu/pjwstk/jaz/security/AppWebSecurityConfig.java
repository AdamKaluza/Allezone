package pl.edu.pjwstk.jaz.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.pjwstk.jaz.auth.ExampleFilter;
import pl.edu.pjwstk.jaz.auth.UserSession;

@Configuration
public class AppWebSecurityConfig {

    @Bean
    public FilterRegistrationBean<ExampleFilter> exampleFilter(UserSession userSession){
        FilterRegistrationBean<ExampleFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new ExampleFilter(userSession));
        registrationBean.addUrlPatterns("/auth0/*");

        return registrationBean;

    }
}
