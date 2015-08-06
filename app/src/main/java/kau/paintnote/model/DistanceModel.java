package kau.paintnote.model;

import java.util.ArrayList;

public class DistanceModel extends BaseModel {

	public DistanceModel() {
		super("distanceMessage");
	}
	
	public double distanceX;
	public double distanceY;
	public String from;
	
	public ArrayList<Double> distanceXList;
	public ArrayList<Double> distanceYList;

}
