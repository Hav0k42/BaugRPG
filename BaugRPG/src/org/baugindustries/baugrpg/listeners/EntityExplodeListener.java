package org.baugindustries.baugrpg.listeners;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityExplodeListener implements Listener {
	private Main plugin;
	public EntityExplodeListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onExplodeChest(EntityExplodeEvent event) {
		File signfile = new File(plugin.getDataFolder() + File.separator + "shops.yml");
	 	FileConfiguration signconfig = YamlConfiguration.loadConfiguration(signfile);
		
	 	if (event.getEntityType().equals(EntityType.CREEPER)) {
	 		File file = new File(plugin.getDataFolder() + File.separator + "config.yml");
		 	FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		 	
			if ((config.contains("allowCreeperGriefing") && !config.getBoolean("allowCreeperGriefing"))) {
				List<Block> blocks = new ArrayList<Block>();

				for (int i = 0; i < event.blockList().size(); i++) {
					blocks.add(event.blockList().get(i));
				}
				
				for (Block block : blocks) {
					event.blockList().remove(block);
				}
			}
	 	}
	 	
	 	if (event.getEntityType().equals(EntityType.PRIMED_TNT) || event.getEntityType().equals(EntityType.MINECART_TNT)) {
	 		File file = new File(plugin.getDataFolder() + File.separator + "config.yml");
		 	FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		 	
			if ((config.contains("allowTntGriefing") && !config.getBoolean("allowTntGriefing"))) {
				List<Block> blocks = new ArrayList<Block>();

				for (int i = 0; i < event.blockList().size(); i++) {
					blocks.add(event.blockList().get(i));
				}
				
				for (Block block : blocks) {
					event.blockList().remove(block);
				}
			}
			
	 	}
	 	
	 	if (event.getEntityType().equals(EntityType.FIREBALL)) {
	 		File file = new File(plugin.getDataFolder() + File.separator + "config.yml");
		 	FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		 	
			if ((config.contains("allowGhastGriefing") && !config.getBoolean("allowGhastGriefing"))) {
				List<Block> blocks = new ArrayList<Block>();

				for (int i = 0; i < event.blockList().size(); i++) {
					blocks.add(event.blockList().get(i));
				}
				
				for (Block block : blocks) {
					event.blockList().remove(block);
				}
			}
			
	 	}
		
	 	
		ArrayList<Location> loc = new ArrayList<Location>();
		
		List<Block> badBlocks = new ArrayList<Block>();
		
		for (int c = 0; c < event.blockList().size(); c++) {
		
			Block block = event.blockList().get(c);
			
			loc.add(block.getLocation().add(1, 0, 0));
			loc.add(block.getLocation().add(-1, 0, 0));
			loc.add(block.getLocation().add(0, 0, -1));
			loc.add(block.getLocation().add(0, 0, 1));
			for (int i = 0; i < loc.size(); i++) {
				String title = loc.get(i).getBlockX() + "" + loc.get(i).getBlockY() + "" + loc.get(i).getBlockZ();
				if (signconfig.contains(title)) {
					Location chestLocation = new Location(block.getWorld(), (int)signconfig.get(title + "chestX"), (int)signconfig.get(title + "chestY"), (int)signconfig.get(title + "chestZ"));
					if (chestLocation.equals(block.getLocation())) {
						badBlocks.add(block);
					}
					
				}
			}
			
			
			
			if (block.getState() instanceof Sign) {
				
				
				Sign sign = (Sign)block.getState();
				String title = sign.getLocation().getBlockX() + "" + sign.getLocation().getBlockY() + "" + sign.getLocation().getBlockZ();
			 	if (signconfig.contains(title)) {//sign is a registered sign shop
			 		badBlocks.add(block);
			 	}
			}
		}
		
		for (Block block : badBlocks) {
			event.blockList().remove(block);
		}
	}
}
