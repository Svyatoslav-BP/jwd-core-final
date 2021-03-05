package com.epam.jwd.core_final.service;

import com.epam.jwd.core_final.context.Application;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.DublicateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class SpaceshipServiceImpl implements SpaceshipService{
    private static final Logger logger = LoggerFactory.getLogger("log");
    private static final Scanner scanner = new Scanner(System.in);
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private static SpaceshipServiceImpl instance;
    private SpaceshipServiceImpl(){
    }
    public static SpaceshipServiceImpl getInstance(){
        if (instance==null){
            instance = new SpaceshipServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Spaceship> findAllSpaceships() {
        return (List<Spaceship>) Application.nassaContext.retrieveBaseEntityList(Spaceship.class);
    }

    @Override
    public List<Spaceship> findAllSpaceshipsByCriteria(Criteria<? extends Spaceship> criteria) {
        List<Spaceship> spaceships = (ArrayList<Spaceship>)(Application.nassaContext.retrieveBaseEntityList(Spaceship.class));
        List<Spaceship> spaceships1 = new ArrayList<>();
        if(((SpaceshipCriteria)criteria).getFlightDistance()!=null){
            spaceships.stream()
                    .filter(i -> i.isReadyForNextMissions() == (((SpaceshipCriteria) criteria).isReadyForNextMissions()))
                    .filter(i -> i.getFlightDistance() >= (((SpaceshipCriteria) criteria).getFlightDistance()))
                    .forEach(i->spaceships1.add(i));
        }
        else {
            spaceships.stream()
                    .filter(i -> i.isReadyForNextMissions() == (((SpaceshipCriteria) criteria).isReadyForNextMissions()))
                    .forEach(i->spaceships1.add(i));
        }
        return spaceships1;
    }

    @Override
    public Optional<Spaceship> findSpaceshipByCriteria(Criteria<? extends Spaceship> criteria) {
        List<Spaceship> spaceships = (ArrayList<Spaceship>)(Application.nassaContext.retrieveBaseEntityList(Spaceship.class));
        Optional<Spaceship> spaceship;
        if(((SpaceshipCriteria)criteria).getFlightDistance()!=null){
           spaceship = spaceships.stream()
                    .filter(i -> i.isReadyForNextMissions() == (((SpaceshipCriteria) criteria).isReadyForNextMissions()))
                    .filter(i -> i.getFlightDistance() >= (((SpaceshipCriteria) criteria).getFlightDistance()))
                    .findAny();
        }
        else if(((SpaceshipCriteria)criteria).getName()!=null){
            spaceship = spaceships.stream()
            .filter(i -> i.isReadyForNextMissions() == (((SpaceshipCriteria) criteria).isReadyForNextMissions()))
                    .filter(i -> i.getName().equals(((SpaceshipCriteria) criteria).getName()))
                    .findAny();
        }
        else {
           spaceship = spaceships.stream()
                    .filter(i -> i.isReadyForNextMissions() == (((SpaceshipCriteria) criteria).isReadyForNextMissions()))
                    .findAny();
        }
        return spaceship;
    }

    @Override
    public Spaceship updateSpaceshipDetails(Spaceship spaceship) {
        System.out.println("Chose field of SpaceShip\n 1:FlightDistance\n 2:Crew\n ");
        int a = scanner.nextInt();
        while (a!=1&&a!=2){
            logger.warn("Bad choice, try again");
            a = scanner.nextInt();
        }
        if (a==1){
            System.out.println("Enter FlightDistance");
            long b = scanner.nextLong();
            spaceship.setFlightDistance(b);
            return spaceship;
        }
        else if (a==2){
            Map<Role,Short> crewMap = new HashMap<>();
            System.out.println("Enter number of MISSION_SPECIALISTS");
            short b = scanner.nextShort();
                crewMap.put(Role.MISSION_SPECIALIST,b);
            System.out.println("Enter number of FLIGHT_ENGINEER");
            b = scanner.nextShort();
            crewMap.put(Role.FLIGHT_ENGINEER,b);
            System.out.println("Enter number of PILOTS");
            b = scanner.nextShort();
            crewMap.put(Role.PILOT,b);
            System.out.println("Enter number of COMMANDER");
            b = scanner.nextShort();
            crewMap.put(Role.COMMANDER,b);

            spaceship.setCrew(crewMap);
            return spaceship;
        }
        else {
            logger.warn("SpaceShip wasn't updated");
            return spaceship;
        }
    }

    @Override
    public Spaceship createSpaceship(Spaceship spaceship) throws RuntimeException, IOException {
        System.out.println("Enter FlightDistance");
        long b = scanner.nextLong();
        spaceship.setFlightDistance(b);
        System.out.println("Good job");


        Map<Role,Short> crewMap = new HashMap<>();
        System.out.println("Enter number of MISSION_SPECIALISTS");
        short a = scanner.nextShort();
        crewMap.put(Role.MISSION_SPECIALIST,a);
        System.out.println("Enter number of FLIGHT_ENGINEER");
        a = scanner.nextShort();
        crewMap.put(Role.FLIGHT_ENGINEER,a);
        System.out.println("Enter number of PILOTS");
        a = scanner.nextShort();
        crewMap.put(Role.PILOT,a);
        System.out.println("Enter number of COMMANDER");
        a = scanner.nextShort();
        crewMap.put(Role.COMMANDER,a);

        spaceship.setCrew(crewMap);
        System.out.println("Good choice");

        boolean flag =true;
        List<Spaceship> spaceships = (List<Spaceship>) (Application.nassaContext.retrieveBaseEntityList(Spaceship.class));

        System.out.println("Enter name");
        do{
            try {
                String c = reader.readLine();
                Optional<Spaceship> temp = spaceships.stream()
                        .filter(i -> i.getName().equals(c))
                        .findAny();
                if (temp.isPresent()) {
                    throw new DublicateException();
                } else {
                    flag = false;
                    spaceship.setName(c);
                }
            }catch( DublicateException e){
                System.out.println(e.getMessage());
                System.out.println("try again");
            }
        }while (flag);
        spaceships.add(spaceship);
        return spaceship;
    }
    }

