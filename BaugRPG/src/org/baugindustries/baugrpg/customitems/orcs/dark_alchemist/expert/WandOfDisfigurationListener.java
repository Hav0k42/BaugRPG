package org.baugindustries.baugrpg.customitems.orcs.dark_alchemist.expert;

import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;

public class WandOfDisfigurationListener implements Listener {
	private Main plugin;
	
	EntityType[] zombies = {EntityType.ZOMBIE, EntityType.HUSK, EntityType.DROWNED, EntityType.ZOMBIE_VILLAGER};
	EntityType[] skeletons = {EntityType.SKELETON, EntityType.STRAY, EntityType.WITHER_SKELETON};
	EntityType[] illagers = {EntityType.PILLAGER, EntityType.VINDICATOR, EntityType.EVOKER, EntityType.ILLUSIONER, EntityType.WITCH};
	EntityType[] horses = {EntityType.HORSE, EntityType.ZOMBIE_HORSE, EntityType.SKELETON_HORSE, EntityType.MULE, EntityType.DONKEY};
	EntityType[] tanks = {EntityType.HOGLIN, EntityType.ZOGLIN, EntityType.RAVAGER};
	EntityType[] winged = {EntityType.CHICKEN, EntityType.BAT, EntityType.PHANTOM, EntityType.BEE, EntityType.VEX, EntityType.PARROT};
	EntityType[] slimes = {EntityType.SLIME, EntityType.MAGMA_CUBE};
	EntityType[] guardians = {EntityType.GUARDIAN, EntityType.ELDER_GUARDIAN};
	EntityType[] worms = {EntityType.SILVERFISH, EntityType.ENDERMITE};
	EntityType[] piglins = {EntityType.PIGLIN, EntityType.PIGLIN_BRUTE, EntityType.ZOMBIFIED_PIGLIN};
	EntityType[] fish = {EntityType.PUFFERFISH, EntityType.TROPICAL_FISH, EntityType.SALMON, EntityType.COD, EntityType.AXOLOTL};
	EntityType[] spiders = {EntityType.SPIDER, EntityType.CAVE_SPIDER};
	EntityType[] llamas = {EntityType.LLAMA, EntityType.TRADER_LLAMA};
	EntityType[] bears = {EntityType.POLAR_BEAR, EntityType.PANDA};
	EntityType[] pets = {EntityType.CAT, EntityType.OCELOT, EntityType.FOX, EntityType.WOLF, EntityType.GOAT};
	EntityType[] cows = {EntityType.COW, EntityType.MUSHROOM_COW};
	EntityType[] squids = {EntityType.SQUID, EntityType.GLOW_SQUID};
	EntityType[] golems = {EntityType.IRON_GOLEM, EntityType.SNOWMAN};
	EntityType[] villagers = {EntityType.VILLAGER, EntityType.WANDERING_TRADER};
	
	EntityType[][] types = {zombies, skeletons, illagers, horses, tanks, winged, slimes, guardians, worms, piglins,
			fish, spiders, llamas, bears, pets, cows, squids, golems, villagers};
	
	
	public WandOfDisfigurationListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!Recipes.WAND_OF_DISFIGURATION.matches(plugin, player.getInventory().getItemInMainHand())) return;
		} else {
			if (!Recipes.WAND_OF_DISFIGURATION.matches(plugin, player.getInventory().getItemInOffHand())) return;
		}
		
		if (!(event.getRightClicked() instanceof LivingEntity)) return;
		
		
		
		LivingEntity entity = (LivingEntity) event.getRightClicked();
		
		
		if (entity.getPersistentDataContainer().has(new NamespacedKey(plugin, "flower"), PersistentDataType.STRING)) return;
		if (entity.getPersistentDataContainer().has(new NamespacedKey(plugin, "wasp"), PersistentDataType.STRING)) return;
		
		for (EntityType[] type : types) {
			for (EntityType entityType : type) {
				if (entity.getType().equals(entityType)) {
					LivingEntity lEntity = (LivingEntity) entity.getWorld().spawnEntity(entity.getLocation(), type[(int) (Math.random() * type.length)]);
					lEntity.setCustomName(entity.getCustomName());
					lEntity.setCustomNameVisible(entity.isCustomNameVisible());
					lEntity.getEquipment().setArmorContents(entity.getEquipment().getArmorContents());
					lEntity.setAbsorptionAmount(entity.getAbsorptionAmount());
					lEntity.addPotionEffects(entity.getActivePotionEffects());
					for (Entity passenger : entity.getPassengers()) {
						lEntity.addPassenger(passenger);
					}
					lEntity.setAI(entity.hasAI());
					lEntity.setArrowCooldown(entity.getArrowCooldown());
					lEntity.setArrowsInBody(entity.getArrowsInBody());
					lEntity.setCanPickupItems(entity.getCanPickupItems());
					lEntity.setCollidable(entity.isCollidable());
					for (UUID uuid : entity.getCollidableExemptions()) {
						lEntity.getCollidableExemptions().add(uuid);
					}
					lEntity.setFallDistance(entity.getFallDistance());
					lEntity.setFireTicks(entity.getFireTicks());
					lEntity.setFreezeTicks(entity.getFreezeTicks());
					lEntity.setGliding(entity.isGliding());
					lEntity.setGlowing(entity.isGlowing());
					lEntity.setGravity(entity.hasGravity());
					if (lEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() > entity.getHealth()) {
						lEntity.setHealth(entity.getHealth());
					} else {
						lEntity.setHealth(lEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
					}
					lEntity.setInvisible(entity.isInvisible());
					lEntity.setInvulnerable(entity.isInvulnerable());
					lEntity.setLastDamage(entity.getLastDamage());
					lEntity.setLastDamageCause(entity.getLastDamageCause());
					if (entity.isLeashed()) {
						lEntity.setLeashHolder(entity.getLeashHolder());
					}
					lEntity.setMaximumAir(entity.getMaximumAir());
					lEntity.setMaximumNoDamageTicks(entity.getMaximumNoDamageTicks());
					lEntity.setNoDamageTicks(entity.getNoDamageTicks());
					lEntity.setOp(entity.isOp());
					lEntity.setPersistent(entity.isPersistent());
					lEntity.setPortalCooldown(entity.getPortalCooldown());
					lEntity.setRemainingAir(entity.getRemainingAir());
					lEntity.setRemoveWhenFarAway(entity.getRemoveWhenFarAway());
					lEntity.setRotation(entity.getLocation().getYaw(), entity.getLocation().getPitch());
					lEntity.setSilent(entity.isSilent());
					lEntity.setSwimming(entity.isSwimming());
					lEntity.setTicksLived(entity.getTicksLived());
					lEntity.setVelocity(entity.getVelocity());
					lEntity.setVisualFire(entity.isVisualFire());
					
					if (lEntity instanceof Ageable && entity instanceof Ageable) {
						if (((Ageable)entity).isAdult()) {
							((Ageable)lEntity).setAdult();
						} else {
							((Ageable)lEntity).setBaby();
						}
						((Ageable)lEntity).setAge(((Ageable)entity).getAge());
					}
					
					entity.remove();
					return;
				}
			}
		}
		
	}
	
	@EventHandler
	public void onInventoryClick(BlockPlaceEvent event) {
		if (!Recipes.WAND_OF_DISFIGURATION.matches(plugin, event.getItemInHand())) return;
		event.setCancelled(true);
	}

}
