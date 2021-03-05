package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;

/**
 * Should be a builder for {@link com.epam.jwd.core_final.domain.CrewMember} fields
 */
public class CrewMemberCriteria extends Criteria<CrewMember> {
    private Role role;
    private Rank rank;
    private String name;
    private boolean isReadyForNextMissions = true;

    public void setRole(Role role) {
        this.role = role;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReadyForNextMissions(boolean readyForNextMissions) {
        isReadyForNextMissions = readyForNextMissions;
    }

    public Role getRole() {
        return role;
    }

    public Rank getRank() {
        return rank;
    }

    public String getName() {
        return name;
    }

    public boolean isReadyForNextMissions() {
        return isReadyForNextMissions;
    }

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

