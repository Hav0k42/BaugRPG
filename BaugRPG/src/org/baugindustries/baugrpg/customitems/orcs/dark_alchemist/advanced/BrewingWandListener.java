package org.baugindustries.baugrpg.customitems.orcs.dark_alchemist.advanced;

import java.util.Arrays;
import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.CauldronLevelChangeEvent;
import org.bukkit.event.block.CauldronLevelChangeEvent.ChangeReason;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

public class BrewingWandListener implements Listener {
	private Main plugin;
	

	List<Material> potionIngredients = Arrays.asList(Material.NETHER_WART, Material.REDSTONE, Material.GLOWSTONE_DUST, Material.FERMENTED_SPIDER_EYE, Material.SUGAR, Material.RABBIT_FOOT, Material.GLISTERING_MELON_SLICE, Material.SPIDER_EYE, Material.PUFFERFISH, Material.MAGMA_CREAM, Material.GOLDEN_CARROT, Material.BLAZE_POWDER, Material.GHAST_TEAR, Material.TURTLE_HELMET, Material.PHANTOM_MEMBRANE);
	
	public BrewingWandListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onClickCauldron(PlayerInteractEvent event) {
		if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		
		if (!event.getClickedBlock().getType().equals(Material.WATER_CAULDRON)) return;
		
		Block cauldron = event.getClickedBlock();
		
		Player player = event.getPlayer();
		
		
		
		if (player.isSneaking()) {
			if (cauldron.hasMetadata("potion")) {
				
				PotionType potion = PotionType.valueOf(cauldron.getMetadata("potion").get(0).asString());
				String[] words = potion.name().split("_");
				String matName = "";
				for (String word : words) {
					matName = matName + word.substring(0, 1) + word.substring(1).toLowerCase() + " ";
				}
				
				player.sendMessage(ChatColor.YELLOW + "This cauldron contains " + matName + "Potion.");
			} else {
				player.sendMessage(ChatColor.YELLOW + "This cauldron contains water.");
			}
			return;
		}
		
		Material ingredient;
		ItemStack brewingWand;
		ItemMeta meta;
		PersistentDataContainer data;
		List<String> lore;
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!Recipes.BREWING_WAND.matches(plugin, player.getInventory().getItemInMainHand())) return;
			brewingWand = player.getInventory().getItemInMainHand();
			meta = brewingWand.getItemMeta();
			lore = meta.getLore();
			data = meta.getPersistentDataContainer();
			
			if (!data.has(new NamespacedKey(plugin, "ingredient"), PersistentDataType.STRING)) {
				player.sendMessage(ChatColor.RED + "Your wand isn't carrying any ingredients.");
				return;
			}
			
