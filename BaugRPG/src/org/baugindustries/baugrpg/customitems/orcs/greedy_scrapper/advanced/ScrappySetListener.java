package org.baugindustries.baugrpg.customitems.orcs.greedy_scrapper.advanced;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class ScrappySetListener implements Listener {
	private Main plugin;
	
	public ScrappySetListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Player)) return;
		if (!(event.getEntity() instanceof Player)) return;
		
		
		Player player = (Player) event.getDamager();
		Player otherPlayer = (Player) event.getEntity();

		if (!Recipes.SCRAPPY_HELMET.matches(plugin, player.getInventory().getHelmet())) return;
		if (!Recipes.SCRAPPY_CHESTPIECE.matches(plugin, player.getInventory().getChestplate())) return;
		if (!Recipes.SCRAPPY_LEGGINGS.matches(plugin, player.getInventory().getLeggings())) return;
		if (!Recipes.SCRAPPY_GREAVES.matches(plugin, player.getInventory().getBoots())) return;
		
		if (Math.random() > 0.1) return;
		
		
		for (ItemStack item : otherPlayer.getInventory()) {
			boolean match = false;
			for (Recipes recipe : Recipes.values()) {
				if (recipe.matches(plugin, item)) {
					match = true;
				}
			}
			if (item.getAmount() > 32 && !match) {
				item.setAmount(item.getAmount() - 1);
				player.getInventory().addItem(new ItemStack(item.getType(), 1));
				return;
			}
		}
		
		
	}

}
