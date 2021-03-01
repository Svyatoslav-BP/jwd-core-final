package com.epam.jwd.core_final.tests;

import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.factory.impl.FlightMissionFactory;
import com.epam.jwd.core_final.factory.impl.PlanetFactory;
import com.epam.jwd.core_final.factory.impl.SpaceshipFactory;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FlightMissionFactoryTest {

    @Test
    void create() {
        FlightMissionFactory flightMissionFactory = new FlightMissionFactory();
        String name = "Mission1";
        SpaceshipFactory spaceshipFactory = new SpaceshipFactory();
        PlanetFactory planetFactory = new PlanetFactory();
        Planet planetfrom = planetFactory.create("Planet1",1,3);
        Planet planetto = planetFactory.create("Planet2",1,9);
        long distance = 4568586754l;
        Map<Role,Short> map = new HashMap<>();
        map.put(Role.PILOT,(short) 21);
        Spaceship spaceship = spaceshipFactory.create(map,"Spaceship1",distance);
        List<CrewMember> list = new ArrayList<>();
        CrewMemberFactory crewMemberFactory = new CrewMemberFactory();
        CrewMember crewMember = crewMemberFactory.create(Rank.TRAINEE,Role.FLIGHT_ENGINEER,"Svyatik");
        list.add(crewMember);
        FlightMission flightMission = flightMissionFactory.create(spaceship,list,name,planetto,planetfrom);
        assertEquals(flightMission.getName(),"Mission1");
        assertEquals(flightMission.getAssignedCrew(),list);
        assertEquals(flightMission.getDistance(),6);
        assertEquals(flightMission.getAssignedSpaceShip().getCrew(),map);
    }
}