package mrunknown404.primalrework.client.gui.screen;

import java.util.OptionalLong;

import org.apache.commons.lang3.StringUtils;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.DirtMessageScreen;
import net.minecraft.client.gui.screen.EditGamerulesScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.FileUtil;
import net.minecraft.util.datafix.codec.DatapackCodec;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.DynamicRegistries.Impl;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;

public class ScreenCreatePrimalWorld extends Screen {
	private static final ITextComponent GAME_MODEL_LABEL = new TranslationTextComponent("selectWorld.gameMode");
	private static final ITextComponent SEED_LABEL = new TranslationTextComponent("selectWorld.enterSeed");
	private static final ITextComponent SEED_INFO = new TranslationTextComponent("selectWorld.seedInfo");
	private static final ITextComponent NAME_LABEL = new TranslationTextComponent("selectWorld.enterName");
	private static final ITextComponent OUTPUT_DIR_INFO = new TranslationTextComponent("selectWorld.resultFolder");
	private final Screen lastScreen;
	private TextFieldWidget nameEdit;
	private TextFieldWidget seedEdit;
	private String resultFolder;
	private GameMode gameMode = GameMode.SURVIVAL;
	private Difficulty selectedDifficulty = Difficulty.NORMAL;
	private Difficulty effectiveDifficulty = Difficulty.NORMAL;
	private boolean commands;
	private boolean commandsChanged;
	public boolean hardCore;
	private Button createButton;
	private Button difficultyButton;
	private Button gameRulesButton;
	private Button commandsButton;
	private ITextComponent gameModeHelp1;
	private ITextComponent gameModeHelp2;
	private String initName;
	private GameRules gameRules = new GameRules();
	private DimensionGeneratorSettings settings;
	private final Impl impl;
	
