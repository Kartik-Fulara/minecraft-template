package com.template.managers;

import com.template.PluginMain;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Basic configuration manager for the template.
 */
public class ConfigManager {

    private final PluginMain plugin;

    public ConfigManager(PluginMain plugin) {
        this.plugin = plugin;
    }

    public void reload() {
        plugin.reloadConfig();
    }

    private FileConfiguration cfg() {
        return plugin.getConfig();
    }

    public boolean isFeatureEnabled() {
        return cfg().getBoolean("features.enabled", true);
    }
}
