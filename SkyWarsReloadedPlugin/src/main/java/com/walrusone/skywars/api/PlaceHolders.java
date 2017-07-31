package com.walrusone.skywars.api;

import com.walrusone.skywars.SkyWarsReloaded;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;

public class PlaceHolders extends EZPlaceholderHook {

    private SkyWarsReloaded ourPlugin;

    public PlaceHolders(SkyWarsReloaded ourPlugin) {
        // this is the plugin that is registering the placeholder and the identifier for our placeholder.
        // the format for placeholders is this:
        // %<placeholder identifier>_<anything you define as an identifier in your method below>%
        // the placeholder identifier can be anything you want as long as it is not already taken by another
        // registered placeholder.
        super(ourPlugin, "swr");
        // this is so we can access our main class below
        this.ourPlugin = ourPlugin;
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        // always check if the player is null for placeholders related to the player!
        if (player == null) {
            return "";
        }
        if (identifier.equals("player_wins")) {
            return String.valueOf(SkyWarsReloaded.getPC().getPlayer(player.getUniqueId()).getWins());
        }
        if (identifier.equals("player_balance")) {
            return String.valueOf(SkyWarsReloaded.getPC().getPlayer(player.getUniqueId()).getBalance());
        }
        if (identifier.equals("player_deaths")) {
            return String.valueOf(SkyWarsReloaded.getPC().getPlayer(player.getUniqueId()).getDeaths());
        }
        if (identifier.equals("player_games_played")) {
            return String.valueOf(SkyWarsReloaded.getPC().getPlayer(player.getUniqueId()).getGamesPlayed());
        }
        if (identifier.equals("player_kills")) {
            return String.valueOf(SkyWarsReloaded.getPC().getPlayer(player.getUniqueId()).getKills());
        }
        if (identifier.equals("player_blocks_placed")) {
            return String.valueOf(SkyWarsReloaded.getPC().getPlayer(player.getUniqueId()).getBlocks());
        }
        // anything else someone types is invalid because we never defined %customplaceholder_<what they want a value for>%
        // we can just return null so the placeholder they specified is not replaced.
        return null;
    }
}
