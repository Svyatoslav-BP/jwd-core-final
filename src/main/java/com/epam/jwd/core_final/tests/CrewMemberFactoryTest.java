package com.epam.jwd.core_final.tests;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;

import static org.junit.jupiter.api.Assertions.*;

class CrewMemberFactoryTest {

    @org.junit.jupiter.api.Test
    void create() {
        CrewMemberFactory crewMemberFactory = new CrewMemberFactory();
        CrewMember crewMember = crewMemberFactory.create(Rank.FIRST_OFFICER, Role.COMMANDER,"Crew1");
        assertEquals(crewMember.getRank(),Rank.FIRST_OFFICER);
        assertEquals(crewMember.getRole(),Role.COMMANDER);
        assertEquals(crewMember.getName(),"Crew1");
    }
}