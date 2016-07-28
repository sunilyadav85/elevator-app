package com.baml.controller;

import com.baml.bean.ElevatorResult;

import java.util.Collection;

public interface ElevatorAppController {
    Collection<ElevatorResult> process(String fileName, String elevatorMode);
}
