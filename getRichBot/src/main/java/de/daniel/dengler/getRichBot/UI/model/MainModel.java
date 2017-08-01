package de.daniel.dengler.getRichBot.UI.model;

import java.util.ArrayList;
import java.util.List;

import de.daniel.dengler.getRichBot.Watcher;

public class MainModel{
	List<Watcher> activeWatchers = new ArrayList<Watcher>();
	

	public void addWatcher(Watcher w) {
		activeWatchers.add(w);
	}


	public void removeWatcher(Watcher w) {
		activeWatchers.remove(w);
	}

}
