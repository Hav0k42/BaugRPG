package org.baugindustries.baugrpg.customitems.dwarves.gilded_miner.intermediate;

import java.util.Collection;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class DrillListener implements Listener {
	private Main plugin;
	
	public DrillListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPickUpOre(BlockBreakEvent event) {
		Player player = event.getPlayer();
		
		Material[] ores = {Material.SAND, Material.RED_SAND, Material.COBBLESTONE, Material.SANDSTONE, Material.RED_SANDSTONE, Material.STONE, Material.QUARTZ_BLOCK, Material.NETHERRACK, Material.NETHER_BRICKS, Material.BASALT, Material.CLAY, Material.STONE_BRICKS, Material.POLISHED_BLACKSTONE_BRICKS, Material.COBBLED_DEEPSLATE, Material.DEEPSLATE_BRICKS, Material.DEEPSLATE_TILES, Material.WET_SPONGE};
		Material[] smelted = {Material.GLASS, Material.GLASS, Material.STONE, Material.SMOOTH_SANDSTONE, Material.SMOOTH_RED_SANDSTONE, Material.SMOOTH_STONE, Material.SMOOTH_QUARTZ, Material.NETHER_BRICK, Material.CRACKED_NETHER_BRICKS, Material.SMOOTH_BASALT, Material.TERRACOTTA, Material.CRACKED_STONE_BRICKS, Material.CRACKED_POLISHED_BLACKSTONE_BRICKS, Material.DEEPSLATE, Material.CRACKED_DEEPSLATE_BRICKS, Material.CRACKED_DEEPSLATE_TILES, Material.SPONGE};
		
		boolean match = false;
		int index = 0;
		Collection<ItemStack> drops = event.getBlock().getDrops(player.getInventory().getItemInMainHand(), player);
		ItemStack[] dropsArray = drops.toArray(new ItemStack[drops.size()]);
		if (dropsArray.length == 0) return;
		
		for (int i = 0; i < ores.length; i++) {
			if (dropsArray[0].getType().equals(ores[i])) {
				match = true;
				index = i;
				break;
			}
		}
		
		if (!match) return;
		
		if (!Recipes.DRILL.playerIsCarrying(player, plugin)) return;
		
		ItemStack coal = null;
		int coalIndex = 0;
		for (int i = 0; i < player.getInventory().getSize(); i++) {
			if (player.getInventory().getItem(i) != null && player.getInventory().getItem(i).getType().equals(Material.COAL)) {
				coal = player.getInventory().getItem(i);
				coalIndex = i;
			}
		}
		if (coal == null) return;
		
		if (Math.random() < 0.125) {
			coal.setAmount(coal.getAmount() - 1);
			player.getInventory().setItem(coalIndex, coal);
		}
		
		event.setDropItems(false);
		
		
		int amount = dropsArray[0].getAmount();
		
		player.getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(smelted[index], amount));
		
		
		
		
		
	}

}
