package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.InvalidArgsFactoryException;
import com.epam.jwd.core_final.factory.EntityFactory;

import java.util.Map;

public class SpaceshipFactory implements EntityFactory<Spaceship> {

    @Override
    public Spaceship create(Object... args) {
        Map<Role,Short> crew = null;
        Long flightDistance = null;
        String name = null;
        Spaceship spaceship = null;

        if(args.length !=3) System.out.println(new InvalidArgsFactoryException("Spaceship1").getMessage());

        for (Object i:args){
            if(i instanceof Map)
                crew = (Map<Role, Short>) i;
            else if(i instanceof Long )
                flightDistance = (Long) i;
            else if(i instanceof String)
                name = (String) i;
        }

        if(crew == null || flightDistance == null || name == null) {
            System.out.println(new InvalidArgsFactoryException("Spaceship2").getMessage());
        }else try {
            spaceship = new SpaceshipCriteria.Builder()
                    .addName(name)
                    .addCrew(crew)
                    .addFlightDistance(flightDistance)
                    .build();
        }catch(InvalidArgsFactoryException e){
            System.out.println(new InvalidArgsFactoryException("Spaceship3").getMessage());

        }
        return spaceship;
    }
}
