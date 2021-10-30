package org.baugindustries.baugrpg.listeners;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.SignData;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

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
			String title = sign.getLocation().getBlockX() + "" + sign.getLocation().getBlockY() + "" + sign.getLocation().getBlockZ();
			
			File signfile = new File(plugin.getDataFolder() + File.separator + "shops.yml");
		 	FileConfiguration signconfig = YamlConfiguration.loadConfiguration(signfile);
		 	
		 	if (signconfig.contains(event.getBlock().getLocation().toString())) {//sign is a registered sign shop
		 		Player player2 = plugin.getServer().getPlayer(UUID.fromString((String) signconfig.get(title+"owner")));
		 		if (!player.equals(player2)) {
		 			player.sendMessage("You cannot break this sign shop");
		 			event.setCancelled(true);
		 		} else {
		 			signconfig.set(title, null);
		 			try {
						signconfig.save(signfile);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		 		}
		 	}
		}
	}
}
