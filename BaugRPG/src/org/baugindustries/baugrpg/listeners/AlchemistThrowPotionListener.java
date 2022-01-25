package org.baugindustries.baugrpg.listeners;

import java.io.File;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class AlchemistThrowPotionListener implements Listener {
	private Main plugin;
	public AlchemistThrowPotionListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void ThrowPotion(PlayerInteractEvent event) {
		ItemStack heldItem = event.getItem();
		if (heldItem == null) return;
		if (!heldItem.getType().equals(Material.SPLASH_POTION)) return;
		
		if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR))) return;
		
		Player player = event.getPlayer();
		
		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
	 	
	 	PotionMeta meta = (PotionMeta) event.getItem().getItemMeta();
	 	PotionData data = meta.getBasePotionData();
	 	
	 	if (skillsconfig.contains("DarkAlchemist1") && skillsconfig.getBoolean("DarkAlchemist1") && !(skillsconfig.contains("DarkAlchemist3") && skillsconfig.getBoolean("DarkAlchemist3"))) {
	 		
	 		for (int i = 0; i < meta.getCustomEffects().size(); i++) {
	 			PotionEffect effect = meta.getCustomEffects().get(i);
	 			PotionEffect newEffect = new PotionEffect(effect.getType(), effect.getDuration(), ((effect.getAmplifier() + 1) * 2) - 1);
	 			meta.getCustomEffects().set(i, newEffect);

	 		}
	 		PotionData newData = new PotionData(PotionType.AWKWARD, false, false);
	 		meta.setBasePotionData(newData);
	 		PotionEffect[] basePotionEffects = potionData(data);
	 		for (int i = 0; i < basePotionEffects.length; i++) {
	 			PotionEffect newEffect = new PotionEffect(basePotionEffects[i].getType(), basePotionEffects[i].getDuration(), ((basePotionEffects[i].getAmplifier() + 1) * 2) - 1);
	 			meta.addCustomEffect(newEffect, false);
	 		}
	 		event.getItem().setItemMeta(meta);
	 	} else if (skillsconfig.contains("DarkAlchemist3") && skillsconfig.getBoolean("DarkAlchemist3") && !(skillsconfig.contains("DarkAlchemist1") && skillsconfig.getBoolean("DarkAlchemist1"))) {
	 		for (int i = 0; i < meta.getCustomEffects().size(); i++) {
	 			PotionEffect effect = meta.getCustomEffects().get(i);
	 			PotionEffect newEffect = new PotionEffect(effect.getType(), effect.getDuration() * 2, effect.getAmplifier());
	 			meta.getCustomEffects().set(i, newEffect);

	 		}
	 		PotionData newData = new PotionData(PotionType.AWKWARD, false, false);
	 		meta.setBasePotionData(newData);
	 		PotionEffect[] basePotionEffects = potionData(data);
	 		for (int i = 0; i < basePotionEffects.length; i++) {
	 			PotionEffect newEffect = new PotionEffect(basePotionEffects[i].getType(), basePotionEffects[i].getDuration() * 2, basePotionEffects[i].getAmplifier());
	 			meta.addCustomEffect(newEffect, false);
	 		}
	 		event.getItem().setItemMeta(meta);
	 	} else if (skillsconfig.contains("DarkAlchemist3") && skillsconfig.getBoolean("DarkAlchemist3") && skillsconfig.contains("DarkAlchemist1") && skillsconfig.getBoolean("DarkAlchemist1")) {
	 		for (int i = 0; i < meta.getCustomEffects().size(); i++) {
	 			PotionEffect effect = meta.getCustomEffects().get(i);
	 			PotionEffect newEffect = new PotionEffect(effect.getType(), effect.getDuration() * 2, ((effect.getAmplifier() + 1) * 2) - 1);
	 			meta.getCustomEffects().set(i, newEffect);

	 		}
	 		PotionData newData = new PotionData(PotionType.AWKWARD, false, false);
	 		meta.setBasePotionData(newData);
	 		PotionEffect[] basePotionEffects = potionData(data);
	 		for (int i = 0; i < basePotionEffects.length; i++) {
	 			PotionEffect newEffect = new PotionEffect(basePotionEffects[i].getType(), basePotionEffects[i].getDuration() * 2, ((basePotionEffects[i].getAmplifier() + 1) * 2) - 1);
	 			meta.addCustomEffect(newEffect, false);
	 		}
	 		event.getItem().setItemMeta(meta);
	 	}
	}
	
	
	public PotionEffect[] potionData(PotionData data) {
		int time = 0;
		int amplifier = 0;
		int time2 = 0;
		int amplifier2 = 0;
		if (data.getType().equals(PotionType.TURTLE_MASTER)) {
			if (data.isExtended()) {
				time = 800;
				time2 = 800;
				amplifier = 3;
				amplifier2 = 2;
			} else if (data.isUpgraded()) {
				time = 400;
				time2 = 400;
				amplifier = 5;
				amplifier2 = 3;
			} else {
				time = 400;
				time2 = 400;
				amplifier = 3;
				amplifier2 = 2;
			}
			PotionEffect[] array = {new PotionEffect(PotionEffectType.SLOW, time, amplifier), new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, time2, amplifier2)};
			return array;
		} else if (data.getType().equals(PotionType.SLOWNESS)) {
			if (data.isExtended()) {
				time = 4800;
				amplifier = 0;
			} else if (data.isUpgraded()) {
				time = 400;
				amplifier = 3;
			} else {
				time = 1800;
				amplifier = 0;
			}
			PotionEffect[] array = {new PotionEffect(PotionEffectType.SLOW, time, amplifier)};
			return array;
		} else if (data.getType().equals(PotionType.SPEED)) {
			if (data.isExtended()) {
				time = 9600;
				amplifier = 0;
			} else if (data.isUpgraded()) {
				time = 1800;
				amplifier = 1;
			} else {
				time = 3600;
				amplifier = 0;
			}
			PotionEffect[] array = {new PotionEffect(PotionEffectType.SPEED, time, amplifier)};
			return array;
		} else if (data.getType().equals(PotionType.FIRE_RESISTANCE)) {
			if (data.isExtended()) {
				time = 9600;
				amplifier = 0;
			}else {
				time = 3600;
				amplifier = 0;
			}
			PotionEffect[] array = {new PotionEffect(PotionEffectType.FIRE_RESISTANCE, time, amplifier)};
			return array;
		} else if (data.getType().equals(PotionType.JUMP)) {
			if (data.isExtended()) {
				time = 9600;
				amplifier = 0;
			} else if (data.isUpgraded()) {
				time = 1800;
				amplifier = 1;
			} else {
				time = 3600;
				amplifier = 0;
			}
			PotionEffect[] array = {new PotionEffect(PotionEffectType.JUMP, time, amplifier)};
			return array;
		} else if (data.getType().equals(PotionType.INVISIBILITY)) {
			if (data.isExtended()) {
				time = 9600;
				amplifier = 0;
			} else {
				time = 3600;
				amplifier = 0;
			}
			PotionEffect[] array = {new PotionEffect(PotionEffectType.INVISIBILITY, time, amplifier)};
			return array;
		} else if (data.getType().equals(PotionType.NIGHT_VISION)) {
			if (data.isExtended()) {
				time = 9600;
				amplifier = 0;
			} else {
				time = 3600;
				amplifier = 0;
			}
			PotionEffect[] array = {new PotionEffect(PotionEffectType.NIGHT_VISION, time, amplifier)};
			return array;
		} else if (data.getType().equals(PotionType.SLOW_FALLING)) {
			if (data.isExtended()) {
				time = 4800;
				amplifier = 0;
			} else {
				time = 1800;
				amplifier = 0;
			}
			PotionEffect[] array = {new PotionEffect(PotionEffectType.SLOW_FALLING, time, amplifier)};
			return array;
		} else if (data.getType().equals(PotionType.LUCK)) {
			PotionEffect[] array = {new PotionEffect(PotionEffectType.LUCK, 6000, 0)};
			return array;
		} else if (data.getType().equals(PotionType.WEAKNESS)) {
			if (data.isExtended()) {
				time = 4800;
				amplifier = 0;
			} else {
				time = 1800;
				amplifier = 0;
			}
			PotionEffect[] array = {new PotionEffect(PotionEffectType.WEAKNESS, time, amplifier)};
			return array;
		} else if (data.getType().equals(PotionType.STRENGTH)) {
			if (data.isExtended()) {
				time = 9600;
				amplifier = 0;
			} else if (data.isUpgraded()) {
				time = 1800;
				amplifier = 1;
			} else {
				time = 3600;
				amplifier = 0;
			}
			PotionEffect[] array = {new PotionEffect(PotionEffectType.INCREASE_DAMAGE, time, amplifier)};
			return array;
		} else if (data.getType().equals(PotionType.REGEN)) {
			if (data.isExtended()) {
				time = 1800;
				amplifier = 0;
			} else if (data.isUpgraded()) {
				time = 450;
				amplifier = 1;
			} else {
				time = 900;
				amplifier = 0;
			}
			PotionEffect[] array = {new PotionEffect(PotionEffectType.REGENERATION, time, amplifier)};
			return array;
		} else if (data.getType().equals(PotionType.POISON)) {
			if (data.isExtended()) {
				time = 1800;
				amplifier = 0;
			} else if (data.isUpgraded()) {
				time = 450;
				amplifier = 1;
			} else {
				time = 900;
				amplifier = 0;
			}
			PotionEffect[] array = {new PotionEffect(PotionEffectType.POISON, time, amplifier)};
			return array;
		} else if (data.getType().equals(PotionType.INSTANT_DAMAGE)) {
			if (data.isUpgraded()) {
				amplifier = 1;
			} else {
				amplifier = 0;
			}
			PotionEffect[] array = {new PotionEffect(PotionEffectType.HARM, time, amplifier)};
			return array;
		} else if (data.getType().equals(PotionType.INSTANT_HEAL)) {
			if (data.isUpgraded()) {
				amplifier = 1;
			} else {
				amplifier = 0;
			}
			PotionEffect[] array = {new PotionEffect(PotionEffectType.HEAL, time, amplifier)};
			return array;
		} else if (data.getType().equals(PotionType.WATER_BREATHING)) {
			if (data.isExtended()) {
				time = 9600;
				amplifier = 0;
			} else {
				time = 3600;
				amplifier = 0;
			}
			PotionEffect[] array = {new PotionEffect(PotionEffectType.WATER_BREATHING, time, amplifier)};
			return array;
		}
		
		
		
		
		PotionEffect[] array = {};
		return array;
		
	}
}
