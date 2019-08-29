package mrunknown404.primalrework;

import java.util.Iterator;

import com.google.common.collect.Lists;

import mrunknown404.primalrework.handlers.RegistryHandler;
import mrunknown404.primalrework.handlers.HarvestHandler;
import mrunknown404.primalrework.handlers.events.BlockEventHandler;
import mrunknown404.primalrework.handlers.events.PlayerEventHandler;
import mrunknown404.primalrework.proxy.CommonProxy;
import mrunknown404.primalrework.util.DummyRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

@Mod(modid = Main.MOD_ID, useMetadata = true)
public class Main {
	
	public static final String MOD_ID = "primalrework";
	
	@Instance
	public static Main main;
	
	@SidedProxy(clientSide = "mrunknown404.primalrework.proxy.ClientProxy", serverSide = "mrunknown404.primalrework.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		HarvestHandler.changeHarvestLevels();
		
		RegistryHandler.registerEntities();
		proxy.registerEntityRenders();
		
		MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
		MinecraftForge.EVENT_BUS.register(new BlockEventHandler());
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.registerSounds();
		removeRecipes();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		
	}
	
	@EventHandler
	public void serverStart(FMLServerStartingEvent e) {
		
	}
	
	public void removeRecipes() {
		ForgeRegistry<IRecipe> rr = (ForgeRegistry<IRecipe>) ForgeRegistries.RECIPES;
		
		for (IRecipe r : Lists.newArrayList(rr.getValuesCollection())) {
			if (r.getRegistryName().toString().startsWith("minecraft:")) {
				rr.remove(r.getRegistryName());
				rr.register(DummyRecipe.from(r));
			}
		}
		
		Iterator<ItemStack> it = FurnaceRecipes.instance().getSmeltingList().keySet().iterator();
		
		while (it.hasNext()) {
			ItemStack recipe = it.next();
			it.remove();
		}
	}
}
