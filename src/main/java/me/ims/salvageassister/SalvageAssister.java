package me.ims.salvageassister;

import me.ims.salvageassister.managers.EventManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = SalvageAssister.MODID, name = SalvageAssister.NAME, version = SalvageAssister.VERSION)
public class SalvageAssister {
    // Mod information
    public static final String MODID = "salvageassister";
    public static final String NAME = "Salvage Assister";
    public static final String VERSION = "1.0.0";
    public static final String FULLNAME = NAME + " " + VERSION;

    // Convenience variables
    public static final Minecraft mc = Minecraft.getMinecraft();
    public static Logger logger;

    // Managers
    public static EventManager eventManager;

    @Mod.Instance(MODID)
    private static SalvageAssister INSTANCE;

    public static SalvageAssister getInstance() {
        return INSTANCE;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        logger.info("Pre Initializing " + FULLNAME);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        logger.info("Initializing " + FULLNAME);

        // Initialize managers
        eventManager = new EventManager();

        // Register events
        MinecraftForge.EVENT_BUS.register(eventManager);
    }
}
