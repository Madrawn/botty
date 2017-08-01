package de.daniel.dengler.getRichBot;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.daniel.dengler.getRichBot.Watcher;
import de.daniel.dengler.getRichBot.UI.LeftListItem;
import de.daniel.dengler.getRichBot.UI.LeftListItemController;
import de.daniel.dengler.getRichBot.UI.model.MainModel;

public class MainModelTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void addWatcherTest() {
		MainModel m = new MainModel();
		Watcher w = new Watcher(new LeftListItem(),new TestWrapper());
		m.addWatcher(w);
		assertTrue(m.activeWatchers.contains(w));
	}
	@Test
	public void removeWatcherTest() {
		MainModel m = new MainModel();
		Watcher w = new Watcher(new LeftListItem(),new TestWrapper());
		m.addWatcher(w);
		m.removeWatcher(w);
		
		assertTrue(!m.activeWatchers.contains(w));
	}

}
