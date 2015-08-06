package kau.paintnote.model;

import java.util.ArrayList;

/**
 * Created by Jaewook on 2015-06-22.
 */
public class ResponseModel {

    private int code;
    private String message;
    private ArrayList<LineModel> lines;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArrayList<LineModel> getLines() {
        return lines;
    }

    public void setLines(ArrayList<LineModel> lines) {
        this.lines = lines;
    }
}
