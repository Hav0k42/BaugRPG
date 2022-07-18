package org.baugindustries.baugrpg.customitems.dwarves.radiant_metallurgist.advanced;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.inventory.ItemStack;

public class EmblemOfTheForgeListener implements Listener {
	private Main plugin;
	
	public EmblemOfTheForgeListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClick(FurnaceExtractEvent event) {
		Player player = event.getPlayer();
		if (!Recipes.EMBLEM_OF_THE_FORGE.playerIsCarrying(player, plugin)) return;
		
		player.getInventory().addItem(new ItemStack(event.getItemType(), event.getItemAmount()));
	}

}
