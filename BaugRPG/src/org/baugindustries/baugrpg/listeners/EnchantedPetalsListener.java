package org.baugindustries.baugrpg.listeners;

import java.io.File;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EnchantedPetalsListener implements Listener {
	private Main plugin;
	public EnchantedPetalsListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onPlayerUse(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
	 	
        if (!(skillsconfig.contains("EnchantedBotanist2") && skillsconfig.getBoolean("EnchantedBotanist2"))) return;
    	if (!event.getAction().equals(Action.RIGHT_CLICK_AIR)) return;
    	if (event.getItem() == null) return;
    	if (plugin.petalTickTime.containsKey(player.getUniqueId()) && plugin.petalTickTime.get(player.getUniqueId()) + 4 == Bukkit.getWorlds().get(0).getFullTime()) {
    		plugin.petalTickTime.put(player.getUniqueId(), Bukkit.getWorlds().get(0).getFullTime());
    		return;
    	}
    	
    	
    	
    	ItemStack item = event.getItem();
    	Material itemMat = item.getType();
    	Boolean potCheck = false;
    	PotionEffect potEff = null;
    	String itemString = event.getItem().getType().name();
    	if (itemMat.equals(Material.ALLIUM)) {
    		potEff = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 400, 0);//Effect, Duration(ticks), Amplifier
    		potCheck = true;
    	} else if (itemMat.equals(Material.AZURE_BLUET)) {
    		potEff = new PotionEffect(PotionEffectType.BLINDNESS, 800, 0);//Effect, Duration(ticks), Amplifier
    		potCheck = true;
    	} else if (itemMat.equals(Material.BLUE_ORCHID) || itemMat.equals(Material.DANDELION)) {
    		potEff = new PotionEffect(PotionEffectType.SATURATION, 5, 0);//Effect, Duration(ticks), Amplifier
    		potCheck = true;
    	} else if (itemMat.equals(Material.CORNFLOWER)) {
    		potEff = new PotionEffect(PotionEffectType.JUMP, 600, 0);//Effect, Duration(ticks), Amplifier
    		potCheck = true;
    	} else if (itemMat.equals(Material.LILY_OF_THE_VALLEY)) {
    		potEff = new PotionEffect(PotionEffectType.POISON, 1200, 0);//Effect, Duration(ticks), Amplifier
    		potCheck = true;
    	} else if (itemMat.equals(Material.OXEYE_DAISY)) {
    		potEff = new PotionEffect(PotionEffectType.REGENERATION, 800, 0);//Effect, Duration(ticks), Amplifier
    		potCheck = true;
    	} else if (itemMat.equals(Material.POPPY)) {
    		potEff = new PotionEffect(PotionEffectType.NIGHT_VISION, 500, 0);//Effect, Duration(ticks), Amplifier
    		potCheck = true;
    	} else if ((itemString.substring(itemString.length() - 5).equals("TULIP"))) {
    		potEff = new PotionEffect(PotionEffectType.WEAKNESS, 900, 0);//Effect, Duration(ticks), Amplifier
    		potCheck = true;
    	} else if (itemMat.equals(Material.WITHER_ROSE)) {
    		potEff = new PotionEffect(PotionEffectType.WITHER, 800, 0);//Effect, Duration(ticks), Amplifier
    		potCheck = true;
    	} else if (itemMat.equals(Material.SUNFLOWER)) {
    		potEff = new PotionEffect(PotionEffectType.GLOWING, 800, 0);//Effect, Duration(ticks), Amplifier
    		potCheck = true;
    	} else if (itemMat.equals(Material.PEONY)) {
    		potEff = new PotionEffect(PotionEffectType.SLOW_FALLING, 200, 0);//Effect, Duration(ticks), Amplifier
    		potCheck = true;
    	} else if (itemMat.equals(Material.PEONY)) {
    		potEff = new PotionEffect(PotionEffectType.SLOW_FALLING, 200, 0);//Effect, Duration(ticks), Amplifier
    		potCheck = true;
    	} else if (itemMat.equals(Material.LILAC)) {
    		potEff = new PotionEffect(PotionEffectType.WATER_BREATHING, 100, 0);//Effect, Duration(ticks), Amplifier
    		potCheck = true;
    	} else if (itemMat.equals(Material.ROSE_BUSH)) {
    		potEff = new PotionEffect(PotionEffectType.SLOW, 400, 0);//Effect, Duration(ticks), Amplifier
    		potCheck = true;
    	} else if (itemMat.equals(Material.GRASS)) {
    		potEff = new PotionEffect(PotionEffectType.CONFUSION, 800, 0);//Effect, Duration(ticks), Amplifier
    		potCheck = true;
    	}
    	
    	if (potCheck) {
    		player.addPotionEffect(potEff);
    		event.getItem().setAmount(item.getAmount() - 1);
    		plugin.petalTickTime.put(player.getUniqueId(), Bukkit.getWorlds().get(0).getFullTime());
    	}
    	
	}
}
