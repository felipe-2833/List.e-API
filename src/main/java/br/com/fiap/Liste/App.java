package br.com.fiap.Liste;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableCaching
@OpenAPIDefinition(info = @Info(title = "ListAnime", 
								version = "v1", 
								description = "API do projeto list.e", 
								contact = @Contact(name = "Felipe Fidelix", email = "felipelevy.fidelix@hotmail.com")))
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
