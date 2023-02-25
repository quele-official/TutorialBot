package de.quele;

import de.quele.commands.MCStatsCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Main {
    public static JDA jda;
    public static void main(String[] args) throws Exception {
        System.out.println("Bot startet!");
        JDA api = JDABuilder.createDefault("MTA2MzQ1Nzc0MTE5NDE1NDEwNA.GTF--l.IvOhqjCLpOC7n1F23bI0VgqeypTSyZaC4gbvrM")
                .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .addEventListeners(new MCStatsCommand()).build();

        api.updateCommands().addCommands(Commands.slash("mcstats", "Get the stats of a minecraft server")
                    .addOption(OptionType.STRING, "domain", "The domain of the minecraft server", true)).queue();
    }
    // add Listeners

    public static void addListeners() {
        jda.addEventListener(new MCStatsCommand());
    }


    public static JDA getJda() {
        return jda;
    }
}