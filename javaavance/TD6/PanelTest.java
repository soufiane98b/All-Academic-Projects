package TD6;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.RandomAccess;

import org.junit.Assert;
import org.junit.Test;

public class PanelTest {
	@Test(expected=IllegalArgumentException.class)
	public void panel1() {
		Panel.panel(5, 1);
	}

	@Test
	public void panelSizes() {
		Assert.assertEquals(3, Panel.panel(0, 2).size());
		Assert.assertEquals(1, Panel.panel(2, 2).size());
		Assert.assertEquals(5, Panel.panel(-2, 2).size());
	}

	@Test
	public void panelIterator() {
		Random random = new Random();
		for(int i=0; i<1000; i++) {
			int start = random.nextInt();
			List<Integer> panel = Panel.panel(start, start + 99);
			Assert.assertEquals(100, panel.size());

			int j = start;
			for(int value: panel) {
				Assert.assertEquals(j++, value);
			}
		}
	}

	@Test
	public void panelIterator2() {
		Random random = new Random();
		for(int i=0; i<1000; i++) {
			int start = random.nextInt();
			List<Integer> panel = Panel.panel(start, start + 73);
			Iterator<Integer> it = panel.listIterator();
			for(int j = start; j <= start + 73; j++) {
				System.out.println("attendu : "+j+", reel : "+(int)it.next());
				Assert.assertEquals(j, (int)it.next());
			}
			Assert.assertFalse(it.hasNext());
		}
	}

	
}
