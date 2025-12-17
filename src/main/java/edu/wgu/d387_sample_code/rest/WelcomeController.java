package edu.wgu.d387_sample_code.rest;

import edu.wgu.d387_sample_code.convertor.TimeZoneConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Project: d387-advanced-java
 * Package: edu.wgu.d387_sample_code.rest
 * <p>
 * User: AnDrew
 * Date: 2/24/2025
 * Time: 6:47 PM
 */
@RestController
@RequestMapping("/api")
public class WelcomeController {
    static ExecutorService messageExecutor = Executors.newFixedThreadPool(5);
    private static ConcurrentLinkedQueue<String> messageCollector;

    @GetMapping("/welcome")
    public Map<String, String> welcome() {
        messageCollector  = new ConcurrentLinkedQueue<>();
        loadMessagesConcurrently();

        // Build the final message after waiting for all tasks to complete
        try {
            Thread.sleep(1000); // Wait for tasks to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        StringBuilder result = new StringBuilder();
        messageCollector.forEach(result::append);

        Map<String, String> response = new HashMap<>();
        response.put("message", result.toString());
        return response;
    }

    @GetMapping("/presentation")
    public Map<String, String> nextPresentation() {

        // Current Date and time in UTC
        ZonedDateTime utcNow = ZonedDateTime.now(ZoneId.of("UTC"));

        // // Presentation date and time in UTC (10 days from now, at 7:30 PM)
        ZonedDateTime presentationUtc = utcNow.plusDays(10).withHour(19).withMinute(30).withSecond(0).withNano(0);

        String utcTime = presentationUtc.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        String utcDate = utcNow.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String etTime = TimeZoneConverter.convertTime(utcTime, ZoneId.of("UTC"), ZoneId.of("America/New_York"));
        String mtTime = TimeZoneConverter.convertTime(utcTime, ZoneId.of("UTC"), ZoneId.of("America/Denver"));

        String presentation = "Join us for an online presentation held at the Landon Hotel on ";
        presentation += utcDate;
        presentation += " at " + utcTime + " UTC | ";
        presentation += mtTime + " Mountain Time | ";
        presentation += etTime + " Eastern Time!";


        Map<String, String> response = new HashMap<>();
        response.put("message", presentation);
        System.out.println(response);
        return response;
    }

    public static void loadMessagesConcurrently() {
        Properties properties = new Properties();

        messageExecutor.execute(() -> {
            try {
                InputStream stream = new ClassPathResource("Welcome_en_US.properties").getInputStream();
                properties.load(stream);
                String message = properties.getProperty("welcomeMessage") + " thread 1 ";
                messageCollector.add(message);
                System.out.println(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        messageExecutor.execute(() -> {
            try {
                InputStream stream = new ClassPathResource("Welcome_fr_CA.properties").getInputStream();
                properties.load(stream);
                String message = properties.getProperty("welcomeMessage") + " thread 2 ";
                messageCollector.add(message);
                System.out.println(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        messageExecutor.execute(() -> {
            try {
                InputStream stream = new ClassPathResource("Welcome_fr_CA.properties").getInputStream();
                properties.load(stream);
                String message = properties.getProperty("welcomeMessage") + " thread 3 ";
                messageCollector.add(message);
                System.out.println(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        messageExecutor.execute(() -> {
            try {
                InputStream stream = new ClassPathResource("Welcome_en_US.properties").getInputStream();
                properties.load(stream);
                String message = properties.getProperty("welcomeMessage") + " thread 4 ";
                messageCollector.add(message);
                System.out.println(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
