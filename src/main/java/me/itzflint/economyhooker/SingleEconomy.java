package me.itzflint.economyhooker;

import me.itzflint.economyhooker.provider.EconomyProvider;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public final class SingleEconomy implements Economy {

    private final EconomyProvider provider;

    public SingleEconomy(EconomyProvider provider) {
        this.provider = provider;
    }

    @Override
    public @NotNull BigDecimal get(@NotNull OfflinePlayer player) {
        return provider.get(player);
    }

    @Override
    public @NotNull BigDecimal get(@NotNull UUID playerId) {
        return provider.get(playerId);
    }

    @Override
    public void set(@NotNull OfflinePlayer player, @NotNull BigDecimal amount) {
        provider.set(player, amount);
    }

    @Override
    public void set(@NotNull UUID playerId, @NotNull BigDecimal amount) {
        provider.set(playerId, amount);
    }

    @Override
    public void add(@NotNull OfflinePlayer player, @NotNull BigDecimal amount) {
        provider.add(player, amount);
    }

    @Override
    public void add(@NotNull UUID playerId, @NotNull BigDecimal amount) {
        provider.add(playerId, amount);
    }

    @Override
    public void remove(@NotNull OfflinePlayer player, @NotNull BigDecimal amount) {
        provider.remove(player, amount);
    }

    @Override
    public void remove(@NotNull UUID playerId, @NotNull BigDecimal amount) {
        provider.remove(playerId, amount);
    }

    @Override
    public boolean has(@NotNull OfflinePlayer player, @NotNull BigDecimal amount) {
        return provider.has(player, amount);
    }

    @Override
    public boolean has(@NotNull UUID playerId, @NotNull BigDecimal amount) {
        return provider.has(playerId, amount);
    }

}
