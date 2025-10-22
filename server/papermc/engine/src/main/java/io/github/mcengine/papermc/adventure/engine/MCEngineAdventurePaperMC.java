package io.github.mcengine.papermc.adventure.engine;

import io.github.mcengine.api.core.MCEngineCoreApi;
import io.github.mcengine.api.core.Metrics;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main PaperMC plugin class for MCEngineAdventure.
 */
public class MCEngineAdventurePaperMC extends JavaPlugin {

    /**
     * Called when the plugin is enabled.
     */
    @Override
    public void onEnable() {
        new Metrics(this, 26213);
        saveDefaultConfig(); // Save config.yml if it doesn't exist

        boolean enabled = getConfig().getBoolean("enable", false);
        if (!enabled) {
            getLogger().warning("Plugin is disabled in config.yml (enable: false). Disabling plugin...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        String license = getConfig().getString("licenses.license", "free"); 
        if (!license.equalsIgnoreCase("free")) { 
            getLogger().warning("Plugin is disabled in config.yml.");
            getLogger().warning("Invalid license.");
            getLogger().warning("Check license or use \"free\".");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Load extensions
        MCEngineCoreApi.loadExtensions(
            this,
            "io.github.mcengine.api.adventure.extension.library.IMCEngineAdventureLibrary",
            "libraries",
            "Library"
        );
        MCEngineCoreApi.loadExtensions(
            this,
            "io.github.mcengine.api.adventure.extension.api.IMCEngineAdventureAPI",
            "apis",
            "API"
        );
        MCEngineCoreApi.loadExtensions(
            this,
            "io.github.mcengine.api.adventure.extension.agent.IMCEngineAdventureAgent",
            "agents",
            "Agent"
        );
        MCEngineCoreApi.loadExtensions(
            this,
            "io.github.mcengine.api.adventure.extension.addon.IMCEngineAdventureAddOn",
            "addons",
            "AddOn"
        );
        MCEngineCoreApi.loadExtensions(
            this,
            "io.github.mcengine.api.adventure.extension.dlc.IMCEngineAdventureDLC",
            "dlcs",
            "DLC"
        );

        // Check for plugin updates
        MCEngineCoreApi.checkUpdate(
            this,
            getLogger(),
            "github",
            "MCEngine-Engine",
            "adventure",
            getConfig().getString("github.token", "null")
        );
    }

    /**
     * Called when the plugin is disabled.
     */
    @Override
    public void onDisable() {
        // Plugin shutdown logic (if any) can go here
    }
}
