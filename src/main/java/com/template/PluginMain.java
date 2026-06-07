package com.template;

import com.template.managers.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginMain extends JavaPlugin {

    private ConfigManager configManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.configManager = new ConfigManager(this);
        
        getLogger().info("MinecraftPluginTemplate enabled!");
        getLogger().info("Pipeline integration is active.");
        
        if (configManager.isFeatureEnabled()) {
            getLogger().info("Core features are enabled.");
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("MinecraftPluginTemplate disabled!");
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}
