package industrial.util;

import arc.Core;
import mindustry.game.Objectives;
import mindustry.type.Planet;
import mindustry.type.Sector;

import static mindustry.Vars.content;

public class IndObjectives{
    public static class sectorsCompleted implements Objectives.Objective {
        public int standard, completed;

        public sectorsCompleted(int standard){
            this.standard = standard;
        }

        @Override
        public boolean complete(){
            return checkSectors() >= this.standard;
        }

        @Override
        public String display(){
            return Core.bundle.format("requirements.sectorsCompleted", standard);
        }

        public int checkSectors(){
            completed = 0;
            for(Planet planet: content.planets()){
                for (Sector sector: planet.sectors){
                    if (sector.isCaptured()){
                        completed += 1;
                    }
                }
            }
            return completed;
        }
    }
}
