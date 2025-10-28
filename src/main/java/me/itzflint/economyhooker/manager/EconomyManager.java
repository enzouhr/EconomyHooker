package me.itzflint.economyhooker.manager;

import me.itzflint.economyhooker.Economy;
import me.itzflint.economyhooker.MultiEconomy;
import me.itzflint.economyhooker.SingleEconomy;
import me.itzflint.economyhooker.exception.MultiCurrencyException;
import me.itzflint.economyhooker.provider.EconomyProvider;
import me.itzflint.economyhooker.provider.MultiEconomyProvider;
import me.itzflint.economyhooker.provider.impl.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public final class EconomyManager {

    private final List<EconomyProvider> providers = new ArrayList<>();

    public EconomyManager() {
        List<EconomyProvider> DEFAULT_PROVIDERS = Arrays.asList(
                new VaultProvider(),
                new MiyukiEconomyProvider(),
                new yEconomiesProvider(),
                new yPointsProvider(),
                new NextCashProvider(),
                new PlayerPointsProvider()
        );
        DEFAULT_PROVIDERS.stream()
                .filter(EconomyProvider::isEnabled)
                .forEach(this::registerProvider);
    }

    public @NotNull List<EconomyProvider> getRegisteredEconomies() {
        return Collections.unmodifiableList(providers);
    }

    public void registerProvider(@NotNull EconomyProvider provider) {
        if (!provider.isEnabled()) return;
        if (providerExists(provider)) throw new IllegalStateException("Already exists provider with id '" + provider.getProviderId() + "'");
        providers.add(provider);
    }

    public @Nullable EconomyProvider getProviderById(@NotNull String provider) {
        String providerId = extractProviderId(provider);
        return getRegisteredEconomies().stream()
                .filter(economyProvider -> economyProvider.getProviderId().equalsIgnoreCase(providerId))
                .findFirst()
                .orElse(null);
    }

    public @NotNull Optional<Economy> getEconomyById(@NotNull String providerId) {
        EconomyProvider provider = getProviderById(providerId);
        if (provider == null) return Optional.empty();
        if (provider instanceof MultiEconomyProvider) {
            String economyId = extractEconomyId(providerId);
            if (economyId == null) {
                throw new MultiCurrencyException("Economy Id not specified for provider: " + provider.getProviderId());
            }
            return Optional.of(new MultiEconomy(economyId, (MultiEconomyProvider) provider));
        }
        return Optional.of(new SingleEconomy(provider));
    }

    private boolean providerExists(@NotNull EconomyProvider provider) {
        return providers.stream()
                .anyMatch(e -> e.getProviderId().equals(provider.getProviderId()));
    }

    private @NotNull String extractProviderId(@NotNull String rawId) {
        return rawId.contains("-") ? rawId.split("-")[0].toLowerCase() : rawId.toLowerCase();
    }

    private @Nullable String extractEconomyId(@NotNull String rawId) {
        return rawId.contains("-") ? rawId.split("-")[1].toLowerCase() : null;
    }

}
