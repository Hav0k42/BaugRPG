package org.baugindustries.baugrpg.customitems.dwarves.radiant_metallurgist.intermediate;

import java.util.HashMap;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Snowman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.BlockIterator;

import org.bukkit.ChatColor;

public class RadiantBoreListener implements Listener {
	private Main plugin;
	
	HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();
	int cooldownTime = 10000;//10 seconds
	
	private int min = 6;
	private int max = 25; 
	
	private int tpSteps = 8;
	
	public RadiantBoreListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(PlayerInteractEvent event) {
		if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		
		Player player = event.getPlayer();
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!Recipes.RADIANT_BORE.matches(plugin, player.getInventory().getItemInMainHand())) return;
		} else {
			if (!Recipes.RADIANT_BORE.matches(plugin, player.getInventory().getItemInOffHand())) return;
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
		
		event.setCancelled(true);
		
		BlockIterator iter = new BlockIterator(player, max);
		
		while (!iter.next().equals(event.getClickedBlock())) { }
		
		for (int i = 0; i < min; i++) {
			Block block = iter.next();
			if (block.isPassable() && !block.isLiquid()) {
				return;
			}
		}
		
		while (iter.hasNext()) {
			Block block = iter.next();
			if (block.isPassable() && !block.isLiquid()) {
				if (isBlockSafe(block)) {
					borePlayer(player, block.getLocation().add(0.5, 0.5, 0.5));
					cooldown.put(player.getUniqueId(), System.currentTimeMillis() + cooldownTime);
					return;
				}
			}
		}
		
		
	}
	
	@EventHandler
	public void onShearSheep(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		
		if (!((event.getRightClicked() instanceof Sheep) || (event.getRightClicked() instanceof MushroomCow) || (event.getRightClicked() instanceof Snowman))) return;
		
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!Recipes.RADIANT_BORE.matches(plugin, player.getInventory().getItemInMainHand())) return;
		} else {
			if (!Recipes.RADIANT_BORE.matches(plugin, player.getInventory().getItemInOffHand())) return;
		}
		

		event.setCancelled(true);
		event.getPlayer().sendMessage(ChatColor.RED + "This item cannot be used like that");
	}
	
	private boolean isBlockSafe(Block block) {
		if (!(block.getRelative(0, 1, 0).isPassable() && !block.getRelative(0, 1, 0).isLiquid())) return false;
		if (!(!block.getRelative(0, -1, 0).isPassable() && !block.getRelative(0, -1, 0).isLiquid())) return false;
		
		return true;
	}
	
	private void borePlayer(Player player, Location destination) {
		Location initLoc = player.getLocation();
		for (int i = 0; i <= tpSteps; i++) {
			double x = (((destination.getX() - initLoc.getX()) * i) / tpSteps) + initLoc.getX();
			double y = (((destination.getY() - initLoc.getY()) * i) / tpSteps) + initLoc.getY();
			double z = (((destination.getZ() - initLoc.getZ()) * i) / tpSteps) + initLoc.getZ();
			Location newLoc = new Location(player.getWorld(), x, y, z, initLoc.getYaw(), initLoc.getPitch());
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				  public void run() {
						player.teleport(newLoc);
						player.getWorld().playSound(newLoc, Sound.BLOCK_ROOTED_DIRT_BREAK, SoundCategory.MASTER, 2f, 1f);
				  }
			 }, i);
		}
	}

}
