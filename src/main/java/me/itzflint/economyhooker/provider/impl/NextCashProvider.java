package me.itzflint.economyhooker.provider.impl;

import me.itzflint.economyhooker.provider.EconomyProvider;
import me.itzflint.economyhooker.provider.ProviderValidator;
import com.nextplugins.cash.NextCash;
import com.nextplugins.cash.api.NextCashAPI;
import com.nextplugins.cash.api.model.account.Account;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Optional;

public final class NextCashProvider implements EconomyProvider {

    private final NextCashAPI api;

    public NextCashProvider() {
        NextCash plugin = ProviderValidator.ofPlugin("NextCash", "com.nextplugins.cash.NextCash");
        this.api = plugin != null ? NextCashAPI.getInstance() : null;
    }

    @Override
    public @NotNull String getProviderId() {
        return "NextCash";
    }

    @Override
    public boolean isEnabled() {
        return api != null;
    }

    private @NotNull Optional<Account> getUser(@NotNull OfflinePlayer player) {
        if (!player.isOnline()) return Optional.empty();
        return api.findAccountByPlayer(player.getPlayer());
    }

    @Override
    public @NotNull BigDecimal get(@NotNull OfflinePlayer player) {
        return getUser(player).map(value -> {
            return BigDecimal.valueOf(value.getBalance());
        }).orElse(BigDecimal.ZERO);
    }

    @Override
    public void set(@NotNull OfflinePlayer player, @NotNull BigDecimal amount) {
        getUser(player).ifPresent(account -> {
            account.setBalance(amount.doubleValue());
        });
    }

    @Override
    public void add(@NotNull OfflinePlayer player, @NotNull BigDecimal amount) {
        getUser(player).ifPresent(account -> {
            account.depositAmount(amount.doubleValue());
        });
    }

    @Override
    public void remove(@NotNull OfflinePlayer player, @NotNull BigDecimal amount) {
        getUser(player).ifPresent(account -> {
            account.withdrawAmount(amount.doubleValue());
        });
    }

    @Override
    public boolean has(@NotNull OfflinePlayer player, @NotNull BigDecimal amount) {
        return getUser(player).map(account -> {
            return account.hasAmount(amount.doubleValue());
        }).orElse(false);
    }

}
