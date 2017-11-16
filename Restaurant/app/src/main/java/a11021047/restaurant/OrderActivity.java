package a11021047.restaurant;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class OrderActivity extends AppCompatActivity {

    ArrayList<String> orders;
    TextView textView;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        textView = findViewById(R.id.text_time);
        listView = findViewById(R.id.orderList);
        orders = new ArrayList<>();
        SharedPreferences sharedPreferences = getSharedPreferences("Data", Context.MODE_PRIVATE);
        String test = sharedPreferences.getString("meal", "");
        ArrayList<String> orders = new ArrayList<>(Arrays.asList(test.split("/")));
        ArrayAdapter<String> list = new ArrayAdapter<>(OrderActivity.this,
                android.R.layout.simple_list_item_1,
                orders);
        listView.setAdapter(list);

//        SharedPreferences sharedPreferences = this.getSharedPreferences("MyData", Context.MODE_PRIVATE);
//        String order_string = sharedPreferences.getString("Orders", "");
//        int size = 0;
//        try {
//            JSONObject object = new JSONObject(order_string);
//            JSONArray array = object.getJSONArray("orders");
//            size = array.length();
//            for (int i = 0; i < size; i++) {
//                String item = array.getString(i);
//                orders.add(item);
//            }
//        }
//        catch (Exception e ) {
//            System.out.println(e.toString());
//        }
//        Set<String> set = sharedPreferences.getStringSet("orders", null);
//        System.out.println(set);
//        List<String> orders = new ArrayList<>(set);
//        System.out.println(orders);
//        int size = orders.size();
////        int size = sharedPreferences.getInt("size", 0);
        textView.setVisibility(View.VISIBLE);
        String text = "Order Size: " + Integer.toString(orders.size()-1);
        textView.setText(text);
//        orders = new ArrayList<>();
//        for (int i = 0; i < size; i++) {
//            orders.add(sharedPreferences.getString("meal_"+Integer.toString(i), "N/A"));
//        }


        ArrayAdapter<String> mylist = new ArrayAdapter<>(this,
            android.R.layout.simple_list_item_1,
            orders);
        listView.setAdapter(mylist);
    }
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//            Intent intent = new Intent(MainActivity.this, ItemsActivity.class);
//            intent.putExtra("course", listView.getItemAtPosition(i).toString());
//            startActivity(intent);

    public void goToMenu(View view) {
        Intent intent = new Intent(OrderActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void sendOrder(View view) {
        textView.setText("SENDING");
        textView.setVisibility(View.VISIBLE);
        SharedPreferences sharedPreferences = getSharedPreferences("Data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("meal", "");
        editor.commit();
        orders = new ArrayList<>();
        ArrayAdapter<String> mylist = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                orders);
        listView.setAdapter(mylist);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://resto.mprog.nl/order";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        parseResponse(response);
                        try {
                            JSONObject object = new JSONObject(response);
                            String text = "Preparation Time: " + Integer.toString(object.getInt("preparation_time"));
                            textView.setText(text);
                            textView.setVisibility(View.VISIBLE);
                        }
                        catch (Exception e) {
                            System.out.println(e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }
        });

        queue.add(stringRequest);

    }
}
