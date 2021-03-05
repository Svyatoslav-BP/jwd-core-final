package com.epam.jwd.core_final.service;

import com.epam.jwd.core_final.context.Application;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Planet;

import java.util.List;
import java.util.Random;

public class SpaceMapServiceImpl implements SpaceMapService{
    private static SpaceMapServiceImpl instance;
    private SpaceMapServiceImpl(){
    }
    public static SpaceMapServiceImpl getInstance(){
        if (instance==null){
            instance = new SpaceMapServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Planet> findAllPlanets() {
        return (List<Planet>) Application.nassaContext.retrieveBaseEntityList(Planet.class);
    }

    @Override
    public Planet getRandomPlanet() {
        Random random = new Random();
        List<Planet> list = (List<Planet>)(Application.nassaContext.retrieveBaseEntityList(Planet.class)) ;
        int a = random.nextInt(Application.nassaContext.retrieveBaseEntityList(Planet.class).size());
        return list.get(a);
    }

    @Override
    public int getDistanceBetweenPlanets(Planet first, Planet second) {
        return  (int) (Math.sqrt((first.getPoint().getX() - second.getPoint().getX()) * (first.getPoint().getX() - second.getPoint().getX()) + (first.getPoint().getY() - second.getPoint().getY()) * (first.getPoint().getY() - second.getPoint().getY())));
    }
}
