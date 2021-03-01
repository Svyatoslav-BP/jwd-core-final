package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;

/**
 * Should be a builder for {@link com.epam.jwd.core_final.domain.CrewMember} fields
 */
public class CrewMemberCriteria extends Criteria<CrewMember> {

    public static class Builder extends BaseBuilder<CrewMember> {

        public CrewMemberCriteria.Builder addName(String name){
            thisClass = new CrewMember(name);
            return this;
        }

        public CrewMemberCriteria.Builder addRank(Rank rank) {
            thisClass.setRank(rank);
            return this;
        }

        public CrewMemberCriteria.Builder addRole(Role role) {
            thisClass.setRole(role);
            return this;
        }


        @Override
        protected CrewMember getThisClass() {
            return thisClass;
        }

    }
}

