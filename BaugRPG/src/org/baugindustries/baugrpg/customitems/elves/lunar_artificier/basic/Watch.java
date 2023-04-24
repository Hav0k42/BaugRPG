package org.baugindustries.baugrpg.customitems.elves.lunar_artificier.basic;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class Watch implements Listener {

	private Main plugin;
	
	
	public Watch(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if (event.getHand() == null) return;
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!Recipes.WATCH.matches(plugin, player.getInventory().getItemInMainHand())) return;
		} else {
			if (!Recipes.WATCH.matches(plugin, player.getInventory().getItemInOffHand())) return;
		}
		
		World world = plugin.getServer().getWorlds().get(0);
		long time = world.getTime();
		int hour = ((int) (time / 1000)) + 8;
		if (hour > 24) {
			hour -= 24;
		}
		
		String relativeText = "AM";
		if (hour > 11 && hour < 24) {
			hour -= 12;
			relativeText = "PM";
		}
		
		int minutes = (int)(((time % 1000.0) / 1000.0) * 60.0);
		
		String minutesText = minutes + "";
		if (minutes < 10) {
			minutesText = "0" + minutes;
		}
		
		
		
		int days = (int) world.getFullTime() / 24000;
		int phase = days % 8;//0 is full moon, 4 is new moon
		
		String moonPhase = "full moon";
		switch (phase) {
			case 0:
				moonPhase = "full moon";
				break;
			case 1:
				moonPhase = "waning gibbous";
				break;
			case 2:
				moonPhase = "third quarter";
				break;
			case 3:
				moonPhase = "waning crescent";
				break;
			case 4:
				moonPhase = "new moon";
				break;
			case 5:
				moonPhase = "waxing crescent";
				break;
			case 6:
				moonPhase = "first quarter";
				break;
			case 7:
				moonPhase = "waxing gibbous";
				break;
		}
		
		player.sendMessage(ChatColor.YELLOW + "It is currently: " + hour + ":" + minutesText + " " + relativeText + ". The moon is currently a " + moonPhase + ".");
		
	}
}
