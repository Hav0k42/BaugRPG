package org.baugindustries.baugrpg.listeners;

import java.io.File;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class WitheredBeheadingListener implements Listener {
	
	private Main plugin;
	public WitheredBeheadingListener(Main plugin) {
		this.plugin = plugin;
	}
	@EventHandler
	public void OrcKillWither(EntityDeathEvent event) {
		Player killer = event.getEntity().getKiller();
		if (killer == null) return;
		UUID uuid = killer.getUniqueId();
		
		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + uuid + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
	 	
	 	if (!(skillsconfig.contains("EnragedBerserker2") && skillsconfig.getBoolean("EnragedBerserker2"))) return;
	 	
	 	event.getDrops().forEach(drop -> {
	 		if (drop.getType().equals(Material.WITHER_SKELETON_SKULL)) return;
	 	});
	 	
	 	double percentage = 0.2;
	 	if (Math.random() > percentage) return;
	 	killer.getWorld().dropItem(event.getEntity().getLocation(), plugin.createItem(Material.WITHER_SKELETON_SKULL, 1));
	}

}
