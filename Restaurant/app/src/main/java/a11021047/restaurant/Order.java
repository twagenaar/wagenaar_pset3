package a11021047.restaurant;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Created by Tessa on 15-11-2017.
 */

public class Order {
    private Hashtable<JSONObject,Integer> orderList;

    public Order() {
        orderList = new Hashtable<>();
//        orderList.add(object);
    }

    public void addItem(JSONObject object) {
        orderList.put(object, 1);
    }

    public Hashtable<JSONObject, Integer> getOrderList() {
        return orderList;
    }

    public int getPrice() {
        int price = 0;
        Enumeration<JSONObject> e;
        JSONObject object;
        Integer count;
        for (e = orderList.keys(); e.hasMoreElements();) {
            object = e.nextElement();
            try {
                count = orderList.get(object);
                price += count * object.getInt("price");
            }
            catch (Exception ex) {
                System.out.println(ex.toString());
            }
        }
        return price;
    }
}
