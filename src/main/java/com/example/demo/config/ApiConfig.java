package com.example.demo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Документация Api к SpringAOPTrackTime",
                version = "2.0.2",
                contact = @Contact(
                        name = "Кирилл"
                )
        )
)
public class ApiConfig {
}
