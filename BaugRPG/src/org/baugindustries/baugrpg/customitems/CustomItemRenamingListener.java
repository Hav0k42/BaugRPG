package org.baugindustries.baugrpg.customitems;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;

import org.bukkit.ChatColor;

public class CustomItemRenamingListener implements Listener {
	private Main plugin;
	
	public CustomItemRenamingListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onRenameCustomItem(PrepareAnvilEvent event) {
		boolean matched = false;
		for (Recipes recipe : Recipes.values()) {
			if (recipe.matches(plugin, event.getInventory().getItem(0))) {
				matched = true;
			}
		}
		
		if (!matched) return;
		
		if (event.getResult() == null) return;
		
		if (event.getInventory().getItem(0).getItemMeta().getDisplayName().equals(event.getResult().getItemMeta().getDisplayName())) return;
		
		event.setResult(new ItemStack(Material.AIR));
		event.getViewers().get(0).sendMessage(ChatColor.RED + "You cannot rename this item");
		
	}
	


}
