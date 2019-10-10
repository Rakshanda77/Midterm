package com.example.midterm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText editText = findViewById(R.id.editText);
        Button button = findViewById(R.id.button);
        TextView textView = findViewById(R.id.textView);
        Spinner spinner = (Spinner) findViewById(R.id.simpleSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.values, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        //spinner.setOnItemClickListener(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final OkHttpClient client = new OkHttpClient();
                String url;
               // url = "https://learn.operatoroverload.com/rental/operators";
                String input = ((TextView) findViewById(R.id.editText)).getText().toString();
                String URL = HttpUrl.parse("https://learn.operatoroverload.com/rental").newBuilder()
                        .addQueryParameter("value",input).build().toString();

                Request req = new Request.Builder().url(URL).build();

               // Request req = new Request.Builder().url(url).addHeader("parameter",input).build();



                Thread t = new Thread() {
                    @Override
                    public void run() {
                        try {
                            Response response = client.newCall(req).execute();
                            String text = response.body().string();
                          Log.d("response", text);
                       //    JSONArray object = (JSONArray) new JSONTokener(text).nextValue();



                            runOnUiThread(() -> {
                                ((TextView) findViewById(R.id.textView)).setText(text);
                            });
                        } catch (IOException e) {
//                            runOnUiThread(() -> {
//                                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
//                            });

                        }
                    }
                };
            t.start();

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
    }




//    public void onItemSelected(AdapterView<?> parent, View view,
//                               int pos, long id) {
//        // An item was selected. You can retrieve the selected item using
//        parent.getItemAtPosition(pos).toString();
//    }

   // @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//    }
}







