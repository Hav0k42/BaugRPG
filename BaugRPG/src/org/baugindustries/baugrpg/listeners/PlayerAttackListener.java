package org.baugindustries.baugrpg.listeners;

import java.io.File;

import org.baugindustries.baugrpg.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerAttackListener implements Listener {
	private Main plugin;
	public PlayerAttackListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void PlayerAttackEvent(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			
			File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
		 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
			
		 	double damageMultiplier = 1;
		 	double initDamage = event.getDamage();
		 	if (skillsconfig.getBoolean("damageOn")) {
			 	damageMultiplier = (1 + ((0.5 / 10) * skillsconfig.getInt("damage")));
		 	}
		 	
		 	if ((skillsconfig.contains("StableMaster2") && skillsconfig.getBoolean("StableMaster2")) && plugin.mountedPlayers.contains(player)) {
		 		damageMultiplier *= 1.5;
		 	}
		 	event.setDamage(initDamage * damageMultiplier);
		}
	}
}
