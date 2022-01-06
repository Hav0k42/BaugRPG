package org.baugindustries.baugrpg;

import java.io.File;
import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import net.md_5.bungee.api.ChatColor;


//Pretty much just the ItemStack version of a lang file. Trying to make the menus consistent so there arent two different types of back buttons or confirm buttons.
public class CustomItems {
	Main plugin;
	CustomItems(Main plugin) {
		this.plugin = plugin;
	}
	
	public ItemStack getBackItem() {
		return plugin.createItem(Material.RED_STAINED_GLASS_PANE, 1, ChatColor.RED + "Back", null);
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
				Arrays.asList(ChatColor.LIGHT_PURPLE + "Access fellow Elves' inventories and ender chests"));
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
		return plugin.createItem(
				Material.ENDER_EYE, 
				1, 
				ChatColor.DARK_GREEN + "TPA", 
				Arrays.asList(ChatColor.LIGHT_PURPLE + "Allow Players to teleport.", config.get("allowTpa").toString()));
	}
	
	public ItemStack getStableMasterSkill1Item(Player player) {
	 	return plugin.createItem(
	 			Material.LEATHER_HORSE_ARMOR,
	 			1,
	 			ChatColor.DARK_AQUA + "Hermes Hooves",
	 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Increase mounted speed threefold",
	 					getSecondDataSkillItemsString(player, "StableMaster1", "8 Points")));
	}
	
	public ItemStack getStableMasterSkill2Item(Player player) {
	 	return plugin.createItem(
	 			Material.IRON_SWORD,
	 			1,
	 			ChatColor.DARK_AQUA + "Mounted Mania",
	 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Increased Damage when riding horses.",
	 					getSecondDataSkillItemsString(player, "StableMaster2", "9 Points")));
	}
	
	public ItemStack getStableMasterSkill3Item(Player player) {
	 	return plugin.createItem(
	 			Material.APPLE,
	 			1,
	 			ChatColor.DARK_AQUA + "Healthy Horses",
	 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Increased Health when riding horses",
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
			if (timeRemaining > minutesToMillis) {
				//display in minutes
				timeString = ((int)(timeRemaining / minutesToMillis) + " Minutes Remaining");
			} else {
				//display in seconds
				timeString = ((int)(timeRemaining / 1000) + " Seconds Remaining");
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
			if (timeRemaining > minutesToMillis) {
				//display in minutes
				timeString = ((int)(timeRemaining / minutesToMillis) + " Minutes Remaining");
			} else {
				//display in seconds
				timeString = ((int)(timeRemaining / 1000) + " Seconds Remaining");
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
	 			ChatColor.DARK_GREEN + "Woodland Absoprtion",
	 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Slow Regen that is not dependent on hunger.",
	 					getSecondDataSkillItemsString(player, "WoodlandCraftsman1", "5 Points")));
	}
	
	public ItemStack getWoodlandCraftsmanSkill2Item(Player player) {
	 	return plugin.createItem(
	 			Material.OAK_SAPLING,
	 			1,
	 			ChatColor.DARK_GREEN + "Arborated Strike",
	 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Low percent chance for attacks",
	 					ChatColor.LIGHT_PURPLE + "to summon an Arborated Strike",
	 					ChatColor.LIGHT_PURPLE + "damaging everything it hits except for elves.",
	 					getSecondDataSkillItemsString(player, "WoodlandCraftsman2", "20 Points")));
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
	 	return plugin.createItem(
	 			Material.NETHER_STAR,
	 			1,
	 			ChatColor.DARK_GREEN + "Starlight Healing",
	 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Heal one nearby elf using starlight power.",
	 					getSecondDataSkillItemsString(player, "LunarArtificer2", "13 Points")));
	}
	
	public ItemStack getLunarArtificerSkill3Item(Player player) {
	 	return plugin.createItem(
	 			Material.REDSTONE_LAMP,
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
	 	return plugin.createItem(
	 			Material.NETHERITE_AXE,
	 			1,
	 			ChatColor.DARK_RED + "Rage",
	 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Hitting a killstreak of 5",
	 					ChatColor.LIGHT_PURPLE + "sends you into a blind rage.",
	 					getSecondDataSkillItemsString(player, "EnragedBerserker1", "15 Points")));
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
	 	return plugin.createItem(
	 			Material.NETHERITE_SCRAP,
	 			1,
	 			ChatColor.DARK_RED + "Greedy Reinforcements",
	 			Arrays.asList(ChatColor.LIGHT_PURPLE + "Give nearby orcs a resistance",
	 					ChatColor.LIGHT_PURPLE + "buff at low health.",
	 					getSecondDataSkillItemsString(player, "GreedyScrapper2", "20 Points")));
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
}
































