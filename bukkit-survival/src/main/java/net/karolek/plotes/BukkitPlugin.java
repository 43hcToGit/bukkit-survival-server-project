package net.karolek.plotes;

import org.bukkit.event.Listener;

public final class BukkitPlugin extends BukkitPluginDelegator {

    @Override
    public void load() {

    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

    @Override
    public void registerListeners(Listener... listeners) {
    }


    public static BukkitPlugin getInstance(){
        return getPlugin(BukkitPlugin.class);
    }
}
