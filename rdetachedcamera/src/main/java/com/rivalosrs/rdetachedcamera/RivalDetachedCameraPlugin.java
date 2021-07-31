package com.rivalosrs.rdetachedcamera;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import org.pf4j.Extension;

@Extension
@PluginDescriptor(
	name = "rDetached Camera",
	enabledByDefault = false
)
@Slf4j
public class RivalDetachedCameraPlugin extends Plugin
{
	static final String CONFIG_GROUP = "rivalDetachedCamera";

	@Inject
	private Client client;

	@Override
	protected void startUp()
	{
		client.setOculusOrbState(1);
		client.setOculusOrbNormalSpeed(36);
		client.setComplianceValue("orbInteraction", true);
	}

	@Override
	protected void shutDown()
	{
		client.setOculusOrbState(0);
		client.setOculusOrbNormalSpeed(12);
		client.setComplianceValue("orbInteraction", false);
	}

}
