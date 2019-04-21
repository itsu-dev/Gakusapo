package net.comorevi.discordconnector.core;

import cn.nukkit.Server;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class DiscordProvider3 extends ListenerAdapter {

    private static String userName;

    public static void main(String[] args) {
        start("MzY2MjQ1MDU4NzExOTEyNDU4.D2J0hA.EIYY1FoT8CTXKykpeAIr5_uF1ys", "いつ");
    }

    public static void start(String token, String userName) {
        DiscordProvider3.userName = userName;
        try {
            JDA jda = new JDABuilder(token).build();
            jda.addEventListener(new DiscordProvider3());
        } catch (LoginException e) {
            Server.getInstance().getLogger().alert("ログインに失敗しました。");
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage().getContentDisplay();

        if (!message.startsWith("!")) {
            return;
        }

        if (event.isFromType(ChannelType.PRIVATE)) {
            if (!event.getAuthor().getName().endsWith(userName)) {
                event.getChannel().sendMessage("指定されたユーザーのみが操作できます。");
                return;
            }

        } else {
            if (!event.getMember().getEffectiveName().endsWith(userName)) {
                event.getChannel().sendMessage("指定されたユーザーのみが操作できます。");
                return;
            }
        }

        Server.getInstance().dispatchCommand(Server.getInstance().getConsoleSender(), message.substring(1));
        Server.getInstance().getLogger().info("Discordから実行されました: " + message.substring(1));
        event.getChannel().sendMessage("コマンドを実行しました！");
    }

}
