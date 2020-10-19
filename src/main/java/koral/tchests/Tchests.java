package koral.tchests;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;

public final class Tchests extends JavaPlugin {
private TchestsListener Listener;
private TchestsCommands commandExecutor;

    static HashMap<String, Boolean> editor = new HashMap<>();
    @Override
    public void onEnable() {
        File file = new File(getDataFolder() + File.separator + "config.yml"); //This will get the config file
        if (!file.exists()) {
            saveDefaultConfig();;
        } else {

            saveDefaultConfig();
            reloadConfig();
        }

        this.Listener = new TchestsListener(this);
        this.commandExecutor = new TchestsCommands(this);
        getServer().getPluginManager().registerEvents(Listener, this);
        this.getCommand("tchestaddnew").setExecutor(this.commandExecutor);
        this.getCommand("tchestremove").setExecutor(this.commandExecutor);
        this.getCommand("tchestremove").setTabCompleter(new TchestsTabCompleter(this));
        this.getCommand("tchesttp").setExecutor(this.commandExecutor);
        this.getCommand("tchesttp").setTabCompleter(new TchestsTabCompleter(this));
        this.getCommand("tchesteditor").setExecutor(this.commandExecutor);
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
