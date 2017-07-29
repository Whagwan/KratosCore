package me.kratoscore.net.bungee.punishment.commands;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import me.kratoscore.net.api.commands.DBCommand;
import me.kratoscore.net.bungee.KratosCore;
import me.kratoscore.net.bungee.events.BanEvent;
import me.kratoscore.net.bungee.punishment.BanAPI;
import me.kratoscore.net.bungee.punishment.DateUtil;
import me.kratoscore.net.bungee.punishment.Punishments;
import me.kratoscore.net.bungee.user.BungeeUser;
import me.kratoscore.net.bungee.utils.PlayerInfo;
import me.kratoscore.net.bungee.utils.UUIDFetcher;
import me.kratoscore.net.bungee.utils.Utils;
import com.google.common.base.Joiner;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class TempbanCommand extends DBCommand {

    public TempbanCommand() {
        super("tempban");
    }

    @Override
    public void onExecute(BungeeUser user, String[] args) {
        onExecute(user.sender(), args);
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Boolean useUUIDs = KratosCore.getInstance().getConfigData().UUIDSTORAGE;
        if (args.length < 3) {
            for (String s : Punishments.punishments.getFile().getStringList("Punishments.Tempban.Messages.WrongArgs")) {
                sender.sendMessage(Utils.format(s));
            }
            return;
        }
        UUID uuid = UUIDFetcher.getUUIDOf(args[0]);
        if (uuid == null && useUUIDs) {
            return;
        }
        if ((useUUIDs ? BanAPI.isBanned(uuid.toString()) : BanAPI.isBanned(args[0]))) {
            for (String s : Punishments.punishments.getFile()
                    .getStringList("Punishments.Tempban.Messages.AlreadyBanned")) {
                sender.sendMessage(Utils.format(s));
            }
            return;
        }
        String reason = Joiner.on(" ").join(Arrays.copyOfRange(args, 2, args.length));
        Long time = -1L;
        try {
            time = DateUtil.parseDateDiff(args[1], true);
        } catch (Exception e) {
            for (String s : Punishments.punishments.getFile().getStringList("Punishments.Tempban.Messages.WrongTime")) {
                sender.sendMessage(Utils.format(s));
            }
            return;
        }
        BanAPI.addBan(sender.getName(), (useUUIDs ? uuid.toString() : args[0]), time, reason);

        ProxiedPlayer p = ProxyServer.getInstance().getPlayer(args[0]);
        if (p != null) {
            SimpleDateFormat df2 = new SimpleDateFormat("kk:mm dd/MM/yyyy");
            String date = df2.format(new Date(time));

            p.disconnect(
                    Utils.format(Joiner.on("\n")
                            .join(Punishments.punishments.getFile()
                                    .getStringList("Punishments.Tempban.Messages.KickMessage"))
                            .replace("%banner%", sender.getName()).replace("%unbantime%", (time == -1L ? "Never" : date))
                            .replace("%reason%", reason)));
        }
        BanEvent event = new BanEvent(sender.getName(), args[0], time, reason);
        BungeeCord.getInstance().getPluginManager().callEvent(event);

        PlayerInfo info = new PlayerInfo((useUUIDs ? uuid.toString() : args[0]));
        info.addBan();
        for (String s : Punishments.punishments.getFile().getStringList("Punishments.Tempban.Messages.Banned")) {
            sender.sendMessage(Utils.format(s.replace("%player%", args[0]).replace("%reason%", reason)));
        }
    }
}