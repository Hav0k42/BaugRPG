package org.baugindustries.baugrpg.listeners;

import java.io.File;

import org.baugindustries.baugrpg.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {
	private Main plugin;
	public PlayerRespawnListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void PlayerRespawnEvent(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		
		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
		
	 	float maxSpeed = 0.35f;
	 	
		if (skillsconfig.getBoolean("speedOn")) {
			player.setWalkSpeed((((maxSpeed - 0.2f) / 10f) * skillsconfig.getInt("speed"))+ 0.2f);
			player.setFlySpeed((((maxSpeed - 0.2f) / 10f) * skillsconfig.getInt("speed")) + 0.2f);
		} else {
			player.setWalkSpeed(0.2f);
			player.setFlySpeed(0.2f);
		}
		
		if (skillsconfig.getBoolean("regenOn")) {
			player.setSaturatedRegenRate(plugin.onJoinListener.getSaturationSlownessMultiplier() * (int)(((skillsconfig.getInt("regen") * -5f) / 9f) + (95f/9f)));
			player.setUnsaturatedRegenRate(plugin.onJoinListener.getSaturationSlownessMultiplier() * (int)(((skillsconfig.getInt("regen") * -40f) / 9f) + (760f/9f)));
		} else {
			player.setSaturatedRegenRate(plugin.onJoinListener.getSaturationSlownessMultiplier() * 10);
			player.setUnsaturatedRegenRate(plugin.onJoinListener.getSaturationSlownessMultiplier() * 80);
		}
		

		File claimsFile = new File(plugin.getDataFolder() + File.separator + "claims.yml");
		FileConfiguration claimsConfig = YamlConfiguration.loadConfiguration(claimsFile);
		int race = plugin.getRace(player);
		
		if (player.getBedSpawnLocation() == null) {
			switch (race) {
			case 1://Men
				event.setRespawnLocation(claimsConfig.getLocation("menSpawn"));
				break;
			case 2://Elves
				event.setRespawnLocation(claimsConfig.getLocation("elfSpawn"));
				break;
			case 3://Dwarves
				event.setRespawnLocation(claimsConfig.getLocation("dwarfSpawn"));
				break;
			case 4://Orcs
				event.setRespawnLocation(claimsConfig.getLocation("orcSpawn"));
				break;
			}
		}
	}
}
