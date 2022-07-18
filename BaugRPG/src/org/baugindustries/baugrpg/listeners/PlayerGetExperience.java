package org.baugindustries.baugrpg.listeners;

import java.io.File;
import java.io.IOException;

import org.baugindustries.baugrpg.Main;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class PlayerGetExperience implements Listener {

	
	private Main plugin;
	private final int levelThreshold = 30;
	public PlayerGetExperience(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void GivePlayerClaimBlocks(PlayerExpChangeEvent event) {
		Player player = event.getPlayer();
		int race = plugin.getRace(player);
		if (!(race == 1 || race == 3)) return;
		
		if (event.getAmount() + (player.getExp() * player.getExpToLevel()) >= player.getExpToLevel() && player.getLevel() >= levelThreshold) {
			setPlayerClaimBlocks(player, getPlayerClaimBlocks(player) + 4);
		}
	}

	
	private int getPlayerClaimBlocks(Player player) {
		File leaderDataFile = new File(plugin.getDataFolder() + File.separator + "leaderData.yml");
		FileConfiguration leaderConfig = YamlConfiguration.loadConfiguration(leaderDataFile);
		
		ConfigurationSection claimBlocks;
		if (leaderConfig.contains("claimBlocks")) {
			claimBlocks = leaderConfig.getConfigurationSection("claimBlocks");
		} else {
			claimBlocks = leaderConfig.createSection("claimBlocks");
			try {
				leaderConfig.save(leaderDataFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String uuid = player.getUniqueId().toString();
		
		if (claimBlocks.contains(uuid)) {
			return claimBlocks.getInt(uuid);
		} else {
			claimBlocks.set(uuid, plugin.startingClaimBlocks);
			try {
				leaderConfig.save(leaderDataFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return plugin.startingClaimBlocks;
		}
	}
	
	private void setPlayerClaimBlocks(Player player, int blocks) {
		File leaderDataFile = new File(plugin.getDataFolder() + File.separator + "leaderData.yml");
		FileConfiguration leaderConfig = YamlConfiguration.loadConfiguration(leaderDataFile);
		
		ConfigurationSection claimBlocks;
		if (leaderConfig.contains("claimBlocks")) {
			claimBlocks = leaderConfig.getConfigurationSection("claimBlocks");
		} else {
			claimBlocks = leaderConfig.createSection("claimBlocks");
			try {
				leaderConfig.save(leaderDataFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		claimBlocks.set(player.getUniqueId().toString(), blocks);
		try {
			leaderConfig.save(leaderDataFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
