package org.baugindustries.baugrpg;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
	
	
	public Inventory getConfirmStepDownMenu() {
		
		int inventorySize = 27;
		String inventoryName = ChatColor.DARK_GREEN + "Confirm Stepping Down";
		
		Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
		
		ItemStack blankItem = plugin.itemManager.getBlankItem();
		for (int i = 0; i < inventorySize; i++) {
			inventory.setItem(i, blankItem);
		}
		
		
		ItemStack infoItem = plugin.createItem(Material.NETHER_STAR,
				1,
				ChatColor.DARK_GREEN + "CONFIRM DECISION",
				Arrays.asList(ChatColor.LIGHT_PURPLE + "Are you sure you want",
						"to step down as Secretary?"));
		
		inventory.setItem(4, infoItem);
		
		
		inventory.setItem(11, plugin.itemManager.getYesItem());
		
		inventory.setItem(15, plugin.itemManager.getNoItem());
		
		return inventory;
	}
	
	
	
	public Inventory getConfirmAppointKingMenu(UUID uuid) {
		
		int inventorySize = 27;
		String inventoryName = ChatColor.DARK_AQUA + "Confirm Appointment";
		
		Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
		
		ItemStack blankItem = plugin.itemManager.getBlankItem();
		for (int i = 0; i < inventorySize; i++) {
			inventory.setItem(i, blankItem);
		}
		
		OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
		
		ItemStack infoItem = plugin.createItem(Material.PLAYER_HEAD,
				1,
				ChatColor.DARK_AQUA + player.getName(),
				Arrays.asList(ChatColor.LIGHT_PURPLE + "Are you sure you want",
						"to appoint " + ChatColor.DARK_AQUA + player.getName(),
						"as King?"));
		
		SkullMeta meta = (SkullMeta) infoItem.getItemMeta();
		meta.setOwningPlayer(player);
		infoItem.setItemMeta(meta);
		
		inventory.setItem(4, infoItem);
		
		
		inventory.setItem(11, plugin.itemManager.getYesItem());
		
		inventory.setItem(15, plugin.itemManager.getNoItem());
		
		return inventory;
	}
	
	
	
	public Inventory getAppointKingMenu(UUID uuid, int page) {
		Player player = Bukkit.getPlayer(uuid);
		List<OfflinePlayer> allOfflineMen = plugin.getAllOfflineMen();
		ItemStack backItem = plugin.itemManager.getBackItem();
		List<ItemStack> menHeads = new ArrayList<ItemStack>();
		int inventorySize = 18;
		if (menHeads.size() > 9) {
			inventorySize = 27;
		}
		if (menHeads.size() > 18) {
			inventorySize = 36;
		}
		if (menHeads.size() > 27) {
			inventorySize = 45;
		}
		if (menHeads.size() > 36) {
			inventorySize = 54;
		}
		for (int i = 0; i < allOfflineMen.size(); i++) {
			ItemStack tempPlayerHead = new ItemStack(Material.PLAYER_HEAD);
			SkullMeta tempPlayerHeadMeta = (SkullMeta)tempPlayerHead.getItemMeta();
			List<String> tempPlayerHeadLore = Arrays.asList(ChatColor.YELLOW + "Appoint " + allOfflineMen.get(i).getName() + " as King.");
			tempPlayerHeadMeta.setLore(tempPlayerHeadLore);
			tempPlayerHeadMeta.setDisplayName(allOfflineMen.get(i).getName());
			tempPlayerHeadMeta.setOwningPlayer(allOfflineMen.get(i));
			tempPlayerHead.setItemMeta(tempPlayerHeadMeta);
			if (!player.getUniqueId().equals(allOfflineMen.get(i).getUniqueId())) {
				menHeads.add(tempPlayerHead);
			}
		}
		
		for (int i = 0; i < page * 45; i++) {
			menHeads.remove(0);
		}
		
		String inventoryName = ChatColor.DARK_AQUA + "Appoint King";
		if (page != 0) {
			inventoryName = inventoryName + " " + page;
		}
		Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
		
		
		for (int i = 0; i < inventorySize; i++) {
			inventory.setItem(i, new ItemStack(Material.AIR));
		}
		
		
		for (int i = 0; i < menHeads.size(); i++) {
			inventory.setItem(i, menHeads.get(i));
			if (i > 45) {
				break;
			}
		}
		
		
		
		
		if (page != 0) {
			inventory.setItem(inventorySize - 2, plugin.itemManager.getPreviousPageItem());
		}
		
		if (menHeads.size() > 45) {
			inventory.setItem(46, backItem);
			inventory.setItem(53, plugin.itemManager.getNextPageItem());
			//add menu to go to the next page of elves, also, figure out how to get multiple pages of elves if for some reason theres a server with more than 45 fucking elves.
		} else if (menHeads.size() > 36) {
			inventory.setItem(46, backItem);
		} else if (menHeads.size() > 27) {
			inventory.setItem(37, backItem);
		} else if (menHeads.size() > 18) {
			inventory.setItem(28, backItem);
		} else if (menHeads.size() > 9) {
			inventory.setItem(19, backItem);
		} else {
			inventory.setItem(10, backItem);
		}
		
		return inventory;
	}
	
	
	
	public Inventory getGovernmentMenuInventory(Player player) {
		PersistentDataContainer data = player.getPersistentDataContainer();
		int race = data.get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER);
		
		int inventorySize = 18;
		String inventoryName = ChatColor.DARK_AQUA + "Kingdom Info";
		switch (race) {
			case 2:
				inventoryName = ChatColor.DARK_GREEN + "Commune Info";
				break;
			case 3:
				inventoryName = ChatColor.DARK_PURPLE + "Guild Info";
				break;
			case 4:
				inventoryName = ChatColor.DARK_RED + "Horde Info";
				break;
		}

		File leaderDataFile = new File(plugin.getDataFolder() + File.separator + "leaderData.yml");
		FileConfiguration leaderConfig = YamlConfiguration.loadConfiguration(leaderDataFile);
		Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
		
		
		
		inventory.setItem(9,  plugin.itemManager.getBackItem());
		
		
		
		boolean enableLeaders = false;
		if (!leaderConfig.contains("elfLeaderUUID") || !leaderConfig.contains("menLeaderUUID")) {
			if (leaderConfig.contains("elfElectionStartTime") && leaderConfig.contains("menElectionStartTime")) {
				if (System.currentTimeMillis() - leaderConfig.getLong("elfElectionStartTime") > 129600000 && System.currentTimeMillis() - leaderConfig.getLong("menElectionStartTime") > 129600000) {
					enableLeaders = true;
					if (leaderConfig.contains("VotedPlayers")) {
						leaderConfig.set("VotedPlayers", null);
					}
				}
				if (System.currentTimeMillis() - leaderConfig.getLong("elfElectionStartTime") > 129600000) {
					List<String> secretaryCandidates = leaderConfig.getStringList("elfCandidates");
					UUID secretaryUUID = null;
					for (String string : secretaryCandidates) {
						UUID candidateUUID = UUID.fromString(string);
						if (secretaryUUID == null || (leaderConfig.getInt(string + "Votes") > leaderConfig.getInt(secretaryUUID.toString() + "Votes"))) {
							secretaryUUID = candidateUUID;
						}
						leaderConfig.set(string + "Votes", null);
					}
					leaderConfig.set("elfLeaderUUID", secretaryUUID.toString());
					leaderConfig.set("elfCandidates", null);
				}
				if (System.currentTimeMillis() - leaderConfig.getLong("menElectionStartTime") > 129600000) {
					List<String> kingCandidates = leaderConfig.getStringList("menCandidates");
					UUID kingUUID = null;
					for (String string : kingCandidates) {
						UUID candidateUUID = UUID.fromString(string);
						if (kingUUID == null || (leaderConfig.getInt(string + "Votes") > leaderConfig.getInt(kingUUID.toString() + "Votes"))) {
							kingUUID = candidateUUID;
						}
						leaderConfig.set(string + "Votes", null);
					}
					leaderConfig.set("menLeaderUUID", kingUUID.toString());
					leaderConfig.set("menCandidates", null);
				}
				try {
					leaderConfig.save(leaderDataFile);
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		} else {
			enableLeaders = true;
		}
		
		
		if (race == 2 && leaderConfig.contains("elfElectionActive") && leaderConfig.getBoolean("elfElectionActive")) {
			if (leaderConfig.getStringList("elfVotedPlayers").contains(player.getUniqueId().toString())) {
				inventory.setItem(4, plugin.itemManager.getAlreadyVotedItem());
			} else {
				inventory.setItem(4, plugin.itemManager.getVoteOnLeaderItem(race));
			}
			inventory.setItem(5, plugin.itemManager.getNominateSelfItem(race));
			
			
			
			if (System.currentTimeMillis() - leaderConfig.getLong("elfElectionStartTime") > 129600000) {
				List<String> secretaryCandidates = leaderConfig.getStringList("elfCandidates");
				UUID secretaryUUID = null;
				for (String string : secretaryCandidates) {
					UUID candidateUUID = UUID.fromString(string);
					if (secretaryUUID == null || (leaderConfig.getInt(string + "Votes") > leaderConfig.getInt(secretaryUUID.toString() + "Votes"))) {
						secretaryUUID = candidateUUID;
					}
					leaderConfig.set(string + "Votes", null);
				}
				leaderConfig.set("elfLeaderUUID", secretaryUUID.toString());
				leaderConfig.set("elfCandidates", null);
				leaderConfig.set("elfVotedPlayers", null);
				leaderConfig.set("elfElectionActive", false);
			}
			try {
				leaderConfig.save(leaderDataFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}
		
		if (race == 1 && leaderConfig.contains("menElectionActive") && leaderConfig.getBoolean("menElectionActive")) {
			if (leaderConfig.getStringList("menVotedPlayers").contains(player.getUniqueId().toString())) {
				inventory.setItem(4, plugin.itemManager.getAlreadyVotedItem());
			} else {
				inventory.setItem(4, plugin.itemManager.getVoteOnLeaderItem(race));
			}
			inventory.setItem(5, plugin.itemManager.getNominateSelfItem(race));
			
			
			
			if (System.currentTimeMillis() - leaderConfig.getLong("menElectionStartTime") > 129600000) {
				List<String> kingCandidates = leaderConfig.getStringList("menCandidates");
				UUID kingUUID = null;
				for (String string : kingCandidates) {
					UUID candidateUUID = UUID.fromString(string);
					if (kingUUID == null || (leaderConfig.getInt(string + "Votes") > leaderConfig.getInt(kingUUID.toString() + "Votes"))) {
						kingUUID = candidateUUID;
					}
					leaderConfig.set(string + "Votes", null);
				}
				leaderConfig.set("menLeaderUUID", kingUUID.toString());
				leaderConfig.set("menCandidates", null);
				leaderConfig.set("menVotedPlayers", null);
				leaderConfig.set("menElectionActive", false);
			}
			try {
				leaderConfig.save(leaderDataFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			
			
		}
		
		SkullMeta skullMeta = (SkullMeta)plugin.itemManager.getLeaderHeadItem(race).getItemMeta();
		ItemStack tempItem = plugin.itemManager.getLeaderHeadItem(race);
		if (!enableLeaders) {
			String leaderTitle = "";
			switch (race) {
				case 1:
					leaderTitle = "King";
					break;
				case 2:
					leaderTitle = "Secretary";
					break;
				case 3:
					leaderTitle = "Emperor";
					break;
				case 4:
					leaderTitle = "Chief";
					break;
			}
			tempItem = plugin.createItem(Material.PLAYER_HEAD, 1, getRaceColor(race) + "UNKNOWN", Arrays.asList("You currently have no " + leaderTitle));
		}
		
		inventory.setItem(2, tempItem);
		
		if (skullMeta.getOwningPlayer() != null) {
			if (skullMeta.getOwningPlayer().getUniqueId().equals(player.getUniqueId())) {
				//Player is the leader
				int currentIndex = 11;
				if (race == 1) {
					inventory.setItem(currentIndex, plugin.itemManager.getAppointKingItem());
					currentIndex++;
				} else if (race == 2) {
					inventory.setItem(currentIndex, plugin.itemManager.getStepDownItem());
					if (leaderConfig.contains("elfElectionActive") && leaderConfig.getBoolean("elfElectionActive")) {
						tempItem = plugin.itemManager.getStepDownItem();
						tempItem.setType(Material.BARRIER);
						inventory.setItem(currentIndex, tempItem);
					}
					currentIndex++;
				}
				
				inventory.setItem(currentIndex, plugin.itemManager.getDraftLawsItem(race));
				currentIndex++;
				
				inventory.setItem(currentIndex, plugin.itemManager.getViewLawSuggestionsItem(race));
				currentIndex++;
				
				inventory.setItem(currentIndex, plugin.itemManager.getViewLawsItem(race));
				currentIndex++;
				
				inventory.setItem(currentIndex, plugin.itemManager.getLeaderViewCrimesItem(race));
				currentIndex++;
				
				if (race == 3) {
					inventory.setItem(currentIndex, plugin.itemManager.getTaxDwarvesItem());
					currentIndex++;
					inventory.setItem(currentIndex, plugin.itemManager.getTaxDwarvesWeeklyItem());
					currentIndex++;
				}
				
			} else {
				//Player is not the leader
				int currentIndex = 11;
				if (race == 1) {
					inventory.setItem(currentIndex, plugin.itemManager.getOverthrowKingItem());
					if (leaderConfig.contains("menElectionActive") && leaderConfig.getBoolean("menElectionActive")) {
						tempItem = plugin.itemManager.getOverthrowKingItem();
						tempItem.setType(Material.BARRIER);
						inventory.setItem(currentIndex, tempItem);
					}
					if (leaderConfig.getStringList("overthrowKingVotes").contains(player.getUniqueId().toString())) {
						inventory.setItem(currentIndex, plugin.itemManager.getAlreadyVotedItem());
					}
					currentIndex++;
				} else if (race == 2) {
					inventory.setItem(currentIndex, plugin.itemManager.getImpeachSecretaryItem());
					if (leaderConfig.contains("elfElectionActive") && leaderConfig.getBoolean("elfElectionActive")) {
						tempItem = plugin.itemManager.getImpeachSecretaryItem();
						tempItem.setType(Material.BARRIER);
						inventory.setItem(currentIndex, tempItem);
					}
					if (leaderConfig.getStringList("impeachSecretaryVotes").contains(player.getUniqueId().toString())) {
						inventory.setItem(currentIndex, plugin.itemManager.getAlreadyVotedItem());
					}
					
					currentIndex++;
				}
				
				inventory.setItem(currentIndex, plugin.itemManager.getSubmitLawSuggestionItem(race));
				currentIndex++;
				
				if (race == 2) {
					inventory.setItem(currentIndex, plugin.itemManager.getVoteOnLawsItem());
					currentIndex++;
				}
				
				inventory.setItem(currentIndex, plugin.itemManager.getViewLawsItem(race));
				currentIndex++;
				
				if (race == 1 || race == 2) {
					inventory.setItem(currentIndex, plugin.itemManager.getViewReportedCrimesItem(race));
					currentIndex++;
				}
				
				inventory.setItem(currentIndex, plugin.itemManager.getReportCrimeItem(race));
				currentIndex++;
				
			}
		} else {
			//There is no leader
			if (leaderConfig.getStringList("VotedPlayers").contains(player.getUniqueId().toString())) {
				inventory.setItem(11, plugin.itemManager.getAlreadyVotedItem());
			} else {
				inventory.setItem(11, plugin.itemManager.getVoteOnLeaderItem(race));
			}
			inventory.setItem(12, plugin.itemManager.getNominateSelfItem(race));
		}
		
		
		
		return inventory;
	}
	
	
	
	
	
	
	
	
	public Inventory getRaceSkillTreeMenuInventory(Player player) {

		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
	 	

		PersistentDataContainer data = player.getPersistentDataContainer();
		int race = data.get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER);
		
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
		
		inventory.setItem(24, lockedItem);
		inventory.setItem(25, lockedItem);
		inventory.setItem(33, lockedItem);
		inventory.setItem(34, lockedItem);
		
		ItemStack[] currentItem = new ItemStack[2];
		String[] skillNames = new String[2];
		switch (race) {
			case 1://Men
				inventory.setItem(6, plugin.itemManager.getMenBuffBiomeItem(player));
				inventory.setItem(7, plugin.itemManager.getMenFallDamageNerfItem(player));
				skillNames[0] = "menBuffBiome";
				skillNames[1] = "menDanger";
				currentItem[0] = plugin.createItem(Material.BLUE_STAINED_GLASS_PANE, 1, ChatColor.DARK_AQUA + "Upgrade Home Field Advantage: Lvl " + (skillsconfig.getInt(skillNames[0]) + 1));
				currentItem[1] = plugin.createItem(Material.BLUE_STAINED_GLASS_PANE, 1, ChatColor.DARK_AQUA + "Upgrade Reinforced Legs: Lvl " + (skillsconfig.getInt(skillNames[1]) + 1));
				break;
			case 2://Elves
				inventory.setItem(6, plugin.itemManager.getElfBuffBiomeItem(player));
				inventory.setItem(7, plugin.itemManager.getElfRespirationItem(player));
				skillNames[0] = "elfBuffBiome";
				skillNames[1] = "elfDanger";
				currentItem[0] = plugin.createItem(Material.BLUE_STAINED_GLASS_PANE, 1, ChatColor.DARK_GREEN + "Upgrade Underbrush Protection: Lvl " + (skillsconfig.getInt(skillNames[0]) + 1));
				currentItem[1] = plugin.createItem(Material.BLUE_STAINED_GLASS_PANE, 1, ChatColor.DARK_GREEN + "Upgrade Aqueous Insufflation: Lvl " + (skillsconfig.getInt(skillNames[1]) + 1));
				break;
			case 3://Dwarves
				inventory.setItem(6, plugin.itemManager.getDwarfBuffBiomeItem(player));
				inventory.setItem(7, plugin.itemManager.getDwarfUndergroundMobResistanceItem(player));
				skillNames[0] = "dwarfBuffBiome";
				skillNames[1] = "dwarfDanger";
				currentItem[0] = plugin.createItem(Material.BLUE_STAINED_GLASS_PANE, 1, ChatColor.DARK_PURPLE + "Upgrade Mountainous Grit: Lvl " + (skillsconfig.getInt(skillNames[0]) + 1));
				currentItem[1] = plugin.createItem(Material.BLUE_STAINED_GLASS_PANE, 1, ChatColor.DARK_PURPLE + "Upgrade Shaded Security: Lvl " + (skillsconfig.getInt(skillNames[1]) + 1));
				break;
			case 4://Orcs
				inventory.setItem(6, plugin.itemManager.getOrcBuffBiomeItem(player));
				inventory.setItem(7, plugin.itemManager.getOrcLavaImmunityItem(player));
				skillNames[0] = "orcBuffBiome";
				skillNames[1] = "orcDanger";
				currentItem[0] = plugin.createItem(Material.BLUE_STAINED_GLASS_PANE, 1, ChatColor.DARK_RED + "Upgrade Hellish Reposte: Lvl " + (skillsconfig.getInt(skillNames[0]) + 1));
				currentItem[1] = plugin.createItem(Material.BLUE_STAINED_GLASS_PANE, 1, ChatColor.DARK_RED + "Upgrade Iron Skin: Lvl " + (skillsconfig.getInt(skillNames[1]) + 1));
				break;
		}
		
		
		
		
		
		for (int i = 0; i < currentItem.length; i++) {
			int levelInt = skillsconfig.getInt(skillNames[i]);
			int rowShifter = i;
			if (levelInt == 0) {
				inventory.setItem(42 + rowShifter, currentItem[i]);
			} else if (levelInt == 1) {
				inventory.setItem(42 + rowShifter, ownedItem);
				inventory.setItem(33 + rowShifter, currentItem[i]);
			} else if (levelInt == 2) {
				inventory.setItem(42 + rowShifter, ownedItem);
				inventory.setItem(33 + rowShifter, ownedItem);
				inventory.setItem(24 + rowShifter, currentItem[i]);
			} else {
				inventory.setItem(42 + rowShifter, ownedItem);
				inventory.setItem(33 + rowShifter, ownedItem);
				inventory.setItem(24 + rowShifter, ownedItem);
			}
		}
		
		
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
		PersistentDataContainer data = player.getPersistentDataContainer();
		int race = data.get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER);
		String inventoryName = getRaceColor(race) + "Scrolls of Baug";
		
		Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
		
		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
		int level = skillsconfig.getInt("totalSkillPoints");
		
		int currentSlot = 11;
		if (race == 1) {//Men
			inventory.setItem(0, plugin.itemManager.getScrollsOfBaugInfoItem());
			
			inventory.setItem(currentSlot, plugin.itemManager.getGovernmentMenuItem(race));
			currentSlot++;
			
			if (level > 5) {
				inventory.setItem(currentSlot, plugin.itemManager.getSkillTreeMenuItem());
			}
			currentSlot++;
			
			
			
		} else if (race == 2) {//Elves
			
			inventory.setItem(0, plugin.itemManager.getScrollsOfBaugInfoItem());
			
			inventory.setItem(currentSlot, plugin.itemManager.getGovernmentMenuItem(race));
			currentSlot++;
			
			if (level > 5) {
				inventory.setItem(currentSlot, plugin.itemManager.getSkillTreeMenuItem());
			}
			currentSlot++;
			
			inventory.setItem(currentSlot, plugin.itemManager.getCommunistHubItem());
			currentSlot++;
			
			
			
		} else if (race == 3) {//Dwarves
			
			inventory.setItem(0, plugin.itemManager.getScrollsOfBaugInfoItem());
			
			inventory.setItem(currentSlot, plugin.itemManager.getGovernmentMenuItem(race));
			currentSlot++;
			
			if (level > 5) {
				inventory.setItem(currentSlot, plugin.itemManager.getSkillTreeMenuItem());
			}
			currentSlot++;
			
			inventory.setItem(currentSlot, plugin.itemManager.getDwarvenBankConversionItem());
			currentSlot++;
			
			
		} else if (race == 4) {//Orcs
			
			inventory.setItem(0, plugin.itemManager.getScrollsOfBaugInfoItem());
			
			inventory.setItem(currentSlot, plugin.itemManager.getGovernmentMenuItem(race));
			currentSlot++;
			
			if (level > 5) {
				inventory.setItem(currentSlot, plugin.itemManager.getSkillTreeMenuItem());
			}
			currentSlot++;

			
			
		} else if (race == 5) {//Wizards
			
			
			inventory.setItem(0, plugin.itemManager.getWizardScrollsOfBaugInfoItem());
			
			
			inventory.setItem(currentSlot, plugin.itemManager.getFeatureManagementItem());
			currentSlot++;
			
			inventory.setItem(currentSlot, plugin.itemManager.getInventorySnoopingItem());
			currentSlot++;
			
			
		}
		
		return inventory;
	}
	
	
	
	
	
	
	
	
	
	
	public Inventory getSetRaceMenuInventory() {
		int inventorySize = 54;
		String inventoryName = ChatColor.DARK_GRAY + "Choose Your Race";
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
		String inventoryName = ChatColor.DARK_GRAY + "Confirm Selection";
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
	
	
	
	
	
	
	
	
	
	
	public Inventory getElvesCommunistInventoryMenuInventory(Player player, int page) {
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
		
		for (int i = 0; i < page * 45; i++) {
			elfHeads.remove(0);
		}
		
		String inventoryName = "Elves Inventories";
		if (page != 0) {
			inventoryName = inventoryName + " " + page;
		}
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
		
		
		
		
		if (page != 0) {
			inventory.setItem(inventorySize - 2, plugin.itemManager.getPreviousPageItem());
		}
		
		if (elfHeads.size() > 45) {
			inventory.setItem(46, backItem);
			inventory.setItem(53, plugin.itemManager.getNextPageItem());
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
	
	
	
	
	
	
	
	
	public Inventory getElvesCommunistEnderChestMenuInventory(Player player, int page) {
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
		
		for (int i = 0; i < page * 45; i++) {
			elfHeads.remove(0);
		}
		
		String inventoryName = "Elves Ender Chests";
		if (page != 0) {
			inventoryName = inventoryName + " " + page;
		}
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
		
		
		
		
		if (page != 0) {
			inventory.setItem(inventorySize - 2, plugin.itemManager.getPreviousPageItem());
		}
		
		if (elfHeads.size() > 45) {
			inventory.setItem(46, backItem);
			inventory.setItem(53, plugin.itemManager.getNextPageItem());
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
	
	
	
	
	
	
	
	
	
	
	public Inventory getInventorySnoopingInventoryMenuInventory(Player player, int page) {
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
		
		for (int i = 0; i < page * 45; i++) {
			playerHeads.remove(0);
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
		if (page != 0) {
			inventoryName = inventoryName + " " + page;
		}
		Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
		
		
		for (int i = 0; i < inventorySize; i++) {
			inventory.setItem(i, new ItemStack(Material.AIR));
		}
		
		
		for (int i = 0; i < playerHeads.size(); i++) {
			inventory.setItem(i, playerHeads.get(i));
			if (i > 44) {
				break;
			}
		}
		
		
		
		
		if (page != 0) {
			inventory.setItem(inventorySize - 2, plugin.itemManager.getPreviousPageItem());
		}
		
		if (playerHeads.size() > 45) {
			inventory.setItem(45, backItem);
			inventory.setItem(53, plugin.itemManager.getNextPageItem());
			//add menu to go to the next page of players, also, figure out how to get multiple pages of elves if for some reason theres a server with more than 45 players.
		} else if (playerHeads.size() > 36) {
			inventory.setItem(45, backItem);
		} else if (playerHeads.size() > 27) {
			inventory.setItem(36, backItem);
		} else if (playerHeads.size() > 18) {
			inventory.setItem(27, backItem);
		} else if (playerHeads.size() > 9) {
			inventory.setItem(18, backItem);
		} else {
			inventory.setItem(9, backItem);
		}
		return inventory;
	}
	
	
	
	
	
	
	
	
	
	public Inventory getInventorySnoopingEnderChestMenuInventory(Player player, int page) {
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
		
		for (int i = 0; i < page * 45; i++) {
			playerHeads.remove(0);
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
		if (page != 0) {
			inventoryName = inventoryName + " " + page;
		}
		Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
		
		
		for (int i = 0; i < inventorySize; i++) {
			inventory.setItem(i, new ItemStack(Material.AIR));
		}
		
		
		for (int i = 0; i < playerHeads.size(); i++) {
			inventory.setItem(i, playerHeads.get(i));
			if (i > 44) {
				break;
			}
		}
		
		
		
		
		if (page != 0) {
			inventory.setItem(inventorySize - 2, plugin.itemManager.getPreviousPageItem());
		}
		
		if (playerHeads.size() > 45) {
			inventory.setItem(45, backItem);
			inventory.setItem(53, plugin.itemManager.getNextPageItem());
			//add menu to go to the next page of players, also, figure out how to get multiple pages of elves if for some reason theres a server with more than 45 players.
		} else if (playerHeads.size() > 36) {
			inventory.setItem(45, backItem);
		} else if (playerHeads.size() > 27) {
			inventory.setItem(36, backItem);
		} else if (playerHeads.size() > 18) {
			inventory.setItem(27, backItem);
		} else if (playerHeads.size() > 9) {
			inventory.setItem(18, backItem);
		} else {
			inventory.setItem(9, backItem);
		}
		return inventory;
	}
	
	public Inventory getVotingMenu(int race) {
		return getVotingMenu(race, 0);
	}
	
	public Inventory getVotingMenu(int race, int page) {
		File leaderDataFile = new File(plugin.getDataFolder() + File.separator + "leaderData.yml");
		FileConfiguration leaderConfig = YamlConfiguration.loadConfiguration(leaderDataFile);
		String raceString = "men";
		if (race == 2) {
			raceString = "elf";
		}
		List<ItemStack> playerHeads = new ArrayList<ItemStack>();
		leaderConfig.getStringList(raceString + "Candidates").forEach(string -> {
			OfflinePlayer candidate = Bukkit.getOfflinePlayer(UUID.fromString(string));
			ItemStack tempPlayerHead = new ItemStack(Material.PLAYER_HEAD);
			SkullMeta tempPlayerHeadMeta = (SkullMeta)tempPlayerHead.getItemMeta();
			tempPlayerHeadMeta.setDisplayName(ChatColor.YELLOW + "Vote for " + candidate.getName());
			tempPlayerHeadMeta.setOwningPlayer(candidate);
			tempPlayerHead.setItemMeta(tempPlayerHeadMeta);
			
			playerHeads.add(tempPlayerHead);
		});
		
		
		for (int i = 0; i < page * 45; i++) {
			playerHeads.remove(0);
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
		

		String inventoryName = getRaceColor(race) + "Voting";
		if (page != 0) {
			inventoryName = inventoryName + " " + page;
		}
		Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
		
		
		for (int i = 0; i < inventorySize; i++) {
			inventory.setItem(i, new ItemStack(Material.AIR));
		}
		
		
		for (int i = 0; i < playerHeads.size(); i++) {
			inventory.setItem(i, playerHeads.get(i));
			if (i > 44) {
				break;
			}
		}
		
		
		if (page != 0) {
			inventory.setItem(inventorySize - 2, plugin.itemManager.getPreviousPageItem());
		}
		
		if (playerHeads.size() > 45) {
			inventory.setItem(45, backItem);
			inventory.setItem(53, plugin.itemManager.getNextPageItem());
			//add menu to go to the next page of players, also, figure out how to get multiple pages of elves if for some reason theres a server with more than 45 players.
		} else if (playerHeads.size() > 36) {
			inventory.setItem(45, backItem);
		} else if (playerHeads.size() > 27) {
			inventory.setItem(36, backItem);
		} else if (playerHeads.size() > 18) {
			inventory.setItem(27, backItem);
		} else if (playerHeads.size() > 9) {
			inventory.setItem(18, backItem);
		} else {
			inventory.setItem(9, backItem);
		}
		return inventory;
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
}
