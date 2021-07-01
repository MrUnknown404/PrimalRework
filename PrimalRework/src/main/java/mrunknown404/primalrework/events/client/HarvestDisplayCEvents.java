package mrunknown404.primalrework.events.client;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import mrunknown404.primalrework.blocks.utils.StagedBlock;
import mrunknown404.primalrework.client.ColorH;
import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.stage.VanillaRegistry;
import mrunknown404.primalrework.utils.DoubleCache;
import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.MathH;
import mrunknown404.primalrework.utils.RayTraceH;
import mrunknown404.primalrework.utils.enums.EnumToolMaterial;
import mrunknown404.primalrework.utils.enums.EnumToolType;
import net.minecraft.block.AbstractBlock;
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
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.LanguageMap;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.gui.GuiUtils;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class HarvestDisplayCEvents {
	private DoubleCache<Block, Item, List<ITextComponent>> blockCache = new DoubleCache<Block, Item, List<ITextComponent>>();
	
	private static final IFormattableTextComponent YES_MINE = new StringTextComponent("\u2714 ").withStyle(TextFormatting.GREEN),
			NO_MINE = new StringTextComponent("\u274C ").withStyle(TextFormatting.RED);
	
	@SubscribeEvent
	public void onRender(RenderGameOverlayEvent.Post e) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.screen != null) {
			return;
		}
		
		MainWindow w = e.getWindow();
		MatrixStack s = e.getMatrixStack();
		Block b = mc.player.getCommandSenderWorld().getBlockState(RayTraceH.rayTrace(mc.player, e.getPartialTicks()).getBlockPos()).getBlock();
		
		List<ITextComponent> texts = new ArrayList<ITextComponent>();
		
		ItemStack stack = mc.player.getMainHandItem();
		Item item = stack.getItem();
		
		if (blockCache.is(b, item)) {
			texts = blockCache.get();
		} else if (b != null && b != Blocks.AIR) {
			List<HarvestInfo> infos = b instanceof StagedBlock ? new ArrayList<HarvestInfo>(((StagedBlock) b).getHarvest().values()) : VanillaRegistry.getHarvestInfos(b);
			texts.add(new StringTextComponent("    ").append(b.getName()));
			
			if (infos == null) {
				texts.add(new TranslationTextComponent("tooltips.block.unbreakable"));
				GuiUtils.drawHoveringText(s, texts, -8, 0, w.getWidth(), w.getHeight(), -1, mc.font);
				return;
			}
			
			AbstractBlock.Properties prop = ObfuscationReflectionHelper.getPrivateValue(AbstractBlock.class, b, "properties");
			float hardness = MathH.roundTo(ObfuscationReflectionHelper.getPrivateValue(AbstractBlock.Properties.class, prop, "destroyTime"), 2);
			
			if (hardness != -1) {
				texts.add(new StringTextComponent(((String.valueOf(hardness).split("\\.")[1].length() < 2) ? hardness + "0" : hardness) + " ")
						.append(new TranslationTextComponent("tooltips.block.hardness")));
			} else {
				texts.add(new TranslationTextComponent("tooltips.block.unbreakable"));
			}
			
			boolean isStaged = (item instanceof StagedItem);
			EnumToolType toolType = isStaged ? ((StagedItem) item).toolType : VanillaRegistry.getToolType(item);
			EnumToolMaterial toolMat = isStaged ? ((StagedItem) item).toolMat : VanillaRegistry.getToolMaterial(item);
			
			for (HarvestInfo info : infos) {
				if (info.toolMat != EnumToolMaterial.unbreakable) {
					boolean canMine = info.toolType == EnumToolType.none ? true : info.toolType == toolType && info.toolMat.level <= toolMat.level;
					texts.add((canMine ? YES_MINE : NO_MINE).copy().append(new TranslationTextComponent("tooltips.stat.level").withStyle(TextFormatting.WHITE)
							.append(" " + info.toolMat.level + " " + info.toolType.getName())));
				}
			}
			
			blockCache.set(b, item, texts);
		}
		
		if (!texts.isEmpty()) {
			drawHoveringText(s, texts, mc.font);
			
			Item bitem = b.asItem();
			if (bitem != null) {
				mc.getItemRenderer().blitOffset += 410;
				mc.getItemRenderer().renderGuiItem(new ItemStack(bitem), 4, 3);
				mc.getItemRenderer().blitOffset -= 410;
			}
			
			mc.textureManager.bind(AbstractGui.GUI_ICONS_LOCATION);
		}
	}
	
	@SuppressWarnings("deprecation")
	private static void drawHoveringText(MatrixStack mStack, List<? extends ITextProperties> textLines, FontRenderer font) {
		if (!textLines.isEmpty()) {
			RenderSystem.disableRescaleNormal();
			RenderSystem.disableDepthTest();
			int tooltipTextWidth = 0;
			
			for (ITextProperties textLine : textLines) {
				int textLineWidth = font.width(textLine) + 2;
				if (textLineWidth > tooltipTextWidth) {
					tooltipTextWidth = textLineWidth;
				}
			}
			
			int tooltipX = 4, tooltipY = 0;
			int titleLinesCount = 1, tooltipHeight = 13;
			
			tooltipHeight += (textLines.size() - 1) * 10;
			if (textLines.size() > titleLinesCount) {
				tooltipHeight += 2;
			}
			
			if (tooltipY < 4) {
				tooltipY = 4;
			}
			
			int backgroundColor = ColorH.rgba2Int(16, 0, 16, 30), borderColor = ColorH.rgba2Int(37, 0, 94, 30);
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
			GuiUtils.drawGradientRect(mat, zLevel, tooltipX + tooltipTextWidth + 2, tooltipY - 3 + 1, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 3 - 1,
					borderColor, borderColor);
			GuiUtils.drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY - 3, tooltipX + tooltipTextWidth + 3, tooltipY - 3 + 1, borderColor, borderColor);
			GuiUtils.drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY + tooltipHeight + 2, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 3, borderColor,
					borderColor);
			
			IRenderTypeBuffer.Impl renderType = IRenderTypeBuffer.immediate(Tessellator.getInstance().getBuilder());
			mStack.translate(0, 0, zLevel);
			
			for (int i = 0; i < textLines.size(); ++i) {
				ITextProperties line = textLines.get(i);
				if (line != null) {
					font.drawInBatch(LanguageMap.getInstance().getVisualOrder(line), tooltipX + (i == 0 ? 2 : 1), tooltipY + (i == 0 ? 3 : 0), -1, true, mat, renderType, false,
							0, 15728880);
				}
				
				if (i + 1 == titleLinesCount) {
					tooltipY += 7;
				}
				
				tooltipY += 10;
			}
			
			renderType.endBatch();
			mStack.popPose();
			
			RenderSystem.enableDepthTest();
			RenderSystem.enableRescaleNormal();
		}
	}
}
