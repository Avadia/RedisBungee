package com.imaginarycode.minecraft.redisbungee.commands;

import com.imaginarycode.minecraft.redisbungee.RedisBungee;
import com.imaginarycode.minecraft.redisbungee.util.MessageUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

import java.text.SimpleDateFormat;
import java.util.UUID;

public class LastSeenCommand extends Command {
    private final RedisBungee plugin;

    public LastSeenCommand(RedisBungee plugin) {
        super("lastseen", "redisbungee.command.lastseen", "rlastseen");
        this.plugin = plugin;
    }

    @Override
    public void execute(final CommandSender sender, final String[] args) {
        plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
            @Override
            public void run() {
                if (args.length > 0) {
                    UUID uuid = plugin.getUuidTranslator().getTranslatedUuid(args[0], true);
                    if (uuid == null) {
                        sender.sendMessage(MessageUtil.PLAYER_NOT_FOUND);
                        return;
                    }
                    long secs = RedisBungee.getApi().getLastOnline(uuid);
                    TextComponent message = new TextComponent();
                    if (secs == 0) {
                        message.setColor(ChatColor.GREEN);
                        message.setText(args[0] + " is currently online.");
                    } else if (secs != -1) {
                        message.setColor(ChatColor.BLUE);
                        message.setText(args[0] + " was last online on " + new SimpleDateFormat().format(secs) + ".");
                    } else {
                        message.setColor(ChatColor.RED);
                        message.setText(args[0] + " has never been online.");
                    }
                    sender.sendMessage(message);
                } else {
                    sender.sendMessage(MessageUtil.NO_PLAYER_SPECIFIED);
                }
            }
        });
    }
}