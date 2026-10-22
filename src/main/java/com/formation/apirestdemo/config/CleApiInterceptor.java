package com.formation.apirestdemo.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class CleApiInterceptor implements HandlerInterceptor {

    private static final String CLE_ATTENDUE = "formation";

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        // On ne contrôle que les requêtes DELETE
        if ("DELETE".equalsIgnoreCase(request.getMethod())) {

            String cle = request.getHeader("X-Api-Key");

            // Clé absente ou incorrecte
            if (!CLE_ATTENDUE.equals(cle)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
        }

        return true;
    }
}
