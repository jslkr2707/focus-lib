package industrial.type;

import arc.util.*;
import mindustry.ClientLauncher;
import mindustry.Vars;
import mindustry.content.Items;
import mindustry.core.UI;
import mindustry.ctype.ContentType;
import mindustry.type.Item;
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
            Vars.ui.research.items.add(stack.item, stack.amount);
        }
    }

    public void setToAdd(ItemStack[] toAdd) {
        this.toAdd = toAdd;
    }

    @Override
    public ItemStack[] researchRequirements() {
        return ItemStack.empty;
    }
}
