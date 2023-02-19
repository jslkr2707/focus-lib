package focus.ui.dialogs;

import focus.type.*;
import arc.*;
import arc.graphics.*;
import arc.input.*;
import arc.math.*;
import arc.scene.event.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.ctype.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.ui.dialogs.*;
import mindustry.world.*;

import static arc.Core.settings;
import static mindustry.Vars.*;
import static mindustry.Vars.ui;

public class FocusDatabaseDialog extends BaseDialog {
    TextField search;
    Table t = new Table();

    public FocusDatabaseDialog(){
        super("@");
        shouldPause = true;
        addCloseButton();
        shown(this::rebuild);
        onResize(this::rebuild);

        t.margin(20).marginTop(0f);

        cont.table(s -> {
            s.image(Icon.zoom).padRight(8);
            search = s.field(null, text -> rebuild()).growX().get();
            search.setMessageText("@players.search");
        }).fillX().padBottom(4).row();

        cont.pane(t).scrollX(false);
    }

    //i didn't want to, but somehow override doesn't work
    void rebuild(){
        t.clear();
        var text = search.getText();

        Seq<Content>[] allContent = Vars.content.getContentMap();

        for(int j = 0; j < allContent.length; j++){
            ContentType type = ContentType.all[j];

            Seq<Content> array = allContent[j]
                    .select(c -> (!(c instanceof UnlockableContent u) ||
                            (u.isHidden() && u.techNode == null) ||
                            (!text.isEmpty() && !u.localizedName.toLowerCase().contains(text.toLowerCase())) || c.minfo != null) && c.minfo.mod == mods.locateMod("focus"));//.filter(c -> !(c instanceof Fuel));
            if(array.size == 0) continue;

            t.add("@content." + type.name() + ".name").growX().left().color(Pal.accent);
            t.row();
            t.image().growX().pad(5).padLeft(0).padRight(0).height(3).color(Pal.accent);
            t.row();
            t.table(list -> {
                list.left();

                int cols = (int) Mathf.clamp((Core.graphics.getWidth() - Scl.scl(30)) / Scl.scl(32 + 12), 1, 22);
                int count = 0;

                for(int i = 0; i < array.size; i++){
                    UnlockableContent unlock = (UnlockableContent)array.get(i);

                    Image image = unlocked(unlock)? new Image(unlock.uiIcon).setScaling(Scaling.fit) : new Image(Icon.lock, Pal.gray);

                    //banned cross
                    if(state.isGame() && (unlock instanceof UnitType u && u.isBanned() || unlock instanceof Block b && state.rules.bannedBlocks.contains(b))){
                        list.stack(image, new Image(Icon.cancel){{
                            setColor(Color.scarlet);
                            touchable = Touchable.disabled;
                        }}).size(8 * 4).pad(3);
                    }else{
                        list.add(image).size(8 * 4).pad(3);
                    }

                    ClickListener listener = new ClickListener();
                    image.addListener(listener);
                    if(!mobile && unlocked(unlock)){
                        image.addListener(new HandCursorListener());
                        image.update(() -> image.color.lerp(!listener.isOver() ? Color.lightGray : Color.white, Mathf.clamp(0.4f * Time.delta)));
                    }

                    if(unlocked(unlock)){
                        image.clicked(() -> {
                            if(Core.input.keyDown(KeyCode.shiftLeft) && Fonts.getUnicode(unlock.name) != 0){
                                Core.app.setClipboardText((char)Fonts.getUnicode(unlock.name) + "");
                                ui.showInfoFade("@copied");
                            }else{
                                ui.content.show(unlock);
                            }
                        });
                        image.addListener(new Tooltip(t -> t.background(Tex.button).add(unlock.localizedName + (settings.getBool("console") ? "\n[gray]" + unlock.name : ""))));
                    }

                    if((++count) % cols == 0){
                        list.row();
                    }
                }
            }).growX().left().padBottom(10);
            t.row();
        }

        //fuelTable(t);

        if(t.getChildren().isEmpty()){
            t.add("@none.found");
        }
    }

    /*
    void fuelTable(Table t){
        Seq<Fuel> fuels = Seq.with(Vars.content.getBy(ContentType.all[0])).filter(c -> c instanceof Fuel).map(c -> (Fuel)c);
        t.add(Core.bundle.format("content.fuel.name")).growX().left().color(Pal.accent);
        t.row();
        t.image().growX().pad(5).padLeft(0).padRight(0).height(3).color(Pal.accent);
        t.row();

        t.table(list -> {
            list.left();

            int cols = (int) Mathf.clamp((Core.graphics.getWidth() - Scl.scl(30)) / Scl.scl(32 + 12), 1, 22);
            int count = 0;

            for(Fuel f: fuels){
                Image image = new Image(f.uiIcon);

                ClickListener listener = new ClickListener();
                image.addListener(listener);
                if(!mobile){
                    image.addListener(new HandCursorListener());
                    image.update(() -> image.color.lerp(!listener.isOver() ? Color.lightGray : Color.white, Mathf.clamp(0.4f * Time.delta)));
                }

                image.clicked(() -> {
                    if(Core.input.keyDown(KeyCode.shiftLeft) && Fonts.getUnicode(f.name) != 0){
                        Core.app.setClipboardText((char)Fonts.getUnicode(f.name) + "");
                        ui.showInfoFade("@copied");
                    }else{
                        ui.content.show(f);
                    }
                });
                image.addListener(new Tooltip(ta -> ta.background(Tex.button).add(f.localizedName + (settings.getBool("console") ? "\n[gray]" + f.name : ""))));


                if((++count) % cols == 0){
                    list.row();
                }
            }
        }).growX().left().padBottom(10);
        t.row();
    }
     */

    boolean unlocked(UnlockableContent content){
        return (!Vars.state.isCampaign() && !Vars.state.isMenu()) || content.unlocked() || content instanceof Focus;
    }
}
