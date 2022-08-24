package com.kcreates.mods.klion.modules;

import com.kcreates.mods.klion.Category;
import com.kcreates.mods.klion.KLion;
import com.kcreates.mods.klion.modules.disabled.EntityOwners;
import com.kcreates.mods.klion.modules.disabled.HealthTags;
import com.kcreates.mods.klion.modules.movement.*;
import com.kcreates.mods.klion.modules.miscellaneous.*;
import com.kcreates.mods.klion.modules.combat.*;
import com.kcreates.mods.klion.modules.player.*;
import com.kcreates.mods.klion.modules.render.*;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ModuleManager {
    public ModuleManager() {
        // COMBAT
        register(new AntiKnockback());
        register(new KillAura());
        register(new Criticals());

        // MISC
        register(new ReportDeath());
        register(new AntiCactus());

        // MOVEMENT
        register(new Flight());
        register(new Parkour());
        register(new AutoSprint());
        register(new Speed());

        // PLAYER
        register(new NoFall());
        register(new Step());
        register(new Reach());
        register(new FluidWalk());

        // RENDER
        register(new TabGUI());
        register(new XRay());
        register(new EntityESP());
        register(new Tracers());
        register(new Fullbright());
    }

    private static final List<Module> modules = new ArrayList<>();
    public static final Category[] categories = Category.values();

    public static void register(Module module) {
        modules.add(module);
    }
    public static List<Module> getModules() {
        return modules;
    }
    public static void _each(Consumer<Module> consumer) {
        for (Module module : modules) {
            consumer.accept(module);
        }
    }
    public static void onTick() {
        _each(Module::onTick);
    }
    public static boolean onKey(int key) {
        boolean[] cancelled = {false, KLion.isInGui()};
        _each(module -> {
            if (!cancelled[0]) cancelled[0] = module.onKey(key, false, cancelled[1]);
            else module.onKey(key, true, cancelled[1]);
            if (!cancelled[0] && !cancelled[1]) {
                if (key != GLFW.GLFW_KEY_UNKNOWN && module.getKey() == key) module.toggle();
            }
        });
        return cancelled[0];
    }
    public static boolean onEvent(Object event) {
        boolean[] cancelled = {false};
        _each(module -> {
            if (!cancelled[0]) cancelled[0] = module.onEvent(event, false);
            else module.onEvent(event, true);
        });
        return cancelled[0];
    }
    public static void onGameLoad() {
        _each(Module::onGameLoad);
    }
    public static List<Module> getModules(Category category) {
        List<Module> modules = new ArrayList<>();
        _each(module -> {
            if (module.getCategory() == category) modules.add(module);
        });
        return modules;
    }
    public static List<Module> getModules(int category) {
        return getModules(categories[category]);
    }
    public static Module getModule(Category category, int module) {
        return getModules(category).get(module);
    }
    public static Module getModule(int category, int module) {
        return getModules(category).get(module);
    }
    public static Module getModule(String name) {
        for (Module m : modules) {
            if (m.getName().toLowerCase().contentEquals(name.toLowerCase())) return m;
        }
        return null;
    }
    public static String getCategoryNameRaw(Category category) {
        return category.name();
    }
    public static String getCategoryNameRaw(int category) {
        return categories[category].name();
    }
    public static String getCategoryName(Category category) {
        String str = category.name();
        return getCase(str);
    }
    public static String getCategoryName(int category) {
        return getCategoryName(categories[category]);
    }
    public static Category getCategory(int category) {
        return categories[category];
    }
    public static String getCase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
    public static Module getModule(Class<?> base) {
        for (Module m : modules) {
            if (m.getClass() == base) return m;
        }
        return null;
    }
}
