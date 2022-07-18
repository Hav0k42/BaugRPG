package org.baugindustries.baugrpg.customitems.elves.enchanted_botanist.expert;

import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Profession;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityCategory;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SoulOfTheFloristListener implements Listener {
	private Main plugin;
	
	int radius = 40;

	
	public SoulOfTheFloristListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void BlockBuild(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		
		if (event.getTo().getBlock().equals(event.getFrom().getBlock())) return;
		
		if (!Recipes.SOUL_OF_THE_FLORIST.playerIsCarrying(player, plugin)) return;
		
		if (plugin.getProfession(player) == null || !plugin.getProfession(player).equals(Profession.ENCHANTED_BOTANIST)) return;
		
		List<Entity> nearbyEntities = player.getNearbyEntities(radius, radius, radius);

		PotionEffect poison = new PotionEffect(PotionEffectType.POISON, 100, 0);
		PotionEffect wither = new PotionEffect(PotionEffectType.WITHER, 100, 0);
		PotionEffect nausea = new PotionEffect(PotionEffectType.CONFUSION, 600, 0);
		
		
		for (Entity entity : nearbyEntities) {
			if (entity instanceof Player) {
				Player otherPlayer = (Player) entity;
				if (plugin.getRace(otherPlayer) != plugin.getRace(player)) {
					otherPlayer.addPotionEffect(nausea);
					if (otherPlayer.hasPotionEffect(PotionEffectType.POISON)) {
						if (otherPlayer.getHealth() > 10) {
							otherPlayer.addPotionEffect(poison);
						}
					}
				}
			} else if (entity instanceof Monster) {
				if (!entity.getPersistentDataContainer().has(new NamespacedKey(plugin, "flower"), PersistentDataType.STRING)) {
					Monster monster = (Monster) entity;
					if (monster.getCategory().equals(EntityCategory.UNDEAD)) {
						if (!monster.hasPotionEffect(PotionEffectType.WITHER)) {
							if (monster.getHealth() > 10) {
								monster.addPotionEffect(wither);
							}
						}
					} else if (monster.getHealth() > 10) {
						if (!monster.hasPotionEffect(PotionEffectType.POISON)) {
							monster.addPotionEffect(poison);
						}
					}
				}
				
			}
		}
	}
}
