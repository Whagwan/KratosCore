package me.kratoscore.net.bungee.punishment.commands;

import java.util.Arrays;
import java.util.UUID;

import me.kratoscore.net.api.commands.DBCommand;
import me.kratoscore.net.bungee.KratosCore;
import me.kratoscore.net.bungee.events.IPBanEvent;
import me.kratoscore.net.bungee.punishment.BanAPI;
import me.kratoscore.net.bungee.punishment.Punishments;
import me.kratoscore.net.bungee.user.BungeeUser;
import me.kratoscore.net.bungee.utils.PlayerInfo;
import me.kratoscore.net.bungee.utils.UUIDFetcher;
import me.kratoscore.net.bungee.utils.Utils;
import com.google.common.base.Joiner;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BanIPCommand extends DBCommand {

    public BanIPCommand() {
        super("banip");
    }

    @Override
    public void onExecute(BungeeUser user, String[] args) {
        onExecute(user.sender(), args);
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Boolean useUUIDs = KratosCore.getInstance().getConfigData().UUIDSTORAGE;
        if (args.length < 2) {
            for (String s : Punishments.punishments.getFile().getStringList("Punishments.BanIP.Messages.WrongArgs")) {
                sender.sendMessage(Utils.format(s));
            }
            return;
        }
        if (BanAPI.isBanned(args[0])) {
            for (String s : Punishments.punishments.getFile()
                    .getStringList("Punishments.BanIP.Messages.AlreadyBanned")) {
                sender.sendMessage(Utils.format(s));
            }
            return;
        }
        ProxiedPlayer p = BungeeCord.getInstance().getPlayer(args[0]);
        UUID uuid = UUIDFetcher.getUUIDOf(args[0]);
        if (uuid == null && useUUIDs) {
            return;
        }

        PlayerInfo info = new PlayerInfo((useUUIDs ? uuid.toString() : args[0]));
        String IP = info.getIP();
        if (IP == null) {
            for (String s : Punishments.punishments.getFile().getStringList("Punishments.BanIP.Messages.NeverJoined")) {
                sender.sendMessage(Utils.format(s));
            }
            return;
        }
        String reason = Joiner.on(" ").join(Arrays.copyOfRange(args, 1, args.length));
        BanAPI.addIPBan(sender.getName(), IP, reason);

        if (p != null) {
            p.disconnect(
                    Utils.format(Joiner.on("\n")
                            .join(Punishments.punishments.getFile()
                                    .getStringList("Punishments.BanIP.Messages.KickMessage"))
                            .replace("%banner%", sender.getName()).replace("%unbantime%", "Never")
                            .replace("%reason%", reason)));
        }
        IPBanEvent event = new IPBanEvent(sender.getName(), args[0], reason);
        BungeeCord.getInstance().getPluginManager().callEvent(event);
        info.addBan();
        for (String s : Punishments.punishments.getFile().getStringList("Punishments.BanIP.Messages.Banned")) {
            sender.sendMessage(Utils.format(s.replace("%ip%", IP).replace("%reason%", reason)));
        }
    }
}