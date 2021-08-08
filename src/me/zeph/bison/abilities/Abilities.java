package me.zeph.bison.abilities;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.ability.AirAbility;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.ability.MultiAbility;
import com.projectkorra.projectkorra.ability.util.MultiAbilityManager;
import com.projectkorra.projectkorra.ability.util.MultiAbilityManager.MultiAbilityInfoSub;
import com.projectkorra.projectkorra.util.ParticleEffect;


public class Abilities extends AirAbility implements MultiAbility{

	//Config variables
	
	
	
	//Set variables
	private enum BisonModes {
		YIP, HOVER, DIVE 
	}
	
	private LivingEntity e;

	
	
	public Abilities(Player player, LivingEntity e) {
		super(player);
		this.e = e;
		MultiAbilityManager.bindMultiAbility(player, "Bison");
		start();
	}

	@Override
	public long getCooldown() {
		return 0;
	}

	@Override
	public Location getLocation() {
		return null;
	}

	@Override
	public String getName() {
		return "Bison";
	}

	@Override
	public boolean isHarmlessAbility() {
		return false;
	}

	@Override
	public boolean isSneakAbility() {
		return false;
	}

	@Override
	public void progress() {
		
		if (e.isDead() || e==null) {
			this.remove();
			return;
		}
		
		if (player.isDead() || !player.isOnline() || player.getVehicle() == null) {
			this.remove();
			return;
		}
		
		e.setFallDistance(0);
		
		switch (player.getInventory().getHeldItemSlot()+1) {
		case 1:
			playParticles();
			playSound();
			e.setGravity(false);
			e.setVelocity(player.getEyeLocation().getDirection());
			break;
		case 2:
			playParticles();
			e.setGravity(false);
			e.setVelocity(new Vector(0,0,0));
			break;
		case 3:
			e.setGravity(false);
			e.setVelocity(new Vector (0,-0.5,0));
			this.remove();
			e.removePassenger(player);
		}
		
	}

	public void playParticles() {
		Location loc = e.getLocation().subtract(0,1,0);
		ParticleEffect.SPELL.display(loc, 5, 1, 0.2, 1);
	}
	
	public void playSound() {
		Location loc = player.getLocation();
		loc.getWorld().playSound(loc, Sound.ENTITY_ENDER_DRAGON_FLAP, 0.5F, 2);
	}
	@Override
	public ArrayList<MultiAbilityInfoSub> getMultiAbilities() {
		final ArrayList<MultiAbilityInfoSub> abils = new ArrayList<>();
		abils.add(new MultiAbilityInfoSub("YipYip", Element.AIR));
		abils.add(new MultiAbilityInfoSub("Steady", Element.AIR));
		abils.add(new MultiAbilityInfoSub("Dismount", Element.AIR));
		return abils;
	}

	@Override
	public String getDescription() {
		return "By: __Zephyrus \n"
				+"Fly around on your sky bison."; 
		
	}
	
	@Override
	public String getInstructions() {
		return "Select a slot to control your bison.\n "
				+ "1) YipYip: Fly your bison as Aang used to - requires saddle.\n"
				+ "2) Steady: Float mid-air.\n"
				+ "3) Dismount: Exit your bison and have it float to the ground.";
		
	}
	
	@Override
	public void remove() {
		super.remove();
		MultiAbilityManager.unbindMultiAbility(player);
		MultiAbilityManager.remove(player);
	}

}


