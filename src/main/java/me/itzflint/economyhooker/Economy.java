package me.itzflint.economyhooker;

import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public interface Economy {

    @NotNull BigDecimal get(@NotNull OfflinePlayer player);
    @NotNull BigDecimal get(@NotNull UUID playerId);

    void set(@NotNull OfflinePlayer player, @NotNull BigDecimal amount);
    void set(@NotNull UUID playerId, @NotNull BigDecimal amount);

    void add(@NotNull OfflinePlayer player, @NotNull BigDecimal amount);
    void add(@NotNull UUID playerId, @NotNull BigDecimal amount);

    void remove(@NotNull OfflinePlayer player, @NotNull BigDecimal amount);
    void remove(@NotNull UUID playerId, @NotNull BigDecimal amount);

    boolean has(@NotNull OfflinePlayer player, @NotNull BigDecimal amount);
    boolean has(@NotNull UUID playerId, @NotNull BigDecimal amount);
    
}
