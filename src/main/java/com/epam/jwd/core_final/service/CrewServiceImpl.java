package com.epam.jwd.core_final.service;

import com.epam.jwd.core_final.context.Application;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.DublicateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CrewServiceImpl implements CrewService{
    private static final Logger logger = LoggerFactory.getLogger("log");
    private static final Scanner scanner = new Scanner(System.in);
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private static CrewServiceImpl instance;
    private CrewServiceImpl(){
    }
    public static CrewServiceImpl getInstance(){
        if (instance==null){
            instance = new CrewServiceImpl();
        }
            return instance;
    }
    @Override
    public List<CrewMember> findAllCrewMembers() {
        return (List<CrewMember>) Application.nassaContext.retrieveBaseEntityList(CrewMember.class);
    }

    @Override
    public List<CrewMember> findAllCrewMembersByCriteria(Criteria<? extends CrewMember> criteria) {
        List<CrewMember> crewMembers = new ArrayList<>(Application.nassaContext.retrieveBaseEntityList(CrewMember.class));
        List<CrewMember> crewMembers2 = new ArrayList<>();
        if(((CrewMemberCriteria)criteria).getRank()!=null){
        crewMembers.stream()
                .filter(i -> i.getRank() == (((CrewMemberCriteria) criteria).getRank()))
                .filter(i -> i.isReadyForNextMissions() == (((CrewMemberCriteria) criteria).isReadyForNextMissions()))
                .forEach(i->crewMembers2.add(i));
        }
        else if(((CrewMemberCriteria)criteria).getRole()!=null){
            crewMembers.stream()
                    .filter(i -> i.getRole() == (((CrewMemberCriteria) criteria).getRole()))
                    .filter(i -> i.isReadyForNextMissions() == (((CrewMemberCriteria) criteria).isReadyForNextMissions()))
                    .forEach(i->crewMembers2.add(i));
        }
        else{
            crewMembers.stream()
                    .filter(i -> i.isReadyForNextMissions() == (((CrewMemberCriteria) criteria).isReadyForNextMissions()))
                    .forEach(i->crewMembers2.add(i));
        }
        return crewMembers2;
    }

    @Override
    public Optional<CrewMember> findCrewMemberByCriteria(Criteria<? extends CrewMember> criteria) {
        List<CrewMember> crewMembers = (ArrayList<CrewMember>)(Application.nassaContext.retrieveBaseEntityList(CrewMember.class));
        Optional<CrewMember> crewMember;
        if(((CrewMemberCriteria)criteria).getRank()!=null){
           crewMember = crewMembers.stream()
                    .filter(i -> i.getRank() == (((CrewMemberCriteria) criteria).getRank()))
                    .filter(i -> i.isReadyForNextMissions() == (((CrewMemberCriteria) criteria).isReadyForNextMissions()))
                   .findAny();
        }
        else if(((CrewMemberCriteria)criteria).getRole()!=null){
            crewMember = crewMembers.stream()
                    .filter(i -> i.getRole() == (((CrewMemberCriteria) criteria).getRole()))
                    .filter(i -> i.isReadyForNextMissions() == (((CrewMemberCriteria) criteria).isReadyForNextMissions()))
                    .findAny();
        }
        else if(((CrewMemberCriteria) criteria).getName()!=null)
        crewMember = crewMembers.stream()
                .filter(i -> i.getName().equals(((CrewMemberCriteria) criteria).getName()))
                .filter(i -> i.isReadyForNextMissions() == (((CrewMemberCriteria) criteria).isReadyForNextMissions()))
                .findAny();
        else {
            crewMember = crewMembers.stream()
                    .filter(i -> i.isReadyForNextMissions() == (((CrewMemberCriteria) criteria).isReadyForNextMissions()))
                    .findAny();
        }
        return crewMember;
    }

    @Override
    public CrewMember updateCrewMemberDetails(CrewMember crewMember) {
        System.out.println("Chose field of CrewMember\n 1:Role\n 2:Rank\n ");
        int a = scanner.nextInt();
        while (a!=1&&a!=2){
            logger.warn("Bad choice, try again");
             a = scanner.nextInt();
        }
        if (a==1){
            System.out.println("Chose Role\n 1:MISSION_SPECIALIST\n 2:FLIGHT_ENGINEER\n 3:PILOT\n 4:COMMANDER");
            int b = scanner.nextInt();
            while (b!=1&&b!=2&&b!=3&&b!=4){
                logger.warn("Bad choice, try again");
                b = scanner.nextInt();
            }
            crewMember.setRole(Role.resolveRoleById(b));
            return crewMember;
        }
        else if (a==2){
            System.out.println("Chose Rank\n 1:TRAINEE\n 2:SECOND_OFFICER\n 3:FIRST_OFFICER\n 4:CAPTAIN");
            int b = scanner.nextInt();
            while (b!=1&&b!=2&&b!=3&&b!=4){
                logger.warn("Bad choice, try again");
                b = scanner.nextInt();
            }
            crewMember.setRank(Rank.resolveRankById(b));
            return crewMember;
        }
        else {
            logger.warn("CrewMember wasn't updated");
            return crewMember;
        }
    }


    @Override
    public CrewMember createCrewMember(CrewMember crewMember) throws DublicateException, IOException {

            System.out.println("Chose Role\n 1:MISSION_SPECIALIST\n 2:FLIGHT_ENGINEER\n 3:PILOT\n 4:COMMANDER");
            int a = scanner.nextInt();
            while (a!=1&&a!=2&&a!=3&&a!=4){
                logger.warn("Bad choise, try again");
                a = scanner.nextInt();
            }
            crewMember.setRole(Role.resolveRoleById(a));
            System.out.println("Good choice");


            System.out.println("Chose Rank\n 1:TRAINEE\n 2:SECOND_OFFICER\n 3:FIRST_OFFICER\n 4:CAPTAIN");
            a = scanner.nextInt();
            while (a!=1&&a!=2&&a!=3&&a!=4){
                logger.warn("Bad choice, try again");
                a = scanner.nextInt();
            }
            crewMember.setRank(Rank.resolveRankById(a));
        System.out.println("Good choice");

            boolean flag =true;
        List<CrewMember> crewMembers = (List<CrewMember>) (Application.nassaContext.retrieveBaseEntityList(CrewMember.class));

        System.out.println("Enter name");
        do{
            try {
                 String b = reader.readLine();
                Optional<CrewMember> temp = crewMembers.stream()
                        .filter(i -> i.getName().equals(b))
                        .findAny();
                if (temp.isPresent()) {
                    throw new DublicateException();
                } else {
                    flag = false;
                    crewMember.setName(b);
                }
            }catch( DublicateException e){
                System.out.println(e.getMessage());
                System.out.println("try again");
            }
        }while (flag);
        crewMembers.add(crewMember);
        return crewMember;
    }
}
