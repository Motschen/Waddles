package com.girafi.waddles.entity;

import com.girafi.waddles.init.PenguinRegistry;
import com.girafi.waddles.init.WaddlesSounds;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTables;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class EntityAdeliePenguin extends AnimalEntity {
    private static final Ingredient TEMPTATION_ITEMS = Ingredient.ofItems(Items.COD, Items.SALMON);
    public short rotationFlipper;
    private boolean moveFlipper = false;

    public EntityAdeliePenguin(World world) {
        super(PenguinRegistry.ADELIE_PENGUIN, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EntityAIExtinguishFire());
        this.goalSelector.add(2, new EscapeDangerGoal(this, 1.5D));
        this.goalSelector.add(3, new AnimalMateGoal(this, 0.8D));
        this.goalSelector.add(4, new FleeEntityGoal<>(this, PolarBearEntity.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.add(5, new TemptGoal(this, 1.0D, false, TEMPTATION_ITEMS));
        this.goalSelector.add(6, new FollowParentGoal(this, 1.1D));
        this.goalSelector.add(7, new WanderAroundGoal(this, 1.0D)); //Wander
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(9, new LookAtEntityGoal(this, EntityAdeliePenguin.class, 6.0F));
        this.goalSelector.add(10, new LookAroundGoal(this));
    }

    @Override
    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(8.0D);
        this.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED).setBaseValue(0.16D);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.isBaby() ? WaddlesSounds.ADELIE_BABY_AMBIENT : WaddlesSounds.ADELIE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return WaddlesSounds.ADELIE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return WaddlesSounds.ADELIE_DEATH;
    }

    @Override
    public void updateSwimming() {
        super.updateSwimming();
        if (world.isClient) {
            if (this.getZ() != this.prevZ) {
                if (moveFlipper) {
                    rotationFlipper++;
                }
            }
        }
    }

    @Override
    protected int getCurrentExperience(PlayerEntity player) { //getExperience
        /*if (ConfigurationHandler.dropExp) {
            return super.getExperiencePoints(player);
        }*/
        return 0;
    }

    @Override
    public boolean canBreatheInWater() { //canBreathUnderwater
        return true;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) { //isBreedingItem
        return TEMPTATION_ITEMS.equals(stack);
    }

    @Override
    public Identifier getLootTableId() {
        /*if (ConfigurationHandler.dropFish) {
            return Waddles.LOOT_ENTITIES_PENGUIN_FISH;
        }*/
        return LootTables.EMPTY;
    }

    @Override
    public PassiveEntity createChild(PassiveEntity var1) {
        return new EntityAdeliePenguin(this.world);
    }

    public float getEyeHeight() {
        return this.isBaby() ? 0.5F : 0.9F;
    }

    private class EntityAIExtinguishFire extends EscapeDangerGoal {
        EntityAIExtinguishFire() {
            super(EntityAdeliePenguin.this, 2.0D);
        }

        @Override
        public boolean canStart() {
            return (EntityAdeliePenguin.this.isBaby() || EntityAdeliePenguin.this.isOnFire()) && super.canStart();
        }
    }
}