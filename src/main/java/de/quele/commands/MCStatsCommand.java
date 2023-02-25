package de.quele.commands;

import de.quele.utils.RestAPI;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MCStatsCommand extends ListenerAdapter {

    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String domain = event.getOption("domain").getAsString();
        if(event.getName().equals("mcstats")) {
            if (event.getOption("domain") != null) {

                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("Minecraft Stats");
                builder.setDescription("Minecraft Stats for " + domain);
                builder.addField("Domain", domain, false);

                RestAPI.getObjectFromWebsiteAsync(domain)
                        .thenAccept(json -> {
                            builder.addField("Online", "Yes", false);
                            builder.addField("Version", json.getString("version"), false);
                            builder.addField("Players", json.getJSONObject("players").getInt("online") + "/" + json.getJSONObject("players").getInt("max"), false);
                            builder.addField("MOTD", json.getJSONObject("motd").getJSONArray("clean").toString(), false);
                            if(json.getBoolean("online")) {
                                event.replyEmbeds(builder.build()).setEphemeral(true).queue();
                            } else {
                                event.reply("Der Server ist zurzeit nicht erreichbar!").setEphemeral(true).queue();
                            }
                        }).exceptionally(ex -> {
                            event.reply("Error: " + ex.getMessage()).setEphemeral(true).queue();
                            return null;
                        });
            } else {
                event.reply("Please enter a domain").setEphemeral(true).queue();
            }
        }
    }

}
