package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.*;

import java.util.Map;

/**
 * Should be a builder for {@link Spaceship} fields
 */
public class SpaceshipCriteria extends Criteria<Spaceship> {
    private Long flightDistance;
    private boolean isReadyForNextMissions = true;
    private String name;



    public Long getFlightDistance() {
        return flightDistance;
    }

    public void setFlightDistance(Long flightDistance) {
        this.flightDistance = flightDistance;
    }

    public boolean isReadyForNextMissions() {
        return isReadyForNextMissions;
    }

    public void setReadyForNextMissions(boolean readyForNextMissions) {
        isReadyForNextMissions = readyForNextMissions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class Builder extends BaseBuilder<Spaceship> {

        public SpaceshipCriteria.Builder addName(String name){
            thisClass = new Spaceship(name);
            return this;
        }

        public SpaceshipCriteria.Builder addCrew(Map<Role,Short> crew){
            thisClass.setCrew(crew);
            return this;
        }

        public SpaceshipCriteria.Builder addFlightDistance(Long flightDistance){
            thisClass.setFlightDistance(flightDistance);
            return this;
        }

        @Override
        protected Spaceship getThisClass() {
            return thisClass;
        }

    }



}
