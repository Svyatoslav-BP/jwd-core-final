package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.exception.InvalidArgsFactoryException;
import com.epam.jwd.core_final.factory.EntityFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FlightMissionFactory implements EntityFactory<FlightMission> {


    @Override
    public FlightMission create(Object... args) {
         String missionName = null;
         Spaceship assignedSpaceShip = null;
         List<CrewMember> assignedCrew = null;
         Long distance = null;
        FlightMission flightMission = null;
        Planet to = null;
        Planet from = null;
        List<LocalDateTime> listLD = new ArrayList<>();


        if(args.length !=5) System.out.println(new InvalidArgsFactoryException("FlightMission1").getMessage());

        for (Object i:args){
             if(i instanceof Spaceship)
                assignedSpaceShip = (Spaceship) i;
            else if(i instanceof List)
                assignedCrew = (List<CrewMember>) i;
            else if(i instanceof String){
                missionName = (String) i;
                } else if(i instanceof Planet){
                    if(from == null){
                        from = (Planet) i;
                    }
                    else if(i instanceof Planet && to == null) to = (Planet) i;
                }
        }
        distance = (long) (Math.sqrt((to.getPoint().getX() - from.getPoint().getX()) * (to.getPoint().getX() - from.getPoint().getX()) + (to.getPoint().getY() - from.getPoint().getY()) * (to.getPoint().getY() - from.getPoint().getY())));



        if(missionName == null || distance == null
                || assignedSpaceShip == null || assignedCrew == null || to == null || from==null) {
            System.out.println(new InvalidArgsFactoryException("FlightMission2").getMessage());
        }else
            try {
            flightMission = new FlightMissionCriteria.Builder()
                    .addName(missionName)
                    .addFrom(from)
                    .addTo(to)
                    .addAssignedCrew(assignedCrew)
                    .addAssignedSpaceShip(assignedSpaceShip)
                    .addDistance(distance)
                    .addStatus()
                    .build();
        }catch(InvalidArgsFactoryException e){
            System.out.println(new InvalidArgsFactoryException("FlightMission3").getMessage());

        }
        return flightMission;
    }
    }


