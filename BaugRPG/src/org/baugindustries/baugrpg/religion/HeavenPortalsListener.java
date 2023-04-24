package org.baugindustries.baugrpg.religion;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.listeners.LunarTransfusionListener;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.block.data.type.Slab;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;


public class HeavenPortalsListener implements Listener {
	private Main plugin;

	File religionInfoFile;
	FileConfiguration religionInfoConfig;
	
	int particleDensity = 50;
	
	public HeavenPortalsListener(Main plugin) {
		this.plugin = plugin;

		
		
		religionInfoFile = new File(plugin.getDataFolder() + File.separator + "religionInfo.yml");
		religionInfoConfig = YamlConfiguration.loadConfiguration(religionInfoFile);
		
		if (!religionInfoConfig.getBoolean("EnderDragonDefeated")) return;
		
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run() {
				religionInfoConfig = YamlConfiguration.loadConfiguration(religionInfoFile);
				ConfigurationSection heavensPortalSection;
				if (religionInfoConfig.contains("Heavens Portals")) {
					heavensPortalSection = religionInfoConfig.getConfigurationSection("Heavens Portals");
				} else {
					heavensPortalSection = religionInfoConfig.createSection("Heavens Portals");
				}
				
				for (String heavensPortalKey : heavensPortalSection.getKeys(false)) {
					ConfigurationSection portal = heavensPortalSection.getConfigurationSection(heavensPortalKey);
					Location loc = portal.getLocation("Location");
					
					if (loc.getChunk().isLoaded()) {
	 					
						loc.getWorld().playSound(loc, Sound.BLOCK_BEACON_AMBIENT, 1, 1);
						
						Color color = null;
						
						String portalType = portal.getString("Type");
						if (portalType.equals("Men")) {
							color = Color.fromRGB(85, 255, 255);
						} else if (portalType.equals("Elf")) {
							color = Color.fromRGB(0, 212, 0);
						} else if (portalType.equals("Dwarf")) {
							color = Color.fromRGB(255, 170, 0);
						} else if (portalType.equals("Orc")) {
							color = Color.fromRGB(170, 0, 0);
						}
						
						Particle particle = Particle.REDSTONE;
						Particle.DustOptions options = new Particle.DustOptions(color, 1);
						boolean northSouth = portal.getBoolean("NorthSouth");
	 					for (Player player : loc.getWorld().getPlayers()) {
	 						double dist = player.getLocation().distance(loc);
	 						if (dist < 120) {
	 							if (northSouth) {
	 								player.spawnParticle(particle, loc.getX() + 0.5, loc.getY() + 4, loc.getZ() + 0.5, particleDensity, 0.1, 2, 0.3, options);
	 								player.spawnParticle(particle, loc.getX() + 0.5, loc.getY() + 4, loc.getZ() + 0.5, particleDensity, 0.1, 0.7, 1, options);
	 							} else {
	 								player.spawnParticle(particle, loc.getX() + 0.5, loc.getY() + 4, loc.getZ() + 0.5, particleDensity, 0.3, 2, 0.1, options);
	 								player.spawnParticle(particle, loc.getX() + 0.5, loc.getY() + 4, loc.getZ() + 0.5, particleDensity, 1, 0.7, 0.1, options);
	 							}
	 						}
	 					}
            		    
					}
				}
			}
		}, 2L, 2L);
		
		
	}
	
	
	@EventHandler
	public void activatePortal(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!player.getInventory().getItemInMainHand().getType().equals(Material.FLINT_AND_STEEL)) return;
	    } else {
			if (!player.getInventory().getItemInOffHand().getType().equals(Material.FLINT_AND_STEEL)) return;
	    }
		
		if (player.getWorld().getName().contains("_baugreligions")) return;
		
		int gateType = isHeavenlyGate(player, event.getClickedBlock().getLocation());
		
		if (gateType > 0) {
			ConfigurationSection heavensPortalSection;
			if (religionInfoConfig.contains("Heavens Portals")) {
				heavensPortalSection = religionInfoConfig.getConfigurationSection("Heavens Portals");
			} else {
				heavensPortalSection = religionInfoConfig.createSection("Heavens Portals");
			}
			
			
			for (String heavensPortalKey : heavensPortalSection.getKeys(false)) {
				ConfigurationSection portal = heavensPortalSection.getConfigurationSection(heavensPortalKey);
				Location loc = portal.getLocation("Location");
				if (loc.equals(event.getClickedBlock().getLocation())) {
					return;
				}
			}
			
			
			
			
			
			ConfigurationSection newPortal = heavensPortalSection.createSection(UUID.randomUUID().toString());
			newPortal.set("Location", event.getClickedBlock().getLocation());
			String raceString = "Men";
			newPortal.set("NorthSouth", true);
			if (gateType > 4) {
				newPortal.set("NorthSouth", false);
				gateType -= 4;
			}
			switch (gateType) {
				case 2:
					raceString = "Elf";
					break;
				case 3:
					raceString = "Dwarf";
					break;
				case 4:
					raceString = "Orc";
					break;
			}
			newPortal.set("Type", raceString);
			
			try {
				religionInfoConfig.save(religionInfoFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			event.setCancelled(true);
		}
	}
	
	
	@EventHandler
	public void teleportPlayer(PlayerMoveEvent event) {

		Player player = event.getPlayer();
		if (event.getFrom().getBlockX() == event.getTo().getBlockX() && event.getFrom().getBlockZ() == event.getTo().getBlockZ()) return;
		
		Location loc = event.getTo().getBlock().getLocation();
		loc.setY(event.getTo().getBlockY() - 1);
		ConfigurationSection heavensPortalSection;
		if (religionInfoConfig.contains("Heavens Portals")) {
			heavensPortalSection = religionInfoConfig.getConfigurationSection("Heavens Portals");
		} else {
			heavensPortalSection = religionInfoConfig.createSection("Heavens Portals");
		}
		
		ConfigurationSection portal = null;
		
		boolean portalFound = false;
		for (String heavensPortalKey : heavensPortalSection.getKeys(false)) {
			loc.setY(event.getTo().getBlockY() - 1);
			portal = heavensPortalSection.getConfigurationSection(heavensPortalKey);
			if (portal.getLocation("Location").equals(loc)) {
				portalFound = true;
				break;
			} else {
				loc.setY(loc.getY() - 1);
				if (portal.getLocation("Location").equals(loc)) {
					portalFound = true;
					break;
				} else {
					loc.setY(loc.getY() - 1);
					if (portal.getLocation("Location").equals(loc)) {
						portalFound = true;
						break;
					} else {
						loc.setY(loc.getY() - 1);
						if (portal.getLocation("Location").equals(loc)) {
							portalFound = true;
							break;
						}
					}
				}
			}
		}
		
		if (!portalFound) return;
		
		
		
		loc = portal.getLocation("Location");
		
		
		
		int gateType = isHeavenlyGate(player, loc);
		if (gateType > 4) {
			gateType -= 4;
		}
		
		
		if (gateType != 0) {
			World world = null;
			
			if (plugin.getRace(player) != gateType) return;
			
			switch (gateType) {
				case 1:
					world = plugin.getServer().getWorld(plugin.getServer().getWorlds().get(0).getName() + "_baugreligions_aeriesqa");
					break;
				case 2:
					world = plugin.getServer().getWorld(plugin.getServer().getWorlds().get(0).getName() + "_baugreligions_taevas");
					break;
				case 3:
					world = plugin.getServer().getWorld(plugin.getServer().getWorlds().get(0).getName() + "_baugreligions_velaruhm");
					break;
				case 4:
					world = plugin.getServer().getWorld(plugin.getServer().getWorlds().get(0).getName() + "_baugreligions_ruzkal");
					break;
			}
			Location spawnLoc = world.getSpawnLocation();
			
			player.teleport(spawnLoc.add(0, 1, 0));
			for (int x = -2; x < 3; x++) {
				for (int z = -2; z < 3; z++) {
					world.getBlockAt(world.getSpawnLocation().add(x, -1, z)).setType(Material.OBSIDIAN);
					world.getBlockAt(world.getSpawnLocation().add(x, 0, z)).setType(Material.AIR);
					world.getBlockAt(world.getSpawnLocation().add(x, 1, z)).setType(Material.AIR);
					world.getBlockAt(world.getSpawnLocation().add(x, 21, z)).setType(Material.AIR);
				}
			}
			
			player.getWorld().playSound(loc, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
		} else {
			player.sendMessage(ChatColor.RED + "Heaven's Gate compromised.");
			heavensPortalSection.set(portal.getName(), null);
			
			try {
				religionInfoConfig.save(religionInfoFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	private int isHeavenlyGate(Player player, Location center) {
		Material solidMat;
		Material stairMat;
		Material slabMat;
		int returnInt = 0;
		if (LunarTransfusionListener.checkBlockInit(center, 0, 0, 0, Material.DIAMOND_BLOCK)) {
			solidMat = Material.STRIPPED_SPRUCE_WOOD;
			stairMat = Material.SPRUCE_STAIRS;
			slabMat = Material.SPRUCE_SLAB;
			returnInt = 1;
		} else if (LunarTransfusionListener.checkBlockInit(center, 0, 0, 0, Material.EMERALD_BLOCK)) {
			solidMat = Material.CHISELED_QUARTZ_BLOCK;
			stairMat = Material.QUARTZ_STAIRS;
			slabMat = Material.QUARTZ_SLAB;
			returnInt = 2;
		} else if (LunarTransfusionListener.checkBlockInit(center, 0, 0, 0, Material.GOLD_BLOCK)) {
			solidMat = Material.CHISELED_DEEPSLATE;
			stairMat = Material.DEEPSLATE_TILE_STAIRS;
			slabMat = Material.DEEPSLATE_TILE_SLAB;
			returnInt = 3;
		} else if (LunarTransfusionListener.checkBlockInit(center, 0, 0, 0, Material.NETHERITE_BLOCK)) {
			solidMat = Material.CHISELED_NETHER_BRICKS;
			stairMat = Material.RED_NETHER_BRICK_STAIRS;
			slabMat = Material.RED_NETHER_BRICK_SLAB;
			returnInt = 4;
		} else {
			return 0;
		}
		
		
		if (LunarTransfusionListener.checkBlockInit(center, 0, 1, 2, solidMat)) {
			//NORTH/SOUTH

			//y = 1
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 1, 3, stairMat, BlockFace.NORTH, Half.TOP)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 1, 2, solidMat)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 1, 1, stairMat, BlockFace.SOUTH, Half.BOTTOM)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 1, -1, stairMat, BlockFace.NORTH, Half.BOTTOM)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 1, -2, solidMat)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 1, -3, stairMat, BlockFace.SOUTH, Half.TOP)) return 0;

			//y = 2
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 2, 3, solidMat)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 2, 2, solidMat)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 2, -2, solidMat)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 2, -3, solidMat)) return 0;
			
			//y = 3
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 3, 3, solidMat)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 3, 2, stairMat, BlockFace.SOUTH, Half.BOTTOM)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 3, -2, stairMat, BlockFace.NORTH, Half.BOTTOM)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 3, -3, solidMat)) return 0;
			
			//y = 4
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 4, 3, solidMat)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 4, 2, stairMat, BlockFace.SOUTH, Half.TOP)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 4, -2, stairMat, BlockFace.NORTH, Half.TOP)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 4, -3, solidMat)) return 0;
			
			//y = 5
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 5, 3, stairMat, BlockFace.NORTH, Half.BOTTOM)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 5, 2, solidMat)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 5, -2, solidMat)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 5, -3, stairMat, BlockFace.SOUTH, Half.BOTTOM)) return 0;
			
			//y = 6
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 6, 2, solidMat)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 6, 1, stairMat, BlockFace.SOUTH, Half.TOP)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 6, -1, stairMat, BlockFace.NORTH, Half.TOP)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 6, -2, solidMat)) return 0;
			
			//y = 7
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 7, 2, slabMat, Slab.Type.BOTTOM)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 7, 1, solidMat)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 7, -1, solidMat)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 7, -2, slabMat, Slab.Type.BOTTOM)) return 0;
			
			//y = 8
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 8, 1, stairMat, BlockFace.NORTH, Half.BOTTOM)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 8, -1, stairMat, BlockFace.SOUTH, Half.BOTTOM)) return 0;
			
			
			
			return returnInt;
		} else {
			
			//EAST/WEST

			//y = 1
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 3, 1, 0, stairMat, BlockFace.WEST, Half.TOP)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 2, 1, 0, solidMat)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 1, 1, 0, stairMat, BlockFace.EAST, Half.BOTTOM)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -1, 1, 0, stairMat, BlockFace.WEST, Half.BOTTOM)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -2, 1, 0, solidMat)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -3, 1, 0, stairMat, BlockFace.EAST, Half.TOP)) return 0;

			//y = 2
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 3, 2, 0, solidMat)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 2, 2, 0, solidMat)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -2, 2, 0, solidMat)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -3, 2, 0, solidMat)) return 0;
			
			//y = 3
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 3, 3, 0, solidMat)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 2, 3, 0, stairMat, BlockFace.EAST, Half.BOTTOM)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -2, 3, 0, stairMat, BlockFace.WEST, Half.BOTTOM)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -3, 3, 0, solidMat)) return 0;
			
			//y = 4
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 3, 4, 0, solidMat)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 2, 4, 0, stairMat, BlockFace.EAST, Half.TOP)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -2, 4, 0, stairMat, BlockFace.WEST, Half.TOP)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -3, 4, 0, solidMat)) return 0;
			
			//y = 5
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 3, 5, 0, stairMat, BlockFace.WEST, Half.BOTTOM)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 2, 5, 0, solidMat)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -2, 5, 0, solidMat)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -3, 5, 0, stairMat, BlockFace.EAST, Half.BOTTOM)) return 0;
			
			//y = 6
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 2, 6, 0, solidMat)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 1, 6, 0, stairMat, BlockFace.EAST, Half.TOP)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -1, 6, 0, stairMat, BlockFace.WEST, Half.TOP)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -2, 6, 0, solidMat)) return 0;
			
			//y = 7
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 2, 7, 0, slabMat, Slab.Type.BOTTOM)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 1, 7, 0, solidMat)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -1, 7, 0, solidMat)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -2, 7, 0, slabMat, Slab.Type.BOTTOM)) return 0;
			
			//y = 8
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 1, 8, 0, stairMat, BlockFace.WEST, Half.BOTTOM)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -1, 8, 0, stairMat, BlockFace.EAST, Half.BOTTOM)) return 0;
			
			
			
			return returnInt + 4;
		}
		
	}

}
