package net.karolek.plotes;

import lombok.Getter;
import net.karolek.plotes.config.ConfigurationController;
import net.karolek.plotes.database.MongoManager;
import net.karolek.plotes.managers.plotes.PlotesManager;
import org.bukkit.event.Listener;

@Getter
public final class BukkitPlugin extends BukkitPluginDelegator {

    private MongoManager mongoManager;
    private ConfigurationController configurationController;

    private PlotesManager plotesManager;

    @Override
    public void load() {
        this.plotesManager = new PlotesManager();
        plotesManager.load();

    }

    @Override
    public void enable() {
        this.mongoManager = new MongoManager("mongodb://localhost:27017", "admin");
        this.configurationController = new ConfigurationController();

        this.registerCommands(

        );

        this.registerListeners(

        );
    }

    @Override
    public void disable() {
        super.disable();
    }

    @Override
    public void registerListeners(Listener... listeners) {
    }


    public static BukkitPlugin getInstance(){
        return getPlugin(BukkitPlugin.class);
    }
}
