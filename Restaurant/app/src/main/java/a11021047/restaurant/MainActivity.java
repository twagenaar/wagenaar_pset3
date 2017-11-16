package a11021047.restaurant;

import android.content.Context;
import android.content.SharedPreferences;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.android.volley.toolbox.Volley.*;

public class MainActivity extends AppCompatActivity {

    ListView listView;
//    ArrayList<String> orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.coursesList);
//        orders = new ArrayList<String>();

//        Order order = new Order();
//        SharedPreferences sharedPref = this.getSharedPreferences("MyData", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();

//        JSONObject order_object = new JSONObject();
//
//        JSONArray order_list = new JSONArray();
//
//        try {
//            for (int i = 0; i < orders.size(); i++) {
//                JSONObject order = new JSONObject();
//                order.put("order", orders.get(i));
//                order_list.put(i, order);
//            }
//
//            order_object.put("orders", order_list);
//        } catch (JSONException e) {
//            System.out.println(e.toString());
//        }
//        String jsonStr = order_object.toString();
//        System.out.println(jsonStr);

//        Set<String> set = new HashSet<String>();
//        assert orders != null;
//        set.addAll(orders);
//        editor.putStringSet("orders", set);
////        editor.putString("Orders", jsonStr);
//        editor.apply();
//        editor.commit();
//
//        int size = orders.size();
//        editor.putInt("size", size);
//        for (int i = 0; i < size; ) {
//            editor.putString("meal_" + Integer.toString(i), orders.get(i));
//        }
//        editor.commit();
//        editor.putStringSet(orders);
//        Context context = getActivity();
//        SharedPreferences sharedPref = context.getSharedPreferences(
//                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
//        SharedPreferences sharedPref = MainActivity().getPreferences(Context.MODE_PRIVATE);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://resto.mprog.nl/";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + "categories",
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
            JSONArray array = object.getJSONArray("categories");
            ArrayList<String> myStringArray = new ArrayList<>();

            for (int i = 0; i < array.length(); i++) {
                String item = array.getString(i);
                myStringArray.add(item);

            }
            System.out.println(myStringArray);
            ArrayAdapter<String> list = new ArrayAdapter<>(MainActivity.this,
                    android.R.layout.simple_list_item_1,
                    myStringArray);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(MainActivity.this, ItemsActivity.class);
                    intent.putExtra("course", listView.getItemAtPosition(i).toString());
                    startActivity(intent);
                }
            });
            listView.setAdapter(list);

        }
        catch(Exception e) {
            System.out.println(e.toString());
        }
    }

    public void goToOrder(View view) {

        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }
}
