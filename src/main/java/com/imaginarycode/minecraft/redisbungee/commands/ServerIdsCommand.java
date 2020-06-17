package com.imaginarycode.minecraft.redisbungee.commands;

import com.google.common.base.Joiner;
import com.imaginarycode.minecraft.redisbungee.RedisBungee;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class ServerIdsCommand extends Command {
    public ServerIdsCommand() {
        super("serverids", "redisbungee.command.serverids");
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        TextComponent textComponent = new TextComponent();
        textComponent.setText("All server IDs: " + Joiner.on(", ").join(RedisBungee.getApi().getAllServers()));
        textComponent.setColor(ChatColor.YELLOW);
        sender.sendMessage(textComponent);
    }
}