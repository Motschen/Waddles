package com.girafi.waddles.client.model;

import com.girafi.waddles.entity.EntityAdeliePenguin;
import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class ModelPenguin extends AnimalModel<EntityAdeliePenguin> implements ModelWithHead {
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart beak;
    private final ModelPart flipperRight;
    private final ModelPart flipperLeft;
    private final ModelPart feetLeft;
    private final ModelPart feetRight;
    private final ModelPart tail;

    public ModelPenguin() {
        this.beak = new ModelPart(this, 18, 0);
        this.beak.setPivot(0.0F, 0.0F, 0.0F);
        this.beak.addCuboid(-0.5F, -3.0F, -4.0F, 1, 2, 3, 0.0F);
        this.setRotateAngle(beak, 0.08726646259971647F, -0.0F, 0.0F);

        this.body = new ModelPart(this, 0, 9);
        this.body.setPivot(0.0F, 12.0F, 1.0F);
        this.body.addCuboid(-2.5F, 0.0F, -2.0F, 5, 11, 5, 0.0F);

        this.feetRight = new ModelPart(this, 0, 25);
        this.feetRight.setPivot(-1.0F, 11.0F, 0.0F);
        this.feetRight.addCuboid(-2.0F, 0.0F, -3.0F, 2, 1, 3, 0.0F);
        this.setRotateAngle(feetRight, 0.0F, 0.2617993877991494F, 0.0F);

        this.head = new ModelPart(this, 0, 0);
        this.head.setPivot(0.0F, 12.0F, 0.0F);
        this.head.addCuboid(-2.0F, -4.0F, -2.0F, 4, 4, 5, 0.0F);

        this.tail = new ModelPart(this, 20, 20);
        this.tail.setPivot(0.0F, 11.0F, 3.0F);
        this.tail.addCuboid(-1.5F, -1.0F, 0.0F, 3, 3, 1, 0.0F);
        this.setRotateAngle(tail, 1.2566370614359172F, 0.0F, 0.0F);

        this.flipperRight = new ModelPart(this, 20, 10);
        this.flipperRight.setPivot(-2.5F, 1.0F, 0.0F);
        this.flipperRight.addCuboid(-1.0F, 0.0F, -1.0F, 1, 7, 3, 0.0F);
        this.setRotateAngle(flipperRight, 0.0F, 0.0F, 0.08726646259971647F);

        this.feetLeft = new ModelPart(this, 0, 25);
        this.feetLeft.mirror = true;
        this.feetLeft.setPivot(1.0F, 11.0F, 0.0F);
        this.feetLeft.addCuboid(0.0F, 0.0F, -3.0F, 2, 1, 3, 0.0F);
        this.setRotateAngle(feetLeft, 0.0F, -0.2617993877991494F, 0.0F);

        this.flipperLeft = new ModelPart(this, 20, 10);
        this.flipperLeft.mirror = true;
        this.flipperLeft.setPivot(2.5F, 1.0F, 0.0F);
        this.flipperLeft.addCuboid(0.0F, 0.0F, -1.0F, 1, 7, 3, 0.0F);
        this.setRotateAngle(flipperLeft, 0.0F, 0.0F, -0.08726646259971647F);

        this.head.addChild(this.beak);
        this.body.addChild(this.flipperRight);
        this.body.addChild(this.flipperLeft);
        this.body.addChild(this.feetRight);
        this.body.addChild(this.feetLeft);
        this.body.addChild(this.tail);
    }

    private void setRotateAngle(ModelPart cuboid, float x, float y, float z) {
        cuboid.pitch = x;
        cuboid.yaw = y;
        cuboid.roll = z;
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumer vertexConsumer, int i, int j, float r, float g, float b, float f) {
        body.render(matrixStack, vertexConsumer, i, j, r, g, b, f);
    }

    @Override
    public void setAngles(EntityAdeliePenguin penguin, float limbSwing, float limbSwingAmount, float HeadYaw, float headPitch, float scaleFactor) {
        this.head.pitch = headPitch * 0.017453292F;
        this.head.yaw = HeadYaw * 0.017453292F;
        this.beak.pitch = this.head.pitch;
        this.beak.yaw = this.head.yaw;
        this.head.roll = (MathHelper.cos(limbSwing * 1.3324F) * 1.4F * limbSwingAmount) / 6;
        this.body.roll = (MathHelper.cos(limbSwing * 1.3324F) * 1.4F * limbSwingAmount) / 6;
        this.feetRight.pitch = MathHelper.cos(limbSwing * 1.3324F) * 1.2F * limbSwingAmount;
        this.feetLeft.pitch = MathHelper.cos(limbSwing * 1.3324F + (float) Math.PI) * 1.2F * limbSwingAmount;
        this.flipperRight.roll = 0.08726646259971647F + (MathHelper.cos((float) penguin.rotationFlipper) * limbSwingAmount);
        this.flipperLeft.roll = -0.08726646259971647F + (MathHelper.cos((float) penguin.rotationFlipper + (float) Math.PI) * limbSwingAmount);
        this.tail.yaw = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * 1.4F * limbSwingAmount;

        //Roation angles when swimming
        //this.body.rotateAngleX = penguin.bodyRotation;

        /*this.flipperRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F; //TODO
        this.flipperLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;*/
    }

    @Override
    public ModelPart getHead(){
        return head;
    }
    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of(this.head, this.beak);
    }

    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(this.body, this.feetLeft, this.feetRight, this.flipperLeft, this.flipperRight,this.tail);
    }
}