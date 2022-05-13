package org.example.LiveSystem.annotations;

import org.example.LiveSystem.service.TokenService;
import org.springframework.core.MethodParameter;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


public class TokenUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private TokenService tokenService;

    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        TokenUser annotation = parameter.getParameterAnnotation(TokenUser.class);
        return annotation != null;
    }


    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        String token = webRequest.getHeader("token");
        if(ObjectUtils.isEmpty(token)){
            return null;
        }
        return tokenService.getUserInfoByToken(token);
    }
}
