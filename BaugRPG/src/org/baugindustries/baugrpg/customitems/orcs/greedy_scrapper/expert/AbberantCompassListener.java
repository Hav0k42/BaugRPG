package org.baugindustries.baugrpg.customitems.orcs.greedy_scrapper.expert;

import org.baugindustries.baugrpg.Main;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import net.minecraft.core.BlockPosition;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.feature.StructureFeature;

public class AbberantCompassListener implements Listener {
	private Main plugin;
	
	public AbberantCompassListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onCrossChunk(PlayerMoveEvent event) {
		if (event.getTo().getChunk().equals(event.getFrom().getChunk())) return;
		
		Player player = event.getPlayer();
		
		BlockPosition bp = new BlockPosition(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
		World world = player.getWorld();
		CraftWorld cWorld = (CraftWorld) world;
		WorldServer worldServer = cWorld.getHandle();
//		BlockPosition monumentPosition = worldServer.a(TagKey.a(new ResourceKey(), new MinecraftKey()), bp, 100, false);
		
	}

}
