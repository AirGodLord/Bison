	package me.zeph.bison.models;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import net.minecraft.server.v1_16_R3.AttributeProvider;
import net.minecraft.server.v1_16_R3.AxisAlignedBB;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.ChatComponentText;
import net.minecraft.server.v1_16_R3.ControllerMove;
import net.minecraft.server.v1_16_R3.ControllerMoveFlying;
import net.minecraft.server.v1_16_R3.DamageSource;
import net.minecraft.server.v1_16_R3.Entity;
import net.minecraft.server.v1_16_R3.EntityBird;
import net.minecraft.server.v1_16_R3.EntityFlying;
import net.minecraft.server.v1_16_R3.EntityHuman;
import net.minecraft.server.v1_16_R3.EntityInsentient;
import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.EntityMonster;
import net.minecraft.server.v1_16_R3.EntityPig;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.GenericAttributes;
import net.minecraft.server.v1_16_R3.ISteerable;
import net.minecraft.server.v1_16_R3.MathHelper;
import net.minecraft.server.v1_16_R3.NavigationAbstract;
import net.minecraft.server.v1_16_R3.NavigationFlying;
import net.minecraft.server.v1_16_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_16_R3.PathfinderGoalFollowEntity;
import net.minecraft.server.v1_16_R3.PathfinderGoalFollowOwner;
import net.minecraft.server.v1_16_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_16_R3.PathfinderGoalPanic;
import net.minecraft.server.v1_16_R3.PathfinderGoalPerch;
import net.minecraft.server.v1_16_R3.PathfinderGoalRandomFly;
import net.minecraft.server.v1_16_R3.PathfinderGoalRandomStrollLand;
import net.minecraft.server.v1_16_R3.PathfinderGoalSit;
import net.minecraft.server.v1_16_R3.SoundEffect;
import net.minecraft.server.v1_16_R3.SoundEffects;
import net.minecraft.server.v1_16_R3.Vec3D;
import net.minecraft.server.v1_16_R3.World;

public class BisonAlpha extends EntityPig {

	public BisonAlpha(Location loc) {
		super(EntityTypes.PIG, ((CraftWorld)loc.getWorld()).getHandle());
		this.setPosition(loc.getX(), loc.getY(), loc.getZ());
		this.setCanPickupLoot(false);
		this.setCustomNameVisible(true);
		this.setNoGravity(true);
		this.setCustomName(new ChatComponentText(ChatColor.translateAlternateColorCodes('&', "&oBison")));
		this.moveController = new BisonAlpha.ControllerPig(this);
		//this.moveController = new ControllerMoveFlying(this, 10, true);
		
	}

	public void initPathFinder() {
		super.initPathfinder();	
		this.goalSelector.a(0, new PathfinderGoalFloat(this));
		this.goalSelector.a(1, new PathfinderGoalRandomStrollLand(this, 1.0D));
		this.goalSelector.a(2, new PathfinderGoalFollowEntity(this, 1.0D, 3.0F, 7.0F));
		this.goalSelector.a(3, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
		
	}


	 public static AttributeProvider.Builder eK() {
	        return EntityInsentient.p().a(GenericAttributes.MAX_HEALTH, 20.0D)
	        		.a(GenericAttributes.MOVEMENT_SPEED, 0.30D)
	        		.a(GenericAttributes.FLYING_SPEED, 0.50D );
	    }

	   @Override
	    public boolean er() {
	        Entity entity = this.getRidingPassenger();

	        if (!(entity instanceof EntityHuman)) {
	            return false;
	        } else {
	        	Player player = (Player) entity;
	            this.setYawPitch(player.getEyeLocation().getPitch(), player.getEyeLocation().getYaw());
	            return true;
	        }
	    }


	   @Override
	    protected NavigationAbstract b(World world) {
	        NavigationFlying navigationflying = new NavigationFlying(this, world);

	        navigationflying.a(false);
	        navigationflying.d(true);
	        navigationflying.b(true);
	        return navigationflying;
	    }


	    static class ControllerPig extends ControllerMove {

	        private final EntityPig i;
	        private int j;

	        public ControllerPig(EntityPig entitypig) {
	            super(entitypig);
	            this.i = entitypig;
	        }

	        @Override
	        public void a() {
	            if (this.h == ControllerMove.Operation.MOVE_TO) {
	                if (this.j-- <= 0) {
	                    this.j += this.i.getRandom().nextInt(5) + 2;
	                    Vec3D vec3d = new Vec3D(this.b - this.i.locX(), this.c - this.i.locY(), this.d - this.i.locZ());
	                   
	                    double d0 = vec3d.f();

	                    vec3d = vec3d.d();
	                    if (this.a(vec3d, MathHelper.f(d0))) {
	                        this.i.setMot(this.i.getMot().e(vec3d.a(0.1D)));
	                    } else {
	                        this.h = ControllerMove.Operation.WAIT;
	                    }
	                }

	            }
	        }

	        private boolean a(Vec3D vec3d, int i) {
	            AxisAlignedBB axisalignedbb = this.i.getBoundingBox();

	            for (int j = 1; j < i; ++j) {
	                axisalignedbb = axisalignedbb.c(vec3d);
	                if (!this.i.world.getCubes(this.i, axisalignedbb)) {
	                    return false;
	                }
	            }

	            return true;
	        }
	    }
	    
	    
	    @Override
	    protected SoundEffect getSoundAmbient() {
	        return SoundEffects.ENTITY_PANDA_AGGRESSIVE_AMBIENT;
	    }

	    @Override
	    protected SoundEffect getSoundHurt(DamageSource damagesource) {
	        return SoundEffects.ENTITY_PANDA_HURT;
	    }

	    @Override
	    protected SoundEffect getSoundDeath() {
	        return SoundEffects.ENTITY_RAVAGER_DEATH;
	    }


	 
	       
	    
	
}


