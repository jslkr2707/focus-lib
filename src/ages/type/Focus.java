package ages.type;

import arc.Core;
import arc.struct.Seq;
import mindustry.ctype.*;
import mindustry.type.ItemStack;

import static ages.util.Overwriter.*;

public class Focus extends UnlockableContent{
    public ItemStack[] requirements;
    public ItemStack[] rewards;
    /* contents to unlock together when unlocked */
    public Seq<UnlockableContent> unlockContents = new Seq<>();

    public Focus(String name){
        super(name);

        hideDetails = false;

        this.localizedName = Core.bundle.get("focus." + this.name + ".name", this.name);
        this.description = Core.bundle.get("focus." + this.name + ".description");
        this.details = Core.bundle.getOrNull("focus." + this.name + ".details");
    }

    public void requirements(ItemStack[] stack){
        this.requirements = stack;
    }

    public void reward(ItemStack[] stack){
        this.rewards = stack;
    }

    public void unlock(UnlockableContent... content){
        for (UnlockableContent i: content){
            this.unlockContents.add(i);
        }
    }

    @Override
    public void onUnlock(){
        for (UnlockableContent content: this.unlockContents){
            if (content != null) content.unlock();
        }

        focusDialog.focusRewards.add(rewards);
        focusDialog.updateVisibility();
    }

    @Override
    public ItemStack[] researchRequirements() {
        return this.requirements;
    }

    @Override
    public ContentType getContentType(){
        return ContentType.typeid_UNUSED;
    }
}
