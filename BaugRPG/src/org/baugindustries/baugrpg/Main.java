package org.baugindustries.baugrpg;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.RandomStringUtils;
import org.baugindustries.baugrpg.commands.BaugScroll;
import org.baugindustries.baugrpg.commands.Chat;
import org.baugindustries.baugrpg.commands.ChatTabCompleter;
import org.baugindustries.baugrpg.commands.ChunkClaim;
import org.baugindustries.baugrpg.commands.Claim;
import org.baugindustries.baugrpg.commands.ClaimTabCompleter;
import org.baugindustries.baugrpg.commands.GetCustomItem;
import org.baugindustries.baugrpg.commands.GetCustomItemTabCompleter;
import org.baugindustries.baugrpg.commands.Map;
import org.baugindustries.baugrpg.commands.RaceWizard;
import org.baugindustries.baugrpg.commands.ResetRace;
import org.baugindustries.baugrpg.commands.SetPoi;
import org.baugindustries.baugrpg.commands.SetRace;
import org.baugindustries.baugrpg.commands.SetpoiTabCompleter;
import org.baugindustries.baugrpg.commands.Spawn;
import org.baugindustries.baugrpg.commands.Tpa;
import org.baugindustries.baugrpg.commands.Tpaccept;
import org.baugindustries.baugrpg.commands.Tpdeny;
import org.baugindustries.baugrpg.commands.Tphere;
import org.baugindustries.baugrpg.commands.Warp;
import org.baugindustries.baugrpg.commands.WarpAccept;
import org.baugindustries.baugrpg.commands.WarpTabCompleter;
import org.baugindustries.baugrpg.commands.econ.Balance;
import org.baugindustries.baugrpg.commands.econ.BankBal;
import org.baugindustries.baugrpg.commands.econ.Deposit;
import org.baugindustries.baugrpg.commands.econ.Pay;
import org.baugindustries.baugrpg.commands.econ.SetBal;
import org.baugindustries.baugrpg.commands.econ.Withdraw;
import org.baugindustries.baugrpg.customitems.CustomItemRenamingListener;
import org.baugindustries.baugrpg.customitems.dwarves.arcane_jeweler.basic.GoldRingListener;
import org.baugindustries.baugrpg.customitems.dwarves.arcane_jeweler.basic.ReinforcedRingListener;
import org.baugindustries.baugrpg.customitems.dwarves.arcane_jeweler.intermediate.BejeweledCompassListener;
import org.baugindustries.baugrpg.customitems.dwarves.arcane_jeweler.intermediate.StuddedSetListener;
import org.baugindustries.baugrpg.customitems.dwarves.gilded_miner.basic.EnduranceRuneListener;
import org.baugindustries.baugrpg.customitems.dwarves.gilded_miner.basic.HardenedStoneListener;
import org.baugindustries.baugrpg.customitems.dwarves.gilded_miner.basic.RockyTransmuterListener;
import org.baugindustries.baugrpg.customitems.dwarves.gilded_miner.intermediate.DrillListener;
import org.baugindustries.baugrpg.customitems.dwarves.gilded_miner.intermediate.ProtectorStoneListener;
import org.baugindustries.baugrpg.customitems.dwarves.gilded_miner.intermediate.RobustRuneListener;
import org.baugindustries.baugrpg.customitems.dwarves.radiant_metallurgist.basic.FerrousHarvesterListener;
import org.baugindustries.baugrpg.customitems.dwarves.radiant_metallurgist.basic.ForgersScrollListener;
import org.baugindustries.baugrpg.customitems.dwarves.radiant_metallurgist.basic.PumiceListener;
import org.baugindustries.baugrpg.customitems.dwarves.radiant_metallurgist.basic.RhyoliteListener;
import org.baugindustries.baugrpg.customitems.dwarves.radiant_metallurgist.intermediate.FlamelashListener;
import org.baugindustries.baugrpg.customitems.dwarves.radiant_metallurgist.intermediate.HandForgeListener;
import org.baugindustries.baugrpg.customitems.dwarves.radiant_metallurgist.intermediate.RadiantBoreListener;
import org.baugindustries.baugrpg.customitems.elves.enchanted_botanist.advanced.AqueousSolutionListener;
import org.baugindustries.baugrpg.customitems.elves.enchanted_botanist.advanced.CorruptedSoilListener;
import org.baugindustries.baugrpg.customitems.elves.enchanted_botanist.advanced.EmblemOfTheBlossomListener;
import org.baugindustries.baugrpg.customitems.elves.enchanted_botanist.advanced.GoldenFlowerListener;
import org.baugindustries.baugrpg.customitems.elves.enchanted_botanist.advanced.ScytheListener;
import org.baugindustries.baugrpg.customitems.elves.enchanted_botanist.basic.AssortedPetalsListener;
import org.baugindustries.baugrpg.customitems.elves.enchanted_botanist.basic.CactusGreavesListener;
import org.baugindustries.baugrpg.customitems.elves.enchanted_botanist.basic.FloralRootsListener;
import org.baugindustries.baugrpg.customitems.elves.enchanted_botanist.basic.FloralTransmuterListener;
import org.baugindustries.baugrpg.customitems.elves.enchanted_botanist.intermediate.DemetersBenevolenceListener;
import org.baugindustries.baugrpg.customitems.elves.enchanted_botanist.intermediate.EnrichedHoeListener;
import org.baugindustries.baugrpg.customitems.elves.enchanted_botanist.intermediate.EnrichedSoilListener;
import org.baugindustries.baugrpg.customitems.elves.enchanted_botanist.intermediate.FloralPoulticeListener;
import org.baugindustries.baugrpg.customitems.elves.enchanted_botanist.intermediate.PotentHoneyListener;
import org.baugindustries.baugrpg.customitems.elves.lunar_artificier.basic.LunarDebrisListener;
import org.baugindustries.baugrpg.customitems.elves.lunar_artificier.basic.NebulousAuraListener;
import org.baugindustries.baugrpg.customitems.elves.lunar_artificier.intermediate.DarkMatterListener;
import org.baugindustries.baugrpg.customitems.elves.lunar_artificier.intermediate.LightBowListener;
import org.baugindustries.baugrpg.customitems.elves.lunar_artificier.intermediate.LightShieldListener;
import org.baugindustries.baugrpg.customitems.elves.lunar_artificier.intermediate.MeteoriteListener;
import org.baugindustries.baugrpg.customitems.elves.lunar_artificier.intermediate.VoidStoneListener;
import org.baugindustries.baugrpg.customitems.elves.woodland_craftsman.basic.ElvenThreadListener;
import org.baugindustries.baugrpg.customitems.elves.woodland_craftsman.basic.EnrichedLogsListener;
import org.baugindustries.baugrpg.customitems.elves.woodland_craftsman.basic.SaplingTransmuterListener;
import org.baugindustries.baugrpg.customitems.elves.woodland_craftsman.basic.SawListener;
import org.baugindustries.baugrpg.customitems.elves.woodland_craftsman.intermediate.ElvenWeaveListener;
import org.baugindustries.baugrpg.customitems.elves.woodland_craftsman.intermediate.EnrichedWoodSetListener;
import org.baugindustries.baugrpg.customitems.elves.woodland_craftsman.intermediate.RustedStaffListener;
import org.baugindustries.baugrpg.customitems.elves.woodland_craftsman.intermediate.ToolbeltListener;
import org.baugindustries.baugrpg.customitems.men.stable_master.advanced.CordListener;
import org.baugindustries.baugrpg.customitems.men.stable_master.advanced.EmblemOfTheStallionListener;
import org.baugindustries.baugrpg.customitems.men.stable_master.advanced.RegenerativeHorseArmorListener;
import org.baugindustries.baugrpg.customitems.men.stable_master.advanced.RuggedSwordListener;
import org.baugindustries.baugrpg.customitems.men.stable_master.basic.HorsehairListener;
import org.baugindustries.baugrpg.customitems.men.stable_master.basic.HorseshoeListener;
import org.baugindustries.baugrpg.customitems.men.stable_master.basic.MorralListener;
import org.baugindustries.baugrpg.customitems.men.stable_master.intermediate.BridleListener;
import org.baugindustries.baugrpg.customitems.men.stable_master.intermediate.RopeListener;
import org.baugindustries.baugrpg.customitems.men.stable_master.intermediate.SpearListener;
import org.baugindustries.baugrpg.customitems.men.stable_master.intermediate.WhistleListener;
import org.baugindustries.baugrpg.customitems.men.steeled_armorer.advanced.EmblemOfTheShieldListener;
import org.baugindustries.baugrpg.customitems.men.steeled_armorer.advanced.SteelMeshListener;
import org.baugindustries.baugrpg.customitems.men.steeled_armorer.advanced.SteelPlateSetListener;
import org.baugindustries.baugrpg.customitems.men.steeled_armorer.basic.FeatheredShoesListener;
import org.baugindustries.baugrpg.customitems.men.steeled_armorer.basic.IronHammerListener;
import org.baugindustries.baugrpg.customitems.men.steeled_armorer.basic.MeshListener;
import org.baugindustries.baugrpg.customitems.men.steeled_armorer.intermediate.HardenedMeshListener;
import org.baugindustries.baugrpg.customitems.men.steeled_armorer.intermediate.IronPlateSetListener;
import org.baugindustries.baugrpg.customitems.men.steeled_armorer.intermediate.KnockbackShieldListener;
import org.baugindustries.baugrpg.customitems.men.verdant_shepherd.advanced.EmblemOfThePastureListener;
import org.baugindustries.baugrpg.customitems.men.verdant_shepherd.advanced.EssenceOfFaunaListener;
import org.baugindustries.baugrpg.customitems.men.verdant_shepherd.advanced.GaiasWrathListener;
import org.baugindustries.baugrpg.customitems.men.verdant_shepherd.advanced.SteelWoolListener;
import org.baugindustries.baugrpg.customitems.men.verdant_shepherd.advanced.VerdantMedallionListener;
import org.baugindustries.baugrpg.customitems.men.verdant_shepherd.basic.DryGrassListener;
import org.baugindustries.baugrpg.customitems.men.verdant_shepherd.basic.HempListener;
import org.baugindustries.baugrpg.customitems.men.verdant_shepherd.basic.LanolinListener;
import org.baugindustries.baugrpg.customitems.men.verdant_shepherd.basic.MerinoWoolListener;
import org.baugindustries.baugrpg.customitems.men.verdant_shepherd.basic.VealListener;
import org.baugindustries.baugrpg.customitems.men.verdant_shepherd.intermediate.CorruptedStaffListener;
import org.baugindustries.baugrpg.customitems.men.verdant_shepherd.intermediate.CrookListener;
import org.baugindustries.baugrpg.customitems.men.verdant_shepherd.intermediate.ShepherdsCompassListener;
import org.baugindustries.baugrpg.customitems.men.verdant_shepherd.intermediate.WaxListener;
import org.baugindustries.baugrpg.customitems.orcs.dark_alchemist.basic.CrimsonPowderListener;
import org.baugindustries.baugrpg.customitems.orcs.dark_alchemist.basic.FlamingBucketListener;
import org.baugindustries.baugrpg.customitems.orcs.dark_alchemist.basic.FungalRootsListener;
import org.baugindustries.baugrpg.customitems.orcs.dark_alchemist.basic.HypnoticRingListener;
import org.baugindustries.baugrpg.customitems.orcs.dark_alchemist.intermediate.BoneMarrowListener;
import org.baugindustries.baugrpg.customitems.orcs.dark_alchemist.intermediate.MagicMirrorListener;
import org.baugindustries.baugrpg.customitems.orcs.dark_alchemist.intermediate.RodOfShadowsListener;
import org.baugindustries.baugrpg.customitems.orcs.dark_alchemist.intermediate.WandOfDisplacementListener;
import org.baugindustries.baugrpg.customitems.orcs.enraged_berserker.basic.EtherealWoodListener;
import org.baugindustries.baugrpg.customitems.orcs.enraged_berserker.basic.HellstoneListener;
import org.baugindustries.baugrpg.customitems.orcs.enraged_berserker.basic.HoglinTuskListener;
import org.baugindustries.baugrpg.customitems.orcs.enraged_berserker.basic.RageStoneListener;
import org.baugindustries.baugrpg.customitems.orcs.enraged_berserker.basic.SpectralWardListener;
import org.baugindustries.baugrpg.customitems.orcs.enraged_berserker.intermediate.BlazingFuryListener;
import org.baugindustries.baugrpg.customitems.orcs.enraged_berserker.intermediate.SpectralHarnessListener;
import org.baugindustries.baugrpg.customitems.orcs.enraged_berserker.intermediate.TheShredderListener;
import org.baugindustries.baugrpg.customitems.orcs.greedy_scrapper.basic.DemonicWrenchListener;
import org.baugindustries.baugrpg.customitems.orcs.greedy_scrapper.basic.MagmaStoneListener;
import org.baugindustries.baugrpg.customitems.orcs.greedy_scrapper.basic.PebblesListener;
import org.baugindustries.baugrpg.customitems.orcs.greedy_scrapper.intermediate.BoltsListener;
import org.baugindustries.baugrpg.customitems.orcs.greedy_scrapper.intermediate.FlamedashBootsListener;
import org.baugindustries.baugrpg.customitems.orcs.greedy_scrapper.intermediate.FragmentedSetListener;
import org.baugindustries.baugrpg.listeners.AlchemistThrowPotionListener;
import org.baugindustries.baugrpg.listeners.ArboratedStrikeListener;
import org.baugindustries.baugrpg.listeners.ArcaneJewelsListener;
import org.baugindustries.baugrpg.listeners.BlockExplodeListener;
import org.baugindustries.baugrpg.listeners.BrewIllegalPotionListener;
import org.baugindustries.baugrpg.listeners.ChestBreakListener;
import org.baugindustries.baugrpg.listeners.ChestOpenListener;
import org.baugindustries.baugrpg.listeners.ClickedTextCommandListener;
import org.baugindustries.baugrpg.listeners.DisableRecipeListener;
import org.baugindustries.baugrpg.listeners.EfficientBotanyListener;
import org.baugindustries.baugrpg.listeners.ElfEatMeat;
import org.baugindustries.baugrpg.listeners.EnchantedPetalsListener;
import org.baugindustries.baugrpg.listeners.EndermanGriefingListener;
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
import org.baugindustries.baugrpg.listeners.PlayerEnterChunkListener;
import org.baugindustries.baugrpg.listeners.PlayerGetExperience;
import org.baugindustries.baugrpg.listeners.PlayerJumpListener;
import org.baugindustries.baugrpg.listeners.PlayerMineListener;
import org.baugindustries.baugrpg.listeners.PlayerRespawnListener;
import org.baugindustries.baugrpg.listeners.RadiantAnvilListener;
import org.baugindustries.baugrpg.listeners.ResetRaceListener;
import org.baugindustries.baugrpg.listeners.SignBreakListener;
import org.baugindustries.baugrpg.listeners.SignShopListener;
import org.baugindustries.baugrpg.listeners.StarlightHealingListener;
import org.baugindustries.baugrpg.listeners.UseBrewingStandListener;
import org.baugindustries.baugrpg.listeners.WitheredBeheadingListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ChooseClassListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ChooseRaceInventoryListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ConfirmClassListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ConfirmRaceInventoryListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.GeneralSkillTreeMenu;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.GovernmentMenu;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.RaceSkillTreeMenu;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.SkillTreeMenu;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.VotingMenu;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Dwarves.GoldConversionMenu;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Dwarves.ScrollsOfBaugDwarvesInventoryListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Elves.ConfirmStepDownMenu;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Elves.ScrollsOfBaugElvesInventoryListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Elves.CommunismHub.ElvesCommunismEnderChestListListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Elves.CommunismHub.ElvesCommunismHubInventoryListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Elves.CommunismHub.ElvesCommunismInventoryListListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Men.AppointKingMenu;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Men.ConfirmAppointKingMenu;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Men.ScrollsOfBaugMenInventoryListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Orcs.ExecutionMenuListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Orcs.ScrollsOfBaugOrcsInventoryListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Wizards.AllRecipesInventoryListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Wizards.ClassDisplayedRecipeInventoryListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Wizards.CustomItemClassListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Wizards.FeatureManagement;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Wizards.ScrollsOfBaugWizardsInventoryListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Wizards.PlayerSnooping.PlayerSnoopingEnderChestListListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Wizards.PlayerSnooping.PlayerSnoopingHubInventoryListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Wizards.PlayerSnooping.PlayerSnoopingInventoryListListener;
import org.baugindustries.baugrpg.listeners.Crafting.AnvilListener;
import org.baugindustries.baugrpg.listeners.Crafting.BrewingStandListener;
import org.baugindustries.baugrpg.listeners.Crafting.CartographyTableListener;
import org.baugindustries.baugrpg.listeners.Crafting.ComposterListener;
import org.baugindustries.baugrpg.listeners.Crafting.CraftingTableListener;
import org.baugindustries.baugrpg.listeners.Crafting.DisplayedRecipeInventoryListener;
import org.baugindustries.baugrpg.listeners.Crafting.DisplayedUsesInventoryListener;
import org.baugindustries.baugrpg.listeners.Crafting.EntityDropRecipeScroll;
import org.baugindustries.baugrpg.listeners.Crafting.FurnaceListener;
import org.baugindustries.baugrpg.listeners.Crafting.LearnedRecipesInventoryListener;
import org.baugindustries.baugrpg.listeners.Crafting.LoomListener;
import org.baugindustries.baugrpg.listeners.Crafting.PlayerPickUpRecipeScroll;
import org.baugindustries.baugrpg.listeners.Crafting.SmithingTableListener;
import org.baugindustries.baugrpg.protection.ChunkProtection;
import org.baugindustries.baugrpg.protection.ClaimProtection;
import org.baugindustries.baugrpg.protection.Claiming;
import org.baugindustries.baugrpg.protection.SpawnProtection;
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
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

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
	public HashMap<String, Player> warpHashMap = new HashMap<String, Player>();
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
	
	public List<UUID> lawSuggestionEscape = new ArrayList<UUID>();
	public HashMap<UUID, Integer> reportCrimeEscape = new HashMap<UUID, Integer>();
	public HashMap<UUID, String> reportCrimeMap = new HashMap<UUID, String>();
	public List<UUID> draftLawEscape = new ArrayList<UUID>();
	public HashMap<UUID, Integer> leaderPunishEscape = new HashMap<UUID, Integer>();
	public HashMap<UUID, Integer> elfPunishEscape = new HashMap<UUID, Integer>();
	public List<UUID> resetRaceEscape = new ArrayList<UUID>();
	
	public UUID taxDwarvesEscape = null;
	public UUID orcVictim = null;
	
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
	public ClickedTextCommandListener clickedTextCommandListener = new ClickedTextCommandListener(this);
	public PlayerEnterChunkListener playerEnterChunkListener = new PlayerEnterChunkListener(this);
	public UseBrewingStandListener useBrewingStandListener = new UseBrewingStandListener(this);
	public BrewIllegalPotionListener brewIllegalPotionListener = new BrewIllegalPotionListener(this);
	
	public EndermanGriefingListener endermanGriefingListener = new EndermanGriefingListener(this);
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
	public CustomItemClassListener customItemClassListener = new CustomItemClassListener(this);
	public AllRecipesInventoryListener allRecipesInventoryListener = new AllRecipesInventoryListener(this);
	public ClassDisplayedRecipeInventoryListener classDisplayedRecipeInventoryListener = new ClassDisplayedRecipeInventoryListener(this);
	public SkillTreeMenu skillTreeMenu = new SkillTreeMenu(this);
	public GeneralSkillTreeMenu generalSkillTreeMenu = new GeneralSkillTreeMenu(this);
	public PlayerAdvancementDoneListener playerAdvancementDoneListener = new PlayerAdvancementDoneListener(this);
	public ChooseClassListener chooseClassListener = new ChooseClassListener(this);
	public ConfirmClassListener confirmClassListener = new ConfirmClassListener(this);
	public RaceSkillTreeMenu raceSkillTreeMenu = new RaceSkillTreeMenu(this);
	public GovernmentMenu governmentMenu = new GovernmentMenu(this);
	public VotingMenu votingMenu = new VotingMenu(this);
	public AppointKingMenu appointKingMenu = new AppointKingMenu(this);
	public ConfirmAppointKingMenu confirmAppointKingMenu = new ConfirmAppointKingMenu(this);
	public ConfirmStepDownMenu confirmStepDownMenu = new ConfirmStepDownMenu(this);
	public ExecutionMenuListener executionMenuListener = new ExecutionMenuListener(this);
	public SpawnProtection spawnProtection = new SpawnProtection(this);
	public ChunkProtection chunkProtection = new ChunkProtection(this);
	public ClaimProtection claimProtection = new ClaimProtection(this);
	public Claiming claiming = new Claiming(this);
	public PlayerGetExperience playerGetExperience = new PlayerGetExperience(this);

	public EntityDropRecipeScroll entityDropRecipeScroll = new EntityDropRecipeScroll(this);
	public PlayerPickUpRecipeScroll playerPickUpRecipeScroll = new PlayerPickUpRecipeScroll(this);
	public LearnedRecipesInventoryListener learnedRecipesInventoryListener = new LearnedRecipesInventoryListener(this);
	public DisplayedRecipeInventoryListener displayedRecipeInventoryListener = new DisplayedRecipeInventoryListener(this);
	public DisplayedUsesInventoryListener displayedUsesInventoryListener = new DisplayedUsesInventoryListener(this);
	public CraftingTableListener craftingTableListener = new CraftingTableListener(this);
	public AnvilListener anvilListener = new AnvilListener(this);
	public BrewingStandListener brewingStandListener = new BrewingStandListener(this);
	public CartographyTableListener cartographyTableListener = new CartographyTableListener(this);
	public ComposterListener composterListener = new ComposterListener(this);
	public FurnaceListener furnaceListener = new FurnaceListener(this);
	public LoomListener loomListener = new LoomListener(this);
	public SmithingTableListener smithingTableListener = new SmithingTableListener(this);

	

	public ResetRaceListener resetRaceListener = new ResetRaceListener(this);
	
	
	//CustomItems
	
	public CustomItemRenamingListener customItemRenamingListener = new CustomItemRenamingListener(this);
	
	//Basic
	
	public HorsehairListener horsehairListener = new HorsehairListener(this);
	public HorseshoeListener horseshoeListener = new HorseshoeListener(this);
	public MorralListener morralListener = new MorralListener(this);
	public MeshListener meshListener = new MeshListener(this);
	public IronHammerListener ironHammerListener = new IronHammerListener(this);
	public FeatheredShoesListener featheredShoesListener = new FeatheredShoesListener(this);
	public MerinoWoolListener merinoWoolListener = new MerinoWoolListener(this);
	public LanolinListener lanolinListener = new LanolinListener(this);
	public DryGrassListener dryGrassListener = new DryGrassListener(this);
	public VealListener vealListener = new VealListener(this);
	public HempListener hempListener = new HempListener(this);
	public AssortedPetalsListener assortedPetalsListener = new AssortedPetalsListener(this);
	public FloralRootsListener floralRootsListener = new FloralRootsListener(this);
	public FloralTransmuterListener floralTransmuterListener = new FloralTransmuterListener(this);
	public CactusGreavesListener cactusGreavesListener = new CactusGreavesListener(this);
	public ElvenThreadListener elvenThreadListener = new ElvenThreadListener(this);
	public EnrichedLogsListener enrichedLogsListener = new EnrichedLogsListener(this);
	public SaplingTransmuterListener saplingTransmuterListener = new SaplingTransmuterListener(this);
	public SawListener sawListener = new SawListener(this);
	public LunarDebrisListener lunarDebrisListener = new LunarDebrisListener(this);
	public NebulousAuraListener nebulousAuraListener = new NebulousAuraListener(this);
	public RhyoliteListener rhyoliteListener = new RhyoliteListener(this);
	public PumiceListener pumiceListener = new PumiceListener(this);
	public ForgersScrollListener forgersScrollListener = new ForgersScrollListener(this);
	public FerrousHarvesterListener ferrousHarvesterListener = new FerrousHarvesterListener(this);
	public ReinforcedRingListener reinforcedRingListener = new ReinforcedRingListener(this);
	public GoldRingListener goldRingListener = new GoldRingListener(this);
	public HardenedStoneListener hardenedStoneListener = new HardenedStoneListener(this);
	public EnduranceRuneListener enduranceRuneListener = new EnduranceRuneListener(this);
	public RockyTransmuterListener rockyTransmuterListener = new RockyTransmuterListener(this);
	public FungalRootsListener fungalRootsListener = new FungalRootsListener(this);
	public CrimsonPowderListener crimsonPowderListener = new CrimsonPowderListener(this);
	public HypnoticRingListener hypnoticRingListener = new HypnoticRingListener(this);
	public FlamingBucketListener flamingBucketListener = new FlamingBucketListener(this);
	public HellstoneListener hellstoneListener = new HellstoneListener(this);
	public EtherealWoodListener etherealWoodListener = new EtherealWoodListener(this);
	public HoglinTuskListener hoglinTuskListener = new HoglinTuskListener(this);
	public RageStoneListener rageStoneListener = new RageStoneListener(this);
	public SpectralWardListener spectralWardListener = new SpectralWardListener(this);
	public PebblesListener pebblesListener = new PebblesListener(this);
	public MagmaStoneListener magmaStoneListener = new MagmaStoneListener(this);
	public DemonicWrenchListener demonicWrenchListener = new DemonicWrenchListener(this);
	
	//Intermediate

	public RopeListener ropeListener = new RopeListener(this);
	public SpearListener spearListener = new SpearListener(this);
	public WhistleListener whistleListener = new WhistleListener(this);
	public BridleListener bridleListener = new BridleListener(this);
	public HardenedMeshListener hardenedMeshListener = new HardenedMeshListener(this);
	public KnockbackShieldListener knockbackShieldListener = new KnockbackShieldListener(this);
	public IronPlateSetListener ironPlateSetListener = new IronPlateSetListener(this);
	public WaxListener waxListener = new WaxListener(this);
	public CrookListener crookListener = new CrookListener(this);
	public ShepherdsCompassListener shepherdsCompassListener = new ShepherdsCompassListener(this);
	public CorruptedStaffListener corruptedStaffListener = new CorruptedStaffListener(this);
	public FloralPoulticeListener floralPoulticeListener = new FloralPoulticeListener(this);
	public EnrichedSoilListener enrichedSoilListener = new EnrichedSoilListener(this);
	public EnrichedHoeListener enrichedHoeListener = new EnrichedHoeListener(this);
	public DemetersBenevolenceListener demetersBenevolenceListener = new DemetersBenevolenceListener(this);
	public PotentHoneyListener potentHoneyListener = new PotentHoneyListener(this);
	public ElvenWeaveListener elvenWeaveListener = new ElvenWeaveListener(this);
	public ToolbeltListener toolbeltListener = new ToolbeltListener(this);
	public RustedStaffListener rustedStaffListener = new RustedStaffListener(this);
	public EnrichedWoodSetListener enrichedWoodSetListener = new EnrichedWoodSetListener(this);
	public MeteoriteListener meteoriteListener = new MeteoriteListener(this);
	public DarkMatterListener darkMatterListener = new DarkMatterListener(this);
	public LightShieldListener lightShieldListener = new LightShieldListener(this);
	public LightBowListener lightBowListener = new LightBowListener(this);
	public VoidStoneListener voidStoneListener = new VoidStoneListener(this);
	public RadiantBoreListener radiantBoreListener = new RadiantBoreListener(this);
	public FlamelashListener flamelashListener = new FlamelashListener(this);
	public HandForgeListener handForgeListener = new HandForgeListener(this);
	public StuddedSetListener studdedSetListener = new StuddedSetListener(this);
	public BejeweledCompassListener bejeweledCompassListener = new BejeweledCompassListener(this);
	public DrillListener drillListener = new DrillListener(this);
	public ProtectorStoneListener protectorStoneListener = new ProtectorStoneListener(this);
	public RobustRuneListener robustRuneListener = new RobustRuneListener(this);
	public RodOfShadowsListener rodOfShadowsListener = new RodOfShadowsListener(this);
	public WandOfDisplacementListener wandOfDisplacementListener = new WandOfDisplacementListener(this);
	public MagicMirrorListener magicMirrorListener = new MagicMirrorListener(this);
	public BoneMarrowListener boneMarrowListener = new BoneMarrowListener(this);
	public BlazingFuryListener blazingFuryListener = new BlazingFuryListener(this);
	public SpectralHarnessListener spectralHarnessListener;
	public TheShredderListener theShredderListener = new TheShredderListener(this);
	public BoltsListener boltsListener = new BoltsListener(this);
	public FragmentedSetListener fragmentedSetListener = new FragmentedSetListener(this);
	public FlamedashBootsListener flamedashBootsListener = new FlamedashBootsListener(this);
	
	//Advanced

	public CordListener cordListener = new CordListener(this);
	public RuggedSwordListener ruggedSwordListener = new RuggedSwordListener(this);
	public RegenerativeHorseArmorListener regenerativeHorseArmorListener = new RegenerativeHorseArmorListener(this);
	public EmblemOfTheStallionListener emblemOfTheStallionListener = new EmblemOfTheStallionListener(this);
	public SteelMeshListener steelMeshListener = new SteelMeshListener(this);
	public EmblemOfTheShieldListener emblemOfTheShieldListener = new EmblemOfTheShieldListener(this);
	public SteelPlateSetListener steelPlateSetListener = new SteelPlateSetListener(this);
	public EssenceOfFaunaListener essenceOfFaunaListener = new EssenceOfFaunaListener(this);
	public SteelWoolListener steelWoolListener = new SteelWoolListener(this);
	public VerdantMedallionListener verdantMedallionListener = new VerdantMedallionListener(this);
	public EmblemOfThePastureListener emblemOfThePastureListener;
	public GaiasWrathListener gaiasWrathListener = new GaiasWrathListener(this);
	public AqueousSolutionListener aqueousSolutionListener = new AqueousSolutionListener(this);
	public CorruptedSoilListener corruptedSoilListener = new CorruptedSoilListener(this);
	public EmblemOfTheBlossomListener emblemOfTheBlossomListener = new EmblemOfTheBlossomListener(this);
	public ScytheListener scytheListener = new ScytheListener(this);
	public GoldenFlowerListener goldenFlowerListener = new GoldenFlowerListener(this);
	
	

	
	
	
	
	public String[] commandStrings = new String[17];
	public final int startingClaimBlocks = 200;
	public final int startingClaimChunks = 250;
	public final int startingSeedChunks = 4;
	
	

	
	@Override
	public void onEnable() {
		
		 
		
		 File defaultDir = new File(this.getDataFolder().getAbsolutePath());
		 defaultDir.mkdir();
		 
		 File inventoryDir = new File(this.getDataFolder() + File.separator + "inventoryData");
		 inventoryDir.mkdir();
		 
		 File skillsDir = new File(this.getDataFolder() + File.separator + "skillsData");
		 skillsDir.mkdir();
		 
		 File claimsFile = new File(this.getDataFolder() + File.separator + "claims.yml");
		 
		 //Check to see if the file already exists. If not, create it.
		 if (!claimsFile.exists()) {
			 try {
				 claimsFile.createNewFile();
			 } catch (IOException e) {
				 e.printStackTrace();
			 }
		 }
		 FileConfiguration claimsConfig = YamlConfiguration.loadConfiguration(claimsFile);
		 
		 String missingPois = "";//points of interest
		 
		 if (!claimsConfig.contains("menSpawn")) {
			 missingPois = missingPois + "Men Spawn Location ";
		 }
		 
		 if (!claimsConfig.contains("elfSpawn")) {
			 missingPois = missingPois + "Elf Spawn Location ";
		 }
		 
		 if (!claimsConfig.contains("dwarfSpawn")) {
			 missingPois = missingPois + "Dwarf Spawn Location ";
		 }
		 
		 if (!claimsConfig.contains("orcSpawn")) {
			 missingPois = missingPois + "Orc Spawn Location ";
		 }
		 
		 if (!claimsConfig.contains("orcExecutioner")) {
			 missingPois = missingPois + "Orc Executioner Location ";
		 }
		 
		 if (!claimsConfig.contains("orcExecutionee")) {
			 missingPois = missingPois + "Orc Executionee Location ";
		 }
		 
		 
		 new SetPoi(this);
		 getCommand("setpoi").setTabCompleter(new SetpoiTabCompleter());
		 
		 final String missingPoisTemp = missingPois;
		 
		 if (!missingPois.equals("")) {
			 getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
				 public void run() {
					Bukkit.broadcastMessage(ChatColor.RED + "Missing points of interest: " + missingPoisTemp + ". Set them at the desired locations using /setpoi <poi>. Run /reload when complete.");
				 }
			}, 1L);
			 
			 return;
		 }
		 
		 
		
		 for (int i = 0; i < commandStrings.length; i++) {
			 commandStrings[i] = "/" + RandomStringUtils.random(150, true, true);
		 }
		 
		 
		 
		 
		 manager = Bukkit.getScoreboardManager();
		 board = manager.getMainScoreboard();
		 PluginManager pluginManager = getServer().getPluginManager();
		 pluginManager.registerEvents(onJoinListener, this);
		 pluginManager.registerEvents(onQuitListener, this);
		 pluginManager.registerEvents(playerCloseInventoryListener, this);
		 pluginManager.registerEvents(horseListener, this);
		 pluginManager.registerEvents(playerDeathListener, this);
		 pluginManager.registerEvents(playerRespawnListener, this);
		 pluginManager.registerEvents(minecartMoveListener, this);
		 
		 
		 
		 pluginManager.registerEvents(elfEatMeat, this);
		 pluginManager.registerEvents(orcEatMeat, this);
		 pluginManager.registerEvents(signShopListener, this);
		 pluginManager.registerEvents(signBreakListener, this);
		 pluginManager.registerEvents(chestBreakListener, this);
		 pluginManager.registerEvents(chestOpenListener, this);
		 pluginManager.registerEvents(blockExplodeListener, this);
		 pluginManager.registerEvents(entityExplodeListener, this);
		 pluginManager.registerEvents(playerJumpListener, this);
		 pluginManager.registerEvents(playerAttackListener, this);
		 pluginManager.registerEvents(playerDamageListener, this);
		 pluginManager.registerEvents(playerMineListener, this);
		 pluginManager.registerEvents(playerAdvancementDoneListener, this);
		 pluginManager.registerEvents(arcaneJewelsListener, this);
		 pluginManager.registerEvents(alchemistThrowPotionListener, this);
		 pluginManager.registerEvents(magmaTransmutationListener, this);
		 pluginManager.registerEvents(orcRageListener, this);
		 pluginManager.registerEvents(witheredBeheadingListener, this);
		 pluginManager.registerEvents(disableRecipeListener, this);
		 pluginManager.registerEvents(lavaFishingListener, this);
		 pluginManager.registerEvents(endermanGriefingListener, this);
		 pluginManager.registerEvents(clickedTextCommandListener, this);
		 
		 pluginManager.registerEvents(chooseRaceInventoryListener, this);
		 pluginManager.registerEvents(confirmRaceInventoryListener, this);
		 pluginManager.registerEvents(goldConversionMenu, this);
		 pluginManager.registerEvents(scrollsOfBaugDwarvesInventoryListener, this);
		 pluginManager.registerEvents(scrollsOfBaugElvesInventoryListener, this);
		 pluginManager.registerEvents(elvesCommunismEnderChestListListener, this);
		 pluginManager.registerEvents(elvesCommunismHubInventoryListener, this);
		 pluginManager.registerEvents(elvesCommunismInventoryListListener, this);
		 pluginManager.registerEvents(scrollsOfBaugMenInventoryListener, this);
		 pluginManager.registerEvents(scrollsOfBaugOrcsInventoryListener, this);
		 pluginManager.registerEvents(featureManagement, this);
		 pluginManager.registerEvents(scrollsOfBaugWizardsInventoryListener, this);
		 pluginManager.registerEvents(playerSnoopingEnderChestListListener, this);
		 pluginManager.registerEvents(playerSnoopingHubInventoryListener, this);
		 pluginManager.registerEvents(playerSnoopingInventoryListListener, this);
		 pluginManager.registerEvents(customItemClassListener, this);
		 pluginManager.registerEvents(allRecipesInventoryListener, this);
		 pluginManager.registerEvents(classDisplayedRecipeInventoryListener, this);
		 pluginManager.registerEvents(skillTreeMenu, this);
		 pluginManager.registerEvents(generalSkillTreeMenu, this);
		 pluginManager.registerEvents(chooseClassListener, this);
		 pluginManager.registerEvents(confirmClassListener, this);
		 pluginManager.registerEvents(raceSkillTreeMenu, this);
		 pluginManager.registerEvents(efficientBotanyListener, this);
		 pluginManager.registerEvents(enchantedPetalsListener, this);
		 pluginManager.registerEvents(starlightHealingListener, this);
		 pluginManager.registerEvents(lunarTransfusionListener, this);
		 pluginManager.registerEvents(arboratedStrikeListener, this);
		 pluginManager.registerEvents(radiantAnvilListener, this);
		 pluginManager.registerEvents(gildedFortuneListener, this);
		 pluginManager.registerEvents(governmentMenu, this);
		 pluginManager.registerEvents(votingMenu, this);
		 pluginManager.registerEvents(appointKingMenu, this);
		 pluginManager.registerEvents(confirmAppointKingMenu, this);
		 pluginManager.registerEvents(confirmStepDownMenu, this);
		 pluginManager.registerEvents(executionMenuListener, this);
		 pluginManager.registerEvents(spawnProtection, this);
		 pluginManager.registerEvents(chunkProtection, this);
		 pluginManager.registerEvents(claimProtection, this);
		 pluginManager.registerEvents(playerEnterChunkListener, this);
		 pluginManager.registerEvents(claiming, this);
		 pluginManager.registerEvents(playerGetExperience, this);
		 pluginManager.registerEvents(entityDropRecipeScroll, this);
		 pluginManager.registerEvents(playerPickUpRecipeScroll, this);
		 pluginManager.registerEvents(learnedRecipesInventoryListener, this);
		 pluginManager.registerEvents(displayedRecipeInventoryListener, this);
		 pluginManager.registerEvents(displayedUsesInventoryListener, this);
		 pluginManager.registerEvents(craftingTableListener, this);
		 pluginManager.registerEvents(useBrewingStandListener, this);
		 pluginManager.registerEvents(brewIllegalPotionListener, this);
		 pluginManager.registerEvents(anvilListener, this);
		 pluginManager.registerEvents(brewingStandListener, this);
		 pluginManager.registerEvents(cartographyTableListener, this);
		 pluginManager.registerEvents(composterListener, this);
		 pluginManager.registerEvents(furnaceListener, this);
		 pluginManager.registerEvents(loomListener, this);
		 pluginManager.registerEvents(smithingTableListener, this);
		 
		 

		 pluginManager.registerEvents(resetRaceListener, this);
		 
		 //Custom Items

		 pluginManager.registerEvents(customItemRenamingListener, this);
		 
		 //Basic

		 pluginManager.registerEvents(horsehairListener, this);
		 pluginManager.registerEvents(horseshoeListener, this);
		 pluginManager.registerEvents(morralListener, this);
		 pluginManager.registerEvents(meshListener, this);
		 pluginManager.registerEvents(ironHammerListener, this);
		 pluginManager.registerEvents(featheredShoesListener, this);
		 pluginManager.registerEvents(merinoWoolListener, this);
		 pluginManager.registerEvents(lanolinListener, this);
		 pluginManager.registerEvents(dryGrassListener, this);
		 pluginManager.registerEvents(vealListener, this);
		 pluginManager.registerEvents(hempListener, this);
		 pluginManager.registerEvents(assortedPetalsListener, this);
		 pluginManager.registerEvents(floralRootsListener, this);
		 pluginManager.registerEvents(floralTransmuterListener, this);
		 pluginManager.registerEvents(cactusGreavesListener, this);
		 pluginManager.registerEvents(elvenThreadListener, this);
		 pluginManager.registerEvents(enrichedLogsListener, this);
		 pluginManager.registerEvents(saplingTransmuterListener, this);
		 pluginManager.registerEvents(sawListener, this);
		 pluginManager.registerEvents(lunarDebrisListener, this);
		 pluginManager.registerEvents(nebulousAuraListener, this);
		 pluginManager.registerEvents(rhyoliteListener, this);
		 pluginManager.registerEvents(pumiceListener, this);
		 pluginManager.registerEvents(forgersScrollListener, this);
		 pluginManager.registerEvents(ferrousHarvesterListener, this);
		 pluginManager.registerEvents(reinforcedRingListener, this);
		 pluginManager.registerEvents(goldRingListener, this);
		 pluginManager.registerEvents(hardenedStoneListener, this);
		 pluginManager.registerEvents(enduranceRuneListener, this);
		 pluginManager.registerEvents(rockyTransmuterListener, this);
		 pluginManager.registerEvents(fungalRootsListener, this);
		 pluginManager.registerEvents(crimsonPowderListener, this);
		 pluginManager.registerEvents(hypnoticRingListener, this);
		 pluginManager.registerEvents(flamingBucketListener, this);
		 pluginManager.registerEvents(hellstoneListener, this);
		 pluginManager.registerEvents(etherealWoodListener, this);
		 pluginManager.registerEvents(hoglinTuskListener, this);
		 pluginManager.registerEvents(rageStoneListener, this);
		 pluginManager.registerEvents(spectralWardListener, this);
		 pluginManager.registerEvents(pebblesListener, this);
		 pluginManager.registerEvents(magmaStoneListener, this);
		 pluginManager.registerEvents(demonicWrenchListener, this);
		 
		 //Intermediate

		 pluginManager.registerEvents(ropeListener, this);
		 pluginManager.registerEvents(spearListener, this);
		 pluginManager.registerEvents(whistleListener, this);
		 pluginManager.registerEvents(bridleListener, this);
		 pluginManager.registerEvents(hardenedMeshListener, this);
		 pluginManager.registerEvents(knockbackShieldListener, this);
		 pluginManager.registerEvents(ironPlateSetListener, this);
		 pluginManager.registerEvents(waxListener, this);
		 pluginManager.registerEvents(crookListener, this);
		 pluginManager.registerEvents(shepherdsCompassListener, this);
		 pluginManager.registerEvents(corruptedStaffListener, this);
		 pluginManager.registerEvents(floralPoulticeListener, this);
		 pluginManager.registerEvents(enrichedSoilListener, this);
		 pluginManager.registerEvents(enrichedHoeListener, this);
		 pluginManager.registerEvents(demetersBenevolenceListener, this);
		 pluginManager.registerEvents(potentHoneyListener, this);
		 pluginManager.registerEvents(elvenWeaveListener, this);
		 pluginManager.registerEvents(toolbeltListener, this);
		 pluginManager.registerEvents(rustedStaffListener, this);
		 pluginManager.registerEvents(enrichedWoodSetListener, this);
		 pluginManager.registerEvents(meteoriteListener, this);
		 pluginManager.registerEvents(darkMatterListener, this);
		 pluginManager.registerEvents(lightShieldListener, this);
		 pluginManager.registerEvents(lightBowListener, this);
		 pluginManager.registerEvents(voidStoneListener, this);
		 pluginManager.registerEvents(radiantBoreListener, this);
		 pluginManager.registerEvents(flamelashListener, this);
		 pluginManager.registerEvents(handForgeListener, this);
		 pluginManager.registerEvents(studdedSetListener, this);
		 pluginManager.registerEvents(bejeweledCompassListener, this);
		 pluginManager.registerEvents(drillListener, this);
		 pluginManager.registerEvents(protectorStoneListener, this);
		 pluginManager.registerEvents(robustRuneListener, this);
		 pluginManager.registerEvents(rodOfShadowsListener, this);
		 pluginManager.registerEvents(wandOfDisplacementListener, this);
		 pluginManager.registerEvents(magicMirrorListener, this);
		 pluginManager.registerEvents(boneMarrowListener, this);
		 pluginManager.registerEvents(blazingFuryListener, this);
		 spectralHarnessListener = new SpectralHarnessListener(this);
		 pluginManager.registerEvents(spectralHarnessListener, this);
		 pluginManager.registerEvents(theShredderListener, this);
		 pluginManager.registerEvents(boltsListener, this);
		 pluginManager.registerEvents(fragmentedSetListener, this);
		 pluginManager.registerEvents(flamedashBootsListener, this);
		 
		 //Advanced

		 pluginManager.registerEvents(cordListener, this);
		 pluginManager.registerEvents(ruggedSwordListener, this);
		 pluginManager.registerEvents(regenerativeHorseArmorListener, this);
		 pluginManager.registerEvents(emblemOfTheStallionListener, this);
		 pluginManager.registerEvents(steelMeshListener, this);
		 pluginManager.registerEvents(emblemOfTheShieldListener, this);
		 pluginManager.registerEvents(steelPlateSetListener, this);
		 pluginManager.registerEvents(essenceOfFaunaListener, this);
		 pluginManager.registerEvents(steelWoolListener, this);
		 pluginManager.registerEvents(verdantMedallionListener, this);
		 emblemOfThePastureListener = new EmblemOfThePastureListener(this);//No event to listen for. All this file does is check each player every 5 seconds.
		 pluginManager.registerEvents(gaiasWrathListener, this);
		 pluginManager.registerEvents(aqueousSolutionListener, this);
		 pluginManager.registerEvents(corruptedSoilListener, this);
		 pluginManager.registerEvents(emblemOfTheBlossomListener, this);
		 pluginManager.registerEvents(scytheListener, this);
		 pluginManager.registerEvents(goldenFlowerListener, this);
		 
		 
		 
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
		 new ChunkClaim(this);
		 new Map(this);
		 
		 new Claim(this);
		 getCommand("claim").setTabCompleter(new ClaimTabCompleter(this));
		 
		 new Warp(this);
		 getCommand("warp").setTabCompleter(new WarpTabCompleter());
		 new WarpAccept(this);
		 new Spawn(this);
		 
		 new GetCustomItem(this);
		 getCommand("GetCustomItem").setTabCompleter(new GetCustomItemTabCompleter(this));
		 
		
		 
		 
		 
		 protocolManager = ProtocolLibrary.getProtocolManager();
		 itemManager = new CustomItems(this);
		 inventoryManager = new CustomInventories(this);
		 
		 for (Recipes recipe : Recipes.values()) {
			 
			 
			 getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
				 public void run() {
					 if (Bukkit.getCraftingRecipe(recipe.getPattern((Main) getServer().getPluginManager().getPlugin("BaugRPG")), getServer().getWorlds().get(0)) != null) {
						 ItemStack duplicatedItem = Bukkit.getCraftingRecipe(recipe.getPattern((Main) getServer().getPluginManager().getPlugin("BaugRPG")), getServer().getWorlds().get(0)).getResult();
						 String matchedString = duplicatedItem.getType().name();
						 for (Recipes recipe : Recipes.values()) {
							 if (duplicatedItem.equals(recipe.getResult((Main) getServer().getPluginManager().getPlugin("BaugRPG")))) {
								 matchedString = recipe.toString();
								 break;
							 }
						 }
						 
						 getLogger().severe("Duplicate recipe found for custom item: " + recipe.toString() + ". Matches with: " + matchedString);
					 }
					 if (!recipe.equals(Recipes.BOTTLED_STARLIGHT)) {
						 Bukkit.addRecipe(recipe.getBukkitRecipe((Main) getServer().getPluginManager().getPlugin("BaugRPG")));
					 }
				 }
			}, 1L);
			 
		 }
		 
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
			 playerEnterChunkListener.updateLocation(player.getUniqueId());
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
			  Player[] OnlinePlayers = miningState.keySet().toArray(new Player[miningState.keySet().size()]);
			     for (Player player : OnlinePlayers) {
			    	 File skillsfile = new File(getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
					 FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
					 
					 if (skillsconfig.getBoolean("miningOn")) {
			    	 
				    	 BlockPosition pos = miningPosition.get(player);
				    	 if (pos == null) {
				    		 continue;
				    	 }
				    	 Location loc = new Location(player.getWorld(), pos.getX(), pos.getY(), pos.getZ());
			    	 
			    	 
						 
						 if ((miningState.get(player).equals("ABORT_DESTROY_BLOCK") || miningState.get(player).equals("DROP_ITEM") || miningState.get(player).equals("DROP_ALL_ITEMS"))) {
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
								  e.printStackTrace();
							  }
						 }
						 
						 if (miningState.get(player).equals("START_DESTROY_BLOCK")) {
							  
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
	
	public boolean isDouble( String input ) {
	    try {
	        Double.parseDouble( input );
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
			e.printStackTrace();
		}
		
		
		for (Animals animal : crookListener.originalLoc.keySet()) {
			animal.getLocation().getChunk().getEntities();
			animal.teleport(crookListener.originalLoc.get(animal));
			animal.setInvisible(false);
			animal.setInvulnerable(false);
			animal.setGravity(true);
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
	
	public List<OfflinePlayer> getAllEligibleElves() {//Eligible for voting
		List<OfflinePlayer> allOfflineElvesList = getAllOfflineElves();
		for (OfflinePlayer elf : allOfflineElvesList) {
			if (elf.getLastPlayed() + 604800000 < System.currentTimeMillis()) {
				allOfflineElvesList.remove(elf);
			}
		}
		
		return allOfflineElvesList;
	}
	
	public List<OfflinePlayer> getAllEligibleMen() {//Eligible for voting
		List<OfflinePlayer> allOfflineMenList = getAllOfflineMen();
		for (OfflinePlayer man : allOfflineMenList) {
			if (man.getLastPlayed() + 604800000 < System.currentTimeMillis()) {
				allOfflineMenList.remove(man);
			}
		}
		
		return allOfflineMenList;
	}
	
	
	public List<OfflinePlayer> getAllOfflineOrcs() {
		OfflinePlayer[] allOfflinePlayers = this.getServer().getOfflinePlayers();
		List<OfflinePlayer> allOfflineElvesList = new ArrayList<OfflinePlayer>();
		
		for (int i = 0; i < allOfflinePlayers.length; i++) {
			Player player;
			OfflinePlayer offlinePlayer = allOfflinePlayers[i];
			if (allOfflinePlayers[i].isOnline()) {
				player = allOfflinePlayers[i].getPlayer();
				
				if (player.getPersistentDataContainer().has(new NamespacedKey(this, "Race"), PersistentDataType.INTEGER) && player.getPersistentDataContainer().get(new NamespacedKey(this, "Race"), PersistentDataType.INTEGER) == 4) {
					allOfflineElvesList.add(offlinePlayer);
				}
				
			} else {
				File file = new File(this.getDataFolder() + File.separator + "inventoryData" + File.separator + offlinePlayer.getUniqueId() + ".yml");
				FileConfiguration config = YamlConfiguration.loadConfiguration(file);
				
				int race = config.getInt("Race Data");
				if (race == 4) {
					allOfflineElvesList.add(offlinePlayer);
				}
			}
		}
		
		
		
		
		return allOfflineElvesList;
	}
	
	
	
	public List<OfflinePlayer> getAllOfflineMen() {
		OfflinePlayer[] allOfflinePlayers = this.getServer().getOfflinePlayers();
		List<OfflinePlayer> allOfflineElvesList = new ArrayList<OfflinePlayer>();
		
		for (int i = 0; i < allOfflinePlayers.length; i++) {
			Player player;
			OfflinePlayer offlinePlayer = allOfflinePlayers[i];
			if (allOfflinePlayers[i].isOnline()) {
				player = allOfflinePlayers[i].getPlayer();
				
				if (player.getPersistentDataContainer().has(new NamespacedKey(this, "Race"), PersistentDataType.INTEGER) && player.getPersistentDataContainer().get(new NamespacedKey(this, "Race"), PersistentDataType.INTEGER) == 1) {
					allOfflineElvesList.add(offlinePlayer);
				}
				
			} else {
				File file = new File(this.getDataFolder() + File.separator + "inventoryData" + File.separator + offlinePlayer.getUniqueId() + ".yml");
				FileConfiguration config = YamlConfiguration.loadConfiguration(file);
				
				int race = config.getInt("Race Data");
				if (race == 1) {
					allOfflineElvesList.add(offlinePlayer);
				}
			}
		}
		
		
		
		
		return allOfflineElvesList;
	}
	
	
	public List<OfflinePlayer> getAllOfflineDwarves() {
		OfflinePlayer[] allOfflinePlayers = this.getServer().getOfflinePlayers();
		List<OfflinePlayer> allOfflineElvesList = new ArrayList<OfflinePlayer>();
		
		for (int i = 0; i < allOfflinePlayers.length; i++) {
			Player player;
			OfflinePlayer offlinePlayer = allOfflinePlayers[i];
			if (allOfflinePlayers[i].isOnline()) {
				player = allOfflinePlayers[i].getPlayer();
				
				if (player.getPersistentDataContainer().has(new NamespacedKey(this, "Race"), PersistentDataType.INTEGER) && player.getPersistentDataContainer().get(new NamespacedKey(this, "Race"), PersistentDataType.INTEGER) == 3) {
					allOfflineElvesList.add(offlinePlayer);
				}
				
			} else {
				File file = new File(this.getDataFolder() + File.separator + "inventoryData" + File.separator + offlinePlayer.getUniqueId() + ".yml");
				FileConfiguration config = YamlConfiguration.loadConfiguration(file);
				
				int race = config.getInt("Race Data");
				if (race == 3) {
					allOfflineElvesList.add(offlinePlayer);
				}
			}
		}
		
		
		
		
		return allOfflineElvesList;
	}
	
	
	//For custom items digits encoded as such:
	//First digit indicates race
	//Second digit indicates what class in the race
	//Third digit indicates item level
	//Fourth digit indicates "this is the third intermediate item for X class."
	
	public ItemStack createItem(Material material, int amount, String displayName, List<String> loreInfo, int customModelData) {
		
		ItemStack item = new ItemStack(material);
		item.setAmount(amount);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(displayName);
		List<String> itemLore = loreInfo;
		itemMeta.setLore(itemLore);
		itemMeta.setCustomModelData(customModelData);
		item.setItemMeta(itemMeta);
		
		return item;
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
	
	public String getRaceString(int race) {
		String raceString = "men";
		switch (race) {
			case 2:
				raceString = "elf";
				break;
			case 3:
				raceString = "dwarf";
				break;
			case 4:
				raceString = "orc";
				break;
		}
		return raceString;
	}
	
	public int getRace(String str) {
		if (str.toLowerCase().equals("men")) {
			return 1;
		}
		if (str.toLowerCase().equals("elf")) {
			return 2;
		}
		if (str.toLowerCase().equals("dwarf")) {
			return 3;
		}
		if (str.toLowerCase().equals("orc")) {
			return 4;
		}
		return 0;
	}
	
	
	
	public int getRace(OfflinePlayer op) {
		if (op.isOnline()) {
			Player player = (Player)op;
			if (player.getPersistentDataContainer().has(new NamespacedKey(this, "Race"), PersistentDataType.INTEGER)) {
				return player.getPersistentDataContainer().get(new NamespacedKey(this, "Race"), PersistentDataType.INTEGER);
			}
			
		} else {
			File file = new File(this.getDataFolder() + File.separator + "inventoryData" + File.separator + op.getUniqueId() + ".yml");
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);
			
			return config.getInt("Race Data");
		}
		return 0;
	}
	
	public double getDistanceFlat(Location loc1, Location loc2) {
		double xDist = loc1.getX() - loc2.getX();
		double zDist = loc1.getZ() - loc2.getZ();
		return Math.sqrt((xDist * xDist) + (zDist * zDist));
	}
	
	public org.bukkit.block.Block getTopBlock(org.bukkit.block.Block block) {
		if (block.getType().isSolid()) {
			BlockIterator iter = new BlockIterator(block.getWorld(), block.getLocation().toVector(), new Vector(0, 1, 0), 0, 0);
			int yPos = block.getY();
			while (iter.hasNext()) {
				org.bukkit.block.Block currentBlock = iter.next();
				if (!currentBlock.getType().isSolid()) {
					yPos = currentBlock.getY() - 1;
					break;
				}
			}
			return block.getWorld().getBlockAt(block.getX(), yPos, block.getZ());
		} else {
			BlockIterator iter = new BlockIterator(block.getWorld(), block.getLocation().toVector(), new Vector(0, -1, 0), 0, 0);
			int yPos = block.getY();
			while (iter.hasNext()) {
				org.bukkit.block.Block currentBlock = iter.next();
				if (currentBlock.getType().isSolid()) {
					yPos = currentBlock.getY();
					break;
				}
			}
			return block.getWorld().getBlockAt(block.getX(), yPos, block.getZ());
		}
		
	}
	
}