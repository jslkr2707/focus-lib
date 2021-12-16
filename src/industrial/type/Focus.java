package industrial.type;

import arc.struct.Seq;
import mindustry.content.Items;
import mindustry.ctype.ContentType;
import mindustry.ctype.UnlockableContent;
import mindustry.type.ItemStack;

import static mindustry.type.ItemStack.*;

public class Focus extends UnlockableContent {
    public ItemStack[] requirements;
    /* what contents to unlock together when unlocked */
    public Seq<UnlockableContent> unlockContents = new Seq<>();
    /* if Focus A is opposite with Focus B, only one of them can be unlocked. */
    public Focus opposite;

    public Focus(String name){
        super(name);
    }

    public void requirements(ItemStack[] stack){
        this.requirements = stack;
    }

    public void addUnlocks(UnlockableContent content){
            this.unlockContents.add(content);
    }

    @Override
    public void onUnlock(){
        if (this.unlockContents != null){
            for (UnlockableContent content: this.unlockContents) {
                content.unlock();
            }
        }
        this.opposite.requirements(with(Items.copper, 21290312));
    }

    @Override
    public ItemStack[] researchRequirements() {
        return requirements;
    }

    @Override
    public String toString(){
        return localizedName;
    }

    @Override
    public ContentType getContentType(){
        return ContentType.effect_UNUSED;
    }
}
