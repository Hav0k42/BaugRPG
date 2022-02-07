package org.baugindustries.baugrpg.listeners;

import java.io.File;

import org.baugindustries.baugrpg.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class EndermanGriefingListener implements Listener {
	private Main plugin;
	public EndermanGriefingListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void EndermanGrief(EntityChangeBlockEvent event) {
		if (!event.getEntityType().equals(EntityType.ENDERMAN)) return;

		File file = new File(plugin.getDataFolder() + File.separator + "config.yml");
	 	FileConfiguration config = YamlConfiguration.loadConfiguration(file);
	 	
		if (!(config.contains("allowEndermanGriefing") && !config.getBoolean("allowEndermanGriefing"))) return;
		event.setCancelled(true);
	}
}
