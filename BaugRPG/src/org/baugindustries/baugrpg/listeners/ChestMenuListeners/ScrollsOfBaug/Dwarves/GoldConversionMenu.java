package org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Dwarves;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import net.md_5.bungee.api.ChatColor;

public class GoldConversionMenu implements Listener{

	
	private Main plugin;
	public GoldConversionMenu(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			Player player = (Player)event.getWhoClicked();
			if (event.getClickedInventory() != null) {
				if (event.getView().getTitle().equals("Dwarven Gold Deposit")) {
					ItemStack confirmItem = plugin.createItem(
							Material.LIME_STAINED_GLASS_PANE, 
							1, 
							ChatColor.GREEN + "CONFIRM", 
							Arrays.asList(ChatColor.LIGHT_PURPLE + "Place gold ingots to the left, ", "and click here to confirm conversion."));
					
					if (event.getCurrentItem().equals(confirmItem)) {
						if (event.getView().getTopInventory().getItem(3) != null && event.getView().getTopInventory().getItem(3).getType().equals(Material.GOLD_INGOT)) {
							File file = new File(plugin.getDataFolder() + File.separator + "econ.yml");
						 	FileConfiguration config = YamlConfiguration.loadConfiguration(file);
						 	
						 	int balance = (int) config.get(player.getUniqueId().toString());
						 	int newBal = balance + (event.getView().getTopInventory().getItem(3).getAmount() * 10);
							player.sendMessage(ChatColor.YELLOW + "Your new balance is " + newBal + ".");
							config.set(player.getUniqueId().toString(), newBal);
							
							try {
								config.save(file);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								
							}
							InventoryClickEvent.getHandlerList().unregister(this);
							event.getView().close();
							event.setCancelled(true);
						}
					}
					if (event.getSlot() != 3 && event.getClickedInventory().getSize() == 9) {
						event.setCancelled(true);
					}
				}
			}
		}
	}
}

