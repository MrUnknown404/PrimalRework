package mrunknown404.primalrework.client.gui;

import java.io.IOException;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;

import mrunknown404.primalrework.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiCustomizeWorldScreen;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiCreatePrimalWorld extends GuiScreen {
	private final GuiScreen parentScreen;
	private GuiTextField worldNameField;
	private GuiTextField worldSeedField;
	private String saveDirName;
	private String gameMode = "survival";
	private String savedGameMode;
	private boolean generateStructuresEnabled = true;
	private boolean allowCheats;
	private boolean allowCheatsWasSetByUser;
	private boolean bonusChestEnabled;
	private boolean hardCoreMode;
	private boolean alreadyGenerated;
	private boolean inMoreWorldOptionsDisplay;
	private GuiButton btnGameMode;
	private GuiButton btnMoreOptions;
	private GuiButton btnMapFeatures;
	private GuiButton btnBonusItems;
	private GuiButton btnMapType;
	private GuiButton btnAllowCommands;
	private GuiButton btnCustomizeType;
	private String gameModeDesc1;
	private String gameModeDesc2;
	private String worldSeed;
	private String worldName;
	private int selectedIndex;
	String chunkProviderSettingsJson = "";
	
	private static final String[] DISALLOWED_FILENAMES = new String[] { "CON", "COM", "PRN", "AUX", "CLOCK$", "NUL", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8",
			"COM9", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9" };
	
	public GuiCreatePrimalWorld(GuiScreen parent) {
		parentScreen = parent;
		worldSeed = "";
		worldName = I18n.format("selectWorld.newWorld");
	}
	
	@Override
	public void updateScreen() {
		worldNameField.updateCursorCounter();
		worldSeedField.updateCursorCounter();
	}
	
	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		buttonList.clear();
		buttonList.add(new GuiButton(0, width / 2 - 155, height - 28, 150, 20, I18n.format("selectWorld.create")));
		buttonList.add(new GuiButton(1, width / 2 + 5, height - 28, 150, 20, I18n.format("gui.cancel")));
		btnGameMode = addButton(new GuiButton(2, width / 2 - 75, 115, 150, 20, I18n.format("selectWorld.gameMode")));
		btnMoreOptions = addButton(new GuiButton(3, width / 2 - 75, 187, 150, 20, I18n.format("selectWorld.moreWorldOptions")));
		btnMapFeatures = addButton(new GuiButton(4, width / 2 - 155, 100, 150, 20, I18n.format("selectWorld.mapFeatures")));
		btnMapFeatures.visible = false;
		btnBonusItems = addButton(new GuiButton(7, width / 2 + 5, 151, 150, 20, I18n.format("selectWorld.bonusItems")));
		btnBonusItems.visible = false;
		btnMapType = addButton(new GuiButton(5, width / 2 + 5, 100, 150, 20, I18n.format("selectWorld.mapType")));
		btnMapType.visible = false;
		btnAllowCommands = addButton(new GuiButton(6, width / 2 - 155, 151, 150, 20, I18n.format("selectWorld.allowCommands")));
		btnAllowCommands.visible = false;
		btnCustomizeType = addButton(new GuiButton(8, width / 2 + 5, 120, 150, 20, I18n.format("selectWorld.customizeType")));
		btnCustomizeType.visible = false;
		worldNameField = new GuiTextField(9, fontRenderer, width / 2 - 100, 60, 200, 20);
		worldNameField.setFocused(true);
		worldNameField.setText(worldName);
		worldSeedField = new GuiTextField(10, fontRenderer, width / 2 - 100, 60, 200, 20);
		worldSeedField.setText(worldSeed);
		showMoreWorldOptions(inMoreWorldOptionsDisplay);
		calcSaveDirName();
		updateDisplayState();
	}
	
	private void calcSaveDirName() {
		saveDirName = worldNameField.getText().trim();
		
		for (char c0 : ChatAllowedCharacters.ILLEGAL_FILE_CHARACTERS) {
			saveDirName = saveDirName.replace(c0, '_');
		}
		
		if (StringUtils.isEmpty(saveDirName)) {
			saveDirName = "World";
		}
		
		saveDirName = getUncollidingSaveDirName(mc.getSaveLoader(), saveDirName);
	}
	
	private void updateDisplayState() {
		btnGameMode.displayString = I18n.format("selectWorld.gameMode") + ": " + I18n.format("selectWorld.gameMode." + gameMode);
		gameModeDesc1 = I18n.format("selectWorld.gameMode." + gameMode + ".line1");
		gameModeDesc2 = I18n.format("selectWorld.gameMode." + gameMode + ".line2");
		btnMapFeatures.displayString = I18n.format("selectWorld.mapFeatures") + " ";
		
		if (generateStructuresEnabled) {
			btnMapFeatures.displayString = btnMapFeatures.displayString + I18n.format("options.on");
		} else {
			btnMapFeatures.displayString = btnMapFeatures.displayString + I18n.format("options.off");
		}
		
		btnBonusItems.displayString = I18n.format("selectWorld.bonusItems") + " ";
		
		if (bonusChestEnabled && !hardCoreMode) {
			btnBonusItems.displayString = btnBonusItems.displayString + I18n.format("options.on");
		} else {
			btnBonusItems.displayString = btnBonusItems.displayString + I18n.format("options.off");
		}
		
		btnMapType.displayString = I18n.format("selectWorld.mapType") + " " + I18n.format(WorldType.WORLD_TYPES[selectedIndex].getTranslationKey());
		btnAllowCommands.displayString = I18n.format("selectWorld.allowCommands") + " ";
		
		if (allowCheats && !hardCoreMode) {
			btnAllowCommands.displayString = btnAllowCommands.displayString + I18n.format("options.on");
		} else {
			btnAllowCommands.displayString = btnAllowCommands.displayString + I18n.format("options.off");
		}
	}
	
	private static String getUncollidingSaveDirName(ISaveFormat saveLoader, String name) {
		name = name.replaceAll("[\\./\"]", "_");
		
		for (String s : DISALLOWED_FILENAMES) {
			if (name.equalsIgnoreCase(s)) {
				name = "_" + name + "_";
			}
		}
		
		while (saveLoader.getWorldInfo(name) != null) {
			name = name + "-";
		}
		
		return name;
	}
	
	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.enabled) {
			if (button.id == 1) {
				mc.displayGuiScreen(parentScreen);
			} else if (button.id == 0) {
				mc.displayGuiScreen((GuiScreen) null);
				
				if (alreadyGenerated) {
					return;
				}
				
				alreadyGenerated = true;
				long i = (new Random()).nextLong();
				String s = worldSeedField.getText();
				
				if (!StringUtils.isEmpty(s)) {
					try {
						long j = Long.parseLong(s);
						
						if (j != 0L) {
							i = j;
						}
					} catch (NumberFormatException var7) {
						i = (long) s.hashCode();
					}
				}
				
				WorldType.WORLD_TYPES[selectedIndex].onGUICreateWorldPress();
				
				WorldSettings worldsettings = new WorldSettings(i, GameType.getByName(gameMode), generateStructuresEnabled, hardCoreMode, WorldType.WORLD_TYPES[selectedIndex]);
				worldsettings.setGeneratorOptions(chunkProviderSettingsJson);
				
				if (bonusChestEnabled && !hardCoreMode) {
					worldsettings.enableBonusChest();
				}
				
				if (allowCheats && !hardCoreMode) {
					worldsettings.enableCommands();
				}
				
				mc.launchIntegratedServer(saveDirName, worldNameField.getText().trim(), worldsettings);
			} else if (button.id == 3) {
				toggleMoreWorldOptions();
			} else if (button.id == 2) {
				if ("survival".equals(gameMode)) {
					if (!allowCheatsWasSetByUser) {
						allowCheats = false;
					}
					
					hardCoreMode = false;
					gameMode = "hardcore";
					hardCoreMode = true;
					btnAllowCommands.enabled = false;
					btnBonusItems.enabled = false;
					updateDisplayState();
				} else if ("hardcore".equals(gameMode)) {
					if (!allowCheatsWasSetByUser) {
						allowCheats = true;
					}
					
					hardCoreMode = false;
					gameMode = "creative";
					updateDisplayState();
					hardCoreMode = false;
					btnAllowCommands.enabled = true;
					btnBonusItems.enabled = true;
				} else {
					if (!allowCheatsWasSetByUser) {
						allowCheats = false;
					}
					
					gameMode = "survival";
					updateDisplayState();
					btnAllowCommands.enabled = true;
					btnBonusItems.enabled = true;
					hardCoreMode = false;
				}
				
				updateDisplayState();
			} else if (button.id == 4) {
				generateStructuresEnabled = !generateStructuresEnabled;
				updateDisplayState();
			} else if (button.id == 7) {
				bonusChestEnabled = !bonusChestEnabled;
				updateDisplayState();
			} else if (button.id == 5) {
				++selectedIndex;
				
				if (selectedIndex >= WorldType.WORLD_TYPES.length) {
					selectedIndex = 0;
				}
				
				while (!canSelectCurWorldType()) {
					++selectedIndex;
					
					if (selectedIndex >= WorldType.WORLD_TYPES.length) {
						selectedIndex = 0;
					}
				}
				
				chunkProviderSettingsJson = "";
				updateDisplayState();
				showMoreWorldOptions(inMoreWorldOptionsDisplay);
			} else if (button.id == 6) {
				allowCheatsWasSetByUser = true;
				allowCheats = !allowCheats;
				updateDisplayState();
			} else if (button.id == 8) {
				onCustomizeButton(mc, WorldType.WORLD_TYPES[selectedIndex], this);
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	private void onCustomizeButton(Minecraft mc, WorldType type, GuiCreatePrimalWorld guiCreateWorld) {
		if (type == WorldType.FLAT) {
			mc.displayGuiScreen(new GuiCreatePrimalFlatWorld(guiCreateWorld, guiCreateWorld.chunkProviderSettingsJson));
		} else if (type == WorldType.CUSTOMIZED) {
			mc.displayGuiScreen(new GuiCustomizeWorldScreen(guiCreateWorld, guiCreateWorld.chunkProviderSettingsJson));
		}
	}
	
	private boolean canSelectCurWorldType() {
		WorldType worldtype = WorldType.WORLD_TYPES[selectedIndex];
		
		if (worldtype != null && worldtype.canBeCreated()) {
			return worldtype == WorldType.DEBUG_ALL_BLOCK_STATES ? isShiftKeyDown() : true;
		} else {
			return false;
		}
	}
	
	private void toggleMoreWorldOptions() {
		showMoreWorldOptions(!inMoreWorldOptionsDisplay);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (worldNameField.isFocused() && !inMoreWorldOptionsDisplay) {
			worldNameField.textboxKeyTyped(typedChar, keyCode);
			worldName = worldNameField.getText();
		} else if (worldSeedField.isFocused() && inMoreWorldOptionsDisplay) {
			worldSeedField.textboxKeyTyped(typedChar, keyCode);
			worldSeed = worldSeedField.getText();
		}
		
		if (keyCode == 28 || keyCode == 156) {
			actionPerformed(buttonList.get(0));
		}
		
		(buttonList.get(0)).enabled = !worldNameField.getText().isEmpty();
		calcSaveDirName();
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
		if (inMoreWorldOptionsDisplay) {
			worldSeedField.mouseClicked(mouseX, mouseY, mouseButton);
		} else {
			worldNameField.mouseClicked(mouseX, mouseY, mouseButton);
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		drawCenteredString(fontRenderer, I18n.format("selectWorld.create"), width / 2, 20, -1);
		
		if (inMoreWorldOptionsDisplay) {
			drawString(fontRenderer, I18n.format("selectWorld.enterSeed"), width / 2 - 100, 47, -6250336);
			drawString(fontRenderer, I18n.format("selectWorld.seedInfo"), width / 2 - 100, 85, -6250336);
			
			if (btnMapFeatures.visible) {
				drawString(fontRenderer, I18n.format("selectWorld.mapFeatures.info"), width / 2 - 150, 122, -6250336);
			}
			
			if (btnAllowCommands.visible) {
				int y = 151;
				if (WorldType.WORLD_TYPES[selectedIndex] == Main.PRIMAL_WORLD) {
					y = 100;
				}
				
				drawString(fontRenderer, I18n.format("selectWorld.allowCommands.info"), width / 2 - 150, (79 + y), -6250336);
			}
			
			worldSeedField.drawTextBox();
			
			if (WorldType.WORLD_TYPES[selectedIndex].hasInfoNotice()) {
				fontRenderer.drawSplitString(I18n.format(WorldType.WORLD_TYPES[selectedIndex].getInfoTranslationKey()), btnMapType.x + 2, btnMapType.y + 22,
						btnMapType.getButtonWidth(), 10526880);
			}
		} else {
			drawString(fontRenderer, I18n.format("selectWorld.enterName"), width / 2 - 100, 47, -6250336);
			drawString(fontRenderer, I18n.format("selectWorld.resultFolder") + " " + saveDirName, width / 2 - 100, 85, -6250336);
			worldNameField.drawTextBox();
			drawString(fontRenderer, gameModeDesc1, width / 2 - 100, 137, -6250336);
			drawString(fontRenderer, gameModeDesc2, width / 2 - 100, 149, -6250336);
		}
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	private void showMoreWorldOptions(boolean toggle) {
		inMoreWorldOptionsDisplay = toggle;
		
		if (WorldType.WORLD_TYPES[selectedIndex] == WorldType.DEBUG_ALL_BLOCK_STATES) {
			btnGameMode.visible = !inMoreWorldOptionsDisplay;
			btnGameMode.enabled = false;
			
			if (savedGameMode == null) {
				savedGameMode = gameMode;
			}
			
			gameMode = "spectator";
			btnMapFeatures.visible = false;
			btnBonusItems.visible = false;
			btnMapType.visible = inMoreWorldOptionsDisplay;
			btnAllowCommands.visible = false;
			btnCustomizeType.visible = false;
		} else if (WorldType.WORLD_TYPES[selectedIndex] == Main.PRIMAL_WORLD) {
			btnGameMode.visible = !inMoreWorldOptionsDisplay;
			btnGameMode.enabled = true;
			
			if (savedGameMode != null) {
				gameMode = savedGameMode;
				savedGameMode = null;
			}
			
			btnAllowCommands.y = 100;
			btnMapFeatures.visible = false;
			btnBonusItems.visible = false;
			btnMapType.visible = inMoreWorldOptionsDisplay;
			btnAllowCommands.visible = inMoreWorldOptionsDisplay;
			btnCustomizeType.visible = false;
		} else {
			btnGameMode.visible = !inMoreWorldOptionsDisplay;
			btnGameMode.enabled = true;
			
			if (savedGameMode != null) {
				gameMode = savedGameMode;
				savedGameMode = null;
			}
			
			btnAllowCommands.y = 151;
			btnMapFeatures.visible = inMoreWorldOptionsDisplay && WorldType.WORLD_TYPES[selectedIndex] != WorldType.CUSTOMIZED;
			btnBonusItems.visible = inMoreWorldOptionsDisplay;
			btnMapType.visible = inMoreWorldOptionsDisplay;
			btnAllowCommands.visible = inMoreWorldOptionsDisplay;
			btnCustomizeType.visible = inMoreWorldOptionsDisplay && WorldType.WORLD_TYPES[selectedIndex].isCustomizable();
		}
		
		updateDisplayState();
		
		if (inMoreWorldOptionsDisplay) {
			btnMoreOptions.displayString = I18n.format("gui.done");
		} else {
			btnMoreOptions.displayString = I18n.format("selectWorld.moreWorldOptions");
		}
	}
}
