package me.zeph.bison.listeners;
 
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.ability.util.MultiAbilityManager;

import me.zeph.bison.bison.Bison;
import me.zeph.bison.models.BisonAlpha;
import net.minecraft.server.v1_16_R3.EntityPig;
import net.minecraft.server.v1_16_R3.EntityZombie;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.WorldServer;
 
 
public class SpawnEntity implements Listener {
 
	
    @EventHandler
    public void onSpawn(EntitySpawnEvent event) {
        if (!(event.getEntity() instanceof EntityPig)) {
            return;
        }
        if (event.getLocation().getBlock().isLiquid()) {
            return;
        }
    
        if ((int) (Math.random() * 4) == 1) {
            event.setCancelled(true);
            
            BisonAlpha pack = new BisonAlpha(event.getLocation());
            
            WorldServer world = ((CraftWorld) event.getLocation().getWorld()).getHandle();
            
            world.addEntity(pack);
  
            LivingEntity e = (LivingEntity) pack.getBukkitEntity();
            e.setInvulnerable(true);
        }
        
        
    }
    
	
    
}