package com.epam.jwd.core_final.service;

import com.epam.jwd.core_final.context.Application;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.exception.DublicateException;
import com.epam.jwd.core_final.util.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.*;

public class MissionServiceImpl implements MissionService{

    private static MissionServiceImpl instance;
    private MissionServiceImpl(){
    }
    public static MissionServiceImpl getInstance(){
        if (instance==null){
            instance = new MissionServiceImpl();
        }
        return instance;
    }

    private static final Logger logger = LoggerFactory.getLogger("log");
    private static final Scanner scanner = new Scanner(System.in);
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private Cache cache = Cache.getInstance();
    @Override
    public List<FlightMission> findAllMissions() {
        return cache.getFlightMissions();
    }

    @Override
    public List<FlightMission> findAllMissionsByCriteria(Criteria<? extends FlightMission> criteria) {
        List<FlightMission> flightMissions = cache.getFlightMissions();
        List<FlightMission> flightMissions1 = new ArrayList<>();
        if(((FlightMissionCriteria)criteria).getMissionResult()!=null){
            flightMissions.stream()
                    .filter(i -> i.getMissionResult() == (((FlightMissionCriteria) criteria).getMissionResult()))
                    .forEach(i->flightMissions1.add(i));
        }
        else {
            logger.error("Invalid statement");
        }
        return flightMissions1;
    }

    @Override
    public Optional<FlightMission> findMissionByCriteria(Criteria<? extends FlightMission> criteria) {
        List<FlightMission> flightMissions = cache.getFlightMissions();
        Optional<FlightMission> flightMission;
        if(((FlightMissionCriteria)criteria).getMissionResult()!=null){
            flightMission = flightMissions.stream()
                    .filter(i -> i.getMissionResult() == (((FlightMissionCriteria) criteria).getMissionResult()))
                    .findAny();
        }
        else if(((FlightMissionCriteria) criteria).getName()!=null)
            flightMission = flightMissions.stream()
                    .filter(i -> i.getName().equals(((FlightMissionCriteria) criteria).getName()))
                    .findAny();
        else {
            logger.error("Invalid criteria");
            flightMission = Optional.empty();
        }
        return flightMission;
    }

    @Override
    public FlightMission updateSpaceshipDetails(FlightMission flightMission) {
        return null;
    }


    @Override
    public void assignCrewMemberOnMission(CrewMember crewMember,FlightMission flightMission) throws RuntimeException {
        crewMember.setReadyForNextMissions(false);
        flightMission.getAssignedCrew().add(crewMember);
    }

    @Override
    public void assignSpaceshipOnMission(Spaceship spaceship,FlightMission flightMission) throws RuntimeException {
        spaceship.setReadyForNextMissions(false);
        flightMission.setAssignedSpaceShip(spaceship);
    }

