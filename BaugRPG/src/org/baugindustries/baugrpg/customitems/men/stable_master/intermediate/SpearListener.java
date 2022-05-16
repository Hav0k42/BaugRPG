package org.baugindustries.baugrpg.customitems.men.stable_master.intermediate;

import java.util.Map;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;

public class SpearListener implements Listener {
	private Main plugin;
	
	public SpearListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onSpearHitEntity(ProjectileHitEvent event) {
		if (!(event.getEntity() instanceof Trident)) return;
		
		Trident trident = (Trident) event.getEntity();
		if (!(trident.getShooter() instanceof Player)) return;
		
		Player shooter = (Player) trident.getShooter();
		
		if (event.getHitEntity() == null) return;
		
		if (!(event.getHitEntity() instanceof LivingEntity)) return;
		
		if ((event.getHitEntity() instanceof Player) && plugin.getRace((Player)event.getHitEntity()) == plugin.getRace(shooter)) return;
		
		ItemStack tridentItem = trident.getItem();
		
		if (!Recipes.SPEAR.matches(plugin, tridentItem)) return;
		
		((LivingEntity)event.getHitEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 300, 254));
		
	}
	
	@EventHandler
	public void onEnchantSpear(EnchantItemEvent event) {
		if (!Recipes.SPEAR.matches(plugin, event.getItem())) return;
		event.getEnchanter().sendMessage(ChatColor.RED + "This item cannot be enchanted at an enchanting table.");
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onEnchantSpear(PrepareAnvilEvent event) {
		if (!Recipes.SPEAR.matches(plugin, event.getInventory().getItem(0))) return;
		
		Player player = (Player) event.getViewers().get(0);
		
		if (event.getResult() == null) return;
		
		Map<Enchantment, Integer> enchants = event.getResult().getEnchantments();
		
		boolean illegalEnchant = false;
		
		for (Enchantment enchantment : enchants.keySet()) {
			if (!(enchantment.equals(Enchantment.DURABILITY) || enchantment.equals(Enchantment.MENDING))) {
				illegalEnchant = true;
				break;
			}
		}
		
		if (!illegalEnchant) return;
		
		player.sendMessage(ChatColor.RED + "This item cannot be enchanted with that enchantment.");
		event.setResult(new ItemStack(Material.AIR));
	}

}
