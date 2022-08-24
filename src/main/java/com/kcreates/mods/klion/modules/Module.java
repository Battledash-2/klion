package com.kcreates.mods.klion.modules;

import com.kcreates.mods.klion.Category;
import com.kcreates.mods.klion.KLion;
import com.kcreates.mods.klion.gui.TabGUI.BooleanSetting;
import com.kcreates.mods.klion.gui.TabGUI.KeybindSetting;
import com.kcreates.mods.klion.gui.TabGUI.Setting;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.List;

public class Module extends DrawableHelper { // extend drawablehelper in case render modules need it
    protected MinecraftClient mc = KLion.mc;

    private String name;
    private int key;
    private boolean toggled;
    private Category category;
    private List<Setting> settings = new ArrayList<>();
    private int id = 0;

    public int getId() {
        return id;
    }

    private BooleanSetting toggleSetting;
    private KeybindSetting keybindSetting;

    public List<Setting> getSettings() {
        return settings;
    }
    public Setting addSetting(Setting setting) {
        this.settings.add(setting);
        return setting;
    }
    public void addSettings(Setting... settings) {
        for (Setting setting : settings) {
            this.settings.add(setting);
        }
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public static int getY() { return 18; };

    public void onEnable() {};
    public void onDisable() {};
    public void onToggle(boolean mode) {};
    public void onTick() {};
    public void onRender(WorldRenderContext context) {};
    public void onHudRender(MatrixStack stack) {};
    public boolean onKey(int key, boolean cancelled, boolean inGui) { return cancelled; };
    public boolean onEvent(Object event, boolean cancelled) { return cancelled; };
    public void onGameLoad() {};

    public void toggle(boolean t) {
        if (t) {
            onEnable();
        } else {
            onDisable();
        }
        onToggle(t);
        this.toggled = t;
        toggleSetting.setValue(t);
    }

    public void toggle() {
        toggle(!this.toggled);
    }

    public Category getCategory() {
        return category;
    }

    public int getKey() {
        return key;
    }
    public void setKey(int key) {
        this.key = key;
        keybindSetting.setKeybind(key);
    }

    public boolean isToggled() {
        return toggled;
    }
    public void setToggled(boolean toggled) {
        this.toggled = toggled;
        toggleSetting.setValue(toggled);
    }

    private void addDefaultSettings() {
        toggleSetting = (BooleanSetting)addSetting(new BooleanSetting("Toggled", setting -> {
            // this.setToggled(setting.getValue());
            // this.toggle();
            this.toggle(setting.getValue());
        }, this.toggled));
        keybindSetting = (KeybindSetting)addSetting(new KeybindSetting("Keybind", setting -> {
            this.setKey(setting.getKeybind());
        }, this.key));
    }
//    public Setting getSetting(int index) {
//        List<Setting> settings = new ArrayList<>();
//        int idx = 0;
//        for (Setting s : this.settings) {
//            if (idx == index+2) return s;
//            idx++;
//        }
//        return null;
//    }

    public Module(String name, int key, Category category) {
        this.name = name;
        this.key = key;
        this.category = category;
        this.toggled = false;
        this.id = ModuleManager.getModules().size();
        addDefaultSettings();
    }
    public Module(String name, int key, Category category, boolean defaultEnabled) {
        this.name = name;
        this.key = key;
        this.category = category;
        this.toggled = defaultEnabled;
        this.id = ModuleManager.getModules().size();
        addDefaultSettings();
    }
}
