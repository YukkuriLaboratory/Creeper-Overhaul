package tech.thatgravyboat.creeperoverhaul;

import java.util.function.Supplier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

public class ModItemsImpl {

    public static <E extends Mob, T extends EntityType<E>>  SpawnEggItem createSpawnEgg(Supplier<T> entity, int primaryColor, int secondaryColor, Item.Properties properties) {
        return new SpawnEggItem(entity.get(), primaryColor, secondaryColor, properties);
    }
}
