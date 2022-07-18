package org.baugindustries.baugrpg.customitems.men.verdant_shepherd.expert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.Animals;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import org.bukkit.ChatColor;

public class VeiledTotemListener implements Listener {
	private Main plugin;
	HashMap<UUID, LivingEntity> disguisedPlayers = new HashMap<UUID, LivingEntity>();
	HashMap<UUID, List<ItemStack>> playerArmor = new HashMap<UUID, List<ItemStack>>();
	
	HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();
	int cooldownTime = 240000;//four minutes
	
	public VeiledTotemListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!(Recipes.VEILED_TOTEM.matches(plugin, player.getInventory().getItemInMainHand()))) return;
	    } else {
			if (!(Recipes.VEILED_TOTEM.matches(plugin, player.getInventory().getItemInOffHand()))) return;
	    }
		
		if (!(event.getRightClicked() instanceof Animals)) return;
		
		if (disguisedPlayers.containsKey(player.getUniqueId())) {
			player.sendMessage(ChatColor.RED + "You are already disguised.");
			return;
		}
		
		if (cooldown.containsKey(player.getUniqueId())) {
			if (cooldown.get(player.getUniqueId()) > System.currentTimeMillis()) {
				int secondsRemaining = (int) ((cooldown.get(player.getUniqueId()) - System.currentTimeMillis()) / 1000) + 1;
				String timeString = " seconds remaining.";
				if (secondsRemaining == 1) {
					timeString = " second remaining.";
				}
				player.sendMessage(ChatColor.RED + "You can't use that yet. " + secondsRemaining + timeString);
				return;
			}
		}
		
		Animals animal = (Animals) event.getRightClicked();
		
		Animals newAnimal = (Animals) player.getWorld().spawnEntity(player.getLocation(), animal.getType());
		newAnimal.setInvulnerable(true);
		newAnimal.setAI(false);
		newAnimal.getCollidableExemptions().add(player.getUniqueId());
		
		
		disguisedPlayers.put(player.getUniqueId(), newAnimal);
		player.setInvisible(true);
		
		List<ItemStack> armor = new ArrayList<ItemStack>();
		armor.add(player.getInventory().getHelmet());
		armor.add(player.getInventory().getChestplate());
		armor.add(player.getInventory().getLeggings());
		armor.add(player.getInventory().getBoots());
		player.getInventory().setHelmet(null);
		player.getInventory().setChestplate(null);
		player.getInventory().setLeggings(null);
		player.getInventory().setBoots(null);
		playerArmor.put(player.getUniqueId(), armor);
		

		cooldown.put(player.getUniqueId(), System.currentTimeMillis() + cooldownTime);
		
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (player.isOnline() && disguisedPlayers.containsKey(player.getUniqueId())) {
					disguisedPlayers.remove(player.getUniqueId());
					player.setInvisible(false);
					newAnimal.remove();
					
					List<ItemStack> armor = playerArmor.get(player.getUniqueId());
					player.getInventory().setHelmet(armor.get(0));
					player.getInventory().setChestplate(armor.get(1));
					player.getInventory().setLeggings(armor.get(2));
					player.getInventory().setBoots(armor.get(3));
					
				}
			}
		}, 1200L);
	}
	
	@EventHandler
	public void onInventoryClick(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (!disguisedPlayers.containsKey(player.getUniqueId())) return;
		
		disguisedPlayers.get(player.getUniqueId()).teleport(player);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!disguisedPlayers.containsKey(event.getWhoClicked().getUniqueId())) return;
		
		if (!event.getClickedInventory().getType().equals(InventoryType.PLAYER)) return;
		event.setCancelled(true);
		event.getWhoClicked().sendMessage(ChatColor.RED + "You cannot do that while disguised.");
	}
	
	@EventHandler
	public void onInventoryClick(PlayerQuitEvent event) {
		if (!disguisedPlayers.containsKey(event.getPlayer().getUniqueId())) return;
		Player player = event.getPlayer();
		disguisedPlayers.get(player.getUniqueId()).remove();
		disguisedPlayers.remove(player.getUniqueId());
		player.setInvisible(false);
		
		List<ItemStack> armor = playerArmor.get(player.getUniqueId());
		player.getInventory().setHelmet(armor.get(0));
		player.getInventory().setChestplate(armor.get(1));
		player.getInventory().setLeggings(armor.get(2));
		player.getInventory().setBoots(armor.get(3));
	}
	
	@EventHandler
	public void onInventoryClick(EntityTargetEvent event) {
		if (event.getTarget() == null) return;
		if (!disguisedPlayers.containsKey(event.getTarget().getUniqueId())) return;
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onInventoryClick(EntityDamageByEntityEvent event) {
		if (!disguisedPlayers.containsKey(event.getEntity().getUniqueId())) return;
		Player player = (Player) event.getEntity();
		disguisedPlayers.get(player.getUniqueId()).remove();
		disguisedPlayers.remove(player.getUniqueId());
		player.setInvisible(false);
		
		List<ItemStack> armor = playerArmor.get(player.getUniqueId());
		player.getInventory().setHelmet(armor.get(0));
		player.getInventory().setChestplate(armor.get(1));
		player.getInventory().setLeggings(armor.get(2));
		player.getInventory().setBoots(armor.get(3));
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onInventoryClick(PlayerInteractEvent event) {
		if (!disguisedPlayers.containsKey(event.getPlayer().getUniqueId())) return;
		Player player = event.getPlayer();
		
		if (!player.isSneaking()) return;
		if (!(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;
		if (!Recipes.VEILED_TOTEM.matches(plugin, player.getInventory().getItemInMainHand())) return;
		
		
		disguisedPlayers.get(player.getUniqueId()).remove();
		disguisedPlayers.remove(player.getUniqueId());
		player.setInvisible(false);
		
		List<ItemStack> armor = playerArmor.get(player.getUniqueId());
		player.getInventory().setHelmet(armor.get(0));
		player.getInventory().setChestplate(armor.get(1));
		player.getInventory().setLeggings(armor.get(2));
		player.getInventory().setBoots(armor.get(3));
	}
	
	@EventHandler
	public void onInventoryClick(BlockPlaceEvent event) {
		if (!Recipes.VEILED_TOTEM.matches(plugin, event.getItemInHand())) return;
		event.setCancelled(true);
	}

}
