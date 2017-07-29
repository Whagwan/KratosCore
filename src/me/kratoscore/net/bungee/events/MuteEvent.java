package me.kratoscore.net.bungee.events;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Event;

public class MuteEvent extends Event implements Cancellable {

    private String muter;
    private String muted, reason;
    private Long expire;
    private Boolean cancelled = false;

    public MuteEvent(String muter, String muted, Long expire, String reason) {
        this.muter = muter;
        this.muted = muted;
        this.expire = expire;
        this.reason = reason;
    }

    public String getMuter() {
        return this.muter;
    }

    public String getMuted() {
        return this.muted;
    }

    public Long getExpire() {
        return expire;
    }

    public String getDate() {
        if (expire == -1L) {
            return "Never";
        }
        SimpleDateFormat df2 = new SimpleDateFormat("kk:mm dd/MM/yyyy");
        return df2.format(new Date(expire));

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