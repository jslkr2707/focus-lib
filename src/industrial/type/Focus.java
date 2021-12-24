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

public class Focus extends UnlockableContent {
    public ItemStack[] requirements;
    /* what contents to unlock together when unlocked */
    public Seq<UnlockableContent> unlockContents = new Seq<>();
    /* if Focus A is opposite with Focus B, only one of them can be unlocked. */
    public Focus opposite;

    public Focus(String name){
        super(name);

        this.localizedName = Core.bundle.get("focus." + this.name + ".name");
    }

    public void requirements(ItemStack[] stack){
        this.requirements = stack;
    }

    public void setOpposite(Focus opposite){ this.opposite = opposite; }

    public void addUnlocks(UnlockableContent... content){
        for (UnlockableContent i: content){
            this.unlockContents.add(i);
        }
    }

    @Override
    public void onUnlock(){
        if (this.unlockContents.size > 0){
            for (UnlockableContent content: this.unlockContents) {
                content.unlock();
            }
        }
        if (this.opposite != null){
            this.opposite.requirements(with(Items.copper, 21290312));
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

    @Override
    public void load(){
        Events.on(EventType.ClientLoadEvent.class, e -> {
            Log.info(this.opposite);
        });
    }
}
