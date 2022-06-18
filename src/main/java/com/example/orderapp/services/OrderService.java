package com.example.orderapp.services;

import com.example.orderapp.models.OrderState;
import com.example.orderapp.models.Pizza;
import com.example.orderapp.repositories.OrderRepository;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class OrderService implements OrderRepository {


    @Override
    public boolean OrderAPizza(Long userId, List<String> pizzaNameList) {
        try {
            String url = "http://localhost:8080/pizzaapp/create-order";
            HttpClient client= new DefaultHttpClient();
            HttpPost request = new HttpPost(url);
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("userId", userId.toString()));
            pairs.add(new BasicNameValuePair("pizzaNames", String.join(",", pizzaNameList)));
            request.setEntity(new UrlEncodedFormEntity(pairs));


            HttpResponse response = client.execute(request);
            var responseEntity = response.getEntity();
            var result = Long.valueOf(responseEntity.toString());

            if (result != 0) {
                ScheduleOrderStateChange(result, OrderState.READY);
            }

           return result != 0;
        } catch (IOException ex) {
            return false;
        }
    }

    @Override
    public void ChangeOrderState(Long orderId, OrderState orderState) {
        try {
            String url = "http://localhost:8080/pizzaapp/update-order-state";
            HttpClient client= new DefaultHttpClient();
            HttpPut request = new HttpPut(url);
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("id", orderId.toString()));
            pairs.add(new BasicNameValuePair("orderState", orderState.toString()));
            request.setEntity(new UrlEncodedFormEntity(pairs));


            HttpResponse response = client.execute(request);
            var responseEntity = response.getEntity();
        } catch (Exception ex) {

        }
    }

    public void ScheduleOrderStateChange(Long orderId, OrderState orderState) {
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        ChangeOrderState(orderId, orderState);
                    }
                },
                300000
        );
    }
}
