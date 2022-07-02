package mrunknown404.primalrework.events.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import mrunknown404.primalrework.blocks.BlockInfo;
import mrunknown404.primalrework.blocks.HarvestInfo;
import mrunknown404.primalrework.blocks.StagedBlock;
import mrunknown404.primalrework.items.StagedItem;
import mrunknown404.primalrework.utils.DoubleCache;
import mrunknown404.primalrework.utils.PRConfig;
import mrunknown404.primalrework.utils.enums.ToolMaterial;
import mrunknown404.primalrework.utils.enums.ToolType;
import mrunknown404.primalrework.utils.helpers.BlockH;
import mrunknown404.primalrework.utils.helpers.ColorH;
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

public class HarvestDisplayCEvents {
	private DoubleCache<Block, Item, List<ITextComponent>> blockCache = DoubleCache.and();
	
	private static final IFormattableTextComponent YES_MINE = WordH.string("\u2714 ").withStyle(TextFormatting.GREEN),
			NO_MINE = WordH.string("\u274C ").withStyle(TextFormatting.RED), ICON_SPACE = WordH.string("    ");
	private static final List<ITextComponent> UNKNOWN_BLOCK = Arrays.asList(WordH.string("???"));
	
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
		
		Block bOld = mc.player.getCommandSenderWorld().getBlockState(RayTraceH.rayTrace(e.getPartialTicks(), false).getBlockPos()).getBlock();
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
		} else if (b != null && b != Blocks.AIR) {
			List<HarvestInfo> infos = BlockH.getBlockHarvestInfos(b);
			
			if (infos == null) {
				GuiUtils.drawHoveringText(s, UNKNOWN_BLOCK, -8, 0, w.getWidth(), w.getHeight(), -1, mc.font);
				return;
			}
			texts.add(config.harvestDisplay_showIcon.get() ? ICON_SPACE.copy().append(b.getName()) : b.getName());
			
			if (config.harvestDisplay_showHardnessAndBlast.get()) {
				BlockInfo blockInfo = b.blockInfo;
				float hardness = blockInfo.getHardness();
				float blast = blockInfo.getBlast();
				
				texts.add(hardness != -1 ? WordH.string(WordH.toPrintableNumber(hardness) + " ").append(WordH.translate("tooltips.block.hardness")) :
						WordH.translate("tooltips.block.unbreakable"));
				texts.add(blast != -1 ? WordH.string(WordH.toPrintableNumber(blast) + " ").append(WordH.translate("tooltips.block.blast")) :
						WordH.translate("tooltips.block.unexplodable"));
			}
			
			if (config.harvestDisplay_showRequiredTool.get()) {
				ToolType toolType = item instanceof StagedItem ? ((StagedItem) item).toolType : ToolType.NONE;
				ToolMaterial toolMat = item instanceof StagedItem ? ((StagedItem) item).toolMat : ToolMaterial.HAND;
				boolean has = infos.stream().anyMatch((i) -> i.toolType == toolType);
				
				for (HarvestInfo info : infos) {
					if (info.toolMat != ToolMaterial.UNBREAKABLE) {
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
			
			if (config.harvestDisplay_showIcon.get()) {
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
		int tooltipTextWidth = 0;
		
		for (ITextProperties textLine : textLines) {
			int textLineWidth = font.width(textLine) + 2;
			if (textLineWidth > tooltipTextWidth) {
				tooltipTextWidth = textLineWidth;
			}
		}
		
		int tooltipX = 4, tooltipY = 4;
		int titleLinesCount = 1, tooltipHeight = 13;
		
		tooltipHeight += (textLines.size() - 1) * 10;
		if (textLines.size() > titleLinesCount) {
			tooltipHeight += 2;
		}
		
		int backgroundColor = ColorH.rgba2Int(16, 0, 16, 160), borderColor = ColorH.rgba2Int(37, 0, 94, 160);
		int zLevel = 400;
		
		mStack.pushPose();
		Matrix4f mat = mStack.last().pose();
		
		GuiUtils.drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY - 4, tooltipX + tooltipTextWidth + 3, tooltipY - 3, backgroundColor, backgroundColor);
		GuiUtils.drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY + tooltipHeight + 3, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 4, backgroundColor,
				backgroundColor);
		GuiUtils.drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY - 3, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
		GuiUtils.drawGradientRect(mat, zLevel, tooltipX - 4, tooltipY - 3, tooltipX - 3, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
		GuiUtils.drawGradientRect(mat, zLevel, tooltipX + tooltipTextWidth + 3, tooltipY - 3, tooltipX + tooltipTextWidth + 4, tooltipY + tooltipHeight + 3, backgroundColor,
				backgroundColor);
		GuiUtils.drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY - 3 + 1, tooltipX - 3 + 1, tooltipY + tooltipHeight + 3 - 1, borderColor, borderColor);
		GuiUtils.drawGradientRect(mat, zLevel, tooltipX + tooltipTextWidth + 2, tooltipY - 3 + 1, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 3 - 1, borderColor,
				borderColor);
		GuiUtils.drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY - 3, tooltipX + tooltipTextWidth + 3, tooltipY - 3 + 1, borderColor, borderColor);
		GuiUtils.drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY + tooltipHeight + 2, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 3, borderColor, borderColor);
		
		IRenderTypeBuffer.Impl renderType = IRenderTypeBuffer.immediate(Tessellator.getInstance().getBuilder());
		mStack.translate(0, 0, zLevel);
		
		for (int i = 0; i < textLines.size(); ++i) {
			ITextProperties line = textLines.get(i);
			if (line != null) {
				font.drawInBatch(LanguageMap.getInstance().getVisualOrder(line), tooltipX + (i == 0 ? 2 : 1), tooltipY + (i == 0 ? 3 : 0), -1, true, mat, renderType, false, 0,
						15728880);
			}
			
			tooltipY += i + 1 == titleLinesCount ? 17 : 10;
		}
		
		renderType.endBatch();
		mStack.popPose();
		
		RenderSystem.enableDepthTest();
		RenderSystem.enableRescaleNormal();
	}
}
