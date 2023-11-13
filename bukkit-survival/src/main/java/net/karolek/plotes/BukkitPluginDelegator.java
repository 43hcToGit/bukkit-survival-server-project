package net.karolek.plotes;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitPluginDelegator extends JavaPlugin {

    private final PluginManager pluginManager = Bukkit.getPluginManager();

    public void load() {}

    public void enable() {}

    public void disable() {}

    @Deprecated
    @Override
    public void onEnable(){
        this.enable();
    }

    @Deprecated
    @Override
    public void onDisable(){
        this.disable();
    }

    @Deprecated
    @Override
    public void onLoad(){
        this.load();
    }

    public void registerCommands(){
    }

    public void registerListeners(Listener... listeners){
        for(Listener listener : listeners)
            this.pluginManager.registerEvents(listener, this);
    }
}
