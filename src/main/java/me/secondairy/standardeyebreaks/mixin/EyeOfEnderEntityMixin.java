package me.secondairy.standardeyebreaks.mixin;

import me.secondairy.standardeyebreaks.random.StandardRandomManager;
import net.minecraft.entity.EyeOfEnderEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EyeOfEnderEntity.class)
public class EyeOfEnderEntityMixin {

    @Shadow
    private boolean dropsItem;

    @Inject(method = "initTargetPos", at = @At("RETURN"))
    public void initTargetPos(Vec3d pos, CallbackInfo ci) {
        this.dropsItem = StandardRandomManager.get().nextInt(5) > 0;
    }
}
