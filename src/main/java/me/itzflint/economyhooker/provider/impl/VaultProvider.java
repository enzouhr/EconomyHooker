package me.itzflint.economyhooker.provider.impl;

import me.itzflint.economyhooker.provider.EconomyProvider;
import me.itzflint.economyhooker.provider.ProviderValidator;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public final class VaultProvider implements EconomyProvider {

    private final Economy vaultEconomy;

    public VaultProvider() {
        this.vaultEconomy = ProviderValidator.ofService("net.milkbowl.vault.economy.Economy");
    }

    @Override
    public @NotNull String getProviderId() {
        return "Vault";
    }

    @Override
    public boolean isEnabled() {
        return vaultEconomy != null;
    }

    @Override
    public @NotNull BigDecimal get(@NotNull OfflinePlayer player) {
        return BigDecimal.valueOf(vaultEconomy.getBalance(player));
    }

    @Override
    public void set(@NotNull OfflinePlayer player, @NotNull BigDecimal amount) {
        BigDecimal balance = get(player);
        int compare = balance.compareTo(amount);
        if (compare > 0) {
            remove(player, balance.subtract(amount));
        } else if(compare < 0) {
            add(player, amount.subtract(balance));
        }
    }

    @Override
    public void add(@NotNull OfflinePlayer player, @NotNull BigDecimal amount) {
        vaultEconomy.depositPlayer(player, amount.doubleValue());
    }

    @Override
    public void remove(@NotNull OfflinePlayer player, @NotNull BigDecimal amount) {
        vaultEconomy.withdrawPlayer(player, amount.doubleValue());
    }

    @Override
    public boolean has(@NotNull OfflinePlayer player, @NotNull BigDecimal amount) {
        return vaultEconomy.has(player, amount.doubleValue());
    }

}
