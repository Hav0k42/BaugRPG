package org.baugindustries.baugrpg.listeners;

import java.io.File;

import org.baugindustries.baugrpg.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRecipeDiscoverEvent;

public class DisableRecipeListener implements Listener {
	private Main plugin;
	public DisableRecipeListener(Main plugin) {
		this.plugin = plugin;
	}
	@EventHandler
	public void RevokeRecipe(PlayerRecipeDiscoverEvent event) {
		File file = new File(plugin.getDataFolder() + File.separator + "config.yml");
	 	FileConfiguration config = YamlConfiguration.loadConfiguration(file);
	 	if (!(config.contains("allowRecipe") && !config.getBoolean("allowRecipe"))) return;
		event.setCancelled(true);
	}
}
