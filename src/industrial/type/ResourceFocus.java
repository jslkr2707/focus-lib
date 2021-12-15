package industrial.type;

import mindustry.type.ItemStack;
import mindustry.ui.dialogs.ResearchDialog;

public class ResourceFocus extends Focus{
    public ItemStack[] item;

    public ResourceFocus(String name){
        super(name);
    }

    public void setAdds(ItemStack[] add){
        this.item = add;
    }

    @Override
    public void onUnlock(){
        giveItems(this.item);
    }

    public void giveItems(ItemStack[] given){
        for (ItemStack i: given){
            new ResearchDialog().items.add(i);
        }
    }
}
