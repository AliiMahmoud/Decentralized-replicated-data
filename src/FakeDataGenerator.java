package dataManager;

import java.util.Random;

public class FakeDataGenerator {
	private boolean canGenerate = true;

	public boolean CanGenerate() {
		return canGenerate;
	}

	public void setCanGenerateFlag(boolean canGenerate) {
		this.canGenerate = canGenerate;
	}

	public FakeDataGenerator() {
	}

	public String generate() {
		Random r = new Random();
		String returned = "";
		for (int i = 0; i < 3 + r.nextInt(10); ++i) {
			for (int j = 0; j < 4 + r.nextInt(15); ++j) {
				char c = (char) ('a' + r.nextInt(26));
				returned += (c);
			}
			returned += " ";
		}
		return returned;
	}
}
