package industrial.type;

import mindustry.type.ItemStack;
import mindustry.ui.dialogs.ResearchDialog;

public class ResourceFocus extends Focus{
    public ItemStack[] toAdd;

    public ResourceFocus(String name){
        super(name);
    }

    @Override
    public void onUnlock(){
        for (ItemStack stack: this.toAdd){
            new ResearchDialog().items.add(stack.item, stack.amount);
        }
    }


}
