package me.itzflint.economyhooker.provider;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unchecked")
public final class ProviderValidator {

    public static <T> @Nullable T ofService(@NotNull String classPackage) {
        try {
            Class<?> clazz = Class.forName(classPackage);
            RegisteredServiceProvider<T> rsp =
                    (RegisteredServiceProvider<T>) Bukkit.getServicesManager().getRegistration(clazz);
            return rsp != null ? rsp.getProvider() : null;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static <T extends JavaPlugin> @Nullable T ofPlugin(@NotNull String pluginName, @NotNull String mainClassName) {
        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
        if (plugin == null || !plugin.isEnabled()) return null;
        try {
            Class<?> clazz = Class.forName(mainClassName, false, plugin.getClass().getClassLoader());
            if (!JavaPlugin.class.isAssignableFrom(clazz)) return null;
            return (T) plugin;
        } catch (ClassNotFoundException ignored) {
            return null;
        }
    }

}
