package org.example.LiveSystem.config;

import org.example.LiveSystem.annotations.TokenUserMethodArgumentResolver;
import org.example.LiveSystem.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author jun
 * @date 2021/11/16
 */
@Configuration
public class EstWebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        // 在这里把自定义的参数解析器添加进去
        resolvers.add(tokenUserHandlerMethodArgumentResolver());
    }

    @Autowired
    private TokenService tokenService;

    @Bean
    public HandlerMethodArgumentResolver tokenUserHandlerMethodArgumentResolver() {
        TokenUserMethodArgumentResolver argumentResolver =
                new TokenUserMethodArgumentResolver();
        argumentResolver.setTokenService(tokenService);
        return argumentResolver;
    }
}
