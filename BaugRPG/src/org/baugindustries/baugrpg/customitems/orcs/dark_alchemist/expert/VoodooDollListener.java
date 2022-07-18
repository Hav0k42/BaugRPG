package org.baugindustries.baugrpg.customitems.orcs.dark_alchemist.expert;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

public class VoodooDollListener implements Listener {
	private Main plugin;
	
	HashMap<UUID, Location> originalLoc = new HashMap<UUID, Location>();
	
	HashMap<UUID, UUID> possessedPlayers = new HashMap<UUID, UUID>();//Possessor, Possessed
	
	long possessionTime = 400;
	
	boolean activatedTask;
	
	public VoodooDollListener(Main plugin) {
		this.plugin = plugin;
		
		activatedTask = false;
	}
	
	
	@EventHandler
	public void onInventoryClick(PlayerInteractEvent event) {
		if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR))) return;
		
		Player player = event.getPlayer();
		
		ItemStack doll;
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!Recipes.VOODOO_DOLL.matches(plugin, player.getInventory().getItemInMainHand())) return;
			doll = player.getInventory().getItemInMainHand();
		} else {
			if (!Recipes.VOODOO_DOLL.matches(plugin, player.getInventory().getItemInOffHand())) return;
			doll = player.getInventory().getItemInOffHand();
		}
		
		ItemMeta dollMeta = doll.getItemMeta();
		if (!dollMeta.getPersistentDataContainer().has(new NamespacedKey(plugin, "boundPlayer"), PersistentDataType.STRING)) {
			player.sendMessage(ChatColor.RED + "This doll is not bound to a player. Swap hands while holding a player's head to bind it to them.");
			return;
		}
		
		UUID otherPlayerUUID = UUID.fromString(dollMeta.getPersistentDataContainer().get(new NamespacedKey(plugin, "boundPlayer"), PersistentDataType.STRING));
		
		if (otherPlayerUUID.equals(player.getUniqueId())) {
			player.sendMessage(ChatColor.RED + "You cannot use a voodoo doll that has been bound to yourself.");
			return;
		}
		
		if (!Bukkit.getOfflinePlayer(otherPlayerUUID).isOnline()) {
			player.sendMessage(ChatColor.RED + Bukkit.getOfflinePlayer(otherPlayerUUID).getName() + " is not currently online.");
			return;
		}
		
		Player otherPlayer = Bukkit.getPlayer(otherPlayerUUID);
		
		originalLoc.put(player.getUniqueId(), player.getLocation());
		player.setGameMode(GameMode.SPECTATOR);
		player.teleport(otherPlayer);
		
		possessedPlayers.put(player.getUniqueId(), otherPlayerUUID);
		
		activateTask();
		
		otherPlayer.sendMessage(ChatColor.GOLD + "You've been possessed for 20 seconds.");
		
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				possessedPlayers.remove(player.getUniqueId());
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						player.setGameMode(GameMode.SURVIVAL);
						player.teleport(originalLoc.get(player.getUniqueId()));
						originalLoc.remove(player.getUniqueId());
					}
				}, 1);
			}
		}, possessionTime);
		
	}
	
	private void activateTask() {
		if (!activatedTask) {
			activatedTask = true;
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				public void run() {
					
					for (UUID possessorUUID : possessedPlayers.keySet()) {
						Player possessor = Bukkit.getPlayer(possessorUUID);
						Player possessed = Bukkit.getPlayer(possessedPlayers.get(possessorUUID));
						
						possessed.setVelocity(possessor.getLocation().getDirection().multiply(1));
						
						Location newLocation = new Location(possessed.getWorld(), possessed.getLocation().getX(), possessed.getLocation().getY(), possessed.getLocation().getZ(), possessor.getLocation().getYaw(), possessor.getLocation().getPitch());
						
						possessor.teleport(newLocation);
					}
					
					
					if (possessedPlayers.size() != 0) {
						plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, this, 1);
					} else {
						activatedTask = false;
					}
				}
			}, 1);
		}
	}


	@EventHandler
	public void onSwapHandsWithDiamond(PlayerSwapHandItemsEvent event) {
		
		Player player = event.getPlayer();
		if (!Recipes.VOODOO_DOLL.matches(plugin, player.getInventory().getItemInMainHand())) return;

		if (!player.getInventory().getItemInOffHand().getType().equals(Material.PLAYER_HEAD)) return;
		
		for (Recipes recipe : Recipes.values()) {
			if (recipe.matches(plugin, player.getInventory().getItemInOffHand())) return;
		}
		
		if (player.getInventory().getItemInOffHand().getAmount() != 1) return;
		
		
		ItemStack head = player.getInventory().getItemInOffHand();
		
		if (!head.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "playerHead"), PersistentDataType.INTEGER)) return;
		
		ItemStack doll = player.getInventory().getItemInMainHand();
		ItemMeta meta = doll.getItemMeta();
		
		List<String> lore = meta.getLore();
		lore.set(2, ChatColor.RED + "Bound to: " + ChatColor.YELLOW + ((SkullMeta)head.getItemMeta()).getOwningPlayer().getName());
		meta.setLore(lore);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "boundPlayer"), PersistentDataType.STRING, ((SkullMeta)head.getItemMeta()).getOwningPlayer().getUniqueId().toString());
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "usesRemaining"), PersistentDataType.INTEGER, 3);
		doll.setItemMeta(meta);
		
		
		event.setCancelled(true);
		player.getInventory().setItemInOffHand(null);
		
		
	}
	
	@EventHandler
	public void onInventoryClick(EntityResurrectEvent event) {
		ItemStack mainHand = event.getEntity().getEquipment().getItemInMainHand();
		ItemStack offHand = event.getEntity().getEquipment().getItemInOffHand();
		
		if (event.isCancelled()) return;
		
		if (mainHand.getType().equals(Material.TOTEM_OF_UNDYING) && !offHand.getType().equals(Material.TOTEM_OF_UNDYING)) {
			if (Recipes.VOODOO_DOLL.matches(plugin, mainHand)) {
				event.getEntity().getEquipment().setItemInMainHand(mainHand);
				event.setCancelled(true);
				return;
			}
		} else if (!mainHand.getType().equals(Material.TOTEM_OF_UNDYING) && offHand.getType().equals(Material.TOTEM_OF_UNDYING)) {
			if (Recipes.VOODOO_DOLL.matches(plugin, offHand)) {
				event.getEntity().getEquipment().setItemInOffHand(offHand);
				event.setCancelled(true);
				return;
			}
		} else if (mainHand.getType().equals(Material.TOTEM_OF_UNDYING) && offHand.getType().equals(Material.TOTEM_OF_UNDYING)) {
			if (Recipes.VOODOO_DOLL.matches(plugin, mainHand) && Recipes.VOODOO_DOLL.matches(plugin, offHand)) {
				event.getEntity().getEquipment().setItemInMainHand(mainHand);
				event.getEntity().getEquipment().setItemInOffHand(offHand);
				event.setCancelled(true);
				return;
			} else if (Recipes.VOODOO_DOLL.matches(plugin, mainHand)) {
				
				event.getEntity().getEquipment().setItemInMainHand(null);
				event.getEntity().getEquipment().setItemInOffHand(mainHand);
				
				return;
			}
		}
		
		
	}

}