    @Override
    public FlightMission createMission(FlightMission flightMission) throws IOException {
        CrewService crewService = CrewServiceImpl.getInstance();
        SpaceshipService spaceshipService = SpaceshipServiceImpl.getInstance();
        SpaceMapService spaceMapService = SpaceMapServiceImpl.getInstance();
        Cache cache = Cache.getInstance();
        ArrayList<FlightMission> flightMissions= (ArrayList<FlightMission>) cache.getFlightMissions();
        System.out.println("Creating of mission");
        System.out.println("Enter name");
        boolean flag = true;
        do{
            try {
                String b = reader.readLine();
                Optional<FlightMission> temp = flightMissions.stream()
                        .filter(i -> i.getName().equals(b))
                        .findAny();
                if (temp.isPresent()) {
                    throw new DublicateException();
                } else {
                    flag = false;
                    flightMission.setName(b);
                }
            }catch( DublicateException e){
                System.out.println(e.getMessage());
                System.out.println("try again");
            }
        }while (flag);

        boolean flag1 = true;
        while (flag1){
            flightMission.setFrom(spaceMapService.getRandomPlanet());
            flightMission.setTo(spaceMapService.getRandomPlanet());
            flightMission.setDistance((long)spaceMapService.getDistanceBetweenPlanets(flightMission.getFrom(),flightMission.getTo()));
            System.out.println("FromPlanet: "+flightMission.getFrom().getName());
            System.out.println("ToPlanet: "+flightMission.getTo().getName());
            System.out.println("You need to fly "+flightMission.getDistance());
            int choice = 0;
            System.out.println("Do you agree?\n 1 - Yea\n 2 - No");
            choice=scanner.nextInt();
            while (choice!=1&&choice!=2){
                System.out.println("Bad choice, try again");
                choice=scanner.nextInt();
            }
            if(choice==1){
                flag1 = false;
            }
        }

        System.out.println("You want to find SpaceShip by yourself?\n 1 - Yea\n 2 - No");
        int choice3=scanner.nextInt();
        while (choice3!=1&&choice3!=2){
            System.out.println("Bad choice, try again");
            choice3=scanner.nextInt();
        }
        if(choice3==1){
        System.out.println("You may use Space ships: ");
        SpaceshipCriteria spaceshipCriteria = new SpaceshipCriteria();
        spaceshipCriteria.setFlightDistance(flightMission.getDistance());
        ArrayList<Spaceship> spaceships = (ArrayList<Spaceship>) spaceshipService.findAllSpaceshipsByCriteria(spaceshipCriteria);
        for (int i = 0; i < spaceships.size(); i++) {
            System.out.println(i + "    :       " + spaceships.get(i).getName() + "  -   " + spaceships.get(i).toString());
        }
            boolean spaceShipsIsEmpty = false;
        if(spaceships.isEmpty()){
            spaceShipsIsEmpty = true;
        }
        System.out.println("You want to use SpaceShip from List or Create your own SpaceShip?\n 1 - From List\n 2 - By myself");
        int choice=scanner.nextInt();
        while ((choice!=1&&choice!=2)||(spaceShipsIsEmpty&&choice==1)){
            System.out.println("Bad choice(May be list is Empty), try again");
            choice=scanner.nextInt();
        }
        if(choice==1){
            System.out.println("Enter number of SpaceShip");
            int choice1 = scanner.nextInt();
            while (choice1>=spaceships.size()){
                System.out.println("Bad chose, Try again");
                choice1 = scanner.nextInt();
            }
            System.out.println("Good Choice!");
            System.out.println("Do you want to update this SpaceShip?\n 1 - Yea\n 2 - No");
            int choice2=scanner.nextInt();
            while (choice2!=1&&choice2!=2){
                System.out.println("Bad choice, try again");
                choice2=scanner.nextInt();
            }
            if(choice2==1){
                System.out.println("Be careful and use flightDistanse >= "+flightMission.getDistance());
                spaceshipService.updateSpaceshipDetails(spaceships.get(choice1));
                while (spaceships.get(choice1).getFlightDistance()<flightMission.getDistance()){
                    spaceshipService.updateSpaceshipDetails(spaceships.get(choice1));
                }
            }
            assignSpaceshipOnMission(spaceships.get(choice1),flightMission);
            System.out.println("SpaceShip was assign to the mission!");
        }
        else if(choice==2){
            System.out.println("You should create SpaceShip with FlightDistance >= "+flightMission.getDistance());
            Spaceship spaceship = spaceshipService.createSpaceship(new Spaceship());
            System.out.println("SpaceShip was created");
            while (spaceship.getFlightDistance()<flightMission.getDistance()){
                System.out.println("Change FlightDistance if you want to assign your spaceShip");
                spaceship = spaceshipService.createSpaceship(new Spaceship());
            }
            assignSpaceshipOnMission(spaceship,flightMission);
            System.out.println("SpaceShip was assign to the mission!");
        }
        }
        else if(choice3==2){

            SpaceshipCriteria spaceshipCriteria = new SpaceshipCriteria();
            spaceshipCriteria.setFlightDistance(flightMission.getDistance());
            if (spaceshipService.findSpaceshipByCriteria(spaceshipCriteria).isPresent()){
            assignSpaceshipOnMission(spaceshipService.findSpaceshipByCriteria(spaceshipCriteria).get(),flightMission);
            }
            else {
                System.out.println("You have to create SpaceShip by yourself");
                System.out.println("You should create SpaceShip with FlightDistance >= "+flightMission.getDistance());
                Spaceship spaceship = spaceshipService.createSpaceship(new Spaceship());
                System.out.println("SpaceShip was created");
                while (spaceship.getFlightDistance()<flightMission.getDistance()){
                    System.out.println("Change FlightDistance if you want to assign your spaceShip");
                    spaceship = spaceshipService.createSpaceship(new Spaceship());
                }
                assignSpaceshipOnMission(spaceship,flightMission);
                System.out.println("SpaceShip was assign to the mission!");
            }
        }

        int m , f , p ,c ;
        m = flightMission.getAssignedSpaceShip().getCrew().get(Role.MISSION_SPECIALIST);
        f = flightMission.getAssignedSpaceShip().getCrew().get(Role.FLIGHT_ENGINEER);
        p = flightMission.getAssignedSpaceShip().getCrew().get(Role.PILOT);
        c = flightMission.getAssignedSpaceShip().getCrew().get(Role.COMMANDER);

        System.out.println("You need: \n"+m+" MISSION_SPECIALISTS\n"
                +f+" FLIGHT_ENGINEERS\n"+p+" PILOTS\n"+c+" COMMANDERS\n");
        System.out.println("You want to find CrewMembers by yourself?\n 1 - Yea\n 2 - No");
        choice3 =scanner.nextInt();
        while (choice3!=1&&choice3!=2){
            System.out.println("Bad choice, try again");
            choice3=scanner.nextInt();
        }
        if(choice3==1) {
           /////////////////////////////////////////////////////////////////////
            System.out.println("You may use CrewMembers : ");
            CrewMemberCriteria criteria = new CrewMemberCriteria();
            ArrayList<CrewMember> crewMembers = (ArrayList<CrewMember>) crewService.findAllCrewMembersByCriteria(criteria);
            for (int i = 0; i < crewMembers.size(); i++) {
                System.out.println(i +"    :       "+crewMembers.get(i).getName()+"  -   "+crewMembers.get(i).toString());
            }

            boolean crewMembersIsEmpty = false;
            if(crewMembers.isEmpty()){
                crewMembersIsEmpty = true;
            }
            System.out.println("You want to use CrewMembers from List or Create your own CrewMembers?\n 1 - From List\n 2 - By myself");
            int choice=scanner.nextInt();
            while ((choice!=1&&choice!=2)||(crewMembersIsEmpty&&choice==1)){
                System.out.println("Bad choice(May be list is Empty), try again");
                choice=scanner.nextInt();
            }

            if(choice==1){

                //1.
                System.out.println("Let's chose "+ f +" FLIGHT_ENGINEERS from list:");
                criteria.setRole(Role.FLIGHT_ENGINEER);
                crewMembers = (ArrayList<CrewMember>) crewService.findAllCrewMembersByCriteria(criteria);
                for (int i = 0; i < f; i++) {
                    crewMembers = (ArrayList<CrewMember>) crewService.findAllCrewMembersByCriteria(criteria);
                    for (int j = 0; j < crewMembers.size(); j++) {
                        System.out.println(j +"    :       "+crewMembers.get(j).getName()+"  -   "+crewMembers.get(j).toString());
                    }
                    if(crewMembersIsEmpty){
                        System.out.println("There are no any FLIGHT_ENGINEERS at List");
                        System.out.println("You have to create " + (f - i - 1) + "CrewMembers(FLIGHT_ENGINEERS)");
                        CrewMember crewMember = crewService.createCrewMember(new CrewMember());
                        while(crewMember.getRole()!=Role.FLIGHT_ENGINEER){
                            System.out.println("Try again");
                            crewMember = crewService.createCrewMember(new CrewMember());
                        }
                        assignCrewMemberOnMission(crewMember,flightMission);
                        System.out.println("CrewMember assigned");
                    }
                    else {
                        System.out.println("Enter number of CrewMember");
                        int choice1 = scanner.nextInt();
                        while (choice1 >= crewMembers.size()) {
                            System.out.println("Bad chose, Try again");
                            choice1 = scanner.nextInt();
                        }
                        System.out.println("Good Choice!");
                        System.out.println("Do you want to update this CrewMember?\n 1 - Yea\n 2 - No");
                        int choice2 = scanner.nextInt();
                        while (choice2 != 1 && choice2 != 2) {
                            System.out.println("Bad choice, try again");
                            choice2 = scanner.nextInt();
                        }
                        if (choice2 == 1) {
                            System.out.println("Be careful and use Role :  FLIGHT_ENGINEERS");
                            crewService.updateCrewMemberDetails(crewMembers.get(choice1));
                            while (crewMembers.get(choice1).getRole() != Role.FLIGHT_ENGINEER) {
                                System.out.println("Crew member must be FLIGHT_ENGINEERS");
                                crewService.updateCrewMemberDetails(crewMembers.get(choice1));
                            }
                        }
                        assignCrewMemberOnMission(crewMembers.get(choice1), flightMission);
                        System.out.println("CrewMember was assign to the mission!");
                    }
                }

                //2.
                System.out.println("Let's chose "+ m +" MISSION_SPECIALISTS from list:");
                criteria.setRole(Role.MISSION_SPECIALIST);
                for (int i = 0; i < m; i++) {
                    crewMembers = (ArrayList<CrewMember>) crewService.findAllCrewMembersByCriteria(criteria);
                    for (int j = 0; j < crewMembers.size(); j++) {
                        System.out.println(j +"    :       "+crewMembers.get(j).getName()+"  -   "+crewMembers.get(j).toString());
                    }
                    if(crewMembersIsEmpty){
                        System.out.println("There are no any MISSION_SPECIALISTS at List");
                        System.out.println("You have to create " + (m - i - 1) + "CrewMembers(MISSION_SPECIALISTS)");
                        CrewMember crewMember = crewService.createCrewMember(new CrewMember());
                        while(crewMember.getRole()!=Role.MISSION_SPECIALIST){
                            System.out.println("Try again");
                            crewMember = crewService.createCrewMember(new CrewMember());
                        }
                        assignCrewMemberOnMission(crewMember,flightMission);
                        System.out.println("CrewMember assigned");
                    }
                    else {
                        System.out.println("Enter number of CrewMember");
                        int choice1 = scanner.nextInt();
                        while (choice1 >= crewMembers.size()) {
                            System.out.println("Bad chose, Try again");
                            choice1 = scanner.nextInt();
                        }
                        System.out.println("Good Choice!");
                        System.out.println("Do you want to update this CrewMember?\n 1 - Yea\n 2 - No");
                        int choice2 = scanner.nextInt();
                        while (choice2 != 1 && choice2 != 2) {
                            System.out.println("Bad choice, try again");
                            choice2 = scanner.nextInt();
                        }
                        if (choice2 == 1) {
                            System.out.println("Be careful and use Role :  MISSION_SPECIALIST");
                            crewService.updateCrewMemberDetails(crewMembers.get(choice1));
                            while (crewMembers.get(choice1).getRole() != Role.MISSION_SPECIALIST) {
                                System.out.println("Crew member must be MISSION_SPECIALIST");
                                crewService.updateCrewMemberDetails(crewMembers.get(choice1));
                            }
                        }
                        assignCrewMemberOnMission(crewMembers.get(choice1), flightMission);
                        System.out.println("CrewMember was assign to the mission!");
                    }
                }

                //3.
                System.out.println("Let's chose "+ p +" PILOTS from list:");
                criteria.setRole(Role.PILOT);
                for (int i = 0; i < p; i++) {
                    crewMembers = (ArrayList<CrewMember>) crewService.findAllCrewMembersByCriteria(criteria);
                    for (int j = 0; j < crewMembers.size(); j++) {
                        System.out.println(j +"    :       "+crewMembers.get(j).getName()+"  -   "+crewMembers.get(j).toString());
                    }
                    if(crewMembersIsEmpty){
                        System.out.println("There are no any PILOTS at List");
                        System.out.println("You have to create " + (p - i - 1) + "CrewMembers(PILOTS)");
                        CrewMember crewMember = crewService.createCrewMember(new CrewMember());
                        while(crewMember.getRole()!=Role.PILOT){
                            System.out.println("Try again");
                            crewMember = crewService.createCrewMember(new CrewMember());
                        }
                        assignCrewMemberOnMission(crewMember,flightMission);
                        System.out.println("CrewMember assigned");
                    }
                    else {
                        System.out.println("Enter number of CrewMember");
                        int choice1 = scanner.nextInt();
                        while (choice1 >= crewMembers.size()) {
                            System.out.println("Bad chose, Try again");
                            choice1 = scanner.nextInt();
                        }
                        System.out.println("Good Choice!");
                        System.out.println("Do you want to update this CrewMember?\n 1 - Yea\n 2 - No");
                        int choice2 = scanner.nextInt();
                        while (choice2 != 1 && choice2 != 2) {
                            System.out.println("Bad choice, try again");
                            choice2 = scanner.nextInt();
                        }
                        if (choice2 == 1) {
                            System.out.println("Be careful and use Role :  PILOTS");
                            crewService.updateCrewMemberDetails(crewMembers.get(choice1));
                            while (crewMembers.get(choice1).getRole() != Role.PILOT) {
                                System.out.println("Crew member must be PILOTS");
                                crewService.updateCrewMemberDetails(crewMembers.get(choice1));
                            }
                        }
                        assignCrewMemberOnMission(crewMembers.get(choice1), flightMission);
                        System.out.println("CrewMember was assign to the mission!");
                    }
                }

                //4
                System.out.println("Let's chose "+ c +" COMMANDERS from list:");
                criteria.setRole(Role.COMMANDER);
                for (int i = 0; i < c; i++) {
                    crewMembers = (ArrayList<CrewMember>) crewService.findAllCrewMembersByCriteria(criteria);
                    for (int j = 0; j < crewMembers.size(); j++) {
                        System.out.println(j +"    :       "+crewMembers.get(j).getName()+"  -   "+crewMembers.get(j).toString());
                    }
                    if(crewMembersIsEmpty){
                        System.out.println("There are no any COMMANDERS at List");
                        System.out.println("You have to create " + (c - i - 1) + "CrewMembers(COMMANDERS)");
                        CrewMember crewMember = crewService.createCrewMember(new CrewMember());
                        while(crewMember.getRole()!=Role.COMMANDER){
                            System.out.println("Try again");
                            crewMember = crewService.createCrewMember(new CrewMember());
                        }
                        assignCrewMemberOnMission(crewMember,flightMission);
                        System.out.println("CrewMember assigned");
                    }
                    else {
                        System.out.println("Enter number of CrewMember");
                        int choice1 = scanner.nextInt();
                        while (choice1 >= crewMembers.size()) {
                            System.out.println("Bad chose, Try again");
                            choice1 = scanner.nextInt();
                        }
                        System.out.println("Good Choice!");
                        System.out.println("Do you want to update this CrewMember?\n 1 - Yea\n 2 - No");
                        int choice2 = scanner.nextInt();
                        while (choice2 != 1 && choice2 != 2) {
                            System.out.println("Bad choice, try again");
                            choice2 = scanner.nextInt();
                        }
                        if (choice2 == 1) {
                            System.out.println("Be careful and use Role :  COMMANDERS");
                            crewService.updateCrewMemberDetails(crewMembers.get(choice1));
                            while (crewMembers.get(choice1).getRole() != Role.COMMANDER) {
                                System.out.println("Crew member must be COMMANDERS");
                                crewService.updateCrewMemberDetails(crewMembers.get(choice1));
                            }
                        }
                        assignCrewMemberOnMission(crewMembers.get(choice1), flightMission);
                        System.out.println("CrewMember was assign to the mission!");
                    }
                }
            }
            else if(choice==2){
                System.out.println("Let's create "+ m +" MISSION_SPECIALISTS");
                for (int i = 0; i < m; i++) {
                    CrewMember crewMember = crewService.createCrewMember(new CrewMember());
                    while(crewMember.getRole()!=Role.MISSION_SPECIALIST){
                        System.out.println("Try again");
                        crewMember = crewService.createCrewMember(new CrewMember());
                    }
                    assignCrewMemberOnMission(crewMember,flightMission);
                    System.out.println("CrewMember assigned");
                }
                System.out.println("Let's create "+ f +" FLIGHT_ENGINEERS");
                for (int i = 0; i < f; i++) {
                    CrewMember crewMember = crewService.createCrewMember(new CrewMember());
                    while(crewMember.getRole()!=Role.FLIGHT_ENGINEER){
                        System.out.println("Try again");
                        crewMember = crewService.createCrewMember(new CrewMember());
                    }
                    assignCrewMemberOnMission(crewMember,flightMission);
                    System.out.println("CrewMember assigned");
                }
                System.out.println("Let's create "+ m +" PILOTS");
                for (int i = 0; i < p; i++) {
                    CrewMember crewMember = crewService.createCrewMember(new CrewMember());
                    while(crewMember.getRole()!=Role.PILOT){
                        System.out.println("Try again");
                        crewMember = crewService.createCrewMember(new CrewMember());
                    }
                    assignCrewMemberOnMission(crewMember,flightMission);
                    System.out.println("CrewMember assigned");
                }
                System.out.println("Let's create "+ f +" COMMANDERS");
                for (int i = 0; i < f; i++) {
                    CrewMember crewMember = crewService.createCrewMember(new CrewMember());
                    while(crewMember.getRole()!=Role.COMMANDER){
                        System.out.println("Try again");
                        crewMember = crewService.createCrewMember(new CrewMember());
                    }
                    assignCrewMemberOnMission(crewMember,flightMission);
                    System.out.println("CrewMember assigned");
                }
            }
        }
        else if(choice3==2){
            CrewMemberCriteria crewMemberCriteria = new CrewMemberCriteria();
            crewMemberCriteria.setRole(Role.MISSION_SPECIALIST);
            for (int i = 0; i < m; i++) {
                if(crewService.findCrewMemberByCriteria(crewMemberCriteria).isPresent()){
                assignCrewMemberOnMission(crewService.findCrewMemberByCriteria(crewMemberCriteria).get(),flightMission);
                }
                else {
                    System.out.println("You have to create " + (m - i - 1) + "CrewMembers(MISSION_SPECIALISTS)");
                    CrewMember crewMember = crewService.createCrewMember(new CrewMember());
                    while(crewMember.getRole()!=Role.MISSION_SPECIALIST){
                        System.out.println("Try again");
                        crewMember = crewService.createCrewMember(new CrewMember());
                    }
                    assignCrewMemberOnMission(crewMember,flightMission);
                    System.out.println("CrewMember assigned");
                }
            }
            crewMemberCriteria.setRole(Role.FLIGHT_ENGINEER);
            for (int i = 0; i < f; i++) {
                if(crewService.findCrewMemberByCriteria(crewMemberCriteria).isPresent()){
                    assignCrewMemberOnMission(crewService.findCrewMemberByCriteria(crewMemberCriteria).get(),flightMission);
                }
                else {
                    System.out.println("You have to create " + (f - i - 1) + "CrewMembers(FLIGHT_ENGINEERS)");
                    CrewMember crewMember = crewService.createCrewMember(new CrewMember());
                    while(crewMember.getRole()!=Role.FLIGHT_ENGINEER){
                        System.out.println("Try again");
                        crewMember = crewService.createCrewMember(new CrewMember());
                    }
                    assignCrewMemberOnMission(crewMember,flightMission);
                    System.out.println("CrewMember assigned");
                }            }
            crewMemberCriteria.setRole(Role.PILOT);
            for (int i = 0; i < p; i++) {
                if(crewService.findCrewMemberByCriteria(crewMemberCriteria).isPresent()){
                    assignCrewMemberOnMission(crewService.findCrewMemberByCriteria(crewMemberCriteria).get(),flightMission);
                }
                else {
                    System.out.println("You have to create " + (p - i - 1) + "CrewMembers(PILOTS)");
                    CrewMember crewMember = crewService.createCrewMember(new CrewMember());
                    while(crewMember.getRole()!=Role.PILOT){
                        System.out.println("Try again");
                        crewMember = crewService.createCrewMember(new CrewMember());
                    }
                    assignCrewMemberOnMission(crewMember,flightMission);
                    System.out.println("CrewMember assigned");
                }            }
            crewMemberCriteria.setRole(Role.COMMANDER);
            for (int i = 0; i < c; i++) {
                if(crewService.findCrewMemberByCriteria(crewMemberCriteria).isPresent()){
                    assignCrewMemberOnMission(crewService.findCrewMemberByCriteria(crewMemberCriteria).get(),flightMission);
                }
                else {
                    System.out.println("You have to create " + (c - i - 1) + "CrewMembers(COMMANDERS)");
                    CrewMember crewMember = crewService.createCrewMember(new CrewMember());
                    while(crewMember.getRole()!=Role.COMMANDER){
                        System.out.println("Try again");
                        crewMember = crewService.createCrewMember(new CrewMember());
                    }
                    assignCrewMemberOnMission(crewMember,flightMission);
                    System.out.println("CrewMember assigned");
                }            }
        }
        flightMission.setMissionResult(MissionResult.PLANNED);
        flightMissions.add(flightMission);
        return flightMission;
    }

    public void startMission (FlightMission flightMission) {
        if(flightMission.getMissionResult()==MissionResult.PLANNED){
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusSeconds(flightMission.getDistance());
        flightMission.setEndDate(end);
        flightMission.setStartDate(start);
        flightMission.setMissionResult(MissionResult.IN_PROGRESS);
        }
        else {
            System.out.println("You should create mission");
        }
    }

    public void cancelMission (FlightMission flightMission) {
        if(flightMission.getMissionResult()==MissionResult.IN_PROGRESS){
            LocalDateTime end = LocalDateTime.now();
            flightMission.setEndDate(end);
            flightMission.setMissionResult(MissionResult.CANCELLED);
            flightMission.getAssignedSpaceShip().setReadyForNextMissions(true);
            for (CrewMember i :
                 flightMission.getAssignedCrew()) {
                i.setReadyForNextMissions(true);
            }
        }
        else {
            System.out.println("You can't cancel unstarted mission");
        }
    }

}
