package org.baugindustries.baugrpg;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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
import org.baugindustries.baugrpg.commands.econ.BankBal;
import org.baugindustries.baugrpg.commands.econ.Deposit;
import org.baugindustries.baugrpg.commands.econ.Pay;
import org.baugindustries.baugrpg.commands.econ.SetBal;
import org.baugindustries.baugrpg.commands.econ.Withdraw;
import org.baugindustries.baugrpg.listeners.BlockExplodeListener;
import org.baugindustries.baugrpg.listeners.ChestBreakListener;
import org.baugindustries.baugrpg.listeners.ChestOpenListener;
import org.baugindustries.baugrpg.listeners.ElfEatMeat;
import org.baugindustries.baugrpg.listeners.EntityExplodeListener;
import org.baugindustries.baugrpg.listeners.HorseListener;
import org.baugindustries.baugrpg.listeners.MinecartMoveListener;
import org.baugindustries.baugrpg.listeners.OnJoinListener;
import org.baugindustries.baugrpg.listeners.OnQuitListener;
import org.baugindustries.baugrpg.listeners.OrcEatMeat;
import org.baugindustries.baugrpg.listeners.PlayerAdvancementDoneListener;
import org.baugindustries.baugrpg.listeners.PlayerAttackListener;
import org.baugindustries.baugrpg.listeners.PlayerCloseInventoryListener;
import org.baugindustries.baugrpg.listeners.PlayerDamageListener;
import org.baugindustries.baugrpg.listeners.PlayerDeathListener;
import org.baugindustries.baugrpg.listeners.PlayerJumpListener;
import org.baugindustries.baugrpg.listeners.PlayerMineListener;
import org.baugindustries.baugrpg.listeners.SignBreakListener;
import org.baugindustries.baugrpg.listeners.SignShopListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ChooseClassListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ChooseRaceInventoryListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ConfirmClassListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ConfirmRaceInventoryListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.GeneralSkillTreeMenu;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.SkillTreeMenu;
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
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World.Environment;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.EnumWrappers;

import net.minecraft.sounds.SoundEffect;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundEffectType;


