package org.baugindustries.baugrpg.listeners;

import java.io.File;

import org.baugindustries.baugrpg.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class MagmaTransmutationListener implements Listener {
	private Main plugin;
	public MagmaTransmutationListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void ClickCauldron(PlayerInteractEvent event) {
		if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		Player player = event.getPlayer();
		
		ItemStack item = event.getItem();
		if (item == null) return;
		if (!item.getType().equals(Material.LAVA_BUCKET)) return;
		
		if (player.isSneaking()) return;
		
		Block block = event.getClickedBlock();
		if (!block.getType().equals(Material.CAULDRON)) return;
		
		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
	 	if (!(skillsconfig.contains("DarkAlchemist2") && skillsconfig.getBoolean("DarkAlchemist2"))) return;
	 	
	 	Location center = block.getLocation();
	 	if (!checkBlockRelative(player, center, 0, -1, 0, Material.PACKED_ICE)) return;
	 	if (!checkBlockRelative(player, center, 1, 0, 0, Material.PACKED_ICE)) return;
	 	if (!checkBlockRelative(player, center, -1, 0, 0, Material.PACKED_ICE)) return;
	 	if (!checkBlockRelative(player, center, 0, 0, 1, Material.PACKED_ICE)) return;
	 	if (!checkBlockRelative(player, center, 0, 0, -1, Material.PACKED_ICE)) return;
	 	
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
			 	block.setType(Material.WATER_CAULDRON);
			 	Levelled cauldron = (Levelled) block.getBlockData();
			 	cauldron.setLevel(cauldron.getMaximumLevel());
			 	block.setBlockData(cauldron);
			}
	  	}, 1L);
	 	
	 	Particle particle = Particle.VILLAGER_ANGRY;
		player.getWorld().spawnParticle(particle, block.getLocation().add(0.5, 0.3, 0.5), 60, 0.25, 0.25, 0.25, 0.03);
	}

	
	private Boolean checkBlockRelative(Player player, Location center, int x, int y, int z, Material mat) {
		Location newLoc = new Location(center.getWorld(), center.getBlockX() + x, center.getBlockY() + y, center.getBlockZ() + z);
		if (newLoc.getWorld().getBlockAt(newLoc).getType().equals(mat)) {
			return true;
		}
		player.sendMessage(ChatColor.YELLOW + "Missing block " + mat.name() + " at: " + newLoc.getBlockX() + ", " + newLoc.getBlockY() + ", " + newLoc.getBlockZ() + ".");
		return false;
	}
}
