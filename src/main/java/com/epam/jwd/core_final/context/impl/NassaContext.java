package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.factory.impl.PlanetFactory;
import com.epam.jwd.core_final.factory.impl.SpaceshipFactory;
import com.epam.jwd.core_final.util.PropertyReaderUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// todo
public class NassaContext implements ApplicationContext {

    // no getters/setters for them
    private Collection<CrewMember> crewMembers = new ArrayList<>();
    private Collection<Spaceship> spaceships = new ArrayList<>();
    private Collection<Planet> planetMap = new ArrayList<>();

    private final ApplicationProperties applicationProperties = new ApplicationProperties();


    @Override
    public <T extends BaseEntity> Collection<T> retrieveBaseEntityList(Class<T> tClass) {
        if(tClass == CrewMember.class){
            return (Collection<T>) crewMembers;
        }
        else if(tClass == Spaceship.class){
            return (Collection<T>) spaceships;
        }
        else if(tClass == Planet.class){
            return (Collection<T>) planetMap;
        }
        return null;
    }

    /**
     * You have to read input files, populate collections
     *
     * @throws InvalidStateException
     */

    @Override
    public void init() throws InvalidStateException {
        String crewFile = "src/main/resources/"+applicationProperties.getInputRootDir()+"/"+applicationProperties.getCrewFileName();
        String spaceshipFile = "src/main/resources/"+applicationProperties.getInputRootDir()+"/"+applicationProperties.getSpaceshipsFileName();
        String spacemapFile = "src/main/resources/"+applicationProperties.getInputRootDir()+"/"+applicationProperties.getPlanetFileName();

        PlanetFactory planetFactory = new PlanetFactory();
        SpaceshipFactory spaceshipFactory = new SpaceshipFactory();
        CrewMemberFactory crewMemberFactory = new CrewMemberFactory();

        try (BufferedReader readerCrew = new BufferedReader(new FileReader(crewFile))) {
            String crewName="";
            int buf;
            int roleId;
            int rankId;
            while ((buf = readerCrew.read())!=-1) {
                if(buf=='#')
                    readerCrew.readLine();
                else if (buf !=';'){
                    crewName+=(char)buf;
                }
                else{
                    roleId = Integer.parseInt(crewName.substring(0,1));
                    rankId = Integer.parseInt(crewName.substring(crewName.length()-1));
                    crewName = crewName.substring(2,crewName.length()-2);
                    crewMembers.add(crewMemberFactory.create(Rank.resolveRankById(rankId),Role.resolveRoleById(roleId),crewName));
                    crewName="";
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader readerSpaceship = new BufferedReader(new FileReader(spaceshipFile))) {
            String spaceshipName="";
            String temp = "";
            int buf;
            Map<Role,Short> crewMap = new HashMap<>();
            Long flightDistance;
            while ((buf = readerSpaceship.read())!=-1) {
                if(buf=='#')
                    readerSpaceship.readLine();
                else if(buf!='\n'){
                    if(Character.isLetter((char)buf)||buf==' '){
                        spaceshipName+=(char)buf;
                    }
                    temp+=(char)buf;
                }
                else {
                    temp=temp.substring(spaceshipName.length()+1);
                    flightDistance = Long.parseLong(temp.substring(0,temp.indexOf(';')));
                    temp=temp.substring(temp.indexOf(';')+1);
                    crewMap.put(Role.resolveRoleById(1),Short.parseShort(temp.substring(temp.indexOf(':')+1,temp.indexOf(','))));
                    temp=temp.substring(temp.indexOf(',')+1);
                    crewMap.put(Role.resolveRoleById(2),Short.parseShort(temp.substring(temp.indexOf(':')+1,temp.indexOf(','))));
                    temp=temp.substring(temp.indexOf(',')+1);
                    crewMap.put(Role.resolveRoleById(3),Short.parseShort(temp.substring(temp.indexOf(':')+1,temp.indexOf(','))));
                    temp=temp.substring(temp.indexOf(',')+1);
                    crewMap.put(Role.resolveRoleById(4),Short.parseShort(temp.substring(temp.indexOf(':')+1,temp.indexOf('}'))));
                    spaceships.add(spaceshipFactory.create(crewMap,flightDistance,spaceshipName));
                    temp="";
                    spaceshipName="";
                    crewMap = new HashMap<>();
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader readerSpacemap = new BufferedReader(new FileReader(spacemapFile))) {
            String spaceMapName="";
            int k = 1;
            int i = 1;
            int buf;
            while ((buf = readerSpacemap.read())!=-1) {
                if(buf!='\n') {
                    if(buf!=','){
                        spaceMapName += (char) buf;
                    }
                    else{
                        if(!spaceMapName.equalsIgnoreCase("null")){
                            planetMap.add(planetFactory.create(i, k, spaceMapName));
                            spaceMapName="";
                        }
                        else {
                            ++i;
                            spaceMapName="";
                        }
                    }
                }
                else {
                    ++k;i=1;
                    spaceMapName="";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //throw new InvalidStateException();
        }
    }

