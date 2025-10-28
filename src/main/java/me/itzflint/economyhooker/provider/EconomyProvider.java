package me.itzflint.economyhooker.provider;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public interface EconomyProvider {

    @NotNull String getProviderId();

    default boolean isMultiCurrency() {
        return false;
    }

    boolean isEnabled();

    @NotNull BigDecimal get(@NotNull OfflinePlayer player);

    default @NotNull BigDecimal get(@NotNull UUID playerId) {
        return get(Bukkit.getOfflinePlayer(playerId));
    }

    void set(@NotNull OfflinePlayer player, @NotNull BigDecimal amount);

    default void set(@NotNull UUID playerId, @NotNull BigDecimal amount) {
        set(Bukkit.getOfflinePlayer(playerId), amount);
    }

    void add(@NotNull OfflinePlayer player, @NotNull BigDecimal amount);

    default void add(@NotNull UUID playerId, @NotNull BigDecimal amount) {
        add(Bukkit.getOfflinePlayer(playerId), amount);
    }

    void remove(@NotNull OfflinePlayer player, @NotNull BigDecimal amount);

    default void remove(@NotNull UUID playerId, @NotNull BigDecimal amount) {
        remove(Bukkit.getOfflinePlayer(playerId), amount);
    }

    boolean has(@NotNull OfflinePlayer player, @NotNull BigDecimal amount);

    default boolean has(@NotNull UUID playerId, @NotNull BigDecimal amount) {
        return has(Bukkit.getOfflinePlayer(playerId), amount);
    }

}
