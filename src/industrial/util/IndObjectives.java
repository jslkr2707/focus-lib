package industrial.util;

import arc.Core;
import mindustry.game.Objectives;
import mindustry.type.Planet;
import mindustry.type.Sector;

import static mindustry.Vars.content;

public class IndObjectives{
    public static class sectorCompletes implements Objectives.Objective{
        public int completeNum;
        public int howManySectors = 0;

        public sectorCompletes(int num){ this.completeNum = num; }

        protected sectorCompletes(){}

        @Override
        public boolean complete(){
            return checkSectors() >= this.completeNum;
        }

        @Override
        public String display(){
            return Core.bundle.format("requirements.sectorNum", completeNum);
        }

        /* checks how many sectors are captured */
        public int checkSectors(){
            howManySectors = 0;
            for (Planet planet: content.planets()){
                for (Sector sector: planet.sectors){
                    if (sector.isCaptured()){ howManySectors += 1; }
                }
            }
            return howManySectors;
        }

    }
}
