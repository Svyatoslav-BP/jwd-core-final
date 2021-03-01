package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.InvalidArgsFactoryException;
import com.epam.jwd.core_final.factory.EntityFactory;

// do the same for other entities
public class CrewMemberFactory implements EntityFactory<CrewMember> {

    @Override
    public CrewMember create(Object... args) {
        Rank rank =null;
        Role role = null;
        String name = null;
        CrewMember crewMember = null;

        if(args.length !=3) System.out.println(new InvalidArgsFactoryException("Crew member1").getMessage());

        for (Object i:args){
            if(i instanceof Rank)
                rank = (Rank) i;
            else if(i instanceof Role)
                role = (Role) i;
            else if(i instanceof String)
                name = (String) i;
        }

        if(role == null || rank == null || name == null) {
            System.out.println(new InvalidArgsFactoryException("Crew member2").getMessage());
        }else try {
            crewMember = new CrewMemberCriteria.Builder()
                    .addName(name)
                    .addRank(rank)
                    .addRole(role)
                    .build();
        }catch(InvalidArgsFactoryException e){
            System.out.println(new InvalidArgsFactoryException("Crew member3").getMessage());

        }
        return crewMember;
    }
}
