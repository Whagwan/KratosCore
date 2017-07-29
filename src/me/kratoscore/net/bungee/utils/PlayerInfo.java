package me.kratoscore.net.bungee.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import me.kratoscore.net.bungee.KratosCore;
import me.kratoscore.net.bungee.managers.DatabaseManager;
import net.md_5.bungee.api.ProxyServer;

public class PlayerInfo {

    DatabaseManager dbmanager = KratosCore.getInstance().getDatabaseManager();
    String player;

    public PlayerInfo(String player){
        this.player = player;
    }

    public boolean isInTable(){
        try {
            PreparedStatement preparedStatement = dbmanager.getConnection().prepareStatement("SELECT `IP` FROM `PlayerInfo` WHERE `Player` = ?;");
            preparedStatement.setString(1, this.player);

            ResultSet result = preparedStatement.executeQuery();
            return result.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void putPlayerInTable(final String ip, final int bans, final int warns, final int mutes, final int kicks){
        ProxyServer.getInstance().getScheduler().runAsync(KratosCore.getInstance(), new Runnable(){

            public void run(){
                if(isInTable()){
                    return;
                }
                try {
                    PreparedStatement preparedStatement = dbmanager.getConnection().prepareStatement("INSERT INTO PlayerInfo(`Player`, `IP`, `Bans`, `Warns`, `Mutes`, `Kicks`) VALUES (?, ?, ?, ?, ?, ?);");
                    preparedStatement.setString(1, player);
                    preparedStatement.setString(2, ip);
                    preparedStatement.setInt(3, bans);
                    preparedStatement.setInt(4, warns);
                    preparedStatement.setInt(5, mutes);
                    preparedStatement.setInt(6, kicks);

                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        });

    }

    public void removePlayer(){
        ProxyServer.getInstance().getScheduler().runAsync(KratosCore.getInstance(), new Runnable(){

            public void run(){
                try {
                    PreparedStatement preparedStatement = dbmanager.getConnection().prepareStatement("DELETE FROM `PlayerInfo` WHERE `Player` = ?;");
                    preparedStatement.setString(1, player);

                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    public String getIP(){
        String ip = null;
        if(this.player.contains(".")){
            return this.player;
        }
        try {
            PreparedStatement preparedStatement = dbmanager.getConnection().prepareStatement("SELECT `IP` FROM `PlayerInfo` WHERE `Player` = ?;");
            preparedStatement.setString(1, player);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                ip = rs.getString("IP");
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return ip;
    }

    public Integer getBans(){
        int bans = 0;
        try {
            PreparedStatement preparedStatement = dbmanager.getConnection().prepareStatement("SELECT `Bans` FROM `PlayerInfo` WHERE `Player` = ?;");
            preparedStatement.setString(1, player);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                bans = rs.getInt("Bans");
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return bans;
    }

    public void setBans(final int bans){
        ProxyServer.getInstance().getScheduler().runAsync(KratosCore.getInstance(), new Runnable(){

            public void run(){
                try {
                    PreparedStatement preparedStatement = dbmanager.getConnection().prepareStatement("UPDATE `PlayerInfo` SET `Bans` = ? WHERE `Player` = ?;");
                    preparedStatement.setInt(1, bans);
                    preparedStatement.setString(2, player);

                    preparedStatement.executeUpdate();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }

        });
    }

    public void addBan(){
        this.setBans(this.getBans() + 1);
    }

    public Integer getMutes(){
        int mutes = 0;
        try {
            PreparedStatement preparedStatement = dbmanager.getConnection().prepareStatement("SELECT `Mutes` FROM `PlayerInfo` WHERE `Player` = ?;");
            preparedStatement.setString(1, player);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                mutes = rs.getInt("Mutes");
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return mutes;
    }

    public void setMutes(final int mutes){
        ProxyServer.getInstance().getScheduler().runAsync(KratosCore.getInstance(), new Runnable(){

            public void run(){
                try {
                    PreparedStatement preparedStatement = dbmanager.getConnection().prepareStatement("UPDATE `PlayerInfo` SET `Mutes` = ? WHERE `Player` = ?;");
                    preparedStatement.setInt(1, mutes);
                    preparedStatement.setString(2, player);

                    preparedStatement.executeUpdate();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }

        });
    }

    public void addMute(){
        this.setMutes(this.getMutes() + 1);
    }

    public Integer getWarns(){
        int warns = 0;
        try {
            PreparedStatement preparedStatement = dbmanager.getConnection().prepareStatement("SELECT `Warns` FROM `PlayerInfo` WHERE `Player` = ?;");
            preparedStatement.setString(1, player);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                warns = rs.getInt("Warns");
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return warns;
    }

    public void setWarns(final int warns){
        ProxyServer.getInstance().getScheduler().runAsync(KratosCore.getInstance(), new Runnable(){

            public void run(){
                try {
                    PreparedStatement preparedStatement = dbmanager.getConnection().prepareStatement("UPDATE `PlayerInfo` SET `Warns` = ? WHERE `Player` = ?;");
                    preparedStatement.setInt(1, warns);
                    preparedStatement.setString(2, player);

                    preparedStatement.executeUpdate();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }

        });
    }

    public void addWarn(){
        this.setWarns(this.getWarns() + 1);
    }

    public Integer getKicks(){
        int kicks = 0;
        try {
            PreparedStatement preparedStatement = dbmanager.getConnection().prepareStatement("SELECT `Kicks` FROM `PlayerInfo` WHERE `Player` = ?;");
            preparedStatement.setString(1, player);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                kicks = rs.getInt("Kicks");
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return kicks;
    }

    public void setKicks(final int kicks){
        ProxyServer.getInstance().getScheduler().runAsync(KratosCore.getInstance(), new Runnable(){

            public void run(){
                try {
                    PreparedStatement preparedStatement = dbmanager.getConnection().prepareStatement("UPDATE `PlayerInfo` SET `Kicks` = ? WHERE `Player` = ?;");
                    preparedStatement.setInt(1, kicks);
                    preparedStatement.setString(2, player);

                    preparedStatement.executeUpdate();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }

        });
    }

    public void addKick(){
        this.setKicks(this.getKicks() + 1);
    }
}
