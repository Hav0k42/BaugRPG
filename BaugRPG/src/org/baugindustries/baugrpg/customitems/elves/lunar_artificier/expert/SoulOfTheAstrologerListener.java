package org.baugindustries.baugrpg.customitems.elves.lunar_artificier.expert;

import java.lang.reflect.InvocationTargetException;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Profession;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedDataWatcher.Serializer;

public class SoulOfTheAstrologerListener implements Listener {
	
	public SoulOfTheAstrologerListener(Main plugin) {
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run() {
				for (Player player : Bukkit.getOnlinePlayers()) {
					if (plugin.getProfession(player) == null || !plugin.getProfession(player).equals(Profession.LUNAR_ARTIFICER)) continue;
					
					if (!Recipes.SOUL_OF_THE_ASTROLOGER.playerIsCarrying(player, plugin)) return;
					
					player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 420, 0));
					
					for (Entity entity : player.getNearbyEntities(60, 60, 60)) {
						PacketContainer packet = plugin.protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
	 					packet.getIntegers().write(0, entity.getEntityId());
	 					
	 					WrappedDataWatcher dataModifier = new WrappedDataWatcher();
            			Serializer serializer = WrappedDataWatcher.Registry.get(Byte.class);
            			dataModifier.setEntity(entity);
            			dataModifier.setObject(0, serializer, (byte) (0x40), true);
            		    packet.getWatchableCollectionModifier().write(0, dataModifier.getWatchableObjects());
            		    try {
							plugin.protocolManager.sendServerPacket(player, packet);
							plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
								public void run() {
									if (!Recipes.SOUL_OF_THE_ASTROLOGER.playerIsCarrying(player, plugin)) {
										if (entity.isGlowing()) {
											entity.setGlowing(false);
											entity.setGlowing(true);
										} else {
											entity.setGlowing(true);
											entity.setGlowing(false);
										}
								}
								}
							}, 200L, 200L);
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}, 200L, 200L);
	}
	
	
	

}
