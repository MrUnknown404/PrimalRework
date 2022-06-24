package mrunknown404.primalrework.client.gui.screen;

import java.util.List;
import java.util.Objects;

import com.mojang.blaze3d.matrix.MatrixStack;

import mrunknown404.primalrework.utils.helpers.WordH;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class ScreenNonSupportedMods extends Screen {
	private final List<String> modids;
	private TranslationTextComponent desc, modlistDesc;
	private LoadingEntryList entryList;
	
	public ScreenNonSupportedMods(List<String> modids) {
		super(WordH.translate("screen.non_supported_mods.title").setStyle(Style.EMPTY.withColor(Color.fromLegacyFormat(TextFormatting.RED))));
		this.modids = modids;
	}
	
	@Override
	protected void init() {
		this.desc = WordH.translate("screen.non_supported_mods.explanation");
		this.modlistDesc = WordH.translate("screen.non_supported_mods.modlist");
		
		addButton(new Button(width / 2 - 75, height - 35, 150, 20, WordH.translate("screen.non_supported_mods.accept"), (button) -> minecraft.setScreen(null)));
		entryList = new LoadingEntryList(this);
		children.add(entryList);
		setFocused(entryList);
	}
	
	@Override
	public void render(MatrixStack stack, int mouseX, int mouseY, float tick) {
		renderBackground(stack);
		entryList.render(stack, mouseX, mouseY, tick);
		drawCenteredString(stack, font, title, width / 2, 4, 16777215);
		drawCenteredString(stack, font, desc, width / 2, 14, 16777215);
		drawCenteredString(stack, font, modlistDesc, width / 2, 24, 16777215);
		super.render(stack, mouseX, mouseY, tick);
	}
	
	private static class LoadingEntryList extends ExtendedList<LoadingEntryList.LoadingMessageEntry> {
		private LoadingEntryList(ScreenNonSupportedMods parent) {
			super(parent.minecraft, parent.width, parent.height, 35, parent.height - 50, 2 * parent.minecraft.font.lineHeight + 8);
			parent.modids.forEach(e -> addEntry(new LoadingMessageEntry(new StringTextComponent(e))));
		}
		
		@Override
		protected int getScrollbarPosition() {
			return this.getRight() - 6;
		}
		
		@Override
		public int getRowWidth() {
			return this.width;
		}
		
		private class LoadingMessageEntry extends ExtendedList.AbstractListEntry<LoadingMessageEntry> {
			private final ITextComponent message;
			private final boolean center;
			
			private LoadingMessageEntry(ITextComponent message) {
				this(message, false);
			}
			
			private LoadingMessageEntry(ITextComponent message, boolean center) {
				this.message = Objects.requireNonNull(message);
				this.center = center;
			}
			
			@Override
			public void render(MatrixStack mStack, int entryIdx, int top, int left, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean p_194999_5_,
					float partialTicks) {
				FontRenderer font = Minecraft.getInstance().font;
				List<IReorderingProcessor> strings = font.split(message, width);
				int y = top + 2;
				
				for (int i = 0; i < Math.min(strings.size(), 2); i++) {
					if (center) {
						font.draw(mStack, strings.get(i), left + (width) - font.width(strings.get(i)) / 2F, y, 0xFFFFFF);
					} else {
						font.draw(mStack, strings.get(i), left + 5, y, 0xFFFFFF);
						y += font.lineHeight;
					}
				}
			}
		}
		
	}
}
