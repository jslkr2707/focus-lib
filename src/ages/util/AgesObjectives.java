package ages.util;

import ages.core.*;
import ages.type.*;
import arc.*;
import arc.util.Log;
import mindustry.ctype.*;
import mindustry.game.Objectives.*;
import mindustry.type.*;

import static mindustry.Vars.*;
import static ages.util.Useful.*;

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

        @Override
        public String display(){
            return Core.bundle.format("requirements.sectorscompleted", standard);
        }
    }

    public static class notUnlocked implements Objective{
        public Focus opposite;

        public notUnlocked(Focus content) { this.opposite = content; }

        @Override
        public boolean complete(){
            return !opposite.unlocked();
        }

        @Override
        public String display(){
            return Core.bundle.format("requirements.notunlocked", opposite.localizedName);
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
