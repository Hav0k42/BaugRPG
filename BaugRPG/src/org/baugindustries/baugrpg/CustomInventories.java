package org.baugindustries.baugrpg;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import net.md_5.bungee.api.ChatColor;

public class CustomInventories {
	Main plugin;
	CustomInventories(Main plugin) {
		this.plugin = plugin;
	}
	
	public Inventory getRaceSkillTreeMenuInventory(Player player) {

		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
		String profession = skillsconfig.getString("class");
		int inventorySize = 45;
		String inventoryName = profession + " Skills";
		Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
		ItemStack blankItem = plugin.itemManager.getBlankItem();
		ItemStack lockedItem = plugin.itemManager.getLockedItem();
		ItemStack ownedItem = plugin.itemManager.getUpgradedItem();
		
		for (int i = 0; i < 45; i++) {
			if (i % 9 == 8 || i % 9 == 5 || i % 9 == 1 || i % 9 == 0) {
				inventory.setItem(i, blankItem);
			}
		}
		inventory.setItem(15, blankItem);
		inventory.setItem(16, blankItem);
		inventory.setItem(9, plugin.createItem(
				Material.BOOK,
				1,
				ChatColor.GOLD + "Available Points", 
				Arrays.asList(ChatColor.LIGHT_PURPLE + "" + skillsconfig.get("skillPoints"))));
		
		inventory.setItem(36, plugin.itemManager.getBackItem());
		inventory.setItem(0, plugin.itemManager.getRaceSkillTreeInfoItem());
		
		
		if (profession.equals("Stable Master")) {//Slots to use: 3, 12, 21, 30, 39
			inventory.setItem(3, plugin.itemManager.getStableMasterSkill1Item(player));
			inventory.setItem(21, plugin.itemManager.getStableMasterSkill2Item(player));
			inventory.setItem(39, plugin.itemManager.getStableMasterSkill3Item(player));
		} else if (profession.equals("Steeled Armorer")) {
			inventory.setItem(21, plugin.itemManager.getSteeledArmorerSkill1Item(player));
		} else if (profession.equals("Verdant Shepherd")) {
			inventory.setItem(21, plugin.itemManager.getVerdantShepherdSkill1Item(player));
		} else if (profession.equals("Enchanted Botanist")) {
			inventory.setItem(12, plugin.itemManager.getEnchantedBotanistSkill1Item(player));
			inventory.setItem(30, plugin.itemManager.getEnchantedBotanistSkill2Item(player));
		} else if (profession.equals("Lunar Artificer")) {
			inventory.setItem(3, plugin.itemManager.getLunarArtificerSkill1Item(player));
			inventory.setItem(21, plugin.itemManager.getLunarArtificerSkill2Item(player));
			inventory.setItem(39, plugin.itemManager.getLunarArtificerSkill3Item(player));
		} else if (profession.equals("Woodland Craftsman")) {
			inventory.setItem(12, plugin.itemManager.getWoodlandCraftsmanSkill1Item(player));
			inventory.setItem(30, plugin.itemManager.getWoodlandCraftsmanSkill2Item(player));
		} else if (profession.equals("Radiant Metallurgist")) {
			inventory.setItem(21, plugin.itemManager.getRadiantMetallurgistSkill1Item(player));
		} else if (profession.equals("Arcane Jeweler")) {
			inventory.setItem(21, plugin.itemManager.getArcaneJewelerSkill1Item(player));
		} else if (profession.equals("Gilded Miner")) {
			inventory.setItem(3, plugin.itemManager.getGildedMinerSkill1Item(player));
			inventory.setItem(21, plugin.itemManager.getGildedMinerSkill2Item(player));
			inventory.setItem(39, plugin.itemManager.getGildedMinerSkill3Item(player));
		} else if (profession.equals("Dark Alchemist")) {
			inventory.setItem(3, plugin.itemManager.getDarkAlchemistSkill1Item(player));
			inventory.setItem(21, plugin.itemManager.getDarkAlchemistSkill2Item(player));
			inventory.setItem(39, plugin.itemManager.getDarkAlchemistSkill3Item(player));
		} else if (profession.equals("Enraged Berserker")) {
			inventory.setItem(12, plugin.itemManager.getEnragedBerserkerSkill1Item(player));
			inventory.setItem(30, plugin.itemManager.getEnragedBerserkerSkill2Item(player));
		} else if (profession.equals("Greedy Scrapper")) {
			inventory.setItem(12, plugin.itemManager.getGreedyScrapperSkill1Item(player));
			inventory.setItem(30, plugin.itemManager.getGreedyScrapperSkill2Item(player));
		}
		
		return inventory;
	}
	
	
	
	
	
	
	
	
	
	
	public Inventory getChooseRaceSkillTreeMenuInventory(Player player) {
		PersistentDataContainer data = player.getPersistentDataContainer();
		int race = data.get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER);
		int inventorySize = 27;
		String inventoryName = "Choose your Class";
		Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
		switch (race) {
			case 1:
				inventory.setItem(11, plugin.itemManager.getStableMasterItem());
				
				inventory.setItem(13, plugin.itemManager.getSteeledArmorerItem());
				
				inventory.setItem(15, plugin.itemManager.getVerdantShepherdItem());
				break;
				
			case 2:
				inventory.setItem(11, plugin.itemManager.getEnchantedBotanistItem());
				
				inventory.setItem(13, plugin.itemManager.getLunarArtificerItem());
				
				inventory.setItem(15, plugin.itemManager.getWoodlandCraftsmanItem());
				break;
				
			case 3:
				inventory.setItem(11, plugin.itemManager.getRadiantMetallurgistItem());
				
				inventory.setItem(13, plugin.itemManager.getArcaneJewelerItem());
				
				inventory.setItem(15, plugin.itemManager.getGildedMinerItem());
				break;
				
			case 4:
				inventory.setItem(11, plugin.itemManager.getDarkAlchemistItem());
				
				inventory.setItem(13, plugin.itemManager.getEnragedBerserkerItem());
				
				inventory.setItem(15, plugin.itemManager.getGreedyScrapperItem());
				break;
		}
		
