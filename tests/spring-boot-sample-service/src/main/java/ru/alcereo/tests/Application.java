package ru.alcereo.tests;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by alcereo on 22.07.17.
 */
@SpringBootApplication
@RestController
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private AtomicLong counter = new AtomicLong();

    @GetMapping("")
    public String counterGet(){
        return "Test request number: "+counter.incrementAndGet();
    }

}
