package org.baugindustries.baugrpg.customitems.orcs.enraged_berserker.basic;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import net.md_5.bungee.api.ChatColor;

public class RageStoneListener implements Listener {
	private Main plugin;
	
	public RageStoneListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(PlayerDeathEvent event) {
		Player player = event.getEntity();
		if (!(player.getKiller() != null && player.getKiller() instanceof Player)) return;
		
		Player killer = (Player) player.getKiller();
		
		if (!Recipes.RAGE_STONE.playerIsCarrying(killer, plugin)) return;
		
		event.getDrops().add(plugin.itemManager.getPlayerHead(player));
		
		
	}
	
	@EventHandler
	public void onUseEgg(PlayerInteractEvent event) {
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!(Recipes.RAGE_STONE.matches(plugin, event.getPlayer().getInventory().getItemInMainHand()))) return;
	    } else {
			if (!(Recipes.RAGE_STONE.matches(plugin, event.getPlayer().getInventory().getItemInOffHand()))) return;
	    }

		event.getPlayer().sendMessage(ChatColor.RED + "This item cannot be used like that");
		event.setCancelled(true);
	}

}
