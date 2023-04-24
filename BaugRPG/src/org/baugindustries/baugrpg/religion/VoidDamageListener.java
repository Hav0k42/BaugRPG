package org.baugindustries.baugrpg.religion;

import java.io.File;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.util.Vector;


public class VoidDamageListener implements Listener {
	private Main plugin;
	
	public VoidDamageListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void activatePortal(EntityDamageEvent event) {
		if (!(event.getEntity() instanceof Player)) return;
		Player player = (Player) event.getEntity();
		if (event.getCause().equals(DamageCause.VOID) && player.getWorld().getName().contains(plugin.getServer().getWorlds().get(0).getName() + "_baugreligions_")) {
			event.setCancelled(true);
			player.setVelocity(new Vector(0, 0.1, 0));
			Location tpLoc;
			if (player.getBedSpawnLocation() == null) {
				int race = plugin.getRace(player);

			 	File claimsFile = new File(plugin.getDataFolder() + File.separator + "claims.yml");
				FileConfiguration claimsConfig = YamlConfiguration.loadConfiguration(claimsFile);
				if (race == 1) {
					tpLoc = claimsConfig.getLocation("menSpawn");
				} else if (race == 2) {
					tpLoc = claimsConfig.getLocation("elfSpawn");
				} else if (race == 3) {
					tpLoc = claimsConfig.getLocation("dwarfSpawn");
				} else {
					tpLoc = claimsConfig.getLocation("orcSpawn");
				}
			} else {
				tpLoc = player.getBedSpawnLocation();
			}
			
			tpLoc.add(0, 0.2, 0);
			player.teleport(tpLoc);
			player.setVelocity(new Vector(0, 0.1, 0));
		}
	}

}
