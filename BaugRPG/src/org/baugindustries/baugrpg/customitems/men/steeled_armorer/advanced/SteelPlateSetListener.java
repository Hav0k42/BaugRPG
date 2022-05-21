package org.baugindustries.baugrpg.customitems.men.steeled_armorer.advanced;

import java.util.ArrayList;
import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class SteelPlateSetListener implements Listener {
	private Main plugin;
	List<Player> stillPlayers;
	
	public SteelPlateSetListener(Main plugin) {
		this.plugin = plugin;
		stillPlayers = new ArrayList<Player>();
	}
	
	
	@EventHandler
	public void onInventoryClick(PlayerMoveEvent event) {
		if (event.getFrom().toVector().equals(event.getTo().toVector())) return;
		Player player = event.getPlayer();
		if (stillPlayers.contains(player)) {
			player.removePotionEffect(PotionEffectType.REGENERATION);
			player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
			stillPlayers.remove(player);
			return;
		}
		
		if (!Recipes.STEEL_PLATE_HELMET.matches(plugin, player.getInventory().getHelmet())) return;
		if (!Recipes.STEEL_PLATE_CHESTPIECE.matches(plugin, player.getInventory().getChestplate())) return;
		if (!Recipes.STEEL_PLATE_LEGGINGS.matches(plugin, player.getInventory().getLeggings())) return;
		if (!Recipes.STEEL_PLATE_GREAVES.matches(plugin, player.getInventory().getBoots())) return;
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {//I'm aware this is super hacky, the way I would've liked to do it was extremely stuttery and only activated after the player turned their head. Something with the way player move events update. A player can move a little bit after having stopped moving and it updates with a head move. Idk. This works. Teleporting the player in place artificially updates their location to negate the effects of the player updating it to a different location by turning their head.
			  public void run() {
				  final Vector initPos = player.getLocation().toVector();
				  plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					  public void run() {
							if (player.getLocation().toVector().equals(initPos) && !stillPlayers.contains(player)) {
								stillPlayers.add(player);
								player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20000000, 0));
								player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20000000, 0));
								player.teleport(player.getLocation());
							}
					  }
				 }, 10L);
			  }
		 }, 10L);
	}

}
