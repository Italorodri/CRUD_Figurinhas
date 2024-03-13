package com.figurinhas.autoestudo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer{
    //  configuração de Cross-Origin Resource Sharing (CORS)
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**") //  Define o padrão de URL para o qual a configuração de CORS será aplicada.
        .allowedOrigins("*") //  Especifica quais origens são permitidas para acessar os recursos
        .allowedHeaders("*") // Define quais cabeçalhos de requisição podem ser utilizados nas requisições CORS. 
        .allowedMethods("GET","PUT","POST","DELETE"); // Lista os métodos HTTP que são permitidos nas requisições CORS

    }
}
