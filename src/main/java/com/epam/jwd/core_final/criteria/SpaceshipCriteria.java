package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.*;

import java.util.Map;

/**
 * Should be a builder for {@link Spaceship} fields
 */
public class SpaceshipCriteria extends Criteria<Spaceship> {
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
