package industrial.type;

import mindustry.ctype.ContentType;
import mindustry.ctype.UnlockableContent;
import mindustry.type.ItemStack;

public class Focus extends UnlockableContent {
    public ItemStack[] requirements;

    public Focus(String name){
        super(name);
    }

    public void requirements(ItemStack[] stack){
        this.requirements = stack;
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