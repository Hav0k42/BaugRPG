package org.baugindustries.baugrpg.customitems.elves.lunar_artificier.basic;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;

public class NebulousAuraListener implements Listener {

	private Main plugin;
	
	
	public NebulousAuraListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onGetBlindnessEffect(EntityPotionEffectEvent event) {
		if (!(event.getEntity() instanceof Player)) return;
		Player player = (Player) event.getEntity();
		
		if (!(event.getAction().equals(Action.ADDED) || event.getAction().equals(Action.CHANGED))) return;
		
		if (!event.getNewEffect().getType().equals(PotionEffectType.BLINDNESS)) return;
		
		if (!Recipes.NEBULOUS_AURA.playerIsCarrying(player, plugin)) return;
		
		event.setCancelled(true);
		
	}
	
	@EventHandler
	public void onInventoryClick(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		
		if (!(event.getRightClicked() instanceof Sheep)) return;
		
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!Recipes.NEBULOUS_AURA.matches(plugin, player.getInventory().getItemInMainHand())) return;
		} else {
			if (!Recipes.NEBULOUS_AURA.matches(plugin, player.getInventory().getItemInOffHand())) return;
		}

		event.setCancelled(true);
		event.getPlayer().sendMessage(ChatColor.RED + "This item cannot be used like that");
		
	}
	
	@EventHandler
	public void onInventoryClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if (!event.getAction().equals(org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK)) return;
		if (!event.getClickedBlock().getType().name().toLowerCase().contains("sign")) return;
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!Recipes.NEBULOUS_AURA.matches(plugin, player.getInventory().getItemInMainHand())) return;
		} else {
			if (!Recipes.NEBULOUS_AURA.matches(plugin, player.getInventory().getItemInOffHand())) return;
		}

		event.setCancelled(true);
		event.getPlayer().sendMessage(ChatColor.RED + "This item cannot be used like that");
		
	}
}
