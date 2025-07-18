package me.secondairy.standardeyebreaks.mixin;

import me.secondairy.standardeyebreaks.random.StandardRandomManager;
import net.minecraft.item.Items;
import net.minecraft.network.ClientConnection;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ConnectedClientData;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.StatHandler;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    @Inject(method = "onPlayerConnect", at = @At("TAIL"))
    private void onPlayerConnect(ClientConnection connection, ServerPlayerEntity player, ConnectedClientData clientData, CallbackInfo ci) {

        RegistryKey<World> worldKey = player.getWorld().getRegistryKey();
        long seed = Objects.requireNonNull(Objects.requireNonNull(player.getServer()).getWorld(worldKey)).getSeed();

        StatHandler statHandler = player.getStatHandler();
        int eyeUses = statHandler.getStat(Stats.USED.getOrCreateStat(Items.ENDER_EYE));

        if (!StandardRandomManager.isInitialized()) {
            StandardRandomManager.init(seed);
            if (eyeUses != 0) {
                for (int i = eyeUses; i > 0; i--) {
                    StandardRandomManager.get().nextInt(5);
                }
            }
        } else {
            if (seed != StandardRandomManager.getSeed()) {
                StandardRandomManager.close();
                StandardRandomManager.init(seed);
            } else {
                if (eyeUses == 0) {
                    StandardRandomManager.close();
                    StandardRandomManager.init(seed);
                }
            }
        }
    }
}

