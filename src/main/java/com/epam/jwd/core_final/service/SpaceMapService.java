package com.epam.jwd.core_final.service;

import com.epam.jwd.core_final.domain.Planet;

public interface SpaceMapService {

    Planet getRandomPlanet();

    // Dijkstra ?
    int getDistanceBetweenPlanets(Planet first, Planet second);
}
