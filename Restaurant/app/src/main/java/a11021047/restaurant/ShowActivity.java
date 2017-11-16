package a11021047.restaurant;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShowActivity extends AppCompatActivity {
    TextView title;
    TextView description;
    ImageView imageView;
    JSONObject object;
    String meal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        title = findViewById(R.id.name);
        description = findViewById(R.id.description);
        imageView = findViewById(R.id.image);

        Intent intent = getIntent();
//        System.out.println(intent.getStringExtra("meal"));
        try {
            object = new JSONObject(intent.getStringExtra("meal"));
//            System.out.println(object.getString("name"));
            meal = object.getString("name");
            title.setText(meal);
            description.setText(object.getString("description"));
            new DownloadImageTask((ImageView) findViewById(R.id.image))
                    .execute(object.getString("image_url"));
//            imageView.setImageURI(Uri.parse(object.getString("image_url")));
//            String url = "https://resto.mprog.nl/menu?category=";
//            RequestQueue queue = Volley.newRequestQueue(this);
//
//            StringRequest stringRequest = new StringRequest(Request.Method.GET, object.getString("image_url"),
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            imageView.setImageURI(Uri.parse(response));
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    System.out.println(error.toString());
//                }
//            });
//
//            queue.add(stringRequest);

//            imageView.setImageDrawable();
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void addOrder(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("Data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String test = sharedPreferences.getString("meal", "");
        ArrayList<String> orders = new ArrayList<>(Arrays.asList(test.split("/")));
        String new_order = "";
        for (int i = 0; i < orders.size(); i++) {
            System.out.println(orders.get(i));
            new_order = new_order + orders.get(i) + "/";
        }
        new_order = new_order + meal;
        editor.putString("meal", new_order);
        editor.apply();
        editor.commit();


//        TextView viewer = findViewById(R.id.textView2);
//        newString = newString + viewer.getText();
//        SharedPreferences prefs = getSharedPreferences("filename", MODE_PRIVATE);
//        SharedPreferences.Editor prefsEditor = prefs.edit();
//        prefsEditor.putString("name", newString);
//        prefsEditor.apply();
//        prefsEditor.commit();
//        String
    }



    public void goToOrder(View view) {

        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }

}
