package ages.util;

import arc.Core;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Objectives;
import mindustry.type.*;

import static mindustry.Vars.*;

public class AgesObjectives {
    public static class sectorsCompleted implements Objectives.Objective {
        public int standard;

        public sectorsCompleted(int standard){
            this.standard = standard;
        }

        @Override
        public boolean complete(){
            return completed() >= this.standard;
        }

        public static int completed(){
            int a = 0;
            for(Planet planet: content.planets()){
                for (Sector sector: planet.sectors){
                    if (sector.isCaptured()){
                        a += 1;
                    }
                }
            }
            return a;
        }

        @Override
        public String display(){
            return Core.bundle.format("requirements.sectorsCompleted", standard);
        }
    }

    public static class notUnlocked implements Objectives.Objective{
        public UnlockableContent content;

        public notUnlocked(UnlockableContent content) { this.content = content; }

        @Override
        public boolean complete(){
            return !content.unlocked();
        }

        @Override
        public String display(){
            return Core.bundle.format("requirements.notUnlocked", content.toString());
        }
    }
}
