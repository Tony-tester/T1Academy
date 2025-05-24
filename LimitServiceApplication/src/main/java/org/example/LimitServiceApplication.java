package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableScheduling
public class LimitServiceApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(LimitServiceApplication.class, args);
    }
}
