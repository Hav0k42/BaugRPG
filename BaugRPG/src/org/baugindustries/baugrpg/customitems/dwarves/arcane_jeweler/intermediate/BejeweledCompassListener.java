package org.baugindustries.baugrpg.customitems.dwarves.arcane_jeweler.intermediate;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import org.bukkit.ChatColor;

public class BejeweledCompassListener implements Listener {
	private Main plugin;
	
	int distBounds = 7;
	HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();
	int cooldownTime = 180000;//3 minutes
	
	public BejeweledCompassListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onSearchForOres(PlayerInteractEvent event) {
		if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR))) return;
		
		
		Player player = event.getPlayer();
		ItemStack compass = null;
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!Recipes.BEJEWELED_COMPASS.matches(plugin, player.getInventory().getItemInMainHand())) return;
			compass = player.getInventory().getItemInMainHand();
		} else {
			if (!Recipes.BEJEWELED_COMPASS.matches(plugin, player.getInventory().getItemInOffHand())) return;
			compass = player.getInventory().getItemInOffHand();
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
		
		
		PersistentDataContainer data = compass.getItemMeta().getPersistentDataContainer();
		int refinedLevel = 1;
		if (data.has(new NamespacedKey(plugin, "level"), PersistentDataType.INTEGER)) {
			refinedLevel = data.get(new NamespacedKey(plugin, "level"), PersistentDataType.INTEGER);
		}
		
		
		CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();
		
		Location nearbyOre = null;
		switch (refinedLevel) {
		case 1:
			Material[] oreOne = {Material.COAL_ORE, Material.DEEPSLATE_COAL_ORE, Material.COPPER_ORE, Material.DEEPSLATE_COPPER_ORE, Material.REDSTONE_ORE, Material.DEEPSLATE_REDSTONE_ORE, Material.LAPIS_ORE, Material.DEEPSLATE_LAPIS_ORE, Material.NETHER_QUARTZ_ORE, Material.GOLD_ORE, Material.DEEPSLATE_GOLD_ORE, Material.NETHER_GOLD_ORE, Material.DIAMOND_ORE, Material.DEEPSLATE_DIAMOND_ORE, Material.ANCIENT_DEBRIS};
			nearbyOre = findNearbyOres(player.getLocation().getBlock(), oreOne);
			break;
		case 2:
			Material[] oreTwo = {Material.REDSTONE_ORE, Material.DEEPSLATE_REDSTONE_ORE, Material.LAPIS_ORE, Material.DEEPSLATE_LAPIS_ORE, Material.NETHER_QUARTZ_ORE, Material.GOLD_ORE, Material.DEEPSLATE_GOLD_ORE, Material.NETHER_GOLD_ORE, Material.DIAMOND_ORE, Material.DEEPSLATE_DIAMOND_ORE, Material.ANCIENT_DEBRIS};
			nearbyOre = findNearbyOres(player.getLocation().getBlock(), oreTwo);
			break;
		case 3:
			Material[] oreThree = {Material.LAPIS_ORE, Material.DEEPSLATE_LAPIS_ORE, Material.NETHER_QUARTZ_ORE, Material.GOLD_ORE, Material.DEEPSLATE_GOLD_ORE, Material.NETHER_GOLD_ORE, Material.DIAMOND_ORE, Material.DEEPSLATE_DIAMOND_ORE, Material.ANCIENT_DEBRIS};
			nearbyOre = findNearbyOres(player.getLocation().getBlock(), oreThree);
			break;
		case 4:
			Material[] oreFour = {Material.GOLD_ORE, Material.DEEPSLATE_GOLD_ORE, Material.NETHER_GOLD_ORE, Material.DIAMOND_ORE, Material.DEEPSLATE_DIAMOND_ORE, Material.ANCIENT_DEBRIS};
			nearbyOre = findNearbyOres(player.getLocation().getBlock(), oreFour);
			break;
		case 5:
			Material[] oreFive = {Material.DIAMOND_ORE, Material.DEEPSLATE_DIAMOND_ORE, Material.ANCIENT_DEBRIS};
			nearbyOre = findNearbyOres(player.getLocation().getBlock(), oreFive);
			break;
		}
		if (nearbyOre == null) {
			player.sendMessage(ChatColor.RED + "No ores found nearby.");
		} else {
			player.sendMessage(ChatColor.GOLD + "Treasure Found!");
			cooldown.put(player.getUniqueId(), System.currentTimeMillis() + cooldownTime);
		}
		compassMeta.setLodestone(nearbyOre);
		compassMeta.setLodestoneTracked(false);
		compass.setItemMeta(compassMeta);
	}
	
	private Location findNearbyOres(Block center, Material[] ores) {
		for (int i = 1; i < distBounds; i++) {
			for (int x = -i; x <= i; x++) {
				for (int y = -i; y <= i; y++) {
					for (int z = -i; z <= i; z++) {
						for (Material ore : ores) {
							if (center.getRelative(x, y, z).getType().equals(ore)) {
								return center.getRelative(x, y, z).getLocation();
							}
						}
					}
				}
			}
		}
		return null;
	}
	
	
	@EventHandler
	public void onSwapHandsWithDiamond(PlayerSwapHandItemsEvent event) {
		
		Player player = event.getPlayer();
		if (!Recipes.BEJEWELED_COMPASS.matches(plugin, player.getInventory().getItemInMainHand())) return;

		if (!player.getInventory().getItemInOffHand().getType().equals(Material.DIAMOND_BLOCK)) return;
		
		for (Recipes recipe : Recipes.values()) {
			if (recipe.matches(plugin, player.getInventory().getItemInOffHand())) return;
		}
		
		if (player.getInventory().getItemInOffHand().getAmount() != 1) return;
		
		ItemStack compass = player.getInventory().getItemInMainHand();
		ItemMeta meta = compass.getItemMeta();
		PersistentDataContainer data = meta.getPersistentDataContainer();
		int refinedLevel = 1;
		if (data.has(new NamespacedKey(plugin, "level"), PersistentDataType.INTEGER)) {
			refinedLevel = data.get(new NamespacedKey(plugin, "level"), PersistentDataType.INTEGER);
		}
		if (refinedLevel < 5) {
			refinedLevel++;
		} else {
			return;
		}
		
		data.set(new NamespacedKey(plugin, "level"), PersistentDataType.INTEGER, refinedLevel);
		List<String> lore = meta.getLore();
		lore.set(5, "Purity Level: " + ChatColor.GREEN + refinedLevel);
		meta.setLore(lore);
		compass.setItemMeta(meta);
		
		event.setCancelled(true);
		player.getInventory().setItemInOffHand(null);
		
		
	}
	
	
	
	@EventHandler
	public void onBindToLodestone(PlayerInteractEvent event) {
		if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		if (!event.getClickedBlock().getType().equals(Material.LODESTONE)) return;
		
		Player player = event.getPlayer();
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!Recipes.BEJEWELED_COMPASS.matches(plugin, player.getInventory().getItemInMainHand())) return;
		} else {
			if (!Recipes.BEJEWELED_COMPASS.matches(plugin, player.getInventory().getItemInOffHand())) return;
		}

		event.setCancelled(true);
		event.getPlayer().sendMessage(ChatColor.RED + "This item cannot be used like that");
	}

}
