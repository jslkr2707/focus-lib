package ages.util;

import ages.type.*;
import arc.*;
import arc.util.Log;
import mindustry.ctype.*;
import mindustry.game.Objectives.*;
import mindustry.type.*;

import static mindustry.Vars.*;

public class AgesObjectives {
    public static class sectorsCompleted implements Objective {
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
            return Core.bundle.format("requirements.sectorscompleted", standard);
        }
    }

    public static class notUnlocked implements Objective{
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

    public static class focusResearch implements Objective {
        public Focus[] prerequisite;

        public focusResearch(Focus... focus) { this.prerequisite = focus; }

        @Override
        public boolean complete() {
            for (Focus f: prerequisite) {
                if (!f.unlocked()) return false;
            }
            return true;
        }

        @Override
        public String display(){
            return Core.bundle.format("focus.prerequisite") + "\n";
        }
    }
}
