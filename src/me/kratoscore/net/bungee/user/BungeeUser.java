package me.kratoscore.net.bungee.user;

import me.kratoscore.net.api.MySQL;
import me.kratoscore.net.api.MySQL.WhereType;
import me.kratoscore.net.bungee.KratosCore;
import me.kratoscore.net.bungee.utils.ActionBarUtil;
import me.kratoscore.net.bungee.utils.TitleUtil;
import me.kratoscore.net.bungee.utils.Utils;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;

public class BungeeUser {

    ProxiedPlayer p;
    BungeeRank rank;
    Boolean socialspy,localspy;

    public BungeeUser(ProxiedPlayer p){
        this.p = p;
        if(KratosCore.getInstance().staff.contains(p.getName().toLowerCase())){
            this.rank = BungeeRank.STAFF;
        } else {
            this.rank = BungeeRank.GUEST;
        }
        if(this.getRank().equals(BungeeRank.STAFF)){
            this.socialspy = true;
        } else {
            this.socialspy = false;
        }
        this.localspy = false;
        p.setPermission("", true);
    }

    public CommandSender sender(){
        return (CommandSender)p;
    }

    public Server getServer(){
        return p.getServer();
    }

    public String getName(){
        return p.getName();
    }

    public void connect(ServerInfo server){
        p.connect(server);
    }

    public Boolean hasPermission(String perm){
        return p.hasPermission(perm);
    }

    public void sendMessage(String message){
        this.getPlayer().sendMessage(Utils.format(message));
    }

    public void sendBar(String message){
        ActionBarUtil.sendActionBar(p, message);
    }

    public void sendTitle(Integer in, Integer stay, Integer out, String title, String subtitle){
        TitleUtil.sendFullTitle(p, in, stay, out, Utils.format(title), Utils.format(subtitle));
    }

    public ProxiedPlayer getPlayer(){
        return p;
    }

    public void setPlayer(ProxiedPlayer p){
        this.p = p;
    }

    public Boolean isSocialSpy(){
        return socialspy;
    }

    public void setSocialSpy(Boolean b){
        this.socialspy = b;
    }

    public Boolean isLocalSpy(){
        return localspy;
    }

    public void setLocalSpy(Boolean b){
        this.localspy = b;
    }

    public Boolean isStaff(){
        return rank.equals(BungeeRank.STAFF);
    }

    public static void setRank(final String name, final BungeeRank rank){
        Runnable r = new Runnable(){

            @Override
            public void run() {
                try {
                    if(rank.equals(BungeeRank.STAFF)){
                        if(!MySQL.getInstance().select().table("Staffs").column("Name").wheretype(WhereType.EQUALS).where("Name").wherereq(name.toLowerCase()).select().next()){
                            MySQL.getInstance().insert().single().table("Staffs").column("Name").value(name.toLowerCase()).insert();
                        }
                    } else {
                        if(MySQL.getInstance().select().table("Staffs").column("Name").wheretype(WhereType.EQUALS).where("Name").wherereq(name.toLowerCase()).select().next()){
                            MySQL.getInstance().delete().table("Staffs").where("Name").equals(name.toLowerCase()).delete();
                        }
                    }
                } catch(Exception e){
                    e.printStackTrace();
                }
            }

        };
        BungeeCord.getInstance().getScheduler().runAsync(KratosCore.getInstance(), r);
    }

    public BungeeRank getRank(){
        return rank;
    }
}