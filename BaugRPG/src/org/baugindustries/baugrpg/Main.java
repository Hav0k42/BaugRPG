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
import org.baugindustries.baugrpg.commands.econ.Balance;
import org.baugindustries.baugrpg.commands.econ.Pay;
import org.baugindustries.baugrpg.listeners.ChestBreakListener;
import org.baugindustries.baugrpg.listeners.ChestOpenListener;
import org.baugindustries.baugrpg.listeners.ElfEatMeat;
import org.baugindustries.baugrpg.listeners.HorseListener;
import org.baugindustries.baugrpg.listeners.MinecartMoveListener;
import org.baugindustries.baugrpg.listeners.OnJoinListener;
import org.baugindustries.baugrpg.listeners.OnQuitListener;
import org.baugindustries.baugrpg.listeners.OrcEatMeat;
import org.baugindustries.baugrpg.listeners.PlayerCloseInventoryListener;
import org.baugindustries.baugrpg.listeners.PlayerDeathListener;
import org.baugindustries.baugrpg.listeners.SignBreakListener;
import org.baugindustries.baugrpg.listeners.SignShopListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ChooseRaceInventoryListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ConfirmRaceInventoryListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Dwarves.GoldConversionMenu;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Dwarves.ScrollsOfBaugDwarvesInventoryListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Elves.ScrollsOfBaugElvesInventoryListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Elves.CommunismHub.ElvesCommunismEnderChestListListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Elves.CommunismHub.ElvesCommunismHubInventoryListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Elves.CommunismHub.ElvesCommunismInventoryListListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Men.ScrollsOfBaugMenInventoryListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Orcs.ScrollsOfBaugOrcsInventoryListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Wizards.FeatureManagement;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Wizards.ScrollsOfBaugWizardsInventoryListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Wizards.PlayerSnooping.PlayerSnoopingEnderChestListListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Wizards.PlayerSnooping.PlayerSnoopingHubInventoryListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Wizards.PlayerSnooping.PlayerSnoopingInventoryListListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.util.logging.Logger;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import net.milkbowl.vault.economy.*;

import org.bukkit.ChatColor;

public class Main extends JavaPlugin {

	
	public ChatChannelManager channelManager;
	public HashMap<Player, Player> tpaHashMap = new HashMap<Player, Player>();
	public HashMap<Player, Player> tpahereHashMap = new HashMap<Player, Player>();
	public HashMap<Player, Integer> signChatEscape = new HashMap<Player, Integer>();
	public HashMap<Player, List<String>> signData = new HashMap<Player, List<String>>();
	public ScoreboardManager manager;
	public Scoreboard board;
	
	
	//Listeners
	public OnJoinListener onJoinListener = new OnJoinListener(this);
	public OnQuitListener onQuitListener = new OnQuitListener(this);
	public PlayerCloseInventoryListener playerCloseInventoryListener = new PlayerCloseInventoryListener(this);
	public HorseListener horseListener = new HorseListener(this);
	public PlayerDeathListener playerDeathListener = new PlayerDeathListener(this);
	public MinecartMoveListener minecartMoveListener = new MinecartMoveListener(this);
	public ElfEatMeat elfEatMeat = new ElfEatMeat(this);
	public OrcEatMeat orcEatMeat = new OrcEatMeat(this);
	public SignShopListener signShopListener = new SignShopListener(this);
	public SignBreakListener signBreakListener = new SignBreakListener(this);
	public ChestBreakListener chestBreakListener = new ChestBreakListener(this);
	public ChestOpenListener chestOpenListener = new ChestOpenListener(this);
	
	
	
	
	public ChooseRaceInventoryListener chooseRaceInventoryListener = new ChooseRaceInventoryListener(this);
	public ConfirmRaceInventoryListener confirmRaceInventoryListener = new ConfirmRaceInventoryListener(this);
	public GoldConversionMenu goldConversionMenu = new GoldConversionMenu(this);
	public ScrollsOfBaugDwarvesInventoryListener scrollsOfBaugDwarvesInventoryListener = new ScrollsOfBaugDwarvesInventoryListener(this);
	public ScrollsOfBaugElvesInventoryListener scrollsOfBaugElvesInventoryListener = new ScrollsOfBaugElvesInventoryListener(this);
	public ElvesCommunismEnderChestListListener elvesCommunismEnderChestListListener = new ElvesCommunismEnderChestListListener(this);
	public ElvesCommunismHubInventoryListener elvesCommunismHubInventoryListener = new ElvesCommunismHubInventoryListener(this);
	public ElvesCommunismInventoryListListener elvesCommunismInventoryListListener = new ElvesCommunismInventoryListListener(this);
	public ScrollsOfBaugMenInventoryListener scrollsOfBaugMenInventoryListener = new ScrollsOfBaugMenInventoryListener(this);
	public ScrollsOfBaugOrcsInventoryListener scrollsOfBaugOrcsInventoryListener = new ScrollsOfBaugOrcsInventoryListener(this);
	public FeatureManagement featureManagement = new FeatureManagement(this);
	public ScrollsOfBaugWizardsInventoryListener scrollsOfBaugWizardsInventoryListener = new ScrollsOfBaugWizardsInventoryListener(this);
	public PlayerSnoopingEnderChestListListener playerSnoopingEnderChestListListener = new PlayerSnoopingEnderChestListListener(this);
	public PlayerSnoopingHubInventoryListener playerSnoopingHubInventoryListener = new PlayerSnoopingHubInventoryListener(this);
	public PlayerSnoopingInventoryListListener playerSnoopingInventoryListListener = new PlayerSnoopingInventoryListListener(this);
	
	
	
	
	
