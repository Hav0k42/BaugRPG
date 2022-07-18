package org.baugindustries.baugrpg.customitems.orcs.dark_alchemist.basic;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.CauldronLevelChangeEvent;
import org.bukkit.event.block.CauldronLevelChangeEvent.ChangeReason;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketEntityEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import org.bukkit.ChatColor;

public class FlamingBucketListener implements Listener {
	private Main plugin;
	
	public FlamingBucketListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onFillBucket(PlayerBucketFillEvent event) {
		
		if (!(Recipes.FLAMING_BUCKET.matches(plugin, event.getPlayer().getInventory().getItemInMainHand()) || Recipes.FLAMING_BUCKET.matches(plugin, event.getPlayer().getInventory().getItemInOffHand()))) return;
		
		if (event.getPlayer().getInventory().getItemInMainHand().getType().name().toLowerCase().contains("bucket") && event.getPlayer().getInventory().getItemInOffHand().getType().name().toLowerCase().contains("bucket")) {
			event.setCancelled(true);
			return;
		}
		
		if (event.getItemStack().getType().equals(Material.LAVA_BUCKET)) {
			event.setItemStack(plugin.itemManager.getFlamingLavaBucketItem());
			plugin.getServer().getScheduler().runTask(plugin, () -> event.getBlock().setType(Material.LAVA));
		} else {
			event.setCancelled(true);
		}
		
	}
	
	
	@EventHandler
	public void onPlacePowderShow(BlockPlaceEvent event) {//Emptying powdered snow and milk needs to get handled.
		if (!(Recipes.FLAMING_BUCKET.matches(plugin, event.getPlayer().getInventory().getItemInMainHand()) || Recipes.FLAMING_BUCKET.matches(plugin, event.getPlayer().getInventory().getItemInOffHand()))) return;
		
		if (event.getPlayer().getInventory().getItemInMainHand().getType().name().toLowerCase().contains("bucket") && event.getPlayer().getInventory().getItemInOffHand().getType().name().toLowerCase().contains("bucket")) {
			event.setCancelled(true);
			return;
		}
	}
	
	@EventHandler
	public void onDrinkMilk(PlayerItemConsumeEvent event) {//Emptying powdered snow and milk needs to get handled.
		if (!(Recipes.FLAMING_BUCKET.matches(plugin, event.getPlayer().getInventory().getItemInMainHand()) || Recipes.FLAMING_BUCKET.matches(plugin, event.getPlayer().getInventory().getItemInOffHand()))) return;
		
		if (event.getPlayer().getInventory().getItemInMainHand().getType().name().toLowerCase().contains("bucket") && event.getPlayer().getInventory().getItemInOffHand().getType().name().toLowerCase().contains("bucket")) {
			event.setCancelled(true);
			return;
		}
	}
	
	@EventHandler
	public void onEmptyBucket(PlayerBucketEmptyEvent event) {//Emptying powdered snow and milk needs to get handled.
		if (!(Recipes.FLAMING_BUCKET.matches(plugin, event.getPlayer().getInventory().getItemInMainHand()) || Recipes.FLAMING_BUCKET.matches(plugin, event.getPlayer().getInventory().getItemInOffHand()))) return;
		
		if (event.getPlayer().getInventory().getItemInMainHand().getType().name().toLowerCase().contains("bucket") && event.getPlayer().getInventory().getItemInOffHand().getType().name().toLowerCase().contains("bucket")) {
			event.setCancelled(true);
			return;
		}
		
		event.setItemStack(plugin.itemManager.getFlamingBucketItem());
		
	}
	
	@EventHandler
	public void onUseBucketEntity(PlayerBucketEntityEvent event) {
		if (!Recipes.FLAMING_BUCKET.matches(plugin, event.getOriginalBucket())) return;
		
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onFillCauldron(CauldronLevelChangeEvent event) {
		if (!event.getReason().equals(ChangeReason.BUCKET_EMPTY)) return;
		Player player = (Player) event.getEntity();
		if (!(Recipes.FLAMING_BUCKET.matches(plugin, player.getInventory().getItemInMainHand()) || Recipes.FLAMING_BUCKET.matches(plugin, player.getInventory().getItemInOffHand()))) return;
		
		player.sendMessage(ChatColor.RED + "You cannot use flaming buckets with cauldrons");
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onEmptyCauldron(CauldronLevelChangeEvent event) {
		if (!event.getReason().equals(ChangeReason.BUCKET_FILL)) return;
		Player player = (Player) event.getEntity();
		if (!(Recipes.FLAMING_BUCKET.matches(plugin, player.getInventory().getItemInMainHand()) || Recipes.FLAMING_BUCKET.matches(plugin, player.getInventory().getItemInOffHand()))) return;
		
		player.sendMessage(ChatColor.RED + "You cannot use flaming buckets with cauldrons");
		event.setCancelled(true);
	}

}
