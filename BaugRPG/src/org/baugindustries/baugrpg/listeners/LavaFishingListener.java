package org.baugindustries.baugrpg.listeners;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.baugindustries.baugrpg.BaugLootTable;
import org.baugindustries.baugrpg.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_18_R1.CraftLootTable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.LootContext.Builder;
import org.bukkit.loot.LootTables;
import org.bukkit.util.Vector;

public class LavaFishingListener implements Listener {
	
	List<FishHook> primedHooks = new ArrayList<FishHook>();
	
	private Main plugin;
	public LavaFishingListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void PlayerFish(PlayerFishEvent event) {
		Player player = event.getPlayer();
		
		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
	 	
	 	if (!(skillsconfig.contains("GreedyScrapper1") && skillsconfig.getBoolean("GreedyScrapper1"))) return;
		
		World world = player.getWorld();
		if (primedHooks.contains(event.getHook())) {
			LootTables lootType = LootTables.FISHING;
			Entity piglin = null;
			if (Math.random() < 0.2) {//Use loot table from piglin trades
				lootType = LootTables.PIGLIN_BARTERING;
				
				//Yes this is hacky, but its the easiest way to get it done so far as I can tell.
				Location newLoc = player.getLocation();
				newLoc.setY(-100);
				piglin = world.spawnEntity(newLoc, EntityType.PIGLIN);
				piglin.remove();
				
			} else {//Use loot table from regular fishing
				//Use luck of the sea two luck buff for chances
				double ran = Math.random();
				if (ran < 0.061) {
					lootType = LootTables.FISHING_JUNK;
				} else if (ran < 0.153) {
					lootType = LootTables.FISHING_TREASURE;
				} else {
					lootType = LootTables.FISHING_FISH;
				}
			}
			
			ItemStack itemStack = null;
			
			BaugLootTable table = new BaugLootTable(lootType.getKey(), ((CraftLootTable)Bukkit.getLootTable(lootType.getKey())).getHandle());
			Builder builder = new Builder(event.getPlayer().getLocation());
			builder.killer(event.getPlayer());
			builder.luck(0);
			builder.lootingModifier(0);
			builder.lootedEntity(piglin);
			LootContext context = builder.build();
			List<ItemStack> drops = (List<ItemStack>) table.populateLoot(new Random(), context);
			itemStack = drops.get(0);
			
			if (itemStack.getType().equals(Material.COD)) {
				itemStack.setType(Material.COOKED_COD);
			} else if (itemStack.getType().equals(Material.SALMON)) {
				itemStack.setType(Material.COOKED_SALMON);
			}
			
			Item item = (Item) world.spawnEntity(event.getHook().getLocation().add(0, 1, 0), EntityType.DROPPED_ITEM);
			item.setItemStack(itemStack);
			item.setVelocity(event.getPlayer().getLocation().add(0, 2, 0).subtract(item.getLocation()).toVector());
			ExperienceOrb xp = (ExperienceOrb) world.spawnEntity(player.getLocation(), EntityType.EXPERIENCE_ORB);
			xp.setExperience(1 + (int)(Math.random() * 6));
			
			//TODO: FIX
			if (player.getInventory().getItemInMainHand().getType().equals(Material.FISHING_ROD)) {
				int slot = player.getInventory().getHeldItemSlot();

				Damageable meta = (Damageable) player.getInventory().getItem(slot).getItemMeta();
				meta.setDamage(meta.getDamage() + 1);
				player.getInventory().getItem(slot).setItemMeta(meta);
			} else if (player.getInventory().getItemInOffHand().getType().equals(Material.FISHING_ROD)) {
				Damageable meta = (Damageable) player.getInventory().getItemInOffHand().getItemMeta();
				meta.setDamage(meta.getDamage() + 1);
				player.getInventory().getItemInOffHand().setItemMeta(meta);
			}
		}
		runFloatBobber(event.getHook(), 0);
	}
	
	private void runFloatBobber(FishHook fishHook, int ticks) {
		Runnable tick = new Runnable() {
			public void run() {
				int newTicks = ticks;
				if (fishHook.getLocation().getBlock().getType().equals(Material.LAVA)) {
					fishHook.setVelocity(new Vector(0, 0.05, 0).add(fishHook.getVelocity()));
					Particle particle = Particle.FALLING_LAVA;
					fishHook.getWorld().spawnParticle(particle, fishHook.getLocation(), 2, 0.15, 0.25, 0.15, 0.03);
					if (Math.random() < 0.1) {
						Particle flameParticle = Particle.LAVA;
						fishHook.getWorld().spawnParticle(flameParticle, fishHook.getLocation(), 1, 0, 0, 0, 0.03);
					}
				}
				fishHook.setFireTicks(0);
				
				if (newTicks >= 500 && newTicks < 600) {
					if (Math.random() < (newTicks - 500.0) / 100.0) {
						fishHook.setVelocity(fishHook.getVelocity().add(new Vector(0, -1, 0)));
						newTicks = 600;
						primedHooks.add(fishHook);
					}
				}
				
				if (newTicks > 600) {
					if (Math.random() < 0.2) {
						fishHook.setVelocity(fishHook.getVelocity().add(new Vector(0, -1, 0)));
					}
					if (Math.random() < 0.1) {
						fishHook.getWorld().playSound(fishHook.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1, 1);
					}
					if (Math.random() < 0.1) {
						fishHook.getWorld().playSound(fishHook.getLocation(), Sound.BLOCK_FIRE_AMBIENT, 1, 1);
					}
				}
				
				if (newTicks > 640) {
					newTicks = 0;
					primedHooks.remove(fishHook);
				}
				
				if (!fishHook.isDead()) {
					runFloatBobber(fishHook, newTicks + 1);
				}
  		  	}
  		};
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, tick, 1L);
	}
	
}
