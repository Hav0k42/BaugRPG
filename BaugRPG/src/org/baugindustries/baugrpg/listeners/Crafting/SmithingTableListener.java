package org.baugindustries.baugrpg.listeners.Crafting;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class SmithingTableListener implements Listener {
	private Main plugin;
	
	public SmithingTableListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPrepareSmithing(PrepareSmithingEvent event) {
		if (event.getResult() == null) return;
		
		boolean match = false;
		for (Recipes recipe : Recipes.values()) {
			if (recipe.matches(plugin, event.getInventory().getItem(0)) || recipe.matches(plugin, event.getInventory().getItem(1))) {
				match = true;
				break;
			}
		}
		
		if (match) {
			Player player = (Player) event.getViewers().get(0);
			player.sendMessage(ChatColor.RED + "This item cannot be used at a smithing table.");
			event.setResult(new ItemStack(Material.AIR));
		}
	}

}
