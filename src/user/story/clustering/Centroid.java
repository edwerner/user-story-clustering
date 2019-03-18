package user.story.clustering;

public class Centroid {
	private double value = 0.0;

	public Centroid(double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}