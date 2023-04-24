package org.baugindustries.baugrpg.religion;

import java.io.File;

import org.baugindustries.baugrpg.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;


public class BuffMobsDamageListener implements Listener {
	private Main plugin;
	
	private final double defenseMultiplier = 2;
	private final double damageMultiplier = 1.5;
	
	public BuffMobsDamageListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void activatePortal(EntityDamageByEntityEvent event) {
		File religionInfoFile = new File(plugin.getDataFolder() + File.separator + "religionInfo.yml");
		FileConfiguration religionInfoConfig = YamlConfiguration.loadConfiguration(religionInfoFile);
		
		if (!religionInfoConfig.getBoolean("EnderDragonDefeated")) return;
		
		if (event.getEntity() instanceof Monster) {
			event.setDamage(event.getDamage() / defenseMultiplier);
		} else if ((event.getEntity() instanceof Player) && (event.getDamager() instanceof Monster)) {
			event.setDamage(event.getDamage() * damageMultiplier);
		}
	}

}
