package industrial.type;

import arc.struct.Seq;
import mindustry.content.UnitTypes;
import mindustry.type.ItemStack;
import mindustry.type.UnitType;

import java.util.Arrays;
import java.util.HashMap;

public class DivisionUnitType extends UnitType {
    public HashMap<UnitType, Integer> compose = new HashMap<>();

    public DivisionUnitType(String name){
        super(name);
        this.speed = setSpeed();
    }

    public void addCompose(UnitType[] type, Integer[] num){
        if (type.length != num.length) return;

        for(int i = 0;i < type.length;i++){
            this.compose.put(type[i], num[i]);
        }
    }

    public Float[] speeds(){
        Seq<Float> speed = new Seq<>();
        for (UnitType unit: this.compose.keySet()){
            speed.add(unit.speed);
        }

        return speed.toArray();
    }

    public float setSpeed(){
        Float[] aa = speeds();
        Arrays.sort(aa);
        return aa[0];
    }
}
