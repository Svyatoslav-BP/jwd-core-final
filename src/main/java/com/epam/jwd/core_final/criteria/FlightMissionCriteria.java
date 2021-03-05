package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.*;


import java.time.LocalDateTime;
import java.util.List;

/**
 * Should be a builder for {@link com.epam.jwd.core_final.domain.FlightMission} fields
 */
public class FlightMissionCriteria extends Criteria<FlightMission> {
    public MissionResult getMissionResult() {
        return missionResult;
    }

    public void setMissionResult(MissionResult missionResult) {
        this.missionResult = missionResult;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private MissionResult missionResult;
    private String name;

    public static class Builder extends BaseBuilder<FlightMission>{

        public FlightMissionCriteria.Builder addName(String name){
            thisClass = new FlightMission(name);
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

        public FlightMissionCriteria.Builder addStatus(){
            thisClass.setMissionResult(MissionResult.PLANNED);
            return this;
        }


        @Override
        protected FlightMission getThisClass() {
            return thisClass;
        }
    }
}
