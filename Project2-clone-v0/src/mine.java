// my version
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class mine {

	private Integer[] locations = new Integer[100];
	public int[] bombLocs = new int[10];

	public void initMineLocs(int numMines) {

		for (int i = 0; i < 100; i++) {
			locations[i] = i;
		}
		ArrayList<Integer> locationsList = new ArrayList<Integer>(
				Arrays.asList(locations));

		Collections.shuffle(locationsList);

		for (int j = 0; j < 10; j++) {
			bombLocs[j] = locationsList.get(j);
		}
	}

	public int[] getMines() {
		return bombLocs;
	}

}