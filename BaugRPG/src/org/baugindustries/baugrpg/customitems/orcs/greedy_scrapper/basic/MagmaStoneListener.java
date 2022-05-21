package org.baugindustries.baugrpg.customitems.orcs.greedy_scrapper.basic;

import java.util.ArrayList;
import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;

import net.md_5.bungee.api.ChatColor;

public class MagmaStoneListener  implements Listener {
	private Main plugin;
	
	private List<Block> tempBlocks = new ArrayList<Block>();
	
	public MagmaStoneListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		
		if (event.getTo().equals(event.getFrom())) return;
		
		if (!Recipes.MAGMA_STONE.playerIsCarrying(player, plugin)) return;
		
		if (!(player.getLocation().getBlock().getRelative(0, -1, 0).getType().equals(Material.LAVA) || player.getLocation().getBlock().getRelative(0, -1, 0).getType().equals(Material.MAGMA_BLOCK))) return;
		
		Block block = player.getLocation().getBlock().getRelative(0, -1, 0);
		
		for (int x = -2; x < 3; x++) {
			for (int z = -2; z < 3; z++) {
				if (!(Math.abs(x) == 2 && Math.abs(z) == 2)) {
					if (block.getRelative(x, 0, z).getType().equals(Material.LAVA)) {
						if (!tempBlocks.contains(block.getRelative(x, 0, z))) {
							tempMakeMagma(block.getRelative(x, 0, z));
							tempBlocks.add(block.getRelative(x, 0, z));
						}
					}
				}
			}
		}
		
	}
	
	private void tempMakeMagma(Block block) {
		long time = (long) ((Math.random() * 40) + 100);
		block.setType(Material.MAGMA_BLOCK);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			  public void run() {
				  block.setType(Material.LAVA);
				  tempBlocks.remove(block);
			  }
		 }, time);
	}
	
	@EventHandler
	public void onBreakTempMagma(BlockBreakEvent event) {
		if (tempBlocks.contains(event.getBlock())) event.setCancelled(true);
	}
	
	@EventHandler
	public void onUseEgg(PlayerInteractEvent event) {
		if (event.getHand() == null) return;
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!(Recipes.MAGMA_STONE.matches(plugin, event.getPlayer().getInventory().getItemInMainHand()))) return;
			event.getPlayer().sendMessage(ChatColor.RED + "This item cannot be used like that");
	    } else {
			if (!(Recipes.MAGMA_STONE.matches(plugin, event.getPlayer().getInventory().getItemInOffHand()))) return;
			event.getPlayer().sendMessage(ChatColor.RED + "This item cannot be used like that");
	    }
		
		event.setCancelled(true);
	}

}
