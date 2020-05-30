package com.sws.events;

import java.util.EventListener;

public interface SWSEventListener extends EventListener {
	public void lmsTransferEvent(SWSEvent event);
}
