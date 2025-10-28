package me.itzflint.economyhooker.provider.impl;

import me.itzflint.economyhooker.provider.EconomyProvider;
import me.itzflint.economyhooker.provider.ProviderValidator;
import com.ystoreplugins.ypoints.Main;
import com.ystoreplugins.ypoints.api.yPointsAPI;
import com.ystoreplugins.ypoints.models.PlayerPoints;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;

public final class yPointsProvider implements EconomyProvider {

    private final yPointsAPI api;

    public yPointsProvider() {
        Main plugin = ProviderValidator.ofPlugin("yPoints", "com.ystoreplugins.ypoints.Main");
        this.api = plugin != null ? yPointsAPI.ypointsapi : null;
    }

    @Override
    public @NotNull String getProviderId() {
        return "yPoints";
    }

    @Override
    public boolean isEnabled() {
        return api != null;
    }

    private @Nullable PlayerPoints getUser(@NotNull OfflinePlayer player) {
        if (!player.isOnline()) return null;
        return api.getPlayer(player.getPlayer());
    }

    @Override
    public @NotNull BigDecimal get(@NotNull OfflinePlayer offlinePlayer) {
        PlayerPoints user = getUser(offlinePlayer);
        return user != null ? BigDecimal.valueOf(api.getPoints(user)) : BigDecimal.ZERO;
    }

    @Override
    public void set(@NotNull OfflinePlayer offlinePlayer, @NotNull BigDecimal bigDecimal) {
        PlayerPoints user = getUser(offlinePlayer);
        if (user == null) return;
        api.setPoints(user, bigDecimal.doubleValue());
    }

    @Override
    public void add(@NotNull OfflinePlayer offlinePlayer, @NotNull BigDecimal bigDecimal) {
        PlayerPoints user = getUser(offlinePlayer);
        if (user == null) return;
        api.addPoints(user, bigDecimal.doubleValue());
    }

    @Override
    public void remove(@NotNull OfflinePlayer offlinePlayer, @NotNull BigDecimal bigDecimal) {
        PlayerPoints user = getUser(offlinePlayer);
        if (user == null) return;
        api.removePoints(user, bigDecimal.doubleValue());
    }

    @Override
    public boolean has(@NotNull OfflinePlayer offlinePlayer, @NotNull BigDecimal bigDecimal) {
        return get(offlinePlayer).compareTo(bigDecimal) >= 0;
    }

}
