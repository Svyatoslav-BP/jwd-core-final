package com.epam.jwd.core_final.service;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Planet;

import java.util.List;

public interface SpaceMapService {

    List<Planet> findAllPlanets();

    Planet getRandomPlanet();

    // Dijkstra ?
    int getDistanceBetweenPlanets(Planet first, Planet second);
}
