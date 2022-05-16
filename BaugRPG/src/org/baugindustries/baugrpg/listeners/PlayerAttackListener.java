package org.baugindustries.baugrpg.listeners;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World.Environment;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_18_R2.CraftServer;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistry;
import net.minecraft.core.IRegistryWritable;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.chunk.Chunk;

public class PlayerAttackListener implements Listener {
	private Main plugin;
	
	List<String> menBiomes = Arrays.asList(
			"minecraft:plains",
			"minecraft:snowy_plains",
			"minecraft:snowy_beach",
			"minecraft:sunflower_plains",
			"minecraft:meadow",
			"terralith:arid_highlands",
			"terralith:ashen_savanna",
			"terralith:blooming_plateau",
			"terralith:blooming_valley",
			"terralith:highlands",
			"terralith:orchid_swamp",
			"terralith:steppe",
			"terralith:temperate_highlands",
			"terralith:valley_clearing"
			);
	List<String> elfBiomes = Arrays.asList(
			"minecraft:forest",
			"minecraft:birch_forest",
			"minecraft:dark_forest",
			"minecraft:flower_forest",
			"minecraft:old_growth_birch_forest",
			"minecraft:taiga",
			"minecraft:old_growth_pine_taiga",
			"minecraft:old_growth_spruce_taiga",
			"minecraft:snowy_taiga",
			"terralith:forested_highlands",
			"terralith:lavender_forest",
			"terralith:lavender_valley",
			"terralith:moonlight_grove",
			"terralith:moonlight_valley",
			"terralith:sakura_grove",
			"terralith:sakura_valley",
			"terralith:shield",
			"terralith:shield_clearing",
			"terralith:siberian_grove",
			"terralith:siberian_taiga",
			"terralith:snowy_maple_forest",
			"terralith:snowy_shield",
			"terralith:wintry_forest",
			"terralithLwintry_lowlands"
			);
	List<String> dwarfBiomes = Arrays.asList(
			"minecraft:grove",
			"minecraft:snowy_slopes",
			"minecraft:jagged_peaks",
			"minecraft:frozen_peaks",
			"minecraft:windswept_hills",
			"minecraft:windswept_gravelly_hills",
			"minecraft:stony_shore",
			"minecraft:stony_peaks",
			"terralith:alpine_grove",
			"terralith:alpine_highlands",
			"terralith:birch_taiga",
			"terralith:caldera",
			"terralith:cold_shrubland",
			"terralith:emerald_peaks",
			"terralith:granite_cliffs",
			"terralith:gravel_desert",
			"terralith:rocky_mountains",
			"terralith:scarlet_mountains",
			"terralith:stony_spires"
			);
	
	public PlayerAttackListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void PlayerAttackEvent(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			
			if (plugin.orcVictim != null && plugin.orcVictim.equals(player.getUniqueId())) {
				event.setCancelled(true);
				return;
			}
			
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
		 		String biomeName = getBiomeKey(player.getLocation()).toString();
			 	if (skillsconfig.contains("menBuffBiomeOn") && skillsconfig.getBoolean("menBuffBiomeOn")) {
			 		if (menBiomes.contains(biomeName)) {
			 			if (skillsconfig.contains("menBuffBiome")) {
			 				int lvl = skillsconfig.getInt("menBuffBiome");
				 			event.setDamage(event.getDamage() * (1 + (lvl / 8.0)));
			 			}
			 		}
			 	}
			 	
			 	
			 	if (skillsconfig.contains("elfBuffBiomeOn") && skillsconfig.getBoolean("elfBuffBiomeOn")) {
			 		if (elfBiomes.contains(biomeName)) {
			 			if (skillsconfig.contains("elfBuffBiome")) {
			 				int lvl = skillsconfig.getInt("elfBuffBiome");
				 			event.setDamage(event.getDamage() * (1 + (lvl / 8.0)));
			 			}
			 		}
			 	}
			 	
			 	if (skillsconfig.contains("dwarfBuffBiomeOn") && skillsconfig.getBoolean("dwarfBuffBiomeOn")) {
			 		if (dwarfBiomes.contains(biomeName) || player.getLocation().getY() < 32) {
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
	
	//Custom biome identification adapted from: https://www.spigotmc.org/threads/1-17-getting-custom-biomes-and-dimensions-by-namespace.513957/
	
	public MinecraftKey getBiomeKey(Location location) {
	    DedicatedServer dedicatedServer = ((CraftServer) Bukkit.getServer()).getServer();
	    
	    IRegistryWritable<BiomeBase> registry = (IRegistryWritable<BiomeBase>) dedicatedServer.aU().b(IRegistry.aP);
	    
	    return registry.b(getBiomeBase(location).a()); // getBiomeBase() from above
	}
	
	public Holder<BiomeBase> getBiomeBase(Location location) {
	    // NMS position
	    BlockPosition pos = new BlockPosition(location.getBlockX(), 0, location.getBlockZ());

	    // NMS chunk from pos
	    Chunk nmsChunk = ((CraftWorld)location.getWorld()).getHandle().l(pos);

	    if (nmsChunk != null) {
	        return nmsChunk.a().c(pos.u(), 0, pos.w());
	    }
	    return null;
	}
}
