package org.baugindustries.baugrpg.religion.dungeons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Pig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.EntitiesLoadEvent;

public class ElfDungeonManager implements Listener {
	private Main plugin;
	
	public ElfDungeonManager(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(EntitiesLoadEvent event) {
		Pig manager = null;
		for (Entity entity : event.getEntities()) {
			if (entity instanceof Pig) {
				Pig pig = (Pig)entity;
				if (
						pig.isInvisible() &&
						!pig.isCollidable() &&
						pig.isInvulnerable() &&
						pig.getCustomName().equals("baugreligions:elf_dungeon_manager")) {
					
					manager = pig;
					break;
					
				}
			}
		}
		
		if (manager == null) return;
		
		
	}
}
