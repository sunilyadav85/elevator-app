package com.baml.controller;

import com.baml.bean.Elevator;
import com.baml.bean.ElevatorResult;
import com.baml.bean.MoveRequest;
import com.baml.service.ElevatorContext;
import com.baml.service.ElevatorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Component
public class ElevatorAppConsoleController {

    @Autowired
    private ElevatorContext elevatorContext;

    @Autowired
    private ElevatorAppControllerHelper controllerHelper;

    public void process(String fileName, char mode)  {
        controllerHelper.initializeElevatorContext(mode);
        Elevator elevator = new Elevator();
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(rowOfMoveRequest -> controllerHelper.getElevatorRequestMap(rowOfMoveRequest).forEach((k, v) -> {
                elevator.addMoveRequests(k);
                elevator.setInitialFloor(v);
                ElevatorResult elevatorResult = elevatorContext.executeElevatorStrategy(elevator);

                controllerHelper.printResult(elevatorResult);
            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}