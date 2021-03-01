package com.epam.jwd.core_final.tests;

import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.factory.impl.PlanetFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlanetFactoryTest {

    @Test
    void create() {
        PlanetFactory planetFactory = new PlanetFactory();
        Planet planet = planetFactory.create(3, 9,"Planet1");
        assertEquals(planet.getPoint().getX(),3);
        assertEquals(planet.getPoint().getY(),9);
        assertEquals(planet.getName(),"Planet1");
    }
}