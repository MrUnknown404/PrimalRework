package mrunknown404.primalrework.datagen;

import mrunknown404.primalrework.PrimalRework;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = PrimalRework.MOD_ID)
public class DataGenerators {
	@SubscribeEvent
	public static void onGatherData(GatherDataEvent e) {
		DataGenerator gen = e.getGenerator();
		ExistingFileHelper efh = e.getExistingFileHelper();
		gen.addProvider(new GeneratorItemModel(gen, efh));
		gen.addProvider(new GeneratorBlockState(gen, efh));
		gen.addProvider(new GeneratorBlockModel(gen, efh));
	}
}
