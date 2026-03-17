package com.erp;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v143.network.Network;

import java.util.Optional;

public class networkHelper {

	private DevTools devTools;

	public networkHelper(ChromeDriver driver) {
		devTools = driver.getDevTools();
		devTools.createSession();
	}

	public void disableNetwork() {
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
				Optional.empty()));

		devTools.send(Network.emulateNetworkConditions(true, // offline
				0, // latency
				0, // download throughput
				0, // upload throughput
				Optional.empty(), // connection type
				Optional.empty(), // packet loss
				Optional.empty(), // packet queue length
				Optional.empty() // packet reordering
		));
	}

	public void enableNetwork() {
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
				Optional.empty()));

		devTools.send(Network.emulateNetworkConditions(false, // online
				0, 100000, 100000, Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));
	}
}