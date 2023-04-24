package org.baugindustries.baugrpg.religion;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockConversionListener implements Listener {
	private Main plugin;
	
	Material[] convertableBlocks = {
			Material.OAK_LOG, 
			Material.DARK_OAK_LOG, 
			Material.BIRCH_LOG, 
			Material.COAL_ORE, 
			Material.DEEPSLATE_COAL_ORE, 
			Material.IRON_ORE, 
			Material.DEEPSLATE_IRON_ORE, 
			Material.COPPER_ORE, 
			Material.DEEPSLATE_COPPER_ORE, 
			Material.GOLD_ORE, 
			Material.DEEPSLATE_GOLD_ORE, 
			Material.DIAMOND_ORE, 
			Material.DEEPSLATE_DIAMOND_ORE, 
			Material.LAPIS_ORE, 
			Material.DEEPSLATE_LAPIS_ORE, 
			Material.EMERALD_ORE, 
			Material.DEEPSLATE_EMERALD_ORE, 
			Material.REDSTONE_ORE, 
			Material.DEEPSLATE_REDSTONE_ORE, 
			Material.NETHER_QUARTZ_ORE, 
			Material.NETHER_GOLD_ORE, 
			Material.GRASS,
			Material.CRIMSON_STEM,
			Material.WARPED_STEM
			};
	
	public BlockConversionListener(Main plugin) {
		this.plugin = plugin;
	}


	@EventHandler
	public void onMineOre(BlockBreakEvent event) {
		String overworldName = plugin.getServer().getWorlds().get(0).getName();
		String worldName = event.getBlock().getWorld().getName();
		if (!worldName.contains(overworldName + "_baugreligions_")) return;
		
		Material blockMat = event.getBlock().getType();
		boolean match = false;
		for (Material mat : convertableBlocks) {
			if (mat.equals(blockMat)) {
				match = true;
				break;
			}
		}
		
		if (!match) return;
		
		if (blockMat.equals(Material.DEEPSLATE_IRON_ORE)) blockMat = Material.IRON_ORE;
		else if (blockMat.equals(Material.DEEPSLATE_COAL_ORE)) blockMat = Material.COAL_ORE;
		else if (blockMat.equals(Material.DEEPSLATE_COPPER_ORE)) blockMat = Material.COPPER_ORE;
		else if (blockMat.equals(Material.DEEPSLATE_REDSTONE_ORE)) blockMat = Material.REDSTONE_ORE;
		else if (blockMat.equals(Material.DEEPSLATE_LAPIS_ORE)) blockMat = Material.LAPIS_ORE;
		else if (blockMat.equals(Material.DEEPSLATE_GOLD_ORE)) blockMat = Material.GOLD_ORE;
		else if (blockMat.equals(Material.DEEPSLATE_DIAMOND_ORE)) blockMat = Material.DIAMOND_ORE;
		else if (blockMat.equals(Material.DEEPSLATE_EMERALD_ORE)) blockMat = Material.EMERALD_ORE;
		else if (blockMat.equals(Material.DARK_OAK_LOG)) blockMat = Material.OAK_LOG;
		else if (blockMat.equals(Material.BIRCH_LOG)) blockMat = Material.OAK_LOG;
		
		ItemStack baseDrop = null;
		ItemStack rareDrop = null;
		ItemStack rarestDrop = null;
		
		if (worldName.equals(overworldName + "_baugreligions_aeriesqa")) {
			if (blockMat.equals(Material.IRON_ORE)) {
				baseDrop = Recipes.IRON_PLATE.getResult(plugin);
				rareDrop = Recipes.HARDENED_PLATE.getResult(plugin);
				rarestDrop = Recipes.STEEL_PLATE.getResult(plugin);
			} else if (blockMat.equals(Material.COAL_ORE)) {
				baseDrop = Recipes.MESH.getResult(plugin);
				rareDrop = Recipes.HARDENED_MESH.getResult(plugin);
				rarestDrop = Recipes.STEEL_MESH.getResult(plugin);
			} else if (blockMat.equals(Material.GRASS)) {
				baseDrop = new ItemStack(Material.GRASS);
				rareDrop = new ItemStack(Material.GRASS);
				rarestDrop = Recipes.DRY_GRASS.getResult(plugin);
			} else {
				return;
			}
			event.setCancelled(true);
		} else if (worldName.equals(overworldName + "_baugreligions_taevas")) {
			if (blockMat.equals(Material.OAK_LOG)) {
				baseDrop = Recipes.ENRICHED_LOGS.getResult(plugin);
			} else if (blockMat.equals(Material.IRON_ORE)) {
				baseDrop = Recipes.LUNAR_DEBRIS.getResult(plugin);
				rareDrop = Recipes.METEORITE.getResult(plugin);
				rarestDrop = Recipes.NEW_MOON_FRAGMENT.getResult(plugin);
			} else if (blockMat.equals(Material.REDSTONE_ORE)) {
				baseDrop = Recipes.STARDUST.getResult(plugin);
				rareDrop = Recipes.STARDUST.getResult(plugin);
				rarestDrop = Recipes.BLOOD_MOON_FRAGMENT.getResult(plugin);
			} else if (blockMat.equals(Material.COAL_ORE)) {
				baseDrop = new ItemStack(Material.COAL);
				rareDrop = Recipes.DARK_MATTER.getResult(plugin);
			} else {
				return;
			}
			event.setCancelled(true);
		} else if (worldName.equals(overworldName + "_baugreligions_velaruhm")) {
			if (blockMat.equals(Material.EMERALD_ORE) || blockMat.equals(Material.DIAMOND_ORE)) {
				if (Math.random() < 0.25) {
					baseDrop = Recipes.RUBY.getResult(plugin);
				} else if (Math.random() < 1.0 / 3.0) {
					baseDrop = Recipes.OPAL.getResult(plugin);
				} else if (Math.random() < 0.5) {
					baseDrop = Recipes.ROYAL_AZEL.getResult(plugin);
				} else {
					baseDrop = Recipes.SAPPHIRE.getResult(plugin);
				}
				
				if (Math.random() < 0.5) {
					rareDrop = Recipes.TURQUOISE.getResult(plugin);
				} else {
					rareDrop = Recipes.TOPAZ.getResult(plugin);
				}
				
				rarestDrop = Recipes.GEM_CLUSTER.getResult(plugin);
			} else if (blockMat.equals(Material.GOLD_ORE)) {
				baseDrop = Recipes.PUMICE.getResult(plugin);
				
				if (Math.random() < 0.5) {
					rareDrop = Recipes.BRASS.getResult(plugin);
				} else {
					rareDrop = Recipes.BRONZE.getResult(plugin);
				}
				
				rarestDrop = Recipes.GOLD_DUST.getResult(plugin);
			} else if (blockMat.equals(Material.IRON_ORE)) {
				if (Math.random() < 1.0 / 3.0) {
					baseDrop = Recipes.STEEL.getResult(plugin);
				} else if (Math.random() < 0.5) {
					baseDrop = Recipes.ZINC.getResult(plugin);
				} else {
					baseDrop = Recipes.ALUMINUM.getResult(plugin);
				}
				
				rareDrop = Recipes.LEAD.getResult(plugin);

				if (Math.random() < 0.5) {
					rarestDrop = Recipes.TITANIUM.getResult(plugin);
				} else {
					rarestDrop = Recipes.PLATINUM.getResult(plugin);
				}
				
			} else {
				return;
			}
			event.setCancelled(true);
		} else {
			if (blockMat.equals(Material.NETHER_GOLD_ORE)) {
				baseDrop = Recipes.HELLSTONE.getResult(plugin);
				
				rareDrop = Recipes.PYROCLASTIC_INGOT.getResult(plugin);
				
				rarestDrop = Recipes.MAGMA_CRYSTAL.getResult(plugin);
			} else if (blockMat.equals(Material.NETHER_QUARTZ_ORE)) {
				baseDrop = Recipes.SCRAP.getResult(plugin);
				
				rareDrop = Recipes.BOLTS.getResult(plugin);
				
				rarestDrop = Recipes.GALVINIZED_ALLOY.getResult(plugin);
			} else if (blockMat.equals(Material.WARPED_STEM) || blockMat.equals(Material.CRIMSON_STEM)) {
				baseDrop = Recipes.ETHEREAL_WOOD.getResult(plugin);
			} else {
				return;
			}
			event.setCancelled(true);
		}
		
		ItemStack drop = null;
		
		double odds = Math.random();
		if (odds < 0.7) {
			drop = baseDrop;
		} else if ((odds < 0.9 && rareDrop != null) || rarestDrop == null) {
			drop = rareDrop;
		} else {
			drop = rarestDrop;
		}
		
		event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation().add(0.5, 0.5, 0.5), drop);
		event.getBlock().breakNaturally(new ItemStack(Material.AIR));
	}
	
}
