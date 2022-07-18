package org.baugindustries.baugrpg.customitems.dwarves.radiant_metallurgist.advanced;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DwarvenAxeListener implements Listener {
	private Main plugin;
	
	public DwarvenAxeListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Player)) return;
		
		Player player = (Player) event.getDamager();
		if (!Recipes.DWARVEN_AXE.matches(plugin, player.getInventory().getItemInMainHand())) return;
		
		if (event.getEntity() instanceof LivingEntity) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 5));
			LivingEntity entity = (LivingEntity) event.getEntity();
			entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 2));
			if (entity.getHealth() < entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() * 0.15) {
				event.setDamage(event.getDamage() * 2);
			}
		}
		
	}

}
