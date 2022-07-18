package org.baugindustries.baugrpg.customitems;

import java.util.Arrays;
import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class SoulboundItemsListener implements Listener {
	private Main plugin;
	
	final List<Recipes> soulboundItems = Arrays.asList(Recipes.SOULBOUND_AXE, Recipes.SOULBOUND_CHESTPIECE, Recipes.SOULBOUND_CROWN, Recipes.SOULBOUND_ELYTRA, Recipes.SOULBOUND_GREAVES, Recipes.SOULBOUND_HOE, Recipes.SOULBOUND_LEGGINGS, Recipes.SOULBOUND_PICKAXE, Recipes.SOULBOUND_SHOVEL, Recipes.SOULBOUND_SWORD);
	
	public SoulboundItemsListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(PlayerDeathEvent event) {
		Player player = event.getEntity();
		
		for (int i = 0; i < player.getInventory().getSize(); i++) {
			ItemStack item = player.getInventory().getItem(i);
			for (Recipes recipe : soulboundItems) {
				if (recipe.matches(plugin, item)) {
					event.getDrops().remove(item);
					int index = i;
					plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						public void run() {
							player.getInventory().setItem(index, item);
						}
					}, 1L);
				}
			}
		}
	}

}
