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
	 	String name = event.getAdvancement().getKey().toString();
		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
	 	if (!(name.substring(0, 16).equals("minecraft:recipe"))) {
		 	skillsconfig.set("skillPoints", skillsconfig.getInt("skillPoints") + 1);
		 	skillsconfig.set("totalSkillPoints", skillsconfig.getInt("totalSkillPoints") + 1);
		 	
		 	if (skillsconfig.getInt("totalSkillPoints") > 5) {
			 	player.sendMessage(ChatColor.GOLD + "Gained skill point. Run /bs to use them. Total points: " + ChatColor.DARK_PURPLE + skillsconfig.getInt("skillPoints"));
		 	} else if (skillsconfig.getInt("totalSkillPoints") == 5) {
		 		player.sendMessage(ChatColor.GOLD + "You have unlocked the ability to use skill points.\n These are used to upgrade various skills, such as how fast you run or how high you jump. \nYou can access the skill tree by running: " + ChatColor.DARK_PURPLE + "/baugscroll");
		 	}
		 	if (skillsconfig.getInt("totalSkillPoints") == 20) {
		 		player.sendMessage(ChatColor.GOLD + "You can now spend your skill points on more specialized, race specific skills.\n Choose your class in the skill tree using: " + ChatColor.DARK_PURPLE + "/baugscroll");
		 	}
		 	
		 	try {
				skillsconfig.save(skillsfile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 	}
	}
}
