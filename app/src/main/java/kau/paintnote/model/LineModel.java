package kau.paintnote.model;

import java.util.ArrayList;
import java.util.List;

import kau.paintnote.LineData;

/**
 * Created by Jaewook on 2015-06-22.
 */
public class LineModel {

    private String name;
    private ArrayList<LineData> lines;
    //	private double scale;
    private String userId;

//	public LineModel(String name, ArrayList<LineData> lines, double scale, String userId) {
//		this.name = name;
//		this.scale = scale;
//		this.userId = userId;
//		this.lines = lines;
//	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<LineData> getLines() {
        return lines;
    }

    public void setLines(ArrayList<LineData> lines) {
        this.lines = lines;
    }

//	public double getScale() {
//		return scale;
//	}
//
//	public void setScale(double scale) {
//		this.scale = scale;
//	}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
