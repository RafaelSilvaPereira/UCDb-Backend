package com.ufcg.cc.psoft.ucdb.controller;
import io.jsonwebtoken.Jwts;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class TokenFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        String header = req.getHeader("Authorization");

        if(header == null || !header.startsWith("Token: ")) { /*estou usando a sintaxe "Token: " no lugar de  "Bearer"*/
            throw new ServletException("Token inexistente ou mal formatado!");
        }

        // Extraindo apenas o token do cabecalho.
        String token = header.substring(7);

        Jwts.parser().setSigningKey("HAPPY_TOMATO").parseClaimsJws(token).getBody();

        try {
            chain.doFilter(request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}