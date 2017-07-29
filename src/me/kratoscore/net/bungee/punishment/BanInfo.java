package me.kratoscore.net.bungee.punishment;

public class BanInfo {

    private String player;
    private String bannedby;
    private Long bantime;
    private String reason;

    public BanInfo(String player, String bannedby, Long bantime, String reason) {
        this.player = player;
        this.bannedby = bannedby;
        this.bantime = bantime;
        this.reason = reason;
    }

    public String getPlayer() {
        return this.player;
    }

    public String getBy() {
        return this.bannedby;
    }

    public Long getTime() {
        return this.bantime;
    }

    public String getReason() {
        return this.reason;
    }

    public String setPlayer(String player) {
        return this.player = player;
    }

    public String setBy(String by) {
        return this.bannedby = by;
    }

    public Long setTime(Long time) {
        return this.bantime = time;
    }

    public String setReason(String reason) {
        return this.reason = reason;
    }
}