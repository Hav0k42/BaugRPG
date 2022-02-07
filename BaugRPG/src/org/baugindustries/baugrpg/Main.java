package org.baugindustries.baugrpg;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.baugindustries.baugrpg.commands.BaugScroll;
import org.baugindustries.baugrpg.commands.Chat;
import org.baugindustries.baugrpg.commands.ChatTabCompleter;
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
import org.baugindustries.baugrpg.listeners.AlchemistThrowPotionListener;
import org.baugindustries.baugrpg.listeners.ArboratedStrikeListener;
import org.baugindustries.baugrpg.listeners.ArcaneJewelsListener;
import org.baugindustries.baugrpg.listeners.BlockExplodeListener;
import org.baugindustries.baugrpg.listeners.ChestBreakListener;
import org.baugindustries.baugrpg.listeners.ChestOpenListener;
import org.baugindustries.baugrpg.listeners.DisableRecipeListener;
import org.baugindustries.baugrpg.listeners.EfficientBotanyListener;
import org.baugindustries.baugrpg.listeners.ElfEatMeat;
import org.baugindustries.baugrpg.listeners.EnchantedPetalsListener;
import org.baugindustries.baugrpg.listeners.EntityExplodeListener;
import org.baugindustries.baugrpg.listeners.GildedFortuneListener;
import org.baugindustries.baugrpg.listeners.HorseListener;
import org.baugindustries.baugrpg.listeners.LavaFishingListener;
import org.baugindustries.baugrpg.listeners.LunarTransfusionListener;
import org.baugindustries.baugrpg.listeners.MagmaTransmutationListener;
import org.baugindustries.baugrpg.listeners.MinecartMoveListener;
import org.baugindustries.baugrpg.listeners.OnJoinListener;
import org.baugindustries.baugrpg.listeners.OnQuitListener;
import org.baugindustries.baugrpg.listeners.OrcEatMeat;
import org.baugindustries.baugrpg.listeners.OrcRageListener;
import org.baugindustries.baugrpg.listeners.PlayerAdvancementDoneListener;
import org.baugindustries.baugrpg.listeners.PlayerAttackListener;
import org.baugindustries.baugrpg.listeners.PlayerCloseInventoryListener;
import org.baugindustries.baugrpg.listeners.PlayerDamageListener;
import org.baugindustries.baugrpg.listeners.PlayerDeathListener;
import org.baugindustries.baugrpg.listeners.PlayerJumpListener;
import org.baugindustries.baugrpg.listeners.PlayerMineListener;
import org.baugindustries.baugrpg.listeners.PlayerRespawnListener;
import org.baugindustries.baugrpg.listeners.RadiantAnvilListener;
import org.baugindustries.baugrpg.listeners.SignBreakListener;
import org.baugindustries.baugrpg.listeners.SignShopListener;
import org.baugindustries.baugrpg.listeners.StarlightHealingListener;
import org.baugindustries.baugrpg.listeners.WitheredBeheadingListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ChooseClassListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ChooseRaceInventoryListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ConfirmClassListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ConfirmRaceInventoryListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.GeneralSkillTreeMenu;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.RaceSkillTreeMenu;
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
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World.Environment;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
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
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedDataWatcher.Serializer;

