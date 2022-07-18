package org.baugindustries.baugrpg.customitems.elves.woodland_craftsman.expert;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class WoodslingListener implements Listener {
	private Main plugin;
	
	
	public WoodslingListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onUseGaiasWrath(PlayerInteractEvent event) {
		if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR))) return;
		
		Player player = event.getPlayer();
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!Recipes.WOODSLING.matches(plugin, player.getInventory().getItemInMainHand())) return;
		} else {
			if (!Recipes.WOODSLING.matches(plugin, player.getInventory().getItemInOffHand())) return;
		}
		ItemStack ammo = null;
		for (ItemStack itemStack : player.getInventory()) {
			if (itemStack != null && (Tag.LOGS.getValues().contains(itemStack.getType()) || Tag.PLANKS.getValues().contains(itemStack.getType()))) {
				ammo = itemStack;
				break;
			}
		}
		
		if (ammo == null) {
			player.sendMessage(ChatColor.RED + "You don't have any ammunition for this.");
			return;
		}

		FallingBlock wood = player.getWorld().spawnFallingBlock(player.getEyeLocation(), ammo.getType().createBlockData());
		wood.getPersistentDataContainer().set(new NamespacedKey(plugin, "woodsling"), PersistentDataType.INTEGER, 1);
		wood.setVelocity(player.getEyeLocation().getDirection().multiply(2.5));
		wood.setHurtEntities(false);
		ammo.setAmount(ammo.getAmount() - 1);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				for (Entity entity : wood.getNearbyEntities(0.5, 0.5, 0.5)) {
					if (entity instanceof LivingEntity) {
						LivingEntity lEntity = (LivingEntity) entity;
						lEntity.damage(plugin.damageArmorCalculation(lEntity, 5));
					}
				}
				if (wood.isValid()) {
					plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, this, 1);
				}
			}
		}, 2);
		
	}

	@EventHandler
	public void onInventoryClick(BlockPlaceEvent event) {
		if (!Recipes.WOODSLING.matches(plugin, event.getItemInHand())) return;
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onWoodLand(EntityChangeBlockEvent event) {
		if (!event.getEntity().getPersistentDataContainer().has(new NamespacedKey(plugin, "woodsling"), PersistentDataType.INTEGER)) return;
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onWoodLand(EntityDamageByEntityEvent event) {
		if (!event.getDamager().getPersistentDataContainer().has(new NamespacedKey(plugin, "woodsling"), PersistentDataType.INTEGER)) return;
		event.setCancelled(true);
	}
	
	
	
}
