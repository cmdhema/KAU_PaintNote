package kau.paintnote;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import kau.paintnote.model.LineModel;
import kau.paintnote.model.ResponseModel;

/**
 * Created by Jaewook on 2015-06-23.
 */
public class NoteRequest extends GetNetworkRequest<ArrayList<ResponseModel>> {

    private String serverUrl;

    public NoteRequest(String serverUrl, ArrayList<ResponseModel> data) {
        super(data);
        this.serverUrl = serverUrl;
    }

    @Override
    public boolean parsingGetRequest(InputStream is, ArrayList<ResponseModel> result) {

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder jsonBuf =  new StringBuilder();
        String line = "";

        try {
            while((line = br.readLine()) != null)
                jsonBuf.append(line);
        } catch (IOException e) {
            e.printStackTrace();
        }

		Log.i("NoteRequest", jsonBuf.toString());
        try {
            JSONObject jData = new JSONObject(jsonBuf.toString());

            ResponseModel resultData = new ResponseModel();
            resultData.setCode(jData.getInt("code"));
            resultData.setMessage(jData.getString("message"));
            if ( resultData.getCode() != 200 )
                return false;

            JSONArray jsonArray = jData.getJSONArray("note");

//			result.add(resultData);
            ArrayList<LineModel> lineList = new ArrayList<>();

            int jsonObjSize = jsonArray.length();

            for (int i = 0; i < jsonObjSize; i++) {

                JSONObject dataObject = jsonArray.getJSONObject(i);

                LineModel lineModel = new LineModel();
                lineModel.setName(dataObject.getString("name"));

                JSONArray dataArray = dataObject.getJSONArray("data");
                int userObjSize = dataArray.length();

                ArrayList<LineData> lines= new ArrayList<>();

                for(int j=0;j<userObjSize;j++) {
                    LineData data = new LineData();
                    JSONObject userObject = dataArray.getJSONObject(j);
                    data.setX(userObject.getInt("x"));
                    data.setX1(userObject.getInt("x1"));
                    data.setY(userObject.getInt("y"));
                    data.setY1(userObject.getInt("y1"));
                    lines.add(data);
                }
                lineModel.setLines(lines);
                lineList.add(lineModel);
            }

            resultData.setLines(lineList);

            result.add(resultData);
        } catch (JSONException je) {
            Log.e("HeartCountRequest", "JSONParsing error", je);
        }

        return true;


    }

    @Override
    public URL getServerURL() {
        try {
            return new URL(serverUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
