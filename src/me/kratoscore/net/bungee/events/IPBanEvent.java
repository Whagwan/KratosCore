package me.kratoscore.net.bungee.events;

import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Event;

public class IPBanEvent extends Event implements Cancellable {

    private String banner;
    private String banned, reason;
    private Boolean cancelled = false;

    public IPBanEvent(String banner, String banned, String reason) {
        this.banner = banner;
        this.banned = banned;
        this.reason = reason;
    }

    public String getBanner() {
        return this.banner;
    }

    public String getBanned() {
        return this.banned;
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