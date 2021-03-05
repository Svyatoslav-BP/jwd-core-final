package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.service.*;
import com.epam.jwd.core_final.util.Cache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;

// todo replace Object with your own types
@FunctionalInterface
public interface ApplicationMenu {

    ApplicationContext getApplicationContext();

    default void printAvailableOptions() throws IOException {
        ApplicationProperties applicationProperties = new ApplicationProperties();
        int refresh = applicationProperties.getFileRefreshRate();
        System.out.println(refresh);
        Timer timer = new Timer();
        timer.schedule(Cache.getInstance(),0,refresh);
        MissionServiceImpl missionService = MissionServiceImpl.getInstance();
        SpaceMapServiceImpl spaceMapService = SpaceMapServiceImpl.getInstance();
        CrewServiceImpl crewService = CrewServiceImpl.getInstance();
        SpaceshipServiceImpl spaceshipService = SpaceshipServiceImpl.getInstance();
        Cache cache = Cache.getInstance();
        ArrayList<FlightMission> missions = (ArrayList<FlightMission>) cache.getFlightMissions();
        Scanner scanner = new Scanner(System.in);

        int menu = 1;
        while (menu!=0) {
            System.out.println("Let's choose ");
            System.out.println("1. Mission");
            System.out.println("2. CrewMembers");
            System.out.println("3. SpaceShips");
            System.out.println("4. Planet");
            System.out.println("0. Exit");
            menu = scanner.nextInt();
            switch (menu) {
                case 1:
                        System.out.println("Let's: choose operation ");
                        System.out.println("1. Create");
                        System.out.println("2. Show missions");
                        System.out.println("0. Exit to menu");
                        menu = scanner.nextInt();
                        switch (menu) {
                            case 1:
                                missionService.createMission(new FlightMission());
                                break;
                            case 2:
                                    System.out.println("Let's: choose operation ");
                                    System.out.println("1. Show all missions");
                                    System.out.println("2. Show CANCELLED missions");
                                    System.out.println("3. Show FAILED missions");
                                    System.out.println("4. Show IN_PROGRESS missions");
                                    System.out.println("5. Show PLANNED missions");
                                    System.out.println("6. Show COMPLETED missions");
                                    System.out.println("0. Exit to menu");
                                    menu = scanner.nextInt();
                                    FlightMissionCriteria flightMissionCriteria = new FlightMissionCriteria();
                                    switch (menu) {
                                        case 1:
                                            for (FlightMission i :
                                                    missionService.findAllMissions()) {
                                                System.out.println(i.getName() + "      " + i.toString());
                                            }
                                            break;
                                        case 2:
                                            flightMissionCriteria.setMissionResult(MissionResult.CANCELLED);
                                            for (FlightMission i :
                                                    missionService.findAllMissionsByCriteria(flightMissionCriteria)) {
                                                System.out.println(i.getName() + "      " + i.toString());
                                            }
                                            break;
                                        case 3:
                                            flightMissionCriteria.setMissionResult(MissionResult.FAILED);
                                            for (FlightMission i :
                                                    missionService.findAllMissionsByCriteria(flightMissionCriteria)) {
                                                System.out.println(i.getName() + "      " + i.toString());
                                            }
                                            break;
                                        case 4:
                                            flightMissionCriteria.setMissionResult(MissionResult.IN_PROGRESS);
                                            int j = 0;
                                            for (FlightMission i :
                                                    missionService.findAllMissionsByCriteria(flightMissionCriteria)) {
                                                System.out.println(j + ":  " + i.getName() + "      " + i.toString());
                                                j++;
                                            }
                                            System.out.println("Let's: choose operation ");
                                            System.out.println("1. Cancel all missions");
                                            System.out.println("2. Cancel mission");
                                            System.out.println("3. Exit to menu");
                                            menu = scanner.nextInt();
                                            switch (menu) {
                                                case 1:
                                                    for (FlightMission i :
                                                            missionService.findAllMissionsByCriteria(flightMissionCriteria)) {
                                                        missionService.cancelMission(i);
                                                    }
                                                    System.out.println("Done");
                                                    break;
                                                case 2:
                                                    System.out.println("Let's: choose number of mission");
                                                    menu = scanner.nextInt();
                                                    while (menu >= missionService.findAllMissionsByCriteria(flightMissionCriteria).size()) {
                                                        System.out.println("Try again");
                                                        menu = scanner.nextInt();
                                                    }
                                                    missionService.cancelMission(missionService.findAllMissionsByCriteria(flightMissionCriteria).get(menu));
                                                    System.out.println("Done");
                                                    break;
                                                default:
                                                    break;
                                            }
                                            break;
                                        case 5:
                                            flightMissionCriteria.setMissionResult(MissionResult.PLANNED);
                                            j = 0;
                                            for (FlightMission i :
                                                    missionService.findAllMissionsByCriteria(flightMissionCriteria)) {
                                                System.out.println(j + ":  " + i.getName() + "      " + i.toString());
                                                j++;
                                            }
                                            System.out.println("Let's: choose operation ");
                                            System.out.println("1. Start all missions");
                                            System.out.println("2. Start mission");
                                            System.out.println("3. Exit to menu");
                                            menu = scanner.nextInt();
                                            switch (menu) {
                                                case 1:
                                                    for (FlightMission i :
                                                            missionService.findAllMissionsByCriteria(flightMissionCriteria)) {
                                                        missionService.startMission(i);
                                                    }
                                                    System.out.println("Done");
                                                    break;
                                                case 2:
                                                    System.out.println("Let's: choose number of mission");
                                                    menu = scanner.nextInt();
                                                    while (menu >= missionService.findAllMissionsByCriteria(flightMissionCriteria).size()) {
                                                        System.out.println("Try again");
                                                        menu = scanner.nextInt();
                                                    }
                                                    missionService.startMission(missionService.findAllMissionsByCriteria(flightMissionCriteria).get(menu));
                                                    System.out.println("Done");
                                                    break;
                                                default:
                                                    break;
                                            }
                                            break;
                                        case 6:
                                            flightMissionCriteria.setMissionResult(MissionResult.COMPLETED);
                                            for (FlightMission i :
                                                    missionService.findAllMissionsByCriteria(flightMissionCriteria)) {
                                                System.out.println(i.getName() + "      " + i.toString());
                                            }
                                            break;
                                        default:
                                            break;
                                    }

                            default:
                                break;
                        }
                        break;


                case 2:
                        System.out.println("Let's: choose operation ");
                        System.out.println("1. Show employed CrewMembers");
                        System.out.println("2. Show available CrewMembers");
                        System.out.println("3. Show all CrewMembers");
                        System.out.println("0. Exit to menu");
                        menu = scanner.nextInt();
                        CrewMemberCriteria crewMemberCriteria = new CrewMemberCriteria();
                        switch (menu) {
                            case 1:
                                crewMemberCriteria.setReadyForNextMissions(false);
                                for (CrewMember i :
                                        crewService.findAllCrewMembersByCriteria(crewMemberCriteria)) {
                                    System.out.println(i.getName() + "      " + i.toString());
                                }
                                break;
                            case 2:
                                crewMemberCriteria.setReadyForNextMissions(true);
                                int j = 0;
                                for (CrewMember i :
                                        crewService.findAllCrewMembersByCriteria(crewMemberCriteria)) {
                                    System.out.println(j + "  " + i.getName() + "      " + i.toString());
                                    j++;
                                }

                                    System.out.println("Let's: choose operation ");
                                    System.out.println("1. Update CrewMember");
                                    System.out.println("0. Exit to menu");
                                    menu = scanner.nextInt();
                                    if (menu == 1) {
                                        System.out.println("Let's: choose number of CrewMember");
                                        menu = scanner.nextInt();
                                        while (menu >= crewService.findAllCrewMembersByCriteria(crewMemberCriteria).size()) {
                                            System.out.println("Try again");
                                            menu = scanner.nextInt();
                                        }
                                        crewService.updateCrewMemberDetails(crewService.findAllCrewMembersByCriteria(crewMemberCriteria).get(menu));
                                        System.out.println("Done");
                                    }
                                    break;

                            case 3:
                                for (CrewMember i :
                                        crewService.findAllCrewMembers()) {
                                    System.out.println(i.getName() + "      " + i.toString());
                                }
                                break;
                            default:
                                break;

                        }
                        break;


                case 3:
                        System.out.println("Let's: choose operation ");
                        System.out.println("1. Show employed SpaceShips");
                        System.out.println("2. Show available SpaceShips");
                        System.out.println("3. Show all SpaceShips");
                        System.out.println("0. Exit to menu");
                        menu = scanner.nextInt();
                        SpaceshipCriteria spaceshipCriteria = new SpaceshipCriteria();
                        switch (menu) {
                            case 1:
                                spaceshipCriteria.setReadyForNextMissions(false);
                                for (Spaceship i :
                                        spaceshipService.findAllSpaceshipsByCriteria(spaceshipCriteria)) {
                                    System.out.println(i.getName() + "      " + i.toString());
                                }
                                break;
                            case 2:
                                spaceshipCriteria.setReadyForNextMissions(true);
                                int j = 0;
                                for (Spaceship i :
                                        spaceshipService.findAllSpaceshipsByCriteria(spaceshipCriteria)) {
                                    System.out.println(j+ "  "+i.getName() + "      " + i.toString());
                                    j++;
                                }

                                    System.out.println("Let's: choose operation ");
                                    System.out.println("1. Update SpaceShip");
                                    System.out.println("0. Exit ");
                                    menu = scanner.nextInt();
                                    if (menu == 1) {
                                        System.out.println("Let's: choose number of SpaceShip");
                                        menu = scanner.nextInt();
                                        while (menu >= spaceshipService.findAllSpaceshipsByCriteria(spaceshipCriteria).size()) {
                                            System.out.println("Try again");
                                            menu = scanner.nextInt();
                                        }
                                        spaceshipService.updateSpaceshipDetails(spaceshipService.findAllSpaceshipsByCriteria(spaceshipCriteria).get(menu));
                                        System.out.println("Done");
                                        break;
                                    }
                                    break;
                            case 3:
                                for (Spaceship i :
                                        spaceshipService.findAllSpaceships()) {
                                    System.out.println(i.getName() + "      " + i.toString());
                                }
                                break;
                            default:
                                break;
                        }
                        break;
                case 4:
                    for (Planet i:
                         spaceMapService.findAllPlanets()) {
                        System.out.println(i.getName()+":   "+i.toString());
                    }
                    break;
                default:
                    menu = 0;
                    timer.cancel();
                    System.out.println("Good bye!");
                    break;
            }
        }
    }

    default Object handleUserInput(Object o) {
        return null;
    }
}
