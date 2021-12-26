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
			if (profession.equals("Arcane Jeweller"))
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
				Arrays.asList(ChatColor.LIGHT_PURPLE + "Masters of the axe and melee combat", "Dwarves dwell within the earth", "constantly delving for the riches beneath the surface."));
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
				"Inventories", 
				Arrays.asList(ChatColor.LIGHT_PURPLE + "Access other players' Inventories"));
	}
	
	public ItemStack getInventorySnoopingEnderChestItem() {
		return plugin.createItem(
				Material.ENDER_CHEST, 
				1, 
				"Ender Chests", 
				Arrays.asList(ChatColor.LIGHT_PURPLE + "Access other players' Ender Chests"));
	}
	
	public ItemStack getTpaFeatureItem() {
		File file = new File(plugin.getDataFolder() + File.separator + "config.yml");
	 	FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		return plugin.createItem(
				Material.ENDER_EYE, 
				1, 
				"TPA", 
				Arrays.asList(ChatColor.LIGHT_PURPLE + "Allow Players to teleport.", config.get("allowTpa").toString()));
	}
}
































