package a11021047.restaurant;

import android.app.Activity;
import android.content.ClipData;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ItemsActivity extends AppCompatActivity {

    ListView listView;
    ArrayList array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        Intent intent = getIntent();
        String course = intent.getStringExtra("course");

        listView = findViewById(R.id.itemsList);

        String url = "https://resto.mprog.nl/menu?category=";
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + course,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }
        });

        queue.add(stringRequest);


    }

    private void parseResponse(String response) {
        try {
            JSONObject object = new JSONObject(response);
            final JSONArray array = object.getJSONArray("items");
            ArrayList<String> myStringArray = new ArrayList<>();

            for (int i = 0; i < array.length(); i++) {
                JSONObject item = array.getJSONObject(i);
                String name = item.getString("name");
                myStringArray.add(name);

            }

            ArrayAdapter<String> list = new ArrayAdapter<>(ItemsActivity.this,
                    android.R.layout.simple_list_item_1,
                    myStringArray);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(ItemsActivity.this, ShowActivity.class);
                    String name = listView.getItemAtPosition(i).toString();
                    try {
                        JSONObject item = array.getJSONObject(i);
                        intent.putExtra("meal", item.toString());
                        startActivity(intent);
                    }
                    catch (Exception e) {
                        System.out.println(e.toString());
                    }
                }
            });
            listView.setAdapter(list);

        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public void goToOrder(View view) {

        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }

}

