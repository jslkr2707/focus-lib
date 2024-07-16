package focus.type;

import arc.Core;
import arc.struct.Seq;
import mindustry.ctype.*;
import mindustry.type.*;

public class Focus extends UnlockableContent{
    public int addSectors = 0;
    public ItemStack[] requirements;
    public ItemStack[] rewards;
    public Seq<UnlockableContent> unlockContents = new Seq<>();
    public Seq<Focus> exclusives = new Seq<>();

    public Focus(String name){
        super(name);

        hideDetails = false;
        this.localizedName = Core.bundle.get("focus." + this.name + ".name", this.name);
        this.description = Core.bundle.get("focus." + this.name + ".description");
        this.details = Core.bundle.getOrNull("focus." + this.name + ".details");
    }

    public void reward(ItemStack[] stack){
        this.rewards = stack;
    }

    public void exclude(Focus... content){
        for (Focus f: content){
            this.exclusives.add(f);
        }
    }

    public void unlock(UnlockableContent... content){
        for (UnlockableContent i: content){
            this.unlockContents.add(i);
        }
    }

    public void requirements(ItemStack[] stack){
        this.requirements = stack;
    }

    @Override
    public void onUnlock(){
        for (UnlockableContent content: this.unlockContents){
            if (content != null) content.unlock();
        }
    }

    @Override
    public void clearUnlock(){
        super.clearUnlock();

        if (Core.settings.has("current")) Core.settings.remove("current");
    }

    @Override
    public ItemStack[] researchRequirements(){
        return requirements;
    }

    @Override
    public ContentType getContentType(){
        return ContentType.typeid_UNUSED;
    }
}
