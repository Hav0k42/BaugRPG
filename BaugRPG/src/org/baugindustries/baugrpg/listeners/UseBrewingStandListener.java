package org.baugindustries.baugrpg.listeners;

import java.io.File;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import net.md_5.bungee.api.ChatColor;

public class UseBrewingStandListener implements Listener {
	private Main plugin;
	
	public UseBrewingStandListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(PlayerInteractEvent event) {
		if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		
		if (!event.getClickedBlock().getType().equals(Material.BREWING_STAND)) return;
		Player player = event.getPlayer();
		
		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
		
	 	String profession = skillsconfig.getString("class");
	 	
		if (!player.hasPermission("minecraft.command.op") && (profession == null || !profession.equals("Dark Alchemist"))) {
			player.sendMessage(ChatColor.RED + "Only Dark Alchemists may use this station.");
			event.setCancelled(true);
		}
	}

}
