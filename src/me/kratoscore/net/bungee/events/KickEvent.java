package me.kratoscore.net.bungee.events;

import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Event;

public class KickEvent extends Event implements Cancellable {

    private String kicker;
    private String kicked, reason;
    private Boolean cancelled = false;

    public KickEvent(String kicker, String kicked, String reason) {
        this.kicked = kicked;
        this.kicker = kicker;
        this.reason = reason;
    }

    public String getKicker() {
        return this.kicker;
    }

    public String getKicked() {
        return this.kicked;
    }

    public String getReason() {
        return this.reason;
    }

    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    public boolean isCancelled() {
        return cancelled;
    }
}

