package me.kratoscore.net.bungee.punishment.commands;

import java.util.Arrays;
import java.util.UUID;

import me.kratoscore.net.api.commands.DBCommand;
import me.kratoscore.net.bungee.KratosCore;
import me.kratoscore.net.bungee.events.BanEvent;
import me.kratoscore.net.bungee.punishment.BanAPI;
import me.kratoscore.net.bungee.punishment.Punishments;
import me.kratoscore.net.bungee.user.BungeeUser;
import me.kratoscore.net.bungee.utils.PlayerInfo;
import me.kratoscore.net.bungee.utils.UUIDFetcher;
import me.kratoscore.net.bungee.utils.Utils;
import com.google.common.base.Joiner;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BanCommand extends DBCommand {

    public BanCommand() {
        super("ban");
    }

    @Override
    public void onExecute(BungeeUser user, String[] args) {
        onExecute(user.sender(), args);
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Boolean useUUIDs = KratosCore.getInstance().getConfigData().UUIDSTORAGE;
        if (args.length < 2) {
            for (String s : Punishments.punishments.getFile().getStringList("Punishments.Ban.Messages.WrongArgs")) {
                sender.sendMessage(TextComponent.fromLegacyText(s.replace("&", "§")));
            }
            return;
        }
        UUID uuid = UUIDFetcher.getUUIDOf(args[0]);
        if (uuid == null && useUUIDs) {
            return;
        }
        if ((useUUIDs ? BanAPI.isBanned(uuid.toString()) : BanAPI.isBanned(args[0]))) {
            for (String s : Punishments.punishments.getFile().getStringList("Punishments.Ban.Messages.AlreadyBanned")) {
                sender.sendMessage(Utils.format(s));
            }
            return;
        }
        ProxiedPlayer p = ProxyServer.getInstance().getPlayer(args[0]);
        String reason = Joiner.on(" ").join(Arrays.copyOfRange(args, 1, args.length));

        BanAPI.addBan(sender.getName(), (useUUIDs ? uuid.toString() : args[0]), -1L, reason);
        if (p != null) {
            p.disconnect(
                    Utils.format(Joiner.on("\n")
                            .join(Punishments.punishments.getFile()
                                    .getStringList("Punishments.Ban.Messages.KickMessage"))
                            .replace("%banner%", sender.getName()).replace("%unbantime%", "Never")
                            .replace("%reason%", reason)));
        }
        PlayerInfo info = new PlayerInfo((useUUIDs ? uuid.toString() : args[0]));
        info.addBan();

        BanEvent event = new BanEvent(sender.getName(), args[0], -1L, reason);
        BungeeCord.getInstance().getPluginManager().callEvent(event);

        for (String s : Punishments.punishments.getFile().getStringList("Punishments.Ban.Messages.Banned")) {
            sender.sendMessage(Utils.format(s.replace("%player%", args[0]).replace("%reason%", reason)));
        }
    }
}