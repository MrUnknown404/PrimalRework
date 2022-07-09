package mrunknown404.primalrework.events.client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import mrunknown404.primalrework.blocks.HarvestInfo;
import mrunknown404.primalrework.blocks.StagedBlock;
import mrunknown404.primalrework.init.InitToolMaterials;
import mrunknown404.primalrework.items.StagedItem;
import mrunknown404.primalrework.utils.DoubleCache;
import mrunknown404.primalrework.utils.PRConfig;
import mrunknown404.primalrework.utils.ToolMaterial;
import mrunknown404.primalrework.utils.enums.ToolType;
import mrunknown404.primalrework.utils.helpers.BlockH;
import mrunknown404.primalrework.utils.helpers.ColorH;
import mrunknown404.primalrework.utils.helpers.ItemH;
import mrunknown404.primalrework.utils.helpers.RayTraceH;
import mrunknown404.primalrework.utils.helpers.WordH;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.LanguageMap;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.gui.GuiUtils;

public class HarvestDisplayEvents {
	private DoubleCache<Block, Item, List<ITextComponent>> blockCache = DoubleCache.and();
	
	private static final IFormattableTextComponent YES_MINE = WordH.string("\u2714 ").withStyle(TextFormatting.GREEN),
			NO_MINE = WordH.string("\u274C ").withStyle(TextFormatting.RED), ICON_SPACE = WordH.string("    ");
	
	@SubscribeEvent
	public void onRender(RenderGameOverlayEvent.Post e) {
		if (e.getType() != ElementType.ALL) {
			return;
		}
		
		Minecraft mc = Minecraft.getInstance();
		if (mc.screen != null || mc.options.renderDebug) {
			return;
		}
		
		PRConfig.Client config = PRConfig.CLIENT;
		if (!config.harvestDisplay_isEnabled.get()) {
			return;
		}
		
		Block bOld = mc.player.getCommandSenderWorld().getBlockState(RayTraceH.rayTrace(mc.player, e.getPartialTicks(), false).getBlockPos()).getBlock();
		ItemStack stack = mc.player.getMainHandItem();
		Item item = stack.getItem();
		
		if ((item != Items.AIR && !(item instanceof StagedItem)) || !(bOld instanceof StagedBlock)) {
			return;
		}
		
		StagedBlock b = (StagedBlock) bOld;
		MainWindow w = e.getWindow();
		MatrixStack s = e.getMatrixStack();
		
		List<ITextComponent> texts = new ArrayList<ITextComponent>();
		
		if (blockCache.is(b, item)) {
			texts = blockCache.get();
		} else if (b != Blocks.AIR) {
			List<HarvestInfo> infos = BlockH.getBlockHarvestInfos(b);
			
			if (infos == null || !b.hasAccessToCurrentStage()) {
				texts = ItemH.UNKNOWN_ITEM;
				GuiUtils.drawHoveringText(s, ItemH.UNKNOWN_ITEM, -8, 0, w.getWidth(), w.getHeight(), -1, mc.font);
				return;
			}
			texts.add(config.harvestDisplay_showIcon.get() && b.getStagedItem() != null ? ICON_SPACE.copy().append(b.getName()) : b.getName());
			
			if (config.harvestDisplay_showHardnessAndBlast.get()) {
				float hardness = b.blockInfo.getHardness();
				float blast = b.blockInfo.getBlast();
				
				texts.add(hardness >= 0 ? WordH.string(WordH.formatNumber(1, hardness) + " ").append(WordH.translate("tooltips.block.hardness")) :
						WordH.translate("tooltips.block.unbreakable"));
				texts.add(blast >= 0 ? WordH.string(WordH.formatNumber(1, blast) + " ").append(WordH.translate("tooltips.block.blast")) :
						WordH.translate("tooltips.block.unexplodable"));
			}
			
			if (config.harvestDisplay_showRequiredTool.get()) {
				ToolType toolType = item instanceof StagedItem ? ((StagedItem) item).toolType : ToolType.NONE;
				ToolMaterial toolMat = item instanceof StagedItem ? ((StagedItem) item).toolMat : InitToolMaterials.HAND.get();
				boolean has = infos.stream().anyMatch((i) -> i.toolType == toolType);
				
				for (HarvestInfo info : infos) {
					if (info.toolMat != InitToolMaterials.UNBREAKABLE.get()) {
						boolean canMine = (!has && info.toolType == ToolType.NONE) || (info.toolType == toolType && info.toolMat.level <= toolMat.level);
						texts.add((canMine ? YES_MINE : NO_MINE).copy()
								.append(WordH.translate("tooltips.stat.level").withStyle(TextFormatting.WHITE).append(" " + info.toolMat.level + " " + info.toolType.getName())));
					}
				}
			}
			
			blockCache.set(b, item, texts);
		}
		
		if (!texts.isEmpty()) {
			drawHoveringText(s, texts, mc.font);
			
			if (config.harvestDisplay_showIcon.get() && b.getStagedItem() != null) {
				StagedItem bitem = b.getStagedItem();
				if (bitem != null) {
					mc.getItemRenderer().blitOffset += 410;
					mc.getItemRenderer().renderGuiItem(new ItemStack(bitem), 4, 3);
					mc.getItemRenderer().blitOffset -= 410;
				}
			}
			
			RenderSystem.enableBlend();
			mc.textureManager.bind(AbstractGui.GUI_ICONS_LOCATION);
		}
	}
	
