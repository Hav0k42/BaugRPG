package org.baugindustries.baugrpg.listeners;

import java.io.File;
import java.io.IOException;

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
			
			File signfile = new File(plugin.getDataFolder() + File.separator + "shops.yml");
		 	FileConfiguration signconfig = YamlConfiguration.loadConfiguration(signfile);
		 	
		 	if (signconfig.contains(event.getBlock().getLocation().toString())) {//sign is a registered sign shop
		 		SignData signData = (SignData)signconfig.get(sign.getLocation().toString());
		 		if (!player.equals(signData.getOwner())) {
		 			player.sendMessage("You cannot break this sign shop");
		 			event.setCancelled(true);
		 		} else {
		 			signconfig.set(event.getBlock().getLocation().getBlockX() + "" + event.getBlock().getLocation().getBlockY() + "" + event.getBlock().getLocation().getBlockZ(), null);
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
