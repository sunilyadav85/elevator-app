package com.baml.controller;

import com.baml.bean.Elevator;
import com.baml.bean.ElevatorResult;
import com.baml.service.ElevatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@RestController
public class ElevatorAppRestController {

    @Autowired
    private ElevatorContext elevatorContext;

    @Autowired
    private ElevatorAppControllerHelper controllerHelper;

    @RequestMapping(value = "/elevator/result", method = RequestMethod.GET)
    public Collection<ElevatorResult> process(@RequestParam(name = "fileName", required = true) String fileName,
                                               @RequestParam(name = "mode", required = true) char mode) {
        controllerHelper.initializeElevatorContext(mode);
        Elevator elevator = new Elevator();
        List<ElevatorResult> elevatorResults = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(rowOfMoveRequest -> controllerHelper.getElevatorRequestMap(rowOfMoveRequest).forEach((k, v) -> {
                elevator.addMoveRequests(k);
                elevator.setInitialFloor(v);
                elevatorResults.add(elevatorContext.executeElevatorStrategy(elevator));

            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return elevatorResults;
    }
}