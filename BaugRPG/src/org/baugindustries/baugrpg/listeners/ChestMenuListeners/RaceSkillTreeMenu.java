package org.baugindustries.baugrpg.listeners.ChestMenuListeners;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class RaceSkillTreeMenu implements Listener{
	private Main plugin;
	public RaceSkillTreeMenu(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player)) return;
		if (event.getClickedInventory() == null) return;
		String[] classNames = {"Stable Master", "Steeled Armorer", "Verdant Shepherd", "Enchanted Botanist", "Lunar Artificer", "Woodland Craftsman", "Radiant Metallurgist", "Arcane Jeweler", "Gilded Miner", "Dark Alchemist", "Enraged Berserker", "Greedy Scrapper"};
		Boolean nameCheck = false;
		String profession = "";
		for (String name: classNames) {
			if (event.getView().getTitle().equals(name + " Skills")) {
				nameCheck = true;
				profession = name;
				continue;
			}
		}
		if (!nameCheck) return;
		if (!event.getClickedInventory().getItem(0).equals(plugin.itemManager.getRaceSkillTreeInfoItem())) return;
		if (!(event.getView().getTopInventory().equals(event.getClickedInventory())) && event.getCursor() == null) return;
		event.setCancelled(true);
		if (event.getCurrentItem() == null) return;
		
		Player player = (Player)event.getWhoClicked();
		
		if (event.getCurrentItem().equals(plugin.itemManager.getBackItem())) {
			player.openInventory(plugin.inventoryManager.getSkillTreeMenuInventory(player));
		}
		
		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
		
	 	Boolean reloadBool = false;
	 	
	 	int slot = event.getSlot();

 		PersistentDataContainer data = player.getPersistentDataContainer();
 		String raceString = "men";
		int race = data.get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER);
 		switch (race) {
	 		case 2:
	 			raceString = "elf";
	 			break;
	 		case 3:
	 			raceString = "dwarf";
	 			break;
	 		case 4:
	 			raceString = "orc";
	 			break;
 		}
	 	if (slot == 6 || slot == 7) {
	 		String skillString = raceString;
	 		if (slot == 6) {
	 			skillString = skillString + "BuffBiomeOn";
	 		} else {
	 			skillString = skillString + "DangerOn";
	 		}
	 		
	 		if (skillsconfig.contains(skillString)) {
	 			skillsconfig.set(skillString, !skillsconfig.getBoolean(skillString));
	 		} else {
	 			skillsconfig.set(skillString, true);
	 		}
	 		
	 		try {
				skillsconfig.save(skillsfile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 		
	 		reloadBool = true;
	 	}
	 	
	 	if (event.getCurrentItem().getType().equals(Material.BLUE_STAINED_GLASS_PANE)) {
	 		if (skillsconfig.getInt("skillPoints") < 1) return;
	 		
	 		
	 		String skillString = raceString;
	 		if (slot % 9 == 6) {
	 			skillString = skillString + "BuffBiome";
	 		} else {
	 			skillString = skillString + "Danger";
	 		}
	 		
	 		skillsconfig.set(skillString, skillsconfig.getInt(skillString) + 1);
 			skillsconfig.set("skillPoints", skillsconfig.getInt("skillPoints") - 1);
 			
 			try {
				skillsconfig.save(skillsfile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 		
	 		reloadBool = true;
	 	}
	 	
	 	if (slot == 3 || slot == 12 || slot == 21 || slot == 30 || slot == 39) {
	 		String skill = "";
	 		int skillNum = 0;
			
			if (slot == 3 || slot == 12) {
				skillNum = 1;
			}
			
			if (slot == 21 || slot == 30) {
				skillNum = 2;
			}
			
			if (slot == 39) {
				skillNum = 3;
			}
			
			
			
			if (profession.equals("Steeled Armorer") || profession.equals("Verdant Shepherd") || profession.equals("Radiant Metallurgist") || profession.equals("Arcane Jeweler")) {
				if (slot == 21) {
					skillNum = 1;
				}
			}
			
			skill = profession.replaceAll("\\s", "") + skillNum;
			
			if (skillsconfig.contains(skill)) {
				skillsconfig.set(skill, !skillsconfig.getBoolean(skill));
				
				if (skill.equals("StableMaster1") && skillsconfig.contains("StableMaster1") && plugin.mountedPlayers.contains(player)) {
		 			AbstractHorse horse = (AbstractHorse) player.getVehicle();
		 			
		 			if (skillsconfig.getBoolean("StableMaster1")) {
		 				horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() * plugin.horseListener.getBuffedHorseSpeed());
		 			} else {
		 				horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() / plugin.horseListener.getBuffedHorseSpeed());
		 			}
		 		}
				
				if (skill.equals("StableMaster3") && skillsconfig.contains("StableMaster3") && plugin.mountedPlayers.contains(player)) {
		 			AbstractHorse horse = (AbstractHorse) player.getVehicle();
		 			
		 			if (skillsconfig.getBoolean("StableMaster3")) {
		 				double healthPercentage = horse.getHealth() / horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
		 				horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() * plugin.horseListener.getBuffedHorseHealth());
		 				horse.setHealth(horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() * healthPercentage);
		 			} else {
		 				double healthPercentage = horse.getHealth() / horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
		 				horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() / plugin.horseListener.getBuffedHorseHealth());
		 				horse.setHealth(horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() * healthPercentage);
		 			}
		 		}
				
	 			reloadBool = true;
		 	} else {
		 		List<String> lore = event.getCurrentItem().getItemMeta().getLore();
		 		String skillPriceStr = lore.get(lore.size() - 1).substring(13);
		 		skillPriceStr = skillPriceStr.substring(0, skillPriceStr.length() - 7);
		 		
		 		int skillPrice = 0;
		 		if (plugin.isInteger(skillPriceStr)) {
		 			skillPrice = Integer.parseInt(skillPriceStr);
			 		if (skillsconfig.getInt("skillPoints") >= skillPrice) {
			 			skillsconfig.set(skill, true);
			 			if (skill.equals("StableMaster1") && skillsconfig.contains("StableMaster1") && plugin.mountedPlayers.contains(player)) {
				 			AbstractHorse horse = (AbstractHorse) player.getVehicle();
				 			
				 			if (skillsconfig.getBoolean("StableMaster1")) {
				 				horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() * plugin.horseListener.getBuffedHorseSpeed());
				 			}
				 		}
						
						if (skill.equals("StableMaster3") && skillsconfig.contains("StableMaster3") && plugin.mountedPlayers.contains(player)) {
				 			AbstractHorse horse = (AbstractHorse) player.getVehicle();
				 			
				 			if (skillsconfig.getBoolean("StableMaster3")) {
				 				double healthPercentage = horse.getHealth() / horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
				 				horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() * plugin.horseListener.getBuffedHorseHealth());
				 				horse.setHealth(horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() * healthPercentage);
				 			} 
				 		}
			 			skillsconfig.set("skillPoints", skillsconfig.getInt("skillPoints") - skillPrice);
			 			reloadBool = true;
			 		}
		 		}
		 	}
			
			try {
				skillsconfig.save(skillsfile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 	}
	 	
	 	
	 	
	 	if (reloadBool) {
	 		player.openInventory(plugin.inventoryManager.getRaceSkillTreeMenuInventory(player));
	 		
	 	}
		
		
		
		
	}
}
