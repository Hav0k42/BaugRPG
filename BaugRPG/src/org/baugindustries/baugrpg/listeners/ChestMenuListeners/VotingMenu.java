package org.baugindustries.baugrpg.listeners.ChestMenuListeners;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class VotingMenu implements Listener {
	private Main plugin;
	public VotingMenu(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player)) return;
		if (!(event.getClickedInventory() != null && event.getCurrentItem() != null)) return;
		if (!(event.getView().getTopInventory().equals(event.getClickedInventory())) && event.getCursor() == null) return;
		if (!(event.getView().getTitle().contains(ChatColor.DARK_AQUA + "Voting") || event.getView().getTitle().contains(ChatColor.DARK_GREEN + "Voting"))) return;
		Player player = (Player)event.getWhoClicked();
		PersistentDataContainer data = player.getPersistentDataContainer();
		int race = data.get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER);
		
		int page = 0;
		String lastChar = event.getView().getTitle().charAt(event.getView().getTitle().length() - 1) + "";
		if (plugin.isInteger(lastChar)) {
			page = Integer.parseInt(lastChar);
		}

		File leaderDataFile = new File(plugin.getDataFolder() + File.separator + "leaderData.yml");
		FileConfiguration leaderConfig = YamlConfiguration.loadConfiguration(leaderDataFile);
		
		if (event.getCurrentItem().equals(plugin.itemManager.getBackItem())) {
			player.openInventory(plugin.inventoryManager.getGovernmentMenuInventory(player));
		}
		
		if (event.getCurrentItem().equals(plugin.itemManager.getNextPageItem()) ) {
			player.openInventory(plugin.inventoryManager.getVotingMenu(race, page + 1));
		}
		
		if (event.getCurrentItem().equals(plugin.itemManager.getPreviousPageItem()) ) {
			player.openInventory(plugin.inventoryManager.getVotingMenu(race, page - 1));
		}
		
		
		if (event.getCurrentItem().getType().equals(Material.PLAYER_HEAD) && event.getCurrentItem().getItemMeta().getDisplayName().contains(ChatColor.YELLOW + "Vote for ")) {
			OfflinePlayer candidate = ((SkullMeta)event.getCurrentItem().getItemMeta()).getOwningPlayer();
			
			String configString = "VotedPlayers";
			if (race == 1) {
				if (leaderConfig.contains("menElectionActive") && leaderConfig.getBoolean("menElectionActive")) {
					configString = "men" + configString;
				}
			} else if (race == 2) {
				if (leaderConfig.contains("elfElectionActive") && leaderConfig.getBoolean("elfElectionActive")) {
					configString = "elf" + configString;
				}
			}
			
				if (leaderConfig.contains(configString)) {
					List<String> votedPlayers = leaderConfig.getStringList(configString);
					votedPlayers.add(player.getUniqueId().toString());
					leaderConfig.set(configString, votedPlayers);
				} else {
					List<String> votedPlayers = new ArrayList<String>();
					votedPlayers.add(player.getUniqueId().toString());
					leaderConfig.set(configString, votedPlayers);
				}
			
			if (leaderConfig.contains(candidate.getUniqueId().toString() + "Votes")) {
				leaderConfig.set(candidate.getUniqueId().toString() + "Votes", leaderConfig.getInt(candidate.getUniqueId().toString() + "Votes") + 1);
			} else {
				leaderConfig.set(candidate.getUniqueId().toString() + "Votes", 1);
			}
			
			
			try {
				leaderConfig.save(leaderDataFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			player.openInventory(plugin.inventoryManager.getGovernmentMenuInventory(player));
		}
		
		event.setCancelled(true);
	}
}
