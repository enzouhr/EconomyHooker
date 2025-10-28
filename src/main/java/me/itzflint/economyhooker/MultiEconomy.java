package me.itzflint.economyhooker;

import me.itzflint.economyhooker.provider.MultiEconomyProvider;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public final class MultiEconomy implements Economy {

    private final String economyId;
    private final MultiEconomyProvider provider;

    public MultiEconomy(String economyId, MultiEconomyProvider provider) {
        this.economyId = economyId;
        this.provider = provider;
    }

    @Override
    public @NotNull BigDecimal get(@NotNull OfflinePlayer player) {
        return provider.get(player, economyId);
    }

    @Override
    public @NotNull BigDecimal get(@NotNull UUID playerId) {
        return provider.get(playerId, economyId);
    }

    @Override
    public void set(@NotNull OfflinePlayer player, @NotNull BigDecimal amount) {
        provider.set(player, economyId, amount);
    }

    @Override
    public void set(@NotNull UUID playerId, @NotNull BigDecimal amount) {
        provider.set(playerId, economyId, amount);
    }

    @Override
    public void add(@NotNull OfflinePlayer player, @NotNull BigDecimal amount) {
        provider.add(player, economyId, amount);
    }

    @Override
    public void add(@NotNull UUID playerId, @NotNull BigDecimal amount) {
        provider.add(playerId, economyId, amount);
    }

    @Override
    public void remove(@NotNull OfflinePlayer player, @NotNull BigDecimal amount) {
        provider.remove(player, economyId, amount);
    }

    @Override
    public void remove(@NotNull UUID playerId, @NotNull BigDecimal amount) {
        provider.remove(playerId, economyId, amount);
    }

    @Override
    public boolean has(@NotNull OfflinePlayer player, @NotNull BigDecimal amount) {
        return provider.has(player, economyId, amount);
    }

    @Override
    public boolean has(@NotNull UUID playerId, @NotNull BigDecimal amount) {
        return provider.has(playerId, economyId, amount);
    }
    
}