		return inventory;
	}
	
	
	
	
	
	
	
	
	
	
	public Inventory getGeneralSkillTreeMenuInventory(Player player) {
		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
		int inventorySize = 54;
		String inventoryName = "General Skills";
		Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
		
		
		inventory.setItem(0, plugin.itemManager.getGeneralSkillTreeInfoItem());
		
		String enabledString = "";
		
		String[] skillNames = {"speed", "jump", "damage", "resistance", "mining", "regen", "swim"};
		String[] skillDisplayNames = {"Speed", "Jump Height", "Attack Damage", "Resistance", "Mining Speed", "Regeneration", "Swim Speed"};
		String[] upgradeText = {"movement speed", "jump height", "attack damage", "damage resistance", "mining speed", "regeneration speed", "swim speed"};
		Material[] skillMats = {Material.FEATHER, Material.RABBIT_FOOT, Material.IRON_AXE, Material.SHIELD, Material.IRON_PICKAXE, Material.APPLE, Material.HEART_OF_THE_SEA};
		
		for (int i = 0; i < skillNames.length; i++) {
			if (skillsconfig.getBoolean(skillNames[i] + "On")) {
				enabledString = ChatColor.GREEN + "ENABLED";
			} else {
				enabledString = ChatColor.RED + "DISABLED";
			}
			
			
			inventory.setItem(i + 1, plugin.createItem(
					skillMats[i],
					1,
					ChatColor.GOLD + skillDisplayNames[i], 
					Arrays.asList(ChatColor.LIGHT_PURPLE + "Upgrade " + upgradeText[i] + ".", "Click to toggle: " + enabledString)));
		}
		
		inventory.setItem(9, plugin.createItem(
				Material.BOOK,
				1,
				ChatColor.GOLD + "Available Points", 
				Arrays.asList(ChatColor.LIGHT_PURPLE + "" + skillsconfig.get("skillPoints"))));
		
		inventory.setItem(45, plugin.itemManager.getBackItem());
		
		ItemStack blankItem = plugin.itemManager.getBlankItem();
		ItemStack lockedItem = plugin.itemManager.getLockedItem();
		ItemStack ownedItem = plugin.itemManager.getUpgradedItem();
		
		for (int i = 0; i < 10; i++) {
			if (i % 9 == 8) {
				inventory.setItem(i, blankItem);
			}
		}
		
		for (int i = 10; i < 18; i++) {
			inventory.setItem(i, blankItem);
		}
		
		for (int i = 18; i < 45; i++) {
			if (i % 9 == 8 || i % 9 == 0) {
				inventory.setItem(i, blankItem);
			} else {
				inventory.setItem(i, lockedItem);
			}
		}
		
		for (int i = 45; i < 54; i++) {
			if (i % 9 == 8) {
				inventory.setItem(i, blankItem);
			} else if (i % 9 != 0) {
				inventory.setItem(i, lockedItem);
			}
		}
		
		ItemStack[] currentItem = new ItemStack[7];
		currentItem[0] = plugin.createItem(Material.BLUE_STAINED_GLASS_PANE, 1, ChatColor.WHITE + "Upgrade speed: Lvl " + (skillsconfig.getInt("speed") + 1));
		currentItem[1] = plugin.createItem(Material.BLUE_STAINED_GLASS_PANE, 1, ChatColor.GREEN + "Upgrade jump: Lvl " + (skillsconfig.getInt("jump") + 1));
		currentItem[2] = plugin.createItem(Material.BLUE_STAINED_GLASS_PANE, 1, ChatColor.DARK_RED + "Upgrade attack damage: Lvl " + (skillsconfig.getInt("damage") + 1));
		currentItem[3] = plugin.createItem(Material.BLUE_STAINED_GLASS_PANE, 1, ChatColor.DARK_AQUA + "Upgrade resistance: Lvl " + (skillsconfig.getInt("resistance") + 1));
		currentItem[4] = plugin.createItem(Material.BLUE_STAINED_GLASS_PANE, 1, ChatColor.YELLOW + "Upgrade mining speed: Lvl " + (skillsconfig.getInt("mining") + 1));
		currentItem[5] = plugin.createItem(Material.BLUE_STAINED_GLASS_PANE, 1, ChatColor.RED + "Upgrade regeneration: Lvl " + (skillsconfig.getInt("regen") + 1));
		currentItem[6] = plugin.createItem(Material.BLUE_STAINED_GLASS_PANE, 1, ChatColor.AQUA + "Upgrade swim speed: Lvl " + (skillsconfig.getInt("swim") + 1));
		
		
		for (int i = 0; i < currentItem.length; i++) {
			int levelInt = skillsconfig.getInt(skillNames[i]);
			int rowShifter = i;
			if (levelInt == 0) {
				inventory.setItem(46 + rowShifter, currentItem[i]);
			} else if (levelInt > 0 && levelInt < 5) {
				inventory.setItem(46 + rowShifter, ownedItem);
				inventory.setItem(37 + rowShifter, currentItem[i]);
			} else if (levelInt > 4 && levelInt < 9) {
				inventory.setItem(46 + rowShifter, ownedItem);
				inventory.setItem(37 + rowShifter, ownedItem);
				inventory.setItem(28 + rowShifter, currentItem[i]);
			} else if (levelInt == 9) {
				inventory.setItem(46 + rowShifter, ownedItem);
				inventory.setItem(37 + rowShifter, ownedItem);
				inventory.setItem(28 + rowShifter, ownedItem);
				inventory.setItem(19 + rowShifter, currentItem[i]);
			} else {
				inventory.setItem(46 + rowShifter, ownedItem);
				inventory.setItem(37 + rowShifter, ownedItem);
				inventory.setItem(28 + rowShifter, ownedItem);
				inventory.setItem(19 + rowShifter, ownedItem);
			}
		}
		
		
		
		return inventory;
	}
	
	
	
	
	
	
	
	
	
	
	public Inventory getSkillTreeMenuInventory(Player player) {
		int inventorySize = 9;
		String inventoryName = "Skill Trees";
		Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
		
		inventory.setItem(0, plugin.itemManager.getBackItem());
		
		inventory.setItem(3, plugin.itemManager.getGeneralSkillTreeMenuItem());
		
		inventory.setItem(5, plugin.itemManager.getRaceSkillTreeMenuItem(player));
		
		return inventory;
	}
	
	
	
	
	
	
	
	
	
	
	public Inventory getBaugScrollMenuInventory(Player player) {

		int inventorySize = 54;
		String inventoryName = "Scrolls of Baug";
		Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
		
		PersistentDataContainer data = player.getPersistentDataContainer();
		int race = data.get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER);
		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
		int level = skillsconfig.getInt("totalSkillPoints");
		
		if (race == 1) {//Men
			inventory.setItem(0, plugin.itemManager.getScrollsOfBaugInfoItem());
			
			if (level > 5) {
				inventory.setItem(11, plugin.itemManager.getSkillTreeMenuItem());
			}
			
			
			
		} else if (race == 2) {//Elves
			
			inventory.setItem(0, plugin.itemManager.getScrollsOfBaugInfoItem());
			
			if (level > 5) {
				inventory.setItem(11, plugin.itemManager.getSkillTreeMenuItem());
			}
			
			inventory.setItem(12, plugin.itemManager.getCommunistHubItem());
			
			
			
			
		} else if (race == 3) {//Dwarves
			
			inventory.setItem(0, plugin.itemManager.getScrollsOfBaugInfoItem());
			
			if (level > 5) {
				inventory.setItem(11, plugin.itemManager.getSkillTreeMenuItem());
			}
			
			inventory.setItem(12, plugin.itemManager.getDwarvenBankConversionItem());
			
			
			
		} else if (race == 4) {//Orcs
			
			inventory.setItem(0, plugin.itemManager.getScrollsOfBaugInfoItem());
			
			if (level > 5) {
				inventory.setItem(11, plugin.itemManager.getSkillTreeMenuItem());
			}

			
			
		} else if (race == 5) {//Wizards
			
			
			inventory.setItem(0, plugin.itemManager.getWizardScrollsOfBaugInfoItem());
			
			
			inventory.setItem(11, plugin.itemManager.getFeatureManagementItem());
			
			
			inventory.setItem(12, plugin.itemManager.getInventorySnoopingItem());
			
			
			
		}
		
		return inventory;
	}
	
	
	
	
	
	
	
	
	
	
	public Inventory getSetRaceMenuInventory() {
		int inventorySize = 54;
		String inventoryName = "Choose Your Race";
		Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
		
		for (int i = 0; i < inventorySize; i++) {
			inventory.setItem(i, plugin.itemManager.getBlankItem());
		}
		
		
		
		inventory.setItem(13, plugin.itemManager.getRaceSelectionItem());
		
		inventory.setItem(28, plugin.itemManager.getSelectManItem());
			
		inventory.setItem(30, plugin.itemManager.getSelectElfItem());
		
		inventory.setItem(32, plugin.itemManager.getSelectDwarfItem());
		
		inventory.setItem(34, plugin.itemManager.getSelectOrcItem());
		
		
		
		return inventory;
	}
	
	
	
	
	
	
	
	
	
	
	public Inventory getConfirmClassMenuInventory(String pickedClass) {

		int inventorySize = 27;
		String inventoryName = "Confirm Class Selection";
		Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
		for (int i = 0; i < inventorySize; i++) {
			inventory.setItem(i, plugin.itemManager.getBlankItem());
		}
		
		
		inventory.setItem(4, plugin.createItem(
				Material.NETHER_STAR, 
				1, 
				ChatColor.YELLOW + "Confirm Selection", 
				Arrays.asList(ChatColor.LIGHT_PURPLE + "Are you sure you want to choose " + pickedClass + "?")));
		
		inventory.setItem(11, plugin.itemManager.getYesItem());
		
		inventory.setItem(15, plugin.itemManager.getNoItem());
		
		
		return inventory;
	}
	
	
	
	
	
	
	
	
	
	
	public Inventory getConfirmRaceMenuInventory(String chosenRace) {
		int inventorySize = 27;
		String inventoryName = "Confirm Selection";
		Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
		for (int i = 0; i < inventorySize; i++) {
			inventory.setItem(i, plugin.itemManager.getBlankItem());
		}
		
		
		inventory.setItem(4, plugin.createItem(
				Material.NETHER_STAR, 
				1, 
				ChatColor.YELLOW + "Confirm Selection", 
				Arrays.asList(ChatColor.LIGHT_PURPLE + "Are you sure you want to choose " + chosenRace + "?")));
		
		inventory.setItem(11, plugin.itemManager.getYesItem());
		
		inventory.setItem(15, plugin.itemManager.getNoItem());
		
		
		return inventory;
	}
	
	
	
	
	
	
	
	
	
	
	public Inventory getGoldConversionMenuInventory() {
		int inventorySize = 9;
		String inventoryName = "Dwarven Gold Deposit";
		Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
		
		for (int i = 0; i < inventorySize; i++) {
			inventory.setItem(i, plugin.itemManager.getBlankItem());
		}
		
		inventory.setItem(3, new ItemStack(Material.AIR));
		
		inventory.setItem(5, plugin.itemManager.getConfirmBankTransferItem());
	
		return inventory;
	}
	
	
	
	
	
	
	
	
	public Inventory getElvesCommunistHubMenuInventory() {
		int inventorySize = 9;
		String inventoryName = "Elves Communism Hub";
		Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
		
		
		for (int i = 0; i < inventorySize; i++) {
			inventory.setItem(i, new ItemStack(Material.AIR));
		}

		
		inventory.setItem(0, plugin.itemManager.getBackItem());
		
		inventory.setItem(3, plugin.itemManager.getCommunistInventoryItem());
		
		inventory.setItem(5, plugin.itemManager.getCommunistEnderChestItem());
		
		return inventory;
	}
	
	
	
	
	
	
	
	
	
	
	public Inventory getElvesCommunistInventoryMenuInventory(Player player) {
		List<OfflinePlayer> allOfflineElves = plugin.getAllOfflineElves();
		ItemStack backItem = plugin.itemManager.getBackItem();
		List<ItemStack> elfHeads = new ArrayList<ItemStack>();
		int inventorySize = 18;
		if (elfHeads.size() > 9) {
			inventorySize = 27;
		}
		if (elfHeads.size() > 18) {
			inventorySize = 36;
		}
		if (elfHeads.size() > 27) {
			inventorySize = 45;
		}
		if (elfHeads.size() > 36) {
			inventorySize = 54;
		}
		for (int i = 0; i < allOfflineElves.size(); i++) {
			ItemStack tempPlayerHead = new ItemStack(Material.PLAYER_HEAD);
			SkullMeta tempPlayerHeadMeta = (SkullMeta)tempPlayerHead.getItemMeta();
			List<String> tempPlayerHeadLore = Arrays.asList(ChatColor.YELLOW + "View " + allOfflineElves.get(i).getName() + "'s inventory");
			tempPlayerHeadMeta.setLore(tempPlayerHeadLore);
			tempPlayerHeadMeta.setDisplayName(allOfflineElves.get(i).getName());
			tempPlayerHeadMeta.setOwningPlayer(allOfflineElves.get(i));
			tempPlayerHead.setItemMeta(tempPlayerHeadMeta);
			if (!player.getUniqueId().equals(allOfflineElves.get(i).getUniqueId())) {
				elfHeads.add(tempPlayerHead);
			}
		}
		
		String inventoryName = "Elves Inventories";
		Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
		
		
		for (int i = 0; i < inventorySize; i++) {
			inventory.setItem(i, new ItemStack(Material.AIR));
		}
		
		
		for (int i = 0; i < elfHeads.size(); i++) {
			inventory.setItem(i, elfHeads.get(i));
			if (i > 45) {
				break;
			}
		}
		
		
		
		
		if (elfHeads.size() > 45) {
			inventory.setItem(46, backItem);
			//add menu to go to the next page of elves, also, figure out how to get multiple pages of elves if for some reason theres a server with more than 45 fucking elves.
		} else if (elfHeads.size() > 36) {
			inventory.setItem(46, backItem);
		} else if (elfHeads.size() > 27) {
			inventory.setItem(37, backItem);
		} else if (elfHeads.size() > 18) {
			inventory.setItem(28, backItem);
		} else if (elfHeads.size() > 9) {
			inventory.setItem(19, backItem);
		} else {
			inventory.setItem(10, backItem);
		}
		
		return inventory;
	}
	
	
	
	
	
	
	
	
	public Inventory getElvesCommunistEnderChestMenuInventory(Player player) {
		List<OfflinePlayer> allOfflineElves = plugin.getAllOfflineElves();
		ItemStack backItem = plugin.itemManager.getBackItem();
		List<ItemStack> elfHeads = new ArrayList<ItemStack>();
		int inventorySize = 18;
		if (elfHeads.size() > 9) {
			inventorySize = 27;
		}
		if (elfHeads.size() > 18) {
			inventorySize = 36;
		}
		if (elfHeads.size() > 27) {
			inventorySize = 45;
		}
		if (elfHeads.size() > 36) {
			inventorySize = 54;
		}
		for (int i = 0; i < allOfflineElves.size(); i++) {
			ItemStack tempPlayerHead = new ItemStack(Material.PLAYER_HEAD);
			SkullMeta tempPlayerHeadMeta = (SkullMeta)tempPlayerHead.getItemMeta();
			List<String> tempPlayerHeadLore = Arrays.asList(ChatColor.YELLOW + "View " + allOfflineElves.get(i).getName() + "'s inventory");
			tempPlayerHeadMeta.setLore(tempPlayerHeadLore);
			tempPlayerHeadMeta.setDisplayName(allOfflineElves.get(i).getName());
			tempPlayerHeadMeta.setOwningPlayer(allOfflineElves.get(i));
			tempPlayerHead.setItemMeta(tempPlayerHeadMeta);
			if (!player.getUniqueId().equals(allOfflineElves.get(i).getUniqueId())) {
				elfHeads.add(tempPlayerHead);
			}
		}
		
		String inventoryName = "Elves Ender Chests";
		Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
		
		
		for (int i = 0; i < inventorySize; i++) {
			inventory.setItem(i, new ItemStack(Material.AIR));
		}
		
		
		for (int i = 0; i < elfHeads.size(); i++) {
			inventory.setItem(i, elfHeads.get(i));
			if (i > 45) {
				break;
			}
		}
		
		
		
		
		if (elfHeads.size() > 45) {
			inventory.setItem(46, backItem);
			//add menu to go to the next page of elves, also, figure out how to get multiple pages of elves if for some reason theres a server with more than 45 fucking elves.
		} else if (elfHeads.size() > 36) {
			inventory.setItem(46, backItem);
		} else if (elfHeads.size() > 27) {
			inventory.setItem(37, backItem);
		} else if (elfHeads.size() > 18) {
			inventory.setItem(28, backItem);
		} else if (elfHeads.size() > 9) {
			inventory.setItem(19, backItem);
		} else {
			inventory.setItem(10, backItem);
		}
		return inventory;
	}
	
	
	
	
	
	
	
	
	
	public Inventory getInventorySnoopingHubMenuInventory() {
		int inventorySize = 9;
		String inventoryName = "Inventory Snooping Hub";
		Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
		
		
		for (int i = 0; i < inventorySize; i++) {
			inventory.setItem(i, new ItemStack(Material.AIR));
		}

		
		inventory.setItem(0, plugin.itemManager.getBackItem());
		
		inventory.setItem(3, plugin.itemManager.getInventorySnoopingInventoryItem());
		
		inventory.setItem(5, plugin.itemManager.getInventorySnoopingEnderChestItem());
		
		return inventory;
	}
	
	
	
	
	
	
	
	
	
	
	public Inventory getInventorySnoopingInventoryMenuInventory(Player player) {
		OfflinePlayer[] allOfflinePlayers = plugin.getServer().getOfflinePlayers();
		
		
		
		
		List<ItemStack> playerHeads = new ArrayList<ItemStack>();
		for (int i = 0; i < allOfflinePlayers.length; i++) {
			ItemStack tempPlayerHead = new ItemStack(Material.PLAYER_HEAD);
			SkullMeta tempPlayerHeadMeta = (SkullMeta)tempPlayerHead.getItemMeta();
			List<String> tempPlayerHeadLore = Arrays.asList(ChatColor.YELLOW + "View " + allOfflinePlayers[i].getName() + "'s inventory");
			tempPlayerHeadMeta.setLore(tempPlayerHeadLore);
			tempPlayerHeadMeta.setDisplayName(allOfflinePlayers[i].getName());
			tempPlayerHeadMeta.setOwningPlayer(allOfflinePlayers[i]);
			tempPlayerHead.setItemMeta(tempPlayerHeadMeta);
			if (!player.getUniqueId().equals(allOfflinePlayers[i].getUniqueId())) {
				playerHeads.add(tempPlayerHead);
			}
		}
		
		
		ItemStack backItem = plugin.itemManager.getBackItem();
		
		
		int inventorySize = 18;
		if (playerHeads.size() > 9) {
			inventorySize = 27;
		}
		if (playerHeads.size() > 18) {
			inventorySize = 36;
		}
		if (playerHeads.size() > 27) {
			inventorySize = 45;
		}
		if (playerHeads.size() > 36) {
			inventorySize = 54;
		}
		
		String inventoryName = "Players Inventories";
		Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
		
		
		for (int i = 0; i < inventorySize; i++) {
			inventory.setItem(i, new ItemStack(Material.AIR));
		}
		
		
		for (int i = 0; i < playerHeads.size(); i++) {
			inventory.setItem(i, playerHeads.get(i));
			if (i > 45) {
				break;
			}
		}
		
		
		
		
		if (playerHeads.size() > 45) {
			inventory.setItem(46, backItem);
			//add menu to go to the next page of players, also, figure out how to get multiple pages of elves if for some reason theres a server with more than 45 players.
		} else if (playerHeads.size() > 36) {
			inventory.setItem(46, backItem);
		} else if (playerHeads.size() > 27) {
			inventory.setItem(37, backItem);
		} else if (playerHeads.size() > 18) {
			inventory.setItem(28, backItem);
		} else if (playerHeads.size() > 9) {
			inventory.setItem(19, backItem);
		} else {
			inventory.setItem(10, backItem);
		}
		
		return inventory;
	}
	
	
	
	
	
	
	
	
	
	public Inventory getInventorySnoopingEnderChestMenuInventory(Player player) {
		OfflinePlayer[] allOfflinePlayers = plugin.getServer().getOfflinePlayers();
		
		
		
		
		List<ItemStack> playerHeads = new ArrayList<ItemStack>();
		for (int i = 0; i < allOfflinePlayers.length; i++) {
			ItemStack tempPlayerHead = new ItemStack(Material.PLAYER_HEAD);
			SkullMeta tempPlayerHeadMeta = (SkullMeta)tempPlayerHead.getItemMeta();
			List<String> tempPlayerHeadLore = Arrays.asList(ChatColor.YELLOW + "View " + allOfflinePlayers[i].getName() + "'s inventory");
			tempPlayerHeadMeta.setLore(tempPlayerHeadLore);
			tempPlayerHeadMeta.setDisplayName(allOfflinePlayers[i].getName());
			tempPlayerHeadMeta.setOwningPlayer(allOfflinePlayers[i]);
			tempPlayerHead.setItemMeta(tempPlayerHeadMeta);
			if (!player.getUniqueId().equals(allOfflinePlayers[i].getUniqueId())) {
				playerHeads.add(tempPlayerHead);
			}
		}
		
		
		ItemStack backItem = plugin.itemManager.getBackItem();
		
		
		int inventorySize = 18;
		if (playerHeads.size() > 9) {
			inventorySize = 27;
		}
		if (playerHeads.size() > 18) {
			inventorySize = 36;
		}
		if (playerHeads.size() > 27) {
			inventorySize = 45;
		}
		if (playerHeads.size() > 36) {
			inventorySize = 54;
		}
		

		String inventoryName = "Players Ender Chests";
		Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
		
		
		for (int i = 0; i < inventorySize; i++) {
			inventory.setItem(i, new ItemStack(Material.AIR));
		}
		
		
		for (int i = 0; i < playerHeads.size(); i++) {
			inventory.setItem(i, playerHeads.get(i));
			if (i > 45) {
				break;
			}
		}
		
		
		
		
		if (playerHeads.size() > 45) {
			inventory.setItem(46, backItem);
			//add menu to go to the next page of players, also, figure out how to get multiple pages of elves if for some reason theres a server with more than 45 players.
		} else if (playerHeads.size() > 36) {
			inventory.setItem(46, backItem);
		} else if (playerHeads.size() > 27) {
			inventory.setItem(37, backItem);
		} else if (playerHeads.size() > 18) {
			inventory.setItem(28, backItem);
		} else if (playerHeads.size() > 9) {
			inventory.setItem(19, backItem);
		} else {
			inventory.setItem(10, backItem);
		}
		return inventory;
	}
}
