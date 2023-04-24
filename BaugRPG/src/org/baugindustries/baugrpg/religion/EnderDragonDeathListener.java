package org.baugindustries.baugrpg.religion;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EnderDragonDeathListener implements Listener {
	private Main plugin;

	public EnderDragonDeathListener(Main plugin) {
		this.plugin = plugin;
		
	}
	
	
	@EventHandler
	public void onEnderDragonDeath(EntityDeathEvent event) {
		if (event.getEntityType().equals(EntityType.ENDER_DRAGON)) {
			File religionInfoFile = new File(plugin.getDataFolder() + File.separator + "religionInfo.yml");
			FileConfiguration religionInfoConfig = YamlConfiguration.loadConfiguration(religionInfoFile);
			
			if (!religionInfoConfig.contains("EnderDragonDefeated")) {
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						plugin.getServer().broadcastMessage(ChatColor.DARK_GREEN + "The gods grow uneasy...");
					}
				}, 150L);
			}
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				public void run() {
					World end = event.getEntity().getLocation().getWorld();
					Location topBlock = null;
					for (int i = 255; i > 0; i--) {
						if (end.getBlockAt(0, i, 0).getType().equals(Material.BEDROCK)) {
							topBlock = end.getBlockAt(0, i, 0).getLocation();
							break;
						}
					}
					end.strikeLightningEffect(topBlock);
					end.strikeLightningEffect(topBlock);
					end.strikeLightningEffect(topBlock);
					end.strikeLightningEffect(topBlock);
					end.strikeLightningEffect(topBlock);
					end.strikeLightningEffect(topBlock);
					end.strikeLightningEffect(topBlock);
					
					int topBlockHeight = topBlock.getBlockY();
					
					for (int y = topBlockHeight - 3; y < topBlockHeight + 5; y++) {
						for (int x = -13; x < 14; x++) {
							for (int z = -13; z < 14; z++) {
								Block block = end.getBlockAt(x, y, z);
								if (!(block.getType().equals(Material.BEDROCK) || block.getType().equals(Material.TORCH) || block.getType().equals(Material.END_PORTAL))) {
									if (y == topBlockHeight - 3) {
										block.setType(Material.END_STONE);
									} else if (block.getLocation().distance(topBlock) < 14) {
										block.setType(Material.AIR);
									}
								}
							}
						}
					}
					
					for (int x = -2; x < 3; x++) {
						for (int z = -2; z < 3; z++) {
							Block block = end.getBlockAt(x, topBlockHeight - 3, z);
							if (!(block.getType().equals(Material.BEDROCK) || block.getType().equals(Material.END_PORTAL))) {
								block.setType(Material.AIR);
							}
						}
					}
				
					//y = 0
					int y = topBlockHeight - 3;
					
					setBlock(end, 0, y, 9, Material.GOLD_BLOCK);
					setBlock(end, -9, y, 0, Material.EMERALD_BLOCK);
					setBlock(end, 0, y, -9, Material.DIAMOND_BLOCK);
					setBlock(end, 9, y, 0, Material.NETHERITE_BLOCK);
					
					
					
					//y = 1
					y++;

					setBlock(end, 3, y, 9, Material.DEEPSLATE_TILE_STAIRS, BlockFace.WEST, Half.TOP);
					setBlock(end, 2, y, 9, Material.CHISELED_DEEPSLATE);
					setBlock(end, 1, y, 9, Material.DEEPSLATE_TILE_STAIRS, BlockFace.EAST, Half.BOTTOM);
					setBlock(end, -1, y, 9, Material.DEEPSLATE_TILE_STAIRS, BlockFace.WEST, Half.BOTTOM);
					setBlock(end, -2, y, 9, Material.CHISELED_DEEPSLATE);
					setBlock(end, -3, y, 9, Material.DEEPSLATE_TILE_STAIRS, BlockFace.EAST, Half.TOP);
					
					setBlock(end, -9, y, 3, Material.QUARTZ_STAIRS, BlockFace.NORTH, Half.TOP);
					setBlock(end, -9, y, 2, Material.CHISELED_QUARTZ_BLOCK);
					setBlock(end, -9, y, 1, Material.QUARTZ_STAIRS, BlockFace.SOUTH, Half.BOTTOM);
					setBlock(end, -9, y, -1, Material.QUARTZ_STAIRS, BlockFace.NORTH, Half.BOTTOM);
					setBlock(end, -9, y, -2, Material.CHISELED_QUARTZ_BLOCK);
					setBlock(end, -9, y, -3, Material.QUARTZ_STAIRS, BlockFace.SOUTH, Half.TOP);
					
					setBlock(end, -3, y, -9, Material.SPRUCE_STAIRS, BlockFace.EAST, Half.TOP);
					setBlock(end, -2, y, -9, Material.STRIPPED_SPRUCE_WOOD);
					setBlock(end, -1, y, -9, Material.SPRUCE_STAIRS, BlockFace.WEST, Half.BOTTOM);
					setBlock(end, 1, y, -9, Material.SPRUCE_STAIRS, BlockFace.EAST, Half.BOTTOM);
					setBlock(end, 2, y, -9, Material.STRIPPED_SPRUCE_WOOD);
					setBlock(end, 3, y, -9, Material.SPRUCE_STAIRS, BlockFace.WEST, Half.TOP);
					
					setBlock(end, 9, y, -3, Material.RED_NETHER_BRICK_STAIRS, BlockFace.SOUTH, Half.TOP);
					setBlock(end, 9, y, -2, Material.CHISELED_NETHER_BRICKS);
					setBlock(end, 9, y, -1, Material.RED_NETHER_BRICK_STAIRS, BlockFace.NORTH, Half.BOTTOM);
					setBlock(end, 9, y, 1, Material.RED_NETHER_BRICK_STAIRS, BlockFace.SOUTH, Half.BOTTOM);
					setBlock(end, 9, y, 2, Material.CHISELED_NETHER_BRICKS);
					setBlock(end, 9, y, 3, Material.RED_NETHER_BRICK_STAIRS, BlockFace.NORTH, Half.TOP);
					
					
					
					//y = 2
					y++;
					
					setBlock(end, 3, y, 9, Material.CHISELED_DEEPSLATE);
					setBlock(end, 2, y, 9, Material.CHISELED_DEEPSLATE);
					setBlock(end, -2, y, 9, Material.CHISELED_DEEPSLATE);
					setBlock(end, -3, y, 9, Material.CHISELED_DEEPSLATE);

					setBlock(end, -9, y, 3, Material.CHISELED_QUARTZ_BLOCK);
					setBlock(end, -9, y, 2, Material.CHISELED_QUARTZ_BLOCK);
					setBlock(end, -9, y, -2, Material.CHISELED_QUARTZ_BLOCK);
					setBlock(end, -9, y, -3, Material.CHISELED_QUARTZ_BLOCK);

					setBlock(end, -3, y, -9, Material.STRIPPED_SPRUCE_WOOD);
					setBlock(end, -2, y, -9, Material.STRIPPED_SPRUCE_WOOD);
					setBlock(end, 2, y, -9, Material.STRIPPED_SPRUCE_WOOD);
					setBlock(end, 3, y, -9, Material.STRIPPED_SPRUCE_WOOD);

					setBlock(end, 9, y, -3, Material.CHISELED_NETHER_BRICKS);
					setBlock(end, 9, y, -2, Material.CHISELED_NETHER_BRICKS);
					setBlock(end, 9, y, 2, Material.CHISELED_NETHER_BRICKS);
					setBlock(end, 9, y, 3, Material.CHISELED_NETHER_BRICKS);
					
					
					
					//y = 3
					y++;
					
					setBlock(end, 3, y, 9, Material.CHISELED_DEEPSLATE);
					setBlock(end, 2, y, 9, Material.DEEPSLATE_TILE_STAIRS, BlockFace.EAST, Half.BOTTOM);
					setBlock(end, -2, y, 9, Material.DEEPSLATE_TILE_STAIRS, BlockFace.WEST, Half.BOTTOM);
					setBlock(end, -3, y, 9, Material.CHISELED_DEEPSLATE);
					
					setBlock(end, -9, y, 3, Material.CHISELED_QUARTZ_BLOCK);
					setBlock(end, -9, y, 2, Material.QUARTZ_STAIRS, BlockFace.SOUTH, Half.BOTTOM);
					setBlock(end, -9, y, -2, Material.QUARTZ_STAIRS, BlockFace.NORTH, Half.BOTTOM);
					setBlock(end, -9, y, -3, Material.CHISELED_QUARTZ_BLOCK);
					
					setBlock(end, -3, y, -9, Material.STRIPPED_SPRUCE_WOOD);
					setBlock(end, -2, y, -9, Material.SPRUCE_STAIRS, BlockFace.WEST, Half.BOTTOM);
					setBlock(end, 2, y, -9, Material.SPRUCE_STAIRS, BlockFace.EAST, Half.BOTTOM);
					setBlock(end, 3, y, -9, Material.STRIPPED_SPRUCE_WOOD);
					
					setBlock(end, 9, y, -3, Material.CHISELED_NETHER_BRICKS);
					setBlock(end, 9, y, -2, Material.RED_NETHER_BRICK_STAIRS, BlockFace.NORTH, Half.BOTTOM);
					setBlock(end, 9, y, 2, Material.RED_NETHER_BRICK_STAIRS, BlockFace.SOUTH, Half.BOTTOM);
					setBlock(end, 9, y, 3, Material.CHISELED_NETHER_BRICKS);
					
					
					
					//y = 4
					y++;
					
					setBlock(end, 3, y, 9, Material.CHISELED_DEEPSLATE);
					setBlock(end, 2, y, 9, Material.DEEPSLATE_TILE_STAIRS, BlockFace.EAST, Half.TOP);
					setBlock(end, -2, y, 9, Material.DEEPSLATE_TILE_STAIRS, BlockFace.WEST, Half.TOP);
					setBlock(end, -3, y, 9, Material.CHISELED_DEEPSLATE);
					
					setBlock(end, -9, y, 3, Material.CHISELED_QUARTZ_BLOCK);
					setBlock(end, -9, y, 2, Material.QUARTZ_STAIRS, BlockFace.SOUTH, Half.TOP);
					setBlock(end, -9, y, -2, Material.QUARTZ_STAIRS, BlockFace.NORTH, Half.TOP);
					setBlock(end, -9, y, -3, Material.CHISELED_QUARTZ_BLOCK);
					
					setBlock(end, -3, y, -9, Material.STRIPPED_SPRUCE_WOOD);
					setBlock(end, -2, y, -9, Material.SPRUCE_STAIRS, BlockFace.WEST, Half.TOP);
					setBlock(end, 2, y, -9, Material.SPRUCE_STAIRS, BlockFace.EAST, Half.TOP);
					setBlock(end, 3, y, -9, Material.STRIPPED_SPRUCE_WOOD);
					
					setBlock(end, 9, y, -3, Material.CHISELED_NETHER_BRICKS);
					setBlock(end, 9, y, -2, Material.RED_NETHER_BRICK_STAIRS, BlockFace.NORTH, Half.TOP);
					setBlock(end, 9, y, 2, Material.RED_NETHER_BRICK_STAIRS, BlockFace.SOUTH, Half.TOP);
					setBlock(end, 9, y, 3, Material.CHISELED_NETHER_BRICKS);
					
					
					
					//y = 5
					y++;

					setBlock(end, 3, y, 9, Material.DEEPSLATE_TILE_STAIRS, BlockFace.WEST, Half.BOTTOM);
					setBlock(end, 2, y, 9, Material.CHISELED_DEEPSLATE);
					setBlock(end, -2, y, 9, Material.CHISELED_DEEPSLATE);
					setBlock(end, -3, y, 9, Material.DEEPSLATE_TILE_STAIRS, BlockFace.EAST, Half.BOTTOM);

					setBlock(end, -9, y, 3, Material.QUARTZ_STAIRS, BlockFace.NORTH, Half.BOTTOM);
					setBlock(end, -9, y, 2, Material.CHISELED_QUARTZ_BLOCK);
					setBlock(end, -9, y, -2, Material.CHISELED_QUARTZ_BLOCK);
					setBlock(end, -9, y, -3, Material.QUARTZ_STAIRS, BlockFace.SOUTH, Half.BOTTOM);

					setBlock(end, -3, y, -9, Material.SPRUCE_STAIRS, BlockFace.EAST, Half.BOTTOM);
					setBlock(end, -2, y, -9, Material.STRIPPED_SPRUCE_WOOD);
					setBlock(end, 2, y, -9, Material.STRIPPED_SPRUCE_WOOD);
					setBlock(end, 3, y, -9, Material.SPRUCE_STAIRS, BlockFace.WEST, Half.BOTTOM);

					setBlock(end, 9, y, -3, Material.RED_NETHER_BRICK_STAIRS, BlockFace.SOUTH, Half.BOTTOM);
					setBlock(end, 9, y, -2, Material.CHISELED_NETHER_BRICKS);
					setBlock(end, 9, y, 2, Material.CHISELED_NETHER_BRICKS);
					setBlock(end, 9, y, 3, Material.RED_NETHER_BRICK_STAIRS, BlockFace.NORTH, Half.BOTTOM);
					
					
					
					//y = 6
					y++;

					setBlock(end, 2, y, 9, Material.CHISELED_DEEPSLATE);
					setBlock(end, 1, y, 9, Material.DEEPSLATE_TILE_STAIRS, BlockFace.EAST, Half.TOP);
					setBlock(end, -1, y, 9, Material.DEEPSLATE_TILE_STAIRS, BlockFace.WEST, Half.TOP);
					setBlock(end, -2, y, 9, Material.CHISELED_DEEPSLATE);

					setBlock(end, -9, y, 2, Material.CHISELED_QUARTZ_BLOCK);
					setBlock(end, -9, y, 1, Material.QUARTZ_STAIRS, BlockFace.SOUTH, Half.TOP);
					setBlock(end, -9, y, -1, Material.QUARTZ_STAIRS, BlockFace.NORTH, Half.TOP);
					setBlock(end, -9, y, -2, Material.CHISELED_QUARTZ_BLOCK);

					setBlock(end, -2, y, -9, Material.STRIPPED_SPRUCE_WOOD);
					setBlock(end, -1, y, -9, Material.SPRUCE_STAIRS, BlockFace.WEST, Half.TOP);
					setBlock(end, 1, y, -9, Material.SPRUCE_STAIRS, BlockFace.EAST, Half.TOP);
					setBlock(end, 2, y, -9, Material.STRIPPED_SPRUCE_WOOD);

					setBlock(end, 9, y, -2, Material.CHISELED_NETHER_BRICKS);
					setBlock(end, 9, y, -1, Material.RED_NETHER_BRICK_STAIRS, BlockFace.NORTH, Half.TOP);
					setBlock(end, 9, y, 1, Material.RED_NETHER_BRICK_STAIRS, BlockFace.SOUTH, Half.TOP);
					setBlock(end, 9, y, 2, Material.CHISELED_NETHER_BRICKS);
					
					
					
					//y = 7
					y++;

					setBlock(end, 2, y, 9, Material.DEEPSLATE_TILE_SLAB);
					setBlock(end, 1, y, 9, Material.CHISELED_DEEPSLATE);
					setBlock(end, -1, y, 9, Material.CHISELED_DEEPSLATE);
					setBlock(end, -2, y, 9, Material.DEEPSLATE_TILE_SLAB);

					setBlock(end, -9, y, 2, Material.QUARTZ_SLAB);
					setBlock(end, -9, y, 1, Material.CHISELED_QUARTZ_BLOCK);
					setBlock(end, -9, y, -1, Material.CHISELED_QUARTZ_BLOCK);
					setBlock(end, -9, y, -2, Material.QUARTZ_SLAB);

					setBlock(end, -2, y, -9, Material.SPRUCE_SLAB);
					setBlock(end, -1, y, -9, Material.STRIPPED_SPRUCE_WOOD);
					setBlock(end, 1, y, -9, Material.STRIPPED_SPRUCE_WOOD);
					setBlock(end, 2, y, -9, Material.SPRUCE_SLAB);

					setBlock(end, 9, y, -2, Material.RED_NETHER_BRICK_SLAB);
					setBlock(end, 9, y, -1, Material.CHISELED_NETHER_BRICKS);
					setBlock(end, 9, y, 1, Material.CHISELED_NETHER_BRICKS);
					setBlock(end, 9, y, 2, Material.RED_NETHER_BRICK_SLAB);
					
					
					
					//y = 8
					y++;

					setBlock(end, 1, y, 9, Material.DEEPSLATE_TILE_STAIRS, BlockFace.WEST, Half.BOTTOM);
					setBlock(end, -1, y, 9, Material.DEEPSLATE_TILE_STAIRS, BlockFace.EAST, Half.BOTTOM);

					setBlock(end, -9, y, 1, Material.QUARTZ_STAIRS, BlockFace.NORTH, Half.BOTTOM);
					setBlock(end, -9, y, -1, Material.QUARTZ_STAIRS, BlockFace.SOUTH, Half.BOTTOM);

					setBlock(end, -1, y, -9, Material.SPRUCE_STAIRS, BlockFace.EAST, Half.BOTTOM);
					setBlock(end, 1, y, -9, Material.SPRUCE_STAIRS, BlockFace.WEST, Half.BOTTOM);

					setBlock(end, 9, y, -1, Material.RED_NETHER_BRICK_STAIRS, BlockFace.SOUTH, Half.BOTTOM);
					setBlock(end, 9, y, 1, Material.RED_NETHER_BRICK_STAIRS, BlockFace.NORTH, Half.BOTTOM);
					

					

					ConfigurationSection heavensPortalSection;
					if (religionInfoConfig.contains("Heavens Portals")) {
						heavensPortalSection = religionInfoConfig.getConfigurationSection("Heavens Portals");
					} else {
						heavensPortalSection = religionInfoConfig.createSection("Heavens Portals");
					}

					boolean setMenPortal = true;
					boolean setElfPortal = true;
					boolean setDwarfPortal = true;
					boolean setOrcPortal = true;
					
					for (String heavensPortalKey : heavensPortalSection.getKeys(false)) {
						ConfigurationSection portal = heavensPortalSection.getConfigurationSection(heavensPortalKey);
						
						if (portal.getLocation("Location").equals(new Location(end, 0, topBlockHeight - 3, -9))) {
							setMenPortal = false;
						}
						
						if (portal.getLocation("Location").equals(new Location(end, -9, topBlockHeight - 3, 0))) {
							setElfPortal = false;
						}

						if (portal.getLocation("Location").equals(new Location(end, 0, topBlockHeight - 3, 9))) {
							setDwarfPortal = false;
						}

						if (portal.getLocation("Location").equals(new Location(end, 9, topBlockHeight - 3, 0))) {
							setOrcPortal = false;
						}
						
						
					}
					
					if (setMenPortal) {
						ConfigurationSection menPortal = heavensPortalSection.createSection(UUID.randomUUID().toString());
						menPortal.set("Location", new Location(end, 0, topBlockHeight - 3, -9));
						menPortal.set("Type", "Men");
						menPortal.set("NorthSouth", false);
					}
					
					if (setElfPortal) {
						ConfigurationSection elfPortal = heavensPortalSection.createSection(UUID.randomUUID().toString());
						elfPortal.set("Location", new Location(end, -9, topBlockHeight - 3, 0));
						elfPortal.set("Type", "Elf");
						elfPortal.set("NorthSouth", true);
					}
					
					if (setDwarfPortal) {
						ConfigurationSection dwarfPortal = heavensPortalSection.createSection(UUID.randomUUID().toString());
						dwarfPortal.set("Location", new Location(end, 0, topBlockHeight - 3, 9));
						dwarfPortal.set("Type", "Dwarf");
						dwarfPortal.set("NorthSouth", false);
					}
					
					if (setOrcPortal) {
						ConfigurationSection orcPortal = heavensPortalSection.createSection(UUID.randomUUID().toString());
						orcPortal.set("Location", new Location(end, 9, topBlockHeight - 3, 0));
						orcPortal.set("Type", "Orc");
						orcPortal.set("NorthSouth", true);
					}
					
					try {
						religionInfoConfig.save(religionInfoFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
			}, 150L);
			
			religionInfoConfig.set("EnderDragonDefeated", true);
			
			try {
				religionInfoConfig.save(religionInfoFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	void setBlock(World world, int x, int y, int z, Material material) {
		world.getBlockAt(x, y, z).setType(material);
	}
	
	void setBlock(World world, int x, int y, int z, Material material, BlockFace face, Half half) {
		Block block = world.getBlockAt(x, y, z);
		block.setType(material);
		Stairs data = (Stairs) block.getBlockData();
		data.setFacing(face);
		data.setHalf(half);
		block.setBlockData(data);
	}
}
