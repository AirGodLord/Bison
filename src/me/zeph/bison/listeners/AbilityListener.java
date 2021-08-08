package me.zeph.bison.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.spigotmc.event.entity.EntityMountEvent;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.ability.util.MultiAbilityManager;

import me.zeph.bison.abilities.Abilities;

public class AbilityListener implements Listener{

	 @EventHandler
	    public void ride(EntityMountEvent event) {
	    	if (!(event.getEntity() instanceof Player)) {
	    		return;
	    	}
	    
	    	Player player = (Player) event.getEntity();
	    	if (!player.isInsideVehicle()) {
	    		return;
	    	}
	    
	    	if (!(event.getMount() instanceof LivingEntity)) {
	    		return;
	    	}
	   
	    	if (! event.getMount().getCustomName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&oBison"))){
	    		return;
	    	}
	    	
	    	LivingEntity e = (LivingEntity) event.getMount();
			BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);

			if (event.isCancelled() || bPlayer == null) {
				return;
			}
			
			if (!MultiAbilityManager.hasMultiAbilityBound(player)) {
				new Abilities(player, e);
			}
	    	
	    }
	 
	 
}
