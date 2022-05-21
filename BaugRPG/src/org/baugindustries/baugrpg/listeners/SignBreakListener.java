package org.baugindustries.baugrpg.listeners;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import net.md_5.bungee.api.ChatColor;

public class SignBreakListener implements Listener{
	private Main plugin;
	public SignBreakListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onBreakSign(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (event.getBlock().getState() instanceof Sign) {
			Sign sign = (Sign)event.getBlock().getState();
			String title = sign.getLocation().getBlockX() + "" + sign.getLocation().getBlockY() + "" + sign.getLocation().getBlockZ() + "" + sign.getWorld().getUID();
			
			File signfile = new File(plugin.getDataFolder() + File.separator + "shops.yml");
		 	FileConfiguration signconfig = YamlConfiguration.loadConfiguration(signfile);
		 	
		 	if (signconfig.contains(title)) {//sign is a registered sign shop
		 		OfflinePlayer player2 = plugin.getServer().getOfflinePlayer(UUID.fromString((String) signconfig.getConfigurationSection(title).getString("owner")));
		 		if (!player.getUniqueId().equals(player2.getUniqueId())) {
		 			player.sendMessage(ChatColor.RED + "You cannot break this sign shop");
		 			event.setCancelled(true);
		 		} else {
		 			signconfig.set(title, null);
		 			try {
						signconfig.save(signfile);
					} catch (IOException e) {
						e.printStackTrace();
					}
		 		}
		 	}
		}
	}
}
