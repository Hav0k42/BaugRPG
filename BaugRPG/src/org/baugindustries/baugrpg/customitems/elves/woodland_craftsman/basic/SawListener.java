package org.baugindustries.baugrpg.customitems.elves.woodland_craftsman.basic;

import java.util.ArrayList;
import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class SawListener implements Listener {
	
	Main plugin;
	
	public SawListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void BreakLog(BlockBreakEvent event) {

		Player player = event.getPlayer();

		
		if (!(Recipes.SAW.matches(plugin, player.getInventory().getItemInMainHand()))) return;
			
		Block block = event.getBlock();
		Material treeType = null;
		Material[] logs = {Material.ACACIA_LOG, Material.BIRCH_LOG, Material.DARK_OAK_LOG, Material.JUNGLE_LOG, Material.OAK_LOG, Material.SPRUCE_LOG};
		for (Material type : logs) {
			if (type.equals(block.getType())) {
				treeType = type;
				break;
			}
		}
		
		
		if (treeType == null) return;//Didn't break a log.
		
		List<Block> logLocations = new ArrayList<Block>();
		logLocations.add(block);
		logLocations = getAdjacentLogs(logLocations, block, treeType);
		
		if (!(logLocations.get(0).getBlockData() instanceof Leaves)) return;
		
		
		logLocations.remove(0);
		int totalLogs = logLocations.size();
		
		logLocations.forEach(goneBlock -> {
			goneBlock.setType(Material.AIR);
		});
		
		int stacks = totalLogs / 64;
		if (totalLogs % 64 != 0) {
			player.getWorld().dropItem(block.getLocation(), new ItemStack(treeType, totalLogs % 64));
		}
		for (int i = 0; i < stacks; i ++) {
			player.getWorld().dropItem(block.getLocation(), new ItemStack(treeType, 64));
		}
		
		
		
		
	}
	
	
	private List<Block> getAdjacentLogs(List<Block> foundLogs, Block currentBlock, Material logMat) {
		if (foundLogs.size() > 128) return foundLogs;
		
		for (int x = -1; x < 2; x++) {
			for (int z = -1; z < 2; z++) {
				for (int y = 0; y < 2; y++) {
					Block newBlock = currentBlock.getRelative(x, y, z);
					if (newBlock.getType().equals(logMat)) {
						if (!foundLogs.contains(newBlock)) {
							foundLogs.add(newBlock);
							foundLogs = getAdjacentLogs(foundLogs, newBlock, logMat);
						}
					} else if (newBlock.getBlockData() instanceof Leaves) {
						Leaves leaf = (Leaves) newBlock.getBlockData();
						if (!(foundLogs.get(0).getBlockData() instanceof Leaves) && !leaf.isPersistent()) {
							foundLogs.set(0, newBlock);
						}
					}
					
				}
			}
		}
		
		
		return foundLogs;
	}

}
