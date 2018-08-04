package com.example.homepc.country;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    // String URL = "https://restcountries.eu/rest/v2/name/usa?fullText=true";
    String baseUrl = "https://restcountries.eu/rest/v2/name/";
    String lastUrl = "?fullText=true";
    TextView textView ,textView2,textView3,textView4,textView5,textView6, textView7;
    EditText editText;
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);
        textView6 = findViewById(R.id.textView6);
        textView7 = findViewById(R.id.textView7);

        imageButton = findViewById(R.id.imageButton);

        editText = findViewById(R.id.editText);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData();
            }
        });
    }

    private void loadData() {

        String country = editText.getText().toString().trim().toLowerCase();
        String finalUrl = baseUrl + country + lastUrl;

        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, finalUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    editText.getText().clear();
                    JSONArray array = new JSONArray(response);
                    JSONObject jsonObject = array.getJSONObject(0);

                    String alphaCode = jsonObject.getString("alpha2Code");
                    String name = jsonObject.getString("name");
                    String capital = jsonObject.getString("capital");
                    JSONArray currency = jsonObject.getJSONArray("currencies");
                    JSONObject object = currency.getJSONObject(0);
                    String symbol = object.getString("symbol");
                    String code = object.getString("code");
                    String currName = object.getString("name");
                    String area = jsonObject.getString("area");
                    // String flag = jsonObject.getString("flag");

                    String ginal = symbol + code + currName + name + capital + "\n\n" + "\n\n" + alphaCode.toLowerCase();
                    textView.setText(name);
                    textView2.setText(capital);
                   // textView3.setText(ginal);
                    textView4.setText(symbol);
                    textView5.setText(code);
                    textView6.setText(currName);
                    textView7.setText(area + " Sq. Metre");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                try {
//                    JSONArray array = new JSONArray(response);
//                    Log.i("JSON", "Array: " + array);
//
//                    JSONObject jsonObject = array.getJSONObject(1);
//                    Log.i("JSON", "OBJ: " + jsonObject);
//
//                    for (int i = 0; i < array.length(); i++) {
//                        JSONObject o = array.getJSONObject(i);
//                        ListItem item = new ListItem(
//                                o.getString("name"),
//                                o.getString("bio"),
//                                "Release Date: " + o.getString("firstappearance"),
//                                o.getString("imageurl")
////                                "Real Name: " + o.getString("realname")
//                        );
//
//
//                        listItems.add(item);
//                        Log.i("JSON", "OBJ: " + o);
//                    }
//
//                    adapter = new MyAdapter(listItems, getApplicationContext());
//                    recyclerView.setAdapter(adapter);
//                    progressDialog.dismiss();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String err = error.toString();
                Toast.makeText(getApplicationContext(), err, Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
