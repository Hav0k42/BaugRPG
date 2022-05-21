package org.baugindustries.baugrpg.customitems.men.stable_master.advanced;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RegenerativeHorseArmorListener implements Listener {
	private Main plugin;
	
	public RegenerativeHorseArmorListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onEquipMorral(InventoryClickEvent event) {

		if (!(event.getInventory().getHolder() instanceof AbstractHorse)) return;
		AbstractHorse horse = (AbstractHorse) event.getInventory().getHolder();
		
		if (horse.getInventory().getItem(1) != null && Recipes.REGENERATIVE_HORSE_ARMOR.matches(plugin, horse.getInventory().getItem(1))) return;//stop checking if theres already a morral in this slot.
		
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (horse.getInventory().getItem(1) != null && Recipes.REGENERATIVE_HORSE_ARMOR.matches(plugin, horse.getInventory().getItem(1))) {
					horse.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20000000, 1));
				}
			}
		}, 1L);
		
	}
	
	@EventHandler
	public void onDeEquipMorral(InventoryClickEvent event) {

		if (!(event.getInventory().getHolder() instanceof AbstractHorse)) return;
		AbstractHorse horse = (AbstractHorse) event.getInventory().getHolder();
		
		if (horse.getInventory().getItem(1) == null || !Recipes.REGENERATIVE_HORSE_ARMOR.matches(plugin, horse.getInventory().getItem(1))) return;//stop checking if theres no morral in this slot.
		
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (horse.getInventory().getItem(1) == null || !Recipes.REGENERATIVE_HORSE_ARMOR.matches(plugin, horse.getInventory().getItem(1))) {
					horse.removePotionEffect(PotionEffectType.REGENERATION);
				}
			}
		}, 1L);
		
	}
}
