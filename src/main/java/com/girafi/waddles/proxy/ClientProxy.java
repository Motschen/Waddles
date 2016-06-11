package com.girafi.waddles.proxy;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import com.girafi.waddles.client.renderer.RenderPenguin;
import com.girafi.waddles.entity.EntityPenguin;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenders() {
        RenderingRegistry.registerEntityRenderingHandler(EntityPenguin.class, new IRenderFactory<EntityPenguin>() {
            @Override
            public Render<? super EntityPenguin> createRenderFor(RenderManager manager) {
                return new RenderPenguin(manager);
            }
        });
    }
}