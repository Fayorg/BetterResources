package net.fayorg.betterresources.entity.renderer;

import net.fayorg.betterresources.entity.throwable.UnstableGem;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class ThrownUnstableGemRenderer extends ThrownItemRenderer<UnstableGem> {
    public ThrownUnstableGemRenderer(EntityRendererProvider.Context p_174416_, float p_174417_, boolean p_174418_) {
        super(p_174416_, p_174417_, p_174418_);
    }
    public ThrownUnstableGemRenderer(EntityRendererProvider.Context pContext) {
        this(pContext, 1.0f, false);
    }
}