	public ScreenCreatePrimalWorld(Screen lastScreen) {
		super(new TranslationTextComponent("selectWorld.create"));
		this.lastScreen = lastScreen;
		this.initName = I18n.get("selectWorld.newWorld");
		this.impl = DynamicRegistries.builtin();
		this.settings = DimensionGeneratorSettings.makeDefault(impl.registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY), impl.registryOrThrow(Registry.BIOME_REGISTRY),
				impl.registryOrThrow(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY));
	}
	
	@Override
	protected void init() {
		minecraft.keyboardHandler.setSendRepeatsToGui(true);
		nameEdit = new TextFieldWidget(font, width / 2 - 100, 60, 200, 20, new TranslationTextComponent("selectWorld.enterName")) {
			@Override
			protected IFormattableTextComponent createNarrationMessage() {
				return super.createNarrationMessage().append(". ").append(new TranslationTextComponent("selectWorld.resultFolder")).append(" ").append(resultFolder);
			}
		};
		
		nameEdit.setValue(initName);
		nameEdit.setResponder((val) -> {
			initName = val;
			createButton.active = !nameEdit.getValue().isEmpty();
			updateResultFolder();
		});
		
		addWidget(nameEdit);
		seedEdit = new TextFieldWidget(font, width / 2 - 100, 120, 200, 20, new TranslationTextComponent("selectWorld.enterSeed"));
		addWidget(seedEdit);
		
		int left = width / 2 - 155;
		int right = width / 2 + 5;
		
		addButton(new Button(left, 160, 150, 20, StringTextComponent.EMPTY, (button) -> {
			switch (gameMode) {
				case SURVIVAL:
					setGameMode(GameMode.HARDCORE);
					break;
				case HARDCORE:
					setGameMode(GameMode.CREATIVE);
					break;
				case CREATIVE:
					setGameMode(GameMode.SURVIVAL);
					break;
			}
			
			button.queueNarration(250);
		}) {
			@Override
			public ITextComponent getMessage() {
				return new TranslationTextComponent("options.generic_value", GAME_MODEL_LABEL,
						new TranslationTextComponent("selectWorld.gameMode." + gameMode.gameType.getName()));
			}
			
			@Override
			protected IFormattableTextComponent createNarrationMessage() {
				return super.createNarrationMessage().append(". ").append(gameModeHelp1).append(" ").append(gameModeHelp2);
			}
		});
		
		commandsButton = addButton(new Button(left, 185, 150, 20, new TranslationTextComponent("selectWorld.allowCommands"), (button) -> {
			commandsChanged = true;
			commands = !commands;
			button.queueNarration(250);
		}) {
			@Override
			public ITextComponent getMessage() {
				return DialogTexts.optionStatus(super.getMessage(), commands && !hardCore);
			}
			
			@Override
			protected IFormattableTextComponent createNarrationMessage() {
				return super.createNarrationMessage().append(". ").append(new TranslationTextComponent("selectWorld.allowCommands.info"));
			}
		});
		
		difficultyButton = addButton(new Button(right, 160, 150, 20, new TranslationTextComponent("options.difficulty"), (button) -> {
			selectedDifficulty = selectedDifficulty.nextById();
			effectiveDifficulty = selectedDifficulty;
			button.queueNarration(250);
		}) {
			@Override
			public ITextComponent getMessage() {
				return (new TranslationTextComponent("options.difficulty")).append(": ").append(effectiveDifficulty.getDisplayName());
			}
		});
		
		gameRulesButton = addButton(new Button(right, 185, 150, 20, new TranslationTextComponent("selectWorld.gameRules"), (button) -> {
			minecraft.setScreen(new EditGamerulesScreen(gameRules.copy(), (val) -> {
				minecraft.setScreen(this);
				val.ifPresent((gameRules) -> {
					this.gameRules = gameRules;
				});
			}));
		}));
		
		createButton = addButton(new Button(left, height - 28, 150, 20, new TranslationTextComponent("selectWorld.create"), (button) -> {
			onCreate();
		}));
		
		createButton.active = !initName.isEmpty();
		addButton(new Button(right, height - 28, 150, 20, DialogTexts.GUI_CANCEL, (button) -> {
			popScreen();
		}));
		
		setInitialFocus(nameEdit);
		setGameMode(gameMode);
		updateResultFolder();
	}
	
	@Override
	public void tick() {
		nameEdit.tick();
		seedEdit.tick();
	}
	
	@Override
	public void render(MatrixStack stack, int mouseX, int mouseY, float tick) {
		renderBackground(stack);
		drawCenteredString(stack, font, title, width / 2, 20, -1);
		drawString(stack, font, SEED_LABEL, width / 2 - 100, 107, -6250336);
		drawString(stack, font, SEED_INFO, width / 2 - 100, 145, -6250336);
		drawString(stack, font, NAME_LABEL, width / 2 - 100, 47, -6250336);
		drawString(stack, font, (new StringTextComponent("")).append(OUTPUT_DIR_INFO).append(" ").append(resultFolder), width / 2 - 100, 85, -6250336);
		nameEdit.render(stack, mouseX, mouseY, tick);
		seedEdit.render(stack, mouseX, mouseY, tick);
		super.render(stack, mouseX, mouseY, tick);
	}
	
	@Override
	public void setFocused(IGuiEventListener gui) {
		if (gui != seedEdit && seedEdit != null && seedEdit.isFocused()) { // Stupid fix
			seedEdit.changeFocus(false);
		}
		
		super.setFocused(gui);
	}
	
	private void updateResultFolder() {
		resultFolder = nameEdit.getValue().trim();
		if (resultFolder.isEmpty()) {
			resultFolder = "World";
		}
		
		try {
			resultFolder = FileUtil.findAvailableName(minecraft.getLevelSource().getBaseDir(), resultFolder, "");
		} catch (@SuppressWarnings("unused") Exception exception1) {
			resultFolder = "World";
			
			try {
				resultFolder = FileUtil.findAvailableName(minecraft.getLevelSource().getBaseDir(), resultFolder, "");
			} catch (Exception exception) {
				throw new RuntimeException("Could not create save folder", exception);
			}
		}
	}
	
	@Override
	public void removed() {
		minecraft.keyboardHandler.setSendRepeatsToGui(false);
	}
	
	private void onCreate() {
		minecraft.forceSetScreen(new DirtMessageScreen(new TranslationTextComponent("createWorld.preparing")));
		WorldSettings worldsettings = new WorldSettings(nameEdit.getValue().trim(), gameMode.gameType, hardCore, effectiveDifficulty, commands && !hardCore,
				hardCore ? new GameRules() : gameRules, DatapackCodec.DEFAULT);
		minecraft.createLevel(resultFolder, worldsettings, impl, settings.withSeed(hardCore, parseSeed()));
	}
	
	private void setGameMode(GameMode gameMode) {
		if (!commandsChanged) {
			commands = gameMode == GameMode.CREATIVE;
		}
		
		if (gameMode == GameMode.HARDCORE) {
			hardCore = true;
			commandsButton.active = false;
			effectiveDifficulty = Difficulty.HARD;
			difficultyButton.active = false;
			gameRulesButton.active = false;
		} else {
			hardCore = false;
			commandsButton.active = true;
			effectiveDifficulty = selectedDifficulty;
			difficultyButton.active = true;
			gameRulesButton.active = true;
		}
		
		this.gameMode = gameMode;
		gameModeHelp1 = new TranslationTextComponent("selectWorld.gameMode." + gameMode.gameType.getName() + ".line1");
		gameModeHelp2 = new TranslationTextComponent("selectWorld.gameMode." + gameMode.gameType.getName() + ".line2");
	}
	
	@Override
	public boolean keyPressed(int key, int p0, int p1) {
		if (super.keyPressed(key, p0, p1)) {
			return true;
		} else if (key != 257 && key != 335) {
			return false;
		} else {
			onCreate();
			return true;
		}
	}
	
	@Override
	public void onClose() {
		popScreen();
	}
	
	private void popScreen() {
		minecraft.setScreen(lastScreen);
	}
	
	private OptionalLong parseSeed() {
		String s = seedEdit.getValue();
		OptionalLong optionallong;
		
		if (StringUtils.isEmpty(s)) {
			return OptionalLong.empty();
		}
		
		OptionalLong optionallong1 = parseLong(s);
		if (optionallong1.isPresent() && optionallong1.getAsLong() != 0) {
			optionallong = optionallong1;
		} else {
			optionallong = OptionalLong.of(s.hashCode());
		}
		
		return optionallong;
	}
	
	private static OptionalLong parseLong(String str) {
		try {
			return OptionalLong.of(Long.parseLong(str));
		} catch (@SuppressWarnings("unused") NumberFormatException e) {
			return OptionalLong.empty();
		}
	}
	
	private static enum GameMode {
		SURVIVAL(GameType.SURVIVAL),
		HARDCORE(GameType.SURVIVAL),
		CREATIVE(GameType.CREATIVE);
		
		private final GameType gameType;
		
		private GameMode(GameType gameType) {
			this.gameType = gameType;
		}
	}
}
