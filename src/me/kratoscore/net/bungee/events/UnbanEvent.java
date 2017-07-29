package me.kratoscore.net.bungee.events;

import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Event;

public class UnbanEvent extends Event implements Cancellable {

    private String unbanner, unbanned;
    private Boolean cancelled = false;

    public UnbanEvent(String unbanner, String unbanned) {
        this.unbanner = unbanner;
        this.unbanned = unbanned;
    }

    public String getUnbanner() {
        return this.unbanner;
    }

    public String getUnbanned() {
        return this.unbanned;
    }

    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    public boolean isCancelled() {
        return cancelled;
    }
}
