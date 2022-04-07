package org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Orcs;

import java.io.File;
import java.io.IOException;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.SkullMeta;


public class ExecutionMenuListener implements Listener {
	private Main plugin;
	public ExecutionMenuListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player)) return;
		if (!(event.getClickedInventory() != null && event.getCurrentItem() != null)) return;
		if (!(event.getView().getTopInventory().equals(event.getClickedInventory())) && event.getCursor() == null) return;
		if (!event.getView().getTitle().equals(ChatColor.DARK_RED + "Executions")) return;
		Player player = (Player)event.getWhoClicked();
		
		if (event.getCurrentItem().equals(plugin.itemManager.getBackItem())) {
			player.openInventory(plugin.inventoryManager.getGovernmentMenuInventory(player));
		}
		
		if (event.getCurrentItem().getType().equals(Material.PLAYER_HEAD) && event.getCurrentItem().getItemMeta().getDisplayName().contains(ChatColor.DARK_RED + "")) {
			if (plugin.orcVictim == null) {
				Player victim = ((SkullMeta)event.getCurrentItem().getItemMeta()).getOwningPlayer().getPlayer();
				
				File leaderDataFile = new File(plugin.getDataFolder() + File.separator + "leaderData.yml");
				FileConfiguration leaderConfig = YamlConfiguration.loadConfiguration(leaderDataFile);
				
				ConfigurationSection executions = leaderConfig.getConfigurationSection("executions");
				ConfigurationSection playerExecutions = executions.getConfigurationSection(player.getUniqueId().toString());
				int newValue = playerExecutions.getInt(victim.getUniqueId().toString()) - 1;
				if (newValue > 0) {
					playerExecutions.set(victim.getUniqueId().toString(), newValue);
				} else {
					playerExecutions.set(victim.getUniqueId().toString(), null);
				}
				
				//Teleport both players to the chopping block
				//Make the victim helpless and in swim mode.
				
				File claimsFile = new File(plugin.getDataFolder() + File.separator + "claims.yml");
				FileConfiguration claimsConfig = YamlConfiguration.loadConfiguration(claimsFile);
				
				player.closeInventory();
				victim.closeInventory();
				
				player.teleport(claimsConfig.getLocation("orcExecutioner"));
				victim.teleport(claimsConfig.getLocation("orcExecutionee"));
				victim.setSwimming(true);
				
				Bukkit.broadcastMessage(ChatColor.DARK_RED + victim.getDisplayName() + ChatColor.WHITE + " is being executed.");
				
				try {
					leaderConfig.save(leaderDataFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				plugin.orcVictim = victim.getUniqueId();
			} else if (plugin.orcVictim.equals(player.getUniqueId())) {
				player.closeInventory();
				player.sendMessage(ChatColor.RED + "I don't think so buddy.");
			} else {
				player.closeInventory();
				player.sendMessage(ChatColor.YELLOW + "An execution is already taking place.");
			}
		}
		
		event.setCancelled(true);
	}
}
