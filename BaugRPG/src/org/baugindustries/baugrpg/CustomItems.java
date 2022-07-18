package org.baugindustries.baugrpg;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import org.bukkit.ChatColor;


//Pretty much just the ItemStack version of a lang file. Trying to make the menus consistent so there arent two different types of back buttons or confirm buttons.
public class CustomItems {
	Main plugin;
	CustomItems(Main plugin) {
		this.plugin = plugin;
	}
	
	public ItemStack getBackItem() {
		return plugin.createItem(Material.RED_STAINED_GLASS_PANE, 1, ChatColor.RED + "Back", null);
	}
	
	public ItemStack getNextPageItem() {
		return plugin.createItem(Material.LIME_STAINED_GLASS_PANE, 1, ChatColor.GREEN + "Next Page", null);
	}
	
	public ItemStack getPreviousPageItem() {
		return plugin.createItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1, ChatColor.AQUA + "Previous Page", null);
	}
	
	public ItemStack getBlankItem() {
		return plugin.createItem(Material.GRAY_STAINED_GLASS_PANE, 1, " ", null);
	}
	
	public ItemStack getLockedItem() {
		return plugin.createItem(Material.BLACK_STAINED_GLASS_PANE, 1, ChatColor.DARK_GRAY + "LOCKED");
	}
	
	public ItemStack getUpgradedItem() {
		return plugin.createItem(Material.LIME_STAINED_GLASS_PANE, 1, ChatColor.GREEN + "UPGRADED");
	}
	
	public ItemStack getYesItem() {
		return plugin.createItem(Material.LIME_STAINED_GLASS_PANE, 1, ChatColor.GREEN + "Yes", null);
	}
	
	public ItemStack getNoItem() {
		return plugin.createItem(Material.RED_STAINED_GLASS_PANE, 1, ChatColor.RED + "No", null);
	}
	
	public ItemStack getScrollsOfBaugInfoItem() {
		return plugin.createItem(
				Material.NETHER_STAR,
				1,
				ChatColor.GOLD + "Scrolls of Baug", 
				Arrays.asList(ChatColor.LIGHT_PURPLE + "This menu gives you access to any and", "all information you need to know to play on this server"));
	}
	
	public ItemStack getSkillTreeMenuItem() {
		return plugin.createItem(
				Material.OAK_SAPLING,
				1,
				ChatColor.GOLD + "Skill Tree",
				Arrays.asList(ChatColor.LIGHT_PURPLE + "Upgrade active and passive skills", "based on your class and race."));
	}
	
	public ItemStack getGeneralSkillTreeInfoItem() {
		return plugin.createItem(
				Material.NETHER_STAR,
				1,
				ChatColor.GOLD + "General Skill Tree", 
				Arrays.asList(ChatColor.LIGHT_PURPLE + "Upgrade different passive skills."));
	}
	
	public ItemStack getGeneralSkillTreeMenuItem() {
		return plugin.createItem(
				Material.OAK_SAPLING,
				1, 
				ChatColor.GOLD + "General Skills", 
				Arrays.asList(ChatColor.LIGHT_PURPLE + "Passive skills every player can use."));
	}
	
	public ItemStack getRaceSkillTreeMenuItem(Player player) {
		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
		
	 	PersistentDataContainer data = player.getPersistentDataContainer();
		int race = data.get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER);

		Material professionMaterial = Material.NETHERITE_SWORD;
		
		
		if (skillsconfig.getString("class") == null) {
			switch (race) {
				case 1:
					return getManChooseYourClassItem();
				case 2:
					return getElfChooseYourClassItem();
				case 3:
					return getDwarfChooseYourClassItem();
				case 4:
					return getOrcChooseYourClassItem();
			}
		} else {
			String profession = skillsconfig.getString("class");
			if (profession.equals("Stable Master"))
				professionMaterial = Material.LEATHER_HORSE_ARMOR;
			if (profession.equals("Steeled Armorer"))
				professionMaterial = Material.IRON_CHESTPLATE;
			if (profession.equals("Verdant Shepherd"))
				professionMaterial = Material.STICK;
			if (profession.equals("Radiant Metallurgist"))
				professionMaterial = Material.BLAST_FURNACE;
			if (profession.equals("Arcane Jeweler"))
				professionMaterial = Material.DIAMOND;
			if (profession.equals("Gilded Miner"))
				professionMaterial = Material.GOLDEN_PICKAXE;
			if (profession.equals("Enchanted Botanist"))
				professionMaterial = Material.LILY_OF_THE_VALLEY;
			if (profession.equals("Woodland Craftsman"))
				professionMaterial = Material.ANVIL;
			if (profession.equals("Lunar Artificer"))
				professionMaterial = Material.NETHER_STAR;
			if (profession.equals("Dark Alchemist"))
				professionMaterial = Material.POTION;
			if (profession.equals("Enraged Berserker"))
				professionMaterial = Material.NETHERITE_AXE;
			if (profession.equals("Greedy Scrapper"))
				professionMaterial = Material.NETHERITE_SCRAP;
			return plugin.createItem(
					professionMaterial,
					1, 
					ChatColor.GOLD + profession + " Skills", 
					Arrays.asList(ChatColor.LIGHT_PURPLE + "Race specific skills that are more speciallized."));
			}
		return getManChooseYourClassItem();
	}
	
	public ItemStack getRaceSkillTreeInfoItem() {
		return plugin.createItem(
				Material.NETHER_STAR,
				1,
				ChatColor.GOLD + "Race Skill Tree", 
				Arrays.asList(ChatColor.LIGHT_PURPLE + "Upgrade different race and class skills."));
	}
	
	public ItemStack getCommunistHubItem() {
		return plugin.createItem(
				Material.CHEST,
				1,
				ChatColor.GOLD + "Shared Inventories",
				Arrays.asList(ChatColor.LIGHT_PURPLE + "Access fellow Elves' ", "inventories and ender chests"));
	}
	
	public ItemStack getDwarvenBankConversionItem() {
		return plugin.createItem(
				Material.GOLD_INGOT,
				1,
				ChatColor.GOLD + "Bank Deposit",
				Arrays.asList(ChatColor.LIGHT_PURPLE + "Convert gold ingots to Dwarven Gold."));
	}
	
	public ItemStack getWizardScrollsOfBaugInfoItem() {
		return plugin.createItem(
				Material.NETHER_STAR,
				1,
				ChatColor.GOLD + "Scrolls of Baug", 
				Arrays.asList(ChatColor.LIGHT_PURPLE + "Welcome to the power menu.", "", "This menu gives you access to every feature", "given to the other races on this server", "You can use these to spy on your subjects",
						"and play god as you please", "", "Your job is to control the populous without them knowing.", "You are the final boss of the BaugRPG"));
	}
	
	public ItemStack getFeatureManagementItem() {
		return plugin.createItem(
				Material.WRITABLE_BOOK,
				1,
				ChatColor.AQUA + "Feature Management", 
				Arrays.asList(ChatColor.LIGHT_PURPLE + "Turn certain features on and off,", "according to how you wish to run your server."));
	}
	
	public ItemStack getInventorySnoopingItem() {
		return plugin.createItem(
				Material.CHEST,
				1,
				ChatColor.AQUA + "Inventory Snooping", 
				Arrays.asList(ChatColor.LIGHT_PURPLE +  "Access every players' inventories and ender chests"));
	}
	
	public ItemStack getViewAllCustomItemsItem() {
		return plugin.createItem(
				Material.CRAFTING_TABLE,
				1,
				ChatColor.AQUA + "Custom Items", 
				Arrays.asList(ChatColor.LIGHT_PURPLE +  "View every custom item and recipes."));
	}
	
	public ItemStack getRaceSelectionItem() {
		return plugin.createItem(
				Material.NETHER_STAR, 
				1, 
				ChatColor.YELLOW + "Choose Your Race", 
				Arrays.asList(ChatColor.LIGHT_PURPLE + "This race will determine the way you build", "the way you play, and the way you", "interact with players on this server.", "", "It cannot be changed later."));
	}
	
	public ItemStack getSelectManItem() {
		return plugin.createItem(
				Material.NETHERITE_SWORD, 
				1, 
				ChatColor.DARK_AQUA + "Men", 
				Arrays.asList(ChatColor.LIGHT_PURPLE + "Masters of the sword and horse combat,", "Men prefer to live in flat open areas", "with lots of room to roam about."));
	}
	
	public ItemStack getSelectElfItem() {
		return plugin.createItem(
				Material.BOW, 
				1, 
				ChatColor.DARK_GREEN + "Elves", 
				Arrays.asList(ChatColor.LIGHT_PURPLE + "Masters of the bow and ranged combat", "Elves reside in wooded areas", "due to their love of nature."));
	}
	
	public ItemStack getSelectDwarfItem() {
		return plugin.createItem(
				Material.NETHERITE_AXE, 
				1, 
				ChatColor.DARK_PURPLE + "Dwarves", 
				Arrays.asList(ChatColor.LIGHT_PURPLE + "Masters of the axe and melee combat", "Dwarves dwell within the earth", "constantly delving for the", "riches beneath the surface."));
	}
	
	public ItemStack getSelectOrcItem() {
		return plugin.createItem(
				Material.NETHERITE_HELMET, 
				1, 
				ChatColor.DARK_RED + "Orcs", 
				Arrays.asList(ChatColor.LIGHT_PURPLE + "Masters of iron forged weaponry and brutal combat", "Orcs smoulder in the fires of hell", "for they cannot go to the surface."));
	}
	
	public ItemStack getStableMasterItem() {
		return plugin.createItem(
				Material.LEATHER_HORSE_ARMOR, 
				1, 
				ChatColor.DARK_AQUA + "Stable Master");
	}
	
	public ItemStack getSteeledArmorerItem() {
		return plugin.createItem(
				Material.IRON_CHESTPLATE, 
				1, 
				ChatColor.DARK_AQUA + "Steeled Armorer");
	}
	
	public ItemStack getVerdantShepherdItem() {
		return plugin.createItem(
				Material.STICK, 
				1, 
				ChatColor.DARK_AQUA + "Verdant Shepherd");
	}
	
	public ItemStack getEnchantedBotanistItem() {
		return plugin.createItem(
				Material.LILY_OF_THE_VALLEY, 
				1, 
				ChatColor.DARK_GREEN + "Enchanted Botanist");
	}
	
	public ItemStack getLunarArtificerItem() {
		return plugin.createItem(
				Material.NETHER_STAR, 
				1,
				ChatColor.DARK_GREEN + "Lunar Artificer");
	}
	
	public ItemStack getWoodlandCraftsmanItem() {
		return plugin.createItem(
				Material.ANVIL, 
				1, 
				ChatColor.DARK_GREEN + "Woodland Craftsman");
	}
	
	public ItemStack getRadiantMetallurgistItem() {
		return plugin.createItem(
				Material.BLAST_FURNACE, 
				1, 
				ChatColor.DARK_PURPLE + "Radiant Metallurgist");
	}
	
	public ItemStack getArcaneJewelerItem() {
		return plugin.createItem(
				Material.DIAMOND, 
				1, 
				ChatColor.DARK_PURPLE + "Arcane Jeweler");
	}
	
	public ItemStack getGildedMinerItem( ) {
		return plugin.createItem(
				Material.GOLDEN_PICKAXE, 
				1, 
				ChatColor.DARK_PURPLE + "Gilded Miner");
	}
	
	public ItemStack getDarkAlchemistItem() {
		return plugin.createItem(
				Material.POTION, 
				1, 
				ChatColor.DARK_RED + "Dark Alchemist");
	}
	
	public ItemStack getEnragedBerserkerItem() {
		return plugin.createItem(
				Material.NETHERITE_AXE, 
				1, 
				ChatColor.DARK_RED + "Enraged Berserker");
	}
	
	public ItemStack getGreedyScrapperItem() {
		return plugin.createItem(
				Material.NETHERITE_SCRAP, 
				1, 
				ChatColor.DARK_RED + "Greedy Scrapper");
	}
	
	public ItemStack getConfirmBankTransferItem() {
		return plugin.createItem(
				Material.LIME_STAINED_GLASS_PANE, 
				1, 
				ChatColor.GREEN + "CONFIRM", 
				Arrays.asList(ChatColor.LIGHT_PURPLE + "Place gold ingots to the left, ", "and click here to confirm conversion."));
	}
	
	public ItemStack getDwarfChooseYourClassItem() {
		return plugin.createItem(
				Material.NETHERITE_AXE,
				1, 
				ChatColor.GOLD + "Race Skills", 
				Arrays.asList(ChatColor.LIGHT_PURPLE + "Choose your class."));
	}
	
	public ItemStack getElfChooseYourClassItem() {
		return plugin.createItem(
				Material.BOW,
				1, 
				ChatColor.GOLD + "Race Skills", 
				Arrays.asList(ChatColor.LIGHT_PURPLE + "Choose your class."));
	}
	
	public ItemStack getManChooseYourClassItem() {
		return plugin.createItem(
				Material.NETHERITE_SWORD,
				1, 
				ChatColor.GOLD + "Race Skills", 
				Arrays.asList(ChatColor.LIGHT_PURPLE + "Choose your class."));
	}
	
	public ItemStack getOrcChooseYourClassItem() {
		return plugin.createItem(
				Material.NETHERITE_HELMET,
				1, 
				ChatColor.GOLD + "Race Skills", 
				Arrays.asList(ChatColor.LIGHT_PURPLE + "Choose your class."));
	}
	
	public ItemStack getCommunistInventoryItem() {
		return plugin.createItem(
				Material.CHEST,
				1,
				ChatColor.YELLOW + "Inventories",
				Arrays.asList(ChatColor.LIGHT_PURPLE + "Access other Elves' Inventories"));
	}
	
	public ItemStack getCommunistEnderChestItem() {
		return plugin.createItem(
				Material.ENDER_CHEST, 
				1, 
				ChatColor.DARK_GREEN + "Ender Chests", 
				Arrays.asList(ChatColor.LIGHT_PURPLE + "Access other Elves' Ender Chests"));
	}
	
	public ItemStack getInventorySnoopingInventoryItem() {
		return plugin.createItem(
				Material.CHEST, 
				1, 
				ChatColor.YELLOW + "Inventories", 
				Arrays.asList(ChatColor.LIGHT_PURPLE + "Access other players' Inventories"));
	}
	
	public ItemStack getInventorySnoopingEnderChestItem() {
		return plugin.createItem(
				Material.ENDER_CHEST, 
				1, 
				ChatColor.DARK_GREEN + "Ender Chests", 
				Arrays.asList(ChatColor.LIGHT_PURPLE + "Access other players' Ender Chests"));
	}
	
	public ItemStack getTpaFeatureItem() {
		File file = new File(plugin.getDataFolder() + File.separator + "config.yml");
	 	FileConfiguration config = YamlConfiguration.loadConfiguration(file);
	 	ChatColor color = ChatColor.RED;
	 	if (config.getBoolean("allowTpa")) {
	 		color = ChatColor.GREEN;
	 	}
		return plugin.createItem(
				Material.ENDER_EYE, 
				1, 
				ChatColor.DARK_GREEN + "TPA", 
				Arrays.asList(ChatColor.LIGHT_PURPLE + "Allow Players to teleport.", color + config.get("allowTpa").toString().toUpperCase()));
	}
	
	public ItemStack getRecipeFeatureItem() {
		File file = new File(plugin.getDataFolder() + File.separator + "config.yml");
	 	FileConfiguration config = YamlConfiguration.loadConfiguration(file);
	 	ChatColor color = ChatColor.RED;
	 	if (config.getBoolean("allowRecipe")) {
	 		color = ChatColor.GREEN;
	 	}
		return plugin.createItem(
				Material.KNOWLEDGE_BOOK, 
				1, 
				ChatColor.DARK_AQUA + "Recipes", 
				Arrays.asList(ChatColor.LIGHT_PURPLE + "Allow Players to learn recipes.", color + config.get("allowRecipe").toString().toUpperCase()));
	}
	
	public ItemStack getEndermanGriefItem() {
		File file = new File(plugin.getDataFolder() + File.separator + "config.yml");
	 	FileConfiguration config = YamlConfiguration.loadConfiguration(file);
	 	ChatColor color = ChatColor.RED;
	 	if (config.getBoolean("allowEndermanGriefing")) {
	 		color = ChatColor.GREEN;
	 	}
		return plugin.createItem(
				Material.ENDER_PEARL, 
				1, 
				ChatColor.DARK_GREEN + "Enderman Griefing", 
				Arrays.asList(ChatColor.LIGHT_PURPLE + "Allow enderman to move blocks or not",
						ChatColor.LIGHT_PURPLE + "without affecting other mob griefing.",
						color + config.get("allowEndermanGriefing").toString().toUpperCase()));
	}
	
	public ItemStack getCreeperGriefItem() {
		File file = new File(plugin.getDataFolder() + File.separator + "config.yml");
	 	FileConfiguration config = YamlConfiguration.loadConfiguration(file);
	 	ChatColor color = ChatColor.RED;
	 	if (config.getBoolean("allowCreeperGriefing")) {
	 		color = ChatColor.GREEN;
	 	}
		return plugin.createItem(
				Material.CREEPER_HEAD, 
				1, 
				ChatColor.DARK_GREEN + "Creeper Griefing", 
				Arrays.asList(ChatColor.LIGHT_PURPLE + "Allow creepers to explode blocks or not",
						ChatColor.LIGHT_PURPLE + "without affecting other mob griefing.",
						color + config.get("allowCreeperGriefing").toString().toUpperCase()));
	}
	
	public ItemStack getTntGriefItem() {
		File file = new File(plugin.getDataFolder() + File.separator + "config.yml");
	 	FileConfiguration config = YamlConfiguration.loadConfiguration(file);
	 	ChatColor color = ChatColor.RED;
	 	if (config.getBoolean("allowTntGriefing")) {
	 		color = ChatColor.GREEN;
	 	}
		return plugin.createItem(
				Material.TNT, 
				1, 
				ChatColor.RED + "TNT Griefing", 
				Arrays.asList(ChatColor.LIGHT_PURPLE + "Allow TNT Griefing",
						color + config.get("allowTntGriefing").toString().toUpperCase()));
	}
	
	public ItemStack getGhastGriefItem() {
		File file = new File(plugin.getDataFolder() + File.separator + "config.yml");
	 	FileConfiguration config = YamlConfiguration.loadConfiguration(file);
	 	ChatColor color = ChatColor.RED;
	 	if (config.getBoolean("allowGhastGriefing")) {
	 		color = ChatColor.GREEN;
	 	}
		return plugin.createItem(
				Material.FIRE_CHARGE, 
				1, 
				ChatColor.GRAY + "Ghast Griefing", 
				Arrays.asList(ChatColor.LIGHT_PURPLE + "Allow Ghast Griefing",
						color + config.get("allowGhastGriefing").toString().toUpperCase()));
	}
	
	public ItemStack getMediumCoreDeathItem() {
		File file = new File(plugin.getDataFolder() + File.separator + "config.yml");
	 	FileConfiguration config = YamlConfiguration.loadConfiguration(file);
	 	ChatColor color = ChatColor.RED;
	 	if (config.getBoolean("mediumCoreDeathOn")) {
	 		color = ChatColor.GREEN;
	 	}
		return plugin.createItem(
				Material.SKELETON_SKULL, 
				1, 
				ChatColor.RED + "Medium Core Death", 
				Arrays.asList(ChatColor.LIGHT_PURPLE + "All items get deleted on death except",
						"for equipment and custom items.",
						"Overriden by Hardcore Death option.",
						color + config.get("mediumCoreDeathOn").toString().toUpperCase()));
	}
	
	public ItemStack getHardcoreDeathItem() {
		File file = new File(plugin.getDataFolder() + File.separator + "config.yml");
	 	FileConfiguration config = YamlConfiguration.loadConfiguration(file);
	 	ChatColor color = ChatColor.RED;
	 	if (config.getBoolean("hardcoreDeathOn")) {
	 		color = ChatColor.GREEN;
	 	}
		return plugin.createItem(
				Material.WITHER_SKELETON_SKULL, 
				1, 
				ChatColor.DARK_RED + "Hardcore Death", 
				Arrays.asList(ChatColor.LIGHT_PURPLE + "All items get deleted on death",
						"Overrides Medium Core Death option.",
						color + config.get("hardcoreDeathOn").toString().toUpperCase()));
	}
	
	public ItemStack getAutoBalanceItem() {
		File file = new File(plugin.getDataFolder() + File.separator + "config.yml");
	 	FileConfiguration config = YamlConfiguration.loadConfiguration(file);
	 	ChatColor color = ChatColor.RED;
	 	if (config.getBoolean("autoBalanceRaces")) {
	 		color = ChatColor.GREEN;
	 	}
		return plugin.createItem(
				Material.LEVER, 
				1, 
				ChatColor.DARK_RED + "Auto Balance", 
				Arrays.asList(ChatColor.LIGHT_PURPLE + "Automatically assign players to the least",
						"played team if the teams are unbalanced.",
						color + config.get("autoBalanceRaces").toString().toUpperCase()));
	}
	
	public ItemStack getStableMasterSkill1Item(Player player) {
	 	return plugin.createItem(
	 			Material.LEATHER_HORSE_ARMOR,
	 			1,
	 			ChatColor.DARK_AQUA + "Hermes Hooves",
	 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Increase mounted speed threefold.",
	 					getSecondDataSkillItemsString(player, "StableMaster1", "8 Points")));
	}
	
	public ItemStack getStableMasterSkill2Item(Player player) {
	 	return plugin.createItem(
	 			Material.IRON_SWORD,
	 			1,
	 			ChatColor.DARK_AQUA + "Mounted Mania",
	 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Increased damage when riding horses.",
	 					getSecondDataSkillItemsString(player, "StableMaster2", "9 Points")));
	}
	
	public ItemStack getStableMasterSkill3Item(Player player) {
	 	return plugin.createItem(
	 			Material.APPLE,
	 			1,
	 			ChatColor.DARK_AQUA + "Healthy Horses",
	 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Increased health when riding horses.",
	 					getSecondDataSkillItemsString(player, "StableMaster3", "8 Points")));
	}
	
	public ItemStack getSteeledArmorerSkill1Item(Player player) {
		if (plugin.steeledResolveCooldown.containsKey(player.getUniqueId())) {
	 		int minutesToMillis = 60000;
	 		if (plugin.steeledResolveCooldown.get(player.getUniqueId()) + (plugin.playerDamageListener.getSteeledResolveCooldownTime() * minutesToMillis) < System.currentTimeMillis()) {
	 			plugin.steeledResolveCooldown.remove(player.getUniqueId());
	 		}
	 	}
		if (plugin.steeledResolveCooldown.containsKey(player.getUniqueId())) {
			int minutesToMillis = 60000;
			Long timeRemaining = (plugin.steeledResolveCooldown.get(player.getUniqueId()) + (plugin.playerDamageListener.getSteeledResolveCooldownTime() * minutesToMillis)) - System.currentTimeMillis();
			String timeString = "";
			int timeValue;
			if (timeRemaining > minutesToMillis) {
				//display in minutes
				timeValue = (int)(timeRemaining / minutesToMillis);
				if (timeValue == 1) {
					timeString = (timeValue + " Minute Remaining");
				} else {
					timeString = (timeValue + " Minutes Remaining");
				}
			} else {
				//display in seconds
				timeValue = (int)(timeRemaining / 1000);
				if (timeValue == 1) {
					timeString = (timeValue + " Second Remaining");
				} else {
					timeString = (timeValue + " Seconds Remaining");
				}
				
			}
			return plugin.createItem(
		 			Material.TOTEM_OF_UNDYING,
		 			1,
		 			ChatColor.DARK_AQUA + "Steeled Resolve",
		 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Instead of dying, your fatal blow transforms",
		 					ChatColor.LIGHT_PURPLE + "you into a statue, saving you from death.",
		 					ChatColor.DARK_AQUA + "Cooldown: " + timeString,
		 					getSecondDataSkillItemsString(player, "SteeledArmorer1", "25 Points")));
		} else {
		 	return plugin.createItem(
		 			Material.TOTEM_OF_UNDYING,
		 			1,
		 			ChatColor.DARK_AQUA + "Steeled Resolve",
		 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Instead of dying, your fatal blow transforms",
		 					ChatColor.LIGHT_PURPLE + "you into a statue, saving you from death.",
		 					getSecondDataSkillItemsString(player, "SteeledArmorer1", "25 Points")));
		}
	}
	
	public ItemStack getVerdantShepherdSkill1Item(Player player) {
		if (plugin.shepherdsGraceCooldown.containsKey(player.getUniqueId())) {
	 		int minutesToMillis = 60000;
	 		if (plugin.shepherdsGraceCooldown.get(player.getUniqueId()) + (plugin.playerDamageListener.getShepherdsGraceCooldownTime() * minutesToMillis) < System.currentTimeMillis()) {
	 			plugin.shepherdsGraceCooldown.remove(player.getUniqueId());
	 		}
	 	}
		if (plugin.shepherdsGraceCooldown.containsKey(player.getUniqueId())) {
			int minutesToMillis = 60000;
			Long timeRemaining = (plugin.shepherdsGraceCooldown.get(player.getUniqueId()) + (plugin.playerDamageListener.getShepherdsGraceCooldownTime() * minutesToMillis)) - System.currentTimeMillis();
			String timeString = "";
			int timeValue;
			if (timeRemaining > minutesToMillis) {
				//display in minutes
				timeValue = (int)(timeRemaining / minutesToMillis);
				if (timeValue == 1) {
					timeString = (timeValue + " Minute Remaining");
				} else {
					timeString = (timeValue + " Minutes Remaining");
				}
			} else {
				//display in seconds
				timeValue = (int)(timeRemaining / 1000);
				if (timeValue == 1) {
					timeString = (timeValue + " Second Remaining");
				} else {
					timeString = (timeValue + " Seconds Remaining");
				}
				
			}
			return plugin.createItem(
		 			Material.SPORE_BLOSSOM,
		 			1,
		 			ChatColor.DARK_AQUA + "Shepherd's Grace",
		 			Arrays.asList(ChatColor.LIGHT_PURPLE + "At low health, heal all other",
		 					ChatColor.LIGHT_PURPLE + "men in a radius of 10 blocks.",
		 					ChatColor.DARK_AQUA + "Cooldown: " + timeString,
		 					getSecondDataSkillItemsString(player, "VerdantShepherd1", "25 Points")));
		} else {
			return plugin.createItem(
		 			Material.SPORE_BLOSSOM,
		 			1,
		 			ChatColor.DARK_AQUA + "Shepherd's Grace",
		 			Arrays.asList(ChatColor.LIGHT_PURPLE + "At low health, heal all other",
		 					ChatColor.LIGHT_PURPLE + "men in a radius of 10 blocks.",
		 					getSecondDataSkillItemsString(player, "VerdantShepherd1", "25 Points")));
		}
	}
	
	public ItemStack getEnchantedBotanistSkill1Item(Player player) {
	 	return plugin.createItem(
	 			Material.IRON_HOE,
	 			1,
	 			ChatColor.DARK_GREEN + "Efficient Botany",
	 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Right click to replant crops.",
	 					getSecondDataSkillItemsString(player, "EnchantedBotanist1", "10 Points")));
	}
	
	public ItemStack getEnchantedBotanistSkill2Item(Player player) {
	 	return plugin.createItem(
	 			Material.CORNFLOWER,
	 			1,
	 			ChatColor.DARK_GREEN + "Enchanted Petals",
	 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Consuming flowers gives",
	 					ChatColor.LIGHT_PURPLE + "different potion effects.",
	 					getSecondDataSkillItemsString(player, "EnchantedBotanist2", "15 Points")));
	}
	
	public ItemStack getWoodlandCraftsmanSkill1Item(Player player) {
	 	return plugin.createItem(
	 			Material.GOLDEN_APPLE,
	 			1,
	 			ChatColor.DARK_GREEN + "Woodland Absorption",
	 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Slow Regen that is not dependent on hunger.",
	 					getSecondDataSkillItemsString(player, "WoodlandCraftsman1", "10 Points")));
	}
	
	public ItemStack getWoodlandCraftsmanSkill2Item(Player player) {
	 	return plugin.createItem(
	 			Material.OAK_SAPLING,
	 			1,
	 			ChatColor.DARK_GREEN + "Arborated Strike",
	 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Low percent chance for attacks",
	 					ChatColor.LIGHT_PURPLE + "to summon an Arborated Strike",
	 					ChatColor.LIGHT_PURPLE + "damaging everything it hits except for elves.",
	 					getSecondDataSkillItemsString(player, "WoodlandCraftsman2", "15 Points")));
	}
	
	public ItemStack getLunarArtificerSkill1Item(Player player) {
	 	return plugin.createItem(
	 			Material.SEA_LANTERN,
	 			1,
	 			ChatColor.DARK_GREEN + "Full Moon Lunar Transfusion",
	 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Transform items in a moon pool",
	 					ChatColor.LIGHT_PURPLE + "during a full moon.",
	 					getSecondDataSkillItemsString(player, "LunarArtificer1", "6 Points")));
	}
	
	public ItemStack getLunarArtificerSkill2Item(Player player) {
		if (plugin.starlightHealingCooldown.containsKey(player.getUniqueId())) {
	 		int minutesToMillis = 60000;
	 		if (plugin.starlightHealingCooldown.get(player.getUniqueId()) + (plugin.starlightHealingListener.getStarlightHealingCooldownTime() * minutesToMillis) < System.currentTimeMillis()) {
	 			plugin.starlightHealingCooldown.remove(player.getUniqueId());
	 		}
	 	}
		if (plugin.starlightHealingCooldown.containsKey(player.getUniqueId())) {
			int minutesToMillis = 60000;
			Long timeRemaining = (plugin.starlightHealingCooldown.get(player.getUniqueId()) + (plugin.starlightHealingListener.getStarlightHealingCooldownTime() * minutesToMillis)) - System.currentTimeMillis();
			String timeString = "";
			int timeValue;
			if (timeRemaining > minutesToMillis) {
				//display in minutes
				timeValue = (int)(timeRemaining / minutesToMillis);
				if (timeValue == 1) {
					timeString = (timeValue + " Minute Remaining");
				} else {
					timeString = (timeValue + " Minutes Remaining");
				}
			} else {
				//display in seconds
				timeValue = (int)(timeRemaining / 1000);
				if (timeValue == 1) {
					timeString = (timeValue + " Second Remaining");
				} else {
					timeString = (timeValue + " Seconds Remaining");
				}
				
			}
			return plugin.createItem(
		 			Material.NETHER_STAR,
		 			1,
		 			ChatColor.DARK_GREEN + "Starlight Healing",
		 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Heal one nearby elf using starlight power.",
		 					ChatColor.DARK_AQUA + "Cooldown: " + timeString,
		 					getSecondDataSkillItemsString(player, "LunarArtificer2", "13 Points")));
		} else {
			return plugin.createItem(
		 			Material.NETHER_STAR,
		 			1,
		 			ChatColor.DARK_GREEN + "Starlight Healing",
		 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Heal one nearby elf using starlight power.",
		 					getSecondDataSkillItemsString(player, "LunarArtificer2", "13 Points")));
		}
	 	
	}
	
	public ItemStack getLunarArtificerSkill3Item(Player player) {
	 	return plugin.createItem(
	 			Material.SHROOMLIGHT,
	 			1,
	 			ChatColor.DARK_GREEN + "New Moon Lunar Transfusion",
	 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Transform items in a moon pool",
	 					ChatColor.LIGHT_PURPLE + "during a new moon.",
	 					getSecondDataSkillItemsString(player, "LunarArtificer3", "6 Points")));
	}
	
	public ItemStack getRadiantMetallurgistSkill1Item(Player player) {
	 	return plugin.createItem(
	 			Material.ANVIL,
	 			1,
	 			ChatColor.DARK_PURPLE + "Radiant Anvils",
	 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Chance for attacks to strike enemies",
	 					ChatColor.LIGHT_PURPLE + "with a barrage of anvils.",
	 					ChatColor.LIGHT_PURPLE + "Struck enemies become irradiated.",
	 					getSecondDataSkillItemsString(player, "RadiantMetallurgist1", "25 Points")));
	}
	
	public ItemStack getArcaneJewelerSkill1Item(Player player) {
	 	return plugin.createItem(
	 			Material.DIAMOND,
	 			1,
	 			ChatColor.DARK_PURPLE + "Arcane Jewels",
	 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Chance for attacks to summon",
	 					ChatColor.LIGHT_PURPLE + "a whirlwind of gems, revitalizing",
	 					ChatColor.LIGHT_PURPLE + "any nearby dwarves.",
	 					getSecondDataSkillItemsString(player, "ArcaneJeweler1", "25 Points")));
	}
	
	public ItemStack getGildedMinerSkill1Item(Player player) {
	 	return plugin.createItem(
	 			Material.GOLDEN_PICKAXE,
	 			1,
	 			ChatColor.DARK_PURPLE + "Gilded Fortune",
	 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Permanent fortune buff.",
	 					getSecondDataSkillItemsString(player, "GildedMiner1", "6 Points")));
	}
	
	public ItemStack getGildedMinerSkill2Item(Player player) {
	 	return plugin.createItem(
	 			Material.MINECART,
	 			1,
	 			ChatColor.DARK_PURPLE + "Powered Minecarts",
	 			Arrays.asList(ChatColor.LIGHT_PURPLE + "All minecarts act as", 
	 					ChatColor.LIGHT_PURPLE + "though they are being powered.",
	 					getSecondDataSkillItemsString(player, "GildedMiner2", "13 Points")));
	}
	
	public ItemStack getGildedMinerSkill3Item(Player player) {
	 	return plugin.createItem(
	 			Material.GOLDEN_PICKAXE,
	 			1,
	 			ChatColor.DARK_PURPLE + "Gilded Haste",
	 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Permanent haste buff.",
	 					getSecondDataSkillItemsString(player, "GildedMiner3", "6 Points")));
	}
	
	public ItemStack getDarkAlchemistSkill1Item(Player player) {
	 	return plugin.createItem(
	 			Material.POTION,
	 			1,
	 			ChatColor.DARK_RED + "Powerful Alchemy",
	 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Thrown potions have double the given effect.",
	 					getSecondDataSkillItemsString(player, "DarkAlchemist1", "8 Points")));
	}
	
	public ItemStack getDarkAlchemistSkill2Item(Player player) {
	 	return plugin.createItem(
	 			Material.CAULDRON,
	 			1,
	 			ChatColor.DARK_RED + "Magma Transmutation",
	 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Transform lava to water in a cauldron.",
	 					getSecondDataSkillItemsString(player, "DarkAlchemist2", "9 Points")));
	}
	
	public ItemStack getDarkAlchemistSkill3Item(Player player) {
	 	return plugin.createItem(
	 			Material.POTION,
	 			1,
	 			ChatColor.DARK_RED + "Lasting Alchemy",
	 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Thrown potions last twice as long.",
	 					getSecondDataSkillItemsString(player, "DarkAlchemist3", "8 Points")));
	}
	
	public ItemStack getEnragedBerserkerSkill1Item(Player player) {

		if (plugin.rageCooldown.containsKey(player.getUniqueId())) {
	 		int minutesToMillis = 60000;
	 		if (plugin.rageCooldown.get(player.getUniqueId()) + (plugin.orcRageListener.getRageCooldownTime() * minutesToMillis) < System.currentTimeMillis()) {
	 			plugin.rageCooldown.remove(player.getUniqueId());
	 		}
	 	}
		if (plugin.rageCooldown.containsKey(player.getUniqueId())) {
			int minutesToMillis = 60000;
			Long timeRemaining = (plugin.rageCooldown.get(player.getUniqueId()) + (plugin.orcRageListener.getRageCooldownTime() * minutesToMillis)) - System.currentTimeMillis();
			String timeString = "";
			int timeValue;
			if (timeRemaining > minutesToMillis) {
				//display in minutes
				timeValue = (int)(timeRemaining / minutesToMillis);
				if (timeValue == 1) {
					timeString = (timeValue + " Minute Remaining");
				} else {
					timeString = (timeValue + " Minutes Remaining");
				}
			} else {
				//display in seconds
				timeValue = (int)(timeRemaining / 1000);
				if (timeValue == 1) {
					timeString = (timeValue + " Second Remaining");
				} else {
					timeString = (timeValue + " Seconds Remaining");
				}
				
			}
			return plugin.createItem(
		 			Material.NETHERITE_AXE,
		 			1,
		 			ChatColor.DARK_RED + "Rage",
		 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Hitting a killstreak of 5",
		 					ChatColor.LIGHT_PURPLE + "sends you into a blind rage.",
		 					ChatColor.DARK_RED + "Cooldown: " + timeString,
		 					getSecondDataSkillItemsString(player, "EnragedBerserker1", "15 Points")));
		} else {
			return plugin.createItem(
		 			Material.NETHERITE_AXE,
		 			1,
		 			ChatColor.DARK_RED + "Rage",
		 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Hitting a killstreak of 5",
		 					ChatColor.LIGHT_PURPLE + "sends you into a blind rage.",
		 					getSecondDataSkillItemsString(player, "EnragedBerserker1", "15 Points")));
		}
		
		
		
		
		
		
	 	
	}
	
	public ItemStack getEnragedBerserkerSkill2Item(Player player) {
	 	return plugin.createItem(
	 			Material.WITHER_SKELETON_SKULL,
	 			1,
	 			ChatColor.DARK_RED + "Withered Beheading",
	 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Increased Wither Skull drops.",
	 					getSecondDataSkillItemsString(player, "EnragedBerserker2", "10 Points")));
	}
	
	public ItemStack getGreedyScrapperSkill1Item(Player player) {
	 	return plugin.createItem(
	 			Material.FISHING_ROD,
	 			1,
	 			ChatColor.DARK_RED + "Molten Fishing",
	 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Allows for fishing in lava.",
	 					getSecondDataSkillItemsString(player, "GreedyScrapper1", "5 Points")));
	}
	
	public ItemStack getGreedyScrapperSkill2Item(Player player) {

		if (plugin.greedyReinforcementCooldown.containsKey(player.getUniqueId())) {
	 		int minutesToMillis = 60000;
	 		if (plugin.greedyReinforcementCooldown.get(player.getUniqueId()) + (plugin.playerDamageListener.getGreedyReinforcementCooldownTime() * minutesToMillis) < System.currentTimeMillis()) {
	 			plugin.greedyReinforcementCooldown.remove(player.getUniqueId());
	 		}
	 	}
		if (plugin.greedyReinforcementCooldown.containsKey(player.getUniqueId())) {
			int minutesToMillis = 60000;
			Long timeRemaining = (plugin.greedyReinforcementCooldown.get(player.getUniqueId()) + (plugin.playerDamageListener.getGreedyReinforcementCooldownTime() * minutesToMillis)) - System.currentTimeMillis();
			String timeString = "";
			int timeValue;
			if (timeRemaining > minutesToMillis) {
				//display in minutes
				timeValue = (int)(timeRemaining / minutesToMillis);
				if (timeValue == 1) {
					timeString = (timeValue + " Minute Remaining");
				} else {
					timeString = (timeValue + " Minutes Remaining");
				}
			} else {
				//display in seconds
				timeValue = (int)(timeRemaining / 1000);
				if (timeValue == 1) {
					timeString = (timeValue + " Second Remaining");
				} else {
					timeString = (timeValue + " Seconds Remaining");
				}
				
			}
			
			return plugin.createItem(
		 			Material.NETHERITE_SCRAP,
		 			1,
		 			ChatColor.DARK_RED + "Greedy Reinforcements",
		 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Give nearby orcs a resistance",
		 					ChatColor.LIGHT_PURPLE + "buff at low health.",
		 					ChatColor.DARK_RED + "Cooldown: " + timeString,
		 					getSecondDataSkillItemsString(player, "GreedyScrapper2", "20 Points")));
		} else {
			return plugin.createItem(
		 			Material.NETHERITE_SCRAP,
		 			1,
		 			ChatColor.DARK_RED + "Greedy Reinforcements",
		 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Give nearby orcs a resistance",
		 					ChatColor.LIGHT_PURPLE + "buff at low health.",
		 					getSecondDataSkillItemsString(player, "GreedyScrapper2", "20 Points")));
		}
		
		
	}
	
	
	
	private String getSecondDataSkillItemsString(Player player, String skill, String priceString) {
		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
	 	
	 	String secondString = ChatColor.DARK_GRAY + "Unowned: " + ChatColor.LIGHT_PURPLE + priceString;
	 	if (skillsconfig.contains(skill)) {
	 		String enabledString = ChatColor.RED + "DISABLED";
	 		if (skillsconfig.getBoolean(skill)) {
	 			enabledString = ChatColor.GREEN + "ENABLED";
	 		}
	 		
	 		secondString = "Click to toggle: " + enabledString;
	 	}
	 	return secondString;
	 	
	}

	
	
	public ItemStack getMenBuffBiomeItem(Player player) {
		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
		
		String enabledString = ChatColor.RED + "DISABLED";
 		if (skillsconfig.getBoolean("menBuffBiomeOn")) {
 			enabledString = ChatColor.GREEN + "ENABLED";
 		}
		
		String secondString = "Click to toggle: " + enabledString;
		
	 	return plugin.createItem(
	 			Material.GRASS,
	 			1,
	 			ChatColor.DARK_AQUA + "Home Field Advantage",
	 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Increased strength to other",
	 					"players in open areas.",
	 					secondString));
	}
	
	public ItemStack getMenFallDamageNerfItem(Player player) {
		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
		
		String enabledString = ChatColor.RED + "DISABLED";
 		if (skillsconfig.getBoolean("menDangerOn")) {
 			enabledString = ChatColor.GREEN + "ENABLED";
 		}
		
		String secondString = "Click to toggle: " + enabledString;
		
	 	return plugin.createItem(
	 			Material.FEATHER,
	 			1,
	 			ChatColor.DARK_AQUA + "Reinforced Legs",
	 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Reduce fall damage.",
	 					secondString));
	}
	
	public ItemStack getElfBuffBiomeItem(Player player) {
		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
		
		String enabledString = ChatColor.RED + "DISABLED";
 		if (skillsconfig.getBoolean("elfBuffBiomeOn")) {
 			enabledString = ChatColor.GREEN + "ENABLED";
 		}
		
		String secondString = "Click to toggle: " + enabledString;
		
	 	return plugin.createItem(
	 			Material.OAK_SAPLING,
	 			1,
	 			ChatColor.DARK_GREEN + "Underbrush Protection",
	 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Increased strength to other",
	 					"players in forests",
	 					secondString));
	}
	
	public ItemStack getElfRespirationItem(Player player) {
		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
		
		String enabledString = ChatColor.RED + "DISABLED";
 		if (skillsconfig.getBoolean("elfDangerOn")) {
 			enabledString = ChatColor.GREEN + "ENABLED";
 		}
		
		String secondString = "Click to toggle: " + enabledString;
		
	 	return plugin.createItem(
	 			Material.WATER_BUCKET,
	 			1,
	 			ChatColor.DARK_GREEN + "Aqueous Insufflation",
	 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Increase drowning time.",
	 					secondString));
	}
	
	public ItemStack getDwarfBuffBiomeItem(Player player) {
		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
		
		String enabledString = ChatColor.RED + "DISABLED";
 		if (skillsconfig.getBoolean("dwarfBuffBiomeOn")) {
 			enabledString = ChatColor.GREEN + "ENABLED";
 		}
		
		String secondString = "Click to toggle: " + enabledString;
		
	 	return plugin.createItem(
	 			Material.STONE,
	 			1,
	 			ChatColor.DARK_PURPLE + "Mountainous Grit",
	 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Increased strength to other",
	 					"players in the mountains", 
	 					"and underground.",
	 					secondString));
	}
	
	public ItemStack getDwarfUndergroundMobResistanceItem(Player player) {
		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
		
		String enabledString = ChatColor.RED + "DISABLED";
 		if (skillsconfig.getBoolean("dwarfDangerOn")) {
 			enabledString = ChatColor.GREEN + "ENABLED";
 		}
		
		String secondString = "Click to toggle: " + enabledString;
		
	 	return plugin.createItem(
	 			Material.REDSTONE_TORCH,
	 			1,
	 			ChatColor.DARK_PURPLE + "Shaded Security",
	 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Resistance to underground monsters.",
	 					secondString));
	}
	
	public ItemStack getOrcBuffBiomeItem(Player player) {
		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
		
		String enabledString = ChatColor.RED + "DISABLED";
 		if (skillsconfig.getBoolean("orcBuffBiomeOn")) {
 			enabledString = ChatColor.GREEN + "ENABLED";
 		}
		
		String secondString = "Click to toggle: " + enabledString;
		
	 	return plugin.createItem(
	 			Material.NETHERRACK,
	 			1,
	 			ChatColor.DARK_RED + "Hellish Reposte",
	 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Increased strength to other",
	 					"players in the nether.",
	 					secondString));
	}
	
	public ItemStack getOrcLavaImmunityItem(Player player) {
		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
		
		String enabledString = ChatColor.RED + "DISABLED";
 		if (skillsconfig.getBoolean("orcDangerOn")) {
 			enabledString = ChatColor.GREEN + "ENABLED";
 		}
		
		String secondString = "Click to toggle: " + enabledString;
		
	 	return plugin.createItem(
	 			Material.LAVA_BUCKET,
	 			1,
	 			ChatColor.DARK_RED + "Iron Skin",
	 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Reduce damage to lava.",
	 					secondString));
	}
	
	public ItemStack getGovernmentMenuItem(int race) {
		String title = ChatColor.DARK_AQUA + "Kingdom Info";
		List<String> infoText = Arrays.asList(ChatColor.LIGHT_PURPLE + "View info about who your",
				"current King is and more.");
		switch (race) {
			case 2:
				title = ChatColor.DARK_GREEN + "Commune Info";
				infoText = Arrays.asList(ChatColor.LIGHT_PURPLE + "View info about who your",
						"current Secretary is and more.");
				break;
			case 3:
				title = ChatColor.DARK_PURPLE + "Guild Info";
				infoText = Arrays.asList(ChatColor.LIGHT_PURPLE + "View info about who your",
						"current Emperor is and more.");
				break;
			case 4:
				title = ChatColor.DARK_RED + "Horde Info";
				infoText = Arrays.asList(ChatColor.LIGHT_PURPLE + "View info about who your",
						"current Chief is and more.");
				break;
		}
		return plugin.createItem(Material.GOLDEN_HELMET,
				1,
				title,
				infoText);
	}
	
	public ItemStack getLeaderHeadItem(int race) {
		File leaderDataFile = new File(plugin.getDataFolder() + File.separator + "leaderData.yml");
		 
		 //Check to see if the file already exists. If not, create it.
		 if (!leaderDataFile.exists()) {
			 try {
				 leaderDataFile.createNewFile();
			 } catch (IOException e) {
				 e.printStackTrace();
			 }
		}
		FileConfiguration leaderConfig = YamlConfiguration.loadConfiguration(leaderDataFile);
		
		UUID leaderUUID = null;
		String leaderTitle = "";
		switch (race) {
			case 1:
				if (leaderConfig.contains("menLeaderUUID")) {
					leaderUUID = UUID.fromString(leaderConfig.getString("menLeaderUUID"));
				}
				leaderTitle = "King";
				break;
			case 2:
				if (leaderConfig.contains("elfLeaderUUID")) {
					leaderUUID = UUID.fromString(leaderConfig.getString("elfLeaderUUID"));
				}
				leaderTitle = "Secretary";
				break;
			case 3:
				File bankfile = new File(plugin.getDataFolder() + File.separator + "bank.yml");
				FileConfiguration bankConfig = YamlConfiguration.loadConfiguration(bankfile);
				
				File econfile = new File(plugin.getDataFolder() + File.separator + "econ.yml");
				FileConfiguration econConfig = YamlConfiguration.loadConfiguration(econfile);
				
				UUID emperorUUID = null;
				
				Set<String> banks = bankConfig.getKeys(false);
				for (String string : banks) {
					UUID uuid = UUID.fromString(string);

					File file = new File(plugin.getDataFolder() + File.separator + "inventoryData" + File.separator + string + ".yml");
					FileConfiguration config = YamlConfiguration.loadConfiguration(file);
					
					int playerRace = config.getInt("Race Data");
					if (Bukkit.getOfflinePlayer(uuid).isOnline()) {
						PersistentDataContainer data = Bukkit.getPlayer(uuid).getPersistentDataContainer();
						playerRace = data.get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER);
					}
					
					if (playerRace == 3) {
						if (emperorUUID == null) {
							emperorUUID = uuid;
						} else if (bankConfig.getInt(string) + econConfig.getInt(string) > bankConfig.getInt(emperorUUID.toString()) + econConfig.getInt(emperorUUID.toString())) {
							emperorUUID = uuid;
						}
					}
				}
				leaderUUID = emperorUUID;
				leaderConfig.set("dwarfLeaderUUID", leaderUUID.toString());
				
				try {
					leaderConfig.save(leaderDataFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				leaderTitle = "Emperor";
				break;
			case 4:
				for (OfflinePlayer orc : plugin.getAllOfflineOrcs()) {
					if (leaderUUID == null) {
						leaderUUID = orc.getUniqueId();
					} else if (orc.getStatistic(Statistic.PLAYER_KILLS) > Bukkit.getOfflinePlayer(leaderUUID).getStatistic(Statistic.PLAYER_KILLS)) {
						leaderUUID = orc.getUniqueId();
					}
				}
				
				leaderConfig.set("orcLeaderUUID", leaderUUID.toString());
				
				try {
					leaderConfig.save(leaderDataFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				leaderTitle = "Chief";
				break;
		}
		
		if (leaderUUID == null) {
			return plugin.createItem(Material.PLAYER_HEAD, 1, getRaceColor(race) + "UNKNOWN", Arrays.asList("You currently have no " + leaderTitle));
		} else {
			OfflinePlayer leaderOP = Bukkit.getOfflinePlayer(leaderUUID);
			ItemStack leaderHead = plugin.createItem(Material.PLAYER_HEAD,
					1,
					getRaceColor(race) + leaderOP.getName(),
					Arrays.asList("is your " + leaderTitle,
							"They can claim land,",
							"declare war,",
							"draft laws,",
							"and more."));
			SkullMeta leaderHeadMeta = (SkullMeta)leaderHead.getItemMeta();
			leaderHeadMeta.setOwningPlayer(leaderOP);
			leaderHead.setItemMeta(leaderHeadMeta);
			return leaderHead;
		}
		
	}
	
	public ItemStack getVoteOnLeaderItem(int race) {
		String leaderTitle = "King";
		if (race == 2) {
			leaderTitle = "Secretary";
		}
		return plugin.createItem(Material.NAME_TAG,
				1,
				getRaceColor(race) + "Vote for which player will your " + leaderTitle);
	}
	
	public ItemStack getAlreadyVotedItem() {
		return plugin.createItem(Material.BARRIER,
				1,
				ChatColor.DARK_RED + "You already voted.");
	}
	
	public ItemStack getNominateSelfItem(int race) {
		String leaderTitle = "King";
		if (race == 2) {
			leaderTitle = "Secretary";
		}
		return plugin.createItem(Material.WRITABLE_BOOK,
				1,
				getRaceColor(race) + "Nominate yourself for " + leaderTitle);
	}
	
	public ItemStack getOverthrowKingItem() {
		return plugin.createItem(
				Material.IRON_SWORD,
				1,
				ChatColor.DARK_AQUA + "Vote to overthrow your current king.");
	}

	public ItemStack getImpeachSecretaryItem() {
		return plugin.createItem(
				Material.WOODEN_SWORD,
				1,
				ChatColor.DARK_GREEN + "Vote to impeach your current secretary.");
	}
	
	
	public ChatColor getRaceColor(int race) {//I PROMISE this method isn't racist. PLEASE BELIEVE ME.
		switch (race) {
			case 1:
				return ChatColor.DARK_AQUA;
			case 2:
				return ChatColor.DARK_GREEN;
			case 3:
				return ChatColor.DARK_PURPLE;
			case 4:
				return ChatColor.DARK_RED;
		}
		return ChatColor.AQUA;
	}

	public ItemStack getSubmitLawSuggestionItem(int race) {
		return plugin.createItem(
				Material.WRITABLE_BOOK,
				1,
				getRaceColor(race) + "Submit a suggestion for a law.");
	}

	public ItemStack getVoteOnLawsItem() {
		return plugin.createItem(
				Material.NAME_TAG,
				1,
				ChatColor.DARK_GREEN + "Vote on different bills.");
	}

	public ItemStack getViewLawsItem(int race) {
		return plugin.createItem(
				Material.WRITTEN_BOOK,
				1,
				getRaceColor(race) + "View passed laws.");
	}

	public ItemStack getViewReportedCrimesItem(int race) {
		return plugin.createItem(
				Material.BOOK,
				1,
				getRaceColor(race) + "View alleged crimes.");
	}

	public ItemStack getReportCrimeItem(int race) {
		return plugin.createItem(
				Material.MAP,
				1,
				getRaceColor(race) + "Report a crime.");
	}

	public ItemStack getAppointKingItem() {
		return plugin.createItem(
				Material.GOLDEN_HELMET,
				1,
				ChatColor.DARK_AQUA + "Appoint a new king.");
	}

	public ItemStack getStepDownItem() {
		return plugin.createItem(
				Material.SPRUCE_STAIRS,
				1,
				ChatColor.DARK_GREEN + "Step down and start a new election for secretary.");
	}

	public ItemStack getDraftLawsItem(int race) {
		return plugin.createItem(
				Material.WRITABLE_BOOK,
				1,
				getRaceColor(race) + "Draft a new law.");
	}

	public ItemStack getViewLawSuggestionsItem(int race) {
		return plugin.createItem(
				Material.KNOWLEDGE_BOOK,
				1,
				getRaceColor(race) + "View suggested laws.");
	}

	public ItemStack getLeaderViewCrimesItem(int race) {
		return plugin.createItem(
				Material.BOOK,
				1,
				getRaceColor(race) + "View alleged crimes.");
	}
	
	public ItemStack getLeaderDecidePunishmentItem(int race) {
		ChatColor color = getRaceColor(race);
		if (race == 2) {
			color = ChatColor.WHITE;
		}
		return plugin.createItem(
				Material.IRON_BARS,
				1,
				color + "Decide punishments for guilty citizens.");
	}
	
	public ItemStack getDecidePunishmentItem() {
		return plugin.createItem(
				Material.IRON_BARS,
				1,
				ChatColor.DARK_GREEN + "Decide punishments for guilty citizens.");
	}

	public ItemStack getTaxDwarvesItem() {
		return plugin.createItem(
				Material.GOLD_NUGGET,
				1,
				ChatColor.DARK_PURPLE + "Decide how much you'll tax your citizens.");
	}
	
	public ItemStack getExecutionItem() {
		return plugin.createItem(
				Material.NETHERITE_AXE,
				1,
				ChatColor.DARK_RED + "View execution bounties you can redeem");
	}
	
	public ItemStack getTaxDwarvesWeeklyItem() {
		return plugin.createItem(
				Material.GOLD_NUGGET,
				1,
				ChatColor.DARK_PURPLE + "Collect the weekly tax.");
	}
	
	public ItemStack getBasicRecipeScrollItem() {
		ItemStack scroll = plugin.createItem(
				Material.MOJANG_BANNER_PATTERN,
				1,
				ChatColor.GRAY + "Basic Recipe");
		scroll.addUnsafeEnchantment(Enchantment.DURABILITY, 0);
		return scroll;
	}
	
	public ItemStack getIntermediateRecipeScrollItem() {
		ItemStack scroll = plugin.createItem(
				Material.MOJANG_BANNER_PATTERN,
				1,
				ChatColor.GRAY + "Intermediate Recipe");
		scroll.addUnsafeEnchantment(Enchantment.DURABILITY, 0);
		return scroll;
	}
	
	public ItemStack getAdvancedRecipeScrollItem() {
		ItemStack scroll = plugin.createItem(
				Material.MOJANG_BANNER_PATTERN,
				1,
				ChatColor.GRAY + "Advanced Recipe");
		scroll.addUnsafeEnchantment(Enchantment.DURABILITY, 0);
		return scroll;
	}
	
	public ItemStack getExpertRecipeScrollItem() {
		ItemStack scroll = plugin.createItem(
				Material.MOJANG_BANNER_PATTERN,
				1,
				ChatColor.GRAY + "Expert Recipe");
		scroll.addUnsafeEnchantment(Enchantment.DURABILITY, 0);
		return scroll;
	}
	
	public ItemStack getViewLearnedRecipesItem(int race) {
		return plugin.createItem(Material.CRAFTING_TABLE,
				1,
				getRaceColor(race) + "View recipes you've learned.");
	}
	
	public ItemStack getLearnedRecipesInfoItem() {
		return plugin.createItem(Material.NETHER_STAR,
				1,
				ChatColor.GOLD + "Click an item to view its recipe.");
	}
	
	public ItemStack getPlayerHead(OfflinePlayer player) {
		ItemStack head = plugin.createItem(Material.PLAYER_HEAD, 1, ChatColor.YELLOW + player.getName() + "'s Head");
		SkullMeta meta = (SkullMeta) head.getItemMeta();
		meta.setOwningPlayer(player);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "playerHead"), PersistentDataType.INTEGER, 1);
		head.setItemMeta(meta);
		
		return head;
	}
	
	public ItemStack getResetRaceItem() {
		return plugin.createItem(Material.PAPER,
				1,
				ChatColor.AQUA + "Reset Race",
				Arrays.asList(ChatColor.RED + "WARNING",
						"Use this item to permanently reset your race.",
						"You will restart with half your earned skill points.",
						"Your achievements will not reset.",
						"You will lose all learned recipes.",
						"You may not use this item more than once."));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//BEGIN LIST OF CRAFTABLE CUSTOM ITEMS
	
	
	
	
	//Stable Master
	
	//Basic
	
	public ItemStack getCordovanLeatherItem() {
		return plugin.createItem(Material.LEATHER,
				1,
				ChatColor.DARK_AQUA + "Cordovan Leather",
				Arrays.asList("Man Made", ChatColor.GREEN + "Basic Material"),
				1111);
	}
	
	public ItemStack getHorsehairItem() {
		return plugin.createItem(Material.STRING,
				2,
				ChatColor.DARK_AQUA + "Horsehair",
				Arrays.asList("Man Made", ChatColor.GREEN + "Basic Material"),
				1112);
	}
	
	public ItemStack getSharpenedHoofItem() {
		return plugin.createItem(Material.FLINT,
				2,
				ChatColor.DARK_AQUA + "Sharpened Hoof",
				Arrays.asList("Man Made", ChatColor.GREEN + "Basic Material"),
				1113);
	}
	
	public ItemStack getHorseshoeItem() {
		return plugin.createItem(Material.GOLDEN_HORSE_ARMOR,
				1,
				ChatColor.DARK_AQUA + "Horseshoe",
				Arrays.asList("Reduces fall damage taken by horses.", "Man Made", ChatColor.GREEN + "Basic Item"),
				1114);
	}
	
	public ItemStack getMorralItem() {
		return plugin.createItem(Material.IRON_HORSE_ARMOR,
				1,
				ChatColor.DARK_AQUA + "Morral",
				Arrays.asList("Prevents your horse from wandering.", "Man Made", ChatColor.GREEN + "Basic Item"),
				1115);
	}
	
	//Intermediate
	
	public ItemStack getRopeItem() {
		return plugin.createItem(Material.STRING,
				2,
				ChatColor.DARK_AQUA + "Rope",
				Arrays.asList("Man Made", ChatColor.YELLOW + "Intermediate Material"),
				1121);
	}
	
	public ItemStack getTemperedLeatherItem() {
		return plugin.createItem(Material.LEATHER,
				2,
				ChatColor.DARK_AQUA + "Tempered Leather",
				Arrays.asList("Man Made", ChatColor.YELLOW + "Intermediate Material"),
				1122);
	}
	
	public ItemStack getSpearItem() {
		return plugin.createItem(Material.TRIDENT,
				1,
				ChatColor.DARK_AQUA + "Spear",
				Arrays.asList("Temporarily paralyzes enemies when thrown.", "Man Made", ChatColor.YELLOW + "Intermediate Item"),
				1123);
	}
	
	public ItemStack getWhistleItem() {
		return plugin.createItem(Material.IRON_NUGGET,
				1,
				ChatColor.DARK_AQUA + "Whistle",
				Arrays.asList("Calls your horse to you.", "Man Made", ChatColor.YELLOW + "Intermediate Item"),
				1124);
	}
	
	public ItemStack getBridleItem() {
		return plugin.createItem(Material.GOLDEN_HORSE_ARMOR,
				1,
				ChatColor.DARK_AQUA + "Bridle",
				Arrays.asList("Increases your horse's jump height.", "Man Made", ChatColor.YELLOW + "Intermediate Item"),
				1125);
	}
	
	//Advanced
	
	public ItemStack getCordItem() {
		return plugin.createItem(Material.STRING,
				1,
				ChatColor.DARK_AQUA + "Cord",
				Arrays.asList("Man Made", ChatColor.GOLD + "Advanced Material"),
				1131);
	}
	
	public ItemStack getEnrichedLeatherItem() {
		return plugin.createItem(Material.LEATHER,
				1,
				ChatColor.DARK_AQUA + "Enriched Leather",
				Arrays.asList("Man Made", ChatColor.GOLD + "Advanced Material"),
				1132);
	}
	
	public ItemStack getRuggedSwordItem() {
		ItemStack ruggedSword = plugin.createItem(Material.STONE_SWORD,
				1,
				ChatColor.DARK_AQUA + "Rugged Sword",
				Arrays.asList("Charge by holding while riding a horse.", "Shift right click to view charge.", "Man Made", ChatColor.GOLD + "Advanced Item"),
				1133);
		ItemMeta meta = ruggedSword.getItemMeta();
		PersistentDataContainer data = meta.getPersistentDataContainer();
		data.set(new NamespacedKey(plugin, "charge"), PersistentDataType.FLOAT, 0f);
		ruggedSword.setItemMeta(meta);
		return ruggedSword;
	}
	
	public ItemStack getRegenerativeHorseArmorItem() {
		return plugin.createItem(Material.DIAMOND_HORSE_ARMOR,
				1,
				ChatColor.DARK_AQUA + "Regenerative Horse Armor",
				Arrays.asList("Gives your horse regeneration.", "Man Made", ChatColor.GOLD + "Advanced Item"),
				1134);
	}
	
	public ItemStack getEmblemOfTheStallionItem() {
		return plugin.createItem(Material.CLAY_BALL,
				1,
				ChatColor.DARK_AQUA + "Emblem of the Stallion",
				Arrays.asList("Increases your speed when carried.", "Man Made", ChatColor.GOLD + "Advanced Item"),
				1135);
	}
	
	//Expert
	
	public ItemStack getEnchantedWhistleItem() {
		return plugin.createItem(Material.IRON_NUGGET,
				1,
				ChatColor.DARK_AQUA + "Enchanted Whistle",
				Arrays.asList("Calls your horse from anywhere", "if it is wearing enchanted horse armor.", "Man Made", ChatColor.RED + "Expert Item"),
				1141);
	}
	
	public ItemStack getEnchantedHorseArmorItem() {
		return plugin.createItem(Material.DIAMOND_HORSE_ARMOR,
				1,
				ChatColor.DARK_AQUA + "Enchanted Horse Armor",
				Arrays.asList("Reciever for an enchanted whistle", "Man Made", ChatColor.RED + "Expert Item"),
				1142);
	}
	
	public ItemStack getSoulboundSwordItem() {
		return plugin.createItem(Material.DIAMOND_SWORD,
				1,
				ChatColor.DARK_AQUA + "Soulbound Sword",
				Arrays.asList("This item will be kept when you die.", "Man Made", ChatColor.RED + "Expert Item"),
				1143);
	}
	
	public ItemStack getSoulOfTheEquestrianItem() {
		return plugin.createItem(Material.HEART_OF_THE_SEA,
				1,
				ChatColor.DARK_AQUA + "Soul of the Equestrian",
				Arrays.asList("Saves you from death and", "summons a stampede.", "Only usable by Stable Masters.", "Shift right click to view cooldown.", "Man Made", ChatColor.RED + "Expert Item"),
				1144);
	}
	
	
	
	
	
	//Steeled Armorer
	
	//Basic
	
	public ItemStack getIronPlateItem() {
		return plugin.createItem(Material.IRON_INGOT,
				5,
				ChatColor.DARK_AQUA + "Iron Plate",
				Arrays.asList("Man Made", ChatColor.GREEN + "Basic Material"),
				1211);
	}
	
	public ItemStack getScrewsItem() {
		return plugin.createItem(Material.IRON_NUGGET,
				1,
				ChatColor.DARK_AQUA + "Screws",
				Arrays.asList("Man Made", ChatColor.GREEN + "Basic Material"),
				1212);
	}
	
	public ItemStack getMeshItem() {
		return plugin.createItem(Material.STRING,
				1,
				ChatColor.DARK_AQUA + "Mesh",
				Arrays.asList("Man Made", ChatColor.GREEN + "Basic Material"),
				1213);
	}
	
	public ItemStack getIronHammerItem() {
		return plugin.createItem(Material.IRON_AXE,
				1,
				ChatColor.DARK_AQUA + "Iron Hammer",
				Arrays.asList("Knocks any nearby enemies back.", "Man Made", ChatColor.GREEN + "Basic Item"),
				1214);
	}
	
	public ItemStack getFeatheredShoesItem() {
		return plugin.createItem(Material.LEATHER_BOOTS,
				1,
				ChatColor.DARK_AQUA + "Feathered Shoes",
				Arrays.asList("Reduces fall damage.", "Man Made", ChatColor.GREEN + "Basic Item"),
				1215);
	}
	
	//Intermediate
	
	public ItemStack getHardenedPlateItem() {
		return plugin.createItem(Material.IRON_INGOT,
				1,
				ChatColor.DARK_AQUA + "Hardened Plate",
				Arrays.asList("Man Made", ChatColor.YELLOW + "Intermediate Material"),
				1221);
	}
	
	public ItemStack getHardenedMeshItem() {
		return plugin.createItem(Material.STRING,
				1,
				ChatColor.DARK_AQUA + "Hardened Mesh",
				Arrays.asList("Man Made", ChatColor.YELLOW + "Intermediate Material"),
				1222);
	}
	
	public ItemStack getKnockbackShieldItem() {
		return plugin.createItem(Material.SHIELD,
				1,
				ChatColor.DARK_AQUA + "Knockback Shield",
				Arrays.asList("Blocking with this shield will", "send the attacker flying back.", "Man Made", ChatColor.YELLOW + "Intermediate Item"),
				1223);
	}
	
	public ItemStack getIronPlateHelmetItem() {
		return plugin.createItem(Material.IRON_HELMET,
				1,
				ChatColor.DARK_AQUA + "Iron Plate Helmet",
				Arrays.asList("Set Bonus: Drastically slows nearby enemies.", "Man Made", ChatColor.YELLOW + "Intermediate Item"),
				1224);
	}
	
	public ItemStack getIronPlateChestpieceItem() {
		return plugin.createItem(Material.IRON_CHESTPLATE,
				1,
				ChatColor.DARK_AQUA + "Iron Plate Chestpiece",
				Arrays.asList("Set Bonus: Drastically slows nearby enemies.", "Man Made", ChatColor.YELLOW + "Intermediate Item"),
				1225);
	}
	
	public ItemStack getIronPlateLeggingsItem() {
		return plugin.createItem(Material.IRON_LEGGINGS,
				1,
				ChatColor.DARK_AQUA + "Iron Plate Leggings",
				Arrays.asList("Set Bonus: Drastically slows nearby enemies.", "Man Made", ChatColor.YELLOW + "Intermediate Item"),
				1226);
	}
	
	public ItemStack getIronPlateGreavesItem() {
		return plugin.createItem(Material.IRON_BOOTS,
				1,
				ChatColor.DARK_AQUA + "Iron Plate Greaves",
				Arrays.asList("Set Bonus: Drastically slows nearby enemies.", "Man Made", ChatColor.YELLOW + "Intermediate Item"),
				1227);
	}
	
	//Advanced
	
	public ItemStack getSteelPlateItem() {
		return plugin.createItem(Material.IRON_INGOT,
				1,
				ChatColor.DARK_AQUA + "Steel Plate",
				Arrays.asList("Man Made", ChatColor.GOLD + "Advanced Material"),
				1231);
	}
	
	public ItemStack getSteelMeshItem() {
		return plugin.createItem(Material.STRING,
				1,
				ChatColor.DARK_AQUA + "Steel Mesh",
				Arrays.asList("Man Made", ChatColor.GOLD + "Advanced Material"),
				1232);
	}
	
	public ItemStack getEmblemOfTheShieldItem() {
		return plugin.createItem(Material.CLAY_BALL,
				1,
				ChatColor.DARK_AQUA + "Emblem of the Shield",
				Arrays.asList("Gives knockback immunity when carried.", "Man Made", ChatColor.GOLD + "Advanced Item"),
				1233);
	}
	
	public ItemStack getSteelPlateHelmetItem() {
		return plugin.createItem(Material.DIAMOND_HELMET,
				1,
				ChatColor.DARK_AQUA + "Steel Plate Helmet",
				Arrays.asList("Set Bonus: Recieve regeneration and strength", "when standing still.", "Man Made", ChatColor.GOLD + "Advanced Item"),
				1234);
	}
	
	public ItemStack getSteelPlateChestpieceItem() {
		return plugin.createItem(Material.DIAMOND_CHESTPLATE,
				1,
				ChatColor.DARK_AQUA + "Steel Plate Chestpiece",
				Arrays.asList("Set Bonus: Recieve regeneration and strength", "when standing still.", "Man Made", ChatColor.GOLD + "Advanced Item"),
				1235);
	}
	
	public ItemStack getSteelPlateLeggingsItem() {
		return plugin.createItem(Material.DIAMOND_LEGGINGS,
				1,
				ChatColor.DARK_AQUA + "Steel Plate Leggings",
				Arrays.asList("Set Bonus: Recieve regeneration and strength", "when standing still.", "Man Made", ChatColor.GOLD + "Advanced Item"),
				1236);
	}
	
	public ItemStack getSteelPlateGreavesItem() {
		return plugin.createItem(Material.DIAMOND_BOOTS,
				1,
				ChatColor.DARK_AQUA + "Steel Plate Greaves",
				Arrays.asList("Set Bonus: Recieve regeneration and strength", "when standing still.", "Man Made", ChatColor.GOLD + "Advanced Item"),
				1237);
	}
	
	//Expert
	
	public ItemStack getSoulOfTheGuardianItem() {
		return plugin.createItem(Material.HEART_OF_THE_SEA,
				1,
				ChatColor.DARK_AQUA + "Soul of the Guardian",
				Arrays.asList("Doubles your defense when carried.", "Only usable by Steeled Armorers.", "Man Made", ChatColor.RED + "Expert Item"),
				1241);
	}
	
	public ItemStack getSoulboundChestpieceItem() {
		return plugin.createItem(Material.DIAMOND_CHESTPLATE,
				1,
				ChatColor.DARK_AQUA + "Soulbound Chestpiece",
				Arrays.asList("This item will be kept when you die.", "Man Made", ChatColor.RED + "Expert Item"),
				1242);
	}
	
	public ItemStack getVisageOfTheGorgonItem() {
		return plugin.createItem(Material.IRON_HELMET,
				1,
				ChatColor.DARK_AQUA + "Visage of the Gorgon",
				Arrays.asList("Anyone that gazes upon you when", "wearing this will be paralyzed", "Man Made", ChatColor.RED + "Expert Item"),
				1243);
	}
	
	
	
	
	
	//Verdant Shepherd
	
	//Basic
	
	public ItemStack getMerinoWoolItem() {
		return plugin.createItem(Material.WHITE_WOOL,
				2,
				ChatColor.DARK_AQUA + "Merino Wool",
				Arrays.asList("Man Made", ChatColor.GREEN + "Basic Material"),
				1311);
	}
	
	public ItemStack getLanolinItem() {
		return plugin.createItem(Material.ORANGE_DYE,
				2,
				ChatColor.DARK_AQUA + "Lanolin",
				Arrays.asList("Man Made", ChatColor.GREEN + "Basic Material"),
				1312);
	}
	
	public ItemStack getDryGrassItem() {
		return plugin.createItem(Material.GRASS,
				1,
				ChatColor.DARK_AQUA + "Dry Grass",
				Arrays.asList("Man Made", ChatColor.GREEN + "Basic Material"),
				1313);
	}
	
	public ItemStack getVealItem() {
		return plugin.createItem(Material.COOKED_MUTTON,
				1,
				ChatColor.DARK_AQUA + "Veal",
				Arrays.asList("Able to tame all tameable animals.", "Man Made", ChatColor.GREEN + "Basic Item"),
				1314);
	}
	
	public ItemStack getHempItem() {
		return plugin.createItem(Material.FERN,
				1,
				ChatColor.DARK_AQUA + "Hemp",
				Arrays.asList(";)", "Man Made", ChatColor.GREEN + "Basic Item"),
				1315);
	}
	
	//Intermediate
	
	public ItemStack getMerinoClothItem() {
		return plugin.createItem(Material.PAPER,
				1,
				ChatColor.DARK_AQUA + "Merino Cloth",
				Arrays.asList("Man Made", ChatColor.YELLOW + "Intermediate Material"),
				1321);
	}
	
	public ItemStack getWaxItem() {
		return plugin.createItem(Material.YELLOW_DYE,
				2,
				ChatColor.DARK_AQUA + "Wax",
				Arrays.asList("Man Made", ChatColor.YELLOW + "Intermediate Material"),
				1322);
	}
	
	public ItemStack getCrookItem() {
		ItemStack crook = plugin.createItem(Material.WOODEN_HOE,
				1,
				ChatColor.DARK_AQUA + "Crook",
				Arrays.asList("Right click an animal to pick it up.", "Man Made", ChatColor.YELLOW + "Intermediate Item"),
				1323);
		ItemMeta meta = crook.getItemMeta();
		PersistentDataContainer data = meta.getPersistentDataContainer();
		data.set(new NamespacedKey(plugin, "UUID"), PersistentDataType.STRING, UUID.randomUUID().toString());
		crook.setItemMeta(meta);
		return crook;
	}
	
	public ItemStack getShepherdsCompassItem() {
		return plugin.createItem(Material.COMPASS,
				1,
				ChatColor.DARK_AQUA + "Shepherd's Compass",
				Arrays.asList("Right click a block to anchor this compass.", "Displays HUD info when carried.", "Man Made", ChatColor.YELLOW + "Intermediate Item"),
				1324);
	}
	
	public ItemStack getCorruptedStaffItem() {
		return plugin.createItem(Material.STICK,
				1,
				ChatColor.DARK_AQUA + "Corrupted Staff",
				Arrays.asList("Converts grass to mycelium", "and cows to mooshrooms.", "Man Made", ChatColor.YELLOW + "Intermediate Item"),
				1325);
	}
	
	//Advanced
	
	public ItemStack getEssenceOfFaunaItem() {
		return plugin.createItem(Material.GREEN_DYE,
				1,
				ChatColor.DARK_AQUA + "Essence of Fauna",
				Arrays.asList("Man Made", ChatColor.GOLD + "Advanced Material"),
				1331);
	}
	
	public ItemStack getSteelWoolItem() {
		return plugin.createItem(Material.GRAY_WOOL,
				1,
				ChatColor.DARK_AQUA + "Steel Wool",
				Arrays.asList("Man Made", ChatColor.GOLD + "Advanced Material"),
				1332);
	}
	
	public ItemStack getVerdantMedallionItem() {
		return plugin.createItem(Material.GOLDEN_CHESTPLATE,
				1,
				ChatColor.DARK_AQUA + "Verdant Medallion",
				Arrays.asList("Wearing this causes all", "nearby animals to swarm you.", "Man Made", ChatColor.GOLD + "Advanced Item"),
				1333);
	}
	
	public ItemStack getEmblemOfThePastureItem() {
		return plugin.createItem(Material.CLAY_BALL,
				1,
				ChatColor.DARK_AQUA + "Emblem of the Pasture",
				Arrays.asList("Increases your regeneration when carried.", "Man Made", ChatColor.GOLD + "Advanced Item"),
				1334);
	}
	
	public ItemStack getGaiasWrathItem() {
		return plugin.createItem(Material.STICK,
				1,
				ChatColor.DARK_AQUA + "Gaia's Wrath",
				Arrays.asList("Fires an orb that damages", "enemies, and boosts allies.", "Man Made", ChatColor.GOLD + "Advanced Item"),
				1335);
	}
	
	//Expert
	
	public ItemStack getSoulOfTheCaretakerItem() {
		return plugin.createItem(Material.HEART_OF_THE_SEA,
				1,
				ChatColor.DARK_AQUA + "Soul of the Caretaker",
				Arrays.asList("Immune to hunger when carried.", "Only usable by Verdant Shepherds.", "Man Made", ChatColor.RED + "Expert Item"),
				1341);
	}
	
	public ItemStack getSoulboundHoeItem() {
		return plugin.createItem(Material.DIAMOND_HOE,
				1,
				ChatColor.DARK_AQUA + "Soulbound Hoe",
				Arrays.asList("This item will be kept when you die.", "Man Made", ChatColor.RED + "Expert Item"),
				1342);
	}
	
	public ItemStack getVeiledTotemItem() {
		return plugin.createItem(Material.POLISHED_DEEPSLATE_WALL,
				1,
				ChatColor.DARK_AQUA + "Veiled Totem",
				Arrays.asList("Right click an animal to disguise", "yourself as that type of animal.", "Sneak right click to exit.", "Man Made", ChatColor.RED + "Expert Item"),
				1343);
	}
	
	
	
	
	
	//Enchanted Botanist
	
	//Basic
	
	public ItemStack getChaffItem() {
		return plugin.createItem(Material.WHEAT,
				1,
				ChatColor.DARK_GREEN + "Chaff",
				Arrays.asList("Elven Craft", ChatColor.GREEN + "Basic Material"),
				2111);
	}
	
	public ItemStack getAssortedPetalsItem() {
		return plugin.createItem(Material.POPPY,
				3,
				ChatColor.DARK_GREEN + "Assorted Petals",
				Arrays.asList("Elven Craft", ChatColor.GREEN + "Basic Material"),
				2112);
	}
	
	public ItemStack getFloralRootsItem() {
		return plugin.createItem(Material.HANGING_ROOTS,
				2,
				ChatColor.DARK_GREEN + "Floral Roots",
				Arrays.asList("Elven Craft", ChatColor.GREEN + "Basic Material"),
				2113);
	}
	
	public ItemStack getFloralTransmuterItem() {
		return plugin.createItem(Material.BAMBOO,
				1,
				ChatColor.DARK_GREEN + "Floral Transmuter",
				Arrays.asList("Click a flower to change its type.", "Elven Craft", ChatColor.GREEN + "Basic Item"),
				2114);
	}
	
	public ItemStack getCactusGreavesItem() {
		return plugin.createItem(Material.CHAINMAIL_BOOTS,
				1,
				ChatColor.DARK_GREEN + "Cactus Greaves",
				Arrays.asList("Increased speed on sand.", "Elven Craft", ChatColor.GREEN + "Basic Item"),
				2115);
	}
	
	//Intermediate
	
	public ItemStack getFloralPoulticeItem() {
		return plugin.createItem(Material.CYAN_DYE,
				1,
				ChatColor.DARK_GREEN + "Floral Poultice",
				Arrays.asList("Elven Craft", ChatColor.YELLOW + "Intermediate Material"),
				2121);
	}
	
	public ItemStack getEnrichedSoilItem() {
		return plugin.createItem(Material.FARMLAND,
				1,
				ChatColor.DARK_GREEN + "Enriched Soil",
				Arrays.asList("Elven Craft", ChatColor.YELLOW + "Intermediate Material"),
				2122);
	}
	
	public ItemStack getEnrichedHoeItem() {
		return plugin.createItem(Material.IRON_HOE,
				1,
				ChatColor.DARK_GREEN + "Enriched Hoe",
				Arrays.asList("Crops drop experience when", "broken with this item.", "Elven Craft", ChatColor.YELLOW + "Intermediate Item"),
				2123);
	}
	
	public ItemStack getDemetersBenevolenceItem() {
		return plugin.createItem(Material.CHAINMAIL_BOOTS,
				1,
				ChatColor.DARK_GREEN + "Demeter's Benevolence",
				Arrays.asList("Grows grass and flowers as you move.", "Elven Craft", ChatColor.YELLOW + "Intermediate Item"),
				2124);
	}
	
	public ItemStack getPotentHoneyItem() {
		return plugin.createItem(Material.HONEY_BOTTLE,
				1,
				ChatColor.DARK_GREEN + "Potent Honey",
				Arrays.asList("Clears all debuffs.", "Elven Craft", ChatColor.YELLOW + "Intermediate Item"),
				2125);
	}
	
	//Advanced
	
	public ItemStack getAqueousSolutionItem() {
		return plugin.createItem(Material.BLUE_DYE,
				1,
				ChatColor.DARK_GREEN + "Aqueous Solution",
				Arrays.asList("Elven Craft", ChatColor.GOLD + "Advanced Material"),
				2131);
	}
	
	public ItemStack getCorruptedSoilItem() {
		return plugin.createItem(Material.MYCELIUM,
				1,
				ChatColor.DARK_GREEN + "Corrupted Soil",
				Arrays.asList("Elven Craft", ChatColor.GOLD + "Advanced Material"),
				2132);
	}
	
	public ItemStack getEmblemOfTheBlossomItem() {
		return plugin.createItem(Material.CLAY_BALL,
				1,
				ChatColor.DARK_GREEN + "Emblem of the Blossom",
				Arrays.asList("Nearby crops will grow", "faster when carried.", "Elven Craft", ChatColor.GOLD + "Advanced Item"),
				2133);
	}
	
	public ItemStack getScytheItem() {
		return plugin.createItem(Material.IRON_HOE,
				1,
				ChatColor.DARK_GREEN + "Scythe",
				Arrays.asList("Crop drops broken with this", "item will be doubled.", "Elven Craft", ChatColor.GOLD + "Advanced Item"),
				2134);
	}
	
	public ItemStack getGoldenFlowerItem() {
		return plugin.createItem(Material.SUNFLOWER,
				1,
				ChatColor.DARK_GREEN + "Golden Flower",
				Arrays.asList("Summons wasps that will come to your aid.", "Elven Craft", ChatColor.GOLD + "Advanced Item"),
				2135);
	}
	
	//Expert
	
	public ItemStack getSoulOfTheFloristItem() {
		return plugin.createItem(Material.HEART_OF_THE_SEA,
				1,
				ChatColor.DARK_GREEN + "Soul of the Florist",
				Arrays.asList("Inflicts nausea and a weak poison on all", "nearby enemies when carried.", "Only usable by Enchanted Botanists", "Elven Craft", ChatColor.RED + "Expert Item"),
				2141);
	}
	
	public ItemStack getSoulboundShovelItem() {
		return plugin.createItem(Material.DIAMOND_SHOVEL,
				1,
				ChatColor.DARK_GREEN + "Soulbound Shovel",
				Arrays.asList("This item will be kept when you die.", "Man Made", ChatColor.RED + "Expert Item"),
				2142);
	}
	
	public ItemStack getStaffOfPersephoneItem() {
		return plugin.createItem(Material.STICK,
				1,
				ChatColor.DARK_GREEN + "Staff of Persephone",
				Arrays.asList("Summons nature's fallen warriors to come to your aid.", "Man Made", ChatColor.RED + "Expert Item"),
				2143);
	}
	
	
	
	
	
	//Woodland Craftsman
	
	//Basic
	
	public ItemStack getElvenThreadItem() {
		return plugin.createItem(Material.STRING,
				1,
				ChatColor.DARK_GREEN + "Elven Thread",
				Arrays.asList("Elven Craft", ChatColor.GREEN + "Basic Material"),
				2211);
	}
	
	public ItemStack getYewBranchesItem() {
		return plugin.createItem(Material.STICK,
				2,
				ChatColor.DARK_GREEN + "Yew Branches",
				Arrays.asList("Elven Craft", ChatColor.GREEN + "Basic Material"),
				2212);
	}
	
	public ItemStack getEnrichedLogsItem() {
		return plugin.createItem(Material.OAK_LOG,
				3,
				ChatColor.DARK_GREEN + "Enriched Logs",
				Arrays.asList("Elven Craft", ChatColor.GREEN + "Basic Material"),
				2213);
	}
	
	public ItemStack getSaplingTransmuterItem() {
		return plugin.createItem(Material.STICK,
				1,
				ChatColor.DARK_GREEN + "Sapling Transmuter",
				Arrays.asList("Click a sapling to change its type.", "Elven Craft", ChatColor.GREEN + "Basic Item"),
				2214);
	}
	
	public ItemStack getSawItem() {
		return plugin.createItem(Material.IRON_AXE,
				1,
				ChatColor.DARK_GREEN + "Saw",
				Arrays.asList("Cuts down the whole tree.", "Elven Craft", ChatColor.GREEN + "Basic Item"),
				2215);
	}
	
	//Intermediate
	
	public ItemStack getNailItem() {
		return plugin.createItem(Material.IRON_NUGGET,
				1,
				ChatColor.DARK_GREEN + "Nail",
				Arrays.asList("Elven Craft", ChatColor.YELLOW + "Intermediate Material"),
				2221);
	}
	
	public ItemStack getElvenWeaveItem() {
		return plugin.createItem(Material.LIME_WOOL,
				1,
				ChatColor.DARK_GREEN + "Elven Weave",
				Arrays.asList("Elven Craft", ChatColor.YELLOW + "Intermediate Material"),
				2222);
	}
	
	public ItemStack getToolbeltItem() {
		return plugin.createItem(Material.CHAINMAIL_LEGGINGS,
				1,
				ChatColor.DARK_GREEN + "Toolbelt",
				Arrays.asList("Expands your offhand slot.", "Press F to switch tool slot.", "Elven Craft", ChatColor.YELLOW + "Intermediate Item"),
				2223);
	}
	
	public ItemStack getRustedStaffItem() {
		return plugin.createItem(Material.LIGHTNING_ROD,
				1,
				ChatColor.DARK_GREEN + "Rusted Staff",
				Arrays.asList("Right click copper to oxidize it.", "Left click copper to polish it.", "Elven Craft", ChatColor.YELLOW + "Intermediate Item"),
				2224);
	}
	
	public ItemStack getEnrichedWoodHelmetItem() {
		return plugin.createItem(Material.GOLDEN_HELMET,
				1,
				ChatColor.DARK_GREEN + "Enriched Wood Helmet",
				Arrays.asList("Set Bonus: Increases how much", "experience you pick up.", "Elven Craft", ChatColor.YELLOW + "Intermediate Item"),
				2225);
	}
	
	public ItemStack getEnrichedWoodChestpieceItem() {
		return plugin.createItem(Material.GOLDEN_CHESTPLATE,
				1,
				ChatColor.DARK_GREEN + "Enriched Wood Chestpiece",
				Arrays.asList("Set Bonus: Increases how much", "experience you pick up.", "Elven Craft", ChatColor.YELLOW + "Intermediate Item"),
				2226);
	}
	
	public ItemStack getEnrichedWoodLeggingsItem() {
		return plugin.createItem(Material.GOLDEN_LEGGINGS,
				1,
				ChatColor.DARK_GREEN + "Enriched Wood Leggings",
				Arrays.asList("Set Bonus: Increases how much", "experience you pick up.", "Elven Craft", ChatColor.YELLOW + "Intermediate Item"),
				2227);
	}
	
	public ItemStack getEnrichedWoodGreavesItem() {
		return plugin.createItem(Material.GOLDEN_BOOTS,
				1,
				ChatColor.DARK_GREEN + "Enriched Wood Greaves",
				Arrays.asList("Set Bonus: Increases how much", "experience you pick up.", "Elven Craft", ChatColor.YELLOW + "Intermediate Item"),
				2228);
	}
	
	//Advanced
	
	public ItemStack getElvenClothItem() {
		return plugin.createItem(Material.PAPER,
				1,
				ChatColor.DARK_GREEN + "Elven Cloth",
				Arrays.asList("Elven Craft", ChatColor.GOLD + "Advanced Material"),
				2231);
	}
	
	public ItemStack getParchmentItem() {
		return plugin.createItem(Material.PAPER,
				1,
				ChatColor.DARK_GREEN + "Parchment",
				Arrays.asList("Elven Craft", ChatColor.GOLD + "Advanced Material"),
				2232);
	}
	
	public ItemStack getEmblemOfTheForestItem() {
		return plugin.createItem(Material.CLAY_BALL,
				1,
				ChatColor.DARK_GREEN + "Emblem of the Forest",
				Arrays.asList("Nearby trees will grow faster when carried.", "Elven Craft", ChatColor.GOLD + "Advanced Item"),
				2233);
	}
	
	public ItemStack getElvenHoodItem() {
		return plugin.createItem(Material.CHAINMAIL_HELMET,
				1,
				ChatColor.DARK_GREEN + "Elven Hood",
				Arrays.asList("Set Bonus: Grants speed and makes you silent.", "Elven Craft", ChatColor.GOLD + "Advanced Item"),
				2234);
	}
	
	public ItemStack getElvenCloakItem() {
		return plugin.createItem(Material.CHAINMAIL_CHESTPLATE,
				1,
				ChatColor.DARK_GREEN + "Elven Cloak",
				Arrays.asList("Set Bonus: Grants speed and makes you silent.", "Elven Craft", ChatColor.GOLD + "Advanced Item"),
				2235);
	}
	
	public ItemStack getElvenLeggingsItem() {
		return plugin.createItem(Material.CHAINMAIL_LEGGINGS,
				1,
				ChatColor.DARK_GREEN + "Elven Leggings",
				Arrays.asList("Set Bonus: Grants speed and makes you silent.", "Elven Craft", ChatColor.GOLD + "Advanced Item"),
				2236);
	}
	
	public ItemStack getElvenGreavesItem() {
		return plugin.createItem(Material.CHAINMAIL_BOOTS,
				1,
				ChatColor.DARK_GREEN + "Elven Greaves",
				Arrays.asList("Set Bonus: Grants speed and makes you silent.", "Elven Craft", ChatColor.GOLD + "Advanced Item"),
				2237);
	}
	
	//Expert
	
	public ItemStack getSoulOfTheWoodsmanItem() {
		return plugin.createItem(Material.HEART_OF_THE_SEA,
				1,
				ChatColor.DARK_GREEN + "Soul of the Woodsman",
				Arrays.asList("Grants strength when carried", "Only usable by Woodland Craftsmen", "Elven Craft", ChatColor.RED + "Expert Item"),
				2241);
	}
	
	public ItemStack getSoulboundLeggingsItem() {
		return plugin.createItem(Material.DIAMOND_LEGGINGS,
				1,
				ChatColor.DARK_GREEN + "Soulbound Leggings",
				Arrays.asList("This item will be kept when you die.", "Elven Craft", ChatColor.RED + "Expert Item"),
				2242);
	}
	
	public ItemStack getWoodslingItem() {
		return plugin.createItem(Material.DEAD_BUSH,
				1,
				ChatColor.DARK_GREEN + "Woodsling",
				Arrays.asList("Right click to launch a block of wood.", "Uses wood as ammunition.", "Elven Craft", ChatColor.RED + "Expert Item"),
				2243);
	}
	
	
	
	
	
	//Lunar Artificer
	
	//Basic
	
	public ItemStack getBottledStarlightItem() {
		ItemStack item = plugin.createItem(
				Material.POTION,
				1,
				ChatColor.DARK_GREEN + "Bottled Starlight",
				Arrays.asList("Obtained in starlight transfusion", "Elven Craft", ChatColor.GREEN + "Basic Material"),
				2311);
		PotionMeta meta = (PotionMeta)item.getItemMeta();
		meta.setColor(Color.WHITE);
		meta.addCustomEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 400, 0), true);
		item.setItemMeta(meta);
		return item;
	}
	
	public ItemStack getLunarDebrisItem() {
		return plugin.createItem(Material.COBBLED_DEEPSLATE,
				2,
				ChatColor.DARK_GREEN + "Lunar Debris",
				Arrays.asList("Elven Craft", ChatColor.GREEN + "Basic Material"),
				2312);
	}
	
	public ItemStack getStardustItem() {
		return plugin.createItem(Material.GLOWSTONE_DUST,
				1,
				ChatColor.DARK_GREEN + "Stardust",
				Arrays.asList("Elven Craft", ChatColor.GREEN + "Basic Material"),
				2313);
	}
	
	public ItemStack getIlluminaOrbItem() {
		return plugin.createItem(Material.LIGHT,
				1,
				ChatColor.DARK_GREEN + "Illumina Orb",
				Arrays.asList("Can be placed.", "Elven Craft", ChatColor.GREEN + "Basic Item"),
				2314);
	}
	
	public ItemStack getNebulousAuraItem() {
		return plugin.createItem(Material.ORANGE_DYE,
				1,
				ChatColor.DARK_GREEN + "Nebulous Aura",
				Arrays.asList("Immunity to blindness.", "Elven Craft", ChatColor.GREEN + "Basic Item"),
				2315);
	}
	
	//Intermediate
	
	public ItemStack getMeteoriteItem() {
		return plugin.createItem(Material.OBSIDIAN,
				2,
				ChatColor.DARK_GREEN + "Meteorite",
				Arrays.asList("Elven Craft", ChatColor.YELLOW + "Intermediate Material"),
				2321);
	}
	
	public ItemStack getDarkMatterItem() {
		return plugin.createItem(Material.DRIED_KELP,
				2,
				ChatColor.DARK_GREEN + "Dark Matter",
				Arrays.asList("Elven Craft", ChatColor.YELLOW + "Intermediate Material"),
				2322);
	}
	
	public ItemStack getLightShieldItem() {
		return plugin.createItem(Material.SHIELD,
				1,
				ChatColor.DARK_GREEN + "Light Shield",
				Arrays.asList("Vaporizes projectiles that pass through it.", "Elven Craft", ChatColor.YELLOW + "Intermediate Item"),
				2323);
	}
	
	public ItemStack getLightBowItem() {
		return plugin.createItem(Material.BOW,
				1,
				ChatColor.DARK_GREEN + "Light Bow",
				Arrays.asList("Does not require arrows. Inflicts glow.", "Elven Craft", ChatColor.YELLOW + "Intermediate Item"),
				2324);
	}
	
	public ItemStack getVoidStoneItem() {
		return plugin.createItem(Material.ENDERMITE_SPAWN_EGG,
				1,
				ChatColor.DARK_GREEN + "Void Stone",
				Arrays.asList("Inflicts blindness on all", "nearby enemies when carried.", "Elven Craft", ChatColor.YELLOW + "Intermediate Item"),
				2325);
	}
	
	//Advanced
	
	public ItemStack getBloodMoonFragmentItem() {
		return plugin.createItem(Material.NETHER_BRICK,
				1,
				ChatColor.DARK_GREEN + "Blood Moon Fragment",
				Arrays.asList("Elven Craft", ChatColor.GOLD + "Advanced Material"),
				2331);
	}
	
	public ItemStack getNewMoonFragmentItem() {
		return plugin.createItem(Material.PRISMARINE_SHARD,
				1,
				ChatColor.DARK_GREEN + "New Moon Fragment",
				Arrays.asList("Elven Craft", ChatColor.GOLD + "Advanced Material"),
				2332);
	}
	
	public ItemStack getLunarBoomerangItem() {
		return plugin.createItem(Material.LEAD,
				1,
				ChatColor.DARK_GREEN + "Lunar Boomerang",
				Arrays.asList("Enemies hit by this will" + "be propelled towards you.", "Elven Craft", ChatColor.GOLD + "Advanced Item"),
				2333);
	}
	
	public ItemStack getEmblemOfTheMoonItem() {
		return plugin.createItem(Material.CLAY_BALL,
				1,
				ChatColor.DARK_GREEN + "Emblem of the Moon",
				Arrays.asList("Gives all nearby entities glowing when carried.", "Elven Craft", ChatColor.GOLD + "Advanced Item"),
				2334);
	}
	
	public ItemStack getStaffOfBalanceItem() {
		return plugin.createItem(Material.STICK,
				1,
				ChatColor.DARK_GREEN + "Staff of Balance",
				Arrays.asList("Shoots a bolt that will judge the player it hits.", "Elven Craft", ChatColor.GOLD + "Advanced Item"),
				2335);
	}
	
	//Expert
	
	public ItemStack getSoulOfTheAstrologerItem() {
		return plugin.createItem(Material.HEART_OF_THE_SEA,
				1,
				ChatColor.DARK_GREEN + "Soul of the Astrologer",
				Arrays.asList("Grants permanent night vision,", "and all entities appear glowing to you.", "Only usable by Lunar Artificers", "Elven Craft", ChatColor.RED + "Expert Item"),
				2341);
	}
	
	public ItemStack getAstralWrenchItem() {
		return plugin.createItem(Material.STICK,
				1,
				ChatColor.DARK_GREEN + "Astral Wrench",
				Arrays.asList("Used to link astral teleporter pads.", "Shift right click for more info.", "Elven Craft", ChatColor.RED + "Expert Item"),
				2342);
	}
	
	public ItemStack getSoulboundElytraItem() {
		return plugin.createItem(Material.ELYTRA,
				1,
				ChatColor.DARK_GREEN + "Soulbound Elytra",
				Arrays.asList("This item will be kept when you die.", "Elven Craft", ChatColor.RED + "Expert Item"),
				2343);
	}
	
	
	
	
	
	//Radiant Metallurgist
	
	//Basic
	
	public ItemStack getSteelItem() {
		return plugin.createItem(Material.IRON_INGOT,
				2,
				ChatColor.DARK_PURPLE + "Steel",
				Arrays.asList("Dwarven Forged", ChatColor.GREEN + "Basic Material"),
				3111);
	}
	
	public ItemStack getRhyoliteItem() {
		return plugin.createItem(Material.SANDSTONE,
				3,
				ChatColor.DARK_PURPLE + "Rhyolite",
				Arrays.asList("Dwarven Forged", ChatColor.GREEN + "Basic Material"),
				3112);
	}
	
	public ItemStack getPumiceItem() {
		return plugin.createItem(Material.TUFF,
				3,
				ChatColor.DARK_PURPLE + "Pumice",
				Arrays.asList("Dwarven Forged", ChatColor.GREEN + "Basic Material"),
				3113);
	}
	
	public ItemStack getForgersScrollItem() {
		return plugin.createItem(Material.PAPER,
				1,
				ChatColor.DARK_PURPLE + "Forger's Scroll",
				Arrays.asList("Anvils cost half as much", "experience to use when carried.", "Dwarven Forged", ChatColor.GREEN + "Basic Item"),
				3114);
	}
	
	public ItemStack getFerrousHarvesterItem() {
		return plugin.createItem(Material.STONE_AXE,
				1,
				ChatColor.DARK_PURPLE + "Ferrous Harvester",
				Arrays.asList("Mobs killed with this will drop iron", "nuggets instead of their normal drops.", "Dwarven Forged", ChatColor.GREEN + "Basic Item"),
				3115);
	}
	
	//Intermediate
	
	public ItemStack getBrassItem() {
		return plugin.createItem(Material.COPPER_INGOT,
				1,
				ChatColor.DARK_PURPLE + "Brass",
				Arrays.asList("Dwarven Forged", ChatColor.YELLOW + "Intermediate Material"),
				3121);
	}
	
	public ItemStack getBronzeItem() {
		return plugin.createItem(Material.COPPER_INGOT,
				1,
				ChatColor.DARK_PURPLE + "Bronze",
				Arrays.asList("Dwarven Forged", ChatColor.YELLOW + "Intermediate Material"),
				3122);
	}
	
	public ItemStack getRadiantBoreItem() {
		return plugin.createItem(Material.SHEARS,
				1,
				ChatColor.DARK_PURPLE + "Radiant Bore",
				Arrays.asList("Tunnels through the earth if", "there is a cave close enough.", "Dwarven Forged", ChatColor.YELLOW + "Intermediate Item"),
				3123);
	}
	
	public ItemStack getFlamelashItem() {
		return plugin.createItem(Material.BLAZE_ROD,
				1,
				ChatColor.DARK_PURPLE + "Flamelash",
				Arrays.asList("Cracks open forward in the earth", "releasing flames as it goes", "setting enemies on fire.", "Dwarven Forged", ChatColor.YELLOW + "Intermediate Item"),
				3124);
	}
	
	public ItemStack getHandForgeItem() {
		return plugin.createItem(Material.FURNACE,
				1,
				ChatColor.DARK_PURPLE + "Hand Forge",
				Arrays.asList("Smelts ores as you", "mine them when carried.", "Consumes coal as fuel.", "Dwarven Forged", ChatColor.YELLOW + "Intermediate Item"),
				3125);
	}
	
	//Advanced
	
	public ItemStack getTitaniumItem() {
		return plugin.createItem(Material.IRON_INGOT,
				1,
				ChatColor.DARK_PURPLE + "Titanium",
				Arrays.asList("Dwarven Forged", ChatColor.GOLD + "Advanced Material"),
				3131);
	}
	
	public ItemStack getEssenceOfFireItem() {
		return plugin.createItem(Material.BLAZE_POWDER,
				1,
				ChatColor.DARK_PURPLE + "Essence of Fire",
				Arrays.asList("Dwarven Forged", ChatColor.GOLD + "Advanced Material"),
				3132);
	}
	
	public ItemStack getEmblemOfTheForgeItem() {
		return plugin.createItem(Material.CLAY_BALL,
				1,
				ChatColor.DARK_PURPLE + "Emblem of the Forge",
				Arrays.asList("Doubles furnace output when carried.", "Dwarven Forged", ChatColor.GOLD + "Advanced Item"),
				3133);
	}
	
	public ItemStack getQuakeStaffItem() {
		return plugin.createItem(Material.STICK,
				1,
				ChatColor.DARK_PURPLE + "Quake Staff",
				Arrays.asList("Launches enemies into the air. May bury enemies.", "Dwarven Forged", ChatColor.GOLD + "Advanced Item"),
				3134);
	}
	
	public ItemStack getDwarvenAxeItem() {
		return plugin.createItem(Material.DIAMOND_AXE,
				1,
				ChatColor.DARK_PURPLE + "Dwarven Axe",
				Arrays.asList("Slows enemies on hit while giving you a burst of speed.", "Enemies at low health take double damage.", "Dwarven Forged", ChatColor.GOLD + "Advanced Item"),
				3135);
	}
	
	//Expert
	
	public ItemStack getSoulOfTheBlacksmithItem() {
		return plugin.createItem(Material.HEART_OF_THE_SEA,
				1,
				ChatColor.DARK_PURPLE + "Soul of the Blacksmith",
				Arrays.asList("Permanent fire resistance when carried.", "Only usable by Radiant Metallurgists.", "Dwarven Forged", ChatColor.RED + "Expert Item"),
				3141);
	}
	
	public ItemStack getTotemOfTheMoleItem() {
		return plugin.createItem(Material.RABBIT_HIDE,
				1,
				ChatColor.DARK_PURPLE + "Totem of the Mole",
				Arrays.asList("Travel through the surface of the earth.", "Right click to cancel, damaging enemies.", "Dwarven Forged", ChatColor.RED + "Expert Item"),
				3142);
	}
	
	public ItemStack getRadiantAxeItem() {
		return plugin.createItem(Material.DIAMOND_AXE,
				1,
				ChatColor.DARK_PURPLE + "Radiant Axe",
				Arrays.asList("Launches radioactive pieces of earth that", "will hit any other nearby enemies,", "damaging and poisoning them.", "Dwarven Forged", ChatColor.RED + "Expert Item"),
				3143);
	}
	
	
	
	
	
	//Arcane Jeweler
	
	//Basic
	
	public ItemStack getRubyItem() {
		return plugin.createItem(Material.DIAMOND,
				3,
				ChatColor.DARK_PURPLE + "Ruby",
				Arrays.asList("Dwarven Forged", ChatColor.GREEN + "Basic Material"),
				3211);
	}
	
	public ItemStack getSapphireItem() {
		return plugin.createItem(Material.DIAMOND,
				3,
				ChatColor.DARK_PURPLE + "Sapphire",
				Arrays.asList("Dwarven Forged", ChatColor.GREEN + "Basic Material"),
				3212);
	}
	
	public ItemStack getRoyalAzelItem() {
		return plugin.createItem(Material.DIAMOND,
				3,
				ChatColor.DARK_PURPLE + "Royal Azel",
				Arrays.asList("Dwarven Forged", ChatColor.GREEN + "Basic Material"),
				3213);
	}
	
	public ItemStack getOpalItem() {
		return plugin.createItem(Material.DIAMOND,
				3,
				ChatColor.DARK_PURPLE + "Opal",
				Arrays.asList("Dwarven Forged", ChatColor.GREEN + "Basic Material"),
				3214);
	}
	
	public ItemStack getReinforcedRingItem() {
		return plugin.createItem(Material.COMPASS,
				1,
				ChatColor.DARK_PURPLE + "Reinforced Ring",
				Arrays.asList("Slightly increases armor durability when carried.", "Dwarven Forged", ChatColor.GREEN + "Basic Item"),
				3215);
	}
	
	public ItemStack getGoldRingItem() {
		return plugin.createItem(Material.CLOCK,
				1,
				ChatColor.DARK_PURPLE + "Gold Ring",
				Arrays.asList("Mining gold ore gives half a", "heart of absorption when carried.", "Dwarven Forged", ChatColor.GREEN + "Basic Item"),
				3216);
	}
	
	//Intermediate
	
	public ItemStack getTopazItem() {
		return plugin.createItem(Material.DIAMOND,
				2,
				ChatColor.DARK_PURPLE + "Topaz",
				Arrays.asList("Dwarven Forged", ChatColor.YELLOW + "Intermediate Material"),
				3221);
	}
	
	public ItemStack getTurquoiseItem() {
		return plugin.createItem(Material.DIAMOND,
				2,
				ChatColor.DARK_PURPLE + "Turquoise",
				Arrays.asList("Dwarven Forged", ChatColor.YELLOW + "Intermediate Material"),
				3222);
	}
	
	public ItemStack getStuddedHelmetItem() {
		return plugin.createItem(Material.GOLDEN_HELMET,
				1,
				ChatColor.DARK_PURPLE + "Studded Helmet",
				Arrays.asList("Set Bonus: Because the gems on this armor", "shine so brightly, mobs cannot aggro you,", "and some enemies may catch fire.", "Dwarven Forged", ChatColor.YELLOW + "Intermediate Item"),
				3223);
	}
	
	public ItemStack getStuddedChestpieceItem() {
		return plugin.createItem(Material.GOLDEN_CHESTPLATE,
				1,
				ChatColor.DARK_PURPLE + "Studded Chestpiece",
				Arrays.asList("Set Bonus: Because the gems on this armor", "shine so brightly, mobs cannot aggro you,", "and some enemies may catch fire.", "Dwarven Forged", ChatColor.YELLOW + "Intermediate Item"),
				3224);
	}
	
	public ItemStack getStuddedLeggingsItem() {
		return plugin.createItem(Material.GOLDEN_LEGGINGS,
				1,
				ChatColor.DARK_PURPLE + "Studded Leggings",
				Arrays.asList("Set Bonus: Because the gems on this armor", "shine so brightly, mobs cannot aggro you,", "and some enemies may catch fire.", "Dwarven Forged", ChatColor.YELLOW + "Intermediate Item"),
				3225);
	}
	
	public ItemStack getStuddedGreavesItem() {
		return plugin.createItem(Material.GOLDEN_BOOTS,
				1,
				ChatColor.DARK_PURPLE + "Studded Greaves",
				Arrays.asList("Set Bonus: Because the gems on this armor", "shine so brightly, mobs cannot aggro you,", "and some enemies may catch fire.", "Dwarven Forged", ChatColor.YELLOW + "Intermediate Item"),
				3226);
	}
	
	public ItemStack getBejeweledCompassItem() {
		return plugin.createItem(Material.COMPASS,
				1,
				ChatColor.DARK_PURPLE + "Bejeweled Compass",
				Arrays.asList("Right click to point in the", "direction of any nearby treasure.", ChatColor.YELLOW + "Swap hands with a diamond block", ChatColor.YELLOW + "to refine the purity", ChatColor.YELLOW + "of the treasure it points to.", "Purity Level: " + ChatColor.GREEN + "1", "Dwarven Forged", ChatColor.YELLOW + "Intermediate Item"),
				3227);
	}
	
	//Advanced
	
	public ItemStack getGoldDustItem() {
		return plugin.createItem(Material.GLOWSTONE_DUST,
				1,
				ChatColor.DARK_PURPLE + "Gold Dust",
				Arrays.asList("Dwarven Forged", ChatColor.GOLD + "Advanced Material"),
				3231);
	}
	
	public ItemStack getGemClusterItem() {
		return plugin.createItem(Material.AMETHYST_CLUSTER,
				1,
				ChatColor.DARK_PURPLE + "Gem Cluster",
				Arrays.asList("Dwarven Forged", ChatColor.GOLD + "Advanced Material"),
				3232);
	}
	
	public ItemStack getEmblemOfTheWhirlwindItem() {
		return plugin.createItem(Material.CLAY_BALL,
				1,
				ChatColor.DARK_PURPLE + "Emblem of the Whirlwind",
				Arrays.asList("Creates an updraft where you're standing.", "Allies will recieve feather falling.", "Enemies will fall when they reach the top.", "Dwarven Forged", ChatColor.GOLD + "Advanced Item"),
				3233);
	}
	
	public ItemStack getDwarvenHelmetItem() {
		return plugin.createItem(Material.DIAMOND_HELMET,
				1,
				ChatColor.DARK_PURPLE + "Dwarven Helmet",
				Arrays.asList("Set Bonus: Deal double damage to", "enemies below 50% health,", "but receive slowness 1.", "Dwarven Forged", ChatColor.GOLD + "Advanced Item"),
				3234);
	}
	
	public ItemStack getDwarvenChestpieceItem() {
		return plugin.createItem(Material.DIAMOND_CHESTPLATE,
				1,
				ChatColor.DARK_PURPLE + "Dwarven Chestpiece",
				Arrays.asList("Set Bonus: Deal double damage to", "enemies below 50% health,", "but receive slowness 1.", "Dwarven Forged", ChatColor.GOLD + "Advanced Item"),
				3235);
	}
	
	public ItemStack getDwarvenLeggingsItem() {
		return plugin.createItem(Material.DIAMOND_LEGGINGS,
				1,
				ChatColor.DARK_PURPLE + "Dwarven Leggings",
				Arrays.asList("Set Bonus: Deal double damage to", "enemies below 50% health,", "but receive slowness 1.", "Dwarven Forged", ChatColor.GOLD + "Advanced Item"),
				3236);
	}
	
	public ItemStack getDwarvenGreavesItem() {
		return plugin.createItem(Material.DIAMOND_BOOTS,
				1,
				ChatColor.DARK_PURPLE + "Dwarven Greaves",
				Arrays.asList("Set Bonus: Deal double damage to", "enemies below 50% health,", "but receive slowness 1.", "Dwarven Forged", ChatColor.GOLD + "Advanced Item"),
				3237);
	}
	
	//Expert
	
	public ItemStack getSoulOfTheEngraverItem() {
		return plugin.createItem(Material.HEART_OF_THE_SEA,
				1,
				ChatColor.DARK_PURPLE + "Soul of the Engraver",
				Arrays.asList("Grants fall damage immunity when carried.", "Only usable by Arcane Jewelers.", "Dwarven Forged", ChatColor.RED + "Expert Item"),
				3241);
	}
	
	public ItemStack getSoulboundCrownItem() {
		return plugin.createItem(Material.DIAMOND_HELMET,
				1,
				ChatColor.DARK_PURPLE + "Soulbound Crown",
				Arrays.asList("This item will be kept when you die.", "Dwarven Forged", ChatColor.RED + "Expert Item"),
				3242);
	}
	
	public ItemStack getCompressiveWandItem() {
		return plugin.createItem(Material.AMETHYST_SHARD,
				1,
				ChatColor.DARK_PURPLE + "Compressive Wand",
				Arrays.asList("Right click coal ore to", "slowly turn it into diamond ore.", "Dwarven Forged", ChatColor.RED + "Expert Item"),
				3243);
	}
	
	
	
	
	
	//Gilded Miner
	
	//Basic
	
	public ItemStack getHardenedStoneItem() {
		return plugin.createItem(Material.STONE,
				4,
				ChatColor.DARK_PURPLE + "Hardened Stone",
				Arrays.asList("Dwarven Forged", ChatColor.GREEN + "Basic Material"),
				3311);
	}
	
	public ItemStack getAluminumItem() {
		return plugin.createItem(Material.IRON_INGOT,
				3,
				ChatColor.DARK_PURPLE + "Aluminum",
				Arrays.asList("Dwarven Forged", ChatColor.GREEN + "Basic Material"),
				3312);
	}
	
	public ItemStack getZincItem() {
		return plugin.createItem(Material.IRON_INGOT,
				3,
				ChatColor.DARK_PURPLE + "Zinc",
				Arrays.asList("Dwarven Forged", ChatColor.GREEN + "Basic Material"),
				3313);
	}
	
	public ItemStack getEnduranceRuneItem() {
		return plugin.createItem(Material.PAPER,
				1,
				ChatColor.DARK_PURPLE + "Endurance Rune",
				Arrays.asList("Reduces the effect sprinting has on hunger.", "Dwarven Forged", ChatColor.GREEN + "Basic Item"),
				3314);
	}
	
	public ItemStack getRockyTransmuterItem() {
		return plugin.createItem(Material.AMETHYST_SHARD,
				1,
				ChatColor.DARK_PURPLE + "Rocky Transmuter",
				Arrays.asList("Click stone to cycle its variant", "Dwarven Forged", ChatColor.GREEN + "Basic Item"),
				3315);
	}
	
	//Intermediate
	
	public ItemStack getBlankEmblemItem() {
		return plugin.createItem(Material.CLAY_BALL,
				4,
				ChatColor.DARK_PURPLE + "Blank Emblem",
				Arrays.asList("Dwarven Forged", ChatColor.YELLOW + "Intermediate Material"),
				3321);
	}
	
	public ItemStack getLeadItem() {
		return plugin.createItem(Material.IRON_INGOT,
				1,
				ChatColor.DARK_PURPLE + "Lead",
				Arrays.asList("Dwarven Forged", ChatColor.YELLOW + "Intermediate Material"),
				3322);
	}
	
	public ItemStack getDrillItem() {
		return plugin.createItem(Material.AMETHYST_SHARD,
				1,
				ChatColor.DARK_PURPLE + "Drill",
				Arrays.asList("Smelts non-ores as you", "mine them when carried.", "Consumes coal as fuel.", "Dwarven Forged", ChatColor.YELLOW + "Intermediate Item"),
				3323);
	}
	
	public ItemStack getProtectorStoneItem() {
		return plugin.createItem(Material.TURTLE_SPAWN_EGG,
				1,
				ChatColor.DARK_PURPLE + "Protector Stone",
				Arrays.asList("Adds one second of resistance for", "every block that you mine when carried.", "Dwarven Forged", ChatColor.YELLOW + "Intermediate Item"),
				3324);
	}
	
	public ItemStack getRobustRuneItem() {
		return plugin.createItem(Material.PAPER,
				1,
				ChatColor.DARK_PURPLE + "Robust Rune",
				Arrays.asList("Lowers the chance for tools to", "lose durability when carried.", "Dwarven Forged", ChatColor.YELLOW + "Intermediate Item"),
				3325);
	}
	
	//Advanced
	
	public ItemStack getEssenceOfEarthItem() {
		return plugin.createItem(Material.BROWN_DYE,
				1,
				ChatColor.DARK_PURPLE + "Essence of Earth",
				Arrays.asList("Dwarven Forged", ChatColor.GOLD + "Advanced Material"),
				3331);
	}
	
	public ItemStack getPlatinumItem() {
		return plugin.createItem(Material.IRON_INGOT,
				1,
				ChatColor.DARK_PURPLE + "Platinum",
				Arrays.asList("Dwarven Forged", ChatColor.GOLD + "Advanced Material"),
				3332);
	}
	
	public ItemStack getMagnetizedIdolItem() {
		return plugin.createItem(Material.POLISHED_BLACKSTONE_WALL,
				1,
				ChatColor.DARK_PURPLE + "Magnetized Idol",
				Arrays.asList("One of your active class skills are", "bestowed upon all nearby allies.", "Dwarven Forged", ChatColor.GOLD + "Advanced Item"),
				3333);
	}
	
	public ItemStack getEmblemOfTheEarthItem() {
		return plugin.createItem(Material.CLAY_BALL,
				1,
				ChatColor.DARK_PURPLE + "Emblem of the Earth",
				Arrays.asList("Grants immunity to mining fatigue when carried.", "Dwarven Forged", ChatColor.GOLD + "Advanced Item"),
				3334);
	}
	
	public ItemStack getGrapplingHookItem() {
		return plugin.createItem(Material.TRIPWIRE_HOOK,
				1,
				ChatColor.DARK_PURPLE + "Grappling Hook",
				Arrays.asList("Left click to throw the hook", "Right click to reel it in", "Sneak to exit", "Dwarven Forged", ChatColor.GOLD + "Advanced Item"),
				3335);
	}
	
	//Expert
	
	public ItemStack getSoulOfTheProspectorItem() {
		return plugin.createItem(Material.HEART_OF_THE_SEA,
				1,
				ChatColor.DARK_PURPLE + "Soul of the Prospector",
				Arrays.asList("Increases your mining plane to 3 by 3 when carried.", "Only usable by Gilded Miners.", "Dwarven Forged", ChatColor.RED + "Expert Item"),
				3341);
	}
	
	public ItemStack getSoulboundPickaxeItem() {
		return plugin.createItem(Material.DIAMOND_PICKAXE,
				1,
				ChatColor.DARK_PURPLE + "Soulbound Pickaxe",
				Arrays.asList("This item will be kept when you die.", "Dwarven Forged", ChatColor.RED + "Expert Item"),
				3342);
	}
	
	public ItemStack getMultitoolItem() {
		return plugin.createItem(Material.DIAMOND_PICKAXE,
				1,
				ChatColor.DARK_PURPLE + "Multitool",
				Arrays.asList("Mines every block. Enchantable with every", "applicable enchantment to all tools.", "Dwarven Forged", ChatColor.RED + "Expert Item"),
				3343);
	}
	
	
	
	
	
	//Dark Alchemist
	
	//Basic
	
	public ItemStack getFungalRootsItem() {
		return plugin.createItem(Material.HANGING_ROOTS,
				1,
				ChatColor.DARK_RED + "Fungal Roots",
				Arrays.asList("Orc Contrived", ChatColor.GREEN + "Basic Material"),
				4111);
	}
	
	public ItemStack getWarpedPowderItem() {
		return plugin.createItem(Material.GUNPOWDER,
				1,
				ChatColor.DARK_RED + "Warped Powder",
				Arrays.asList("Orc Contrived", ChatColor.GREEN + "Basic Material"),
				4112);
	}
	
	public ItemStack getCrimsonPowderItem() {
		return plugin.createItem(Material.REDSTONE,
				1,
				ChatColor.DARK_RED + "Crimson Powder",
				Arrays.asList("Orc Contrived", ChatColor.GREEN + "Basic Material"),
				4113);
	}
	
	public ItemStack getHypnoticRingItem() {
		return plugin.createItem(Material.CLOCK,
				1,
				ChatColor.DARK_RED + "Hypnotic Ring",
				Arrays.asList("Piglins will not attack you when carried.", "Orc Contrived", ChatColor.GREEN + "Basic Item"),
				4114);
	}
	
	public ItemStack getFlamingBucketItem() {
		return plugin.createItem(Material.BUCKET,
				1,
				ChatColor.DARK_RED + "Flaming Bucket",
				Arrays.asList("Does not remove lava block when filling with lava.", "Orc Contrived", ChatColor.GREEN + "Basic Item"),
				4115);
	}
	
	//Flaming Bucket Variants because using a bucket renames it to the default bucket name. I'm so annoyed I have to do this.
	//I might not actually have to do this, I just am because I want to get any potential items registered before I have to deal with handling.
	
	public ItemStack getFlamingLavaBucketItem() {
		return plugin.createItem(Material.LAVA_BUCKET,
				1,
				ChatColor.DARK_RED + "Flaming Lava Bucket",
				Arrays.asList("Does not remove lava block when filling with lava.", "Orc Contrived", ChatColor.GREEN + "Basic Item"),
				4115);
	}
	
	//Intermediate
	
	public ItemStack getEtherealPowderItem() {
		return plugin.createItem(Material.PHANTOM_MEMBRANE,
				1,
				ChatColor.DARK_RED + "Ethereal Powder",
				Arrays.asList("Orc Contrived", ChatColor.YELLOW + "Intermediate Material"),
				4121);
	}
	
	public ItemStack getHoglinEyeItem() {
		return plugin.createItem(Material.FERMENTED_SPIDER_EYE,
				2,
				ChatColor.DARK_RED + "Hoglin Eye",
				Arrays.asList("Orc Contrived", ChatColor.YELLOW + "Intermediate Material"),
				4122);
	}
	
	public ItemStack getRodOfShadowsItem() {
		return plugin.createItem(Material.STICK,
				1,
				ChatColor.DARK_RED + "Rod of Shadows",
				Arrays.asList("Gives you invisibility and blindness", "for 30 seconds when used.", "Orc Contrived", ChatColor.YELLOW + "Intermediate Item"),
				4123);
	}
	
	public ItemStack getWandOfDisplacementItem() {
		return plugin.createItem(Material.END_ROD,
				1,
				ChatColor.DARK_RED + "Wand of Displacement",
				Arrays.asList("Use when looking towards an", "enemy to swap places with them.", "Orc Contrived", ChatColor.YELLOW + "Intermediate Item"),
				4124);
	}
	
	public ItemStack getMagicMirrorItem() {
		return plugin.createItem(Material.GLASS_PANE,
				1,
				ChatColor.DARK_RED + "Magic Mirror",
				Arrays.asList("Teleports you to your spawn", "point outside of danger.", "Orc Contrived", ChatColor.YELLOW + "Intermediate Item"),
				4125);
	}
	
	//Advanced
	
	public ItemStack getEssenceOfVengeanceItem() {
		return plugin.createItem(Material.RED_DYE,
				1,
				ChatColor.DARK_RED + "Essence of Vengeance",
				Arrays.asList("Orc Contrived", ChatColor.GOLD + "Advanced Material"),
				4131);
	}
	
	public ItemStack getWitherPowderItem() {
		return plugin.createItem(Material.GUNPOWDER,
				1,
				ChatColor.DARK_RED + "Wither Powder",
				Arrays.asList("Orc Contrived", ChatColor.GOLD + "Advanced Material"),
				4132);
	}
	
	public ItemStack getEmblemOfTheElixirItem() {
		return plugin.createItem(Material.CLAY_BALL,
				1,
				ChatColor.DARK_RED + "Emblem of the Elixir",
				Arrays.asList("Gives you a random buff every", "minute that lasts a few seconds.", "Orc Contrived", ChatColor.GOLD + "Advanced Item"),
				4133);
	}
	
	public ItemStack getBrewingWandItem() {
		return plugin.createItem(Material.BLAZE_ROD,
				1,
				ChatColor.DARK_RED + "Brewing Wand",
				Arrays.asList("Swap hands to load ingredients.", "Right click a filled cauldron to brew", "that ingredient into the potion.", ChatColor.YELLOW + "Current ingredient: ", "Orc Contrived", ChatColor.GOLD + "Advanced Item"),
				4134);
	}
	
	public ItemStack getAnomalousPickaxeItem() {
		return plugin.createItem(Material.DIAMOND_PICKAXE,
				1,
				ChatColor.DARK_RED + "Anomalous Pickaxe",
				Arrays.asList("Drops overworld ores when used", "on nether ores and vice versa.", "Orc Contrived", ChatColor.GOLD + "Advanced Item"),
				4135);
	}
	
	//Expert
	
	public ItemStack getSoulOfTheScientistItem() {
		return plugin.createItem(Material.HEART_OF_THE_SEA,
				1,
				ChatColor.DARK_RED + "Soul of the Scientist",
				Arrays.asList("Greatly increases your luck when carried.", "Only usable by Dark Alchemists.", "Orc Contrived", ChatColor.RED + "Expert Item"),
				4141);
	}
	
	public ItemStack getWandOfDisfigurationItem() {
		return plugin.createItem(Material.END_ROD,
				1,
				ChatColor.DARK_RED + "Wand of Disfiguration",
				Arrays.asList("Right click a mob to change its type.", "Orc Contrived", ChatColor.RED + "Expert Item"),
				4142);
	}
	
	public ItemStack getVoodooDollItem() {
		return plugin.createItem(Material.TOTEM_OF_UNDYING,
				1,
				ChatColor.DARK_RED + "Voodoo Doll",
				Arrays.asList("Right click to briefly take control", "of another player. Leaves your body defenseless.", ChatColor.RED + "Bound to: " + ChatColor.YELLOW + "UNBOUND", "Swap hands with a player's head to", "bind to that player", "Orc Contrived", ChatColor.RED + "Expert Item"),
				4143);
	}
	
	//Enraged Berserker
	
	//Basic
	
	public ItemStack getHellstoneItem() {
		return plugin.createItem(Material.MAGMA_BLOCK,
				3,
				ChatColor.DARK_RED + "Hellstone",
				Arrays.asList("Orc Contrived", ChatColor.GREEN + "Basic Material"),
				4211);
	}
	
	public ItemStack getEtherealWoodItem() {
		return plugin.createItem(Material.OAK_LOG,
				2,
				ChatColor.DARK_RED + "Ethereal Wood",
				Arrays.asList("Orc Contrived", ChatColor.GREEN + "Basic Material"),
				4212);
	}
	
	public ItemStack getHoglinTuskItem() {
		return plugin.createItem(Material.BONE,
				1,
				ChatColor.DARK_RED + "Hoglin Tusk",
				Arrays.asList("Orc Contrived", ChatColor.GREEN + "Basic Material"),
				4213);
	}
	
	public ItemStack getRageStoneItem() {
		return plugin.createItem(Material.STRIDER_SPAWN_EGG,
				1,
				ChatColor.DARK_RED + "Rage Stone",
				Arrays.asList("Players will drop player heads when carried.", "Orc Contrived", ChatColor.GREEN + "Basic Item"),
				4214);
	}
	
	public ItemStack getSpectralWardItem() {
		return plugin.createItem(Material.GHAST_TEAR,
				1,
				ChatColor.DARK_RED + "Spectral Ward",
				Arrays.asList("Nearby ghasts will not attack you,", "and will be drawn to you when carried.", "Orc Contrived", ChatColor.GREEN + "Basic Item"),
				4215);
	}
	
	//Intermediate
	
	public ItemStack getBoneMarrowItem() {
		return plugin.createItem(Material.WHITE_DYE,
				1,
				ChatColor.DARK_RED + "Bone Marrow",
				Arrays.asList("Orc Contrived", ChatColor.YELLOW + "Intermediate Material"),
				4221);
	}
	
	public ItemStack getPyroclasticIngotItem() {
		return plugin.createItem(Material.NETHERITE_INGOT,
				1,
				ChatColor.DARK_RED + "Pyroclastic Ingot",
				Arrays.asList("Orc Contrived", ChatColor.YELLOW + "Intermediate Material"),
				4222);
	}
	
	public ItemStack getBlazingFuryItem() {
		return plugin.createItem(Material.IRON_AXE,
				1,
				ChatColor.DARK_RED + "Blazing Fury",
				Arrays.asList("Ignites all nearby enemies when charged.", "Orc Contrived", ChatColor.YELLOW + "Intermediate Item"),
				4223);
	}
	
	public ItemStack getSpectralHarnessItem() {
		return plugin.createItem(Material.SADDLE,
				1,
				ChatColor.DARK_RED + "Spectral Harness",
				Arrays.asList("Left click to ride a ghast. Right click to breed.", "Orc Contrived", ChatColor.YELLOW + "Intermediate Item"),
				4224);
	}
	
	public ItemStack getTheShredderItem() {
		return plugin.createItem(Material.IRON_AXE,
				1,
				ChatColor.DARK_RED + "The Shredder",
				Arrays.asList("Deals massive durability damage to shields.", "Orc Contrived", ChatColor.YELLOW + "Intermediate Item"),
				4225);
	}
	
	//Advanced
	
	public ItemStack getEmptySoulItem() {
		return plugin.createItem(Material.HEART_OF_THE_SEA,
				1,
				ChatColor.DARK_RED + "Empty Soul",
				Arrays.asList("Orc Contrived", ChatColor.GOLD + "Advanced Material"),
				4231);
	}
	
	public ItemStack getBloodOfTheForsakenItem() {
		return plugin.createItem(Material.SPIDER_EYE,
				1,
				ChatColor.DARK_RED + "Blood of the Forsaken",
				Arrays.asList("Orc Contrived", ChatColor.GOLD + "Advanced Material"),
				4232);
	}
	
	public ItemStack getEmblemOfTheBladeItem() {
		return plugin.createItem(Material.CLAY_BALL,
				1,
				ChatColor.DARK_RED + "Emblem of the Blade",
				Arrays.asList("Attacking heals you for 10%", "of the damage dealt when carried.", "Orc Contrived", ChatColor.GOLD + "Advanced Item"),
				4233);
	}
	
	public ItemStack getFleshCandleItem() {
		return plugin.createItem(Material.RED_CANDLE,
				1,
				ChatColor.DARK_RED + "Flesh Candle",
				Arrays.asList("Triples mob spawns in a", "radius around you when carried.", "Orc Contrived", ChatColor.GOLD + "Advanced Item"),
				4234);
	}
	
	public ItemStack getCaliburnItem() {
		return plugin.createItem(Material.DIAMOND_SWORD,
				1,
				ChatColor.DARK_RED + "Caliburn",
				Arrays.asList("Lights enemies ablaze with Greek fire.", "Orc Contrived", ChatColor.GOLD + "Advanced Item"),
				4235);
	}
	
	//Expert
	
	public ItemStack getSoulOfTheWarriorItem() {
		return plugin.createItem(Material.HEART_OF_THE_SEA,
				1,
				ChatColor.DARK_RED + "Soul of the Warrior",
				Arrays.asList("Increases your base health when carried.", "Only usable by Enraged Berserkers.", "Orc Contrived", ChatColor.RED + "Expert Item"),
				4241);
	}
	
	public ItemStack getSoulboundAxeItem() {
		return plugin.createItem(Material.DIAMOND_AXE,
				1,
				ChatColor.DARK_RED + "Soulbound Axe",
				Arrays.asList("This item will be kept when you die.", "Orc Contrived", ChatColor.RED + "Expert Item"),
				4242);
	}
	
	public ItemStack getPhoenixAshesItem() {
		return plugin.createItem(Material.BLAZE_POWDER,
				1,
				ChatColor.DARK_RED + "Phoenix Ashes",
				Arrays.asList("Right click to become an enraged phoenix.", "Sneak to cancel.", "Orc Contrived", ChatColor.RED + "Expert Item"),
				4243);
	}
	
	
	
	
	
	//Greedy Scrapper
	
	//Basic
	
	public ItemStack getScrapItem() {
		return plugin.createItem(Material.QUARTZ,
				3,
				ChatColor.DARK_RED + "Scrap",
				Arrays.asList("Orc Contrived", ChatColor.GREEN + "Basic Material"),
				4311);
	}
	
	public ItemStack getWastelandGooItem() {
		return plugin.createItem(Material.RAW_COPPER,
				2,
				ChatColor.DARK_RED + "Wasteland Goo",
				Arrays.asList("Orc Contrived", ChatColor.GREEN + "Basic Material"),
				4312);
	}
	
	public ItemStack getPebblesItem() {
		return plugin.createItem(Material.MELON_SEEDS,
				2,
				ChatColor.DARK_RED + "Pebbles",
				Arrays.asList("Orc Contrived", ChatColor.GREEN + "Basic Material"),
				4313);
	}
	
	public ItemStack getMagmaStoneItem() {
		return plugin.createItem(Material.SPIDER_SPAWN_EGG,
				1,
				ChatColor.DARK_RED + "Magma Stone",
				Arrays.asList("Allows you to walk on lava when carried.", "Orc Contrived", ChatColor.GREEN + "Basic Item"),
				4314);
	}
	
	public ItemStack getDemonicWrenchItem() {
		return plugin.createItem(Material.NETHERITE_HOE,
				1,
				ChatColor.DARK_RED + "Demonic Wrench",
				Arrays.asList("Right click a spawner to pick it up.", "Orc Contrived", ChatColor.GREEN + "Basic Item"),
				4315);
	}
	
	//Intermediate
	
	public ItemStack getBoltsItem() {
		return plugin.createItem(Material.LEVER,
				1,
				ChatColor.DARK_RED + "Bolts",
				Arrays.asList("Orc Contrived", ChatColor.YELLOW + "Intermediate Material"),
				4321);
	}
	
	public ItemStack getSteelFeatherItem() {
		return plugin.createItem(Material.FEATHER,
				1,
				ChatColor.DARK_RED + "Steel Feather",
				Arrays.asList("Orc Contrived", ChatColor.YELLOW + "Intermediate Material"),
				4322);
	}
	
	public ItemStack getFragmentedHelmetItem() {
		return plugin.createItem(Material.CHAINMAIL_HELMET,
				1,
				ChatColor.DARK_RED + "Fragmented Helmet",
				Arrays.asList("Set Bonus: Sends out fragments on hit.", "Hit enemies will be damaged and withered.", "Orc Contrived", ChatColor.YELLOW + "Intermediate Item"),
				4323);
	}
	
	public ItemStack getFragmentedChestpieceItem() {
		return plugin.createItem(Material.CHAINMAIL_CHESTPLATE,
				1,
				ChatColor.DARK_RED + "Fragmented Chestpiece",
				Arrays.asList("Set Bonus: Sends out fragments on hit.", "Hit enemies will be damaged and withered.", "Orc Contrived", ChatColor.YELLOW + "Intermediate Item"),
				4324);
	}
	
	public ItemStack getFragmentedLeggingsItem() {
		return plugin.createItem(Material.CHAINMAIL_LEGGINGS,
				1,
				ChatColor.DARK_RED + "Fragmented Leggings",
				Arrays.asList("Set Bonus: Sends out fragments on hit.", "Hit enemies will be damaged and withered.", "Orc Contrived", ChatColor.YELLOW + "Intermediate Item"),
				4325);
	}
	
	public ItemStack getFragmentedGreavesItem() {
		return plugin.createItem(Material.CHAINMAIL_BOOTS,
				1,
				ChatColor.DARK_RED + "Fragmented Greaves",
				Arrays.asList("Set Bonus: Sends out fragments on hit.", "Hit enemies will be damaged and withered.", "Orc Contrived", ChatColor.YELLOW + "Intermediate Item"),
				4326);
	}
	
	public ItemStack getFlamedashBootsItem() {
		return plugin.createItem(Material.GOLDEN_BOOTS,
				1,
				ChatColor.DARK_RED + "Flamedash Boots",
				Arrays.asList("Leaves a trail of fire behind you when sprinting.", "Orc Contrived", ChatColor.YELLOW + "Intermediate Item"),
				4327);
	}
	
	//Advanced
	
	public ItemStack getMagmaCrystalItem() {
		return plugin.createItem(Material.QUARTZ,
				1,
				ChatColor.DARK_RED + "Magma Crystal",
				Arrays.asList("Orc Contrived", ChatColor.GOLD + "Advanced Material"),
				4331);
	}
	
	public ItemStack getGalvinizedAlloyItem() {
		return plugin.createItem(Material.IRON_INGOT,
				1,
				ChatColor.DARK_RED + "Galvinized Alloy",
				Arrays.asList("Orc Contrived", ChatColor.GOLD + "Advanced Material"),
				4332);
	}
	
	public ItemStack getScrappyHelmetItem() {
		return plugin.createItem(Material.DIAMOND_HELMET,
				1,
				ChatColor.DARK_RED + "Scrappy Helmet",
				Arrays.asList("Set Bonus: 10% chance to steal", "an item from an enemy on attack.", "Orc Contrived", ChatColor.GOLD + "Advanced Item"),
				4333);
	}
	
	public ItemStack getScrappyChestpieceItem() {
		return plugin.createItem(Material.DIAMOND_CHESTPLATE,
				1,
				ChatColor.DARK_RED + "Scrappy Chestpiece",
				Arrays.asList("Set Bonus: 10% chance to steal", "an item from an enemy on attack.", "Orc Contrived", ChatColor.GOLD + "Advanced Item"),
				4334);
	}
	
	public ItemStack getScrappyLeggingsItem() {
		return plugin.createItem(Material.DIAMOND_LEGGINGS,
				1,
				ChatColor.DARK_RED + "Scrappy Leggings",
				Arrays.asList("Set Bonus: 10% chance to steal", "an item from an enemy on attack.", "Orc Contrived", ChatColor.GOLD + "Advanced Item"),
				4335);
	}

	public ItemStack getScrappyGreavesItem() {
		return plugin.createItem(Material.DIAMOND_BOOTS,
				1,
				ChatColor.DARK_RED + "Scrappy Greaves",
				Arrays.asList("Set Bonus: 10% chance to steal", "an item from an enemy on attack.", "Orc Contrived", ChatColor.GOLD + "Advanced Item"),
				4336);
	}
	
	public ItemStack getEmblemOfTheRavenItem() {
		return plugin.createItem(Material.CLAY_BALL,
				1,
				ChatColor.DARK_RED + "Emblem of the Raven",
				Arrays.asList("Items on the ground will be", "drawn to you when carried.", "Orc Contrived", ChatColor.GOLD + "Advanced Item"),
				4337);
	}
	
	//Expert
	
	public ItemStack getSoulOfTheThiefItem() {
		return plugin.createItem(Material.HEART_OF_THE_SEA,
				1,
				ChatColor.DARK_RED + "Soul of the Thief",
				Arrays.asList("10% chance to dodge damage when carried.", "Only usable by Greedy Scrappers.", "Orc Contrived", ChatColor.RED + "Expert Item"),
				4341);
	}
	
	public ItemStack getSoulboundGreavesItem() {
		return plugin.createItem(Material.DIAMOND_BOOTS,
				1,
				ChatColor.DARK_RED + "Soulbound Greaves",
				Arrays.asList("This item will be kept when you die.", "Orc Contrived", ChatColor.RED + "Expert Item"),
				4342);
	}
	
	public ItemStack getAberrantCompassItem() {
		return plugin.createItem(Material.COMPASS,
				1,
				ChatColor.DARK_RED + "Aberrant Compass",
				Arrays.asList("Points to the nearest rare structure.", "Right click to warp to it.", "Orc Contrived", ChatColor.RED + "Expert Item"),
				4343);
	}
	
}


