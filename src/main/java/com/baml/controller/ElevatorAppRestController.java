package com.baml.controller;

import com.baml.bean.ElevatorResult;
import com.baml.enums.ElevatorMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class ElevatorAppRestController implements ElevatorAppController {

    @Autowired
    private ElevatorAppControllerHelper controllerHelper;

    @RequestMapping(value = "/elevator/result", method = RequestMethod.GET)
    public Collection<ElevatorResult> process(@RequestParam(name = "fileName", required = true) String fileName,
                                              @RequestParam(name = "elevatorMode", required = true) String elevatorMode) {
        return controllerHelper.processRequest(fileName, ElevatorMode.valueOf(elevatorMode.toUpperCase()));
    }
}