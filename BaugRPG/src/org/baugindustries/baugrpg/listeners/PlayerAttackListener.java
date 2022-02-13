package org.baugindustries.baugrpg.listeners;

import java.io.File;

import org.baugindustries.baugrpg.Main;
import org.bukkit.World.Environment;
import org.bukkit.block.Biome;
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
		 	
		 	
		 	if (event.getEntity() instanceof Player) {
		 		Biome biome = player.getLocation().getBlock().getBiome();
		 		
			 	if (skillsconfig.contains("menBuffBiomeOn") && skillsconfig.getBoolean("menBuffBiomeOn")) {
			 		if (biome.equals(Biome.PLAINS) || biome.equals(Biome.SNOWY_PLAINS) || biome.equals(Biome.SNOWY_BEACH) || biome.equals(Biome.PLAINS) || biome.equals(Biome.SUNFLOWER_PLAINS) || biome.equals(Biome.MEADOW)) {
			 			if (skillsconfig.contains("menBuffBiome")) {
			 				int lvl = skillsconfig.getInt("menBuffBiome");
				 			event.setDamage(event.getDamage() * (1 + (lvl / 8.0)));
			 			}
			 		}
			 	}
			 	
			 	
			 	if (skillsconfig.contains("elfBuffBiomeOn") && skillsconfig.getBoolean("elfBuffBiomeOn")) {
			 		if (biome.equals(Biome.FOREST) || biome.equals(Biome.BIRCH_FOREST) || biome.equals(Biome.DARK_FOREST) || biome.equals(Biome.FLOWER_FOREST) || biome.equals(Biome.OLD_GROWTH_BIRCH_FOREST) ||
			 				biome.equals(Biome.TAIGA) || biome.equals(Biome.OLD_GROWTH_PINE_TAIGA) || biome.equals(Biome.OLD_GROWTH_SPRUCE_TAIGA)|| biome.equals(Biome.SNOWY_TAIGA)) {
			 			if (skillsconfig.contains("elfBuffBiome")) {
			 				int lvl = skillsconfig.getInt("elfBuffBiome");
				 			event.setDamage(event.getDamage() * (1 + (lvl / 8.0)));
			 			}
			 		}
			 	}
			 	
			 	if (skillsconfig.contains("dwarfBuffBiomeOn") && skillsconfig.getBoolean("dwarfBuffBiomeOn")) {
			 		if (biome.equals(Biome.GROVE) || biome.equals(Biome.SNOWY_SLOPES) || biome.equals(Biome.JAGGED_PEAKS) || biome.equals(Biome.FROZEN_PEAKS) || biome.equals(Biome.WINDSWEPT_HILLS) ||
			 				biome.equals(Biome.WINDSWEPT_GRAVELLY_HILLS) || biome.equals(Biome.STONY_SHORE) || biome.equals(Biome.STONY_PEAKS) || player.getLocation().getY() < 32) {
			 			if (skillsconfig.contains("dwarfBuffBiome")) {
			 				int lvl = skillsconfig.getInt("dwarfBuffBiome");
				 			event.setDamage(event.getDamage() * (1 + (lvl / 8.0)));
			 			}
			 		}
			 	}
			 	
			 	if (skillsconfig.contains("orcBuffBiomeOn") && skillsconfig.getBoolean("orcBuffBiomeOn")) {
			 		if (player.getLocation().getWorld().getEnvironment().equals(Environment.NETHER)) {
			 			if (skillsconfig.contains("orcBuffBiome")) {
			 				int lvl = skillsconfig.getInt("orcBuffBiome");
				 			event.setDamage(event.getDamage() * (1 + (lvl / 8.0)));
			 			}
			 		}
			 	}
		 	}
		}
	}
}
