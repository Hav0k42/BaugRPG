package org.baugindustries.baugrpg.customitems.men.stable_master.advanced;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import net.md_5.bungee.api.ChatColor;

public class RuggedSwordListener implements Listener {
	private Main plugin;
	float chargeIncrements = 0.0001f;
	float dischargeIncrements = 0.03f;
	
	public RuggedSwordListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void checkCharge(PlayerInteractEvent event) {
		if (!(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;

		Player player = event.getPlayer();
		if (!player.isSneaking()) return;
		
		ItemStack ruggedSword = null;
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!Recipes.RUGGED_SWORD.matches(plugin, player.getInventory().getItemInMainHand())) return;
			ruggedSword = player.getInventory().getItemInMainHand();
		} else {
			if (!Recipes.RUGGED_SWORD.matches(plugin, player.getInventory().getItemInOffHand())) return;
			ruggedSword = player.getInventory().getItemInOffHand();
		}
		
		PersistentDataContainer data = ruggedSword.getItemMeta().getPersistentDataContainer();
		float charge = data.get(new NamespacedKey(plugin, "charge"), PersistentDataType.FLOAT);
		player.sendMessage(ChatColor.YELLOW + "Rugged Sword charge is: " + ((float)Math.round(charge * 10000)) / 100.0 + "%.");
	}
	
	@EventHandler
	public void rideHorse(PlayerMoveEvent event) {
		if (event.getFrom().getBlock().equals(event.getTo().getBlock())) return;
		Player player = event.getPlayer();
		if (!(player.getVehicle() instanceof AbstractHorse)) return;
		
		if (Recipes.RUGGED_SWORD.matches(plugin, player.getInventory().getItemInMainHand())) {
			ItemStack ruggedSword = player.getInventory().getItemInMainHand();
			
			ItemMeta meta = ruggedSword.getItemMeta();
			PersistentDataContainer data = meta.getPersistentDataContainer();
			float charge = data.get(new NamespacedKey(plugin, "charge"), PersistentDataType.FLOAT);
			
			if (charge + chargeIncrements <= 1) {
				charge += chargeIncrements;
				data.set(new NamespacedKey(plugin, "charge"), PersistentDataType.FLOAT, charge);
				ruggedSword.setItemMeta(meta);
			}
		}
		if (Recipes.RUGGED_SWORD.matches(plugin, player.getInventory().getItemInOffHand())) {
			ItemStack ruggedSword = player.getInventory().getItemInOffHand();
			
			ItemMeta meta = ruggedSword.getItemMeta();
			PersistentDataContainer data = meta.getPersistentDataContainer();
			float charge = data.get(new NamespacedKey(plugin, "charge"), PersistentDataType.FLOAT);
			
			if (charge + chargeIncrements <= 1) {
				charge += chargeIncrements;
				data.set(new NamespacedKey(plugin, "charge"), PersistentDataType.FLOAT, charge);
				ruggedSword.setItemMeta(meta);
			}
		}
	}
	
	@EventHandler
	public void attackEntity(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Player)) return;
		
		Player player = (Player) event.getDamager();
		
		if (Recipes.RUGGED_SWORD.matches(plugin, player.getInventory().getItemInMainHand())) {
			ItemStack ruggedSword = player.getInventory().getItemInMainHand();
			
			ItemMeta meta = ruggedSword.getItemMeta();
			PersistentDataContainer data = meta.getPersistentDataContainer();
			float charge = data.get(new NamespacedKey(plugin, "charge"), PersistentDataType.FLOAT);
			
			event.setDamage(event.getDamage() * ((charge * 1.75) + 1));
			
			if (charge - dischargeIncrements >= 0) {
				charge -= dischargeIncrements;
			} else {
				charge = 0;
			}
			data.set(new NamespacedKey(plugin, "charge"), PersistentDataType.FLOAT, charge);
			ruggedSword.setItemMeta(meta);
		}
	}

}
