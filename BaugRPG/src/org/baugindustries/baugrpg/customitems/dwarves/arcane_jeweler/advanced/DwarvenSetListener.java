package org.baugindustries.baugrpg.customitems.dwarves.arcane_jeweler.advanced;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DwarvenSetListener implements Listener {
	private Main plugin;
	
	public DwarvenSetListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Player)) return;
		
		Player player = (Player) event.getDamager();
		
		if (!Recipes.DWARVEN_HELMET.matches(plugin, player.getInventory().getHelmet())) return;
		if (!Recipes.DWARVEN_CHESTPIECE.matches(plugin, player.getInventory().getChestplate())) return;
		if (!Recipes.DWARVEN_LEGGINGS.matches(plugin, player.getInventory().getLeggings())) return;
		if (!Recipes.DWARVEN_GREAVES.matches(plugin, player.getInventory().getBoots())) return;
		
		if (event.getEntity() instanceof LivingEntity) {
			LivingEntity entity = (LivingEntity) event.getEntity();
			if (entity.getHealth() < entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() * 0.5) {
				event.setDamage(event.getDamage() * 2);
			}
		}
		
	}
	
	@EventHandler
	public void onInventoryClick(PlayerMoveEvent event) {
		if (event.getTo().getBlock().equals(event.getFrom().getBlock())) return;
		
		Player player = event.getPlayer();
		
		if (!Recipes.DWARVEN_HELMET.matches(plugin, player.getInventory().getHelmet())) return;
		if (!Recipes.DWARVEN_CHESTPIECE.matches(plugin, player.getInventory().getChestplate())) return;
		if (!Recipes.DWARVEN_LEGGINGS.matches(plugin, player.getInventory().getLeggings())) return;
		if (!Recipes.DWARVEN_GREAVES.matches(plugin, player.getInventory().getBoots())) return;

		player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 2));
		
	}

}
