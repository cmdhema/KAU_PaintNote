package kau.paintnote;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import kau.paintnote.model.ResponseModel;

@EActivity(R.layout.activity_notelist)
public class NoteListActivity extends ActionBarActivity {

    @Extra
    String userId;
    @ViewById(R.id.lv_name)
    ListView listView;

    ArrayAdapter<String> adapter;
    ArrayList<String> noteName;

    @AfterViews
    void init() {
        Log.i("NoteListActivity", GlobalValue.serverURL + "/note/" + userId);
        NoteRequest request = new NoteRequest(GlobalValue.serverURL + "/note/" + userId, new ArrayList<ResponseModel>());
        request.setOnGetMethodProcessListener(new GetNetworkRequest.OnGetMethodProcessListener<ArrayList<ResponseModel>>() {
            @Override
            public void onGetMethodProcessSuccess(GetNetworkRequest<ArrayList<ResponseModel>> request) {
                if ( request.getResult().get(0).getCode() == 200 ) {
                    GlobalValue.lineList = request.getResult();
                    noteName = new ArrayList<>();
                    for ( int i = 0; i < request.getResult().get(0).getLines().size(); i++ ) {
                        noteName.add(request.getResult().get(0).getLines().get(i).getName());
                    }
                    adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_single_choice, noteName) {
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);
                            TextView text = (TextView) view.findViewById(android.R.id.text1);
                            text.setTextColor(Color.BLACK);
                            return view;
                        }
                    };
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onGetMethodProcessError(GetNetworkRequest<ArrayList<ResponseModel>> request) {

            }
        });

        NetworkModel.getInstance().getNetworkData(this, request);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                Intent intent = new Intent(NoteListActivity.this, NoteActivity_.class);
                intent.putExtra("index", index);
                startActivity(intent);
            }
        });
    }

    @Click(R.id.btn_refresh)
    void refresh() {
        NoteRequest request = new NoteRequest(GlobalValue.serverURL + "/note/" + userId, new ArrayList<ResponseModel>());
        request.setOnGetMethodProcessListener(new GetNetworkRequest.OnGetMethodProcessListener<ArrayList<ResponseModel>>() {
            @Override
            public void onGetMethodProcessSuccess(GetNetworkRequest<ArrayList<ResponseModel>> request) {
                if ( request.getResult().get(0).getCode() == 200 ) {
                    GlobalValue.lineList = request.getResult();
                    noteName.clear();
                    for ( int i = 0; i < request.getResult().get(0).getLines().size(); i++ ) {
                        noteName.add(request.getResult().get(0).getLines().get(i).getName());
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onGetMethodProcessError(GetNetworkRequest<ArrayList<ResponseModel>> request) {

            }
        });

        NetworkModel.getInstance().getNetworkData(this, request);

    }
}
