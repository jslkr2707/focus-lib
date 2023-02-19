package focus.util;

import focus.type.*;
import focus.ui.dialogs.*;
import arc.*;
import mindustry.*;
import mindustry.ctype.*;
import mindustry.game.Objectives.*;
import mindustry.type.*;

import static mindustry.Vars.*;

public class FObjectives {
    public static class customResearch implements Objective {
        public UnlockableContent content;

        public customResearch(UnlockableContent content){ this.content = content;};

        @Override
        public boolean complete() { return content.unlocked();}

        @Override
        public String display() { return Core.bundle.format("requirements.unlocked", content.localizedName); }
    }

    public static class sectorsCompleted implements Objective {
        public int standard;

        public sectorsCompleted(int standard){
            this.standard = standard;
        }

        @Override
        public boolean complete(){
            return FocusDialog.completed() >= this.standard;
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

    public static class inGame implements Objective {
        boolean playing = Vars.state.isCampaign();

        @Override
        public boolean complete() {
            return playing;
        }

        @Override
        public String display(){
            return Core.bundle.format("requirements.playing") + "\n";
        }
    }
}
