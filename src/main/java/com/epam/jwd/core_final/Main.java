package com.epam.jwd.core_final;

import com.epam.jwd.core_final.context.Application;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.factory.impl.FlightMissionFactory;
import com.epam.jwd.core_final.factory.impl.PlanetFactory;
import com.epam.jwd.core_final.factory.impl.SpaceshipFactory;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws InvalidStateException {

        //Application.start();
        Map<Role,Short> map = new HashMap<>();
        map.put(Role.resolveRoleById(1), (short) 4);
        map.put(Role.resolveRoleById(2), (short) 5);
        CrewMemberFactory crewMemberFactory = new CrewMemberFactory();
        SpaceshipFactory spaceshipFactory = new SpaceshipFactory();
        PlanetFactory planetFactory = new PlanetFactory();
        FlightMissionFactory flightMissionFactory = new FlightMissionFactory();
        System.out.println(crewMemberFactory.create("Svyat", Rank.CAPTAIN,Role.PILOT).getId());
        System.out.println(spaceshipFactory.create("spaceSvyat", map, 45678L).getCrew());

        System.out.println(planetFactory.create(3, 7, "fhvbd").getId());
        NassaContext nassaContext = new NassaContext();
        nassaContext.init();


    }
}