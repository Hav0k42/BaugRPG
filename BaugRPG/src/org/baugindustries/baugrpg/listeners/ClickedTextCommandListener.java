package org.baugindustries.baugrpg.listeners;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class ClickedTextCommandListener implements Listener {
	private Main plugin;
	
	public ClickedTextCommandListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onSendClickedCommand(PlayerCommandPreprocessEvent event) {
		
		if (event.getMessage().length() < 151) return;
		if (!plugin.isInteger(event.getMessage().substring(151, event.getMessage().length()))) return;
		
		int index = Integer.parseInt(event.getMessage().substring(151, event.getMessage().length()));
		
		Player player = event.getPlayer();
		PersistentDataContainer data = player.getPersistentDataContainer();
		int race = data.get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER);

		File leaderDataFile = new File(plugin.getDataFolder() + File.separator + "leaderData.yml");
		FileConfiguration leaderConfig = YamlConfiguration.loadConfiguration(leaderDataFile);
		
		if (event.getMessage().substring(0, 151).equals(plugin.commandStrings[0])) {//View next law suggestion
			
			
			
			String lawConfigString = (plugin.getRaceString(race)) + "LawSuggestions";
			if (leaderConfig.contains(lawConfigString) && leaderConfig.getStringList(lawConfigString).size() != 0) {
				
				index++;
				if (index >= leaderConfig.getStringList(lawConfigString).size()) {
					index = 0;
				}
				
				String suggestionString = leaderConfig.getStringList(lawConfigString).get(index);
				
				
				
				TextComponent nextButton = new TextComponent("NEXT");
				nextButton.setColor(net.md_5.bungee.api.ChatColor.GREEN);
				nextButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[0] + index));
				nextButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the next suggestion.")));
				
				TextComponent backButton = new TextComponent("BACK       ");
				backButton.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
				backButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[1] + index));
				backButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the previous suggestion.")));
				
				TextComponent deleteButton = new TextComponent("DISCARD       ");
				deleteButton.setColor(net.md_5.bungee.api.ChatColor.RED);
				deleteButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[2] + index));
				deleteButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Discard the current suggestion.")));
				
				backButton.addExtra(deleteButton);
				backButton.addExtra(nextButton);
				backButton.addExtra("\n \n ");
				
				player.sendMessage("\n \n \n \n \n \n \n \n \n \n \n \n \n \n \n" + ChatColor.YELLOW + "Suggestion: " + suggestionString + "\n ");
				player.spigot().sendMessage(backButton);
			} else {
				player.sendMessage(ChatColor.RED + "There are currently no suggestions for laws.");
			}
			
			
			event.setCancelled(true);
		} else if (event.getMessage().substring(0, 151).equals(plugin.commandStrings[1])) {//View previous law suggestion
			
			String lawConfigString = (plugin.getRaceString(race)) + "LawSuggestions";
			if (leaderConfig.contains(lawConfigString) && leaderConfig.getStringList(lawConfigString).size() != 0) {
				
				index--;
				if (index < 0) {
					index = leaderConfig.getStringList(lawConfigString).size() - 1;
				}
				
				String suggestionString = leaderConfig.getStringList(lawConfigString).get(index);
				
				
				
				TextComponent nextButton = new TextComponent("NEXT");
				nextButton.setColor(net.md_5.bungee.api.ChatColor.GREEN);
				nextButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[0] + index));
				nextButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the next suggestion.")));
				
				TextComponent backButton = new TextComponent("BACK       ");
				backButton.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
				backButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[1] + index));
				backButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the previous suggestion.")));
				
				TextComponent deleteButton = new TextComponent("DISCARD       ");
				deleteButton.setColor(net.md_5.bungee.api.ChatColor.RED);
				deleteButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[2] + index));
				deleteButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Discard the current suggestion.")));
				
				backButton.addExtra(deleteButton);
				backButton.addExtra(nextButton);
				backButton.addExtra("\n \n ");
				
				player.sendMessage("\n \n \n \n \n \n \n \n \n \n \n \n \n \n \n" + ChatColor.YELLOW + "Suggestion: " + suggestionString + "\n ");
				player.spigot().sendMessage(backButton);
			} else {
				player.sendMessage(ChatColor.RED + "There are currently no suggestions for laws.");
			}
			
			event.setCancelled(true);
		} else if (event.getMessage().substring(0, 151).equals(plugin.commandStrings[2])) {//Delete current law suggestion
			
			
			
			String lawConfigString = (plugin.getRaceString(race)) + "LawSuggestions";
			
			List<String> tempList = leaderConfig.getStringList(lawConfigString);
			if (tempList.size() != 0) {
				tempList.remove(index);

				leaderConfig.set(lawConfigString, tempList);
				
				try {
					leaderConfig.save(leaderDataFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if (index >= leaderConfig.getStringList(lawConfigString).size()) {
				index = 0;
			}
			if (leaderConfig.contains(lawConfigString) && leaderConfig.getStringList(lawConfigString).size() != 0) {
				
				
				String suggestionString = leaderConfig.getStringList(lawConfigString).get(index);
				
				
				
				TextComponent nextButton = new TextComponent("NEXT");
				nextButton.setColor(net.md_5.bungee.api.ChatColor.GREEN);
				nextButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[0] + index));
				nextButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the next suggestion.")));
				
				TextComponent backButton = new TextComponent("BACK       ");
				backButton.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
				backButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[1] + index));
				backButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the previous suggestion.")));
				
				TextComponent deleteButton = new TextComponent("DISCARD       ");
				deleteButton.setColor(net.md_5.bungee.api.ChatColor.RED);
				deleteButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[2] + index));
				deleteButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Discard the current suggestion.")));
				
				backButton.addExtra(deleteButton);
				backButton.addExtra(nextButton);
				backButton.addExtra("\n \n ");
				
				player.sendMessage("\n \n \n \n \n \n \n \n \n \n \n \n \n \n \n" + ChatColor.YELLOW + "Suggestion: " + suggestionString + "\n ");
				player.spigot().sendMessage(backButton);
			} else {
				player.sendMessage(ChatColor.RED + "There are currently no suggestions for laws.");
			}
			
			
			event.setCancelled(true);
			
		} else if (event.getMessage().substring(0, 151).equals(plugin.commandStrings[3])) {//NEXT elven bill

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
					
					index++;
					if (index >= unvotedBills.size()) {
						index = 0;
					}

					String billString = bills.getConfigurationSection(unvotedBills.get(index)).getString("title");
					
					TextComponent nextButton = new TextComponent("NEXT");
					nextButton.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
					nextButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[3] + index));
					nextButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the next bill.")));
					
					TextComponent backButton = new TextComponent("BACK       ");
					backButton.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
					backButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[4] + index));
					backButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the previous bill.")));
		
					TextComponent passButton = new TextComponent("PASS       ");
					passButton.setColor(net.md_5.bungee.api.ChatColor.GREEN);
					passButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[5] + index));
					passButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Vote to pass this law.")));
					
					TextComponent dontPassButton = new TextComponent("VETO       ");
					dontPassButton.setColor(net.md_5.bungee.api.ChatColor.RED);
					dontPassButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[6] + index));
					dontPassButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Vote to not pass this law.")));
					
					backButton.addExtra(dontPassButton);
					backButton.addExtra(passButton);
					backButton.addExtra(nextButton);
					backButton.addExtra("\n \n ");
					
					player.sendMessage("\n \n \n \n \n \n \n \n \n \n \n \n \n \n \n" + ChatColor.YELLOW + "Law: " + billString + "\n ");
					player.spigot().sendMessage(backButton);
					
				} else {
					player.sendMessage(ChatColor.RED + "There are currently no laws to vote on.");
				}
			} else {
				player.sendMessage(ChatColor.RED + "There are currently no laws to vote on.");
			}
			event.setCancelled(true);
		} else if (event.getMessage().substring(0, 151).equals(plugin.commandStrings[4])) {//BACK to the last elven bill

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
					
					index--;
					if (index < 0) {
						index = unvotedBills.size() - 1;
					}
					
					String billString = bills.getConfigurationSection(unvotedBills.get(index)).getString("title");
					
					TextComponent nextButton = new TextComponent("NEXT");
					nextButton.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
					nextButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[3] + index));
					nextButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the next bill.")));
					
					TextComponent backButton = new TextComponent("BACK       ");
					backButton.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
					backButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[4] + index));
					backButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the previous bill.")));
		
					TextComponent passButton = new TextComponent("PASS       ");
					passButton.setColor(net.md_5.bungee.api.ChatColor.GREEN);
					passButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[5] + index));
					passButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Vote to pass this law.")));
					
					TextComponent dontPassButton = new TextComponent("VETO       ");
					dontPassButton.setColor(net.md_5.bungee.api.ChatColor.RED);
					dontPassButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[6] + index));
					dontPassButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Vote to not pass this law.")));
					
					backButton.addExtra(dontPassButton);
					backButton.addExtra(passButton);
					backButton.addExtra(nextButton);
					backButton.addExtra("\n \n ");
					
					player.sendMessage("\n \n \n \n \n \n \n \n \n \n \n \n \n \n \n" + ChatColor.YELLOW + "Law: " + billString + "\n ");
					player.spigot().sendMessage(backButton);
					
				} else {
					player.sendMessage(ChatColor.RED + "There are currently no laws to vote on.");
				}
			} else {
				player.sendMessage(ChatColor.RED + "There are currently no laws to vote on.");
			}
			event.setCancelled(true);
		} else if (event.getMessage().substring(0, 151).equals(plugin.commandStrings[5])) {//PASS the elven bill

			String lawConfigString = "elfDraftLaws";
			
			if (leaderConfig.contains(lawConfigString)) {
			
				ConfigurationSection bills = leaderConfig.getConfigurationSection(lawConfigString);
				
				if (bills.getKeys(false).size() == 0) {
					player.sendMessage(ChatColor.RED + "There are currently no laws to vote on.");
					return;
				}
				
				ConfigurationSection currentBill = bills.getConfigurationSection(bills.getKeys(false).toArray(new String[bills.getKeys(false).size()])[index]);//This is so many fucking steps. omg
				currentBill.set(player.getUniqueId().toString(), "PASS");
				
				int passCount = 0;
				int vetoCount = 0;
				
				String[] keys = currentBill.getKeys(false).toArray(new String[currentBill.getKeys(false).size()]);
				
				for (String key : keys) {
					if (currentBill.getString(key).equals("PASS")) {
						passCount++;
					} else if (currentBill.getString(key).equals("VETO")) {
						vetoCount++;
					}
				}
				
				if (passCount > vetoCount) {
					if (passCount > plugin.getAllEligibleElves().size() - 1) {
						if (!leaderConfig.contains("elfLaws")) {
							List<String> suggestionList = new ArrayList<String>();
							suggestionList.add(currentBill.getString("title"));
							leaderConfig.set("elfLaws", suggestionList);
						} else {
							List<String> suggestionList = leaderConfig.getStringList("elfLaws");
							suggestionList.add(currentBill.getString("title"));
							leaderConfig.set("elfLaws", suggestionList);
						}
						bills.set(currentBill.getName(), null);
					}
				} else {
					if (vetoCount > plugin.getAllEligibleElves().size() - 1) {
						bills.set(currentBill.getName(), null);
					}
				}
				
				List<String> unvotedBills = new ArrayList<String>();
				bills.getKeys(false).forEach(billString -> {
					if (!bills.getConfigurationSection(billString).contains(player.getUniqueId().toString())) {
						unvotedBills.add(billString);
					}
				});
				
				
				
				if (unvotedBills.size() != 0) {
					
					if (index >= unvotedBills.size()) {
						index = 0;
					}

					String billString = bills.getConfigurationSection(unvotedBills.get(index)).getString("title");
					
					TextComponent nextButton = new TextComponent("NEXT");
					nextButton.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
					nextButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[3] + index));
					nextButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the next bill.")));
					
					TextComponent backButton = new TextComponent("BACK       ");
					backButton.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
					backButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[4] + index));
					backButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the previous bill.")));
		
					TextComponent passButton = new TextComponent("PASS       ");
					passButton.setColor(net.md_5.bungee.api.ChatColor.GREEN);
					passButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[5] + index));
					passButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Vote to pass this law.")));
					
					TextComponent dontPassButton = new TextComponent("VETO       ");
					dontPassButton.setColor(net.md_5.bungee.api.ChatColor.RED);
					dontPassButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[6] + index));
					dontPassButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Vote to not pass this law.")));
					
					backButton.addExtra(dontPassButton);
					backButton.addExtra(passButton);
					backButton.addExtra(nextButton);
					backButton.addExtra("\n \n ");
					
					player.sendMessage("\n \n \n \n \n \n \n \n \n \n \n \n \n \n \n" + ChatColor.YELLOW + "Law: " + billString + "\n ");
					player.spigot().sendMessage(backButton);
					
				} else {
					player.sendMessage(ChatColor.RED + "There are currently no laws to vote on.");
				}
			} else {
				player.sendMessage(ChatColor.RED + "There are currently no laws to vote on.");
			}
			
			try {
				leaderConfig.save(leaderDataFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			event.setCancelled(true);
		} else if (event.getMessage().substring(0, 151).equals(plugin.commandStrings[6])) {//VETO the elven bill

			String lawConfigString = "elfDraftLaws";
			
			if (leaderConfig.contains(lawConfigString)) {
			
				ConfigurationSection bills = leaderConfig.getConfigurationSection(lawConfigString);
				
				if (bills.getKeys(false).size() == 0) {
					player.sendMessage(ChatColor.RED + "There are currently no laws to vote on.");
					return;
				}
				
				ConfigurationSection currentBill = bills.getConfigurationSection(bills.getKeys(false).toArray(new String[bills.getKeys(false).size()])[index]);//This is so many fucking steps. omg
				currentBill.set(player.getUniqueId().toString(), "VETO");
				
				int passCount = 0;
				int vetoCount = 0;
				
				String[] keys = currentBill.getKeys(false).toArray(new String[currentBill.getKeys(false).size()]);
				
				for (String key : keys) {
					if (currentBill.getString(key).equals("PASS")) {
						passCount++;
					} else if (currentBill.getString(key).equals("VETO")) {
						vetoCount++;
					}
				}
				
				if (passCount > vetoCount) {
					if (passCount > plugin.getAllEligibleElves().size() - 1) {
						if (!leaderConfig.contains("elfLaws")) {
							List<String> suggestionList = new ArrayList<String>();
							suggestionList.add(currentBill.getString("title"));
							leaderConfig.set("elfLaws", suggestionList);
						} else {
							List<String> suggestionList = leaderConfig.getStringList("elfLaws");
							suggestionList.add(currentBill.getString("title"));
							leaderConfig.set("elfLaws", suggestionList);
						}
						bills.set(currentBill.getName(), null);
					}
				} else {
					if (vetoCount > plugin.getAllEligibleElves().size() - 1) {
						bills.set(currentBill.getName(), null);
					}
				}
				
				List<String> unvotedBills = new ArrayList<String>();
				bills.getKeys(false).forEach(billString -> {
					if (!bills.getConfigurationSection(billString).contains(player.getUniqueId().toString())) {
						unvotedBills.add(billString);
					}
				});
				
				
				
				if (unvotedBills.size() != 0) {
					
					if (index >= unvotedBills.size()) {
						index = 0;
					}

					String billString = bills.getConfigurationSection(unvotedBills.get(index)).getString("title");
					
					TextComponent nextButton = new TextComponent("NEXT");
					nextButton.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
					nextButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[3] + index));
					nextButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the next bill.")));
					
					TextComponent backButton = new TextComponent("BACK       ");
					backButton.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
					backButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[4] + index));
					backButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the previous bill.")));
		
					TextComponent passButton = new TextComponent("PASS       ");
					passButton.setColor(net.md_5.bungee.api.ChatColor.GREEN);
					passButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[5] + index));
					passButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Vote to pass this law.")));
					
					TextComponent dontPassButton = new TextComponent("VETO       ");
					dontPassButton.setColor(net.md_5.bungee.api.ChatColor.RED);
					dontPassButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[6] + index));
					dontPassButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Vote to not pass this law.")));
					
					backButton.addExtra(dontPassButton);
					backButton.addExtra(passButton);
					backButton.addExtra(nextButton);
					backButton.addExtra("\n \n ");
					
					player.sendMessage("\n \n \n \n \n \n \n \n \n \n \n \n \n \n \n" + ChatColor.YELLOW + "Law: " + billString + "\n ");
					player.spigot().sendMessage(backButton);
					
				} else {
					player.sendMessage(ChatColor.RED + "There are currently no laws to vote on.");
				}
			} else {
				player.sendMessage(ChatColor.RED + "There are currently no laws to vote on.");
			}
			
			try {
				leaderConfig.save(leaderDataFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			event.setCancelled(true);
		} else if (event.getMessage().substring(0, 151).equals(plugin.commandStrings[7])) {//View next law
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
				
				index++;
				if (index >= leaderConfig.getStringList(lawConfigString).size()) {
					index = 0;
				}
				
				String suggestionString = leaderConfig.getStringList(lawConfigString).get(index);
				TextComponent nextButton = new TextComponent("       NEXT");
				nextButton.setColor(net.md_5.bungee.api.ChatColor.GREEN);
				nextButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[7] + index));
				nextButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the next " + lawName.toLowerCase())));
				
				TextComponent backButton = new TextComponent("       BACK");
				backButton.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
				backButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[8] + index));
				backButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the next " + lawName.toLowerCase())));
				
				backButton.addExtra(nextButton);
				backButton.addExtra("\n \n ");
				
				
				
				player.sendMessage("\n \n \n \n \n \n \n \n \n \n \n \n \n" + plugin.itemManager.getRaceColor(race) + "                                 " + lawName + (index + 1) + "\n \n" + ChatColor.GOLD + lawName + ": " + suggestionString + "\n ");
				player.spigot().sendMessage(backButton);
				event.setCancelled(true);
			}
		} else if (event.getMessage().substring(0, 151).equals(plugin.commandStrings[8])) {//view previous law
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
				
				index--;
				if (index < 0) {
					index = leaderConfig.getStringList(lawConfigString).size() - 1;
				}
				
				String suggestionString = leaderConfig.getStringList(lawConfigString).get(index);
				TextComponent nextButton = new TextComponent("       NEXT");
				nextButton.setColor(net.md_5.bungee.api.ChatColor.GREEN);
				nextButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[7] + index));
				nextButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the next " + lawName.toLowerCase())));
				
				TextComponent backButton = new TextComponent("       BACK");
				backButton.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
				backButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[8] + index));
				backButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the next " + lawName.toLowerCase())));
				
				backButton.addExtra(nextButton);
				backButton.addExtra("\n \n ");
				
				
				
				player.sendMessage("\n \n \n \n \n \n \n \n \n \n \n \n \n" + plugin.itemManager.getRaceColor(race) + "                                 " + lawName + (index + 1) + "\n \n" + ChatColor.GOLD + lawName + ": " + suggestionString + "\n ");
				player.spigot().sendMessage(backButton);
				event.setCancelled(true);
			}
		} else if (event.getMessage().substring(0, 151).equals(plugin.commandStrings[9])) {//next crime
			String crimeConfigString = plugin.getRaceString(race) + "ReportedCrimes";
			
			if (leaderConfig.contains(crimeConfigString)) {
			
				ConfigurationSection crimes = leaderConfig.getConfigurationSection(crimeConfigString);
				List<String> unvotedCrimes = new ArrayList<String>();
				crimes.getKeys(false).forEach(crimeString -> {
					if (!crimes.getConfigurationSection(crimeString).contains(player.getUniqueId().toString())) {
						unvotedCrimes.add(crimeString);
					}
				});
				
				index++;
				if (index >= unvotedCrimes.size()) {
					index = 0;
				}
				
				if (unvotedCrimes.size() != 0) {
					String crimeString = crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("title");
					String defendant = Bukkit.getOfflinePlayer(UUID.fromString(crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("defendant"))).getName();
					String plaintiff = Bukkit.getOfflinePlayer(UUID.fromString(crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("plaintiff"))).getName();
					
					TextComponent nextButton = new TextComponent("NEXT");
					nextButton.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
					nextButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[9] + index));
					nextButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the next crime.")));
					
					TextComponent backButton = new TextComponent("BACK       ");
					backButton.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
					backButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[10] + index));
					backButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the previous crime.")));
		
					TextComponent passButton = new TextComponent("INNOCENT       ");
					passButton.setColor(net.md_5.bungee.api.ChatColor.GREEN);
					passButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[11] + index));
					passButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Declare this defendant innocent.")));
					
					TextComponent dontPassButton = new TextComponent("GUILTY       ");
					dontPassButton.setColor(net.md_5.bungee.api.ChatColor.RED);
					dontPassButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[12] + index));
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
			event.setCancelled(true);
		} else if (event.getMessage().substring(0, 151).equals(plugin.commandStrings[10])) {//previous crime
			String crimeConfigString = plugin.getRaceString(race) + "ReportedCrimes";
			
			if (leaderConfig.contains(crimeConfigString)) {
			
				ConfigurationSection crimes = leaderConfig.getConfigurationSection(crimeConfigString);
				List<String> unvotedCrimes = new ArrayList<String>();
				crimes.getKeys(false).forEach(crimeString -> {
					if (!crimes.getConfigurationSection(crimeString).contains(player.getUniqueId().toString())) {
						unvotedCrimes.add(crimeString);
					}
				});
				
				index--;
				if (index < 0) {
					index = unvotedCrimes.size() - 1;
				}
				
				if (unvotedCrimes.size() != 0) {
					String crimeString = crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("title");
					String defendant = Bukkit.getOfflinePlayer(UUID.fromString(crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("defendant"))).getName();
					String plaintiff = Bukkit.getOfflinePlayer(UUID.fromString(crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("plaintiff"))).getName();
					
					TextComponent nextButton = new TextComponent("NEXT");
					nextButton.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
					nextButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[9] + index));
					nextButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the next crime.")));
					
					TextComponent backButton = new TextComponent("BACK       ");
					backButton.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
					backButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[10] + index));
					backButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the previous crime.")));
		
					TextComponent passButton = new TextComponent("INNOCENT       ");
					passButton.setColor(net.md_5.bungee.api.ChatColor.GREEN);
					passButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[11] + index));
					passButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Declare this defendant innocent.")));
					
					TextComponent dontPassButton = new TextComponent("GUILTY       ");
					dontPassButton.setColor(net.md_5.bungee.api.ChatColor.RED);
					dontPassButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[12] + index));
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
			event.setCancelled(true);
		} else if (event.getMessage().substring(0, 151).equals(plugin.commandStrings[11])) {//innocent
			
			
			String crimeConfigString = plugin.getRaceString(race) + "ReportedCrimes";
			
			if (leaderConfig.contains(crimeConfigString)) {
				
			
				ConfigurationSection crimes = leaderConfig.getConfigurationSection(crimeConfigString);
				
				
				
				
				
				
				if (crimes.getKeys(false).size() == 0) {
					player.sendMessage(ChatColor.RED + "There are currently no crimes to vote on.");
					return;
				}
				
				ConfigurationSection currentCrime = crimes.getConfigurationSection(crimes.getKeys(false).toArray(new String[crimes.getKeys(false).size()])[index]);//This is so many fucking steps. omg
				currentCrime.set(player.getUniqueId().toString(), "INNOCENT");
				
				int innocentCount = 0;
				int guiltyCount = 0;
				
				String[] keys = currentCrime.getKeys(false).toArray(new String[currentCrime.getKeys(false).size()]);
				
				for (String key : keys) {
					if (currentCrime.getString(key).equals("INNOCENT")) {
						innocentCount++;
					} else if (currentCrime.getString(key).equals("GUILTY")) {
						guiltyCount++;
					}
				}
				
				int elligibleVoters = 0;
				if (race == 1) {
					elligibleVoters = plugin.getAllEligibleMen().size();
				} else if (race == 2) {
					elligibleVoters = plugin.getAllEligibleElves().size();
				}
				
				if (innocentCount > guiltyCount) {
					if (innocentCount > elligibleVoters - 1) {
						//verdict is innocent
						crimes.set(currentCrime.getName(), null);
					}
				} else {
					if (guiltyCount > elligibleVoters - 1) {
						//verdict guilty
						ConfigurationSection guiltyCrimes;
						if (!leaderConfig.contains(plugin.getRaceString(race) + "GuiltyCrimes")) {
							guiltyCrimes = leaderConfig.createSection(plugin.getRaceString(race) + "GuiltyCrimes");
						} else {
							guiltyCrimes = leaderConfig.getConfigurationSection(plugin.getRaceString(race) + "GuiltyCrimes");
						}

						ConfigurationSection crime = guiltyCrimes.createSection(currentCrime.getName());
						crime.set("title", currentCrime.getString("title"));
						crime.set("defendant", currentCrime.getString("defendant"));
					}
					crimes.set(currentCrime.getName(), null);
				}
				
				
				List<String> unvotedCrimes = new ArrayList<String>();
				crimes.getKeys(false).forEach(crimeString -> {
					if (!crimes.getConfigurationSection(crimeString).contains(player.getUniqueId().toString())) {
						unvotedCrimes.add(crimeString);
					}
				});
				
				if (index >= unvotedCrimes.size()) {
					index = 0;
				}
				
				if (unvotedCrimes.size() != 0) {
					String crimeString = crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("title");
					String defendant = Bukkit.getOfflinePlayer(UUID.fromString(crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("defendant"))).getName();
					String plaintiff = Bukkit.getOfflinePlayer(UUID.fromString(crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("plaintiff"))).getName();
					
					TextComponent nextButton = new TextComponent("NEXT");
					nextButton.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
					nextButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[9] + index));
					nextButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the next crime.")));
					
					TextComponent backButton = new TextComponent("BACK       ");
					backButton.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
					backButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[10] + index));
					backButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the previous crime.")));
		
					TextComponent passButton = new TextComponent("INNOCENT       ");
					passButton.setColor(net.md_5.bungee.api.ChatColor.GREEN);
					passButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[11] + index));
					passButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Declare this defendant innocent.")));
					
					TextComponent dontPassButton = new TextComponent("GUILTY       ");
					dontPassButton.setColor(net.md_5.bungee.api.ChatColor.RED);
					dontPassButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[12] + index));
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
			try {
				leaderConfig.save(leaderDataFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			event.setCancelled(true);
		} else if (event.getMessage().substring(0, 151).equals(plugin.commandStrings[12])) {//guilty

			
			String crimeConfigString = plugin.getRaceString(race) + "ReportedCrimes";
			
			if (leaderConfig.contains(crimeConfigString)) {
				
			
				ConfigurationSection crimes = leaderConfig.getConfigurationSection(crimeConfigString);
				
				
				
				
				
				
				if (crimes.getKeys(false).size() == 0) {
					player.sendMessage(ChatColor.RED + "There are currently no crimes to vote on.");
					return;
				}
				
				ConfigurationSection currentCrime = crimes.getConfigurationSection(crimes.getKeys(false).toArray(new String[crimes.getKeys(false).size()])[index]);//This is so many fucking steps. omg
				currentCrime.set(player.getUniqueId().toString(), "GUILTY");
				
				int innocentCount = 0;
				int guiltyCount = 0;
				
				String[] keys = currentCrime.getKeys(false).toArray(new String[currentCrime.getKeys(false).size()]);
				
				for (String key : keys) {
					if (currentCrime.getString(key).equals("INNOCENT")) {
						innocentCount++;
					} else if (currentCrime.getString(key).equals("GUILTY")) {
						guiltyCount++;
					}
				}
				
				int elligibleVoters = 0;
				if (race == 1) {
					elligibleVoters = plugin.getAllEligibleMen().size();
				} else if (race == 2) {
					elligibleVoters = plugin.getAllEligibleElves().size();
				}
				
				if (innocentCount > guiltyCount) {
					if (innocentCount > elligibleVoters - 1) {
						//verdict is innocent
						crimes.set(currentCrime.getName(), null);
					}
				} else {
					if (guiltyCount > elligibleVoters - 1) {
						//verdict guilty
						ConfigurationSection guiltyCrimes;
						if (!leaderConfig.contains(plugin.getRaceString(race) + "GuiltyCrimes")) {
							guiltyCrimes = leaderConfig.createSection(plugin.getRaceString(race) + "GuiltyCrimes");
						} else {
							guiltyCrimes = leaderConfig.getConfigurationSection(plugin.getRaceString(race) + "GuiltyCrimes");
						}

						ConfigurationSection crime = guiltyCrimes.createSection(currentCrime.getName());
						crime.set("title", currentCrime.getString("title"));
						crime.set("defendant", currentCrime.getString("defendant"));
						crime.set("plaintiff", currentCrime.getString("plaintiff"));
					}
					crimes.set(currentCrime.getName(), null);
				}
				
				
				List<String> unvotedCrimes = new ArrayList<String>();
				crimes.getKeys(false).forEach(crimeString -> {
					if (!crimes.getConfigurationSection(crimeString).contains(player.getUniqueId().toString())) {
						unvotedCrimes.add(crimeString);
					}
				});
				
				if (index >= unvotedCrimes.size()) {
					index = 0;
				}
				
				if (unvotedCrimes.size() != 0) {
					String crimeString = crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("title");
					String defendant = Bukkit.getOfflinePlayer(UUID.fromString(crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("defendant"))).getName();
					String plaintiff = Bukkit.getOfflinePlayer(UUID.fromString(crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("plaintiff"))).getName();
					
					TextComponent nextButton = new TextComponent("NEXT");
					nextButton.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
					nextButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[9] + index));
					nextButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the next crime.")));
					
					TextComponent backButton = new TextComponent("BACK       ");
					backButton.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
					backButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[10] + index));
					backButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the previous crime.")));
		
					TextComponent passButton = new TextComponent("INNOCENT       ");
					passButton.setColor(net.md_5.bungee.api.ChatColor.GREEN);
					passButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[11] + index));
					passButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Declare this defendant innocent.")));
					
					TextComponent dontPassButton = new TextComponent("GUILTY       ");
					dontPassButton.setColor(net.md_5.bungee.api.ChatColor.RED);
					dontPassButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[12] + index));
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
			try {
				leaderConfig.save(leaderDataFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			event.setCancelled(true);
		}  else if (event.getMessage().substring(0, 151).equals(plugin.commandStrings[13])) {//next crime LEADER
			String crimeConfigString = plugin.getRaceString(race) + "ReportedCrimes";
			
			if (leaderConfig.contains(crimeConfigString)) {
			
				ConfigurationSection crimes = leaderConfig.getConfigurationSection(crimeConfigString);
				List<String> unvotedCrimes = new ArrayList<String>();
				crimes.getKeys(false).forEach(crimeString -> {
					if (!crimes.getConfigurationSection(crimeString).contains(player.getUniqueId().toString())) {
						unvotedCrimes.add(crimeString);
					}
				});
				
				index++;
				if (index >= unvotedCrimes.size()) {
					index = 0;
				}
				
				if (unvotedCrimes.size() != 0) {
					String crimeString = crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("title");
					String defendant = Bukkit.getOfflinePlayer(UUID.fromString(crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("defendant"))).getName();
					String plaintiff = Bukkit.getOfflinePlayer(UUID.fromString(crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("plaintiff"))).getName();
					
					TextComponent nextButton = new TextComponent("NEXT");
					nextButton.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
					nextButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[13] + index));
					nextButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the next crime.")));
					
					TextComponent backButton = new TextComponent("BACK       ");
					backButton.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
					backButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[14] + index));
					backButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the previous crime.")));
		
					TextComponent passButton = new TextComponent("INNOCENT       ");
					passButton.setColor(net.md_5.bungee.api.ChatColor.GREEN);
					passButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[15] + index));
					passButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Declare this defendant innocent.")));
					
					TextComponent dontPassButton = new TextComponent("GUILTY       ");
					dontPassButton.setColor(net.md_5.bungee.api.ChatColor.RED);
					dontPassButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[16] + index));
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
			event.setCancelled(true);
		} else if (event.getMessage().substring(0, 151).equals(plugin.commandStrings[14])) {//previous crime
			String crimeConfigString = plugin.getRaceString(race) + "ReportedCrimes";
			
			if (leaderConfig.contains(crimeConfigString)) {
			
				ConfigurationSection crimes = leaderConfig.getConfigurationSection(crimeConfigString);
				List<String> unvotedCrimes = new ArrayList<String>();
				crimes.getKeys(false).forEach(crimeString -> {
					if (!crimes.getConfigurationSection(crimeString).contains(player.getUniqueId().toString())) {
						unvotedCrimes.add(crimeString);
					}
				});
				
				index--;
				if (index < 0) {
					index = unvotedCrimes.size() - 1;
				}
				
				if (unvotedCrimes.size() != 0) {
					String crimeString = crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("title");
					String defendant = Bukkit.getOfflinePlayer(UUID.fromString(crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("defendant"))).getName();
					String plaintiff = Bukkit.getOfflinePlayer(UUID.fromString(crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("plaintiff"))).getName();
					
					TextComponent nextButton = new TextComponent("NEXT");
					nextButton.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
					nextButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[13] + index));
					nextButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the next crime.")));
					
					TextComponent backButton = new TextComponent("BACK       ");
					backButton.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
					backButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[14] + index));
					backButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the previous crime.")));
		
					TextComponent passButton = new TextComponent("INNOCENT       ");
					passButton.setColor(net.md_5.bungee.api.ChatColor.GREEN);
					passButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[15] + index));
					passButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Declare this defendant innocent.")));
					
					TextComponent dontPassButton = new TextComponent("GUILTY       ");
					dontPassButton.setColor(net.md_5.bungee.api.ChatColor.RED);
					dontPassButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[16] + index));
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
			event.setCancelled(true);
		} else if (event.getMessage().substring(0, 151).equals(plugin.commandStrings[15])) {//innocent
			
			
			String crimeConfigString = plugin.getRaceString(race) + "ReportedCrimes";
			
			if (leaderConfig.contains(crimeConfigString)) {
				
			
				ConfigurationSection crimes = leaderConfig.getConfigurationSection(crimeConfigString);
				
				
				if (crimes.getKeys(false).size() == 0) {
					player.sendMessage(ChatColor.RED + "There are currently no crimes to judge.");
					return;
				}
				
				ConfigurationSection currentCrime = crimes.getConfigurationSection(crimes.getKeys(false).toArray(new String[crimes.getKeys(false).size()])[index]);//This is so many fucking steps. omg
				
				crimes.set(currentCrime.getName(), null);
				
				List<String> unvotedCrimes = new ArrayList<String>();
				crimes.getKeys(false).forEach(crimeString -> {
					if (!crimes.getConfigurationSection(crimeString).contains(player.getUniqueId().toString())) {
						unvotedCrimes.add(crimeString);
					}
				});
				
				if (index >= unvotedCrimes.size()) {
					index = 0;
				}
				
				if (unvotedCrimes.size() != 0) {
					String crimeString = crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("title");
					String defendant = Bukkit.getOfflinePlayer(UUID.fromString(crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("defendant"))).getName();
					String plaintiff = Bukkit.getOfflinePlayer(UUID.fromString(crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("plaintiff"))).getName();
					
					TextComponent nextButton = new TextComponent("NEXT");
					nextButton.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
					nextButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[13] + index));
					nextButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the next crime.")));
					
					TextComponent backButton = new TextComponent("BACK       ");
					backButton.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
					backButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[14] + index));
					backButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the previous crime.")));
		
					TextComponent passButton = new TextComponent("INNOCENT       ");
					passButton.setColor(net.md_5.bungee.api.ChatColor.GREEN);
					passButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[15] + index));
					passButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Declare this defendant innocent.")));
					
					TextComponent dontPassButton = new TextComponent("GUILTY       ");
					dontPassButton.setColor(net.md_5.bungee.api.ChatColor.RED);
					dontPassButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[16] + index));
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
			try {
				leaderConfig.save(leaderDataFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			event.setCancelled(true);
		} else if (event.getMessage().substring(0, 151).equals(plugin.commandStrings[16])) {//guilty

			
			String crimeConfigString = plugin.getRaceString(race) + "ReportedCrimes";
			
			if (leaderConfig.contains(crimeConfigString)) {
				
			
				ConfigurationSection crimes = leaderConfig.getConfigurationSection(crimeConfigString);
				
				if (crimes.getKeys(false).size() == 0) {
					player.sendMessage(ChatColor.RED + "There are currently no crimes to judge.");
					return;
				}
				
				ConfigurationSection currentCrime = crimes.getConfigurationSection(crimes.getKeys(false).toArray(new String[crimes.getKeys(false).size()])[index]);//This is so many fucking steps. omg
				currentCrime.set(player.getUniqueId().toString(), "GUILTY");
				
				ConfigurationSection guiltyCrimes;
				if (!leaderConfig.contains(plugin.getRaceString(race) + "GuiltyCrimes")) {
					guiltyCrimes = leaderConfig.createSection(plugin.getRaceString(race) + "GuiltyCrimes");
				} else {
					guiltyCrimes = leaderConfig.getConfigurationSection(plugin.getRaceString(race) + "GuiltyCrimes");
				}

				ConfigurationSection crime = guiltyCrimes.createSection(currentCrime.getName());
				crime.set("title", currentCrime.getString("title"));
				crime.set("defendant", currentCrime.getString("defendant"));
				crime.set("plaintiff", currentCrime.getString("plaintiff"));
				crimes.set(currentCrime.getName(), null);
				
				
				List<String> unvotedCrimes = new ArrayList<String>();
				crimes.getKeys(false).forEach(crimeString -> {
					if (!crimes.getConfigurationSection(crimeString).contains(player.getUniqueId().toString())) {
						unvotedCrimes.add(crimeString);
					}
				});
				
				if (index >= unvotedCrimes.size()) {
					index = 0;
				}
				
				if (unvotedCrimes.size() != 0) {
					String crimeString = crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("title");
					String defendant = Bukkit.getOfflinePlayer(UUID.fromString(crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("defendant"))).getName();
					String plaintiff = Bukkit.getOfflinePlayer(UUID.fromString(crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("plaintiff"))).getName();
					
					TextComponent nextButton = new TextComponent("NEXT");
					nextButton.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
					nextButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[13] + index));
					nextButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the next crime.")));
					
					TextComponent backButton = new TextComponent("BACK       ");
					backButton.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
					backButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[14] + index));
					backButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View the previous crime.")));
		
					TextComponent passButton = new TextComponent("INNOCENT       ");
					passButton.setColor(net.md_5.bungee.api.ChatColor.GREEN);
					passButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[15] + index));
					passButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Declare this defendant innocent.")));
					
					TextComponent dontPassButton = new TextComponent("GUILTY       ");
					dontPassButton.setColor(net.md_5.bungee.api.ChatColor.RED);
					dontPassButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.commandStrings[16] + index));
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
			
			try {
				leaderConfig.save(leaderDataFile);
			} catch (IOException e) {
				e.printStackTrace();
			}

			
			event.setCancelled(true);
		}
		
	}
}
