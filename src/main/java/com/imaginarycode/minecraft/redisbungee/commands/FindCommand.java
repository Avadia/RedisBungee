package com.imaginarycode.minecraft.redisbungee.commands;

import com.imaginarycode.minecraft.redisbungee.RedisBungee;
import com.imaginarycode.minecraft.redisbungee.util.MessageUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Command;

import java.util.UUID;

public class FindCommand extends Command {
    private final RedisBungee plugin;

    public FindCommand(RedisBungee plugin) {
        super("find", "bungeecord.command.find", "rfind");
        this.plugin = plugin;
    }

    @Override
    public void execute(final CommandSender sender, final String[] args) {
        plugin.getProxy().getScheduler().runAsync(plugin, () -> {
            if (args.length > 0) {
                UUID uuid = plugin.getUuidTranslator().getTranslatedUuid(args[0], true);
                if (uuid == null) {
                    sender.sendMessage(MessageUtil.PLAYER_NOT_FOUND);
                    return;
                }
                ServerInfo si = RedisBungee.getApi().getServerFor(uuid);
                if (si != null) {
                    TextComponent message = new TextComponent();
                    message.setColor(ChatColor.BLUE);
                    message.setText(args[0] + " is on " + si.getName() + ".");
                    sender.sendMessage(message);
                } else {
                    sender.sendMessage(MessageUtil.PLAYER_NOT_FOUND);
                }
            } else {
                sender.sendMessage(MessageUtil.NO_PLAYER_SPECIFIED);
            }
        });
    }
}