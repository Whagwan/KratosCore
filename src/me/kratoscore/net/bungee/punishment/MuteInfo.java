package me.kratoscore.net.bungee.punishment;

public class MuteInfo {

    private String player;
    private String bannedby;
    private Long mutetime;
    private String reason;

    public MuteInfo(String player, String bannedby, Long mutetime, String reason) {
        this.player = player;
        this.bannedby = bannedby;
        this.mutetime = mutetime;
        this.reason = reason;
    }

    public String getPlayer() {
        return this.player;
    }

    public String getBy() {
        return this.bannedby;
    }

    public Long getTime() {
        return this.mutetime;
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
        return this.mutetime = time;
    }

    public String setReason(String reason) {
        return this.reason = reason;
    }
}
