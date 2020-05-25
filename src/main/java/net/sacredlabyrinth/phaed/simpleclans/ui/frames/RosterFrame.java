package net.sacredlabyrinth.phaed.simpleclans.ui.frames;

import java.util.Collections;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.Helper;
import net.sacredlabyrinth.phaed.simpleclans.RankPermission;
import net.sacredlabyrinth.phaed.simpleclans.ui.InventoryDrawer;
import net.sacredlabyrinth.phaed.simpleclans.ui.SCComponent;
import net.sacredlabyrinth.phaed.simpleclans.ui.SCComponentImpl;
import net.sacredlabyrinth.phaed.simpleclans.ui.SCFrame;
import net.sacredlabyrinth.phaed.simpleclans.utils.Paginator;
import org.jetbrains.annotations.NotNull;

import static net.sacredlabyrinth.phaed.simpleclans.SimpleClans.lang;

public class RosterFrame extends SCFrame {

	private final Clan subject;
	private Paginator paginator;

	public RosterFrame(Player viewer, SCFrame parent, Clan subject) {
		super(parent, viewer);
		this.subject = subject;
	}

	@Override
	public void createComponents() {
		List<ClanPlayer> allMembers = subject.getLeaders();
		allMembers.addAll(subject.getNonLeaders());
		paginator = new Paginator(getSize() - 9, allMembers.size());

		for (int slot = 0; slot < 9; slot++) {
			if (slot == 2 || slot == 4 || slot == 6 || slot == 7)
				continue;
			add(Components.getPanelComponent(slot));
		}

		add(Components.getBackComponent(getParent(), 2));

		SCComponent invite = new SCComponentImpl(lang("gui.roster.invite.title"),
				Collections.singletonList(lang("gui.roster.invite.lore")), Material.LIME_WOOL, 4);
		invite.setListener(ClickType.LEFT, () -> InventoryDrawer.open(new InviteFrame(this, getViewer())));
		invite.setPermission(ClickType.LEFT, RankPermission.INVITE);
		add(invite);

		add(Components.getPreviousPageComponent(6, this::previousPage));
		add(Components.getNextPageComponent(7, this::nextPage));

		int slot = 9;
		for (int i = paginator.getMinIndex(); paginator.isValidIndex(i); i++) {
			add(Components.getPlayerComponent(this, getViewer(), allMembers.get(i), slot, true));
			slot++;
		}
	}

	private void previousPage() {
		if (paginator.previousPage()) {
			updateFrame();
		}
	}

	private void nextPage() {
		if (paginator.nextPage()) {
			updateFrame();
		}
	}

	private void updateFrame() {
		InventoryDrawer.update(this);
	}

	@Override
	public @NotNull String getTitle() {
		return lang("gui.roster.title", Helper.stripColors(subject.getColorTag()));
	}

	@Override
	public int getSize() {
		return 6 * 9;
	}

}
