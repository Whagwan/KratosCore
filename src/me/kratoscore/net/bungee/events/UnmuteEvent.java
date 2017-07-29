package me.kratoscore.net.bungee.events;

import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Event;

public class UnmuteEvent extends Event implements Cancellable {

    private String unmuter, unmuted;
    private Boolean cancelled = false;

    public UnmuteEvent(String unmuter, String unmuted) {
        this.unmuter = unmuter;
        this.unmuted = unmuted;
    }

    public String getUnmuter() {
        return this.unmuter;
    }

    public String getUnmuted() {
        return this.unmuted;
    }

    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    public boolean isCancelled() {
        return cancelled;
    }
}