package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.service.SpaceshipServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class Cache {
    private List<FlightMission> flightMissions;

    public List<FlightMission> getFlightMissions() {
        return flightMissions;
    }


    private static Cache instance;
    private Cache(){
        flightMissions = new ArrayList<>();
    }
    public static Cache getInstance(){
        if (instance==null){
            instance = new Cache();
        }
        return instance;
    }

    public void refreshCache(){
        if(flightMissions.size() != 0 ) {
            LocalDateTime temp = LocalDateTime.now();
            for (int i = 0; i < flightMissions.size(); i++) {
                if(flightMissions.get(i).getMissionResult()==MissionResult.IN_PROGRESS) {
                    if (flightMissions.get(i).getEndDate().isBefore(LocalDateTime.now())) {
                        Random random = new Random();
                        int res = random.nextInt(101);
                        if (res <= 75) {
                            flightMissions.get(i).setMissionResult(MissionResult.COMPLETED);
                            flightMissions.get(i).getAssignedSpaceShip().setReadyForNextMissions(true);
                            flightMissions.get(i).getAssignedCrew().stream()
                                    .forEach(crew -> crew.setReadyForNextMissions(true));
                        } else {
                            System.out.println("SpaceShip" + flightMissions.get(i).getAssignedSpaceShip().getName()
                                    + "crashed\n" + "Crew of this SpaceShip was dead");
                            flightMissions.get(i).setMissionResult(MissionResult.FAILED);
                        }
                    }
                }
            }
        }
    }
}
