package org.baugindustries.baugrpg.listeners.Crafting;

import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class LearnedRecipesInventoryListener implements Listener {
	private Main plugin;
	public LearnedRecipesInventoryListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player)) return;
		if (!(event.getClickedInventory() != null && event.getCurrentItem() != null)) return;
		if (!event.getView().getTitle().equals(ChatColor.GOLD + "Learned Recipes")) return;
		if (!(event.getView().getTopInventory().equals(event.getClickedInventory())) && event.getCursor() == null) return;
		Player player = (Player)event.getWhoClicked();
		event.setCancelled(true);
		if (event.getCurrentItem().equals(plugin.itemManager.getBackItem())) {
			player.openInventory(plugin.inventoryManager.getBaugScrollMenuInventory(player));
			return;
		}
		
		if (!event.getCurrentItem().getItemMeta().hasDisplayName()) return;
		
		if (event.getSlot() == 0) return;
		
		Recipes recipe = Recipes.valueOf(event.getCurrentItem().getItemMeta().getDisplayName().toUpperCase().replace(' ', '_').replace("'", "").substring(2));
		if (recipe == null) {
			player.sendMessage(event.getCurrentItem().getItemMeta().getDisplayName().toUpperCase().replace(' ', '_'));
			return;
		}
		
		
		String inventoryName = ChatColor.GOLD + "Recipe";
		Inventory inventory = Bukkit.createInventory(null, 45, inventoryName);
		
		ItemStack[] pattern = recipe.getPattern(plugin);
		
		inventory.setItem(10, pattern[0]);
		inventory.setItem(11, pattern[1]);
		inventory.setItem(12, pattern[2]);
		inventory.setItem(19, pattern[3]);
		inventory.setItem(20, pattern[4]);
		inventory.setItem(21, pattern[5]);
		inventory.setItem(28, pattern[6]);
		inventory.setItem(29, pattern[7]);
		inventory.setItem(30, pattern[8]);
		
		ItemStack resultItem = recipe.getResult(plugin);
		ItemMeta resultMeta = resultItem.getItemMeta();
		List<String> lore = resultMeta.getLore();
		lore.add("");
		lore.add("Click for total vanilla ingredients.");
		resultMeta.setLore(lore);
		resultItem.setItemMeta(resultMeta);
		
		inventory.setItem(25, resultItem);
		
		for (int i = 5; i < 45; i += 9) {
			inventory.setItem(i, plugin.itemManager.getBlankItem());
		}
		
		inventory.setItem(36, plugin.itemManager.getBackItem());
		player.openInventory(inventory);
		
	}
}