	public Economy econ = null;
	
	@Override
	public void onEnable() {
		 final Logger log = Logger.getLogger("Minecraft");
		 manager = Bukkit.getScoreboardManager();
		 board = manager.getMainScoreboard();
		 this.getServer().getPluginManager().registerEvents(onJoinListener, this);
		 this.getServer().getPluginManager().registerEvents(onQuitListener, this);
		 this.getServer().getPluginManager().registerEvents(playerCloseInventoryListener, this);
		 this.getServer().getPluginManager().registerEvents(horseListener, this);
		 this.getServer().getPluginManager().registerEvents(playerDeathListener, this);
		 this.getServer().getPluginManager().registerEvents(minecartMoveListener, this);
		 this.getServer().getPluginManager().registerEvents(elfEatMeat, this);
		 this.getServer().getPluginManager().registerEvents(orcEatMeat, this);
		 this.getServer().getPluginManager().registerEvents(signShopListener, this);
		 this.getServer().getPluginManager().registerEvents(signBreakListener, this);
		 this.getServer().getPluginManager().registerEvents(chestBreakListener, this);
		 this.getServer().getPluginManager().registerEvents(chestOpenListener, this);
		 new Pay(this);
		 new Balance(this);
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
		 
		 orcLight();
		 
		 
		 File file = new File(this.getDataFolder() + File.separator + "econ.yml");
	 	 FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		 
		 //Check to see if the file already exists. If not, create it.
		 if (!file.exists()) {
			 try {
				 file.createNewFile();
			 } catch (IOException e) {
				 e.printStackTrace();
			 }
		 }
		 
		 File signfile = new File(this.getDataFolder() + File.separator + "shops.yml");
	 	 FileConfiguration signconfig = YamlConfiguration.loadConfiguration(signfile);
		 
		 //Check to see if the file already exists. If not, create it.
		 if (!signfile.exists()) {
			 try {
				 signfile.createNewFile();
			 } catch (IOException e) {
				 e.printStackTrace();
			 }
		 }
		 
		 
		 File configfile = new File(this.getDataFolder() + File.separator + "config.yml");
	 	 FileConfiguration configconfig = YamlConfiguration.loadConfiguration(configfile);
		 
		 //Check to see if the file already exists. If not, create it.
		 if (!configfile.exists()) {
			 try {
				 configfile.createNewFile();
			 } catch (IOException e) {
				 e.printStackTrace();
			 }
		 }
	}
	
	void orcLight() {
		this.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {//check if any orcs are in sunlight
			 
			  public void run() {
			     Player[] OnlinePlayers = getServer().getOnlinePlayers().toArray(new Player[getServer().getOnlinePlayers().size()]);
			     for (int i = 0; i < OnlinePlayers.length; i++) {
			    	 Player p = OnlinePlayers[i];
			    	 if (p.getPlayer().getPersistentDataContainer().has(new NamespacedKey(Main.this, "Race"), PersistentDataType.INTEGER)) {
			    		 int race = p.getPlayer().getPersistentDataContainer().get(new NamespacedKey(Main.this, "Race"), PersistentDataType.INTEGER);
			    		 if (race == 4) {//Player is an orc
			    			 int skyLight = p.getLocation().getBlock().getLightFromSky();
			    			 if (skyLight > 3) {
				    			 if (!p.getWorld().hasStorm()) {
				    				 if (!(p.getWorld().getTime() < 23500 && p.getWorld().getTime() > 12500)) {
				    					 p.setFireTicks(skyLight * 20);
				    				 }
				    			 }
			    			 }
			    			 orcLight();
			    		 }
			    	 }
			     }
			  }
			}, 5L);
	}

	public boolean isInteger( String input ) {
	    try {
	        Integer.parseInt( input );
	        return true;
	    }
	    catch( Exception e ) {
	        return false;
	    }
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