package org.baugindustries.baugrpg.customitems.elves.woodland_craftsman.advanced;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;

public class ElvenSetListener implements Listener {
	private Main baugPlugin;
	
	public ElvenSetListener(Main baugPlugin) {
		this.baugPlugin = baugPlugin;
		baugPlugin.protocolManager.addPacketListener(new PacketAdapter(baugPlugin, ListenerPriority.NORMAL, PacketType.Play.Server.NAMED_SOUND_EFFECT) {
	            @Override
	            public void onPacketSending(PacketEvent event){
	            	//Need multiple players to test
	            	//Not able to trace the sounds origin using packets. Get the sound's origin and then check if there is a players wearing the full set within 8 blocks of the sounds location.
	            	
	            	Player player = event.getPlayer();
	            	PacketContainer sound = event.getPacket();
	            	double x = sound.getIntegers().read(0) / 8.0;
	            	double y = sound.getIntegers().read(1) / 8.0;
	            	double z = sound.getIntegers().read(2) / 8.0;
	            	
	            	Location soundLoc = new Location(player.getWorld(), x, y, z);
	            	
	            	for (Entity entity : soundLoc.getWorld().getNearbyEntities(soundLoc, 8, 8, 8)) {
	            		if (entity instanceof Player) {
	            			Player otherPlayer = (Player) entity;
	            			if (!Recipes.ELVEN_HOOD.matches(baugPlugin, otherPlayer.getInventory().getHelmet())) continue;
	            			if (!Recipes.ELVEN_CLOAK.matches(baugPlugin, otherPlayer.getInventory().getChestplate())) continue;
	            			if (!Recipes.ELVEN_LEGGINGS.matches(baugPlugin, otherPlayer.getInventory().getLeggings())) continue;
	            			if (!Recipes.ELVEN_GREAVES.matches(baugPlugin, otherPlayer.getInventory().getBoots())) continue;
	            			
	            			event.setCancelled(true);
	            			break;
	            		}
	            	}
	            }
	     });
	}

	
	@EventHandler
	public void run(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (!Recipes.ELVEN_HOOD.matches(baugPlugin, player.getInventory().getHelmet())) return;
		if (!Recipes.ELVEN_CLOAK.matches(baugPlugin, player.getInventory().getChestplate())) return;
		if (!Recipes.ELVEN_LEGGINGS.matches(baugPlugin, player.getInventory().getLeggings())) return;
		if (!Recipes.ELVEN_GREAVES.matches(baugPlugin, player.getInventory().getBoots())) return;


		
		player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 5, 1));
	}
}
