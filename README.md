# EconomyHooker

[![](https://jitpack.io/v/enzouhr/EconomyHooker.svg)](https://jitpack.io/#enzouhr/EconomyHooker)
[![](https://img.shields.io/discord/390919659874156560.svg?colorB=5865f2&label=Discord&logo=discord&logoColor=white)](https://discord.gg/PH94WbJ6ug)

**EconomyHooker** é uma biblioteca que facilita a integração com múltiplos plugins de economia

## Features

* Suporte a plugins multi-economias (ex: yEconomias e MiyukiEconomy)

## Instalação

### Utilizando Maven:
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.enzouhr</groupId>
        <artifactId>EconomyHooker</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

### Utilizando Gradle:

```groovy
plugins {
    id 'com.gradleup.shadow' version '8.3.0'
}

repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.enzouhr:EconomyHooker:1.0.0'
}

shadowJar {
    relocate 'me.itzflint.economyhooker', 'com.yourpackage.economyhooker'
}
```

## Exemplos:

### Integração com plugins com suporte:

1. Plugin de Economia Única
```java
EconomyManager economyManager = new EconomyManager();

economyManager.getEconomyById("vault").ifPresent(economy -> {
  BigDecimal balance = economy.get(player); 
  economy.set(player, new BigDecimal(100));
  economy.add(player, new BigDecimal(200))
  economy.remove(player, new BigDecimal(50))
  boolean hasBalance = economy.has(player, new BigDecimal(100))
});
```

2. Plugin de Multi-Economias
```java
EconomyManager economyManager = new EconomyManager();

economyManager.getEconomyById("MiyukiEconomy-cash").ifPresent(economy -> {
  BigDecimal balance = economy.get(player); 
  economy.set(player, new BigDecimal(100));
  economy.add(player, new BigDecimal(200))
  economy.remove(player, new BigDecimal(50))
  boolean hasBalance = economy.has(player, new BigDecimal(100))
});
```

### Como adicionar suporte a plugins externos:
* Plugin de Economia Única
```java
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
```

* Plugin de Multi-Economias
```java
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
```

Após fazer sua classe de Provider, registre ela no EconomyManager

```java
EconomyManager economyManager = new EconomyManager()
EconomyProvider exampleProvider = new ExampleProvider()
economyManager.registerProvider(exampleProvider)
```

### Desenvolvido com ☕ por [Enzo Uehara](https://github.com/enzouhr)
