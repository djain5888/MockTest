package com.kanishque.mocktest.UI.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kanishque.mocktest.Adapter.ListAdapter;
import com.kanishque.mocktest.Helpers.JsonSingleton;
import com.kanishque.mocktest.Helpers.Url;
import com.kanishque.mocktest.Models.ListItem;
import com.kanishque.mocktest.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListFragment extends Fragment {


    private  final String TAG = getClass().getSimpleName();
    private RecyclerView recyclerView;
    private List<ListItem> data_list;
    private ListAdapter madapter;
    private CardView cardView;
    String gid;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list,container,false);
        gid = getArguments().getString("gid");
        Log.d(TAG, "onCreateView: ListFragment"+gid);
        recyclerView = view.findViewById(R.id.recycler_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        cardView = view.findViewById(R.id.group_card);
        doInBackground();
        return view;


    }

    private void doInBackground() {


        StringRequest stRequest = new StringRequest(Request.Method.POST, Url.URL_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject obj = new JSONObject(response);
                    JSONObject jsonObject1 = obj.getJSONObject("tests");
                    data_list = new ArrayList();
                    Log.i(TAG, "onResponse: RESponse"+response);

                    JSONArray dataArray = jsonObject1.getJSONArray("payload");
                    if (jsonObject1.getString("status").equals("True")) {
                        for (int i = 0; i < dataArray.length(); i++) {

                            JSONObject object = dataArray.getJSONObject(i);
                            ListItem myList = new ListItem(object.getString("test_id"), object.getString("gid"), object.getString("test_name"),object.getString("test_marks"),object.getLong("test_time"));
                            Log.i(TAG, "onResponse: RESPONSE" + myList.toString());
                            data_list.add(myList);
                        }
                        madapter = new ListAdapter(getContext(), data_list);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(madapter);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ERROR",error );

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("gid",gid);


                return params;
            }
        };

        JsonSingleton.getInstance(getContext()).addToQueue(stRequest);

    }
}
