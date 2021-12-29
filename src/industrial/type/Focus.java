package industrial.type;

import arc.Core;
import arc.Events;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Nullable;
import mindustry.Vars;
import mindustry.content.Items;
import mindustry.ctype.ContentType;
import mindustry.ctype.UnlockableContent;
import mindustry.game.EventType;
import mindustry.type.ItemStack;

import static mindustry.type.ItemStack.*;

public class Focus extends UnlockableContent{
    public ItemStack[] requirements;
    /* what contents to unlock together when unlocked */
    public Seq<UnlockableContent> unlockContents = new Seq<>();

    public Focus(String name){
        super(name);
    }

    public void requirements(ItemStack[] stack){
        this.requirements = stack;
    }


    public void addUnlocks(UnlockableContent... content){
        for (UnlockableContent i: content){
            this.unlockContents.add(i);
        }
    }

    @Override
    public void onUnlock(){
        if (this.unlockContents != null){
            for (UnlockableContent content: this.unlockContents) {
                content.unlock();
            }
        }
    }

    @Override
    public ItemStack[] researchRequirements() {
        return this.requirements;
    }

    @Override
    public ContentType getContentType(){
        return ContentType.effect_UNUSED;
    }
}
