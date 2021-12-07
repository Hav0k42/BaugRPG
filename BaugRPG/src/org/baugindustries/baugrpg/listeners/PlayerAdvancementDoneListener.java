package org.baugindustries.baugrpg.listeners;

import java.io.File;
import java.io.IOException;

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
	public void PlayerAdvancementDoneEvent(PlayerAdvancementDoneEvent event) {//TOTAL OF 91 AVAILABLE SKILL POINTS
		Player player = event.getPlayer();
		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
	 	
	 	skillsconfig.set("skillPoints", skillsconfig.getInt("skillPoints") + 1);
	 	String name = event.getAdvancement().getKey().toString();
	 	if (skillsconfig.getInt("skillPoints") > 4 && !(name.substring(0, 16).equals("minecraft:recipe"))) {
	 		player.sendMessage(name);
		 	player.sendMessage(ChatColor.GOLD + "Gained skill point. Run /bs to use them. Total points: " + ChatColor.DARK_PURPLE + skillsconfig.getInt("skillPoints"));
	 	}
	 	
	 	try {
			skillsconfig.save(skillsfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
