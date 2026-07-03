package com.example;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;

@ConfigGroup("nodrinkinbank")
public interface NoDrinkInBankConfig extends Config
{
    // No settings needed right now — this exists so the plugin has
    // a config group RuneLite can register. Add options here later
    // if you want, e.g. a toggle to also hide "Drink" in the trade
    // or grand exchange interfaces.
}