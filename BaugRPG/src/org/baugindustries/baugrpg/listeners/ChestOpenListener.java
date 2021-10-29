package org.baugindustries.baugrpg.listeners;

import java.io.File;
import java.util.ArrayList;


import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.SignData;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import com.sun.tools.javac.util.List;

import net.md_5.bungee.api.ChatColor;

public class ChestOpenListener implements Listener{
	private Main plugin;
	public ChestOpenListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInteractChest(PlayerInteractEvent event) {
		File signfile = new File(plugin.getDataFolder() + File.separator + "shops.yml");
	 	FileConfiguration signconfig = YamlConfiguration.loadConfiguration(signfile);
		
		Player player = event.getPlayer();
		ArrayList<Location> loc = new ArrayList<Location>();
		loc.add(event.getClickedBlock().getLocation().add(1, 0, 0));
		loc.add(event.getClickedBlock().getLocation().add(-1, 0, 0));
		loc.add(event.getClickedBlock().getLocation().add(0, 0, -1));
		loc.add(event.getClickedBlock().getLocation().add(0, 0, 1));
		for (int i = 0; i < loc.size(); i++) {
			if (signconfig.contains(loc.get(i).toString())) {
				SignData signData = (SignData)signconfig.get(loc.get(i).getBlockX() + "" + loc.get(i).getBlockY() + "" + loc.get(i).getBlockZ());
				if (signData.getChestLocation().equals(event.getClickedBlock().getLocation())) {
					if (!player.equals(signData.getOwner())) {
						player.sendMessage(ChatColor.RED + "This chest is locked.");
						event.setCancelled(true);
					}
				}
				
			}
		}
		
	}
}
