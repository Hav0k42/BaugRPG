package org.baugindustries.baugrpg.listeners;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;

public class OrcRageListener implements Listener {
	private int rageCooldownTime = 10;
	
	private Main plugin;
	public OrcRageListener(Main plugin) {
		this.plugin = plugin;
	}
	
	public int getRageCooldownTime() {
		return rageCooldownTime;
	}
	
	@EventHandler
	public void OrcKillEntity(EntityDeathEvent event) {
		Player killer = event.getEntity().getKiller();
		if (killer == null) return;
		UUID uuid = killer.getUniqueId();
		
		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + killer.getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
	 	
	 	if (!(skillsconfig.contains("EnragedBerserker1") && skillsconfig.getBoolean("EnragedBerserker1"))) return;
	 	
	 	if (plugin.rageCooldown.containsKey(killer.getUniqueId())) {
	 		int minutesToMillis = 60000;
	 		if (plugin.rageCooldown.get(killer.getUniqueId()) + (rageCooldownTime * minutesToMillis) < System.currentTimeMillis()) {
	 			plugin.rageCooldown.remove(killer.getUniqueId());
	 		}
	 	}
        
        if (plugin.rageCooldown.containsKey(killer.getUniqueId())) return;
	 	
		
		if (plugin.berserkerKillstreaks.containsKey(uuid)) {
			long currentData = plugin.berserkerKillstreaks.get(uuid);
			currentData++;//Amount of kills
			plugin.berserkerKillstreaks.put(uuid, currentData);
		} else {
			plugin.berserkerKillstreaks.put(uuid, 1L);
		}
		runKillTick(uuid, 0);
		
		if (plugin.berserkerKillstreaks.get(uuid) < 5) return;//activate rage mode
		
		int rageDuration = 600;
		
		killer.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, rageDuration, 3));
		killer.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, rageDuration, 3));
		killer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, rageDuration, 0));
		killer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, rageDuration, 4));
		killer.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, rageDuration, -3));//In place of the nonexistent Vulnerability effect
		
		PacketContainer rageEffect = plugin.protocolManager.createPacket(PacketType.Play.Server.SET_BORDER_WARNING_DISTANCE);
		rageEffect.getIntegers().write(0, 2147483647);
		try {
			plugin.protocolManager.sendServerPacket(killer, rageEffect);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		plugin.rageCooldown.put(killer.getUniqueId(), System.currentTimeMillis());
		
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				//remove screen border effect
				PacketContainer rageEffect = plugin.protocolManager.createPacket(PacketType.Play.Server.SET_BORDER_WARNING_DISTANCE);
				rageEffect.getIntegers().write(0, 5);
				try {
					plugin.protocolManager.sendServerPacket(killer, rageEffect);
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
  		  	}
  		}, rageDuration);
	}
	
	
	
	private void runKillTick(UUID uuid, int cooldownTime) {
		Runnable tick = new Runnable() {
			public void run() {
				if (cooldownTime > 400 || (plugin.berserkerKillstreaks.containsKey(uuid) && plugin.berserkerKillstreaks.get(uuid) == 5)) {//Time in which to get 5 kills. Currently 20 seconds. 1 kill every 4 seconds
					plugin.berserkerKillstreaks.remove(uuid);
				} else {
					runKillTick(uuid, cooldownTime + 1);
				}
  		  	}
  		};
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, tick, 1L);
	}

}
