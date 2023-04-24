package org.baugindustries.baugrpg.customitems.dwarves.radiant_metallurgist.expert;

import java.lang.reflect.InvocationTargetException;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityCategory;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;

import it.unimi.dsi.fastutil.ints.IntArrayList;

public class RadiantAxeListener implements Listener {
	private Main plugin;

	private double fragChance = 0.45;
	private int fragCount = 40;
	
	public RadiantAxeListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onHit(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Player)) return;
		Player player = (Player) event.getDamager();
		
		if (!Recipes.RADIANT_AXE.matches(plugin, player.getInventory().getItemInMainHand())) return;
		
		if (Math.random() > fragChance) return;
		
		if (!(event.getEntity() instanceof LivingEntity)) return;
		
		LivingEntity lEntity = (LivingEntity) event.getEntity();
		
		for (int i = 0; i < fragCount; i++) {
			Arrow frag = lEntity.launchProjectile(Arrow.class, new Vector((Math.random() * 2) - 1, (Math.random() * 2) - 1, (Math.random() * 2) - 1).normalize().multiply(1.4));
			frag.getPersistentDataContainer().set(new NamespacedKey(plugin, "radiantDirt"), PersistentDataType.INTEGER, 1);
			frag.getPersistentDataContainer().set(new NamespacedKey(plugin, "race"), PersistentDataType.INTEGER, plugin.getRace(player));
			ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(frag.getLocation(), EntityType.ARMOR_STAND);
			armorStand.setInvisible(true);
			armorStand.setInvulnerable(true);
			armorStand.getEquipment().setHelmet(new ItemStack(Material.BROWN_DYE));
			armorStand.setHeadPose(new EulerAngle(frag.getVelocity().getX(), frag.getVelocity().getY(), frag.getVelocity().getZ()));
			
			armorStand.setSmall(true);
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				  public void run() {
					  	if (frag.isValid()) {
					  		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, this, 1L);
					  	} else {
					  		armorStand.remove();
					  	}
					  	Location newLoc = new Location(frag.getWorld(), frag.getLocation().getX(), frag.getLocation().getY() - 1.3, frag.getLocation().getZ());
						armorStand.teleport(newLoc);
				  }
			 }, 1L);
			
			PacketContainer packet = plugin.protocolManager.createPacket(PacketType.Play.Server.ENTITY_DESTROY);
			int[] entityID = {frag.getEntityId()};
			packet.getModifier().write(0, new IntArrayList(entityID));
			for (Entity entity : frag.getNearbyEntities(40, 40, 40)) {
				if (entity instanceof Player) {
					try {
						plugin.protocolManager.sendServerPacket((Player) entity, packet);
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
			
		}
		
	}
	
	
	
	@EventHandler
	public void onHit(ProjectileHitEvent event) {
		if (!event.getEntity().getPersistentDataContainer().has(new NamespacedKey(plugin, "radiantDirt"), PersistentDataType.INTEGER)) return;
		Arrow frag = (Arrow) event.getEntity();
		event.setCancelled(true);
		if (event.getHitEntity() != null) {//hit entity
			if (!(event.getHitEntity() instanceof LivingEntity)) return;
			if (event.getHitEntity() instanceof Player && plugin.getRace((Player)event.getHitEntity()) == event.getEntity().getPersistentDataContainer().get(new NamespacedKey(plugin, "race"), PersistentDataType.INTEGER)) return;
			LivingEntity hitEntity = (LivingEntity) event.getHitEntity();
			hitEntity.damage(plugin.damageArmorCalculation(hitEntity, 4));
			if (hitEntity.getCategory().equals(EntityCategory.UNDEAD)) {
				hitEntity.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 1));
			} else {
				hitEntity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 1));
			}
		}
		frag.remove();
	}

}
