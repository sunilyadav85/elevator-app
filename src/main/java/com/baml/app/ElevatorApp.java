package com.baml.app;

import com.baml.controller.ElevatorAppConsoleController;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootApplication
public class ElevatorApp implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Autowired
    private ElevatorAppConsoleController consoleController;

    public static void main(String[] args) {

        String appName = System.getProperty("app.name");
        if (appName == null) {
            appName = "ElevatorApp";
        }

        String envProperty = "elevator.app.environment";
        String env = getArgOrProperty(envProperty, args);
        if (env == null) {
            String errorMsg = String.format("System property %s must be defined for %s.", envProperty, appName);
            throw new IllegalStateException(errorMsg);
        }

        Predicate<String> isParameter = input -> input.startsWith("--");
        List<String> argsList = Arrays.asList(args);
        Collection<String> parameters = Collections2.filter(argsList, isParameter);
        Collection<String> configurations = Collections2.filter(argsList, Predicates.not(isParameter));

        SpringApplication springApplication = new SpringApplication(Lists.asList(ElevatorApp.class, configurations.toArray()).toArray());
        springApplication.setWebEnvironment(getArgOrProperty("elevator.app.http.port", args) != null);
        springApplication.setAdditionalProfiles(env);
        springApplication.run(parameters.toArray(new String[0]));

        applicationContext.getBean(ElevatorApp.class).printWelcomeMessageAndProcessInput();
    }

    private void printWelcomeMessageAndProcessInput() {
        System.out.println("************Welcome to Elevator app ***************");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the file name with location: ");
        String fileName = scanner.nextLine().trim();

        System.out.println("Please enter Mode (A/B): ");
        char mode = scanner.next().trim().charAt(0);

        consoleController.process(fileName, mode);
    }

    private static String getArgOrProperty(String argOrPropertyName, String[] args) {
        Pattern pattern = Pattern.compile(String.format("--%s=(.*)", argOrPropertyName));
        for (String arg : args) {
            Matcher matcher = pattern.matcher(arg);
            if (matcher.matches()) {
                return matcher.group(1);
            }
        }
        return System.getProperty(argOrPropertyName);
    }

    @Override
    public void setApplicationContext(ApplicationContext appContext) throws BeansException {
        applicationContext = appContext;
    }
}