import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
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
	public HashMap<UUID, Long> steeledResolveCooldown = new HashMap<UUID, Long>();
	public HashMap<UUID, Long> steeledResolveTpTicks = new HashMap<UUID, Long>();
	public HashMap<UUID, Location> steeledResolveInitLoc = new HashMap<UUID, Location>();
	public HashMap<UUID, Integer> steeledResolveNpcId = new HashMap<UUID, Integer>();
	public HashMap<UUID, Long> shepherdsGraceCooldown = new HashMap<UUID, Long>();
	public HashMap<UUID, Long> shepherdsGraceTicks = new HashMap<UUID, Long>();
	public HashMap<UUID, Long> greedyReinforcementCooldown = new HashMap<UUID, Long>();
	public HashMap<UUID, Long> greedyReinforcementTicks = new HashMap<UUID, Long>();
	public List<Player> mountedPlayers = new ArrayList<Player>();
	public List<UUID> steeledResolveDisconnectedPlayers = new ArrayList<UUID>();
	public HashMap<UUID, Long> sneakingLunarArtificers = new HashMap<UUID, Long>();
	public HashMap<UUID, Long> petalTickTime = new HashMap<UUID, Long>();
	public HashMap<UUID, List<UUID>> glowingElvesPerArtificer = new HashMap<UUID, List<UUID>>();
	public HashMap<UUID, Long> starlightHealingTicks = new HashMap<UUID, Long>();
	public HashMap<UUID, Long> starlightHealingCooldown = new HashMap<UUID, Long>();
	public HashMap<UUID, Long> berserkerKillstreaks = new HashMap<UUID, Long>();
	public HashMap<UUID, Long> rageCooldown = new HashMap<UUID, Long>();
	public List<UUID> anvilUUIDs = new ArrayList<UUID>();
	public List<Location> anvilSpawnLocs = new ArrayList<Location>();
	public List<ArmorStandEntity> activeTrees = new ArrayList<ArmorStandEntity>();
	public List<List<ArmorStandEntity>> activeTornadoes = new ArrayList<List<ArmorStandEntity>>();
	public ScoreboardManager manager;
	public Scoreboard board;
	public ProtocolManager protocolManager;
	public CustomItems itemManager;
	public CustomInventories inventoryManager;
	
	
	//Listeners
	public OnJoinListener onJoinListener = new OnJoinListener(this);
	public OnQuitListener onQuitListener = new OnQuitListener(this);
	public PlayerCloseInventoryListener playerCloseInventoryListener = new PlayerCloseInventoryListener(this);
	public HorseListener horseListener = new HorseListener(this);
	public PlayerDeathListener playerDeathListener = new PlayerDeathListener(this);
	public PlayerRespawnListener playerRespawnListener = new PlayerRespawnListener(this);
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
	public EfficientBotanyListener efficientBotanyListener = new EfficientBotanyListener(this);
	public EnchantedPetalsListener enchantedPetalsListener = new EnchantedPetalsListener(this);
	public StarlightHealingListener starlightHealingListener = new StarlightHealingListener(this);
	public LunarTransfusionListener lunarTransfusionListener = new LunarTransfusionListener(this);
	public ArboratedStrikeListener arboratedStrikeListener = new ArboratedStrikeListener(this);
	public RadiantAnvilListener radiantAnvilListener = new RadiantAnvilListener(this);
	public GildedFortuneListener gildedFortuneListener = new GildedFortuneListener(this);
	public ArcaneJewelsListener arcaneJewelsListener = new ArcaneJewelsListener(this);
	public AlchemistThrowPotionListener alchemistThrowPotionListener = new AlchemistThrowPotionListener(this);
	public MagmaTransmutationListener magmaTransmutationListener = new MagmaTransmutationListener(this);
	public OrcRageListener orcRageListener = new OrcRageListener(this);
	public WitheredBeheadingListener witheredBeheadingListener = new WitheredBeheadingListener(this);
	public DisableRecipeListener disableRecipeListener = new DisableRecipeListener(this);
	public LavaFishingListener lavaFishingListener = new LavaFishingListener(this);
	
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
	public RaceSkillTreeMenu raceSkillTreeMenu = new RaceSkillTreeMenu(this);
	
	
	
	
	

	
	@Override
	public void onEnable() {
		 File defaultDir = new File(this.getDataFolder().getAbsolutePath());
		 defaultDir.mkdir();
		
		
		 manager = Bukkit.getScoreboardManager();
		 board = manager.getMainScoreboard();
		 this.getServer().getPluginManager().registerEvents(onJoinListener, this);
		 this.getServer().getPluginManager().registerEvents(onQuitListener, this);
		 this.getServer().getPluginManager().registerEvents(playerCloseInventoryListener, this);
		 this.getServer().getPluginManager().registerEvents(horseListener, this);
		 this.getServer().getPluginManager().registerEvents(playerDeathListener, this);
		 this.getServer().getPluginManager().registerEvents(playerRespawnListener, this);
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
		 this.getServer().getPluginManager().registerEvents(arcaneJewelsListener, this);
		 this.getServer().getPluginManager().registerEvents(alchemistThrowPotionListener, this);
		 this.getServer().getPluginManager().registerEvents(magmaTransmutationListener, this);
		 this.getServer().getPluginManager().registerEvents(orcRageListener, this);
		 this.getServer().getPluginManager().registerEvents(witheredBeheadingListener, this);
		 this.getServer().getPluginManager().registerEvents(disableRecipeListener, this);
		 this.getServer().getPluginManager().registerEvents(lavaFishingListener, this);
		 
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
		 this.getServer().getPluginManager().registerEvents(raceSkillTreeMenu, this);
		 this.getServer().getPluginManager().registerEvents(efficientBotanyListener, this);
		 this.getServer().getPluginManager().registerEvents(enchantedPetalsListener, this);
		 this.getServer().getPluginManager().registerEvents(starlightHealingListener, this);
		 this.getServer().getPluginManager().registerEvents(lunarTransfusionListener, this);
		 this.getServer().getPluginManager().registerEvents(arboratedStrikeListener, this);
		 this.getServer().getPluginManager().registerEvents(radiantAnvilListener, this);
		 this.getServer().getPluginManager().registerEvents(gildedFortuneListener, this);
		 
		 
		 new Pay(this);
		 new Balance(this);
		 new ResetRace(this);
		 new SetRace(this);
		 new Chat(this);
		 getCommand("chat").setTabCompleter(new ChatTabCompleter());
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
		 itemManager = new CustomItems(this);
		 inventoryManager = new CustomInventories(this);
		 
		 
		 Runnable craftsmanRegen = new Runnable() {
			  public void run() {
				  
				 Bukkit.getOnlinePlayers().forEach(player -> {
					 File skillsfile = new File(getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
					 FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
					 if (skillsconfig.contains("WoodlandCraftsman1") && skillsconfig.getBoolean("WoodlandCraftsman1")) {
						 if (player.getHealth() + 1 < 20) {
								player.setHealth(player.getHealth() + 1);
						 }
					 }
				 });
				 
			  }
		 };
		 getServer().getScheduler().scheduleSyncRepeatingTask(this, craftsmanRegen, 600L, 600L);
		 
		 
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
		 
		 protocolManager.addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Server.ENTITY_METADATA) {
	            @Override
	            public void onPacketSending(PacketEvent event){
	            	Player player = event.getPlayer();
	                PacketContainer packet = event.getPacket();
	                if (glowingElvesPerArtificer.containsKey(player.getUniqueId())) {
	                	List<UUID> nearbyPlayers = glowingElvesPerArtificer.get(player.getUniqueId());
	                	nearbyPlayers.forEach(uuid -> {
	                		Player nearbyPlayer = Bukkit.getPlayer(uuid);
	                		if (nearbyPlayer != null && nearbyPlayer.getEntityId() == packet.getIntegers().read(0)) {
	                			//Entity sent in packet is a player that we want to be glowing
	                			WrappedDataWatcher dataModifier = new WrappedDataWatcher();
	                			Serializer serializer = WrappedDataWatcher.Registry.get(Byte.class);
	                			dataModifier.setEntity(nearbyPlayer);
	                			dataModifier.setObject(0, serializer, (byte) (0x40), true);
	                		    packet.getWatchableCollectionModifier().write(0, dataModifier.getWatchableObjects());
	                		}
	                	});
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
		 List<Player> onlinePlayers = getOnlinePlayers();
		 for (int i = 0; i < getServer().getOnlinePlayers().size(); i++) {
			 channelManager.joinChannel(onlinePlayers.get(i), "Global Chat");
		 }
		 
		 orcLight();
		 miningBuff();
		 
		 for (Player player: getOnlinePlayers()) {
			 if (player.getVehicle() instanceof AbstractHorse) {
				 mountedPlayers.add(player);
			 }
		 }
		 
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
		 
		 File steeledResolvecooldownDatafile = new File(this.getDataFolder() + File.separator + "steeledResolveCooldownData.yml");
		 FileConfiguration steeledResolvecooldownDataconfig = YamlConfiguration.loadConfiguration(steeledResolvecooldownDatafile);
		 //Check to see if the file already exists. If not, create it.
		 if (!steeledResolvecooldownDatafile.exists()) {
			 try {
				 steeledResolvecooldownDatafile.createNewFile();
			 } catch (IOException e) {
				 e.printStackTrace();
			 }
		 }
		 for (String uuidStr: steeledResolvecooldownDataconfig.getKeys(true)) {
			 UUID uuid = UUID.fromString(uuidStr);
			 steeledResolveCooldown.put(uuid, steeledResolvecooldownDataconfig.getLong(uuidStr));
		 }
		 
		 
		 
		 
		 
		 File shepherdsGracecooldownDatafile = new File(this.getDataFolder() + File.separator + "shepherdsGraceCooldownData.yml");
		 FileConfiguration shepherdsGracecooldownDataconfig = YamlConfiguration.loadConfiguration(shepherdsGracecooldownDatafile);
		 //Check to see if the file already exists. If not, create it.
		 if (!shepherdsGracecooldownDatafile.exists()) {
			 try {
				 shepherdsGracecooldownDatafile.createNewFile();
			 } catch (IOException e) {
				 e.printStackTrace();
			 }
		 }
		 for (String uuidStr: shepherdsGracecooldownDataconfig.getKeys(true)) {
			 UUID uuid = UUID.fromString(uuidStr);
			 shepherdsGraceCooldown.put(uuid, shepherdsGracecooldownDataconfig.getLong(uuidStr));
		 }
		 
		 
		 File greedyReinforcementcooldownDatafile = new File(this.getDataFolder() + File.separator + "greedyReinforcementCooldownData.yml");
		 FileConfiguration greedyReinforcementcooldownDataconfig = YamlConfiguration.loadConfiguration(greedyReinforcementcooldownDatafile);
		 //Check to see if the file already exists. If not, create it.
		 if (!greedyReinforcementcooldownDatafile.exists()) {
			 try {
				 greedyReinforcementcooldownDatafile.createNewFile();
			 } catch (IOException e) {
				 e.printStackTrace();
			 }
		 }
		 for (String uuidStr: greedyReinforcementcooldownDataconfig.getKeys(true)) {
			 UUID uuid = UUID.fromString(uuidStr);
			 greedyReinforcementCooldown.put(uuid, greedyReinforcementcooldownDataconfig.getLong(uuidStr));
		 }
		 
		 
		 
		 File starlightHealingcooldownDatafile = new File(this.getDataFolder() + File.separator + "starlightHealingCooldownData.yml");
		 FileConfiguration starlightHealingcooldownDataconfig = YamlConfiguration.loadConfiguration(starlightHealingcooldownDatafile);
		 //Check to see if the file already exists. If not, create it.
		 if (!starlightHealingcooldownDatafile.exists()) {
			 try {
				 starlightHealingcooldownDatafile.createNewFile();
			 } catch (IOException e) {
				 e.printStackTrace();
			 }
		 }
		 for (String uuidStr: starlightHealingcooldownDataconfig.getKeys(true)) {
			 UUID uuid = UUID.fromString(uuidStr);
			 starlightHealingCooldown.put(uuid, starlightHealingcooldownDataconfig.getLong(uuidStr));
		 }
		 
		 File ragecooldownDatafile = new File(this.getDataFolder() + File.separator + "rageCooldownData.yml");
		 FileConfiguration ragecooldownDataconfig = YamlConfiguration.loadConfiguration(ragecooldownDatafile);
		 //Check to see if the file already exists. If not, create it.
		 if (!ragecooldownDatafile.exists()) {
			 try {
				 ragecooldownDatafile.createNewFile();
			 } catch (IOException e) {
				 e.printStackTrace();
			 }
		 }
		 for (String uuidStr: ragecooldownDataconfig.getKeys(true)) {
			 UUID uuid = UUID.fromString(uuidStr);
			 rageCooldown.put(uuid, ragecooldownDataconfig.getLong(uuidStr));
		 }
		 
		 
		 

		 float maxSpeed = 0.35f;
		 
		 Bukkit.getOnlinePlayers().forEach(player -> {
			 	File skillsfile = new File(getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
			 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
				if (skillsconfig.getBoolean("speedOn")) {
					player.setWalkSpeed((((maxSpeed - 0.2f) / 10f) * skillsconfig.getInt("speed"))+ 0.2f);
					player.setFlySpeed((((maxSpeed - 0.2f) / 10f) * skillsconfig.getInt("speed")) + 0.2f);
				} else {
					player.setWalkSpeed(0.2f);
					player.setFlySpeed(0.2f);
				}
				
				
				
				if (skillsconfig.getBoolean("regenOn")) {
					player.setSaturatedRegenRate(onJoinListener.getSaturationSlownessMultiplier() * (int)(((skillsconfig.getInt("regen") * -5f) / 9f) + (95f/9f)));
					player.setUnsaturatedRegenRate(onJoinListener.getSaturationSlownessMultiplier() * (int)(((skillsconfig.getInt("regen") * -40f) / 9f) + (760f/9f)));
				} else {
					player.setSaturatedRegenRate(onJoinListener.getSaturationSlownessMultiplier() * 10);
					player.setUnsaturatedRegenRate(onJoinListener.getSaturationSlownessMultiplier() * 80);
				}
		 });
		 
		 
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
							  float mineSpeed = 0;
							  if (miningSpeed.containsKey(player)) {
								  mineSpeed = miningSpeed.get(player);
							  }
							  float buffedSpeed = mineSpeed * (1 + (miningLevel / 10f));
							  float totalPercentage = buffedSpeed + miningPercentage.get(player);
							  miningPercentage.put(player, totalPercentage);
							  int animationStage = (int) (totalPercentage * 10) - 1;
							  breakAnimation.getIntegers().write(1, animationStage);
							  if (animationStage >= 9) {// used to be == 9, but when players mine too fast it skips nine, gets to 10 and keeps going up. This fixes it.
								  Sound sound = null;
								try {
									WorldServer nmsWorld = ((CraftWorld) player.getWorld()).getHandle();
							        Block nmsBlock = nmsWorld.a_(new net.minecraft.core.BlockPosition(pos.getX(), pos.getY(), pos.getZ())).b();
							        
							        SoundEffectType soundEffectType = nmsBlock.m(null);
							        SoundEffect soundEffect = soundEffectType.c();//c is breaksound, f is hitsound

							        net.minecraft.resources.MinecraftKey nmsString = soundEffect.a();//return minecraftkey of soundeffect
							        
							        sound = Sound.valueOf(nmsString.toString().replace(".", "_").toUpperCase().substring(10));
							        
								} catch (SecurityException | IllegalArgumentException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							        
								  
								  
								  player.getWorld().playSound(loc, sound, SoundCategory.BLOCKS, 1, 1);
								  Particle particle = Particle.BLOCK_DUST;
								  player.getWorld().spawnParticle(particle, loc.getX() + 0.5, loc.getY() + 0.5, loc.getZ() + 0.5, 20, 0.1, 0.1, 0.1, player.getWorld().getBlockAt(loc).getBlockData());
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
			    					 if (p.getPotionEffect(PotionEffectType.FIRE_RESISTANCE) == null) {
			    					 p.setFireTicks(skyLight * 20);	
			    					 }
			    				 }
			    			 }
		    			 }
//		    			 orcLight();
		    		 }
		    		 if (race == 2) {//elves
		    			 if (p.getWorld().getEnvironment().equals(Environment.NETHER)) {
		    				 if (p.getPotionEffect(PotionEffectType.FIRE_RESISTANCE) == null) {
			    				 p.setFireTicks(400);
			    				 p.damage(4);
		    				 }
		    			 }
		    		 }
		    		 if (race == 3) {//dwarven miner haste buff
		    			File skillsfile = new File(getDataFolder() + File.separator + "skillsData" + File.separator + p.getUniqueId() + ".yml");
		 	    	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
		 	    	 	if (skillsconfig.contains("GildedMiner3") && skillsconfig.getBoolean("GildedMiner3")) {
		 	    			p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 6, 1));
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
		
		File steeledResolvecooldownDatafile = new File(this.getDataFolder() + File.separator + "steeledResolveCooldownData.yml");
		FileConfiguration steeledResolvecooldownDataconfig = new YamlConfiguration();
		//Check to see if the file already exists. If not, create it.
		if (!steeledResolvecooldownDatafile.exists()) {
			try {
				steeledResolvecooldownDatafile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		steeledResolveCooldown.forEach((key, value) -> {
				UUID uuid = key;
				steeledResolvecooldownDataconfig.set(uuid.toString(), value);
		    });
		
		
		
		
		File steeledResolveDisconnectedDatafile = new File(this.getDataFolder() + File.separator + "steeledResolveDisconnectedData.yml");
		FileConfiguration steeledResolveDisconnectedDataconfig = new YamlConfiguration();
		
		steeledResolveTpTicks.forEach((key, value) -> {
			if (value < 401) {
				steeledResolveDisconnectedPlayers.add(key);
			}
		});
		
		
		for (UUID uuid: steeledResolveDisconnectedPlayers) {
			steeledResolveDisconnectedDataconfig.set(uuid.toString(), steeledResolveInitLoc.get(uuid));
		}
		
		
		File shepherdsGracecooldownDatafile = new File(this.getDataFolder() + File.separator + "shepherdsGraceCooldownData.yml");
		FileConfiguration shepherdsGracecooldownDataconfig = new YamlConfiguration();
		
		if (!shepherdsGracecooldownDatafile.exists()) {
			try {
				shepherdsGracecooldownDatafile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		shepherdsGraceCooldown.forEach((key, value) -> {
			UUID uuid = key;
			shepherdsGracecooldownDataconfig.set(uuid.toString(), value);
	    });
		
		
		
		File greedyReinforcementcooldownDatafile = new File(this.getDataFolder() + File.separator + "greedyReinforcementCooldownData.yml");
		FileConfiguration greedyReinforcementcooldownDataconfig = new YamlConfiguration();
		
		if (!greedyReinforcementcooldownDatafile.exists()) {
			try {
				greedyReinforcementcooldownDatafile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		greedyReinforcementCooldown.forEach((key, value) -> {
			UUID uuid = key;
			greedyReinforcementcooldownDataconfig.set(uuid.toString(), value);
	    });
		
		
		
		File starlightHealingcooldownDatafile = new File(this.getDataFolder() + File.separator + "starlightHealingCooldownData.yml");
		FileConfiguration starlightHealingcooldownDataconfig = new YamlConfiguration();
		
		if (!starlightHealingcooldownDatafile.exists()) {
			try {
				starlightHealingcooldownDatafile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		starlightHealingCooldown.forEach((key, value) -> {
			UUID uuid = key;
			starlightHealingcooldownDataconfig.set(uuid.toString(), value);
	    });
		
		File ragecooldownDatafile = new File(this.getDataFolder() + File.separator + "rageCooldownData.yml");
		FileConfiguration ragecooldownDataconfig = new YamlConfiguration();
		
		if (!ragecooldownDatafile.exists()) {
			try {
				ragecooldownDatafile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		rageCooldown.forEach((key, value) -> {
			UUID uuid = key;
			ragecooldownDataconfig.set(uuid.toString(), value);
	    });
		
		
		anvilUUIDs.forEach(uuid -> {
			getServer().getEntity(uuid).remove();
	    });
		
		anvilSpawnLocs.forEach(loc -> {
			loc.getBlock().setType(Material.AIR);
		});
		
		activeTrees.forEach(tree -> {
			tree.kill();
		});
		
		activeTornadoes.forEach(tornado -> {
			tornado.forEach(layer -> {
				layer.kill();
			});
		});
		
		
		
		
		
		
		try {
			steeledResolvecooldownDataconfig.save(steeledResolvecooldownDatafile);
			steeledResolveDisconnectedDataconfig.save(steeledResolveDisconnectedDatafile);
			shepherdsGracecooldownDataconfig.save(shepherdsGracecooldownDatafile);
			starlightHealingcooldownDataconfig.save(starlightHealingcooldownDatafile);
			ragecooldownDataconfig.save(ragecooldownDatafile);
			greedyReinforcementcooldownDataconfig.save(greedyReinforcementcooldownDatafile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		List<Player> allOnlinePlayers = new ArrayList<Player>();
		OfflinePlayer[] allOfflinePlayers = getServer().getOfflinePlayers();
		for (int i = 0; i < allOfflinePlayers.length; i++) {
			if (allOfflinePlayers[i].isOnline()) {
				allOnlinePlayers.add(allOfflinePlayers[i].getPlayer());
			}
		}
		return allOnlinePlayers;
	}
	
	public List<Player> getOnlineMen() {//I know its better to keep track of which race is online at what time by updating a list of players for each race when they join, but at this point ive realized it too late and its too much work for me to go back and fix it right now. I may later.
		List<Player> allOnlinePlayers = new ArrayList<Player>();
		OfflinePlayer[] allOfflinePlayers = getServer().getOfflinePlayers();
		for (int i = 0; i < allOfflinePlayers.length; i++) {
			if (allOfflinePlayers[i].isOnline()) {
				Player player = allOfflinePlayers[i].getPlayer();
				if (player.getPersistentDataContainer().has(new NamespacedKey(this, "Race"), PersistentDataType.INTEGER) && player.getPersistentDataContainer().get(new NamespacedKey(this, "Race"), PersistentDataType.INTEGER) == 1) {
					allOnlinePlayers.add(player);
				}
			}
		}
		return allOnlinePlayers;
	}
	
	
	
}