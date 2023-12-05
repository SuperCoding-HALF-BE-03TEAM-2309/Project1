package com.supercoding.Project1.config;

import com.supercoding.Project1.dto.AuthInfo;
import com.supercoding.Project1.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class MemberAuthArgumentResolver implements HandlerMethodArgumentResolver {
    private final JwtService jwtService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return AuthInfo.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String authorization = webRequest.getHeader("Authorization");
        if(authorization == null || !authorization.startsWith("Bearer ")){
            throw new RuntimeException("UnauthorizedException");
        }
        String token = authorization.substring(7);
        Map<String, String> decodedToken = jwtService.decode(token);
        String email = decodedToken.get(JwtService.CLAIM_NAME_EMAIL);

        if(email == null){
            throw new RuntimeException("UnauthorizedException");
        }
        return AuthInfo.of(email);
    }
}
