package user.story.clustering;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KMeans {
	private static final int NUM_CLUSTERS = 10;
	private static final int TOTAL_DATA = 3276;
	public static List<Double> SAMPLES = new ArrayList<Double>();
	private static ArrayList<Data> dataSet = new ArrayList<Data>();
	private static ArrayList<Centroid> centroids = new ArrayList<Centroid>();

	public KMeans() throws IOException {
		initialize();
		kMeansCluster();
	}

	private static void initialize() {
		centroids.add(new Centroid(1.0));
		centroids.add(new Centroid(2.0));
		centroids.add(new Centroid(3.0));
		centroids.add(new Centroid(4.0));
		centroids.add(new Centroid(5.0));
		centroids.add(new Centroid(6.0));
		centroids.add(new Centroid(7.0));
		centroids.add(new Centroid(8.0));
		centroids.add(new Centroid(9.0));
		centroids.add(new Centroid(10.0));
	}

	private static void kMeansCluster() throws IOException {
		final double bigNumber = Math.pow(10, 10);
		double minimum = bigNumber;
		double distance = 0.0;
		int sampleNumber = 0;
		int cluster = 0;
		boolean isStillMoving = true;
		Data newData = null;
		
		
		// Add in new data and recalculate centroids
		while (dataSet.size() < TOTAL_DATA) {
			newData = new Data(SAMPLES.get(sampleNumber));
			dataSet.add(newData);
			minimum = bigNumber;
			for (int i = 1; i < NUM_CLUSTERS; i++) {
				distance = distance(newData, centroids.get(i));
				if (distance < minimum) {
					minimum = distance;
					cluster = i;
				}
			}
			newData.setValue(cluster);
			
			// calculate new centroids
			for (int i = 0; i < NUM_CLUSTERS; i++) {
				int total = 0;
				int totalInCluster = 0;
				for (int j = 0; j < dataSet.size(); j++) {
					if (dataSet.get(j).getValue() == i) {
						total += dataSet.get(j).getValue();
						totalInCluster++;
					}
				}
				if (totalInCluster > 0) {
					double value = centroids.get(i).getValue();
					double centroidValue = value / totalInCluster;
					centroids.get(i).setValue(centroidValue);
				}
			}
			sampleNumber++;
		}

		// Shift centroids
		while (isStillMoving) {
			// calculate new centroids.
			for (int i = 0; i < NUM_CLUSTERS; i++) {
				int total = 0;
				int totalInCluster = 0;
				for (int j = 0; j < dataSet.size(); j++) {
					if (dataSet.get(j).getValue() == i) {
						total += dataSet.get(j).getValue();
						totalInCluster++;
					}
				}
				if (totalInCluster > 0) {
					double centroidValue = total / totalInCluster;
					centroids.get(i).setValue(centroidValue);
				}
			}

			isStillMoving = false;

			for (int i = 0; i < dataSet.size(); i++) {
				Data tempData = dataSet.get(i);
				minimum = bigNumber;
				for (int j = 0; j < NUM_CLUSTERS; j++) {
					distance = distance(tempData, centroids.get(j));
					if (distance < minimum) {
						minimum = distance;
						cluster = j;
					}
				}

				String output = "Cluster " + i + ": User Story ID: " + tempData.getValue();
			    BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", true));
			    writer.newLine();
			    writer.append(output);
			    writer.close();
			    System.out.println(output);
			    
				tempData.setValue(cluster);
				if (tempData.getValue() != cluster) {
					tempData.setValue(cluster);
					isStillMoving = true;
				}
			}
		}
	}

	private static double distance(Data d, Centroid c) {
		return Math.sqrt(Math.pow((c.getValue() - d.getValue()), 2) + Math.pow((c.getValue() - d.getValue()), 2));
	}
}