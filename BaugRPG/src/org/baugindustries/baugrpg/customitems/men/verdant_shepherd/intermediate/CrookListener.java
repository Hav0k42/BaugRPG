package org.baugindustries.baugrpg.customitems.men.verdant_shepherd.intermediate;

import java.util.HashMap;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.world.EntitiesLoadEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import org.bukkit.ChatColor;


public class CrookListener implements Listener {

	
	private Main plugin;
	
	HashMap<UUID, Animals> crookedAnimals;
	public HashMap<Animals, Location> originalLoc;
	
	public CrookListener(Main plugin) {
		this.plugin = plugin;
		crookedAnimals = new HashMap<UUID, Animals>();
		originalLoc = new HashMap<Animals, Location>();
	}
	
	
	@EventHandler
	public void onPickUpAnimal(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		
		if (!(event.getRightClicked() instanceof Animals)) return;
		
		Animals animal = (Animals) event.getRightClicked();
		
		if (animal instanceof Vehicle && !player.isSneaking()) return; 
		
		ItemStack crook = null;
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!Recipes.CROOK.matches(plugin, player.getInventory().getItemInMainHand())) return;
			crook = player.getInventory().getItemInMainHand();
		} else {
			if (!Recipes.CROOK.matches(plugin, player.getInventory().getItemInOffHand())) return;
			crook = player.getInventory().getItemInOffHand();
		}
		
		ItemMeta crookMeta = crook.getItemMeta();
		
		UUID crookUUID = UUID.fromString(crookMeta.getPersistentDataContainer().get(new NamespacedKey(plugin, "UUID"), PersistentDataType.STRING));
		
		
		if (crookedAnimals.containsKey(crookUUID)) {
			player.sendMessage(ChatColor.RED + "This crook already has an animal.");
			return;
		}
		
		originalLoc.put(animal, animal.getLocation());
		

		player.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, event.getRightClicked().getLocation(), 4, 0.5, 0.5, 0.5);
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, SoundCategory.MASTER, 2f, 1f);
		
		animal.setInvisible(true);
		animal.setInvulnerable(true);
		animal.setGravity(false);
		Location tpLoc = animal.getLocation();
		tpLoc.setY(-70);
		animal.teleport(tpLoc);
		

		
		
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			  public void run() {
					crookedAnimals.put(crookUUID, (Animals)event.getRightClicked());
			  }
		 }, 1L);
		
	}
	
	@EventHandler
	public void onPlaceAnimal(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		
		
		
		ItemStack crook = null;
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!Recipes.CROOK.matches(plugin, player.getInventory().getItemInMainHand())) return;
			crook = player.getInventory().getItemInMainHand();
		} else {
			if (!Recipes.CROOK.matches(plugin, player.getInventory().getItemInOffHand())) return;
			crook = player.getInventory().getItemInOffHand();
		}
		
		ItemMeta crookMeta = crook.getItemMeta();
		
		UUID crookUUID = UUID.fromString(crookMeta.getPersistentDataContainer().get(new NamespacedKey(plugin, "UUID"), PersistentDataType.STRING));
		
		event.setCancelled(true);
		
		
		
		if (!crookedAnimals.containsKey(crookUUID)) return;
		
		Animals animal = crookedAnimals.get(crookUUID);
		Location tpLoc = player.getLastTwoTargetBlocks(null, 10).get(0).getLocation();
		if (animal.getLocation().getChunk().isEntitiesLoaded()) {
			animal.teleport(tpLoc);
			animal.setInvisible(false);
			animal.setInvulnerable(false);
			animal.setGravity(true);
		} else {
			animal.getLocation().getChunk().getEntities();
			animal.teleport(tpLoc);
			animal.setInvisible(false);
			animal.setInvulnerable(false);
			animal.setGravity(true);
		}
		

		player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, tpLoc, 10, 0.5, 0.5, 0.5);
		player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.MASTER, 2f, 1f);
		
		originalLoc.remove(animal);
		crookedAnimals.remove(crookUUID);
		
		
		
	}
	
	
	@EventHandler
	public void onLoadAnimal(EntitiesLoadEvent event) {//This could very possibly become extremely laggy. Recommend semi-frequent server resets to deal with that, OR add a one hour timer to remove players from the horse map.
		for (Entity entity : event.getEntities()) {
			if (entity instanceof Animals) {
				Animals animal = (Animals) entity;
				for (UUID crookUUID : crookedAnimals.keySet()) {
					if (animal.getUniqueId().equals(crookedAnimals.get(crookUUID).getUniqueId())) {
						crookedAnimals.put(crookUUID, animal);
					}
				}
			}
		}
	}

}
