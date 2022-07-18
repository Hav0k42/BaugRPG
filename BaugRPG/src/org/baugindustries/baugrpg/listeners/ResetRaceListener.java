package org.baugindustries.baugrpg.listeners;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import org.bukkit.ChatColor;

public class ResetRaceListener implements Listener {
	private Main plugin;
	public ResetRaceListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onuseItem(PlayerInteractEvent event) {
		if (!(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;
		
		if (!event.getPlayer().getInventory().getItemInMainHand().equals(plugin.itemManager.getResetRaceItem())) return;
		
		if (plugin.getRace(event.getPlayer()) == 0) {
			event.getPlayer().sendMessage(ChatColor.RED + "You must have a race to reset it.");
			return;
		}
		
		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + event.getPlayer().getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
		if (skillsconfig.contains("racereset")) {
			event.getPlayer().sendMessage(ChatColor.RED + "You cannot reset your race again.");
			return;
		}
		
		if (plugin.resetRaceEscape.contains(event.getPlayer().getUniqueId())) return;
		
		UUID leaderUUID = null;
		File leaderDataFile = new File(plugin.getDataFolder() + File.separator + "leaderData.yml");
		 
		 //Check to see if the file already exists. If not, create it.
		 if (!leaderDataFile.exists()) {
			 try {
				 leaderDataFile.createNewFile();
			 } catch (IOException e) {
				 e.printStackTrace();
			 }
		}
		FileConfiguration leaderConfig = YamlConfiguration.loadConfiguration(leaderDataFile);
		switch (plugin.getRace(event.getPlayer())) {
			case 1:
				if (leaderConfig.contains("menLeaderUUID")) {
					leaderUUID = UUID.fromString(leaderConfig.getString("menLeaderUUID"));
				}
				break;
			case 2:
				if (leaderConfig.contains("elfLeaderUUID")) {
					leaderUUID = UUID.fromString(leaderConfig.getString("elfLeaderUUID"));
				}
				break;
			case 3:
				File bankfile = new File(plugin.getDataFolder() + File.separator + "bank.yml");
				FileConfiguration bankConfig = YamlConfiguration.loadConfiguration(bankfile);
				
				File econfile = new File(plugin.getDataFolder() + File.separator + "econ.yml");
				FileConfiguration econConfig = YamlConfiguration.loadConfiguration(econfile);
				
				UUID emperorUUID = null;
				
				Set<String> banks = bankConfig.getKeys(false);
				for (String string : banks) {
					UUID uuid = UUID.fromString(string);

					File file = new File(plugin.getDataFolder() + File.separator + "inventoryData" + File.separator + string + ".yml");
					FileConfiguration config = YamlConfiguration.loadConfiguration(file);
					
					int playerRace = config.getInt("Race Data");
					if (Bukkit.getOfflinePlayer(uuid).isOnline()) {
						PersistentDataContainer data = Bukkit.getPlayer(uuid).getPersistentDataContainer();
						playerRace = data.get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER);
					}
					
					if (playerRace == 3) {
						if (emperorUUID == null) {
							emperorUUID = uuid;
						} else if (bankConfig.getInt(string) + econConfig.getInt(string) > bankConfig.getInt(emperorUUID.toString()) + econConfig.getInt(emperorUUID.toString())) {
							emperorUUID = uuid;
						}
					}
				}
				leaderUUID = emperorUUID;
				leaderConfig.set("dwarfLeaderUUID", leaderUUID.toString());
				
				try {
					leaderConfig.save(leaderDataFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				break;
			case 4:
				for (OfflinePlayer orc : plugin.getAllOfflineOrcs()) {
					if (leaderUUID == null) {
						leaderUUID = orc.getUniqueId();
					} else if (orc.getStatistic(Statistic.PLAYER_KILLS) > Bukkit.getOfflinePlayer(leaderUUID).getStatistic(Statistic.PLAYER_KILLS)) {
						leaderUUID = orc.getUniqueId();
					}
				}
				
				leaderConfig.set("orcLeaderUUID", leaderUUID.toString());
				
				try {
					leaderConfig.save(leaderDataFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				break;
		}
		
		if (leaderUUID.equals(event.getPlayer().getUniqueId())) {
			event.getPlayer().sendMessage(ChatColor.RED + "You cannot reset your race if your are the leader of your race.");
			return;
		}
		
		event.getPlayer().sendMessage(ChatColor.RED + "Are you sure you want to permanently reset your race and completely start over as a new being? If so please enter \"CONFIRM\" followed by your username. Enter \"EXIT\" to exit >");
		plugin.resetRaceEscape.add(event.getPlayer().getUniqueId());
	}

}
