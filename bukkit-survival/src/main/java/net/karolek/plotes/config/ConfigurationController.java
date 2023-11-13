package net.karolek.plotes.config;

import lombok.Getter;
import net.karolek.plotes.BukkitPlugin;
import net.karolek.plotes.config.impl.ConfigData;
import net.karolek.plotes.utils.GsonUtil;

@Getter
public class ConfigurationController {

    private final ConfigData configData;

    public ConfigurationController() {
        this.configData = (ConfigData) GsonUtil.load(BukkitPlugin.getInstance(), new ConfigData());
    }
}
