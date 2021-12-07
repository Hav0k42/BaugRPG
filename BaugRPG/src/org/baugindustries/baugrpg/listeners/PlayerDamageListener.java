package org.baugindustries.baugrpg.listeners;

import java.io.File;

import org.baugindustries.baugrpg.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamageListener implements Listener {
	private Main plugin;
	public PlayerDamageListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void PlayerDamageEvent(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			
			Player player = (Player) event.getEntity();
			
			
			File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
		 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
			
		 	if (skillsconfig.getBoolean("resistanceOn")) {
			 	double damageDivider = ((0.5 / 10) * skillsconfig.getInt("resistance"));
				double initDamage = event.getDamage();
				event.setDamage(initDamage - (initDamage * damageDivider));
		 	}
			
		}
	}
}
