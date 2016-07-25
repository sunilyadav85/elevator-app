package com.baml.bean;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ElevatorResult {

    private List<Integer> floorsTravelled = new ArrayList<>();
    private int distanceTravelled;

    public void addToFloorsTravelled(int floor) {
        floorsTravelled.add(floor);
    }

    public void addToFloorsTravelled(Collection<Integer> floor) {
        floorsTravelled.addAll(floor);
    }

    public List<Integer> getFloorsTravelled() {
        return floorsTravelled;
    }

    public int getDistanceTravelled() {
        return distanceTravelled;
    }

    public void setDistanceTravelled(int distanceTravelled) {
        this.distanceTravelled = distanceTravelled;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
