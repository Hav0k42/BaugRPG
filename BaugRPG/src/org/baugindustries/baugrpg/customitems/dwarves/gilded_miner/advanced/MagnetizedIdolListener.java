package org.baugindustries.baugrpg.customitems.dwarves.gilded_miner.advanced;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import org.bukkit.ChatColor;

public class MagnetizedIdolListener implements Listener {
	private Main plugin;
	
	private HashMap<UUID, String> bestowedAbility = new HashMap<UUID, String>();
	
	int secondsLasted = 300; //five minutes
	
	HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();
	int cooldownTime = 1800000;//30 minutes
	
	public MagnetizedIdolListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClick(PlayerMoveEvent event) {
		if (event.getTo().getBlock().equals(event.getFrom().getBlock())) return;

		Player player = event.getPlayer();
		if (cooldown.containsKey(player.getUniqueId())) {
			if (cooldown.get(player.getUniqueId()) > System.currentTimeMillis()) {
				return;
			}
		}
		
		if (bestowedAbility.containsKey(player.getUniqueId())) return;
		
		
		for (Entity entity : player.getNearbyEntities(25, 25, 25)) {
			if (entity instanceof Player) {
				Player otherPlayer = (Player) entity;

				if (plugin.getRace(player) == plugin.getRace(otherPlayer)) {
				
					if (Recipes.MAGNETIZED_IDOL.playerIsCarrying(otherPlayer, plugin)) {
						
	
						File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
					 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
						
						File otherskillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + otherPlayer.getUniqueId() + ".yml");
					 	FileConfiguration otherskillsconfig = YamlConfiguration.loadConfiguration(otherskillsfile);
					 	
					 	
					 	
					 	if (otherskillsconfig.contains("class")) {
					 		
					 		String className = otherskillsconfig.getString("class").replace(" ", "");
					 		List<Integer> nums = new ArrayList<Integer>();
					 		if (otherskillsconfig.contains(className + "1")) {
						 		nums.add(Integer.valueOf(1));
					 		}
					 		if (otherskillsconfig.contains(className + "2")) {
						 		nums.add(Integer.valueOf(2));
					 		}
					 		if (otherskillsconfig.contains(className + "3")) {
						 		nums.add(Integer.valueOf(3));
					 		}
							
					 		if (nums.size() == 0) return;
					 		
					 		String ability = className + nums.get((int) (Math.random() * nums.size()));
					 		if (!skillsconfig.contains(ability)) {
					 			cooldown.put(player.getUniqueId(), System.currentTimeMillis() + cooldownTime);
								bestowedAbility.put(player.getUniqueId(), ability);
								plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
									public void run() {
										bestowedAbility.remove(player.getUniqueId());
										
										if (ability.equals("StableMaster1") && plugin.mountedPlayers.contains(player)) {
								 			AbstractHorse horse = (AbstractHorse) player.getVehicle();
								 			
							 				horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() / plugin.horseListener.getBuffedHorseSpeed());
								 		}
										
										if (ability.equals("StableMaster3") && plugin.mountedPlayers.contains(player)) {
								 			AbstractHorse horse = (AbstractHorse) player.getVehicle();
								 			
							 				double healthPercentage = horse.getHealth() / horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
							 				horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() / plugin.horseListener.getBuffedHorseHealth());
							 				horse.setHealth(horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() * healthPercentage);
								 		}
										
										player.sendMessage(ChatColor.GOLD + "You no longer have the ability to use " + getAbilityName(ability) + ".");
										
									}
								}, secondsLasted * 20);
								
								player.sendMessage(ChatColor.GOLD + otherPlayer.getName() + " has bestowed you with " + getAbilityName(ability) + " for 5 minutes");
								
								if (ability.equals("StableMaster1") && plugin.mountedPlayers.contains(player)) {
						 			AbstractHorse horse = (AbstractHorse) player.getVehicle();
						 			
					 				horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() * plugin.horseListener.getBuffedHorseSpeed());
						 			
						 		}
								
								if (ability.equals("StableMaster3") && plugin.mountedPlayers.contains(player)) {
						 			AbstractHorse horse = (AbstractHorse) player.getVehicle();
						 			
					 				double healthPercentage = horse.getHealth() / horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
					 				horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() * plugin.horseListener.getBuffedHorseHealth());
					 				horse.setHealth(horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() * healthPercentage);
						 			
						 		}
								
								
								
								break;
					 		}
					 	}
					}
				}
			}
		}
		
	}
	
	private String getAbilityName(String ability) {
		switch (ability) {
			case "StableMaster1":
				return "Hermes Hooves";
			case "StableMaster2":
				return "Mounted Mania";
			case "StableMaster3":
				return "Healthy Horses";
			case "SteeledArmorer1":
				return "Steeled Resolve";
			case "VerdantShepherd1":
				return "Shepherd's Grace";
			case "EnchantedBotanist1":
				return "Efficient Botany";
			case "EnchantedBotanist2":
				return "Enchanted Petals";
			case "WoodlandCraftsman1":
				return "Woodland Absorption";
			case "WoodlandCraftsman2":
				return "Arborated Strike";
			case "LunarArtificer1":
				return "Full Moon Lunar Transfusion";
			case "LunarArtificer2":
				return "Starlight Healing";
			case "LunarArtificer3":
				return "New Moon Lunar Transfusion";
			case "RadiantMetallurgist1":
				return "Radiant Anvils";
			case "ArcaneJeweler1":
				return "Arcane Jewels";
			case "GildedMiner1":
				return "Gilded Fortune";
			case "GildedMiner2":
				return "Powered Minecarts";
			case "GildedMiner3":
				return "Gilded Haste";
			case "DarkAlchemist1":
				return "Powerful Alchemy";
			case "DarkAlchemist2":
				return "Magma Transmutation";
			case "DarkAlchemist3":
				return "Lasting Alchemy";
			case "EnragedBerserker1":
				return "Rage";
			case "EnragedBerserker2":
				return "Withered Beheading";
			case "GreedyScrapper1":
				return "Molten Fishing";
			case "GreedyScrapper2":
				return "Greedy Reinforcements";
		}
		return null;
	}
	
	public String getActiveBestowedAbility(UUID uuid) {
		if (bestowedAbility.containsKey(uuid)) {
			return bestowedAbility.get(uuid);
		} else {
			return "";
		}
	}
	
	@EventHandler
	public void onInventoryClick(BlockPlaceEvent event) {
		if (!Recipes.MAGNETIZED_IDOL.matches(plugin, event.getItemInHand())) return;
		event.setCancelled(true);
	}

}
