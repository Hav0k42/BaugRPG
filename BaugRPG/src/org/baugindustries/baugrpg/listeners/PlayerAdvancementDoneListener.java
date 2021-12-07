package org.baugindustries.baugrpg.listeners;

import java.io.File;

import org.baugindustries.baugrpg.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import net.md_5.bungee.api.ChatColor;

public class PlayerAdvancementDoneListener implements Listener {
	private Main plugin;
	public PlayerAdvancementDoneListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void PlayerAdvancementDoneEvent(PlayerAdvancementDoneEvent event) {
		Player player = event.getPlayer();
		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
	 	
	 	skillsconfig.set("skillPoints", skillsconfig.getInt("skillPoints") + 1);
	 	if (skillsconfig.getInt("skillPoints") > 4) {
		 	player.sendMessage(ChatColor.GOLD + "Gained skill point. Run /bs to use them. Total points: " + ChatColor.DARK_PURPLE + skillsconfig.getInt("skillPoints"));
	 	}
	}
}
