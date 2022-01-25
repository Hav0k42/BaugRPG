package org.baugindustries.baugrpg.listeners;

import java.io.File;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RadiantAnvilListener implements Listener {
	private Main plugin;
	public RadiantAnvilListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void PlayerAttackEvent(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Player)) return;
		
		Player attacker = (Player) event.getDamager();
		Entity victim = event.getEntity();
		
		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + attacker.getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
	 	
	 	if (!(skillsconfig.contains("RadiantMetallurgist1") && skillsconfig.getBoolean("RadiantMetallurgist1"))) return;
	 	double percentage = 0.01;
	 	if (Math.random() > percentage) return;
	 	
	 	int anvilAmount = 30;
	 	int anvilRange = 13;
	 	int anvilHeight = 20;
	 	int tickSpacer = 1;
	 	
	 	//Sometimes there is lag whenever anvils are spawned, but in my testing world its only associated with a particular location and I've optimized this as much as I feel like. I think its just a vanilla rendering bug or something.
	 	
	 	Runnable spawnAnvil = new Runnable() {
			public void run() {
				Location loc = victim.getLocation();
				loc.add((Math.random() * anvilRange) - (anvilRange / 2), anvilHeight, (Math.random() * anvilRange) - (anvilRange / 2));
				Location locBeneath = new Location(loc.getWorld(), loc.getX(), loc.getY() - 1, loc.getZ());
				if (loc.getBlock().getType().equals(Material.AIR) || loc.getBlock().getType().equals(Material.CAVE_AIR) || loc.getBlock().getType().equals(Material.VOID_AIR)) {
					if (locBeneath.getBlock().getType().equals(Material.AIR) || locBeneath.getBlock().getType().equals(Material.CAVE_AIR) || locBeneath.getBlock().getType().equals(Material.VOID_AIR)) {
						loc.getBlock().setType(Material.ANVIL);
						Location blockLoc = new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
						plugin.anvilSpawnLocs.add(blockLoc);
					}
				}
			}
 		};
	 	
	 	for (int i = 0; i < anvilAmount; i++) {
	 		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, spawnAnvil, i * tickSpacer);
	 	}
	}
	
	
	@EventHandler
	public void AnvilLandEvent(EntityChangeBlockEvent event) {
		if (!(event.getEntity() instanceof FallingBlock)) return;
		if (event.getTo().equals(Material.ANVIL) || event.getTo().equals(Material.CHIPPED_ANVIL)) {//anvil lands
			UUID uuid = event.getEntity().getUniqueId();
			if (plugin.anvilUUIDs.contains(uuid)) {
				event.setCancelled(true);
			}
		} else if (event.getBlock().getType().equals(Material.ANVIL)) {//anvil falls
			if (plugin.anvilSpawnLocs.contains(event.getBlock().getLocation())) {//anvil was created by this listener
				plugin.anvilSpawnLocs.remove(event.getBlock().getLocation());
				UUID uuid = event.getEntity().getUniqueId();
				plugin.anvilUUIDs.add(uuid);
				((FallingBlock)event.getEntity()).setDropItem(false);
				event.getEntity().setGlowing(true);
			}
		}
		
		
	}
	
	@EventHandler
	public void AnvilHitEvent(EntityDamageByEntityEvent event) {
		if (!(plugin.anvilUUIDs.contains(event.getDamager().getUniqueId()))) return;
		
		if (!(event.getEntity() instanceof LivingEntity)) return;
		
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			PersistentDataContainer data = player.getPersistentDataContainer();
			if (data.has(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER)) {
				int race = data.get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER);
				if (race == 3) {
					event.setCancelled(true);
					return;
				}
			}
		}
		LivingEntity entity = (LivingEntity) event.getEntity();
		entity.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 600, 0));
		entity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 600, 0));
		event.setDamage(4);
	}
}
