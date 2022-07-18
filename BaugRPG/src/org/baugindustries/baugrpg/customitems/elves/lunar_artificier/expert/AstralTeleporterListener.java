package org.baugindustries.baugrpg.customitems.elves.lunar_artificier.expert;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.baugindustries.baugrpg.listeners.LunarTransfusionListener;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;


public class AstralTeleporterListener implements Listener {
	private Main plugin;
	
	HashMap<UUID, List<Location>> selectedLocations = new HashMap<UUID, List<Location>>();
	HashMap<UUID, Long> lastTeleported = new HashMap<UUID, Long>();
	FileConfiguration astralPadconfig;
	File astralPadfile;
	
	int particleDensity = 20;
	
	public AstralTeleporterListener(Main plugin) {
		this.plugin = plugin;
		
		astralPadfile = new File(plugin.getDataFolder() + File.separator + "astralPadLocations.yml");
		
		if (!astralPadfile.exists()) {
			try {
				astralPadfile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		

		astralPadconfig = YamlConfiguration.loadConfiguration(astralPadfile);
		
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run() {
				for (String astralPadKey : astralPadconfig.getKeys(false)) {
					Location loc2 = astralPadconfig.getLocation(astralPadKey);
					String otherAstralPadKey = loc2.getBlockX() + "" + loc2.getBlockY() + "" + loc2.getBlockZ() + "" + loc2.getWorld().getUID().toString();
					Location loc1 = astralPadconfig.getLocation(otherAstralPadKey);
					
					if (loc1.getChunk().isLoaded()) {
	 					
						loc1.getWorld().playSound(loc1, Sound.BLOCK_BEACON_AMBIENT, 1, 1);
						
						Color color;
						if (astralPadKey.hashCode() < otherAstralPadKey.hashCode()) {
							color = Color.fromRGB(93, 212, 255);
						} else {
							color = Color.fromRGB(255, 153, 93);
						}
						
						Particle particle = Particle.REDSTONE;
						Particle.DustOptions options = new Particle.DustOptions(color, 1);
						
	 					for (Player player : loc1.getWorld().getPlayers()) {
	 						double dist = player.getLocation().distance(loc1);
	 						if (dist < 120) {
	 							Location subtracted = player.getLocation().subtract(loc1.getX() + 0.5, 0, loc1.getZ() + 0.5);
	 							double rotationAngle = Math.atan2(subtracted.getZ(), subtracted.getX()) + (Math.PI / 2);
	 							for (double i = -1; i < 2; i += 2.0 / particleDensity) {
	 								
	 								if (i < -1 + (2 * (2.0 / particleDensity)) || i > 1 - (2 * (2.0 / particleDensity))) {
	 									i -= 1.8 / particleDensity;
	 								}
	 								
	 								if (i > 1) {
	 									break;
	 								}
	 								
	 								double x = i;
	 								double y = loc1.getY() + 3.2 + Math.sqrt(2 - (2 * i * i));
	 								double z = 0;
	 								
	 								double xRotated = (x * Math.cos(rotationAngle)) - (z * Math.sin(rotationAngle));
	 								double zRotated = (z * Math.cos(rotationAngle)) + (x * Math.sin(rotationAngle));
	 								
	 								
	 								Location particleLoc = new Location(loc1.getWorld(), xRotated + loc1.getX() + 0.5, y, zRotated + loc1.getZ() + 0.5);
	 								player.spawnParticle(particle, particleLoc, 1, options);
	 							}
	 							
	 							for (double i = -1; i < 2; i += 2.0 / particleDensity) {
	 								
	 								if (i < -1 + (2 * (2.0 / particleDensity)) || i > 1 - (2 * (2.0 / particleDensity))) {
	 									i -= 1.8 / particleDensity;
	 								}
	 								
	 								if (i > 1) {
	 									break;
	 								}
	 								
	 								double x = i;
	 								double y = loc1.getY() + 3.2 - Math.sqrt(2 - (2 * i * i));
	 								double z = 0;
	 								
	 								double xRotated = (x * Math.cos(rotationAngle)) - (z * Math.sin(rotationAngle));
	 								double zRotated = (z * Math.cos(rotationAngle)) + (x * Math.sin(rotationAngle));
	 								
	 								
	 								Location particleLoc = new Location(loc1.getWorld(), xRotated + loc1.getX() + 0.5, y, zRotated + loc1.getZ() + 0.5);
	 								player.spawnParticle(particle, particleLoc, 1, options);
	 							}
	 						}
	 					}
            		    
					}
				}
			}
		}, 2L, 2L);
		
		
	}
	
	
	@EventHandler
	public void onInventoryClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if (event.getHand() == null) return;
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!Recipes.ASTRAL_WRENCH.matches(plugin, player.getInventory().getItemInMainHand())) return;
		} else {
			if (!Recipes.ASTRAL_WRENCH.matches(plugin, player.getInventory().getItemInOffHand())) return;
		}
		
		
		if (player.isSneaking() && (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
			player.sendMessage(ChatColor.YELLOW + "Astral teleporter pads allow you to teleport around the world. You may link two pads together by selecting them with the Astral Wrench. Left click to select the first one, and right click to select the second. For more info visit: https://github.com/Hav0k42/BaugRPG/wiki/Astral-Wrench");
			return;
		}
		

		if (event.getClickedBlock() == null) return;
		
		event.setCancelled(true);
		
		int isAstralPadInt = isAstralPad(player, event.getClickedBlock().getLocation());
		
		if (isAstralPadInt == -1) {
			player.sendMessage(ChatColor.RED + "Astral pad not found. (Sea Lantern)");
			return;
		} else if (isAstralPadInt == 0) {
			return;
		}
		
		Location loc = event.getClickedBlock().getLocation();
		if (astralPadconfig.contains(loc.getBlockX() + "" + loc.getBlockY() + "" + loc.getBlockZ() + "" + loc.getWorld().getUID().toString())) {
			player.sendMessage(ChatColor.RED + "This astral pad is already linked.");
			return;
		}
		
		if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
			if (selectedLocations.containsKey(player.getUniqueId())) {
				if (selectedLocations.get(player.getUniqueId()).get(1) != null) {
					if (!selectedLocations.get(player.getUniqueId()).get(1).equals(event.getClickedBlock().getLocation())) {
						player.sendMessage(ChatColor.GREEN + "Astral pads linked!");
						
						List<Location> locs = selectedLocations.get(player.getUniqueId());
						locs.set(0, event.getClickedBlock().getLocation());
						astralPadconfig.set(locs.get(1).getBlockX() + "" + locs.get(1).getBlockY() + "" + locs.get(1).getBlockZ() + "" + locs.get(1).getWorld().getUID().toString(), locs.get(0));
						astralPadconfig.set(locs.get(0).getBlockX() + "" + locs.get(0).getBlockY() + "" + locs.get(0).getBlockZ() + "" + locs.get(0).getWorld().getUID().toString(), locs.get(1));
						
						selectedLocations.remove(player.getUniqueId());
						
						try {
							astralPadconfig.save(astralPadfile);
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						player.sendMessage(ChatColor.RED + "You cannot link an astral pad to itself.");
					}
				} else {
					player.sendMessage(ChatColor.GREEN + "Astral pad one changed.");
					List<Location> locs = selectedLocations.get(player.getUniqueId());
					locs.set(0, event.getClickedBlock().getLocation());
					selectedLocations.put(player.getUniqueId(), locs);
				}
			} else {
				player.sendMessage(ChatColor.GREEN + "Astral pad one selected.");
				List<Location> locs = Arrays.asList(event.getClickedBlock().getLocation(), null);
				selectedLocations.put(player.getUniqueId(), locs);
			}
		} else if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (selectedLocations.containsKey(player.getUniqueId())) {
				if (selectedLocations.get(player.getUniqueId()).get(0) != null) {
					if (!selectedLocations.get(player.getUniqueId()).get(0).equals(event.getClickedBlock().getLocation())) {
						player.sendMessage(ChatColor.GREEN + "Astral pads linked!");
						
						List<Location> locs = selectedLocations.get(player.getUniqueId());
						locs.set(1, event.getClickedBlock().getLocation());
						astralPadconfig.set(locs.get(1).getBlockX() + "" + locs.get(1).getBlockY() + "" + locs.get(1).getBlockZ() + "" + locs.get(1).getWorld().getUID().toString(), locs.get(0));
						astralPadconfig.set(locs.get(0).getBlockX() + "" + locs.get(0).getBlockY() + "" + locs.get(0).getBlockZ() + "" + locs.get(0).getWorld().getUID().toString(), locs.get(1));
						
						selectedLocations.remove(player.getUniqueId());
						
						try {
							astralPadconfig.save(astralPadfile);
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						player.sendMessage(ChatColor.RED + "You cannot link an astral pad to itself.");
					}
				} else {
					player.sendMessage(ChatColor.GREEN + "Astral pad two changed.");
					List<Location> locs = selectedLocations.get(player.getUniqueId());
					locs.set(1, event.getClickedBlock().getLocation());
					selectedLocations.put(player.getUniqueId(), locs);
				}
			} else {
				player.sendMessage(ChatColor.GREEN + "Astral pad two selected.");
				List<Location> locs = Arrays.asList(null, event.getClickedBlock().getLocation());
				selectedLocations.put(player.getUniqueId(), locs);
			}
		}
		
	}
	
	
	@EventHandler
	public void teleportPlayer(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (event.getFrom().getBlockX() == event.getTo().getBlockX() && event.getFrom().getBlockZ() == event.getTo().getBlockZ()) return;
		
		if (lastTeleported.containsKey(player.getUniqueId()) && lastTeleported.get(player.getUniqueId()) + 600 > System.currentTimeMillis()) return;//300 is 12 ticks
		
		Location loc1 = event.getTo().getBlock().getLocation();
		loc1.setY(event.getTo().getBlockY() - 1);
		String astralPadConfigStr1 = loc1.getBlockX() + "" + loc1.getBlockY() + "" + loc1.getBlockZ() + "" + loc1.getWorld().getUID().toString();
		if (!astralPadconfig.contains(astralPadConfigStr1)) {
			
			loc1.setY(event.getTo().getBlockY() - 2);
			astralPadConfigStr1 = loc1.getBlockX() + "" + loc1.getBlockY() + "" + loc1.getBlockZ() + "" + loc1.getWorld().getUID().toString();
			if (!astralPadconfig.contains(astralPadConfigStr1)) {
				
				loc1.setY(event.getTo().getBlockY() - 3);
				astralPadConfigStr1 = loc1.getBlockX() + "" + loc1.getBlockY() + "" + loc1.getBlockZ() + "" + loc1.getWorld().getUID().toString();
				if (!astralPadconfig.contains(astralPadConfigStr1)) {
					return;
				}
				
			}
		}
		
		Location loc2 = astralPadconfig.getLocation(astralPadConfigStr1);
		String astralPadConfigStr2 = loc2.getBlockX() + "" + loc2.getBlockY() + "" + loc2.getBlockZ() + "" + loc2.getWorld().getUID().toString();
		
		
		
		if (isAstralPad(player, loc1) == 1 && isAstralPad(player, loc2) == 1) {
			Location newLoc = new Location(loc2.getWorld(), loc2.getX() - 0.5, loc2.getY(), loc2.getZ() - 0.5, player.getLocation().getYaw(), player.getLocation().getPitch());
			Location offset = player.getLocation().add(0.5, 0, 0.5).subtract(loc1);
			Vector vel = player.getVelocity();
			player.teleport(newLoc.add(offset));
			player.setVelocity(vel);
			
			lastTeleported.put(player.getUniqueId(), System.currentTimeMillis());
			
			player.getWorld().playSound(loc1, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
			player.getWorld().playSound(loc2, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
		} else {
			player.sendMessage(ChatColor.RED + "Astral pads compromised, disabling teleportation.");
			astralPadconfig.set(astralPadConfigStr1, null);
			astralPadconfig.set(astralPadConfigStr2, null);
			
			try {
				astralPadconfig.save(astralPadfile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	private int isAstralPad(Player player, Location center) {
		if (LunarTransfusionListener.checkBlockInit(center, 0, 0, 0, Material.SEA_LANTERN)) {
			
			//y = 0
			
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -1, 0, -1, Material.CALCITE)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -1, 0, 0, Material.CALCITE)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -1, 0, 1, Material.CALCITE)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 0, -1, Material.CALCITE)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 0, 1, Material.CALCITE)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 1, 0, -1, Material.CALCITE)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 1, 0, 0, Material.CALCITE)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 1, 0, 1, Material.CALCITE)) return 0;
			
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 2, 0, -1, Material.COAL_BLOCK)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 2, 0, 0, Material.COAL_BLOCK)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 2, 0, 1, Material.COAL_BLOCK)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -2, 0, -1, Material.COAL_BLOCK)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -2, 0, 0, Material.COAL_BLOCK)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -2, 0, 1, Material.COAL_BLOCK)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -1, 0, 2, Material.COAL_BLOCK)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 0, 2, Material.COAL_BLOCK)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 1, 0, 2, Material.COAL_BLOCK)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -1, 0, -2, Material.COAL_BLOCK)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 0, -2, Material.COAL_BLOCK)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 1, 0, -2, Material.COAL_BLOCK)) return 0;
			
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 2, 0, 2, Material.QUARTZ_PILLAR)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 2, 0, -2, Material.QUARTZ_PILLAR)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -2, 0, 2, Material.QUARTZ_PILLAR)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -2, 0, -2, Material.QUARTZ_PILLAR)) return 0;
			
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 3, 0, -1, Material.SMOOTH_QUARTZ_STAIRS, Stairs.Shape.STRAIGHT, Half.BOTTOM, BlockFace.WEST)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 3, 0, 0, Material.SMOOTH_QUARTZ_STAIRS, Stairs.Shape.STRAIGHT, Half.BOTTOM, BlockFace.WEST)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 3, 0, 1, Material.SMOOTH_QUARTZ_STAIRS, Stairs.Shape.STRAIGHT, Half.BOTTOM, BlockFace.WEST)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -3, 0, -1, Material.SMOOTH_QUARTZ_STAIRS, Stairs.Shape.STRAIGHT, Half.BOTTOM, BlockFace.EAST)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -3, 0, 0, Material.SMOOTH_QUARTZ_STAIRS, Stairs.Shape.STRAIGHT, Half.BOTTOM, BlockFace.EAST)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -3, 0, 1, Material.SMOOTH_QUARTZ_STAIRS, Stairs.Shape.STRAIGHT, Half.BOTTOM, BlockFace.EAST)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -1, 0, 3, Material.SMOOTH_QUARTZ_STAIRS, Stairs.Shape.STRAIGHT, Half.BOTTOM, BlockFace.NORTH)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 0, 3, Material.SMOOTH_QUARTZ_STAIRS, Stairs.Shape.STRAIGHT, Half.BOTTOM, BlockFace.NORTH)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 1, 0, 3, Material.SMOOTH_QUARTZ_STAIRS, Stairs.Shape.STRAIGHT, Half.BOTTOM, BlockFace.NORTH)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -1, 0, -3, Material.SMOOTH_QUARTZ_STAIRS, Stairs.Shape.STRAIGHT, Half.BOTTOM, BlockFace.SOUTH)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 0, -3, Material.SMOOTH_QUARTZ_STAIRS, Stairs.Shape.STRAIGHT, Half.BOTTOM, BlockFace.SOUTH)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 1, 0, -3, Material.SMOOTH_QUARTZ_STAIRS, Stairs.Shape.STRAIGHT, Half.BOTTOM, BlockFace.SOUTH)) return 0;
			
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 3, 0, 2, Material.SMOOTH_QUARTZ_SLAB, Slab.Type.BOTTOM)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 3, 0, -2, Material.SMOOTH_QUARTZ_SLAB, Slab.Type.BOTTOM)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -3, 0, 2, Material.SMOOTH_QUARTZ_SLAB, Slab.Type.BOTTOM)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -3, 0, -2, Material.SMOOTH_QUARTZ_SLAB, Slab.Type.BOTTOM)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 2, 0, 3, Material.SMOOTH_QUARTZ_SLAB, Slab.Type.BOTTOM)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -2, 0, 3, Material.SMOOTH_QUARTZ_SLAB, Slab.Type.BOTTOM)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 2, 0, -3, Material.SMOOTH_QUARTZ_SLAB, Slab.Type.BOTTOM)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -2, 0, -3, Material.SMOOTH_QUARTZ_SLAB, Slab.Type.BOTTOM)) return 0;
			
			
			//y = 1
			

			if (!LunarTransfusionListener.checkBlockRelative(player, center, 2, 1, 2, Material.BLACKSTONE_WALL)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 2, 1, -2, Material.BLACKSTONE_WALL)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -2, 1, 2, Material.BLACKSTONE_WALL)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -2, 1, -2, Material.BLACKSTONE_WALL)) return 0;
			
			
			//y = 2
			

			if (!LunarTransfusionListener.checkBlockRelative(player, center, 2, 2, 2, Material.BLACKSTONE_WALL)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 2, 2, -2, Material.BLACKSTONE_WALL)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -2, 2, 2, Material.BLACKSTONE_WALL)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -2, 2, -2, Material.BLACKSTONE_WALL)) return 0;
			
			
			//y = 3
			

			if (!LunarTransfusionListener.checkBlockRelative(player, center, 2, 3, 2, Material.BLACKSTONE_WALL)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 2, 3, -2, Material.BLACKSTONE_WALL)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -2, 3, 2, Material.BLACKSTONE_WALL)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -2, 3, -2, Material.BLACKSTONE_WALL)) return 0;
			
			
			//y = 4
			

			if (!LunarTransfusionListener.checkBlockRelative(player, center, 2, 4, 2, Material.BLACKSTONE_WALL)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 2, 4, -2, Material.BLACKSTONE_WALL)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -2, 4, 2, Material.BLACKSTONE_WALL)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -2, 4, -2, Material.BLACKSTONE_WALL)) return 0;
			
			
			//y = 5
			

			if (!LunarTransfusionListener.checkBlockRelative(player, center, 2, 5, 2, Material.SMOOTH_QUARTZ_SLAB, Slab.Type.BOTTOM)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 2, 5, -2, Material.SMOOTH_QUARTZ_SLAB, Slab.Type.BOTTOM)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -2, 5, 2, Material.SMOOTH_QUARTZ_SLAB, Slab.Type.BOTTOM)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -2, 5, -2, Material.SMOOTH_QUARTZ_SLAB, Slab.Type.BOTTOM)) return 0;
			
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 2, 5, -1, Material.SMOOTH_QUARTZ_STAIRS, Stairs.Shape.STRAIGHT, Half.TOP, BlockFace.WEST)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 2, 5, 0, Material.SMOOTH_QUARTZ_STAIRS, Stairs.Shape.STRAIGHT, Half.TOP, BlockFace.WEST)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 2, 5, 1, Material.SMOOTH_QUARTZ_STAIRS, Stairs.Shape.STRAIGHT, Half.TOP, BlockFace.WEST)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -2, 5, -1, Material.SMOOTH_QUARTZ_STAIRS, Stairs.Shape.STRAIGHT, Half.TOP, BlockFace.EAST)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -2, 5, 0, Material.SMOOTH_QUARTZ_STAIRS, Stairs.Shape.STRAIGHT, Half.TOP, BlockFace.EAST)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -2, 5, 1, Material.SMOOTH_QUARTZ_STAIRS, Stairs.Shape.STRAIGHT, Half.TOP, BlockFace.EAST)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -1, 5, 2, Material.SMOOTH_QUARTZ_STAIRS, Stairs.Shape.STRAIGHT, Half.TOP, BlockFace.NORTH)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 5, 2, Material.SMOOTH_QUARTZ_STAIRS, Stairs.Shape.STRAIGHT, Half.TOP, BlockFace.NORTH)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 1, 5, 2, Material.SMOOTH_QUARTZ_STAIRS, Stairs.Shape.STRAIGHT, Half.TOP, BlockFace.NORTH)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -1, 5, -2, Material.SMOOTH_QUARTZ_STAIRS, Stairs.Shape.STRAIGHT, Half.TOP, BlockFace.SOUTH)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 5, -2, Material.SMOOTH_QUARTZ_STAIRS, Stairs.Shape.STRAIGHT, Half.TOP, BlockFace.SOUTH)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 1, 5, -2, Material.SMOOTH_QUARTZ_STAIRS, Stairs.Shape.STRAIGHT, Half.TOP, BlockFace.SOUTH)) return 0;
			
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 5, 0, Material.AIR)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 5, 1, Material.SMOOTH_QUARTZ_SLAB, Slab.Type.TOP)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 5, -1, Material.SMOOTH_QUARTZ_SLAB, Slab.Type.TOP)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 1, 5, 0, Material.SMOOTH_QUARTZ_SLAB, Slab.Type.TOP)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 1, 5, 1, Material.SMOOTH_QUARTZ_SLAB, Slab.Type.TOP)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 1, 5, -1, Material.SMOOTH_QUARTZ_SLAB, Slab.Type.TOP)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -1, 5, 0, Material.SMOOTH_QUARTZ_SLAB, Slab.Type.TOP)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -1, 5, 1, Material.SMOOTH_QUARTZ_SLAB, Slab.Type.TOP)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -1, 5, -1, Material.SMOOTH_QUARTZ_SLAB, Slab.Type.TOP)) return 0;
			
			
			//y = 6
			

			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 6, 0, Material.SMOOTH_QUARTZ_SLAB, Slab.Type.BOTTOM)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 6, 1, Material.SMOOTH_QUARTZ_SLAB, Slab.Type.BOTTOM)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 0, 6, -1, Material.SMOOTH_QUARTZ_SLAB, Slab.Type.BOTTOM)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 1, 6, 0, Material.SMOOTH_QUARTZ_SLAB, Slab.Type.BOTTOM)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 1, 6, 1, Material.SMOOTH_QUARTZ_SLAB, Slab.Type.BOTTOM)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, 1, 6, -1, Material.SMOOTH_QUARTZ_SLAB, Slab.Type.BOTTOM)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -1, 6, 0, Material.SMOOTH_QUARTZ_SLAB, Slab.Type.BOTTOM)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -1, 6, 1, Material.SMOOTH_QUARTZ_SLAB, Slab.Type.BOTTOM)) return 0;
			if (!LunarTransfusionListener.checkBlockRelative(player, center, -1, 6, -1, Material.SMOOTH_QUARTZ_SLAB, Slab.Type.BOTTOM)) return 0;
			
			return 1;
		} else {
			return -1;
		}
	}

}
