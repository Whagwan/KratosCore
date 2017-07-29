package me.kratoscore.net.bungee.events;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Event;

public class BanEvent extends Event implements Cancellable {

    private String banner;
    private String banned, reason;
    private Long expire;
    private Boolean cancelled = false;

    public BanEvent(String banner, String banned, Long expire, String reason) {
        this.banner = banner;
        this.banned = banned;
        this.expire = expire;
        this.reason = reason;
    }

    public String getBanner() {
        return this.banner;
    }

    public String getBanned() {
        return this.banned;
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
