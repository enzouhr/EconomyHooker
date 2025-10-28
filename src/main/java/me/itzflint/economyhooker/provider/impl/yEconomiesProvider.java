package me.itzflint.economyhooker.provider.impl;

import br.com.ystoreplugins.product.yeconomias.EconomiasAPIHolder;
import br.com.ystoreplugins.product.yeconomias.internal.UserHolder;
import me.itzflint.economyhooker.provider.MultiEconomyProvider;
import me.itzflint.economyhooker.provider.ProviderValidator;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

public final class yEconomiesProvider implements MultiEconomyProvider {

    private final EconomiasAPIHolder api;

    public yEconomiesProvider() {
        this.api = ProviderValidator.ofService("br.com.ystoreplugins.product.yeconomias.EconomiasAPIHolder");
    }

    public @NotNull CompletableFuture<UserHolder> getUser(@NotNull OfflinePlayer player) {
        return api.getAccount(player.getName());
    }

    private @NotNull UserHolder getUserSync(@NotNull OfflinePlayer player) {
        if (Bukkit.isPrimaryThread()) throw new IllegalStateException("Cannot call yEconomies synchronously on main thread!");
        try {
            return getUser(player).join();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get user from yEconomies", e);
        }
    }

    @Override
    public @NotNull String getProviderId() {
        return "yEconomias";
    }

    @Override
    public boolean isEnabled() {
        return api != null;
    }

    @Override
    public @NotNull BigDecimal get(@NotNull OfflinePlayer player, @NotNull String economyId) {
        return BigDecimal.valueOf(api.getBalance(getUserSync(player), economyId));
    }

    @Override
    public void set(@NotNull OfflinePlayer player, @NotNull String economyId, @NotNull BigDecimal amount) {
        getUser(player).thenAccept(userHolder -> {
            api.setBalance(userHolder, economyId, amount.doubleValue());
        });
    }

    @Override
    public void add(@NotNull OfflinePlayer player, @NotNull String economyId, @NotNull BigDecimal amount) {
        getUser(player).thenAccept(userHolder -> {
            api.addBalance(userHolder, economyId, amount.doubleValue());
        });
    }

    @Override
    public void remove(@NotNull OfflinePlayer player, @NotNull String economyId, @NotNull BigDecimal amount) {
        getUser(player).thenAccept(userHolder -> {
            api.removeBalance(userHolder, economyId, amount.doubleValue());
        });
    }

    @Override
    public boolean has(@NotNull OfflinePlayer player, @NotNull String economyId, @NotNull BigDecimal amount) {
        BigDecimal balance = get(player, economyId);
        return balance.compareTo(amount) >= 0;
    }

}
