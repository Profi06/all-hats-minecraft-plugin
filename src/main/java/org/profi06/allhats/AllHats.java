package org.profi06.allhats;

import org.bukkit.plugin.java.JavaPlugin;

public final class AllHats extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getCommand("hatset").setExecutor(new HatCommandExecutor());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
