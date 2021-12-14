package industrial.type;

import arc.struct.Seq;
import com.sun.istack.internal.NotNull;
import mindustry.content.Items;
import mindustry.ctype.ContentType;
import mindustry.ctype.UnlockableContent;
import mindustry.type.ItemStack;

public class Focus extends UnlockableContent {
    public ItemStack[] requirements;
    /* if Focus A is opposite with Focus B, only one of them can be unlocked. */
    public Focus opposite;

    public Focus(String name){
        super(name);
    }

    public void requirements(ItemStack[] stack){
        this.requirements = stack;
    }

    @Override
    public void onUnlock(){
        opposite.requirements = ItemStack.with(Items.copper, 2147483647);
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
