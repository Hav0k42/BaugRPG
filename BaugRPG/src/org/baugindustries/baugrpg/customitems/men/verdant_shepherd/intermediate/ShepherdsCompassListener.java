package org.baugindustries.baugrpg.customitems.men.verdant_shepherd.intermediate;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class ShepherdsCompassListener implements Listener {
	private Main plugin;
	
	public ShepherdsCompassListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onBindCompass(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		
		
		
		ItemStack compass = null;
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!Recipes.SHEPHERDS_COMPASS.matches(plugin, player.getInventory().getItemInMainHand())) return;
			compass = player.getInventory().getItemInMainHand();
		} else {
			if (!Recipes.SHEPHERDS_COMPASS.matches(plugin, player.getInventory().getItemInOffHand())) return;
			compass = player.getInventory().getItemInOffHand();
		}
		
		CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();
		compassMeta.setLodestone(event.getClickedBlock().getLocation());
		compassMeta.setLodestoneTracked(false);
		compass.setItemMeta(compassMeta);
		
	}
	

	@EventHandler
	public void onChangeCoordsHud(PlayerMoveEvent event) {
		if (event.getFrom().getBlock().equals(event.getTo().getBlock())) return;
		
		Player player = event.getPlayer();
		
		if (!Recipes.SHEPHERDS_COMPASS.playerIsCarrying(player, plugin)) return;
		
		TextComponent message = new TextComponent("Coords: " + event.getTo().getBlockX() + ", " + event.getTo().getBlockY() + ", " + event.getTo().getBlockZ() + ", Facing: " + player.getFacing().toString().substring(0, 1) + player.getFacing().toString().toLowerCase().substring(1));
		message.setColor(ChatColor.GOLD);
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, message);
		
	}

}
