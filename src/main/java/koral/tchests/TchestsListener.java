package koral.tchests;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;


public class TchestsListener implements Listener {

    Tchests plugin;

    public TchestsListener(final Tchests plugin) {
        this.plugin = plugin;
    }


    /**
    **podczas zamykania inv sprawdza czy ma permisje tchest.create jesli tak, to sprawdza czy ma editora.
    **reszta zapisuje do configu items bez serialize
    **kazda nowa skrzynka to nowe ID, zeby mozna bylo sie fajnie do nich odnosic
     **/
    @EventHandler
    public void onTChestCloseSaveItems(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        if (e.getInventory().getHolder() instanceof Chest && ((Chest) e.getInventory().getHolder()).getCustomName() !=null
                && ((Chest) e.getInventory().getHolder()).getCustomName().equals(ChatColor.RED + "Treasure chest")
                && p.hasPermission("tchest.create")) 
        {
            if (plugin.editor.containsKey(p.getUniqueId().toString()) && plugin.editor.get(p.getUniqueId().toString()) == true ) {
                Chest chest = (Chest) e.getInventory().getHolder();
                Block b = chest.getBlock();
                ArrayList<ItemStack> items = new ArrayList<>();
                for (ItemStack is : e.getInventory()) {
                    if (is != null)
                        items.add(is);
                }
                int counter = 0;
                if (this.plugin.getConfig().getConfigurationSection("Chest." + counter) == null) {
                    this.plugin.getConfig().set("Chest." + counter + ".Items",items);
                    this.plugin.getConfig().set("Chest." + counter + ".Location", b.getLocation());
                    this.plugin.saveConfig();
                }
                for (final String ids : this.plugin.getConfig().getConfigurationSection("Chest.").getKeys(false)) {
                    if (b.getLocation().equals(this.plugin.getConfig().get("Chest." + counter + ".Location"))) {
                        p.sendMessage("Zedytowano zawartość treasure chesta.");
                        break;
                    }
                    counter++;
                }
                this.plugin.getConfig().set("Chest." + counter + ".Items",items);
                this.plugin.getConfig().set("Chest." + counter + ".Location", b.getLocation());
                this.plugin.saveConfig();
            }
        }
        else
            return;
    }
    /**
    Tu na szybkosci zrobilem, ze jak gracz zamyka to usuwa skrzynie, wypaduja itemy i pojawia sie skrzynia po prostu zarys,
     */


    @EventHandler
    public void onSingleChestCloseRemove(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        if (e.getInventory().getHolder() instanceof Chest
            && ((Chest) e.getInventory().getHolder()).getCustomName() !=null
            &&  ((Chest) e.getInventory().getHolder()).getCustomName().equals(ChatColor.RED + "Treasure chest")) {
            if(!plugin.editor.containsKey(p.getUniqueId().toString()) || plugin.editor.get(p.getUniqueId().toString()) == false ) {
                Chest chest = (Chest) e.getInventory().getHolder();
                Block block = chest.getBlock();
                Material previous = block.getType();
                block.setType(Material.AIR);
                Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> block.setType(previous), 80L);
                block.getState();
            }
            else
                return;
        }
    }



}
