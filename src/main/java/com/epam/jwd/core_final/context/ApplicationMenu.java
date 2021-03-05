package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.service.*;
import com.epam.jwd.core_final.util.Cache;

import java.io.IOException;
import java.util.List;

// todo replace Object with your own types
@FunctionalInterface
public interface ApplicationMenu {

    ApplicationContext getApplicationContext();

    default void printAvailableOptions() throws IOException {
//        SpaceMapServiceImpl spaceMapService = SpaceMapServiceImpl.getInstance();
//        CrewServiceImpl crewService = CrewServiceImpl.getInstance();
//        System.out.println(spaceMapService.getDistanceBetweenPlanets(spaceMapService.getRandomPlanet(), spaceMapService.getRandomPlanet()));
//        CrewMemberCriteria criteria = new CrewMemberCriteria();
//        //criteria.setRank(Rank.resolveRankById(3));
//        System.out.println(crewService.findAllCrewMembersByCriteria(criteria));
//        criteria.setName("Davey Bentley");
//        System.out.println(crewService.findAllCrewMembers().get(0).getName());
//        System.out.println(crewService.findCrewMemberByCriteria(criteria));
//        System.out.println(spaceMapService.getRandomPlanet().getName());
        MissionServiceImpl missionService = MissionServiceImpl.getInstance();
        missionService.createMission(new FlightMission());
        Cache cache = Cache.getInstance();
        System.out.println(cache.getFlightMissions().get(0).getAssignedCrew().size());


    }

    default Object handleUserInput(Object o) {
        return null;
    }
}
