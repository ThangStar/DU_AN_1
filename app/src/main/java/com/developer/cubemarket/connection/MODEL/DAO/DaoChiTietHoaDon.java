package com.developer.cubemarket.connection.MODEL.DAO;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.developer.cubemarket.connection.IResult.IResult_chitiethoadon;
import com.developer.cubemarket.connection.MODEL.KET_NOI_SEVER.HttpsTrustManager;
import com.developer.cubemarket.connection.MODEL.KET_NOI_SEVER.Link;
import com.developer.cubemarket.connection.MODEL.OOP.ChiTietHoaDon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class DaoChiTietHoaDon {
    Context context;
    String TAG="TAG";
    IResult_chitiethoadon mResultCallback = null;

    public DaoChiTietHoaDon(IResult_chitiethoadon resultCallback, Context context) {
        mResultCallback = resultCallback;
        this.context = context;
    }
    public  void getdata_chitiet_hoadon( int mahoadon ,int chucvu,int id){

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest= new StringRequest(Request.Method.POST, Link.getdata_chitiet_hoadon, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                List<ChiTietHoaDon> ee = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0 ; i<jsonArray.length();i++){
                        try {
                            JSONObject jsonObject= jsonArray.getJSONObject(i);
                            int masanpham=jsonObject.getInt("masanpham");
                            String tensanpham= jsonObject.getString("tensanpham");
                            String img= jsonObject.getString("img");
                            String tenkichthuoc= jsonObject.getString("tenkichthuoc");
                            String tenmau= jsonObject.getString("tenmau");
                            int soluong=jsonObject.getInt("soluong");
                            int giamua=jsonObject.getInt("giamua");
                            int tongtien=jsonObject.getInt("tongtien");

                            ee.add(new ChiTietHoaDon(masanpham,tensanpham,img,tenkichthuoc,tenmau,soluong,giamua,tongtien));

                            //---------------------------------------viets code ở dưới này---------------------------------------

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d(TAG, "đã xảy ra lỗi : gggg"+e);
                        }

                    }
                } catch (JSONException e) {
                    Log.d(TAG, "đã xảy ra lỗi : llllll"+e);
                    e.printStackTrace();
                }

                if(mResultCallback != null){

                    mResultCallback.notifySuccess("chitiet_hoadon", ee);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "xảy ra lỗi >>>>" +error);


            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> stringStringMap= new HashMap<>();

                stringStringMap.put("id_mahoadon", String.valueOf(mahoadon));
                stringStringMap.put("id", String.valueOf(id));
                stringStringMap.put("chucvu", String.valueOf(chucvu));


                return stringStringMap;
            }
        };

        requestQueue.add(stringRequest);

    }
}
