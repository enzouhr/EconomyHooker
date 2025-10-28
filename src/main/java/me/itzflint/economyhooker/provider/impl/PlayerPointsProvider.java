package me.itzflint.economyhooker.provider.impl;

import me.itzflint.economyhooker.provider.EconomyProvider;
import me.itzflint.economyhooker.provider.ProviderValidator;
import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public final class PlayerPointsProvider implements EconomyProvider {

    private final PlayerPointsAPI api;

    public PlayerPointsProvider() {
        PlayerPoints plugin = ProviderValidator.ofPlugin("PlayerPoints", "org.black_ixx.playerpoints.PlayerPoints");
        this.api = plugin != null ? plugin.getAPI() : null;
    }

    @Override
    public @NotNull String getProviderId() {
        return "PlayerPoints";
    }

    @Override
    public boolean isEnabled() {
        return api != null;
    }

    @Override
    public @NotNull BigDecimal get(@NotNull OfflinePlayer player) {
        return BigDecimal.valueOf(api.look(player.getUniqueId()));
    }

    @Override
    public void set(@NotNull OfflinePlayer player, @NotNull BigDecimal amount) {
        api.set(player.getUniqueId(), amount.intValue());
    }

    @Override
    public void add(@NotNull OfflinePlayer player, @NotNull BigDecimal amount) {
        api.give(player.getUniqueId(), amount.intValue());
    }

    @Override
    public void remove(@NotNull OfflinePlayer player, @NotNull BigDecimal amount) {
        api.take(player.getUniqueId(), amount.intValue());
    }

    @Override
    public boolean has(@NotNull OfflinePlayer player, @NotNull BigDecimal amount) {
        return get(player).compareTo(amount) >= 0;
    }

}
