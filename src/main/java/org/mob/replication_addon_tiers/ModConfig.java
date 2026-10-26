package org.mob.replication_addon_tiers;

import com.buuz135.replication.ReplicationConfig;
import com.hrznstudio.titanium.annotation.config.ConfigFile;
import com.hrznstudio.titanium.annotation.config.ConfigVal;

@ConfigFile
public class ModConfig {

    @ConfigFile.Child(ModConfig.class)
    public class Disintegrator {

        @ConfigFile.Child(ModConfig.class)
        public class Advanced{

            @ConfigVal
            @ConfigVal.InRangeInt(min = 1)
            public static int MAX_PROGRESS = ReplicationConfig.Disintegrator.MAX_PROGRESS / 2;

            @ConfigVal
            @ConfigVal.InRangeInt(min = 1)
            public static int TANK_CAPACITY = 128000;

        }

        @ConfigFile.Child(ModConfig.class)
        public class Elite{

            @ConfigVal
            @ConfigVal.InRangeInt(min = 1)
            public static int MAX_PROGRESS = 1;

            @ConfigVal
            @ConfigVal.InRangeInt(min = 1)
            public static int TANK_CAPACITY = 256000;

        }

    }

    @ConfigFile.Child(ModConfig.class)
    public class MatterPipe {

        @ConfigVal(comment = "Advanced Matter Pipe Power Transfer Rate Multiplier")
        @ConfigVal.InRangeInt(min = 1)
        public static int ADVANCED_MATTER_PIPE = 4;

        @ConfigVal(comment = "Elite Matter Pipe Power Transfer Rate Multiplier")
        @ConfigVal.InRangeInt(min = 1)
        public static int ELITE_MATTER_PIPE = 16;

        @ConfigVal(comment = "Ultimate Matter Pipe Power Transfer Rate Multiplier")
        @ConfigVal.InRangeInt(min = 1)
        public static int ULTIMATE_MATTER_PIPE = 64;

    }

    @ConfigFile.Child(ModConfig.class)
    public class Replicator {

        @ConfigVal(comment = "The progress shown in game will be double")
        @ConfigVal.InRangeInt(min = 1)
        public static int ADVANCED_REPLICATOR_MAX_PROGRESS = 4;

        @ConfigVal.InRangeInt(min = 1)
        public static int ELITE_REPLICATOR_MAX_PROGRESS = 1;


    }

    @ConfigFile.Child(ModConfig.class)
    public class MatterTank {

        @ConfigVal(comment = "Multiplier for Matter Tank Tier 1")
        @ConfigVal.InRangeInt(min = 1, max = 1024)
        public static int MATTER_TANK_TIER_1 = 2;

        @ConfigVal(comment = "Multiplier for Matter Tank Tier 2")
        @ConfigVal.InRangeInt(min = 1, max = 1024)
        public static int MATTER_TANK_TIER_2 = 4;

        @ConfigVal(comment = "Multiplier for Matter Tank Tier 3")
        @ConfigVal.InRangeInt(min = 1, max = 1024)
        public static int MATTER_TANK_TIER_3 = 8;

        @ConfigVal(comment = "Multiplier for Matter Tank Tier 4")
        @ConfigVal.InRangeInt(min = 1, max = 1024)
        public static int MATTER_TANK_TIER_4 = 16;

        @ConfigVal(comment = "Multiplier for Matter Tank Tier 5")
        @ConfigVal.InRangeInt(min = 1, max = 1024)
        public static int MATTER_TANK_TIER_5 = 32;

        @ConfigVal(comment = "Multiplier for Matter Tank Tier 6")
        @ConfigVal.InRangeInt(min = 1, max = 1024)
        public static int MATTER_TANK_TIER_6 = 64;

        @ConfigVal(comment = "Multiplier for Matter Tank Tier 7")
        @ConfigVal.InRangeInt(min = 1, max = 1024)
        public static int MATTER_TANK_TIER_7 = 128;

        @ConfigVal(comment = "Multiplier for Matter Tank Tier 8")
        @ConfigVal.InRangeInt(min = 1, max = 1024)
        public static int MATTER_TANK_TIER_8 = 256;


    }
}