			ingredient = Material.valueOf(data.get(new NamespacedKey(plugin, "ingredient"), PersistentDataType.STRING));
		} else {
			if (!Recipes.BREWING_WAND.matches(plugin, player.getInventory().getItemInOffHand())) return;
			brewingWand = player.getInventory().getItemInOffHand();
			meta = brewingWand.getItemMeta();
			lore = meta.getLore();
			data = meta.getPersistentDataContainer();
			
			if (!data.has(new NamespacedKey(plugin, "ingredient"), PersistentDataType.STRING)) {
				player.sendMessage(ChatColor.RED + "Your wand isn't carrying any ingredients.");
				return;
			}
			
			ingredient = Material.valueOf(data.get(new NamespacedKey(plugin, "ingredient"), PersistentDataType.STRING));
		}
		
		if (cauldron.hasMetadata("potion")) {
			
			PotionType currentPotion = PotionType.valueOf(cauldron.getMetadata("potion").get(0).asString());
			
			if (currentPotion.equals(PotionType.AWKWARD)) {
				switch (ingredient) {
					case SUGAR:
						cauldron.removeMetadata("potion", plugin);
						cauldron.setMetadata("potion", new FixedMetadataValue(plugin, PotionType.SPEED.name()));
						
						data.remove(new NamespacedKey(plugin, "ingredient"));
						lore.set(3, ChatColor.YELLOW + "Current ingredient: ");
						meta.setLore(lore);
						brewingWand.setItemMeta(meta);
						
						player.getWorld().spawnParticle(Particle.REDSTONE, cauldron.getLocation().add(0.5, 0.5, 0.5), 5, 0.5, 0.5, 0.5, new Particle.DustOptions(Color.RED, 1));
						
						return;
					case RABBIT_FOOT:
						cauldron.removeMetadata("potion", plugin);
						cauldron.setMetadata("potion", new FixedMetadataValue(plugin, PotionType.JUMP.name()));
						
						data.remove(new NamespacedKey(plugin, "ingredient"));
						lore.set(3, ChatColor.YELLOW + "Current ingredient: ");
						meta.setLore(lore);
						brewingWand.setItemMeta(meta);
						
						player.getWorld().spawnParticle(Particle.REDSTONE, cauldron.getLocation().add(0.5, 0.5, 0.5), 5, 0.5, 0.5, 0.5, new Particle.DustOptions(Color.RED, 1));
						
						return;
					case BLAZE_POWDER:
						cauldron.removeMetadata("potion", plugin);
						cauldron.setMetadata("potion", new FixedMetadataValue(plugin, PotionType.STRENGTH.name()));
						
						data.remove(new NamespacedKey(plugin, "ingredient"));
						lore.set(3, ChatColor.YELLOW + "Current ingredient: ");
						meta.setLore(lore);
						brewingWand.setItemMeta(meta);
						
						player.getWorld().spawnParticle(Particle.REDSTONE, cauldron.getLocation().add(0.5, 0.5, 0.5), 5, 0.5, 0.5, 0.5, new Particle.DustOptions(Color.RED, 1));
						
						return;
					case GLISTERING_MELON_SLICE:
						cauldron.removeMetadata("potion", plugin);
						cauldron.setMetadata("potion", new FixedMetadataValue(plugin, PotionType.INSTANT_HEAL.name()));
						
						data.remove(new NamespacedKey(plugin, "ingredient"));
						lore.set(3, ChatColor.YELLOW + "Current ingredient: ");
						meta.setLore(lore);
						brewingWand.setItemMeta(meta);
						
						player.getWorld().spawnParticle(Particle.REDSTONE, cauldron.getLocation().add(0.5, 0.5, 0.5), 5, 0.5, 0.5, 0.5, new Particle.DustOptions(Color.RED, 1));
						
						return;
					case SPIDER_EYE:
						cauldron.removeMetadata("potion", plugin);
						cauldron.setMetadata("potion", new FixedMetadataValue(plugin, PotionType.POISON.name()));
						
						data.remove(new NamespacedKey(plugin, "ingredient"));
						lore.set(3, ChatColor.YELLOW + "Current ingredient: ");
						meta.setLore(lore);
						brewingWand.setItemMeta(meta);
						
						player.getWorld().spawnParticle(Particle.REDSTONE, cauldron.getLocation().add(0.5, 0.5, 0.5), 5, 0.5, 0.5, 0.5, new Particle.DustOptions(Color.RED, 1));
						
						return;
					case GHAST_TEAR:
						cauldron.removeMetadata("potion", plugin);
						cauldron.setMetadata("potion", new FixedMetadataValue(plugin, PotionType.REGEN.name()));
						
						data.remove(new NamespacedKey(plugin, "ingredient"));
						lore.set(3, ChatColor.YELLOW + "Current ingredient: ");
						meta.setLore(lore);
						brewingWand.setItemMeta(meta);
						
						player.getWorld().spawnParticle(Particle.REDSTONE, cauldron.getLocation().add(0.5, 0.5, 0.5), 5, 0.5, 0.5, 0.5, new Particle.DustOptions(Color.RED, 1));
						
						return;
					case MAGMA_CREAM:
						cauldron.removeMetadata("potion", plugin);
						cauldron.setMetadata("potion", new FixedMetadataValue(plugin, PotionType.FIRE_RESISTANCE.name()));
						
						data.remove(new NamespacedKey(plugin, "ingredient"));
						lore.set(3, ChatColor.YELLOW + "Current ingredient: ");
						meta.setLore(lore);
						brewingWand.setItemMeta(meta);
						
						player.getWorld().spawnParticle(Particle.REDSTONE, cauldron.getLocation().add(0.5, 0.5, 0.5), 5, 0.5, 0.5, 0.5, new Particle.DustOptions(Color.RED, 1));
						
						return;
					case PUFFERFISH:
						cauldron.removeMetadata("potion", plugin);
						cauldron.setMetadata("potion", new FixedMetadataValue(plugin, PotionType.WATER_BREATHING.name()));
						
						data.remove(new NamespacedKey(plugin, "ingredient"));
						lore.set(3, ChatColor.YELLOW + "Current ingredient: ");
						meta.setLore(lore);
						brewingWand.setItemMeta(meta);
						
						player.getWorld().spawnParticle(Particle.REDSTONE, cauldron.getLocation().add(0.5, 0.5, 0.5), 5, 0.5, 0.5, 0.5, new Particle.DustOptions(Color.RED, 1));
						
						return;
					case GOLDEN_CARROT:
						cauldron.removeMetadata("potion", plugin);
						cauldron.setMetadata("potion", new FixedMetadataValue(plugin, PotionType.NIGHT_VISION.name()));
						
						data.remove(new NamespacedKey(plugin, "ingredient"));
						lore.set(3, ChatColor.YELLOW + "Current ingredient: ");
						meta.setLore(lore);
						brewingWand.setItemMeta(meta);
						
						player.getWorld().spawnParticle(Particle.REDSTONE, cauldron.getLocation().add(0.5, 0.5, 0.5), 5, 0.5, 0.5, 0.5, new Particle.DustOptions(Color.RED, 1));
						
						return;
					case TURTLE_HELMET:
						cauldron.removeMetadata("potion", plugin);
						cauldron.setMetadata("potion", new FixedMetadataValue(plugin, PotionType.TURTLE_MASTER.name()));
						
						data.remove(new NamespacedKey(plugin, "ingredient"));
						lore.set(3, ChatColor.YELLOW + "Current ingredient: ");
						meta.setLore(lore);
						brewingWand.setItemMeta(meta);
						
						player.getWorld().spawnParticle(Particle.REDSTONE, cauldron.getLocation().add(0.5, 0.5, 0.5), 5, 0.5, 0.5, 0.5, new Particle.DustOptions(Color.RED, 1));
						
						return;
					case PHANTOM_MEMBRANE:
						cauldron.removeMetadata("potion", plugin);
						cauldron.setMetadata("potion", new FixedMetadataValue(plugin, PotionType.SLOW_FALLING.name()));
						
						data.remove(new NamespacedKey(plugin, "ingredient"));
						lore.set(3, ChatColor.YELLOW + "Current ingredient: ");
						meta.setLore(lore);
						brewingWand.setItemMeta(meta);
						
						player.getWorld().spawnParticle(Particle.REDSTONE, cauldron.getLocation().add(0.5, 0.5, 0.5), 5, 0.5, 0.5, 0.5, new Particle.DustOptions(Color.RED, 1));
						
						return;
					default:
						break;
				}
			} else if (currentPotion.equals(PotionType.SPEED) || currentPotion.equals(PotionType.JUMP)) {
				if (ingredient.equals(Material.FERMENTED_SPIDER_EYE)) {
					cauldron.removeMetadata("potion", plugin);
					cauldron.setMetadata("potion", new FixedMetadataValue(plugin, PotionType.SLOWNESS.name()));
					return;
				}
			}
			
			// Both potions have been made illegal for brewing.
			
//			else if (currentPotion.equals(PotionType.INSTANT_HEAL) || currentPotion.equals(PotionType.POISON)) {
//				if (ingredient.equals(Material.FERMENTED_SPIDER_EYE)) {
//					cauldron.removeMetadata("potion", plugin);
//					cauldron.setMetadata("potion", new FixedMetadataValue(plugin, PotionType.INSTANT_DAMAGE.name()));
//					return;
//				}
//			} else if (currentPotion.equals(PotionType.NIGHT_VISION)) {
//				if (ingredient.equals(Material.FERMENTED_SPIDER_EYE)) {
//					cauldron.removeMetadata("potion", plugin);
//					cauldron.setMetadata("potion", new FixedMetadataValue(plugin, PotionType.INVISIBILITY.name()));
//					return;
//				}
//			}
					
			
		} else {
			switch (ingredient) {
				case NETHER_WART:
					cauldron.setMetadata("potion", new FixedMetadataValue(plugin, PotionType.AWKWARD.name()));
					
					data.remove(new NamespacedKey(plugin, "ingredient"));
					lore.set(3, ChatColor.YELLOW + "Current ingredient: ");
					meta.setLore(lore);
					brewingWand.setItemMeta(meta);
					
					player.getWorld().spawnParticle(Particle.REDSTONE, cauldron.getLocation().add(0.5, 0.5, 0.5), 5, 0.5, 0.5, 0.5, new Particle.DustOptions(Color.RED, 1));
					
					return;
				case SPIDER_EYE:
					cauldron.setMetadata("potion", new FixedMetadataValue(plugin, PotionType.MUNDANE.name()));
					
					data.remove(new NamespacedKey(plugin, "ingredient"));
					lore.set(3, ChatColor.YELLOW + "Current ingredient: ");
					meta.setLore(lore);
					brewingWand.setItemMeta(meta);
					
					player.getWorld().spawnParticle(Particle.REDSTONE, cauldron.getLocation().add(0.5, 0.5, 0.5), 5, 0.5, 0.5, 0.5, new Particle.DustOptions(Color.RED, 1));
					
					return;
				case GHAST_TEAR:
					cauldron.setMetadata("potion", new FixedMetadataValue(plugin, PotionType.MUNDANE.name()));
					
					data.remove(new NamespacedKey(plugin, "ingredient"));
					lore.set(3, ChatColor.YELLOW + "Current ingredient: ");
					meta.setLore(lore);
					brewingWand.setItemMeta(meta);
					
					player.getWorld().spawnParticle(Particle.REDSTONE, cauldron.getLocation().add(0.5, 0.5, 0.5), 5, 0.5, 0.5, 0.5, new Particle.DustOptions(Color.RED, 1));
					
					return;
				case RABBIT_FOOT:
					cauldron.setMetadata("potion", new FixedMetadataValue(plugin, PotionType.MUNDANE.name()));
					
					data.remove(new NamespacedKey(plugin, "ingredient"));
					lore.set(3, ChatColor.YELLOW + "Current ingredient: ");
					meta.setLore(lore);
					brewingWand.setItemMeta(meta);
					
					player.getWorld().spawnParticle(Particle.REDSTONE, cauldron.getLocation().add(0.5, 0.5, 0.5), 5, 0.5, 0.5, 0.5, new Particle.DustOptions(Color.RED, 1));
					
					return;
				case BLAZE_POWDER:
					cauldron.setMetadata("potion", new FixedMetadataValue(plugin, PotionType.MUNDANE.name()));
					
					data.remove(new NamespacedKey(plugin, "ingredient"));
					lore.set(3, ChatColor.YELLOW + "Current ingredient: ");
					meta.setLore(lore);
					brewingWand.setItemMeta(meta);
					
					player.getWorld().spawnParticle(Particle.REDSTONE, cauldron.getLocation().add(0.5, 0.5, 0.5), 5, 0.5, 0.5, 0.5, new Particle.DustOptions(Color.RED, 1));
					
					return;
				case GLISTERING_MELON_SLICE:
					cauldron.setMetadata("potion", new FixedMetadataValue(plugin, PotionType.MUNDANE.name()));
					
					data.remove(new NamespacedKey(plugin, "ingredient"));
					lore.set(3, ChatColor.YELLOW + "Current ingredient: ");
					meta.setLore(lore);
					brewingWand.setItemMeta(meta);
					
					player.getWorld().spawnParticle(Particle.REDSTONE, cauldron.getLocation().add(0.5, 0.5, 0.5), 5, 0.5, 0.5, 0.5, new Particle.DustOptions(Color.RED, 1));
					
					return;
				case SUGAR:
					cauldron.setMetadata("potion", new FixedMetadataValue(plugin, PotionType.MUNDANE.name()));
					
					data.remove(new NamespacedKey(plugin, "ingredient"));
					lore.set(3, ChatColor.YELLOW + "Current ingredient: ");
					meta.setLore(lore);
					brewingWand.setItemMeta(meta);
					
					player.getWorld().spawnParticle(Particle.REDSTONE, cauldron.getLocation().add(0.5, 0.5, 0.5), 5, 0.5, 0.5, 0.5, new Particle.DustOptions(Color.RED, 1));
					
					return;
				case MAGMA_CREAM:
					cauldron.setMetadata("potion", new FixedMetadataValue(plugin, PotionType.MUNDANE.name()));
					
					data.remove(new NamespacedKey(plugin, "ingredient"));
					lore.set(3, ChatColor.YELLOW + "Current ingredient: ");
					meta.setLore(lore);
					brewingWand.setItemMeta(meta);
					
					player.getWorld().spawnParticle(Particle.REDSTONE, cauldron.getLocation().add(0.5, 0.5, 0.5), 5, 0.5, 0.5, 0.5, new Particle.DustOptions(Color.RED, 1));
					
					return;
				case REDSTONE:
					cauldron.setMetadata("potion", new FixedMetadataValue(plugin, PotionType.MUNDANE.name()));
					
					data.remove(new NamespacedKey(plugin, "ingredient"));
					lore.set(3, ChatColor.YELLOW + "Current ingredient: ");
					meta.setLore(lore);
					brewingWand.setItemMeta(meta);
					
					player.getWorld().spawnParticle(Particle.REDSTONE, cauldron.getLocation().add(0.5, 0.5, 0.5), 5, 0.5, 0.5, 0.5, new Particle.DustOptions(Color.RED, 1));
					
					return;
				case GLOWSTONE_DUST:
					cauldron.setMetadata("potion", new FixedMetadataValue(plugin, PotionType.THICK.name()));
					
					data.remove(new NamespacedKey(plugin, "ingredient"));
					lore.set(3, ChatColor.YELLOW + "Current ingredient: ");
					meta.setLore(lore);
					brewingWand.setItemMeta(meta);
					
					player.getWorld().spawnParticle(Particle.REDSTONE, cauldron.getLocation().add(0.5, 0.5, 0.5), 5, 0.5, 0.5, 0.5, new Particle.DustOptions(Color.RED, 1));
					
					return;
				case FERMENTED_SPIDER_EYE:
					cauldron.setMetadata("potion", new FixedMetadataValue(plugin, PotionType.WEAKNESS.name()));
					
					data.remove(new NamespacedKey(plugin, "ingredient"));
					lore.set(3, ChatColor.YELLOW + "Current ingredient: ");
					meta.setLore(lore);
					brewingWand.setItemMeta(meta);
					
					player.getWorld().spawnParticle(Particle.REDSTONE, cauldron.getLocation().add(0.5, 0.5, 0.5), 5, 0.5, 0.5, 0.5, new Particle.DustOptions(Color.RED, 1));
					
					return;
				default:
					break;
			}
		}
		
		player.sendMessage(ChatColor.RED + "That ingredient cannot be brewed into this potion using a cauldron.");
		
	}
	
	
	
	
	@EventHandler
	public void onSwapHandsWithIngredient(PlayerSwapHandItemsEvent event) {
		
		Player player = event.getPlayer();
		if (!Recipes.BREWING_WAND.matches(plugin, player.getInventory().getItemInMainHand())) return;

		if (!potionIngredients.contains(player.getInventory().getItemInOffHand().getType())) return;
		
		for (Recipes recipe : Recipes.values()) {
			if (recipe.matches(plugin, player.getInventory().getItemInOffHand())) return;
		}
		
		if (player.getInventory().getItemInOffHand().getAmount() != 1) return;
		
		ItemStack brewingWand = player.getInventory().getItemInMainHand();
		ItemMeta meta = brewingWand.getItemMeta();
		PersistentDataContainer data = meta.getPersistentDataContainer();
		
		
		
		Material loadedMaterial = player.getInventory().getItemInOffHand().getType();
		if (data.has(new NamespacedKey(plugin, "ingredient"), PersistentDataType.STRING)) {
			player.getInventory().setItemInOffHand(new ItemStack(Material.valueOf(data.get(new NamespacedKey(plugin, "ingredient"), PersistentDataType.STRING)), 1));
		} else {
			player.getInventory().setItemInOffHand(null);
		}
		
		data.set(new NamespacedKey(plugin, "ingredient"), PersistentDataType.STRING, loadedMaterial.name());
		List<String> lore = meta.getLore();
		String[] words = loadedMaterial.name().split("_");
		String matName = "";
		for (String word : words) {
			matName = matName + word.substring(0, 1) + word.substring(1).toLowerCase() + " ";
		}
		lore.set(3, ChatColor.YELLOW + "Current ingredient: " + matName);
		meta.setLore(lore);
		brewingWand.setItemMeta(meta);
		event.setCancelled(true);
		
		
	}
	

	@EventHandler
	public void onScoopPotion(CauldronLevelChangeEvent event) {

		Block cauldron = event.getBlock();
		
		if (!cauldron.hasMetadata("potion")) return;
		
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (!cauldron.getType().equals(Material.WATER_CAULDRON)) {
					cauldron.removeMetadata("potion", plugin);
				}
			}
		}, 1L);
		
		if (!event.getReason().equals(ChangeReason.BOTTLE_FILL)) return;
		
		if (!(event.getEntity() instanceof Player)) return;
		Player player = (Player) event.getEntity();
		event.setCancelled(true);
		
		Levelled cauldronData = (Levelled) cauldron.getBlockData();
		if (cauldronData.getLevel() > 1) {
			cauldronData.setLevel(Math.max(cauldronData.getLevel() - 1, 1));
			event.getBlock().setBlockData(cauldronData);
		} else {
			event.getBlock().setType(Material.CAULDRON);
		}
		
		ItemStack potion = new ItemStack(Material.POTION, 1);
		PotionMeta potMeta = (PotionMeta) potion.getItemMeta();
		potMeta.setBasePotionData(new PotionData(PotionType.valueOf(cauldron.getMetadata("potion").get(0).asString())));
		potion.setItemMeta(potMeta);
		
		if (player.getInventory().getItemInMainHand().getType().equals(Material.GLASS_BOTTLE)) {
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				public void run() {
					ItemStack newItem = null;
			    	newItem = player.getInventory().getItemInMainHand();
			    	if (newItem.getAmount() == 1) {
			    		player.getInventory().setItemInMainHand(null);
			    	} else {
				    	newItem.setAmount(newItem.getAmount() - 1);
				    	player.getInventory().setItemInMainHand(newItem);
			    	}
			    	
					player.getInventory().addItem(potion);
				}
			}, 1L);
		} else {
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				public void run() {
					ItemStack newItem = null;
			    	newItem = player.getInventory().getItemInOffHand();
			    	if (newItem.getAmount() == 1) {
			    		player.getInventory().setItemInOffHand(null);
			    	} else {
				    	newItem.setAmount(newItem.getAmount() - 1);
				    	player.getInventory().setItemInOffHand(newItem);
			    	}
			    	
					player.getInventory().addItem(potion);
				}
			}, 1L);
		}
		
	}
	
}
