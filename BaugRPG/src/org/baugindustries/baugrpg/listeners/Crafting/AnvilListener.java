package org.baugindustries.baugrpg.listeners.Crafting;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;

import org.bukkit.ChatColor;

public class AnvilListener implements Listener {
	private Main plugin;
	
	public AnvilListener(Main plugin) {
		this.plugin = plugin;
	}

	
	@EventHandler
	public void onEnchantSpear(PrepareAnvilEvent event) {
		if (event.getResult() == null) return;
		
		boolean match = false;
		for (Recipes recipe : Recipes.values()) {
			if (recipe.matches(plugin, event.getInventory().getItem(1))) {
				match = true;
				break;
			}
		}
		
		if (match) {
			Player player = (Player) event.getViewers().get(0);
			player.sendMessage(ChatColor.RED + "This item cannot be combined with other items.");
			event.setResult(new ItemStack(Material.AIR));
		}
	}
}
