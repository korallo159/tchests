package koral.tchests;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class TchestsTabCompleter implements TabCompleter {
Tchests plugin;
    public TchestsTabCompleter(final Tchests plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length <= 1){

            ConfigurationSection cfgList = this.plugin.getConfig().getConfigurationSection("Chest.");
            if (cfgList == null || cfgList.getKeys(false).size() == 0) {
                return null;
            }
            List<String> lista = new ArrayList<>();
            for (String s: cfgList.getKeys(false))
            {
                lista.add(s);
            }
            return lista;

        }
        return null;
    }
}
