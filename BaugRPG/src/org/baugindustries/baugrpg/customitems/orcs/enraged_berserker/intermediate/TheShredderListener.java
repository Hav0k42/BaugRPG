package org.baugindustries.baugrpg.customitems.orcs.enraged_berserker.intermediate;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class TheShredderListener implements Listener {
	private Main plugin;
	
	public TheShredderListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void hitWithShredder(EntityDamageByEntityEvent event) {
		if (!(event.getEntity() instanceof Player)) return;
		Player player = (Player) event.getEntity();
		
		if (!(event.getDamager() instanceof Player)) return;
		Player attacker = (Player) event.getDamager();
		
		if (!Recipes.THE_SHREDDER.matches(plugin, attacker.getInventory().getItemInMainHand())) return;
		
		if (!player.isBlocking()) return;
		
		if (event.getFinalDamage() != 0) return;
		
		ItemStack shield = player.getItemInUse();
		Damageable meta = (Damageable) shield.getItemMeta();
		meta.setDamage(meta.getDamage() + 40);
		player.getItemInUse().setItemMeta(meta);
			
	}

}
