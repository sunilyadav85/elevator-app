package com.baml.bean;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ElevatorResult {

    private final List<Integer> floorsTravelled = new ArrayList<>();
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ElevatorResult that = (ElevatorResult) o;

        if (distanceTravelled != that.distanceTravelled) return false;
        if (floorsTravelled != null ? !floorsTravelled.equals(that.floorsTravelled) : that.floorsTravelled != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = floorsTravelled != null ? floorsTravelled.hashCode() : 0;
        result = 31 * result + distanceTravelled;
        return result;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
