package org.baugindustries.baugrpg.customitems.dwarves.gilded_miner.expert;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class MultiToolListener implements Listener {
	private Main plugin;
	
	public MultiToolListener(Main plugin) {
		this.plugin = plugin;
	}
	
	private void changeType(Player player, Material mat) {
		player.getInventory().getItemInMainHand().setType(mat);
	}
	
	@EventHandler
	public void onInventoryClick(PlayerInteractEvent event) {
		if (!event.getAction().equals(Action.LEFT_CLICK_BLOCK)) return;
		Player player = event.getPlayer();
		if (!Recipes.MULTITOOL.matches(plugin, player.getInventory().getItemInMainHand())) return;
		
		if (Tag.MINEABLE_PICKAXE.getValues().contains(event.getClickedBlock().getType())) {
			changeType(player, Material.DIAMOND_PICKAXE);
		} else if (Tag.MINEABLE_AXE.getValues().contains(event.getClickedBlock().getType())) {
			changeType(player, Material.DIAMOND_AXE);
		} else if (Tag.MINEABLE_SHOVEL.getValues().contains(event.getClickedBlock().getType())) {
			changeType(player, Material.DIAMOND_SHOVEL);
		} else if (Tag.MINEABLE_HOE.getValues().contains(event.getClickedBlock().getType())) {
			changeType(player, Material.DIAMOND_HOE);
		}
		
	}

}
