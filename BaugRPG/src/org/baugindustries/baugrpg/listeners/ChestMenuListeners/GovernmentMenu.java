package org.baugindustries.baugrpg.listeners.ChestMenuListeners;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;


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
			//TODO
		} else if (event.getCurrentItem().equals(plugin.itemManager.getViewLawSuggestionsItem(race))) {
			//TODO
		} else if (event.getCurrentItem().equals(plugin.itemManager.getViewLawsItem(race))) {
			//TODO
		} else if (event.getCurrentItem().equals(plugin.itemManager.getLeaderViewCrimesItem(race))) {
			//TODO
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
			//TODO
		} else if (event.getCurrentItem().equals(plugin.itemManager.getViewReportedCrimesItem(race))) {
			//TODO
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
				plugin.reportCrimeEscape.add(player.getUniqueId());
				reportCrimeCooldownConfig.set(player.getUniqueId().toString(), System.currentTimeMillis());
				try {
					reportCrimeCooldownConfig.save(reportCrimeCooldownFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		event.setCancelled(true);
	}
}
