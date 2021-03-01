package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.*;


import java.time.LocalDateTime;
import java.util.List;

/**
 * Should be a builder for {@link com.epam.jwd.core_final.domain.FlightMission} fields
 */
public class FlightMissionCriteria extends Criteria<FlightMission> {
    public static class Builder extends BaseBuilder<FlightMission>{

        public FlightMissionCriteria.Builder addName(String name){
            thisClass = new FlightMission(name);
            return this;
        }

        public FlightMissionCriteria.Builder addStartDate(LocalDateTime startDate){
            thisClass.setStartDate(startDate);
            return this;
        }

        public FlightMissionCriteria.Builder addEndDate(LocalDateTime end){
            thisClass.setEndDate(end);
            return this;
        }

        public FlightMissionCriteria.Builder addDistance(long distance){
            thisClass.setDistance(distance);
            return this;
        }

        public FlightMissionCriteria.Builder addAssignedSpaceShip(Spaceship assignedSpaceShip){
            thisClass.setAssignedSpaceShip(assignedSpaceShip);
            return this;
        }


        public FlightMissionCriteria.Builder addAssignedCrew(List<CrewMember> assignedCrew){
            thisClass.setAssignedCrew(assignedCrew);
            return this;
        }

        public FlightMissionCriteria.Builder addFrom(Planet from){
            thisClass.setFrom(from);
            return this;
        }

        public FlightMissionCriteria.Builder addTo(Planet to){
            thisClass.setTo(to);
            return this;
        }


        @Override
        protected FlightMission getThisClass() {
            return thisClass;
        }
    }
}
