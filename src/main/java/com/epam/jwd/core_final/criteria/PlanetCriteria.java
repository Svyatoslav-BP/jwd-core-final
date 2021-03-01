package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.Role;

import java.util.Map;

public class PlanetCriteria extends Criteria<Planet>{
    public static class Builder extends BaseBuilder<Planet> {

        public PlanetCriteria.Builder addName(String name){
            thisClass = new Planet(name);
            return this;
        }

        public PlanetCriteria.Builder addX(int x) {
            thisClass.getPoint().setX(x);
            return this;
        }

        public PlanetCriteria.Builder addY(int y) {
            thisClass.getPoint().setY(y);
            return this;
        }

        @Override
        protected Planet getThisClass() {
            return thisClass;
        }

    }
}
