package industrial.type;

import arc.util.*;
import mindustry.core.UI;
import mindustry.ctype.ContentType;
import mindustry.type.ItemStack;
import mindustry.ui.dialogs.ResearchDialog;

public class ResourceFocus extends Focus{
    public static ResearchDialog researchDialog = new ResearchDialog();
    public ItemStack[] toAdd;

    public ResourceFocus(String name){
        super(name);
    }

    @Override
    public void onUnlock(){
        for (ItemStack stack: this.toAdd){
            researchDialog.items.add(stack.item, stack.amount);
        }

    }

    @Override
    public ItemStack[] researchRequirements() {
        return ItemStack.empty;
    }
}
