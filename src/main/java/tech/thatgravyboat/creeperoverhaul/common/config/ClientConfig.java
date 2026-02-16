package tech.thatgravyboat.creeperoverhaul.common.config;

import com.teamresourceful.resourcefulconfig.api.annotations.Comment;
import com.teamresourceful.resourcefulconfig.api.annotations.Config;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigInfo;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigOption;
import com.teamresourceful.resourcefulconfig.api.types.entries.Observable;

@Config("crepperoverhaul-client")
@ConfigInfo(
    icon = "creeper",
    title = "Creeper Overhaul Client",
    description = "Client side configuration for Creeper Overhaul"
)
public final class ClientConfig {

    @ConfigEntry(id = "replaceDefaultCreeper", translation = "Replace Default Creeper")
    @Comment("""
    Change the Vanilla Creeper to a new and improved texture with better animations.
    §cNote: Restart required to see changes.
    """)
    public static boolean replaceDefaultCreeper = true;

    @ConfigOption.Separator(
            value = "Cosmetics",
            description = """
            Settings for players with cosmetics.
            §eNote: Not all players have access to cosmetics.
            """
    )
    @ConfigEntry(id = "showCosmetic", translation = "Show Cosmetic")
    @Comment("Shows your cosmetic on your player for others.")
    public static Observable<Boolean> showCosmetic = Observable.of(true);
}
