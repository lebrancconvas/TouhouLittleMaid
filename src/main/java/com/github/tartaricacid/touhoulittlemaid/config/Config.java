package com.github.tartaricacid.touhoulittlemaid.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class Config {
    public static ForgeConfigSpec.ConfigValue<String> MAID_TAMED_ITEM;
    public static ForgeConfigSpec.ConfigValue<String> MAID_TEMPTATION_ITEM;
    public static ForgeConfigSpec.ConfigValue<String> MAID_NTR_ITEM;
    public static ForgeConfigSpec.BooleanValue MAID_CHANGE_MODEL;
    public static ForgeConfigSpec.IntValue OWNER_MAX_MAID_NUM;

    public static ForgeConfigSpec.BooleanValue CHAIR_CHANGE_MODEL;

    public static ForgeConfigSpec initConfig() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("maid");
        {
            builder.comment("The item that can tamed maid");
            MAID_TAMED_ITEM = builder.define("MaidTamedItem", "minecraft:cake");

            builder.comment("The item that can temptation maid");
            MAID_TEMPTATION_ITEM = builder.define("MaidTemptationItem", "minecraft:cake");

            builder.comment("The item that can ntr maid");
            MAID_NTR_ITEM = builder.define("MaidNtrItem", "minecraft:structure_void");

            builder.comment("Maid can switch models freely");
            MAID_CHANGE_MODEL = builder.define("MaidChangeModel", true);

            builder.comment("The maximum number of maids the player own");
            OWNER_MAX_MAID_NUM = builder.defineInRange("OwnerMaxMaidNum", Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
        }
        builder.pop();

        builder.push("chair");
        {
            builder.comment("Chair can switch models freely");
            CHAIR_CHANGE_MODEL = builder.define("ChairChangeModel", true);
        }
        builder.pop();

        return builder.build();
    }
}
