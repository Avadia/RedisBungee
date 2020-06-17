package com.imaginarycode.minecraft.redisbungee.commands;

import com.google.common.base.Joiner;
import com.imaginarycode.minecraft.redisbungee.RedisBungee;
import com.imaginarycode.minecraft.redisbungee.util.MessageUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class SendToAllCommand extends Command {

    public SendToAllCommand() {
        super("sendtoall", "redisbungee.command.sendtoall", "rsendtoall");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length > 0) {
            String command = Joiner.on(" ").skipNulls().join(args);
            RedisBungee.getApi().sendProxyCommand(command);
            TextComponent message = new TextComponent();
            message.setColor(ChatColor.GREEN);
            message.setText("Sent the command /" + command + " to all proxies.");
            sender.sendMessage(message);
        } else {
            sender.sendMessage(MessageUtil.NO_COMMAND_SPECIFIED);
        }
    }
}