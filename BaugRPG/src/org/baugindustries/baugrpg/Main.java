package org.baugindustries.baugrpg;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.baugindustries.baugrpg.commands.BaugScroll;
import org.baugindustries.baugrpg.commands.Chat;
import org.baugindustries.baugrpg.commands.RaceWizard;
import org.baugindustries.baugrpg.commands.ResetRace;
import org.baugindustries.baugrpg.commands.SetRace;
import org.baugindustries.baugrpg.commands.Tpa;
import org.baugindustries.baugrpg.commands.Tpaccept;
import org.baugindustries.baugrpg.commands.Tpdeny;
import org.baugindustries.baugrpg.commands.Tphere;
import org.baugindustries.baugrpg.listeners.OnJoinListener;
import org.baugindustries.baugrpg.listeners.OnQuitListener;
import org.baugindustries.baugrpg.listeners.PlayerCloseInventoryListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Dwarves.MinecartMoveListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Dwarves.PlayerKillListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Men.HorseListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import org.bukkit.ChatColor;

public class Main extends JavaPlugin {

	
	public ChatChannelManager channelManager;
	public HashMap<Player, Player> tpaHashMap = new HashMap<Player, Player>();
	public HashMap<Player, Player> tpahereHashMap = new HashMap<Player, Player>();
	public ScoreboardManager manager;
	public Scoreboard board;
	
	@Override
	public void onEnable() {
		 manager = Bukkit.getScoreboardManager();
		 board = manager.getMainScoreboard();
		 this.getServer().getPluginManager().registerEvents(new OnJoinListener(this), this);
		 this.getServer().getPluginManager().registerEvents(new OnQuitListener(this), this);
		 this.getServer().getPluginManager().registerEvents(new PlayerCloseInventoryListener(this), this);
		 this.getServer().getPluginManager().registerEvents(new HorseListener(this), this);
		 this.getServer().getPluginManager().registerEvents(new PlayerKillListener(this), this);
		 this.getServer().getPluginManager().registerEvents(new MinecartMoveListener(this), this);
		 new ResetRace(this);
		 new SetRace(this);
		 new Chat(this);
		 new RaceWizard(this);//The Wizard race will serve as an "operator" race in the BaugRPG plugin, and should be reserved for admins, and people that will oversee RP events.
		 new BaugScroll(this);
		 new Tpa(this);
		 new Tphere(this);
		 new Tpaccept(this);
		 new Tpdeny(this);
		 
		 if (board.getTeam("Men") == null) {
			 Team menTeam = board.registerNewTeam("Men");
			 menTeam.setColor(ChatColor.DARK_AQUA);
		 }
		 
		 if (board.getTeam("Elves") == null) {
			 Team elfTeam = board.registerNewTeam("Elves");
			 elfTeam.setColor(ChatColor.DARK_GREEN);
		 }
		 
		 if (board.getTeam("Dwarves") == null) {
			 Team dwarfTeam = board.registerNewTeam("Dwarves");
			 dwarfTeam.setColor(ChatColor.DARK_PURPLE);
		 }
		 
		 if (board.getTeam("Orcs") == null) {
			 Team orcTeam = board.registerNewTeam("Orcs");
			 orcTeam.setColor(ChatColor.DARK_RED);
		 }
		 
		 if (board.getTeam("Wizards") == null) {
			 Team wizardTeam = board.registerNewTeam("Wizards");
			 wizardTeam.setColor(ChatColor.AQUA);
		 }
		 
		 getServer().getPluginManager().registerEvents(new ChatChannel(this), this);
		 
		 channelManager = new ChatChannelManager();
		 
	}
	
	public void onDisable() {
		//Save player's inventory to YML file
				
		for (int i = 0; i < this.getServer().getOfflinePlayers().length; i++) {
				
			if (this.getServer().getOfflinePlayers()[i].isOnline()) {
				
				Player player = this.getServer().getOfflinePlayers()[i].getPlayer();
				
				//create file with player's UUID
				File file = new File(this.getDataFolder() + File.separator + "inventoryData" + File.separator + player.getUniqueId() + ".yml");
				FileConfiguration config = YamlConfiguration.loadConfiguration(file);
				
				//Check to see if the file already exists. If not, create it.
				if (!file.exists()) {
					try {
						file.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				config.set("Username", player.getDisplayName());
				config.set("UUID", player.getUniqueId().toString());
				config.set("Inventory", player.getInventory().getContents());
				
				PersistentDataContainer data = player.getPlayer().getPersistentDataContainer();
				if (data.has(new NamespacedKey(this, "Race"), PersistentDataType.INTEGER)) {
					config.set("Race Data", data.get(new NamespacedKey(this, "Race"), PersistentDataType.INTEGER));
				}
				
					
				
				
				try {
					config.save(file);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
				
	}
	
	public List<OfflinePlayer> getAllOfflineElves() {
		OfflinePlayer[] allOfflinePlayers = this.getServer().getOfflinePlayers();
		List<OfflinePlayer> allOfflineElvesList = new ArrayList<OfflinePlayer>();
		
		for (int i = 0; i < allOfflinePlayers.length; i++) {
			Player player;
			OfflinePlayer offlinePlayer = allOfflinePlayers[i];
			if (allOfflinePlayers[i].isOnline()) {
				player = allOfflinePlayers[i].getPlayer();
				
				if (player.getPersistentDataContainer().has(new NamespacedKey(this, "Race"), PersistentDataType.INTEGER) && player.getPersistentDataContainer().get(new NamespacedKey(this, "Race"), PersistentDataType.INTEGER) == 2) {
					allOfflineElvesList.add(offlinePlayer);
				}
				
			} else {
				File file = new File(this.getDataFolder() + File.separator + "inventoryData" + File.separator + offlinePlayer.getUniqueId() + ".yml");
				FileConfiguration config = YamlConfiguration.loadConfiguration(file);
				
				int race = config.getInt("Race Data");
				if (race == 2) {
					allOfflineElvesList.add(offlinePlayer);
				}
			}
		}
		
		
		
		
		return allOfflineElvesList;
	}
	
	
	
	public ItemStack createItem(Material material, int amount, String displayName, List<String> loreInfo) {
		
		ItemStack item = new ItemStack(material);
		item.setAmount(amount);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(displayName);
		List<String> itemLore = loreInfo;
		itemMeta.setLore(itemLore);
		item.setItemMeta(itemMeta);
		
		return item;
	}
	
	public ItemStack createItem(Material material, int amount, String displayName) {
		
		ItemStack item = new ItemStack(material);
		item.setAmount(amount);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(displayName);
		item.setItemMeta(itemMeta);
		
		return item;
	}
	
	public ItemStack createItem(Material material, int amount) {
		ItemStack item = new ItemStack(material);
		item.setAmount(amount);
		return item;
	}
	
	
	public List<Player> getOnlinePlayers() {
		List<Player> allOnlinePlayers = new ArrayList<Player>();//this line doesnt work
		OfflinePlayer[] allOfflinePlayers = getServer().getOfflinePlayers();
		for (int i = 0; i < allOfflinePlayers.length; i++) {
			if (allOfflinePlayers[i].isOnline()) {
				allOnlinePlayers.add(allOfflinePlayers[i].getPlayer());
			}
		}
		return allOnlinePlayers;
	}
	
	
	
	
	
}