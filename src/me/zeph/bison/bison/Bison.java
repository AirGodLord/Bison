package me.zeph.bison.bison;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.projectkorra.projectkorra.ability.CoreAbility;

import me.zeph.bison.listeners.AbilityListener;
import me.zeph.bison.listeners.SpawnEntity;
import me.zeph.bison.models.BisonAlpha;
import net.minecraft.server.v1_16_R3.WorldServer;



public final class Bison extends JavaPlugin{
	
    public static Bison plugin;

	@Override
	public void onEnable() {
		plugin = this;
		this.getServer().getPluginManager().registerEvents(new SpawnEntity(), this);
		
		plugin.getLogger().info("Successfully enabled Bison.");
		CoreAbility.registerPluginAbilities(plugin, "me.zeph.bison.abilities");
        this.registerListeners();
	}
	
	@Override
	public void onDisable() {
		plugin.getLogger().info("Successfully disabled Bison.");
	}

	 public static Bison getInstance() {
	        return plugin;
	    }

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("bison")) {						
			Player player = (Player) sender;
			Location loc = player.getLocation().add(0,1,0);
				sender.sendMessage(ChatColor.GOLD+"Bison spawned.");
				BisonAlpha pack = new BisonAlpha(loc);
				
				WorldServer world = ((CraftWorld) loc.getWorld()).getHandle();
	            
				world.addEntity(pack);
			
				
	            LivingEntity e = (LivingEntity) pack.getBukkitEntity();
	                      
				return true;
			}
		return false;
}
	

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new AbilityListener(), this);
    }
	
}
