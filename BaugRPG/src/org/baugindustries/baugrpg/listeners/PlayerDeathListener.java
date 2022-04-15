package org.baugindustries.baugrpg.listeners;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.persistence.PersistentDataType;

import net.md_5.bungee.api.ChatColor;

public class PlayerDeathListener implements Listener{

	
	private Main plugin;
	public PlayerDeathListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void PlayerDeathEvent(PlayerDeathEvent event) {
		Player player = event.getEntity();
		if (player.getKiller() != null) {
			Player killer = (Player) player.getKiller();
			
			int playerRace = plugin.getRace(player);
			int killerRace = plugin.getRace(killer);
			
			if (killerRace == 3 && playerRace == 2) {//Dwarf killed an elf
				killer.getWorld().dropItemNaturally(player.getLocation(), plugin.createItem(Material.GOLD_INGOT, 1 + (int)(Math.random() * 3)));
			}
			
			if (playerRace > 0 && killerRace > 0 && playerRace != killerRace) {
				File claimsFile = new File(plugin.getDataFolder() + File.separator + "claims.yml");
				FileConfiguration claimsConfig = YamlConfiguration.loadConfiguration(claimsFile);
				
				String killerRaceString = plugin.getRaceString(killerRace);
				String playerRaceString = plugin.getRaceString(playerRace);
				
				int availableChunks;
				if (claimsConfig.contains(killerRaceString + "TotalChunks")) {
					availableChunks = claimsConfig.getInt(killerRaceString + "TotalChunks") + 1;
				} else {
					availableChunks = plugin.startingClaimChunks + 1;
				}
				claimsConfig.set(killerRaceString + "TotalChunks", availableChunks);
				
				if (claimsConfig.contains(playerRaceString + "TotalChunks")) {
					availableChunks = claimsConfig.getInt(playerRaceString + "TotalChunks") - 1;
				} else {
					availableChunks = plugin.startingClaimChunks - 1;
				}

				
				claimsConfig.set(playerRaceString + "TotalChunks", availableChunks);
				
				try {
					claimsConfig.save(claimsFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if (plugin.orcVictim != null && plugin.orcVictim.equals(player.getUniqueId())) {
				plugin.orcVictim = null;
			}
		} else if (plugin.orcVictim != null && plugin.orcVictim.equals(player.getUniqueId())) {
			event.setDeathMessage(ChatColor.DARK_RED + player.getDisplayName() + ChatColor.WHITE + " tried to escape the inevitable.");
		}
		
		File file = new File(plugin.getDataFolder() + File.separator + "config.yml");
	 	FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		List<ItemStack> drops = event.getDrops();
		if (config.contains("hardcoreDeathOn") && config.getBoolean("hardcoreDeathOn")) {
			drops.forEach(drop -> {
				drops.remove(drop);
			});
		} else if (!config.contains("mediumCoreDeathOn") || config.getBoolean("mediumCoreDeathOn")) {
			Recipes[] values = Recipes.values();
			ItemFactory factory = plugin.getServer().getItemFactory();
			for (int c = drops.size() - 1; c >= 0; c--) {
				ItemStack drop = drops.get(c);
				boolean passed = false;
				if (drop.getType().getMaxDurability() > 10) {
					passed = true;
				} else {
					for (int i = 0; i < values.length; i++) {
						ItemStack customItem = values[i].getResult(plugin);
						customItem.setAmount(drop.getAmount());
						if (customItem.equals(drop)) {
							passed = true;
							break;
						}
					}
				}
				
				if (!passed) {
					drops.remove(c);
				}
			}
		}
		
		
		int race = player.getPersistentDataContainer().get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER);
	    
	    switch (race) {
	    	case 0://normie
	    		break;
	    	case 1://man
	    		break;
	    	case 2://elf
	    		if (player.getLastDamageCause().getCause().equals(DamageCause.CUSTOM)) {
    				event.setDeathMessage(ChatColor.DARK_GREEN + player.getDisplayName() + ChatColor.WHITE + " attempted to enter the Nether unprotected.");
    			}
	    		break;
	    	case 3://dwarf
	    		break;
	    	case 4://orc
	    		if (player.getLocation().getBlock().getLightFromSky() != 0) {
	    			if (player.getLastDamageCause().getCause().equals(DamageCause.FIRE_TICK)) {
	    				event.setDeathMessage(ChatColor.DARK_RED + player.getDisplayName() + ChatColor.WHITE + " attempted to venture into the sun");
	    			}
	    		}
	    		
	    		break;
	    	case 5://wizard
	    		break;
	    }
	}
	
	
}
