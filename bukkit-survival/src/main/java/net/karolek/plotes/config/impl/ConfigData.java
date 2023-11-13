package net.karolek.plotes.config.impl;

import lombok.Getter;

@Getter
public class ConfigData {

    private final String serverName;

    public ConfigData() {
        this.serverName = "&6Wizard&fHub.PL";
    }
}
