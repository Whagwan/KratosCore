package me.kratoscore.net.bungee.punishment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import me.kratoscore.net.bungee.KratosCore;
import me.kratoscore.net.bungee.managers.DatabaseManager;

public class BanAPI {

    private static DatabaseManager dbmanager = KratosCore.getInstance().getDatabaseManager();

    public static boolean isIPBanned(String IP) {
        BanIPInfo info = Punishments.getIPBanInfo(IP);
        if (info != null) {
            return true;
        }
        try {
            PreparedStatement preparedStatement = dbmanager.getConnection()
                    .prepareStatement("SELECT `Reason` FROM `IPBans` WHERE `Banned` = ?;");
            preparedStatement.setString(1, IP);

            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            KratosCore.getInstance().getLogger()
                    .info("An error occured while connecting to the database!" + e.getMessage());
        }

        return false;
    }

    public static List<String> getIPBans() {
        List<String> list = new ArrayList<String>();
        try {
            PreparedStatement preparedStatement = dbmanager.getConnection()
                    .prepareStatement("SELECT `Banned` FROM `IPBans`;");

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("Banned"));
            }
        } catch (SQLException e) {
            KratosCore.getInstance().getLogger()
                    .info("An error occured while connecting to the database!" + e.getMessage());
        }
        return list;
    }

    public static void addIPBan(String banned_by, String banned, String reason) {
        try {
            PreparedStatement preparedStatement = dbmanager.getConnection()
                    .prepareStatement("INSERT INTO IPBans(`BannedBy`, `Banned`, `Reason`) VALUES (?, ?, ?);");
            preparedStatement.setString(1, banned_by);
            preparedStatement.setString(2, banned);
            preparedStatement.setString(3, reason);

            preparedStatement.executeUpdate();

            Punishments.ipbans.add(new BanIPInfo(banned, banned_by, reason));
        } catch (SQLException e) {
            KratosCore.getInstance().getLogger()
                    .info("An error occured while connecting to the database!" + e.getMessage());
        }
    }

    public static void removeIPBan(String player) {
        try {
            PreparedStatement preparedStatement = dbmanager.getConnection()
                    .prepareStatement("DELETE FROM `IPBans` WHERE `Banned` = ?;");
            preparedStatement.setString(1, player);

            preparedStatement.executeUpdate();

            BanIPInfo info = Punishments.getIPBanInfo(player);
            if (info != null) {
                Punishments.ipbans.remove(info);
            }
        } catch (SQLException e) {
            KratosCore.getInstance().getLogger()
                    .info("An error occured while connecting to the database!" + e.getMessage());
        }
    }

    public static String getIPBannedBy(String player) {
        BanIPInfo info = Punishments.getIPBanInfo(player);
        if (info != null) {
            return info.getBy();
        }
        String playername = "";
        try {
            PreparedStatement preparedStatement = dbmanager.getConnection()
                    .prepareStatement("SELECT `BannedBy` FROM `IPBans` WHERE `Banned` = ?;");
            preparedStatement.setString(1, player);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                playername = rs.getString("BannedBy");
            }
        } catch (SQLException e) {
            KratosCore.getInstance().getLogger()
                    .info("An error occured while connecting to the database!" + e.getMessage());
        }
        return playername;
    }

    public static String getIPReason(String player) {
        BanIPInfo info = Punishments.getIPBanInfo(player);
        if (info != null) {
            return info.getReason();
        }
        String reason = "";
        try {
            PreparedStatement preparedStatement = dbmanager.getConnection()
                    .prepareStatement("SELECT `Reason` FROM `IPBans` WHERE `Banned` = ?;");
            preparedStatement.setString(1, player);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                reason = rs.getString("Reason");
            }
        } catch (SQLException e) {
            KratosCore.getInstance().getLogger()
                    .info("An error occured while connecting to the database!" + e.getMessage());
        }
        return reason;
    }

    public static boolean isBanned(String player) {
        BanInfo info = Punishments.getBanInfo(player);
        if (info != null) {
            return true;
        }
        try {
            PreparedStatement preparedStatement = dbmanager.getConnection()
                    .prepareStatement("SELECT `Reason` FROM `Bans` WHERE `Banned` = ?;");
            preparedStatement.setString(1, player);

            ResultSet rs = preparedStatement.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            KratosCore.getInstance().getLogger()
                    .info("An error occured while connecting to the database!" + e.getMessage());
        }
        return false;
    }

    public static List<String> getBans() {
        List<String> list = new ArrayList<String>();
        try {
            PreparedStatement preparedStatement = dbmanager.getConnection()
                    .prepareStatement("SELECT `Banned` FROM `Bans`;");

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("Banned"));
            }
        } catch (SQLException e) {
            KratosCore.getInstance().getLogger()
                    .info("An error occured while connecting to the database!" + e.getMessage());
        }
        return list;
    }

    public static void addBan(String banned_by, String banned, Long ban_time, String reason) {
        try {
            PreparedStatement preparedStatement = dbmanager.getConnection().prepareStatement(
                    "INSERT INTO Bans(`BannedBy`, `Banned`, `BanTime`, `Reason`) VALUES (?, ?, ?, ?);");
            preparedStatement.setString(1, banned_by);
            preparedStatement.setString(2, banned);
            preparedStatement.setLong(3, ban_time);
            preparedStatement.setString(4, reason);

            preparedStatement.executeUpdate();

            Punishments.bans.add(new BanInfo(banned, banned_by, ban_time, reason));
        } catch (SQLException e) {
            KratosCore.getInstance().getLogger()
                    .info("An error occured while connecting to the database!" + e.getMessage());
        }
    }

    public static void removeBan(String player) {
        try {
            PreparedStatement preparedStatement = dbmanager.getConnection()
                    .prepareStatement("DELETE FROM `Bans` WHERE `Banned` = ?;");
            preparedStatement.setString(1, player);

            preparedStatement.executeUpdate();

            BanInfo info = Punishments.getBanInfo(player);
            if (info != null) {
                Punishments.bans.remove(info);
            }
        } catch (SQLException e) {
            KratosCore.getInstance().getLogger()
                    .info("An error occured while connecting to the database!" + e.getMessage());
        }
    }

    public static String getBannedBy(String player) {
        BanInfo info = Punishments.getBanInfo(player);
        if (info != null) {
            return info.getBy();
        }
        String playername = "";
        try {
            PreparedStatement preparedStatement = dbmanager.getConnection()
                    .prepareStatement("SELECT `BannedBy` FROM `Bans` WHERE `Banned` = ?;");
            preparedStatement.setString(1, player);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                playername = rs.getString("BannedBy");
            }
        } catch (SQLException e) {
            KratosCore.getInstance().getLogger()
                    .info("An error occured while connecting to the database!" + e.getMessage());
        }
        return playername;
    }

    public static Long getBanTime(String player) {
        BanInfo info = Punishments.getBanInfo(player);
        if (info != null) {
            return info.getTime();
        }
        long bantime = -1;
        try {
            PreparedStatement preparedStatement = dbmanager.getConnection()
                    .prepareStatement("SELECT `BanTime` FROM `Bans` WHERE `Banned` = ?;");
            preparedStatement.setString(1, player);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                bantime = rs.getLong("BanTime");
            }
        } catch (SQLException e) {
            KratosCore.getInstance().getLogger()
                    .info("An error occured while connecting to the database!" + e.getMessage());
        }
        return bantime;
    }

    public static String getReason(String player) {
        BanInfo info = Punishments.getBanInfo(player);
        if (info != null) {
            return info.getReason();
        }
        String reason = "";
        try {
            PreparedStatement preparedStatement = dbmanager.getConnection()
                    .prepareStatement("SELECT `Reason` FROM `Bans` WHERE `Banned` = ?;");
            preparedStatement.setString(1, player);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                reason = rs.getString("Reason");
            }
        } catch (SQLException e) {
            KratosCore.getInstance().getLogger()
                    .info("An error occured while connecting to the database!" + e.getMessage());
        }
        return reason;
    }
}