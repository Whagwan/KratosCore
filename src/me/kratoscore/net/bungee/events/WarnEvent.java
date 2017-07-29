package me.kratoscore.net.bungee.events;

import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Event;

public class WarnEvent extends Event implements Cancellable {

    private String warner, warned;
    private String reason;
    private Boolean cancelled = false;

    public WarnEvent(String warner, String warned, String reason) {
        this.warned = warned;
        this.warner = warner;
        this.reason = reason;
    }

    public String getWarner() {
        return this.warner;
    }

    public String getWarned() {
        return this.warned;
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
