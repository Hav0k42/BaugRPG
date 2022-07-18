package org.baugindustries.baugrpg.listeners;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import org.bukkit.ChatColor;

public class OnJoinListener implements Listener {
	private int saturationSlownessMultiplier = 40;
	
	public HashMap<UUID, PermissionAttachment> perms = new HashMap<UUID, PermissionAttachment>();
	
	private Main plugin;
	public OnJoinListener(Main plugin) {
		this.plugin = plugin;
	}
	
	public int getSaturationSlownessMultiplier() {
		return saturationSlownessMultiplier;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		plugin.playerEnterChunkListener.updateLocation(player.getUniqueId());
		
		//Check if player is listed in wallet yml.
		File econfile = new File(plugin.getDataFolder() + File.separator + "econ.yml");
	 	FileConfiguration econconfig = YamlConfiguration.loadConfiguration(econfile);
	 	if (econconfig.get(event.getPlayer().getUniqueId().toString()) == null) {
	 		econconfig.set(event.getPlayer().getUniqueId().toString(), 0);
	 	}
	 	
	 	try {
			econconfig.save(econfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 	
	 	File bankfile = new File(plugin.getDataFolder() + File.separator + "bank.yml");
	 	FileConfiguration bankconfig = YamlConfiguration.loadConfiguration(bankfile);
	 	if (bankconfig.get(event.getPlayer().getUniqueId().toString()) == null) {
	 		bankconfig.set(event.getPlayer().getUniqueId().toString(), 0);
	 	}
	 	
	 	try {
			bankconfig.save(bankfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 	
	 	if (player.getGameMode().equals(GameMode.SPECTATOR)) {
	 		player.setGameMode(GameMode.SURVIVAL);
	 	}
	 		
	 	if (plugin.steeledResolveDisconnectedPlayers.contains(player.getUniqueId())) {
	 		player.teleport(plugin.steeledResolveInitLoc.get(player.getUniqueId()));
	 		plugin.steeledResolveDisconnectedPlayers.remove(player.getUniqueId());
	 	}
	 	
	 	File steeledResolveDisconnectedDatafile = new File(plugin.getDataFolder() + File.separator + "steeledResolveDisconnectedData.yml");
		FileConfiguration steeledResolveDisconnectedDataconfig = YamlConfiguration.loadConfiguration(steeledResolveDisconnectedDatafile);
		
		if (steeledResolveDisconnectedDataconfig.contains(player.getUniqueId().toString())) {
			player.teleport(steeledResolveDisconnectedDataconfig.getLocation(player.getUniqueId().toString()));
			steeledResolveDisconnectedDataconfig.set(player.getUniqueId().toString(), null);
		}
		
		
	 	
		
	 	File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + event.getPlayer().getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
	 	
	 	
		 
		//Check to see if the file already exists. If not, create it.
		if (!skillsfile.exists()) {
			try {
				skillsfile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			skillsconfig.set("totalSkillPoints", 0);
			skillsconfig.set("skillPoints", 0);
			skillsconfig.set("speed", 0);
			skillsconfig.set("jump", 0);
			skillsconfig.set("damage", 0);
			skillsconfig.set("resistance", 0);
			skillsconfig.set("mining", 0);
			skillsconfig.set("regen", 0);
			skillsconfig.set("swim", 0);
			
			skillsconfig.set("speedOn", false);
			skillsconfig.set("jumpOn", false);
			skillsconfig.set("damageOn", false);
			skillsconfig.set("resistanceOn", false);
			skillsconfig.set("miningOn", false);
			skillsconfig.set("regenOn", false);
			skillsconfig.set("swimOn", false);
			
			try {
				skillsconfig.save(skillsfile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			float maxSpeed = 0.35f;
			
			if (skillsconfig.getBoolean("speedOn")) {
				player.setWalkSpeed((((maxSpeed - 0.2f) / 10f) * skillsconfig.getInt("speed")) + 0.2f);
				player.setFlySpeed((((maxSpeed - 0.2f) / 10f) * skillsconfig.getInt("speed")) + 0.2f);
			} else {
				player.setWalkSpeed(0.2f);
				player.setFlySpeed(0.2f);
			}
			
			if (skillsconfig.getBoolean("regenOn")) {
				player.setSaturatedRegenRate(saturationSlownessMultiplier * (int)(((skillsconfig.getInt("regen") * -5f) / 9f) + (95f/9f)));
				player.setUnsaturatedRegenRate(saturationSlownessMultiplier * (int)(((skillsconfig.getInt("regen") * -40f) / 9f) + (760f/9f)));
			} else {
				player.setSaturatedRegenRate(saturationSlownessMultiplier * 10);
				player.setUnsaturatedRegenRate(saturationSlownessMultiplier * 80);
			}
		}
		
		//Player has not joined before, and needs to get a race assigned.
		PersistentDataContainer data = event.getPlayer().getPersistentDataContainer();
		File file = new File(plugin.getDataFolder() + File.separator + "config.yml");
	 	FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (!event.getPlayer().hasPlayedBefore() || !data.has(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER)) {
			
			File manualRaceAssigningfile = new File(plugin.getDataFolder() + File.separator + "raceAssingments.yml");
		 	FileConfiguration manualRaceAssigningconfig = YamlConfiguration.loadConfiguration(manualRaceAssigningfile);
			if (manualRaceAssigningconfig.contains(player.getName())) {
				int race = manualRaceAssigningconfig.getInt(player.getName());
				data.set(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER, race);//Men: 1, Elves: 2, Dwarves: 3, Orcs: 4
				
				player.closeInventory();
				
				File claimsFile = new File(plugin.getDataFolder() + File.separator + "claims.yml");
				FileConfiguration claimsConfig = YamlConfiguration.loadConfiguration(claimsFile);
				
				switch (race) {
					case 1://Men
						player.teleport(claimsConfig.getLocation("menSpawn"));
						player.sendMessage(ChatColor.GOLD + "You've been automatically placed in the race of " + ChatColor.DARK_AQUA + "Men" + ChatColor.GOLD + ".");
						plugin.board.getTeam("Men").addEntry(player.getName());
						break;
					case 2://Elves
						player.teleport(claimsConfig.getLocation("elfSpawn"));
						player.sendMessage(ChatColor.GOLD + "You've been automatically placed in the race of " + ChatColor.DARK_GREEN + "Elves" + ChatColor.GOLD + ".");
						plugin.board.getTeam("Elves").addEntry(player.getName());
						break;
					case 3://Dwarves
						player.teleport(claimsConfig.getLocation("dwarfSpawn"));
						player.sendMessage(ChatColor.GOLD + "You've been automatically placed in the race of " + ChatColor.DARK_PURPLE + "Dwarves" + ChatColor.GOLD + ".");
						plugin.board.getTeam("Dwarves").addEntry(player.getName());
						break;
					case 4://Orcs
						player.teleport(claimsConfig.getLocation("orcSpawn"));
						player.sendMessage(ChatColor.GOLD + "You've been automatically placed in the race of " + ChatColor.DARK_RED + "Orcs" + ChatColor.GOLD + ".");
						plugin.board.getTeam("Orcs").addEntry(player.getName());
						break;
					case 5://Wizards
						plugin.board.getTeam("Wizards").addEntry(player.getName());
						break;
				}
				PermissionAttachment attachment = player.addAttachment(plugin);
				perms.put(player.getUniqueId(), attachment);
				PermissionAttachment pperms = perms.get(player.getUniqueId());
				if (race == 2) {
					pperms.setPermission("OpenInv.editinv", true);
					pperms.setPermission("OpenInv.openinv", true);
					pperms.setPermission("OpenInv.editender", true);
					pperms.setPermission("OpenInv.openenderall", true);
				} else {
					pperms.setPermission("OpenInv.exempt", true);
				}
				
				manualRaceAssigningconfig.set(player.getName(), null);
				try {
					manualRaceAssigningconfig.save(manualRaceAssigningfile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			} else if (config.getBoolean("autoBalanceRaces") && stdDev() > 2) {
				int race = 0;
				if (plugin.board.getTeam("Men").getSize() < plugin.board.getTeam("Elves").getSize() && plugin.board.getTeam("Men").getSize() < plugin.board.getTeam("Dwarves").getSize() && plugin.board.getTeam("Men").getSize() < plugin.board.getTeam("Orcs").getSize()) {
					race = 1;
				} else if (plugin.board.getTeam("Elves").getSize() < plugin.board.getTeam("Dwarves").getSize() && plugin.board.getTeam("Elves").getSize() < plugin.board.getTeam("Orcs").getSize()) {
					race = 2;
				} else if (plugin.board.getTeam("Dwarves").getSize() < plugin.board.getTeam("Orcs").getSize()) {
					race = 3;
				} else {
					race = 4;
				}
				data.set(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER, race);//Men: 1, Elves: 2, Dwarves: 3, Orcs: 4
				
				player.closeInventory();
				
				File claimsFile = new File(plugin.getDataFolder() + File.separator + "claims.yml");
				FileConfiguration claimsConfig = YamlConfiguration.loadConfiguration(claimsFile);
				
				switch (race) {
					case 1://Men
						player.teleport(claimsConfig.getLocation("menSpawn"));
						player.sendMessage(ChatColor.GOLD + "You've been automatically placed in the race of " + ChatColor.DARK_AQUA + "Men" + ChatColor.GOLD + ".");
						plugin.board.getTeam("Men").addEntry(player.getName());
						break;
					case 2://Elves
						player.teleport(claimsConfig.getLocation("elfSpawn"));
						player.sendMessage(ChatColor.GOLD + "You've been automatically placed in the race of " + ChatColor.DARK_GREEN + "Elves" + ChatColor.GOLD + ".");
						plugin.board.getTeam("Elves").addEntry(player.getName());
						break;
					case 3://Dwarves
						player.teleport(claimsConfig.getLocation("dwarfSpawn"));
						player.sendMessage(ChatColor.GOLD + "You've been automatically placed in the race of " + ChatColor.DARK_PURPLE + "Dwarves" + ChatColor.GOLD + ".");
						plugin.board.getTeam("Dwarves").addEntry(player.getName());
						break;
					case 4://Orcs
						player.teleport(claimsConfig.getLocation("orcSpawn"));
						player.sendMessage(ChatColor.GOLD + "You've been automatically placed in the race of " + ChatColor.DARK_RED + "Orcs" + ChatColor.GOLD + ".");
						plugin.board.getTeam("Orcs").addEntry(player.getName());
						break;
					case 5://Wizards
						plugin.board.getTeam("Wizards").addEntry(player.getName());
						break;
				}
				PermissionAttachment attachment = player.addAttachment(plugin);
				perms.put(player.getUniqueId(), attachment);
				PermissionAttachment pperms = perms.get(player.getUniqueId());
				if (race == 2) {
					pperms.setPermission("OpenInv.editinv", true);
					pperms.setPermission("OpenInv.openinv", true);
					pperms.setPermission("OpenInv.editender", true);
					pperms.setPermission("OpenInv.openenderall", true);
				} else {
					pperms.setPermission("OpenInv.exempt", true);
				}
				
				manualRaceAssigningconfig.set(player.getName(), null);
				try {
					manualRaceAssigningconfig.save(manualRaceAssigningfile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			} else {
				event.getPlayer().openInventory(plugin.inventoryManager.getSetRaceMenuInventory());
			}
			
		} else {//Player has joined before, update their username to display their race.
			int race = data.get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER);
			switch (race) {
				case 1://Men
					plugin.board.getTeam("Men").addEntry(player.getName());
					break;
				case 2://Elves
					plugin.board.getTeam("Elves").addEntry(player.getName());
					break;
				case 3://Dwarves
					plugin.board.getTeam("Dwarves").addEntry(player.getName());
					break;
				case 4://Orcs
					plugin.board.getTeam("Orcs").addEntry(player.getName());
					break;
				case 5://Wizards
					plugin.board.getTeam("Wizards").addEntry(player.getName());
					break;
			}
			PermissionAttachment attachment = player.addAttachment(plugin);
			perms.put(player.getUniqueId(), attachment);
			PermissionAttachment pperms = perms.get(player.getUniqueId());
			if (race == 2) {
				pperms.setPermission("OpenInv.editinv", true);
				pperms.setPermission("OpenInv.openinv", true);
				pperms.setPermission("OpenInv.editender", true);
				pperms.setPermission("OpenInv.openenderall", true);
			} else {
				pperms.setPermission("OpenInv.exempt", true);
			}
		}
	}
	
	
	private double stdDev() {
		double mean = plugin.board.getTeam("Men").getSize() + plugin.board.getTeam("Elves").getSize() + plugin.board.getTeam("Dwarves").getSize() + plugin.board.getTeam("Orcs").getSize();
		mean = mean / 4.0;
		
		double squares = (plugin.board.getTeam("Men").getSize() - mean) * (plugin.board.getTeam("Men").getSize() - mean) + (plugin.board.getTeam("Elves").getSize() - mean) * (plugin.board.getTeam("Elves").getSize() - mean) + (plugin.board.getTeam("Dwarves").getSize() - mean) * (plugin.board.getTeam("Dwarves").getSize() - mean) + (plugin.board.getTeam("Orcs").getSize() - mean) * (plugin.board.getTeam("Orcs").getSize() - mean);
		double variance = squares / 3;
		return Math.sqrt(variance);
	}
	
}
