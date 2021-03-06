package mrunknown404.primalrework.client.gui.screen;

import java.util.HashMap;
import java.util.Map;

import com.mojang.blaze3d.matrix.MatrixStack;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.client.gui.GuiQuestTree;
import mrunknown404.primalrework.client.gui.widget.QuestInfoList;
import mrunknown404.primalrework.client.gui.widget.QuestTabList;
import mrunknown404.primalrework.init.InitQuests;
import mrunknown404.primalrework.quests.Quest;
import mrunknown404.primalrework.quests.QuestTab;
import mrunknown404.primalrework.utils.MathH;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class ScreenQuestMenu extends Screen {
	private static final ResourceLocation BG = new ResourceLocation(PrimalRework.MOD_ID, "textures/gui/quest/quest_menu_background.png");
	public static final ResourceLocation QUEST_CHECKMARK = new ResourceLocation(PrimalRework.MOD_ID, "textures/gui/quest/quest_icon_checkmark.png");
	
	public static final ResourceLocation QUEST_ICON = new ResourceLocation(PrimalRework.MOD_ID, "textures/gui/quest/quest_icon.png");
	public static final ResourceLocation QUEST_ICON_HOVER = new ResourceLocation(PrimalRework.MOD_ID, "textures/gui/quest/quest_icon_hover.png");
	public static final ResourceLocation QUEST_ICON_SELECTED = new ResourceLocation(PrimalRework.MOD_ID, "textures/gui/quest/quest_icon_selected.png");
	public static final ResourceLocation QUEST_ICON_SELECTED_HOVER = new ResourceLocation(PrimalRework.MOD_ID, "textures/gui/quest/quest_icon_selected_hover.png");
	public static final ResourceLocation QUEST_END_ICON = new ResourceLocation(PrimalRework.MOD_ID, "textures/gui/quest/quest_end_icon.png");
	public static final ResourceLocation QUEST_END_ICON_HOVER = new ResourceLocation(PrimalRework.MOD_ID, "textures/gui/quest/quest_end_icon_hover.png");
	public static final ResourceLocation QUEST_END_ICON_SELECTED = new ResourceLocation(PrimalRework.MOD_ID, "textures/gui/quest/quest_end_icon_selected.png");
	public static final ResourceLocation QUEST_END_ICON_SELECTED_HOVER = new ResourceLocation(PrimalRework.MOD_ID, "textures/gui/quest/quest_end_icon_selected_hover.png");
	
	private static final Map<Item, GuiQuestTree> TREE_MAP = new HashMap<Item, GuiQuestTree>();
	private QuestTabList tabList;
	private QuestInfoList questInfo;
	private static float scale = 1f;
	
	public static Item selectedTab;
	
	public ScreenQuestMenu() {
		super(new TranslationTextComponent("screen.quest.menu.title"));
	}
	
	@Override
	protected void init() {
		tabList = new QuestTabList(this, minecraft);
		children.add(tabList);
		TREE_MAP.clear();
		questInfo = null;
		
		for (QuestTab tab : InitQuests.getTabs()) {
			TREE_MAP.put(tab.getIcon().getItem(), new GuiQuestTree(this, minecraft, tab));
		}
		scale = 1;
		
		selectedTab = InitQuests.TAB_STAGE_0.getIcon().getItem();
	}
	
	@Override
	public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
		minecraft.getTextureManager().bind(BG);
		blit(stack, 0, 0, 0, 0, width, height, width, height);
		
		stack.scale(getScale(), getScale(), 1);
		TREE_MAP.get(selectedTab).render(stack, mouseX, mouseY, partialTicks);
		stack.scale(1f / getScale(), 1f / getScale(), 1);
		
		tabList.render(stack, mouseX, mouseY, partialTicks);
		
		if (questInfo != null) {
			questInfo.render(stack, mouseX, mouseY, partialTicks);
		}
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int partial) {
		boolean flag = tabList.mouseClicked(mouseX, mouseY, partial);
		if (flag) {
			return true;
		} else if (mouseX > 39) {
			if (questInfo == null) {
				return TREE_MAP.get(selectedTab).mouseClicked(mouseX, mouseY, partial);
			} else if (mouseY < height - QuestInfoList.getInfoHeight(this)) {
				return TREE_MAP.get(selectedTab).mouseClicked(mouseX, mouseY, partial);
			}
			
			questInfo.mouseClicked(mouseX, mouseY, partial);
		}
		
		return false;
	}
	
	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int partial, double moveX, double moveY) {
		boolean flag = tabList.mouseDragged(mouseX, mouseY, partial, moveX, moveY);
		if (flag) {
			return true;
		} else if (mouseX > 39) {
			if (questInfo == null) {
				return TREE_MAP.get(selectedTab).mouseDragged(mouseX, mouseY, partial, moveX, moveY);
			} else if (mouseY < height - QuestInfoList.getInfoHeight(this)) {
				return TREE_MAP.get(selectedTab).mouseDragged(mouseX, mouseY, partial, moveX, moveY);
			}
			questInfo.mouseDragged(mouseX, mouseY, partial, moveX, moveY);
		}
		
		return false;
	}
	
	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double direction) {
		if (mouseX <= 39) {
			return tabList.mouseScrolled(mouseX, mouseY, direction);
		} else if (questInfo != null && mouseY >= height - QuestInfoList.getInfoHeight(this)) {
			return questInfo.mouseScrolled(mouseX, mouseY, direction);
		}
		
		scale = MathH.clamp(scale + (float) direction / 20f * (float) minecraft.options.mouseWheelSensitivity, 0.5f, 2f);
		return true;
	}
	
	public void setQuestInfo(Quest quest) {
		if (questInfo == null) {
			questInfo = new QuestInfoList(this, minecraft, quest);
		} else if (questInfo.isQuest(quest)) {
			questInfo = null;
		} else {
			questInfo = new QuestInfoList(this, minecraft, quest);
		}
	}
	
	public QuestInfoList getQuestInfo() {
		return questInfo;
	}
	
	public static float getScale() {
		return scale;
	}
}
