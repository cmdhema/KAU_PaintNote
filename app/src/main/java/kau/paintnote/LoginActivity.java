package kau.paintnote;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import kau.paintnote.model.ResponseModel;
import kau.paintnote.model.UserModel;

/**
 * Created by Jaewook on 2015-06-22.
 */

@EActivity(R.layout.activity_login)
public class LoginActivity extends Activity {

    @ViewById(R.id.et_id)
    EditText idEt;
    @ViewById(R.id.et_pw)
    EditText passwordEt;
    @ViewById(R.id.et_ip)
    EditText ipEt;

    Gson gson;

    @AfterViews
    void init() {
        gson = new Gson();
    }

    @Click(R.id.btn_login)
    void login() {

        UserModel user = new UserModel(" " + idEt.getText().toString(), " " + passwordEt.getText().toString());
        GlobalValue.serverURL = ipEt.getText().toString();
        String userJson = gson.toJson(user);

        Log.i("TAG", userJson);
        LoginRequest request = new LoginRequest(ipEt.getText().toString(), new ResponseModel(), userJson);
        request.setOnPostMethodProcessListener(new PostNetworkRequest.OnPostMethodProcessListener<ResponseModel>() {
            @Override
            public void onPostMethodProcessSuccess(PostNetworkRequest<ResponseModel> request) {
                if (request.getResult().getCode() == 200) {

                    Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, NoteListActivity_.class);
                    intent.putExtra("userId", idEt.getText().toString());
                    startActivity(intent);
                }
            }

            @Override
            public void onPostMethodProcessError(PostNetworkRequest<ResponseModel> request) {

            }
        });
        NetworkModel.getInstance().sendNetworkData(this, request);
    }
}
