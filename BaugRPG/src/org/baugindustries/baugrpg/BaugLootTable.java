package org.baugindustries.baugrpg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_18_R2.CraftLootTable;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;

import net.minecraft.server.level.WorldServer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameter;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameterSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;
import net.minecraft.world.phys.Vec3D;

public class BaugLootTable extends CraftLootTable {

	public BaugLootTable(NamespacedKey key, LootTable handle) {
		super(key, handle);
	}
	
	@Override
    public Collection<ItemStack> populateLoot(Random random, LootContext context) {
        LootTableInfo nmsContext = convertContext(context);
        List<net.minecraft.world.item.ItemStack> nmsItems = getHandle().a(nmsContext);
        Collection<ItemStack> bukkit = new ArrayList<>(nmsItems.size());

        for (net.minecraft.world.item.ItemStack item : nmsItems) {
            if (item.b()) {
                continue;
            }
            bukkit.add(CraftItemStack.asBukkitCopy(item));
        }

        return bukkit;
    }

	private LootTableInfo convertContext(LootContext context) {
        Location loc = context.getLocation();
        WorldServer handle = ((CraftWorld) loc.getWorld()).getHandle();

        LootTableInfo.Builder builder = new LootTableInfo.Builder(handle);
        setMaybe(builder, LootContextParameters.f, new Vec3D(loc.getX(), loc.getY(), loc.getZ()));
        if (getHandle() != LootTable.a) {
            // builder.luck(context.getLuck());

            if (context.getLootedEntity() != null) {
                Entity nmsLootedEntity = ((CraftEntity) context.getLootedEntity()).getHandle();
                setMaybe(builder, LootContextParameters.a, nmsLootedEntity);
                setMaybe(builder, LootContextParameters.c, DamageSource.n);
                setMaybe(builder, LootContextParameters.f, nmsLootedEntity.cV());
            }

            if (context.getKiller() != null) {
                EntityHuman nmsKiller = ((CraftHumanEntity) context.getKiller()).getHandle();
                setMaybe(builder, LootContextParameters.d, nmsKiller);
                // If there is a player killer, damage source should reflect that in case loot tables use that information
                setMaybe(builder, LootContextParameters.c, DamageSource.a(nmsKiller));
                
                //NEW LINE
                setMaybe(builder, LootContextParameters.i, (CraftItemStack.asNMSCopy(context.getKiller().getItemInUse())));
                //END NEW LINE
                
                setMaybe(builder, LootContextParameters.b, nmsKiller); // SPIGOT-5603 - Set minecraft:killed_by_player
            }

            // SPIGOT-5603 - Use LootContext#lootingModifier
            if (context.getLootingModifier() != LootContext.DEFAULT_LOOT_MODIFIER) {
                setMaybe(builder, LootContextParameters.LOOTING_MOD, context.getLootingModifier());
            }
            
        }

        // SPIGOT-5603 - Avoid IllegalArgumentException in LootTableInfo#build()
        LootContextParameterSet.Builder nmsBuilder = new LootContextParameterSet.Builder();
        for (LootContextParameter<?> param : getHandle().a().a()) {
            nmsBuilder.a(param);
        }
        for (LootContextParameter<?> param : getHandle().a().b()) {
            if (!getHandle().a().a().contains(param)) {
                nmsBuilder.b(param);
            }
        }
        nmsBuilder.b(LootContextParameters.LOOTING_MOD);

        return builder.a(nmsBuilder.a());
    }
	
	
	private <T> void setMaybe(LootTableInfo.Builder builder, LootContextParameter<T> param, T value) {
        if (getHandle().a().a().contains(param) || getHandle().a().b().contains(param)) {
            builder.a(param, value);
        }
    }
	
}
