package com.imaginarycode.minecraft.redisbungee.commands;

import com.imaginarycode.minecraft.redisbungee.RedisBungee;
import com.imaginarycode.minecraft.redisbungee.util.MessageUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

import java.util.UUID;

public class PlayerProxyCommand extends Command {
    private final RedisBungee plugin;

    public PlayerProxyCommand(RedisBungee plugin) {
        super("pproxy", "redisbungee.command.pproxy");
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
                String proxy = RedisBungee.getApi().getProxy(uuid);
                if (proxy != null) {
                    TextComponent message = new TextComponent();
                    message.setColor(ChatColor.GREEN);
                    message.setText(args[0] + " is connected to " + proxy + ".");
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