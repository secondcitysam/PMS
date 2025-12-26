package crud_cr.mvc_1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns("/home" ,
                        "/projects/**",
                        "/tasks/**"
                )
                .excludePathPatterns("/login",
                        "/signup",
                        "/css/**",
                        "/js/**",
                        "/images/**"
                );

    }
}
