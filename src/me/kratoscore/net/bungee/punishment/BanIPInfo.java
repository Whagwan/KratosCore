package me.kratoscore.net.bungee.punishment;

public class BanIPInfo {

    private String ip;
    private String bannedby;
    private String reason;

    public BanIPInfo(String ip, String bannedby, String reason) {
        this.ip = ip;
        this.bannedby = bannedby;
        this.reason = reason;
    }

    public String getIP() {
        return this.ip;
    }

    public String getBy() {
        return this.bannedby;
    }

    public String getReason() {
        return this.reason;
    }

    public String seIP(String ip) {
        return this.ip = ip;
    }

    public String setBy(String by) {
        return this.bannedby = by;
    }

    public String setReason(String reason) {
        return this.reason = reason;
    }
}