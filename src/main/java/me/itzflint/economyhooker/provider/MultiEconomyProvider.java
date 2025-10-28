package me.itzflint.economyhooker.provider;

import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

import org.bukkit.Bukkit;

public interface MultiEconomyProvider extends EconomyProvider {

    @Override
    default boolean isMultiCurrency() {
        return true;
    }

    // Incompatible methods
    @Override
    default @NotNull BigDecimal get(@NotNull OfflinePlayer player) {
        throw new IllegalStateException("Cannot get with no specified economy id");
    }

    @Override
    default void set(@NotNull OfflinePlayer player, @NotNull BigDecimal amount) {
        throw new IllegalStateException("Cannot set with no specified economy id");
    }

    @Override
    default void add(@NotNull OfflinePlayer player, @NotNull BigDecimal amount) {
        throw new IllegalStateException("Cannot add with no specified economy id");
    }

    @Override
    default void remove(@NotNull OfflinePlayer player, @NotNull BigDecimal amount) {
        throw new IllegalStateException("Cannot remove with no specified economy id");
    }

    @Override
    default boolean has(@NotNull OfflinePlayer player, @NotNull BigDecimal amount) {
        throw new IllegalStateException("Cannot verify with no specified economy id");
    }

    // Multi-Economy methods
    @NotNull BigDecimal get(@NotNull OfflinePlayer player, @NotNull String economyId);

    default @NotNull BigDecimal get(@NotNull UUID playerId, @NotNull String economyId) {
        return get(Bukkit.getOfflinePlayer(playerId), economyId);
    }

    void set(@NotNull OfflinePlayer player, @NotNull String economyId, @NotNull BigDecimal amount);

    default void set(@NotNull UUID playerId, @NotNull String economyId, @NotNull BigDecimal amount) {
        set(Bukkit.getOfflinePlayer(playerId), economyId, amount);
    }

    void add(@NotNull OfflinePlayer player, @NotNull String economyId, @NotNull BigDecimal amount);

    default void add(@NotNull UUID playerId, @NotNull String economyId, @NotNull BigDecimal amount) {
        add(Bukkit.getOfflinePlayer(playerId), economyId, amount);
    }

    void remove(@NotNull OfflinePlayer player, @NotNull String economyId, @NotNull BigDecimal amount);

    default void remove(@NotNull UUID playerId, @NotNull String economyId, @NotNull BigDecimal amount) {
        remove(Bukkit.getOfflinePlayer(playerId), economyId, amount);
    }

    boolean has(@NotNull OfflinePlayer player, @NotNull String economyId, @NotNull BigDecimal amount);

    default boolean has(@NotNull UUID playerId, @NotNull String economyId, @NotNull BigDecimal amount) {
        return has(Bukkit.getOfflinePlayer(playerId), economyId, amount);
    }

}

