package tech.thatgravyboat.creeperoverhaul.api;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;

public final class PluginRegistry {

    private static final PluginRegistry INSTANCE = new PluginRegistry();

    private final Map<Identifier, CreeperPlugin> registeredPlugins = new HashMap<>();
    private Collection<CreeperPlugin> plugins = registeredPlugins.values();

    private PluginRegistry() {
    }

    public static PluginRegistry getInstance() {
        return INSTANCE;
    }

    public void registerPlugins(Collection<CreeperPlugin> plugins) {
        registeredPlugins.clear();
        plugins.forEach(this::registerPlugin);
    }

    public void registerPlugin(CreeperPlugin plugin) {
        if (registeredPlugins.containsKey(plugin.id())) {
            throw new IllegalArgumentException("Plugin with id " + plugin.id() + " already exists!");
        }
        registeredPlugins.put(plugin.id(), plugin);
        plugins = registeredPlugins.values();
    }

    public CreeperPlugin getPlugin(Identifier id) {
        return registeredPlugins.get(id);
    }

    public boolean canAttack(BaseCreeper creeper, LivingEntity entity) {
        for (CreeperPlugin plugin : plugins) {
            if (!plugin.canAttack(entity)) {
                return false;
            }
        }
        return true;
    }

    public boolean isAfraidOf(BaseCreeper creeper, LivingEntity entity) {
        for (CreeperPlugin plugin : plugins) {
            if (plugin.isAfraidOf(creeper, entity)) {
                return true;
            }
        }
        return false;
    }
}