public class Main extends JavaPlugin {

	
	public ChatChannelManager channelManager;
	public HashMap<Player, Player> tpaHashMap = new HashMap<Player, Player>();
	public HashMap<Player, Player> tpahereHashMap = new HashMap<Player, Player>();
	public HashMap<Player, Integer> signChatEscape = new HashMap<Player, Integer>();
	public HashMap<Player, List<String>> signData = new HashMap<Player, List<String>>();
	public HashMap<Player, Location> positionData = new HashMap<Player, Location>();
	public HashMap<Player, String> miningState = new HashMap<Player, String>();
	public HashMap<Player, BlockPosition> miningPosition = new HashMap<Player, BlockPosition>();
	public HashMap<Player, Float> miningSpeed = new HashMap<Player, Float>();
	public HashMap<Player, Integer> miningTicks = new HashMap<Player, Integer>();
	public HashMap<Player, Float> miningPercentage = new HashMap<Player, Float>();
	public ScoreboardManager manager;
	public Scoreboard board;
	public ProtocolManager protocolManager;
	
	
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
	public BlockExplodeListener blockExplodeListener = new BlockExplodeListener(this);
	public EntityExplodeListener entityExplodeListener = new EntityExplodeListener(this);
	public PlayerJumpListener playerJumpListener = new PlayerJumpListener(this);
	public PlayerAttackListener playerAttackListener = new PlayerAttackListener(this);
	public PlayerDamageListener playerDamageListener = new PlayerDamageListener(this);
	public PlayerMineListener playerMineListener = new PlayerMineListener(this);
	
	
	
	
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
	public SkillTreeMenu skillTreeMenu = new SkillTreeMenu(this);
	public GeneralSkillTreeMenu generalSkillTreeMenu = new GeneralSkillTreeMenu(this);
	public PlayerAdvancementDoneListener playerAdvancementDoneListener = new PlayerAdvancementDoneListener(this);
	public ChooseClassListener chooseClassListener = new ChooseClassListener(this);
	public ConfirmClassListener confirmClassListener = new ConfirmClassListener(this);
	
	
	
	
	

	
	@Override
	public void onEnable() {
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
		 this.getServer().getPluginManager().registerEvents(blockExplodeListener, this);
		 this.getServer().getPluginManager().registerEvents(entityExplodeListener, this);
		 this.getServer().getPluginManager().registerEvents(playerJumpListener, this);
		 this.getServer().getPluginManager().registerEvents(playerAttackListener, this);
		 this.getServer().getPluginManager().registerEvents(playerDamageListener, this);
		 this.getServer().getPluginManager().registerEvents(playerMineListener, this);
		 this.getServer().getPluginManager().registerEvents(playerAdvancementDoneListener, this);
		 
		 this.getServer().getPluginManager().registerEvents(chooseRaceInventoryListener, this);
		 this.getServer().getPluginManager().registerEvents(confirmRaceInventoryListener, this);
		 this.getServer().getPluginManager().registerEvents(goldConversionMenu, this);
		 this.getServer().getPluginManager().registerEvents(scrollsOfBaugDwarvesInventoryListener, this);
		 this.getServer().getPluginManager().registerEvents(scrollsOfBaugElvesInventoryListener, this);
		 this.getServer().getPluginManager().registerEvents(elvesCommunismEnderChestListListener, this);
		 this.getServer().getPluginManager().registerEvents(elvesCommunismHubInventoryListener, this);
		 this.getServer().getPluginManager().registerEvents(elvesCommunismInventoryListListener, this);
		 this.getServer().getPluginManager().registerEvents(scrollsOfBaugMenInventoryListener, this);
		 this.getServer().getPluginManager().registerEvents(scrollsOfBaugOrcsInventoryListener, this);
		 this.getServer().getPluginManager().registerEvents(featureManagement, this);
		 this.getServer().getPluginManager().registerEvents(scrollsOfBaugWizardsInventoryListener, this);
		 this.getServer().getPluginManager().registerEvents(playerSnoopingEnderChestListListener, this);
		 this.getServer().getPluginManager().registerEvents(playerSnoopingHubInventoryListener, this);
		 this.getServer().getPluginManager().registerEvents(playerSnoopingInventoryListListener, this);
		 this.getServer().getPluginManager().registerEvents(skillTreeMenu, this);
		 this.getServer().getPluginManager().registerEvents(generalSkillTreeMenu, this);
		 this.getServer().getPluginManager().registerEvents(chooseClassListener, this);
		 this.getServer().getPluginManager().registerEvents(confirmClassListener, this);
		 
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
		 new BankBal(this);
		 new Withdraw(this);
		 new Deposit(this);
		 new SetBal(this);
		 
		 protocolManager = ProtocolLibrary.getProtocolManager();
		 
		 
		 
		 
		 
		 protocolManager.addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Client.BLOCK_DIG) {
	            @Override
	            public void onPacketReceiving(PacketEvent event){
	            	Player player = event.getPlayer();
	                PacketContainer packet = event.getPacket();
	                EnumWrappers.PlayerDigType digType = packet.getPlayerDigTypes().getValues().get(0);
	                
					
					
	                if (digType.name().equals("START_DESTROY_BLOCK") || digType.name().equals("ABORT_DESTROY_BLOCK")) {
	                	miningTicks.put(player, 0);
	                	miningPercentage.put(player, 0f);
	                }
	                miningState.put(player, digType.name());
	                BlockPosition pos = packet.getBlockPositionModifier().read(0);
	                if (!(pos.getX() == 0 && pos.getY() == 0 && pos.getZ() == 0)) {
	                	miningPosition.put(player, pos);	
	                }
	            }
	     });
		 
		 
		 
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
		 miningBuff();
		 
		 
		 File file = new File(this.getDataFolder() + File.separator + "econ.yml");
		 
		 //Check to see if the file already exists. If not, create it.
		 if (!file.exists()) {
			 try {
				 file.createNewFile();
			 } catch (IOException e) {
				 e.printStackTrace();
			 }
		 }
		 
		 File bankfile = new File(this.getDataFolder() + File.separator + "bank.yml");
		 
		 //Check to see if the file already exists. If not, create it.
		 if (!bankfile.exists()) {
			 try {
				 bankfile.createNewFile();
			 } catch (IOException e) {
				 e.printStackTrace();
			 }
		 }
		 
		 
		 
		 File signfile = new File(this.getDataFolder() + File.separator + "shops.yml");
		 
		 //Check to see if the file already exists. If not, create it.
		 if (!signfile.exists()) {
			 try {
				 signfile.createNewFile();
			 } catch (IOException e) {
				 e.printStackTrace();
			 }
		 }
		 
		 
		 File configfile = new File(this.getDataFolder() + File.separator + "config.yml");
		 
		 //Check to see if the file already exists. If not, create it.
		 if (!configfile.exists()) {
			 try {
				 configfile.createNewFile();
			 } catch (IOException e) {
				 e.printStackTrace();
			 }
		 }
	}
	
	Runnable miningBuffRunnable = new Runnable() {
		 
		  public void run() {
			  Player[] OnlinePlayers = getServer().getOnlinePlayers().toArray(new Player[getServer().getOnlinePlayers().size()]);
			     for (int i = 0; i < OnlinePlayers.length; i++) {
			    	 Player player = OnlinePlayers[i];
			    	 
			    	 File skillsfile = new File(getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
					 FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
					 
					 if (skillsconfig.getBoolean("miningOn")) {
			    	 
				    	 BlockPosition pos = miningPosition.get(player);
				    	 if (pos == null) {
				    		 continue;
				    	 }
				    	 Location loc = new Location(player.getWorld(), pos.getX(), pos.getY(), pos.getZ());
			    	 
			    	 
						 
						 if (miningState.containsKey(player) && (miningState.get(player).equals("ABORT_DESTROY_BLOCK") || miningState.get(player).equals("DROP_ITEM") || miningState.get(player).equals("DROP_ALL_ITEMS"))) {
							 PacketContainer breakAnimation = protocolManager.createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
							 breakAnimation.getBlockPositionModifier().write(0, miningPosition.get(player));
							 breakAnimation.getIntegers().write(1, -1);
							 if ((miningState.get(player).equals("DROP_ITEM") || miningState.get(player).equals("DROP_ALL_ITEMS")) && miningTicks.get(player) != 0) {
							 	miningState.put(player, "START_DESTROY_BLOCK");
							 	Float breakSpeed = player.getWorld().getBlockAt(loc).getBreakSpeed(player);
							 	miningSpeed.put(player, breakSpeed);
							 }
							 
							  try {
								  protocolManager.sendServerPacket(player, breakAnimation);
							  } catch (InvocationTargetException e) {
								  // TODO Auto-generated catch block
								  e.printStackTrace();
							  }
						 }
						 
						 if (miningState.containsKey(player) && miningState.get(player).equals("START_DESTROY_BLOCK")) {
							  
							  miningTicks.put(player, miningTicks.get(player) + 1);
							  
							  PacketContainer breakAnimation = protocolManager.createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
							  breakAnimation.getBlockPositionModifier().write(0, miningPosition.get(player));
							  float miningLevel = skillsconfig.getInt("mining");
							  float mineSpeed = miningSpeed.get(player);
							  float buffedSpeed = mineSpeed * (1 + (miningLevel / 10f));
							  float totalPercentage = buffedSpeed + miningPercentage.get(player);
							  miningPercentage.put(player, totalPercentage);
							  int animationStage = (int) (totalPercentage * 10) - 1;
							  breakAnimation.getIntegers().write(1, animationStage);
							  if (animationStage == 9) {
								  Sound sound = null;
								try {
									World nmsWorld = ((CraftWorld) player.getWorld()).getHandle();
							        Block nmsBlock = nmsWorld.getType(new net.minecraft.core.BlockPosition(pos.getX(), pos.getY(), pos.getZ())).getBlock();
							        SoundEffectType soundEffectType = nmsBlock.getStepSound(null);
							        SoundEffect soundEffect = soundEffectType.c();//c is breaksound, f is hitsound

							        net.minecraft.resources.MinecraftKey nmsString = soundEffect.a();//return minecraftkey of soundeffect

							        sound = Sound.valueOf(nmsString.getNamespace().replace(".", "_").toUpperCase());
								} catch (SecurityException | IllegalArgumentException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							        
								  
								  
								  player.getWorld().playSound(loc, sound, SoundCategory.BLOCKS, 1, 1);
								  player.breakBlock(player.getWorld().getBlockAt(loc));
								  
								  miningState.put(player, "STOP_DESTROY_BLOCK");
							  }
							  try {
								  
								  protocolManager.sendServerPacket(player, breakAnimation);
							  } catch (InvocationTargetException e) {
								  // TODO Auto-generated catch block
								  e.printStackTrace();
							  }
						  }
			     	  }
			     }
		  }
	};
	
	void miningBuff() {
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, miningBuffRunnable, 1L, 1L);
	}
	


  	
	
	Runnable orcLightRunnable = new Runnable() {//check if any orcs are in sunlight
		 
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
//		    			 orcLight();
		    		 }
		    		 if (race == 2) {//elves
		    			 if (p.getWorld().getEnvironment().equals(Environment.NETHER)) {
		    				 p.setFireTicks(400);
		    			 }
		    		 }
		    	 }
		     }
		  }
		};
	
	void orcLight() {
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, orcLightRunnable, 5L, 5L);
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