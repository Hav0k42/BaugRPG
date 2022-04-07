package org.baugindustries.baugrpg.listeners.ChestMenuListeners;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;


public class GovernmentMenu implements Listener{
	private Main plugin;
	public GovernmentMenu(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player)) return;
		if (!(event.getClickedInventory() != null && event.getCurrentItem() != null)) return;
		if (!(event.getView().getTitle().equals(ChatColor.DARK_AQUA + "Kingdom Info") || event.getView().getTitle().equals(ChatColor.DARK_GREEN + "Commune Info") || event.getView().getTitle().equals(ChatColor.DARK_PURPLE + "Guild Info") || event.getView().getTitle().equals(ChatColor.DARK_RED + "Horde Info"))) return;
		if (!(event.getView().getTopInventory().equals(event.getClickedInventory())) && event.getCursor() == null) return;
		Player player = (Player)event.getWhoClicked();
		PersistentDataContainer data = player.getPersistentDataContainer();
		int race = data.get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER);

		File leaderDataFile = new File(plugin.getDataFolder() + File.separator + "leaderData.yml");
		FileConfiguration leaderConfig = YamlConfiguration.loadConfiguration(leaderDataFile);
		
		if (event.getCurrentItem().equals(plugin.itemManager.getBackItem())) {
			player.openInventory(plugin.inventoryManager.getBaugScrollMenuInventory(player));
		} else if (event.getCurrentItem().equals(plugin.itemManager.getVoteOnLeaderItem(race))) {
			String raceString = "men";
			if (race == 2) {
				raceString = "elf";
			}
			if (!leaderConfig.contains(raceString + "Candidates")) {
				player.closeInventory();
				player.sendMessage(ChatColor.RED + "There are no candidates to vote for.");
			} else {
				player.openInventory(plugin.inventoryManager.getVotingMenu(race));
			}
		} else if (event.getCurrentItem().equals(plugin.itemManager.getAppointKingItem())) {
			if (leaderConfig.contains("menElectionActive") && leaderConfig.getBoolean("menElectionActive")) {
				player.closeInventory();
				player.sendMessage(ChatColor.RED + "You've been overthrown, and cannot appoint a new king.");
			} else {
				player.openInventory(plugin.inventoryManager.getAppointKingMenu(player.getUniqueId(), 0));
			}
		} else if (event.getCurrentItem().equals(plugin.itemManager.getStepDownItem())) {
			
			player.openInventory(plugin.inventoryManager.getConfirmStepDownMenu());
			
		} else if (event.getCurrentItem().equals(plugin.itemManager.getDraftLawsItem(race))) {
			File draftLawCooldownFile = new File(plugin.getDataFolder() + File.separator + "draftLawCooldownData.yml");
			if (!draftLawCooldownFile.exists()) {
				try {
					draftLawCooldownFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			FileConfiguration draftLawCooldownConfig = YamlConfiguration.loadConfiguration(draftLawCooldownFile);
			
			if (draftLawCooldownConfig.contains(player.getUniqueId().toString()) && (86400000 + draftLawCooldownConfig.getLong(player.getUniqueId().toString())) > System.currentTimeMillis()) {
				
				int minutesToMillis = 60000;
				Long timeRemaining = (draftLawCooldownConfig.getLong(player.getUniqueId().toString()) + 86400000) - System.currentTimeMillis();
				String timeString = "";
				int timeValue;
				if (timeRemaining > minutesToMillis * 60) {
					//display in hours
					timeValue = (int)(timeRemaining / (minutesToMillis * 60));
					if (timeValue == 1) {
						timeString = (timeValue + " hour remaining");
					} else {
						timeString = (timeValue + " hours remaining");
					}
				} else if (timeRemaining > minutesToMillis) {
					//display in minutes
					timeValue = (int)(timeRemaining / minutesToMillis);
					if (timeValue == 1) {
						timeString = (timeValue + " minute remaining");
					} else {
						timeString = (timeValue + " minutes remaining");
					}
				} else {
					//display in seconds
					timeValue = (int)(timeRemaining / 1000);
					if (timeValue == 1) {
						timeString = (timeValue + " second remaining");
					} else {
						timeString = (timeValue + " seconds remaining");
					}
				}
				
				player.sendMessage(ChatColor.RED + "You already drafted a law today. " + timeString + " until you can draft another law.");
				player.closeInventory();
			} else {
				player.sendMessage(ChatColor.GREEN + "Draft your law.");
				player.closeInventory();
				plugin.draftLawEscape.add(player.getUniqueId());
				draftLawCooldownConfig.set(player.getUniqueId().toString(), System.currentTimeMillis());
				try {
					draftLawCooldownConfig.save(draftLawCooldownFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else if (event.getCurrentItem().equals(plugin.itemManager.getViewLawSuggestionsItem(race))) {
			String lawConfigString = (plugin.getRaceString(race)) + "LawSuggestions";
			if (leaderConfig.contains(lawConfigString) && leaderConfig.getStringList(lawConfigString).size() != 0) {
				String suggestionString = leaderConfig.getStringList(lawConfigString).get(0);
				TextComponent nextButton = new TextComponent("NEXT");
				nextButton.setColor(net.md_5.bungee.api.ChatColor.GREEN);
				nextButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[0] + "0"));
				nextButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the next suggestion.")));
				
				TextComponent backButton = new TextComponent("BACK       ");
				backButton.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
				backButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[1] + "0"));
				backButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the previous suggestion.")));
				
				TextComponent deleteButton = new TextComponent("DISCARD       ");
				deleteButton.setColor(net.md_5.bungee.api.ChatColor.RED);
				deleteButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[2] + "0"));
				deleteButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Discard the current suggestion.")));
				
				backButton.addExtra(deleteButton);
				backButton.addExtra(nextButton);
				backButton.addExtra("\n \n ");
				
				player.sendMessage("\n \n \n \n \n \n \n \n \n \n \n \n \n \n \n" + ChatColor.YELLOW + "Suggestion: " + suggestionString + "\n ");
				player.spigot().sendMessage(backButton);
				player.closeInventory();
			} else {
				player.sendMessage(ChatColor.RED + "There are currently no suggestions for laws.");
			}
		} else if (event.getCurrentItem().equals(plugin.itemManager.getViewLawsItem(race))) {
			String lawConfigString = (plugin.getRaceString(race)) + "Laws";
			if (leaderConfig.contains(lawConfigString) && leaderConfig.getStringList(lawConfigString).size() != 0) {
				
				String lawName = "";
				switch (race) {
					case 1:
						lawName = "Sanction ";
						break;
					case 2:
						lawName = "Decree ";
						break;
					case 3:
						lawName = "Law ";
						break;
					case 4:
						lawName = "Code ";
						break;
				}
				
				String suggestionString = leaderConfig.getStringList(lawConfigString).get(0);
				TextComponent nextButton = new TextComponent("       NEXT");
				nextButton.setColor(net.md_5.bungee.api.ChatColor.GREEN);
				nextButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[7] + "0"));
				nextButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the next " + lawName.toLowerCase())));
				
				TextComponent backButton = new TextComponent("       BACK");
				backButton.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
				backButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[8] + "0"));
				backButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the next " + lawName.toLowerCase())));
				
				backButton.addExtra(nextButton);
				backButton.addExtra("\n \n ");
				
				
				
				player.sendMessage("\n \n \n \n \n \n \n \n \n \n \n \n \n" + plugin.itemManager.getRaceColor(race) + "                                 " + lawName + "1" + "\n \n" + ChatColor.GOLD + lawName + ": " + suggestionString + "\n ");
				player.spigot().sendMessage(backButton);
				player.closeInventory();
			} else {
				
				String lawName = "";
				switch (race) {
					case 1:
						lawName = "human sanctions";
						break;
					case 2:
						lawName = "elven decrees";
						break;
					case 3:
						lawName = "dwarven laws";
						break;
					case 4:
						lawName = "orcish codes";
						break;
				}
				
				player.sendMessage(ChatColor.RED + "There are currently no " + lawName + ".");
			}
		} else if (event.getCurrentItem().equals(plugin.itemManager.getLeaderViewCrimesItem(race))) {
			String crimeConfigString = plugin.getRaceString(race) + "ReportedCrimes";
			
			if (leaderConfig.contains(crimeConfigString)) {
			
				ConfigurationSection crimes = leaderConfig.getConfigurationSection(crimeConfigString);
				List<String> unvotedCrimes = new ArrayList<String>();
				crimes.getKeys(false).forEach(crimeString -> {
					if (!crimes.getConfigurationSection(crimeString).contains(player.getUniqueId().toString())) {
						unvotedCrimes.add(crimeString);
					}
				});
				
				
				if (unvotedCrimes.size() != 0) {
					String crimeString = crimes.getConfigurationSection(unvotedCrimes.get(0)).getString("title");
					String defendant = Bukkit.getOfflinePlayer(UUID.fromString(crimes.getConfigurationSection(unvotedCrimes.get(0)).getString("defendant"))).getName();
					String plaintiff = Bukkit.getOfflinePlayer(UUID.fromString(crimes.getConfigurationSection(unvotedCrimes.get(0)).getString("plaintiff"))).getName();
					
					TextComponent nextButton = new TextComponent("NEXT");
					nextButton.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
					nextButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[13] + "0"));
					nextButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the next crime.")));
					
					TextComponent backButton = new TextComponent("BACK       ");
					backButton.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
					backButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[14] + "0"));
					backButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the previous crime.")));
		
					TextComponent passButton = new TextComponent("INNOCENT       ");
					passButton.setColor(net.md_5.bungee.api.ChatColor.GREEN);
					passButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[15] + "0"));
					passButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Declare this defendant innocent.")));
					
					TextComponent dontPassButton = new TextComponent("GUILTY       ");
					dontPassButton.setColor(net.md_5.bungee.api.ChatColor.RED);
					dontPassButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[16] + "0"));
					dontPassButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Declare this defendant guilty.")));
					
					backButton.addExtra(dontPassButton);
					backButton.addExtra(passButton);
					backButton.addExtra(nextButton);
					backButton.addExtra("\n \n ");
					
					player.sendMessage("\n \n \n \n \n \n \n \n \n \n \n \n \n" + ChatColor.GOLD + "DEFENDANT: " + defendant + ",    PLAINTIFF: " + plaintiff
					+ ChatColor.YELLOW + "\n \nCrime: " + crimeString + "\n ");
					player.spigot().sendMessage(backButton);
					player.closeInventory();
				} else {
					player.sendMessage(ChatColor.RED + "There are currently no crimes to judge.");
				}
			} else {
				player.sendMessage(ChatColor.RED + "There are currently no crimes to judge.");
			}
			
			
		} else if (event.getCurrentItem().equals(plugin.itemManager.getTaxDwarvesItem())) {
			player.closeInventory();
			player.sendMessage(ChatColor.GOLD + "What percent do you want to tax your citizens weekly? Enter a number between 0 and 5.");
			plugin.taxDwarvesEscape = player.getUniqueId();
		} else if (event.getCurrentItem().equals(plugin.itemManager.getTaxDwarvesWeeklyItem())) {
			int week = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
			if (leaderConfig.contains("taxedWeeks")) {
				List<Integer> taxedWeeks = leaderConfig.getIntegerList("taxedWeeks");
				if (week == 1 && taxedWeeks.get(taxedWeeks.size()) > 1) {
					//It is now a new year.
					
					taxedWeeks = new ArrayList<Integer>();
				}
				
				
				if (taxedWeeks.contains(week)) {
					//already taxed
					player.sendMessage(ChatColor.RED + "Taxes have already been collected this week.");
				} else {
					//not taxed yet
					
					File bankFile = new File(plugin.getDataFolder() + File.separator + "bank.yml");
				 	FileConfiguration bankConfig = YamlConfiguration.loadConfiguration(bankFile);
					
				 	double totalTaxes = 0;
				 	double taxPercent = leaderConfig.getDouble("taxAmount");
				 	
					for (OfflinePlayer dwarf : plugin.getAllOfflineDwarves()) {
						if (!dwarf.getUniqueId().equals(player.getUniqueId())) {
							if (bankConfig.contains(dwarf.getUniqueId().toString())) {
								double dwarfBankBal = bankConfig.getInt(dwarf.getUniqueId().toString());
								totalTaxes += taxPercent * dwarfBankBal;
								dwarfBankBal *= (1-taxPercent);
								if (dwarf.isOnline()) {
									((Player)dwarf).sendMessage(ChatColor.GOLD + "Your emperor has collected the weekly tax. Your new bank balance is: " + (int)dwarfBankBal + ".");
								}
								bankConfig.set(dwarf.getUniqueId().toString(), (int)dwarfBankBal);
							} else {
								bankConfig.set(dwarf.getUniqueId().toString(), 0);
							}
						}
					}
					
					bankConfig.set(player.getUniqueId().toString(), (int)(bankConfig.getInt(player.getUniqueId().toString()) + totalTaxes));
					
					taxedWeeks.add(week);
					leaderConfig.set("taxedWeeks", taxedWeeks);
					
					try {
						bankConfig.save(bankFile);
						leaderConfig.save(bankFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					player.closeInventory();
					player.sendMessage(ChatColor.GOLD + "You have collected " + (int)totalTaxes + " dwarven gold in taxes.");
				}
				
			} else {
				//not taxed yet
				
				File bankFile = new File(plugin.getDataFolder() + File.separator + "bank.yml");
			 	FileConfiguration bankConfig = YamlConfiguration.loadConfiguration(bankFile);
				
			 	double totalTaxes = 0;
			 	double taxPercent = leaderConfig.getDouble("taxAmount");
			 	
				for (OfflinePlayer dwarf : plugin.getAllOfflineDwarves()) {
					if (!dwarf.getUniqueId().equals(player.getUniqueId())) {
						if (bankConfig.contains(dwarf.getUniqueId().toString())) {
							double dwarfBankBal = bankConfig.getInt(dwarf.getUniqueId().toString());
							totalTaxes += taxPercent * dwarfBankBal;
							dwarfBankBal *= (1-taxPercent);
							if (dwarf.isOnline()) {
								((Player)dwarf).sendMessage(ChatColor.GOLD + "Your emperor has collected the weekly tax. Your new bank balance is: " + (int)dwarfBankBal + ".");
							}
							bankConfig.set(dwarf.getUniqueId().toString(), (int)dwarfBankBal);
						} else {
							bankConfig.set(dwarf.getUniqueId().toString(), 0);
						}
					}
				}
				
				bankConfig.set(player.getUniqueId().toString(), (int)(bankConfig.getInt(player.getUniqueId().toString()) + totalTaxes));
				
				List<Integer> taxedWeeks = new ArrayList<Integer>();
				taxedWeeks.add(week);
				leaderConfig.set("taxedWeeks", taxedWeeks);
				
				try {
					bankConfig.save(bankFile);
					leaderConfig.save(bankFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				player.closeInventory();
				player.sendMessage(ChatColor.GOLD + "You have collected " + (int)totalTaxes + " dwarven gold in taxes.");
			}
		} else if (event.getCurrentItem().equals(plugin.itemManager.getOverthrowKingItem())) {
			player.closeInventory();
			player.sendMessage(ChatColor.YELLOW + "You've voted to overthrow your King.");
			
			if (leaderConfig.contains("overthrowKingVotes")) {
				List<String> overthrowVotes = leaderConfig.getStringList("overthrowKingVotes");
				overthrowVotes.add(player.getUniqueId().toString());
				leaderConfig.set("overthrowKingVotes", overthrowVotes);
			} else {
				List<String> overthrowVotes = new ArrayList<String>();
				overthrowVotes.add(player.getUniqueId().toString());
				leaderConfig.set("overthrowKingVotes", overthrowVotes);
			}
			
			if (leaderConfig.getStringList("overthrowKingVotes").size() > plugin.getAllEligibleMen().size() - 1) {
				leaderConfig.set("overthrowKingVotes", null);
				leaderConfig.set("menElectionActive", true);
				leaderConfig.set("menElectionStartTime", System.currentTimeMillis());
				
				for (OfflinePlayer man : plugin.getAllOfflineMen()) {
					if (man.isOnline()) {
						if (man.getUniqueId().equals(leaderConfig.get("menLeaderUUID"))) {
							((Player)man).sendMessage(ChatColor.YELLOW + "You've been overthrown. You have 36 hours left as King. Use them well.");
						} else {
							((Player)man).sendMessage(ChatColor.YELLOW + "Your King has been overthrown. Vote for a new leader using /bs.");
						}
					}
				}
				
			}
			
			
			
			try {
				leaderConfig.save(leaderDataFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (event.getCurrentItem().equals(plugin.itemManager.getImpeachSecretaryItem())) {
			player.closeInventory();
			player.sendMessage(ChatColor.YELLOW + "You've voted to impeach your current secretary.");
			
			if (leaderConfig.contains("impeachSecretaryVotes")) {
				List<String> impeachVotes = leaderConfig.getStringList("impeachSecretaryVotes");
				impeachVotes.add(player.getUniqueId().toString());
				leaderConfig.set("impeachSecretaryVotes", impeachVotes);
			} else {
				List<String> impeachVotes = new ArrayList<String>();
				impeachVotes.add(player.getUniqueId().toString());
				leaderConfig.set("impeachSecretaryVotes", impeachVotes);
			}
			
			if (leaderConfig.getStringList("impeachSecretaryVotes").size() > plugin.getAllEligibleElves().size() / 2) {
				leaderConfig.set("impeachSecretaryVotes", null);
				leaderConfig.set("elfElectionActive", true);
				leaderConfig.set("elfElectionStartTime", System.currentTimeMillis());
				
				for (OfflinePlayer elf : plugin.getAllOfflineElves()) {
					if (elf.isOnline()) {
						if (elf.getUniqueId().equals(leaderConfig.get("elfLeaderUUID"))) {
							((Player)elf).sendMessage(ChatColor.YELLOW + "You've been impeached. You have 36 hours left as Secretary. Use them well.");
						} else {
							((Player)elf).sendMessage(ChatColor.YELLOW + "Your secretary has been impeached. Vote for a new leader using /bs.");
						}
					}
				}
				
			}
			
			
			
			try {
				leaderConfig.save(leaderDataFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (event.getCurrentItem().equals(plugin.itemManager.getSubmitLawSuggestionItem(race))) {
			File lawSuggestionCooldownFile = new File(plugin.getDataFolder() + File.separator + "lawSuggestionCooldownData.yml");
			if (!lawSuggestionCooldownFile.exists()) {
				try {
					lawSuggestionCooldownFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			FileConfiguration lawSuggestionCooldownConfig = YamlConfiguration.loadConfiguration(lawSuggestionCooldownFile);
			
			if (lawSuggestionCooldownConfig.contains(player.getUniqueId().toString()) && (86400000 + lawSuggestionCooldownConfig.getLong(player.getUniqueId().toString())) > System.currentTimeMillis()) {
				
				int minutesToMillis = 60000;
				Long timeRemaining = (lawSuggestionCooldownConfig.getLong(player.getUniqueId().toString()) + 86400000) - System.currentTimeMillis();
				String timeString = "";
				int timeValue;
				if (timeRemaining > minutesToMillis * 60) {
					//display in hours
					timeValue = (int)(timeRemaining / (minutesToMillis * 60));
					if (timeValue == 1) {
						timeString = (timeValue + " hour remaining");
					} else {
						timeString = (timeValue + " hours remaining");
					}
				} else if (timeRemaining > minutesToMillis) {
					//display in minutes
					timeValue = (int)(timeRemaining / minutesToMillis);
					if (timeValue == 1) {
						timeString = (timeValue + " minute remaining");
					} else {
						timeString = (timeValue + " minutes remaining");
					}
				} else {
					//display in seconds
					timeValue = (int)(timeRemaining / 1000);
					if (timeValue == 1) {
						timeString = (timeValue + " second remaining");
					} else {
						timeString = (timeValue + " seconds remaining");
					}
				}
				
				player.sendMessage(ChatColor.RED + "You already submitted a suggestion today. " + timeString + " until you can submit another suggestion.");
				player.closeInventory();
			} else {
				player.sendMessage(ChatColor.GREEN + "Enter your law suggestion.");
				player.closeInventory();
				plugin.lawSuggestionEscape.add(player.getUniqueId());
				lawSuggestionCooldownConfig.set(player.getUniqueId().toString(), System.currentTimeMillis());
				try {
					lawSuggestionCooldownConfig.save(lawSuggestionCooldownFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else if (event.getCurrentItem().equals(plugin.itemManager.getVoteOnLawsItem())) {
			String lawConfigString = "elfDraftLaws";
			
			if (leaderConfig.contains(lawConfigString)) {
			
				ConfigurationSection bills = leaderConfig.getConfigurationSection(lawConfigString);
				List<String> unvotedBills = new ArrayList<String>();
				bills.getKeys(false).forEach(billString -> {
					if (!bills.getConfigurationSection(billString).contains(player.getUniqueId().toString())) {
						unvotedBills.add(billString);
					}
				});
				
				
				if (unvotedBills.size() != 0) {
					String billString = bills.getConfigurationSection(unvotedBills.get(0)).getString("title");
					
					TextComponent nextButton = new TextComponent("NEXT");
					nextButton.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
					nextButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[3] + "0"));
					nextButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the next bill.")));
					
					TextComponent backButton = new TextComponent("BACK       ");
					backButton.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
					backButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[4] + "0"));
					backButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the previous bill.")));
		
					TextComponent passButton = new TextComponent("PASS       ");
					passButton.setColor(net.md_5.bungee.api.ChatColor.GREEN);
					passButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[5] + "0"));
					passButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Vote to pass this law.")));
					
					TextComponent dontPassButton = new TextComponent("VETO       ");
					dontPassButton.setColor(net.md_5.bungee.api.ChatColor.RED);
					dontPassButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[6] + "0"));
					dontPassButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Vote to not pass this law.")));
					
					backButton.addExtra(dontPassButton);
					backButton.addExtra(passButton);
					backButton.addExtra(nextButton);
					backButton.addExtra("\n \n ");
					
					player.sendMessage("\n \n \n \n \n \n \n \n \n \n \n \n \n \n \n" + ChatColor.YELLOW + "Law: " + billString + "\n ");
					player.spigot().sendMessage(backButton);
					player.closeInventory();
				} else {
					player.sendMessage(ChatColor.RED + "There are currently no laws to vote on.");
				}
			} else {
				player.sendMessage(ChatColor.RED + "There are currently no laws to vote on.");
			}
			
		} else if (event.getCurrentItem().equals(plugin.itemManager.getLeaderDecidePunishmentItem(race))) {
			String crimeConfigString = plugin.getRaceString(race) + "GuiltyCrimes";
			
			if (leaderConfig.contains(crimeConfigString)) {
				
				ConfigurationSection crimes = leaderConfig.getConfigurationSection(crimeConfigString);
				List<String> unvotedCrimes = new ArrayList<String>();
				crimes.getKeys(false).forEach(crimeString -> {
					unvotedCrimes.add(crimeString);
				});
				if (unvotedCrimes.size() != 0) {
					String crimeString = crimes.getConfigurationSection(unvotedCrimes.get(0)).getString("title");
					String defendant = Bukkit.getOfflinePlayer(UUID.fromString(crimes.getConfigurationSection(unvotedCrimes.get(0)).getString("defendant"))).getName();
					String plaintiff = Bukkit.getOfflinePlayer(UUID.fromString(crimes.getConfigurationSection(unvotedCrimes.get(0)).getString("plaintiff"))).getName();
					
					player.sendMessage("\n \n \n \n \n \n \n \n \n \n \n \n \n" + ChatColor.GOLD + "DEFENDANT: " + defendant + ",    PLAINTIFF: " + plaintiff
							+ ChatColor.YELLOW + "\n \nCrime: " + crimeString + "\n ");
					
					String infoString = "";
					switch (race) {
					case 1:
						infoString = ChatColor.GOLD + "\nHow many claim blocks do you want to revoke from " + defendant + "and award to" + plaintiff + "?";
						infoString = infoString + ChatColor.GREEN + "\nEnter a number between 1-200. Enter NEXT to view next dispute, BACK to go back, or EXIT to return to the chat.";
						break;
					case 3:
						infoString = ChatColor.GOLD + "\nWhat percentage of " + defendant + "'s wealth do you want to award " + plaintiff + "?";
						infoString = infoString + ChatColor.GREEN + "\nEnter a number between 0-5. Enter NEXT to view next dispute, BACK to go back, or EXIT to return to the chat.";
						break;
					case 4:
						infoString = ChatColor.GOLD + "\nHow many executions do you want to award " + plaintiff + "?";
						infoString = infoString + ChatColor.GREEN + "\nEnter a number between 1-5. Enter NEXT to view next dispute, BACK to go back, or EXIT to return to the chat.";
						break;
					}
					player.sendMessage(infoString);
					plugin.leaderPunishEscape.put(player.getUniqueId(), 0);
					player.closeInventory();
				} else {
					player.sendMessage(ChatColor.RED + "There are currently no criminals to punish.");
				}
			} else {
				player.sendMessage(ChatColor.RED + "There are currently no criminals to punish.");
			}
		} else if (event.getCurrentItem().equals(plugin.itemManager.getDecidePunishmentItem())) {
			String crimeConfigString = "elfGuiltyCrimes";
			
			if (leaderConfig.contains(crimeConfigString)) {
				
				ConfigurationSection crimes = leaderConfig.getConfigurationSection(crimeConfigString);
				List<String> unvotedCrimes = new ArrayList<String>();
				crimes.getKeys(false).forEach(crimeString -> {
					if (!crimes.getConfigurationSection(crimeString).contains(player.getUniqueId().toString())) {
						unvotedCrimes.add(crimeString);
					}
				});
				if (unvotedCrimes.size() != 0) {
					String crimeString = crimes.getConfigurationSection(unvotedCrimes.get(0)).getString("title");
					String defendant = Bukkit.getOfflinePlayer(UUID.fromString(crimes.getConfigurationSection(unvotedCrimes.get(0)).getString("defendant"))).getName();
					String plaintiff = Bukkit.getOfflinePlayer(UUID.fromString(crimes.getConfigurationSection(unvotedCrimes.get(0)).getString("plaintiff"))).getName();
					
					player.sendMessage("\n \n \n \n \n \n \n \n \n \n \n \n \n" + ChatColor.GOLD + "DEFENDANT: " + defendant + ",    PLAINTIFF: " + plaintiff
							+ ChatColor.YELLOW + "\n \nCrime: " + crimeString + "\n ");
					
					String infoString = ChatColor.GOLD + "\nHow many hours do you want to restrict " + defendant + "'s access to communal resources?" + ChatColor.GREEN + "\nEnter a number between 1-48. Enter NEXT to view next dispute, BACK to go back, or EXIT to return to the chat.";
					
					
					player.sendMessage(infoString);
					plugin.elfPunishEscape.put(player.getUniqueId(), 0);
					player.closeInventory();
				} else {
					player.sendMessage(ChatColor.RED + "There are currently no criminals to punish.");
				}
			} else {
				player.sendMessage(ChatColor.RED + "There are currently no criminals to punish.");
			}
		} else if (event.getCurrentItem().equals(plugin.itemManager.getExecutionItem())) {
			//TODO
			
			if (leaderConfig.contains("executions")) {
				ConfigurationSection executions = leaderConfig.getConfigurationSection("executions");
				if (executions.contains(player.getUniqueId().toString())) {
					ConfigurationSection playerExecutions = executions.getConfigurationSection(player.getUniqueId().toString());
					if (playerExecutions.getKeys(false).size() != 0) {
						String[] executionKeys = playerExecutions.getKeys(false).toArray(new String[playerExecutions.getKeys(false).size()]);
						boolean online = false;
						for (String victimUUID : executionKeys) {
							OfflinePlayer victim = Bukkit.getOfflinePlayer(UUID.fromString(victimUUID));
							if (victim.isOnline()) {
								online = true;
							}
						}
						if (online) {
							player.openInventory(plugin.inventoryManager.getExecutionMenu(player.getUniqueId()));
						} else {
							player.sendMessage(ChatColor.RED + "None of the players you have executions for are online.");
							player.closeInventory();
							event.setCancelled(true);
							return;
						}
					} else {
						player.sendMessage(ChatColor.RED + "You do not have any executions to redeem.");
						player.closeInventory();
						event.setCancelled(true);
						return;
					}
				} else {
					player.sendMessage(ChatColor.RED + "You do not have any executions to redeem.");
					player.closeInventory();
					event.setCancelled(true);
					return;
				}
			} else {
				leaderConfig.createSection("executions");
				player.sendMessage(ChatColor.RED + "You do not have any executions to redeem.");
				player.closeInventory();
				event.setCancelled(true);
				return;
			}
		} else if (event.getCurrentItem().equals(plugin.itemManager.getViewReportedCrimesItem(race))) {
			String crimeConfigString = plugin.getRaceString(race) + "ReportedCrimes";
			
			if (leaderConfig.contains(crimeConfigString)) {
			
				ConfigurationSection crimes = leaderConfig.getConfigurationSection(crimeConfigString);
				List<String> unvotedCrimes = new ArrayList<String>();
				crimes.getKeys(false).forEach(crimeString -> {
					if (!crimes.getConfigurationSection(crimeString).contains(player.getUniqueId().toString())) {
						unvotedCrimes.add(crimeString);
					}
				});
				
				
				if (unvotedCrimes.size() != 0) {
					String crimeString = crimes.getConfigurationSection(unvotedCrimes.get(0)).getString("title");
					String defendant = Bukkit.getOfflinePlayer(UUID.fromString(crimes.getConfigurationSection(unvotedCrimes.get(0)).getString("defendant"))).getName();
					String plaintiff = Bukkit.getOfflinePlayer(UUID.fromString(crimes.getConfigurationSection(unvotedCrimes.get(0)).getString("plaintiff"))).getName();
					
					TextComponent nextButton = new TextComponent("NEXT");
					nextButton.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
					nextButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[9] + "0"));
					nextButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the next crime.")));
					
					TextComponent backButton = new TextComponent("BACK       ");
					backButton.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
					backButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[10] + "0"));
					backButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the previous crime.")));
					
					TextComponent passButton = new TextComponent("INNOCENT       ");
					passButton.setColor(net.md_5.bungee.api.ChatColor.GREEN);
					passButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[11] + "0"));
					passButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Declare this defendant innocent.")));
					
					TextComponent dontPassButton = new TextComponent("GUILTY       ");
					dontPassButton.setColor(net.md_5.bungee.api.ChatColor.RED);
					dontPassButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[12] + "0"));
					dontPassButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Declare this defendant guilty.")));
					
					backButton.addExtra(dontPassButton);
					backButton.addExtra(passButton);
					backButton.addExtra(nextButton);
					backButton.addExtra("\n \n ");
					
					player.sendMessage("\n \n \n \n \n \n \n \n \n \n \n \n \n" + ChatColor.GOLD + "DEFENDANT: " + defendant + ",    PLAINTIFF: " + plaintiff
					+ ChatColor.YELLOW + "\n \nCrime: " + crimeString + "\n ");
					player.spigot().sendMessage(backButton);
					player.closeInventory();
				} else {
					player.sendMessage(ChatColor.RED + "There are currently no crimes to vote on.");
				}
			} else {
				player.sendMessage(ChatColor.RED + "There are currently no crimes to vote on.");
			}
			
		} else if (event.getCurrentItem().equals(plugin.itemManager.getReportCrimeItem(race))) {
			File reportCrimeCooldownFile = new File(plugin.getDataFolder() + File.separator + "reportCrimeCooldownData.yml");
			if (!reportCrimeCooldownFile.exists()) {
				try {
					reportCrimeCooldownFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			FileConfiguration reportCrimeCooldownConfig = YamlConfiguration.loadConfiguration(reportCrimeCooldownFile);
			
			if (reportCrimeCooldownConfig.contains(player.getUniqueId().toString()) && (86400000 + reportCrimeCooldownConfig.getLong(player.getUniqueId().toString())) > System.currentTimeMillis()) {
				
				int minutesToMillis = 60000;
				Long timeRemaining = (reportCrimeCooldownConfig.getLong(player.getUniqueId().toString()) + 86400000) - System.currentTimeMillis();
				String timeString = "";
				int timeValue;
				if (timeRemaining > minutesToMillis * 60) {
					//display in hours
					timeValue = (int)(timeRemaining / (minutesToMillis * 60));
					if (timeValue == 1) {
						timeString = (timeValue + " hour remaining");
					} else {
						timeString = (timeValue + " hours remaining");
					}
				} else if (timeRemaining > minutesToMillis) {
					//display in minutes
					timeValue = (int)(timeRemaining / minutesToMillis);
					if (timeValue == 1) {
						timeString = (timeValue + " minute remaining");
					} else {
						timeString = (timeValue + " minutes remaining");
					}
				} else {
					//display in seconds
					timeValue = (int)(timeRemaining / 1000);
					if (timeValue == 1) {
						timeString = (timeValue + " second remaining");
					} else {
						timeString = (timeValue + " seconds remaining");
					}
				}
				
				player.sendMessage(ChatColor.RED + "You already reported a crime today. " + timeString + " until you can report another crime.");
				player.closeInventory();
			} else {
				player.sendMessage(ChatColor.GREEN + "Report your crime.");
				player.closeInventory();
				plugin.reportCrimeEscape.put(player.getUniqueId(), 0);
			}
		} else if (event.getCurrentItem().equals(plugin.itemManager.getNominateSelfItem(race))) {
			String raceString = "men";
			if (race == 2) {
				raceString = "elf";
			}
			
			if (!leaderConfig.contains(raceString + "ElectionStartTime")) {
				leaderConfig.set(raceString + "ElectionStartTime", System.currentTimeMillis());
			}
			
			if (leaderConfig.contains(raceString + "Candidates")) {
				List<String> candidates = leaderConfig.getStringList(raceString + "Candidates");
				if (!candidates.contains(player.getUniqueId().toString())) {
					candidates.add(player.getUniqueId().toString());
					leaderConfig.set(raceString + "Candidates", candidates);
				}
			} else {
				List<String> candidates = new ArrayList<String>();
				candidates.add(player.getUniqueId().toString());
				leaderConfig.set(raceString + "Candidates", candidates);
			}
		}
		try {
			leaderConfig.save(leaderDataFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		event.setCancelled(true);
	}
}
