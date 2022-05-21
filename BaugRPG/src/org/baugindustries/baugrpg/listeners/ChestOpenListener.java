package org.baugindustries.baugrpg.listeners;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import net.md_5.bungee.api.ChatColor;

public class ChestOpenListener implements Listener{
	private Main plugin;
	public ChestOpenListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInteractChest(PlayerInteractEvent event) {
		
		if (!(event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
			return;
		}
		
		File signfile = new File(plugin.getDataFolder() + File.separator + "shops.yml");
	 	FileConfiguration signconfig = YamlConfiguration.loadConfiguration(signfile);
		
		Player player = event.getPlayer();
		ArrayList<Location> loc = new ArrayList<Location>();
		loc.add(event.getClickedBlock().getLocation().add(1, 0, 0));
		loc.add(event.getClickedBlock().getLocation().add(-1, 0, 0));
		loc.add(event.getClickedBlock().getLocation().add(0, 0, -1));
		loc.add(event.getClickedBlock().getLocation().add(0, 0, 1));
		for (int i = 0; i < loc.size(); i++) {
			String title = loc.get(i).getBlockX() + "" + loc.get(i).getBlockY() + "" + loc.get(i).getBlockZ() + loc.get(i).getWorld().getUID();
			if (signconfig.contains(title)) {
				Location chestLocation = signconfig.getConfigurationSection(title).getLocation("chestLoc");
				if (chestLocation.equals(event.getClickedBlock().getLocation())) {
					if (!player.getUniqueId().equals((UUID.fromString((String) signconfig.getConfigurationSection(title).getString("owner"))))) {
						player.sendMessage(ChatColor.RED + "This chest is locked.");
						event.setCancelled(true);
					}
				}
				
			}
		}
	}
}
