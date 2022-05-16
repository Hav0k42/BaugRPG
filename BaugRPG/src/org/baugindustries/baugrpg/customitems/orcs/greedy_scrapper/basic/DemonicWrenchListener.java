package org.baugindustries.baugrpg.customitems.orcs.greedy_scrapper.basic;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

import net.md_5.bungee.api.ChatColor;

public class DemonicWrenchListener implements Listener {
	
	Main plugin;
	
	public DemonicWrenchListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void clickSpawner(PlayerInteractEvent event) {

		Player player = event.getPlayer();
		
		if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!(Recipes.DEMONIC_WRENCH.matches(plugin, player.getInventory().getItemInMainHand()))) return;
	    } else {
			if (!(Recipes.DEMONIC_WRENCH.matches(plugin, player.getInventory().getItemInOffHand()))) return;
	    }
		
		event.setCancelled(true);
		
		if (!event.getClickedBlock().getType().equals(Material.SPAWNER)) return;
		
		CreatureSpawner spawner = (CreatureSpawner) event.getClickedBlock().getState();
		
		String mobName = "";
		String[] segments = spawner.getSpawnedType().toString().split("_");
		
		for (String segment : segments) {
			mobName = mobName + segment.substring(0, 1) + segment.substring(1).toLowerCase() + " ";
		}
		
		ItemStack spawnerItem = plugin.createItem(Material.SPAWNER, 1, ChatColor.YELLOW + mobName + "Spawner");
		BlockStateMeta itemMeta = (BlockStateMeta)spawnerItem.getItemMeta();
		itemMeta.setBlockState(spawner);
		spawnerItem.setItemMeta(itemMeta);
		
		player.getWorld().dropItem(event.getClickedBlock().getLocation(), spawnerItem);
		event.getClickedBlock().setType(Material.AIR);
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			player.getInventory().setItemInMainHand(null);
	    } else {
			player.getInventory().setItemInOffHand(null);
	    }
		
	}

}