	@SuppressWarnings("deprecation")
	private static void drawHoveringText(MatrixStack mStack, List<? extends ITextProperties> textLines, FontRenderer font) {
		RenderSystem.disableRescaleNormal();
		RenderSystem.disableDepthTest();
		
		int y = 4, width = font.width(textLines.stream().max(Comparator.comparingInt(font::width)).get()) + 2, height = 13 + ((textLines.size() - 1) * 10);
		int bgColor = ColorH.rgba2Int(16, 0, 16, 200), borderColor = ColorH.rgba2Int(37, 0, 94, 160);
		if (textLines.size() > 1) {
			height += 2;
		}
		
		mStack.pushPose();
		Matrix4f mat = mStack.last().pose();
		
		GuiUtils.drawGradientRect(mat, 400, 1, y - 4, width + 7, y - 3, bgColor, bgColor);
		GuiUtils.drawGradientRect(mat, 400, 1, y + height + 3, width + 7, y + height + 4, bgColor, bgColor);
		GuiUtils.drawGradientRect(mat, 400, 1, y - 3, width + 7, y + height + 3, bgColor, bgColor);
		GuiUtils.drawGradientRect(mat, 400, 0, y - 3, 1, y + height + 3, bgColor, bgColor);
		GuiUtils.drawGradientRect(mat, 400, width + 7, y - 3, width + 8, y + height + 3, bgColor, bgColor);
		GuiUtils.drawGradientRect(mat, 400, 1, y - 2, 2, y + height + 2, borderColor, borderColor);
		GuiUtils.drawGradientRect(mat, 400, width + 6, y - 2, width + 7, y + height + 2, borderColor, borderColor);
		GuiUtils.drawGradientRect(mat, 400, 1, y - 3, width + 7, y - 2, borderColor, borderColor);
		GuiUtils.drawGradientRect(mat, 400, 1, y + height + 2, width + 7, y + height + 3, borderColor, borderColor);
		
		IRenderTypeBuffer.Impl renderType = IRenderTypeBuffer.immediate(Tessellator.getInstance().getBuilder());
		mStack.translate(0, 0, 400);
		
		for (int i = 0; i < textLines.size(); ++i) {
			ITextProperties line = textLines.get(i);
			if (line != null) {
				font.drawInBatch(LanguageMap.getInstance().getVisualOrder(line), 4 + (i == 0 ? 2 : 1), y + (i == 0 ? 3 : 0), -1, true, mat, renderType, false, 0, 15728880);
			}
			
			y += i == 0 ? 17 : 10;
		}
		
		renderType.endBatch();
		mStack.popPose();
		
		RenderSystem.enableDepthTest();
		RenderSystem.enableRescaleNormal();
	}
}
