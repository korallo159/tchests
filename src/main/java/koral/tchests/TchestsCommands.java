package koral.tchests;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class TchestsCommands implements CommandExecutor {

    Tchests plugin;
    public TchestsCommands(final Tchests plugin) {
        this.plugin = plugin;
    }

    /**
    tchestaddnew dodaje skrzynie
     tchestremove usuwa skrzynie razem z id
     tchesttp teleportuje do id z skrzynia
     tchesteditor to edytor skrzyni
     */

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = ((Player) sender).getPlayer();
            if(command.getName().equals("tchestaddnew")){
                player.getInventory().addItem(getTchest(1));
                player.sendMessage(ChatColor.RED + "Stworzyles treasure chesta. Wloz do niego przedmioty. Po zamknieciu przedmioty sie zapisza.");
            }
            if(command.getName().equals("tchestremove") && StringUtils.isNumeric(args[0])){
                Integer id = Integer.valueOf(args[0]);
                if(this.plugin.getConfig().get("Chest." +id) != null) {
                    Block b = player.getWorld().getBlockAt(this.plugin.getConfig().getLocation("Chest." + id + ".Location"));
                    b.setType(Material.AIR);
                    this.plugin.getConfig().set("Chest." + id, null);
                    this.plugin.saveConfig();
                    player.sendMessage("Usunales skrzynie z ID: " + (args[0]));
                }
                else
                    player.sendMessage("Nie ma skrzyni z takim ID");
            }
            if(command.getName().equals("tchesttp") && StringUtils.isNumeric(args[0])){
                Integer id = Integer.valueOf(args[0]);
                if(this.plugin.getConfig().get("Chest." +id + ".Location") != null) {
                    player.teleport(this.plugin.getConfig().getLocation("Chest." + id + ".Location"));
                    player.sendMessage("Przeteleportowano do skrzyni z id:" + id);
                }
                else player.sendMessage("Nie ma skrzyni z takim ID");

            }

            if(command.getName().equals("tchesteditor")){
                if(plugin.editor.get(player.getUniqueId().toString()) == null || plugin.editor.get(player.getUniqueId().toString()) == false) {
                    plugin.editor.put(player.getUniqueId().toString(), true);
                    player.sendMessage("Wlaczyles edytowanie treasure chestow");
                }
                else if(plugin.editor.get(player.getUniqueId().toString()) == true){
                    plugin.editor.put(player.getUniqueId().toString(), false);
                    player.sendMessage("Wylaczyles edytowanie tchestow.");
                }
            }


        }
        else return true;


        return true;
    }



    public ItemStack getTchest(int amount) {
        ItemStack tchest = new ItemStack(Material.CHEST);
        ItemMeta itemMeta = tchest.getItemMeta();

        itemMeta.setDisplayName(ChatColor.RED + "Treasure chest");

        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.DARK_RED + "treasure chest");
        itemMeta.setLore(lore);

        tchest.setItemMeta(itemMeta);

        return tchest;
    }
}
