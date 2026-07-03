package com.example;

import com.google.inject.Provides;
import java.util.Arrays;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.MenuEntry;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@PluginDescriptor(
        name = "No Drink In Bank",
        description = "Removes the Drink option from potions while the bank interface is open",
        tags = {"bank", "potion", "drink", "menu", "qol"}
)
public class NoDrinkInBankPlugin extends Plugin
{
    @Inject
    private Client client;

    @Inject
    private NoDrinkInBankConfig config;

    @Provides
    NoDrinkInBankConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(NoDrinkInBankConfig.class);
    }

    @Subscribe
    public void onMenuEntryAdded(MenuEntryAdded event)
    {
        if (!isBankOpen())
        {
            return;
        }

        String option = event.getOption();

        // Covers "Drink" and cases like "Drink(1)" or similar option variants
        if (option == null || !option.toLowerCase().startsWith("drink"))
        {
            return;
        }

        removeCurrentEntry();
    }

    /**
     * Removes the most recently added menu entry (the one that triggered onMenuEntryAdded)
     * by rebuilding the client's menu entry array without it.
     */
    private void removeCurrentEntry()
    {
        MenuEntry[] entries = client.getMenuEntries();

        if (entries.length == 0)
        {
            return;
        }

        // The entry that was just added is always the last one in the array
        MenuEntry[] newEntries = Arrays.copyOf(entries, entries.length - 1);
        client.setMenuEntries(newEntries);
    }

    private boolean isBankOpen()
    {
        return client.getWidget(WidgetInfo.BANK_CONTAINER) != null;
    }
}