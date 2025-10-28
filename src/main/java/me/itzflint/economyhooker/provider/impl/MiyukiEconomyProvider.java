package me.itzflint.economyhooker.provider.impl;

import app.miyuki.miyukieconomy.bukkit.BukkitMiyukiEconomyBootstrap;
import app.miyuki.miyukieconomy.common.api.MiyukiEconomyAPI;
import app.miyuki.miyukieconomy.common.currency.Currency;
import app.miyuki.miyukieconomy.common.user.User;
import me.itzflint.economyhooker.provider.MultiEconomyProvider;
import me.itzflint.economyhooker.provider.ProviderValidator;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public final class MiyukiEconomyProvider implements MultiEconomyProvider {

    private final MiyukiEconomyAPI api;

    public MiyukiEconomyProvider() {
        BukkitMiyukiEconomyBootstrap plugin = ProviderValidator.ofPlugin("MiyukiEconomy", "app.miyuki.miyukieconomy.bukkit.BukkitMiyukiEconomyBootstrap");
        this.api = plugin != null ? plugin.getMiyukiEconomy().getMiyukiEconomyAPI() : null;
    }

    private @NotNull User getUser(@NotNull OfflinePlayer player) {
        return api.getUser(player.getName());
    }

    private @NotNull Currency getCurrency(@NotNull String economyId) {
        return api.getCurrency(economyId);
    }

    @Override
    public @NotNull String getProviderId() {
        return "MiyukiEconomy";
    }

    @Override
    public boolean isEnabled() {
        return api != null;
    }

    @Override
    public @NotNull BigDecimal get(@NotNull OfflinePlayer player, @NotNull String economyId) {
        return api.getBalance(getUser(player), getCurrency(economyId));
    }

    @Override
    public void set(@NotNull OfflinePlayer player, @NotNull String economyId, @NotNull BigDecimal amount) {
        api.setBalance(getUser(player), getCurrency(economyId), amount);
    }

    @Override
    public void add(@NotNull OfflinePlayer player, @NotNull String economyId, @NotNull BigDecimal amount) {
        api.addBalance(getUser(player), getCurrency(economyId), amount);
    }

    @Override
    public void remove(@NotNull OfflinePlayer player, @NotNull String economyId, @NotNull BigDecimal amount) {
        api.removeBalance(getUser(player), getCurrency(economyId), amount);
    }

    @Override
    public boolean has(@NotNull OfflinePlayer player, @NotNull String economyId, @NotNull BigDecimal amount) {
        return api.hasBalance(getUser(player), getCurrency(economyId), amount);
    }

}
