package org.baugindustries.baugrpg.customitems.elves.woodland_craftsman.intermediate;

import java.io.File;
import java.io.IOException;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class ToolbeltListener implements Listener {
	private Main plugin;
	
	private int toolSlots = 9;
	
	FileConfiguration toolsConfig;
	public ToolbeltListener(Main plugin) {
		this.plugin = plugin;
		
		File toolsFile = new File(plugin.getDataFolder() + File.separator + "tools.yml");
		 
		 if (!toolsFile.exists()) {
			 try {
				 toolsFile.createNewFile();
			 } catch (IOException e) {
				 e.printStackTrace();
			 }
		 }
		 toolsConfig = YamlConfiguration.loadConfiguration(toolsFile);
		
	}
	
	
	@EventHandler
	public void onCycleTool(PlayerSwapHandItemsEvent event) {
		Player player = event.getPlayer();
		if (!Recipes.TOOLBELT.matches(plugin, player.getInventory().getLeggings())) return;
		
		event.setCancelled(true);
		
		ConfigurationSection tools = null;
		if (toolsConfig.contains(player.getUniqueId().toString())) {
			tools = toolsConfig.getConfigurationSection(player.getUniqueId().toString());
		} else {
			tools = toolsConfig.createSection(player.getUniqueId().toString());
		}
		
		int activeSlot;
		if (tools.contains("slot")) {
			activeSlot = tools.getInt("slot");
		} else {
			activeSlot = 1;
		}
		

		tools.set("tool" + activeSlot, player.getInventory().getItemInOffHand());
		
		activeSlot++;
		if (activeSlot > toolSlots) {
			activeSlot = 1;
		}
		
		if (tools.contains("tool" + activeSlot)) {
			player.getInventory().setItemInOffHand(tools.getItemStack("tool" + activeSlot));
		} else {
			player.getInventory().setItemInOffHand(null);
		}
		
		tools.set("slot", activeSlot);
		
		try {
			toolsConfig.save(new File(plugin.getDataFolder() + File.separator + "tools.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
