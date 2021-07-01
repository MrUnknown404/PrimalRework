package mrunknown404.primalrework.client.gui;

import java.util.ArrayDeque;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.toasts.AdvancementToast;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.gui.toasts.RecipeToast;
import net.minecraft.client.gui.toasts.ToastGui;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class GuiNoToast extends ToastGui {
	
	public GuiNoToast(Minecraft mc) {
		super(mc);
		ObfuscationReflectionHelper.setPrivateValue(ToastGui.class, this, new BlockedDeque(), "queued");
	}
	
	private static class BlockedDeque extends ArrayDeque<IToast> {
		private static final long serialVersionUID = -4587998326350584733L;
		
		private static boolean isBlocked(IToast toast) {
			return toast instanceof AdvancementToast || toast instanceof RecipeToast;
		}
		
		@Override
		public void addFirst(IToast t) {
			if (isBlocked(t)) {
				return;
			}
			
			super.addFirst(t);
		}
		
		@Override
		public void addLast(IToast t) {
			if (isBlocked(t)) {
				return;
			}
			
			super.addLast(t);
		}
		
		@Override
		public boolean add(IToast t) {
			addLast(t);
			return !isBlocked(t);
		}
		
		@Override
		public boolean offerFirst(IToast t) {
			addFirst(t);
			return !isBlocked(t);
		}
		
		@Override
		public boolean offerLast(IToast t) {
			addLast(t);
			return !isBlocked(t);
		}
	}
}
