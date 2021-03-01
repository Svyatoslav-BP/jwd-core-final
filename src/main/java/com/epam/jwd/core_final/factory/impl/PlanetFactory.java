package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.criteria.PlanetCriteria;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.InvalidArgsFactoryException;
import com.epam.jwd.core_final.factory.EntityFactory;

import java.util.Map;

public class PlanetFactory implements EntityFactory<Planet> {
    @Override
    public Planet create(Object... args) {
        int x = -1;
        int y = -1;
        String name = null;
        Planet planet = null;

        if(args.length !=3) System.out.println(new InvalidArgsFactoryException("Planet1").getMessage());

        for (Object i:args){
            if(i instanceof Integer){
                if(x<0) {
                    x = (int) i;
                }
                else {
                    y = (int) i;
                }
            }
            else if(i instanceof String)
                name = (String) i;
        }

        if(x < 0 || y < 0 || name == null) {
            System.out.println(new InvalidArgsFactoryException("Planet2").getMessage());
        }else try {
            planet = new PlanetCriteria.Builder()
                    .addName(name)
                    .addX(x)
                    .addY(y)
                    .build();
        }catch(InvalidArgsFactoryException e){
            System.out.println(new InvalidArgsFactoryException("Planet3").getMessage());

        }
        return planet;
    }
}
