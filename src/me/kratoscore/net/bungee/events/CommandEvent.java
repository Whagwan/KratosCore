package me.kratoscore.net.bungee.events;

import me.kratoscore.net.bungee.user.BungeeUser;

import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Event;

public class CommandEvent extends Event implements Cancellable {

    private BungeeUser player;
    private String command;
    private Boolean cancelled = false;

    public CommandEvent(BungeeUser player, String command) {
        this.player = player;
        this.command = command;
    }

    public BungeeUser getPlayer() {
        return this.player;
    }

    public String getCommand() {
        return this.command;
    }

    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    public boolean isCancelled() {
        return cancelled;
    }
}
