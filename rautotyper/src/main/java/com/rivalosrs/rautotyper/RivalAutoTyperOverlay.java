package com.rivalosrs.rautotyper;

import com.openosrs.client.ui.overlay.components.table.TableAlignment;
import com.openosrs.client.ui.overlay.components.table.TableComponent;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.TitleComponent;

import javax.inject.Inject;
import java.awt.*;

public final class RivalAutoTyperOverlay extends OverlayPanel
{
	private final RivalAutoTyperPlugin plugin;

	@Inject
	private RivalAutoTyperOverlay(final RivalAutoTyperPlugin plugin)
	{
		super(plugin);

		this.plugin = plugin;
		setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
		setLayer(OverlayLayer.ALWAYS_ON_TOP);
	}

	@Override
	public Dimension render(final Graphics2D graphics)
	{

		final TableComponent tableComponent = new TableComponent();
		tableComponent.setColumnAlignments(TableAlignment.LEFT, TableAlignment.RIGHT);

		panelComponent.getChildren().add(TitleComponent.builder()
			.text("rAuto Typer")
			.color(Color.GREEN)
			.build());

		tableComponent.addRow("Running:", Boolean.toString(plugin.isRunning()));
		tableComponent.addRow("Message:", Integer.toString(plugin.getCurrentMessageIndex()));

		panelComponent.getChildren().add(tableComponent);

		return super.render(graphics);
	}
}
