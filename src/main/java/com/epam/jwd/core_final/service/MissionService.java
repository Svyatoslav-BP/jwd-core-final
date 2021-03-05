package com.epam.jwd.core_final.service;

import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Spaceship;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface MissionService {

    List<FlightMission> findAllMissions();

    List<FlightMission> findAllMissionsByCriteria(Criteria<? extends FlightMission> criteria);

    Optional<FlightMission> findMissionByCriteria(Criteria<? extends FlightMission> criteria);

    FlightMission updateSpaceshipDetails(FlightMission flightMission);

    // todo create custom exception for case, when crewMember is not able to be assigned
     void assignCrewMemberOnMission(CrewMember crewMember,FlightMission flightMission) throws RuntimeException;

    // todo create custom exception for case, when spaceship is not able to be assigned
    void assignSpaceshipOnMission(Spaceship crewMember,FlightMission flightMission) throws RuntimeException;

    FlightMission createMission(FlightMission flightMission) throws IOException;
}
