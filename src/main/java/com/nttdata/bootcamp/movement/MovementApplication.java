package com.nttdata.bootcamp.movement;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Movement API", version = "1.0", description = "Documentation Movement API v1.0"))
public class MovementApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovementApplication.class, args);
    }

}
