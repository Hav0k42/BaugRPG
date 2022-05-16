package org.baugindustries.baugrpg.customitems.elves.lunar_artificier.intermediate;

import java.util.HashMap;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Material;
import org.bukkit.entity.AbstractArrow.PickupStatus;
import org.bukkit.entity.Player;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class LightBowListener implements Listener {
	private Main plugin;
	
	private HashMap<UUID, ItemStack> originalItem;
	
	public LightBowListener(Main plugin) {
		this.plugin = plugin;
		originalItem = new HashMap<UUID, ItemStack>();
	}
	
	
	@EventHandler
	public void onDrawBow(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		

		
		if (!(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!Recipes.LIGHT_BOW.matches(plugin, player.getInventory().getItemInMainHand())) return;
	    } else {
			if (!Recipes.LIGHT_BOW.matches(plugin, player.getInventory().getItemInOffHand())) return;
	    }
		
		Runnable checkBow = new Runnable() {
			  public void run() {
				  if (Recipes.LIGHT_BOW.matches(plugin, player.getItemInUse())) return;
				  if (!originalItem.containsKey(player.getUniqueId())) return;
				  
				  player.getInventory().setItem(9, originalItem.get(player.getUniqueId()));
				  originalItem.remove(player.getUniqueId());
				  
			  }
		 };
		for (int i = 0; i < 10; i++) {
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, checkBow, i);
		}
		

		if (originalItem.containsKey(player.getUniqueId())) return;
		
		originalItem.put(player.getUniqueId(), player.getInventory().getItem(9));
		
		player.getInventory().setItem(9, new ItemStack(Material.ARROW));
		
		
		
	}
	

	@EventHandler
	public void onShootBow(EntityShootBowEvent event) {
		if (!(event.getEntity() instanceof Player)) return;
		Player player = (Player) event.getEntity();
		
		if (!Recipes.LIGHT_BOW.matches(plugin, event.getBow())) return;
		

		if (!originalItem.containsKey(player.getUniqueId())) return;
		
		SpectralArrow arrow = player.launchProjectile(SpectralArrow.class);
		arrow.setPickupStatus(PickupStatus.DISALLOWED);
		
		
		event.setProjectile(arrow);
		
		event.setConsumeItem(false);
		

		player.getInventory().setItem(9, originalItem.get(player.getUniqueId()));
		
		originalItem.remove(player.getUniqueId());
	}

	@EventHandler
	public void onSwitchHand(PlayerItemHeldEvent event) {
		Player player = event.getPlayer();
		
		
		if (!originalItem.containsKey(player.getUniqueId())) return;
		
		if (!Recipes.LIGHT_BOW.matches(plugin, player.getInventory().getItem(event.getPreviousSlot()))) return;
		
		
		
		player.getInventory().setItem(9, originalItem.get(player.getUniqueId()));
		
		originalItem.remove(player.getUniqueId());
		
	}
	
	@EventHandler
	public void onCycleTool(PlayerSwapHandItemsEvent event) {
		Player player = event.getPlayer();
		if (!originalItem.containsKey(player.getUniqueId())) return;
		Runnable checkBow = new Runnable() {
			  public void run() {
				  if (Recipes.LIGHT_BOW.matches(plugin, player.getItemInUse())) return;
				  if (!originalItem.containsKey(player.getUniqueId())) return;
				  
				  player.getInventory().setItem(9, originalItem.get(player.getUniqueId()));
				  originalItem.remove(player.getUniqueId());
				  
			  }
		 };
		for (int i = 0; i < 10; i++) {
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, checkBow, i);
		}
	}

}
