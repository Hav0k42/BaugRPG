package org.baugindustries.baugrpg.listeners;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.baugindustries.baugrpg.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Slab;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class LunarTransfusionListener implements Listener {
	private final Slab.Type top = Slab.Type.TOP;
	private final Slab.Type bottom = Slab.Type.BOTTOM;
	
	
	private Main plugin;
	public LunarTransfusionListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void PlayerInteractEvent(PlayerInteractEvent event) {
		if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		
		Player player = event.getPlayer();
		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
	 	
	 	if (!((skillsconfig.contains("LunarArtificer1") && skillsconfig.getBoolean("LunarArtificer1")) || (skillsconfig.contains("LunarArtificer3") && skillsconfig.getBoolean("LunarArtificer3")))) return;
		
	 	if (event.getItem() == null) return;
	 	
		if (!event.getItem().getType().equals(Material.GLASS_BOTTLE)) return;
		
		Block block = event.getPlayer().getTargetBlock((Set<Material>) null, 4);
	    if (!block.getType().equals(Material.WATER)) return;
		
		
		
		World world = player.getWorld();
		
		if (!(world.getTime() < 23500 && world.getTime() > 12500)) return;
		if (world.hasStorm()) return;
		
		
		double itemDist = 2;
		Collection<Entity> nearbyEntities = world.getNearbyEntities(block.getLocation(), itemDist, itemDist, itemDist);
		List<Entity> nearbyItems = new ArrayList<Entity>();
		nearbyEntities.forEach(entity -> {
			nearbyItems.add(entity);
		});
		for (int i = 0; i < nearbyItems.size(); i++) {
			if (!(nearbyItems.get(i) instanceof Item)) {
				nearbyItems.remove(i);
			} else if (!world.getBlockAt(nearbyItems.get(i).getLocation()).getType().equals(Material.WATER)) {
				nearbyItems.remove(i);
			}
		}
		if (nearbyItems.size() == 0) return;
		
		int poolStructure = getPoolStructure(player, block.getLocation());
		
		
		int days = (int) world.getFullTime() / 24000;
		int phase = days % 8;//0 is full moon, 4 is new moon
		
		HashMap<Material, Material> activeRecipes = new HashMap<Material, Material>();
		
		if (phase == 0 && (skillsconfig.contains("LunarArtificer1") && skillsconfig.getBoolean("LunarArtificer1"))) {//full moon recipes
			activeRecipes.put(Material.STONE, Material.PRISMARINE_BRICKS);
			activeRecipes.put(Material.COBBLESTONE, Material.PRISMARINE);
			activeRecipes.put(Material.DEEPSLATE, Material.DARK_PRISMARINE);
			activeRecipes.put(Material.GRASS, Material.WARPED_ROOTS);
			activeRecipes.put(Material.VINE, Material.TWISTING_VINES);
			if (poolStructure == 1 || poolStructure == 2 || poolStructure == 5) {
				activeRecipes.put(Material.POLISHED_DEEPSLATE, Material.PACKED_ICE);
				activeRecipes.put(Material.GLOWSTONE, Material.SEA_LANTERN);
				activeRecipes.put(Material.BONE, Material.NAUTILUS_SHELL);
				activeRecipes.put(Material.OAK_SAPLING, Material.WARPED_FUNGUS);
				if (poolStructure == 2 || poolStructure == 5) {
					activeRecipes.put(Material.RAW_IRON, Material.PRISMARINE_SHARD);
					activeRecipes.put(Material.GRASS_BLOCK, Material.WARPED_NYLIUM);
					activeRecipes.put(Material.REDSTONE, Material.GUNPOWDER);
					activeRecipes.put(Material.WHEAT_SEEDS, Material.PRISMARINE_CRYSTALS);
					if (poolStructure == 5) {
						activeRecipes.put(Material.DIAMOND_BLOCK, Material.HEART_OF_THE_SEA);
					}
				}
			}
		} else if (phase == 4 && (skillsconfig.contains("LunarArtificer3") && skillsconfig.getBoolean("LunarArtificer3"))) {//new moon recipes
			activeRecipes.put(Material.STONE, Material.NETHERRACK);
			activeRecipes.put(Material.COBBLESTONE, Material.SOUL_SAND);
			activeRecipes.put(Material.DEEPSLATE, Material.NETHER_BRICKS);
			activeRecipes.put(Material.GRASS, Material.CRIMSON_ROOTS);
			activeRecipes.put(Material.VINE, Material.WEEPING_VINES);
			if (poolStructure == 3 || poolStructure == 4 || poolStructure == 5) {
				activeRecipes.put(Material.POLISHED_DEEPSLATE, Material.RED_NETHER_BRICKS);
				activeRecipes.put(Material.GLOWSTONE, Material.SHROOMLIGHT);
				activeRecipes.put(Material.BONE, Material.BLAZE_ROD);
				activeRecipes.put(Material.OAK_SAPLING, Material.CRIMSON_FUNGUS);
				if (poolStructure == 4 || poolStructure == 5) {
					activeRecipes.put(Material.RAW_IRON, Material.QUARTZ);
					activeRecipes.put(Material.GRASS_BLOCK, Material.CRIMSON_NYLIUM);
					activeRecipes.put(Material.REDSTONE, Material.GLOWSTONE_DUST);
					activeRecipes.put(Material.WHEAT_SEEDS, Material.NETHER_WART);
					if (poolStructure == 5) {
						activeRecipes.put(Material.DIAMOND_BLOCK, Material.ANCIENT_DEBRIS);
					}
				}
			}
		}
		
		
		
		List<Item> mutatableItems = new ArrayList<Item>();
		for (int i = 0; i < nearbyItems.size(); i++) {
			if (activeRecipes.containsKey(((Item)nearbyItems.get(i)).getItemStack().getType())) {
				mutatableItems.add((Item) nearbyItems.get(i));
			}
		}
		
		if (mutatableItems.size() == 0) return;
		
		for (int i = 0; i < mutatableItems.size(); i++) {
			ItemStack itemStack = mutatableItems.get(i).getItemStack();
			Material type = itemStack.getType();
			itemStack.setType(activeRecipes.get(type));
			mutatableItems.get(i).setItemStack(itemStack);
		}
		

		Particle particle = Particle.END_ROD;
		player.getWorld().spawnParticle(particle, block.getLocation().add(0.5, 1, 0.5), 60, 0.25, 0.25, 0.25, 0.03);
		

	    ItemStack newItem = event.getItem();
		
		if (player.getInventory().getItemInMainHand().equals(newItem)) {
	    	if (newItem.getAmount() == 1) {
	    		player.getInventory().setItemInMainHand(null);
	    	} else {
		    	newItem.setAmount(newItem.getAmount() - 1);
		    	player.getInventory().setItemInMainHand(newItem);
	    	}
	    } else {
	    	if (newItem.getAmount() == 1) {
	    		player.getInventory().setItemInOffHand(null);
	    	} else {
		    	newItem.setAmount(newItem.getAmount() - 1);
		    	player.getInventory().setItemInOffHand(newItem);
	    	}
	    }
		
		player.getInventory().addItem(plugin.itemManager.getBottledStarlightItem());
		event.setCancelled(true);
	}
	
	private int getPoolStructure(Player player, Location center) {
		if (checkBlockInit(center, 0, -1, 0, Material.DARK_PRISMARINE)) {//Check for pris structures
			if (checkBlockInit(center, 1, 1, 1, Material.PRISMARINE_BRICKS)) {//Check for level 1 structure
				//y=0
				if (!checkBlockRelative(player, center, 1, 0, 0, Material.DARK_PRISMARINE)) return 0;
				if (!checkBlockRelative(player, center, -1, 0, 0, Material.DARK_PRISMARINE)) return 0;
				if (!checkBlockRelative(player, center, 0, 0, 1, Material.DARK_PRISMARINE)) return 0;
				if (!checkBlockRelative(player, center, 0, 0, -1, Material.DARK_PRISMARINE)) return 0;
				
				if (!checkBlockRelative(player, center, 2, 0, 0, Material.PRISMARINE_BRICKS)) return 0;
				if (!checkBlockRelative(player, center, -2, 0, 0, Material.PRISMARINE_BRICKS)) return 0;
				if (!checkBlockRelative(player, center, 0, 0, 2, Material.PRISMARINE_BRICKS)) return 0;
				if (!checkBlockRelative(player, center, 0, 0, -2, Material.PRISMARINE_BRICKS)) return 0;
				
				
				//y=1
				if (!checkBlockRelative(player, center, 1, 1, 0, Material.PRISMARINE_BRICK_SLAB, bottom)) return 0;
				if (!checkBlockRelative(player, center, -1, 1, 0, Material.PRISMARINE_BRICK_SLAB, bottom)) return 0;
				if (!checkBlockRelative(player, center, 0, 1, 1, Material.PRISMARINE_BRICK_SLAB, bottom)) return 0;
				if (!checkBlockRelative(player, center, 0, 1, -1, Material.PRISMARINE_BRICK_SLAB, bottom)) return 0;
				
				//Already checked 1, 1, 1
				if (!checkBlockRelative(player, center, 1, 1, -1, Material.PRISMARINE_BRICKS)) return 0;
				if (!checkBlockRelative(player, center, -1, 1, 1, Material.PRISMARINE_BRICKS)) return 0;
				if (!checkBlockRelative(player, center, -1, 1, -1, Material.PRISMARINE_BRICKS)) return 0;
				
				
				//y=2
				if (!checkBlockRelative(player, center, 1, 2, 1, Material.PRISMARINE_WALL)) return 0;
				if (!checkBlockRelative(player, center, -1, 2, 1, Material.PRISMARINE_WALL)) return 0;
				if (!checkBlockRelative(player, center, 1, 2, -1, Material.PRISMARINE_WALL)) return 0;
				if (!checkBlockRelative(player, center, -1, 2, -1, Material.PRISMARINE_WALL)) return 0;
				
				
				//Passed the test
				return 1;
				
			} else if (checkBlockInit(center, 1, 1, 1, Material.SEA_LANTERN)) {//Check for level 2 structure
				//y=0
				if (!checkBlockRelative(player, center, 1, 0, 0, Material.DARK_PRISMARINE)) return 0;
				if (!checkBlockRelative(player, center, -1, 0, 0, Material.DARK_PRISMARINE)) return 0;
				if (!checkBlockRelative(player, center, 0, 0, 1, Material.DARK_PRISMARINE)) return 0;
				if (!checkBlockRelative(player, center, 0, 0, -1, Material.DARK_PRISMARINE)) return 0;
				
				if (!checkBlockRelative(player, center, 2, 0, 0, Material.PRISMARINE_BRICKS)) return 0;
				if (!checkBlockRelative(player, center, -2, 0, 0, Material.PRISMARINE_BRICKS)) return 0;
				if (!checkBlockRelative(player, center, 0, 0, 2, Material.PRISMARINE_BRICKS)) return 0;
				if (!checkBlockRelative(player, center, 0, 0, -2, Material.PRISMARINE_BRICKS)) return 0;
				
				if (!checkBlockRelative(player, center, 2, 0, 1, Material.PACKED_ICE)) return 0;
				if (!checkBlockRelative(player, center, 2, 0, -1, Material.PACKED_ICE)) return 0;
				if (!checkBlockRelative(player, center, -2, 0, 1, Material.PACKED_ICE)) return 0;
				if (!checkBlockRelative(player, center, -2, 0, -1, Material.PACKED_ICE)) return 0;
				if (!checkBlockRelative(player, center, 1, 0, 2, Material.PACKED_ICE)) return 0;
				if (!checkBlockRelative(player, center, -1, 0, 2, Material.PACKED_ICE)) return 0;
				if (!checkBlockRelative(player, center, 1, 0, -2, Material.PACKED_ICE)) return 0;
				if (!checkBlockRelative(player, center, -1, 0, -2, Material.PACKED_ICE)) return 0;
				
				
				//y=1
				if (!checkBlockRelative(player, center, 1, 1, 0, Material.PRISMARINE_BRICK_SLAB, bottom)) return 0;
				if (!checkBlockRelative(player, center, -1, 1, 0, Material.PRISMARINE_BRICK_SLAB, bottom)) return 0;
				if (!checkBlockRelative(player, center, 0, 1, 1, Material.PRISMARINE_BRICK_SLAB, bottom)) return 0;
				if (!checkBlockRelative(player, center, 0, 1, -1, Material.PRISMARINE_BRICK_SLAB, bottom)) return 0;
				
				//Already checked 1, 1, 1
				if (!checkBlockRelative(player, center, 1, 1, -1, Material.SEA_LANTERN)) return 0;
				if (!checkBlockRelative(player, center, -1, 1, 1, Material.SEA_LANTERN)) return 0;
				if (!checkBlockRelative(player, center, -1, 1, -1, Material.SEA_LANTERN)) return 0;
				
				
				//y=2
				if (!checkBlockRelative(player, center, 1, 2, 1, Material.PRISMARINE_WALL)) return 0;
				if (!checkBlockRelative(player, center, -1, 2, 1, Material.PRISMARINE_WALL)) return 0;
				if (!checkBlockRelative(player, center, 1, 2, -1, Material.PRISMARINE_WALL)) return 0;
				if (!checkBlockRelative(player, center, -1, 2, -1, Material.PRISMARINE_WALL)) return 0;
				
				
				//y=3
				if (!checkBlockRelative(player, center, 1, 3, 1, Material.PRISMARINE_WALL)) return 0;
				if (!checkBlockRelative(player, center, -1, 3, 1, Material.PRISMARINE_WALL)) return 0;
				if (!checkBlockRelative(player, center, 1, 3, -1, Material.PRISMARINE_WALL)) return 0;
				if (!checkBlockRelative(player, center, -1, 3, -1, Material.PRISMARINE_WALL)) return 0;
				
				
				//y=4
				if (!checkBlockRelative(player, center, 1, 4, 1, Material.DARK_PRISMARINE_SLAB, bottom)) return 0;
				if (!checkBlockRelative(player, center, -1, 4, 1, Material.DARK_PRISMARINE_SLAB, bottom)) return 0;
				if (!checkBlockRelative(player, center, 1, 4, -1, Material.DARK_PRISMARINE_SLAB, bottom)) return 0;
				if (!checkBlockRelative(player, center, -1, 4, -1, Material.DARK_PRISMARINE_SLAB, bottom)) return 0;
				
				if (!checkBlockRelative(player, center, 1, 4, 0, Material.DARK_PRISMARINE_SLAB, top)) return 0;
				if (!checkBlockRelative(player, center, -1, 4, 0, Material.DARK_PRISMARINE_SLAB, top)) return 0;
				if (!checkBlockRelative(player, center, 0, 4, 1, Material.DARK_PRISMARINE_SLAB, top)) return 0;
				if (!checkBlockRelative(player, center, 0, 4, -1, Material.DARK_PRISMARINE_SLAB, top)) return 0;
				
				//y=5
				if (!checkBlockRelative(player, center, 0, 5, 0, Material.DARK_PRISMARINE_SLAB, bottom)) return 0;
				
				
				//Passed the test
				return 2;
			}
		} else if (checkBlockInit(center, 0, -1, 0, Material.NETHER_BRICKS)) {//Check for nether structures
			if (checkBlockInit(center, 1, 1, 1, Material.NETHERRACK)) {//Check for level 1 structure
				//y=0
				if (!checkBlockRelative(player, center, 1, 0, 0, Material.NETHER_BRICKS)) return 0;
				if (!checkBlockRelative(player, center, -1, 0, 0, Material.NETHER_BRICKS)) return 0;
				if (!checkBlockRelative(player, center, 0, 0, 1, Material.NETHER_BRICKS)) return 0;
				if (!checkBlockRelative(player, center, 0, 0, -1, Material.NETHER_BRICKS)) return 0;
				
				if (!checkBlockRelative(player, center, 2, 0, 0, Material.SOUL_SAND)) return 0;
				if (!checkBlockRelative(player, center, -2, 0, 0, Material.SOUL_SAND)) return 0;
				if (!checkBlockRelative(player, center, 0, 0, 2, Material.SOUL_SAND)) return 0;
				if (!checkBlockRelative(player, center, 0, 0, -2, Material.SOUL_SAND)) return 0;
				
				
				//y=1
				if (!checkBlockRelative(player, center, 1, 1, 0, Material.NETHER_BRICK_SLAB, bottom)) return 0;
				if (!checkBlockRelative(player, center, -1, 1, 0, Material.NETHER_BRICK_SLAB, bottom)) return 0;
				if (!checkBlockRelative(player, center, 0, 1, 1, Material.NETHER_BRICK_SLAB, bottom)) return 0;
				if (!checkBlockRelative(player, center, 0, 1, -1, Material.NETHER_BRICK_SLAB, bottom)) return 0;
				
				//Already checked 1, 1, 1
				if (!checkBlockRelative(player, center, 1, 1, -1, Material.NETHERRACK)) return 0;
				if (!checkBlockRelative(player, center, -1, 1, 1, Material.NETHERRACK)) return 0;
				if (!checkBlockRelative(player, center, -1, 1, -1, Material.NETHERRACK)) return 0;
				
				
				//y=2
				if (!checkBlockRelative(player, center, 1, 2, 1, Material.NETHER_BRICK_WALL)) return 0;
				if (!checkBlockRelative(player, center, -1, 2, 1, Material.NETHER_BRICK_WALL)) return 0;
				if (!checkBlockRelative(player, center, 1, 2, -1, Material.NETHER_BRICK_WALL)) return 0;
				if (!checkBlockRelative(player, center, -1, 2, -1, Material.NETHER_BRICK_WALL)) return 0;
				
				
				//Passed the test
				return 3;
			} else if (checkBlockInit(center, 1, 1, 1, Material.SHROOMLIGHT)) {//Check for level 2 structure
				//y=0
				if (!checkBlockRelative(player, center, 1, 0, 0, Material.NETHER_BRICKS)) return 0;
				if (!checkBlockRelative(player, center, -1, 0, 0, Material.NETHER_BRICKS)) return 0;
				if (!checkBlockRelative(player, center, 0, 0, 1, Material.NETHER_BRICKS)) return 0;
				if (!checkBlockRelative(player, center, 0, 0, -1, Material.NETHER_BRICKS)) return 0;
				
				if (!checkBlockRelative(player, center, 2, 0, 0, Material.SOUL_SAND)) return 0;
				if (!checkBlockRelative(player, center, -2, 0, 0, Material.SOUL_SAND)) return 0;
				if (!checkBlockRelative(player, center, 0, 0, 2, Material.SOUL_SAND)) return 0;
				if (!checkBlockRelative(player, center, 0, 0, -2, Material.SOUL_SAND)) return 0;
				
				if (!checkBlockRelative(player, center, 2, 0, 1, Material.RED_NETHER_BRICKS)) return 0;
				if (!checkBlockRelative(player, center, 2, 0, -1, Material.RED_NETHER_BRICKS)) return 0;
				if (!checkBlockRelative(player, center, -2, 0, 1, Material.RED_NETHER_BRICKS)) return 0;
				if (!checkBlockRelative(player, center, -2, 0, -1, Material.RED_NETHER_BRICKS)) return 0;
				if (!checkBlockRelative(player, center, 1, 0, 2, Material.RED_NETHER_BRICKS)) return 0;
				if (!checkBlockRelative(player, center, -1, 0, 2, Material.RED_NETHER_BRICKS)) return 0;
				if (!checkBlockRelative(player, center, 1, 0, -2, Material.RED_NETHER_BRICKS)) return 0;
				if (!checkBlockRelative(player, center, -1, 0, -2, Material.RED_NETHER_BRICKS)) return 0;
				
				
				//y=1
				if (!checkBlockRelative(player, center, 1, 1, 0, Material.NETHER_BRICK_SLAB, bottom)) return 0;
				if (!checkBlockRelative(player, center, -1, 1, 0, Material.NETHER_BRICK_SLAB, bottom)) return 0;
				if (!checkBlockRelative(player, center, 0, 1, 1, Material.NETHER_BRICK_SLAB, bottom)) return 0;
				if (!checkBlockRelative(player, center, 0, 1, -1, Material.NETHER_BRICK_SLAB, bottom)) return 0;
				
				//Already checked 1, 1, 1
				if (!checkBlockRelative(player, center, 1, 1, -1, Material.SHROOMLIGHT)) return 0;
				if (!checkBlockRelative(player, center, -1, 1, 1, Material.SHROOMLIGHT)) return 0;
				if (!checkBlockRelative(player, center, -1, 1, -1, Material.SHROOMLIGHT)) return 0;
				
				
				//y=2
				if (!checkBlockRelative(player, center, 1, 2, 1, Material.NETHER_BRICK_WALL)) return 0;
				if (!checkBlockRelative(player, center, -1, 2, 1, Material.NETHER_BRICK_WALL)) return 0;
				if (!checkBlockRelative(player, center, 1, 2, -1, Material.NETHER_BRICK_WALL)) return 0;
				if (!checkBlockRelative(player, center, -1, 2, -1, Material.NETHER_BRICK_WALL)) return 0;
				
				
				//y=3
				if (!checkBlockRelative(player, center, 1, 3, 1, Material.NETHER_BRICK_WALL)) return 0;
				if (!checkBlockRelative(player, center, -1, 3, 1, Material.NETHER_BRICK_WALL)) return 0;
				if (!checkBlockRelative(player, center, 1, 3, -1, Material.NETHER_BRICK_WALL)) return 0;
				if (!checkBlockRelative(player, center, -1, 3, -1, Material.NETHER_BRICK_WALL)) return 0;
				
				
				//y=4
				if (!checkBlockRelative(player, center, 1, 4, 1, Material.NETHER_BRICK_SLAB, bottom)) return 0;
				if (!checkBlockRelative(player, center, -1, 4, 1, Material.NETHER_BRICK_SLAB, bottom)) return 0;
				if (!checkBlockRelative(player, center, 1, 4, -1, Material.NETHER_BRICK_SLAB, bottom)) return 0;
				if (!checkBlockRelative(player, center, -1, 4, -1, Material.NETHER_BRICK_SLAB, bottom)) return 0;
				
				if (!checkBlockRelative(player, center, 1, 4, 0, Material.NETHER_BRICK_SLAB, top)) return 0;
				if (!checkBlockRelative(player, center, -1, 4, 0, Material.NETHER_BRICK_SLAB, top)) return 0;
				if (!checkBlockRelative(player, center, 0, 4, 1, Material.NETHER_BRICK_SLAB, top)) return 0;
				if (!checkBlockRelative(player, center, 0, 4, -1, Material.NETHER_BRICK_SLAB, top)) return 0;
				
				//y=5
				if (!checkBlockRelative(player, center, 0, 5, 0, Material.NETHER_BRICK_SLAB, bottom)) return 0;
				
				
				//Passed the test
				return 4;
			}
		} else if (checkBlockInit(center, 2, 0, 1, Material.SEA_LANTERN) || checkBlockInit(center, 1, 0, -2, Material.SEA_LANTERN) || checkBlockInit(center, -2, 0, -1, Material.SEA_LANTERN) || checkBlockInit(center, -1, 0, 2, Material.SEA_LANTERN)) {//Check for ultimate structure
			int orientation = 0;
			if (checkBlockInit(center, 1, 0, -2, Material.SEA_LANTERN)) {
				orientation = 1;
			} else if (checkBlockInit(center, -2, 0, -1, Material.SEA_LANTERN)) {
				orientation = 2;
			} else if (checkBlockInit(center, -1, 0, 2, Material.SEA_LANTERN)) {
				orientation = 3;
			}
			
			
			//y=-1
			if (!checkBlockRelative(player, center, 0, -1, 0, Material.STONE)) return 0;
			
			
			
			//y=0
			if (!checkBlockRelative(player, center, -1, 0, 1, Material.NETHER_BRICK_SLAB, bottom, orientation, true)) return 0;
			if (!checkBlockRelative(player, center, 0, 0, 1, Material.NETHER_BRICK_SLAB, bottom, orientation, true)) return 0;
			if (!checkBlockRelative(player, center, 1, 0, 1, Material.NETHER_BRICK_SLAB, bottom, orientation, true)) return 0;
			if (!checkBlockRelative(player, center, 1, 0, 0, Material.NETHER_BRICK_SLAB, bottom, orientation, true)) return 0;
			if (!checkBlockRelative(player, center, 1, 0, -1, Material.PRISMARINE_BRICK_SLAB, bottom, orientation, true)) return 0;
			if (!checkBlockRelative(player, center, 0, 0, -1, Material.PRISMARINE_BRICK_SLAB, bottom, orientation, true)) return 0;
			if (!checkBlockRelative(player, center, -1, 0, -1, Material.PRISMARINE_BRICK_SLAB, bottom, orientation, true)) return 0;
			if (!checkBlockRelative(player, center, -1, 0, 0, Material.PRISMARINE_BRICK_SLAB, bottom, orientation, true)) return 0;
			//Already checked 2, 0, 1 for sea lantern
			if (!checkBlockRelative(player, center, 2, 0, 2, Material.PRISMARINE_BRICKS, orientation)) return 0;
			if (!checkBlockRelative(player, center, 1, 0, 2, Material.PRISMARINE_BRICKS, orientation)) return 0;
			if (!checkBlockRelative(player, center, 0, 0, 2, Material.PRISMARINE_BRICKS, orientation)) return 0;
			if (!checkBlockRelative(player, center, -1, 0, 2, Material.PRISMARINE_BRICKS, orientation)) return 0;
			if (!checkBlockRelative(player, center, -2, 0, 2, Material.PRISMARINE_BRICKS, orientation)) return 0;
			if (!checkBlockRelative(player, center, -2, 0, 1, Material.PRISMARINE_BRICKS, orientation)) return 0;
			if (!checkBlockRelative(player, center, -2, 0, 0, Material.PRISMARINE_BRICKS, orientation)) return 0;
			if (!checkBlockRelative(player, center, -2, 0, -1, Material.SHROOMLIGHT, orientation)) return 0;
			if (!checkBlockRelative(player, center, -2, 0, -2, Material.NETHER_BRICKS, orientation)) return 0;
			if (!checkBlockRelative(player, center, -1, 0, -2, Material.NETHER_BRICKS, orientation)) return 0;
			if (!checkBlockRelative(player, center, 0, 0, -2, Material.NETHER_BRICKS, orientation)) return 0;
			if (!checkBlockRelative(player, center, 1, 0, -2, Material.NETHER_BRICKS, orientation)) return 0;
			if (!checkBlockRelative(player, center, 2, 0, -2, Material.NETHER_BRICKS, orientation)) return 0;
			if (!checkBlockRelative(player, center, 2, 0, -1, Material.NETHER_BRICKS, orientation)) return 0;
			if (!checkBlockRelative(player, center, 2, 0, 0, Material.NETHER_BRICKS, orientation)) return 0;
			
			if (!checkBlockRelative(player, center, 3, 0, 1, Material.PACKED_ICE, orientation)) return 0;
			if (!checkBlockRelative(player, center, 3, 0, 2, Material.PRISMARINE, orientation)) return 0;
			if (!checkBlockRelative(player, center, 2, 0, 3, Material.PRISMARINE, orientation)) return 0;
			if (!checkBlockRelative(player, center, 1, 0, 3, Material.PACKED_ICE, orientation)) return 0;
			if (!checkBlockRelative(player, center, 0, 0, 3, Material.PACKED_ICE, orientation)) return 0;
			if (!checkBlockRelative(player, center, -1, 0, 3, Material.PACKED_ICE, orientation)) return 0;
			if (!checkBlockRelative(player, center, -2, 0, 3, Material.PRISMARINE, orientation)) return 0;
			if (!checkBlockRelative(player, center, -3, 0, 2, Material.PRISMARINE, orientation)) return 0;
			if (!checkBlockRelative(player, center, -3, 0, 1, Material.PACKED_ICE, orientation)) return 0;
			if (!checkBlockRelative(player, center, -3, 0, 0, Material.PACKED_ICE, orientation)) return 0;
			if (!checkBlockRelative(player, center, -3, 0, -1, Material.SOUL_SAND, orientation)) return 0;
			if (!checkBlockRelative(player, center, -3, 0, -2, Material.RED_NETHER_BRICKS, orientation)) return 0;
			if (!checkBlockRelative(player, center, -2, 0, -3, Material.RED_NETHER_BRICKS, orientation)) return 0;
			if (!checkBlockRelative(player, center, -1, 0, -3, Material.SOUL_SAND, orientation)) return 0;
			if (!checkBlockRelative(player, center, 0, 0, -3, Material.SOUL_SAND, orientation)) return 0;
			if (!checkBlockRelative(player, center, 1, 0, -3, Material.SOUL_SAND, orientation)) return 0;
			if (!checkBlockRelative(player, center, 2, 0, -3, Material.RED_NETHER_BRICKS, orientation)) return 0;
			if (!checkBlockRelative(player, center, 3, 0, -2, Material.RED_NETHER_BRICKS, orientation)) return 0;
			if (!checkBlockRelative(player, center, 3, 0, -1, Material.SOUL_SAND, orientation)) return 0;
			if (!checkBlockRelative(player, center, 3, 0, 0, Material.SOUL_SAND, orientation)) return 0;
			
			if (!checkBlockRelative(player, center, 4, 0, -1, Material.RED_NETHER_BRICKS, orientation)) return 0;
			if (!checkBlockRelative(player, center, 4, 0, 0, Material.RED_NETHER_BRICKS, orientation)) return 0;
			if (!checkBlockRelative(player, center, 4, 0, 1, Material.RED_NETHER_BRICKS, orientation)) return 0;
			if (!checkBlockRelative(player, center, 1, 0, 4, Material.PRISMARINE, orientation)) return 0;
			if (!checkBlockRelative(player, center, 0, 0, 4, Material.PRISMARINE, orientation)) return 0;
			if (!checkBlockRelative(player, center, -1, 0, 4, Material.PRISMARINE, orientation)) return 0;
			if (!checkBlockRelative(player, center, -4, 0, -1, Material.PRISMARINE, orientation)) return 0;
			if (!checkBlockRelative(player, center, -4, 0, 0, Material.PRISMARINE, orientation)) return 0;
			if (!checkBlockRelative(player, center, -4, 0, 1, Material.PRISMARINE, orientation)) return 0;
			if (!checkBlockRelative(player, center, -1, 0, -4, Material.RED_NETHER_BRICKS, orientation)) return 0;
			if (!checkBlockRelative(player, center, 0, 0, -4, Material.RED_NETHER_BRICKS, orientation)) return 0;
			if (!checkBlockRelative(player, center, 1, 0, -4, Material.RED_NETHER_BRICKS, orientation)) return 0;
			
			
			
			//y=1
			if (!checkBlockRelative(player, center, 2, 1, 2, Material.PRISMARINE_BRICKS, orientation)) return 0;
			if (!checkBlockRelative(player, center, -2, 1, 2, Material.PRISMARINE_BRICKS, orientation)) return 0;
			if (!checkBlockRelative(player, center, 2, 1, -2, Material.NETHER_BRICKS, orientation)) return 0;
			if (!checkBlockRelative(player, center, -2, 1, -2, Material.NETHER_BRICKS, orientation)) return 0;
			
			
			
			//y=2
			if (!checkBlockRelative(player, center, 2, 2, 2, Material.PRISMARINE_WALL, orientation)) return 0;
			if (!checkBlockRelative(player, center, -2, 2, 2, Material.PRISMARINE_WALL, orientation)) return 0;
			if (!checkBlockRelative(player, center, 2, 2, -2, Material.NETHER_BRICK_WALL, orientation)) return 0;
			if (!checkBlockRelative(player, center, -2, 2, -2, Material.NETHER_BRICK_WALL, orientation)) return 0;
			
			
			
			//y=3
			if (!checkBlockRelative(player, center, 2, 3, 2, Material.PRISMARINE_WALL, orientation)) return 0;
			if (!checkBlockRelative(player, center, -2, 3, 2, Material.PRISMARINE_WALL, orientation)) return 0;
			if (!checkBlockRelative(player, center, 2, 3, -2, Material.NETHER_BRICK_WALL, orientation)) return 0;
			if (!checkBlockRelative(player, center, -2, 3, -2, Material.NETHER_BRICK_WALL, orientation)) return 0;
			
			
			
			//y=4
			if (!checkBlockRelative(player, center, 2, 4, 2, Material.PRISMARINE_WALL, orientation)) return 0;
			if (!checkBlockRelative(player, center, -2, 4, 2, Material.PRISMARINE_WALL, orientation)) return 0;
			if (!checkBlockRelative(player, center, 2, 4, -2, Material.NETHER_BRICK_WALL, orientation)) return 0;
			if (!checkBlockRelative(player, center, -2, 4, -2, Material.NETHER_BRICK_WALL, orientation)) return 0;
			
			if (!checkBlockRelative(player, center, 1, 4, 2, Material.DARK_PRISMARINE_STAIRS, orientation)) return 0;
			if (!checkBlockRelative(player, center, -1, 4, 2, Material.DARK_PRISMARINE_STAIRS, orientation)) return 0;
			if (!checkBlockRelative(player, center, -2, 4, 1, Material.DARK_PRISMARINE_STAIRS, orientation)) return 0;
			if (!checkBlockRelative(player, center, -2, 4, -1, Material.NETHER_BRICK_STAIRS, orientation)) return 0;
			if (!checkBlockRelative(player, center, -1, 4, -2, Material.NETHER_BRICK_STAIRS, orientation)) return 0;
			if (!checkBlockRelative(player, center, 1, 4, -2, Material.NETHER_BRICK_STAIRS, orientation)) return 0;
			if (!checkBlockRelative(player, center, 2, 4, -1, Material.NETHER_BRICK_STAIRS, orientation)) return 0;
			if (!checkBlockRelative(player, center, 2, 4, 1, Material.DARK_PRISMARINE_STAIRS, orientation)) return 0;
			
			
			
			//y=5
			if (!checkBlockRelative(player, center, 2, 5, -2, Material.NETHER_BRICK_SLAB, bottom, orientation)) return 0;
			if (!checkBlockRelative(player, center, 2, 5, -1, Material.NETHER_BRICKS, orientation)) return 0;
			if (!checkBlockRelative(player, center, 2, 5, 0, Material.NETHER_BRICKS, orientation)) return 0;
			if (!checkBlockRelative(player, center, 2, 5, 1, Material.DARK_PRISMARINE, orientation)) return 0;
			if (!checkBlockRelative(player, center, 2, 5, 2, Material.DARK_PRISMARINE_SLAB, bottom, orientation)) return 0;
			if (!checkBlockRelative(player, center, 1, 5, 2, Material.DARK_PRISMARINE, orientation)) return 0;
			if (!checkBlockRelative(player, center, 0, 5, 2, Material.SEA_LANTERN, orientation)) return 0;
			if (!checkBlockRelative(player, center, -1, 5, 2, Material.DARK_PRISMARINE, orientation)) return 0;
			if (!checkBlockRelative(player, center, -2, 5, 2, Material.DARK_PRISMARINE_SLAB, bottom, orientation)) return 0;
			if (!checkBlockRelative(player, center, -2, 5, 1, Material.DARK_PRISMARINE, orientation)) return 0;
			if (!checkBlockRelative(player, center, -2, 5, 0, Material.DARK_PRISMARINE, orientation)) return 0;
			if (!checkBlockRelative(player, center, -2, 5, -1, Material.NETHER_BRICKS, orientation)) return 0;
			if (!checkBlockRelative(player, center, -2, 5, -2, Material.NETHER_BRICK_SLAB, bottom, orientation)) return 0;
			if (!checkBlockRelative(player, center, -1, 5, -2, Material.NETHER_BRICKS, orientation)) return 0;
			if (!checkBlockRelative(player, center, 0, 5, -2, Material.SHROOMLIGHT, orientation)) return 0;
			if (!checkBlockRelative(player, center, 1, 5, -2, Material.NETHER_BRICKS, orientation)) return 0;
			
			
			
			//y=6
			if (!checkBlockRelative(player, center, 0, 6, -2, Material.NETHER_BRICK_SLAB, bottom, orientation)) return 0;
			if (!checkBlockRelative(player, center, 2, 6, 0, Material.NETHER_BRICK_SLAB, bottom, orientation)) return 0;
			if (!checkBlockRelative(player, center, 0, 6, 2, Material.DARK_PRISMARINE_SLAB, bottom, orientation)) return 0;
			if (!checkBlockRelative(player, center, -2, 6, 0, Material.DARK_PRISMARINE_SLAB, bottom, orientation)) return 0;
			
			if (!checkBlockRelative(player, center, 1, 6, 1, Material.NETHER_BRICK_SLAB, bottom, orientation)) return 0;
			if (!checkBlockRelative(player, center, -1, 6, 1, Material.NETHER_BRICK_SLAB, bottom, orientation)) return 0;
			if (!checkBlockRelative(player, center, 1, 6, -1, Material.DARK_PRISMARINE_SLAB, bottom, orientation)) return 0;
			if (!checkBlockRelative(player, center, -1, 6, -1, Material.DARK_PRISMARINE_SLAB, bottom, orientation)) return 0;
			
			if (!checkBlockRelative(player, center, 1, 6, 0, Material.DARK_PRISMARINE, orientation)) return 0;
			if (!checkBlockRelative(player, center, -1, 6, 0, Material.NETHER_BRICKS, orientation)) return 0;
			if (!checkBlockRelative(player, center, 0, 6, 1, Material.NETHER_BRICKS, orientation)) return 0;
			if (!checkBlockRelative(player, center, 0, 6, -1, Material.DARK_PRISMARINE, orientation)) return 0;
			
			if (!checkBlockRelative(player, center, 0, 6, 0, Material.AIR)) return 0;
			
			//Passed the test
			return 5;
		}
		return 0;
	}
	
	private Boolean checkBlockInit(Location center, int x, int y, int z, Material mat) {
		Location newLoc = new Location(center.getWorld(), center.getBlockX() + x, center.getBlockY() + y, center.getBlockZ() + z);
		if (newLoc.getWorld().getBlockAt(newLoc).getType().equals(mat)) {
			return true;
		}
		return false;
	}
	
	private Boolean checkBlockRelative(Player player, Location center, int x, int y, int z, Material mat) {
		Location newLoc = new Location(center.getWorld(), center.getBlockX() + x, center.getBlockY() + y, center.getBlockZ() + z);
		if (newLoc.getWorld().getBlockAt(newLoc).getType().equals(mat)) {
			return true;
		}
		player.sendMessage(ChatColor.YELLOW + "Missing block " + mat.name() + " at: " + newLoc.getBlockX() + ", " + newLoc.getBlockY() + ", " + newLoc.getBlockZ() + ".");
		return false;
	}
	
	private Boolean checkBlockRelative(Player player, Location center, int x, int y, int z, Material mat, Slab.Type slabPos) {
		Location newLoc = new Location(center.getWorld(), center.getBlockX() + x, center.getBlockY() + y, center.getBlockZ() + z);
		if (newLoc.getWorld().getBlockAt(newLoc).getType().equals(mat)) {
			if (newLoc.getWorld().getBlockAt(newLoc).getBlockData() instanceof Slab) {
				Slab slab = (Slab) newLoc.getWorld().getBlockAt(newLoc).getBlockData();
				if (slab.getType().equals(slabPos)) {
					return true;
				}
			} else {
				return true;
			}
		}
		player.sendMessage(ChatColor.YELLOW + "Missing block " + mat.name() + " " + slabPos.name() + " at: " + newLoc.getBlockX() + ", " + newLoc.getBlockY() + ", " + newLoc.getBlockZ() + ".");
		return false;
	}
	
	
	
	
	
	
	
	private Boolean checkBlockRelative(Player player, Location center, int x, int y, int z, Material mat, int orientation) {//0 is default, so do nothing
		
		int helper = x;
		switch (orientation) {
			case 1:
				x = z;
				z = -helper;
				break;
			case 2:
				x = -x;
				z = -z;
				break;
			case 3:
				x = -z;
				z = helper;
				break;
		}
		Location newLoc = new Location(center.getWorld(), center.getBlockX() + x, center.getBlockY() + y, center.getBlockZ() + z);
		
		if (newLoc.getWorld().getBlockAt(newLoc).getType().equals(mat)) {
			return true;
		}
		
		player.sendMessage(ChatColor.YELLOW + "Missing block " + mat.name() + " at: " + newLoc.getBlockX() + ", " + newLoc.getBlockY() + ", " + newLoc.getBlockZ() + ".");
		return false;
	}
	
	private Boolean checkBlockRelative(Player player, Location center, int x, int y, int z, Material mat, Slab.Type slabPos, int orientation) {
		
		int helper = x;
		switch (orientation) {
			case 1:
				x = z;
				z = -helper;
				break;
			case 2:
				x = -x;
				z = -z;
				break;
			case 3:
				x = -z;
				z = helper;
				break;
		}
		
		Location newLoc = new Location(center.getWorld(), center.getBlockX() + x, center.getBlockY() + y, center.getBlockZ() + z);
		
		if (newLoc.getWorld().getBlockAt(newLoc).getType().equals(mat)) {
			if (newLoc.getWorld().getBlockAt(newLoc).getBlockData() instanceof Slab) {
				Slab slab = (Slab) newLoc.getWorld().getBlockAt(newLoc).getBlockData();
				if (slab.getType().equals(slabPos)) {
					return true;
				}
			} else {
				return true;
			}
		}
		
		player.sendMessage(ChatColor.YELLOW + "Missing block " + mat.name() + " " + slabPos.name() + " at: " + newLoc.getBlockX() + ", " + newLoc.getBlockY() + ", " + newLoc.getBlockZ() + ".");
		return false;
	}
	
	
	private Boolean checkBlockRelative(Player player, Location center, int x, int y, int z, Material mat, Slab.Type slabPos, int orientation, boolean waterlogged) {
		
		int helper = x;
		switch (orientation) {
			case 1:
				x = z;
				z = -helper;
				break;
			case 2:
				x = -x;
				z = -z;
				break;
			case 3:
				x = -z;
				z = helper;
				break;
		}
		
		Location newLoc = new Location(center.getWorld(), center.getBlockX() + x, center.getBlockY() + y, center.getBlockZ() + z);
		
		if (newLoc.getWorld().getBlockAt(newLoc).getType().equals(mat)) {
			if (newLoc.getWorld().getBlockAt(newLoc).getBlockData() instanceof Slab) {
				Slab slab = (Slab) newLoc.getWorld().getBlockAt(newLoc).getBlockData();
				if (slab.getType().equals(slabPos) && (slab.isWaterlogged() == waterlogged)) {
					return true;
				}
			} else {
				return true;
			}
		}
		
		player.sendMessage(ChatColor.YELLOW + "Missing waterlogged block " + mat.name() + " " + slabPos.name() + " at: " + newLoc.getBlockX() + ", " + newLoc.getBlockY() + ", " + newLoc.getBlockZ() + ".");
		return false;
	}
	
	
	
}
