package com.developer.cubemarket.connection.MODEL.DAO;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.developer.cubemarket.connection.MODEL.IResult.IResult_sanpham;
import com.developer.cubemarket.connection.MODEL.KET_NOI_SEVER.HttpsTrustManager;
import com.developer.cubemarket.connection.MODEL.KET_NOI_SEVER.Link;
import com.developer.cubemarket.connection.MODEL.OOP.Danhmuc;
import com.developer.cubemarket.connection.MODEL.OOP.Sanpham;
import com.developer.cubemarket.utils.CallBackDelProduct;
import com.developer.cubemarket.utils.CallBackInsertProduct;
import com.developer.cubemarket.utils.CallBackProduct;
import com.developer.cubemarket.utils.CallBackProductSale;
import com.developer.cubemarket.utils.CallBackSearchProduct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DaoSanPham {
    Context context;
    String TAG="TAG";
    IResult_sanpham mResultCallback = null;

    public DaoSanPham(Context context) {
        this.context = context;
    }

    public  void insert_sanpham(CallBackInsertProduct callBackInsertProduct, int madanhmuc, String tensanpham, String img, String nhasanxuat,
                                int soluong, int giaban, String chitiet, String tenmau, String tenkichthuoc, int id){

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest= new StringRequest(Request.Method.POST, Link.insert_sanpham, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.toString().trim().equals("success")){
                    Log.d(TAG, "thêm thành công");
                    callBackInsertProduct.onSuccess("thêm sản phẩm thành công");
                }else{
                    Log.d(TAG, "lỗi>>"+response.toString());
                    callBackInsertProduct.onFail("lỗi>>"+response.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBackInsertProduct.onError("lỗi>>"+error.toString());
                Log.d(TAG, "xảy ra lỗi >>>>" +error);


            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> stringStringMap= new HashMap<>();
                Danhmuc danhmuc = new Danhmuc();
                stringStringMap.put("madanhmuc", String.valueOf(madanhmuc));
                stringStringMap.put("tensanpham",tensanpham);
                stringStringMap.put("hinhanh",img );// chuyển hình thành base 64

                stringStringMap.put("nhasanxuat", nhasanxuat);
                stringStringMap.put("soluong", String.valueOf(soluong));

                stringStringMap.put("giaban", String.valueOf(giaban));
                stringStringMap.put("chitiet",chitiet);
                stringStringMap.put("tenmau",tenmau);
                stringStringMap.put("tenkichthuoc",tenkichthuoc);
                stringStringMap.put("id", String.valueOf(id));
                return stringStringMap;
            }
        };

        requestQueue.add(stringRequest);

    }
    // hàm chuyển ảnh thành base 64
    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        return imgString;
    }
    public  void delete_sanpham(CallBackDelProduct callback, int masanpham){

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest= new StringRequest(Request.Method.POST, Link.delete_sanpham, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.toString().trim().equals("success")){
                    Log.d(TAG, "xóa thành công");
                    callback.onSuccess("xóa thành công");
                }else{
                    callback.onFail("lỗi>>"+response.toString());
                    Log.d(TAG, "lỗi>>"+response.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError("lỗi>>"+error.toString());
                Log.d(TAG, "xảy ra lỗi >>>>" +error);


            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> stringStringMap= new HashMap<>();

                stringStringMap.put("masanpham", String.valueOf(masanpham));

                return stringStringMap;
            }
        };

        requestQueue.add(stringRequest);

    }
    public  void update_sanpham( Sanpham sanpham){

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest= new StringRequest(Request.Method.POST, Link.update_sanpham, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.toString().trim().equals("success")){
                    Log.d(TAG, "câp nhập thành công");
                }else{
                    Log.d(TAG, "lỗi>>"+response.toString());
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

                stringStringMap.put("masanpham", String.valueOf(sanpham.getMasanpham()));
                Danhmuc danhmuc = new Danhmuc();
                stringStringMap.put("madanhmuc", String.valueOf(danhmuc.getMadanhmuc()));
                stringStringMap.put("tensanpham",sanpham.getTensanpham());
                stringStringMap.put("hinhanh",sanpham.getImg() );// chuyển hình thành base 64

                stringStringMap.put("nhasanxuat", sanpham.getNhasanxuat());
                stringStringMap.put("soluong", String.valueOf(sanpham.getSoluong()));

                stringStringMap.put("giaban", String.valueOf(sanpham.getGiaban()));
                stringStringMap.put("chitiet",sanpham.getChitiet());
                return stringStringMap;
            }
        };

        requestQueue.add(stringRequest);

    }
    public  void getdata_sanpham(CallBackProduct callback, int id, int chucvu){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest= new StringRequest(Request.Method.POST, Link.getdata_sanpham, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                List<Sanpham> ee= new ArrayList();
                Log.d(TAG, "onResponse: " +response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0 ; i<jsonArray.length();i++){
                        try {
                            JSONObject jsonObject= jsonArray.getJSONObject(i);
                            int masanpham=jsonObject.getInt("masanpham");
                            int madanhmuc= jsonObject.getInt("madanhmuc");
                            String tensanpham= jsonObject.getString("tensanpham");
                            String img = jsonObject.getString("img");
                            String nhasanxuat=jsonObject.getString("nhasanxuat");
                            int soluong=jsonObject.getInt("soluong");
                            int giaban= jsonObject.getInt("giaban");
                            String chitiet= jsonObject.getString("chitiet");
                            int id=jsonObject.getInt("id");
                            String tendanhmuc= jsonObject.getString("tendanhmuc");
                            String khuvuc= jsonObject.getString("khuvuc");

                            //---------------------------------------viets code ở dưới này---------------------------------------
                            callback.onSuccess(new Sanpham(masanpham, new Danhmuc(madanhmuc,tendanhmuc,khuvuc,"hinh"),
                                    tensanpham,img,nhasanxuat,soluong,giaban,chitiet,id));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.onFail(e.toString());
                            Log.d(TAG, "đã xảy ra lỗi : gggg"+e);
                        }

                    }
                } catch (JSONException e) {
                    callback.onError(e.toString());
                    Log.d(TAG, "đã xảy ra lỗi : llllll"+e);
                    e.printStackTrace();
                }
                if(mResultCallback != null){

                    mResultCallback.notifySuccess("sanpham", ee);
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
                stringStringMap.put("YEUCAUGEDATAALL", "XEMCHUNG");
                stringStringMap.put("id", String.valueOf(id));
                stringStringMap.put("chucvu", String.valueOf(chucvu));
                return stringStringMap;
            }
        };
        requestQueue.add(stringRequest);
    }
    public  void getdata_sanphamsaphet(int id,int chucvu){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest= new StringRequest(Request.Method.POST, Link.getdata_sanpham, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: >>> "+response);
                List<Sanpham> ee= new ArrayList();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0 ; i<jsonArray.length();i++){
                        try {
                            JSONObject jsonObject= jsonArray.getJSONObject(i);
                            int masanpham=jsonObject.getInt("masanpham");
                            int madanhmuc= jsonObject.getInt("madanhmuc");
                            String tensanpham= jsonObject.getString("tensanpham");
                            String img = jsonObject.getString("img");
                            String nhasanxuat=jsonObject.getString("nhasanxuat");
                            int soluong=jsonObject.getInt("soluong");
                            int giaban= jsonObject.getInt("giaban");
                            String chitiet= jsonObject.getString("chitiet");
                            int id=jsonObject.getInt("id");
                            String tendanhmuc= jsonObject.getString("tendanhmuc");
                            String khuvuc= jsonObject.getString("khuvuc");

                            ee.add(new Sanpham(masanpham, new Danhmuc(madanhmuc,tendanhmuc,khuvuc,"hinh"),tensanpham,img,nhasanxuat,soluong,giaban,chitiet,id));

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

                    mResultCallback.notifySuccess("sanpham", ee);
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
                stringStringMap.put("YEUCAUGEDATAALL", "XEMSANPHAMSAPHET");
                stringStringMap.put("id", String.valueOf(id));
                stringStringMap.put("chucvu", String.valueOf(chucvu));
                return stringStringMap;
            }
        };
        requestQueue.add(stringRequest);
    }
    public  void getdata_sanpham_all(CallBackProductSale callBackProductSale, int id, int chucvu){
        Log.d("III", "id: "+id+" chucVu: "+chucvu);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest= new StringRequest(Request.Method.POST, Link.getdata_sanpham, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("789", "onResponse: >>> 789"+response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0 ; i<jsonArray.length();i++){
                        try {
                            JSONObject jsonObject= jsonArray.getJSONObject(i);
                            int masanpham=jsonObject.getInt("masanpham");
                            int madanhmuc= jsonObject.getInt("madanhmuc");
                            String tensanpham= jsonObject.getString("tensanpham");
                            String img = jsonObject.getString("img");
                            String nhasanxuat=jsonObject.getString("nhasanxuat");
                            int soluong=jsonObject.getInt("soluong");
                            int giaban= jsonObject.getInt("giaban");
                            String chitiet= jsonObject.getString("chitiet");
                            int id=jsonObject.getInt("id");
                            String tendanhmuc= jsonObject.getString("tendanhmuc");
                            String khuvuc= jsonObject.getString("khuvuc");
                            callBackProductSale.onSuccess(new Sanpham(masanpham, new Danhmuc(madanhmuc,tendanhmuc,khuvuc,"hinh"),
                                    tensanpham,img,nhasanxuat,soluong,giaban,chitiet,id));
                            //---------------------------------------viets code ở dưới này---------------------------------------
                            Log.d("AAA", tensanpham);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callBackProductSale.onFail(e.toString());
                            Log.d("AAA", e.toString());
                        }

                    }
                } catch (JSONException e) {
                    callBackProductSale.onError(e.toString());
                    Log.d("AAA", e.toString());
                    Log.d(TAG, "đã xảy ra lỗi : llllll"+e);
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "xảy ra lỗi >>>>" +error.networkResponse);
                Log.d(TAG, "xảy ra lỗi >>>>" +error.getLocalizedMessage());
                Log.d(TAG, "xảy ra lỗi >>>>" + Arrays.toString(error.getStackTrace()));

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> stringStringMap= new HashMap<>();
                stringStringMap.put("YEUCAUGEDATAALL", "XEMSANPHAMALL");
                stringStringMap.put("id", String.valueOf(id));
                stringStringMap.put("chucvu", String.valueOf(chucvu));
                return stringStringMap;
            }
        };
        requestQueue.add(stringRequest);
    }
    public  void search_sanpham_chung(CallBackSearchProduct callBack, String noidungsearch, int id, int chucvu){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest= new StringRequest(Request.Method.POST, Link.search_sanpham, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: >>> "+response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0 ; i<jsonArray.length();i++){
                        try {
                            JSONObject jsonObject= jsonArray.getJSONObject(i);
                            int masanpham=jsonObject.getInt("masanpham");
                            int madanhmuc= jsonObject.getInt("madanhmuc");
                            String tensanpham= jsonObject.getString("tensanpham");
                            String img = jsonObject.getString("img");
                            String nhasanxuat=jsonObject.getString("nhasanxuat");
                            int soluong=jsonObject.getInt("soluong");
                            int giaban= jsonObject.getInt("giaban");
                            String chitiet= jsonObject.getString("chitiet");
                            int id=jsonObject.getInt("id");
                            String tendanhmuc= jsonObject.getString("tendanhmuc");
                            String khuvuc= jsonObject.getString("khuvuc");



                            //---------------------------------------viets code ở dưới này---------------------------------------
                            Log.d("SEARCH: TEN SAN PHAM => ", tensanpham);
                            callBack.onSuccess(new Sanpham(masanpham, new Danhmuc(madanhmuc,tendanhmuc,khuvuc,"hinh"),
                                    tensanpham,img,nhasanxuat,soluong,giaban,chitiet,id));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callBack.onFail(e.toString());
                            Log.d(TAG, "đã xảy ra lỗi : gggg"+e);
                        }

                    }
                    callBack.onFinish();

                } catch (JSONException e) {
                    callBack.onError(e.toString());
                    Log.d(TAG, "đã xảy ra lỗi : llllll"+e);
                    e.printStackTrace();
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
                stringStringMap.put("noidungsearch", noidungsearch);
                stringStringMap.put("YEUCAUGEDATAALL", "XEMCHUNG");
                stringStringMap.put("id", String.valueOf(id));
                stringStringMap.put("chucvu", String.valueOf(chucvu));
                return stringStringMap;
            }
        };
        requestQueue.add(stringRequest);
    }
    public  void search_sanpham_rieng(String noidungsearch,int id,int chucvu){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest= new StringRequest(Request.Method.POST, Link.search_sanpham, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: >>> "+response);
                List<Sanpham> ee= new ArrayList();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0 ; i<jsonArray.length();i++){
                        try {
                            JSONObject jsonObject= jsonArray.getJSONObject(i);
                            int masanpham=jsonObject.getInt("masanpham");
                            int madanhmuc= jsonObject.getInt("madanhmuc");
                            String tensanpham= jsonObject.getString("tensanpham");
                            String img = jsonObject.getString("img");
                            String nhasanxuat=jsonObject.getString("nhasanxuat");
                            int soluong=jsonObject.getInt("soluong");
                            int giaban= jsonObject.getInt("giaban");
                            String chitiet= jsonObject.getString("chitiet");
                            int id=jsonObject.getInt("id");
                            String tendanhmuc= jsonObject.getString("tendanhmuc");
                            String khuvuc= jsonObject.getString("khuvuc");

                            ee.add(new Sanpham(masanpham, new Danhmuc(madanhmuc,tendanhmuc,khuvuc,"hinh"),tensanpham,img,nhasanxuat,soluong,giaban,chitiet,id));

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

                    mResultCallback.notifySuccess("sanpham", ee);
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
                stringStringMap.put("noidungsearch", noidungsearch);
                stringStringMap.put("YEUCAUGEDATAALL", "XEMALL");
                stringStringMap.put("id", String.valueOf(id));
                stringStringMap.put("chucvu", String.valueOf(chucvu));
                return stringStringMap;
            }
        };
        requestQueue.add(stringRequest);
    }
    public  void update_soluong_sanpham(int masanpham,int soluong){

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest= new StringRequest(Request.Method.POST, Link.update_soluong_sanpham, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.toString().trim().equals("success")){
                    Log.d(TAG, "câp nhập thành công");
                }else{
                    Log.d(TAG, "lỗi>>"+response.toString());
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

                stringStringMap.put("masanpham", String.valueOf(masanpham));
                stringStringMap.put("soluong", String.valueOf(soluong));
                return stringStringMap;
            }
        };

        requestQueue.add(stringRequest);

    }

    public  void sanpham_tuongtu(int id,int chucvu,String tendanhmuc,String tensanpham){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest= new StringRequest(Request.Method.POST, Link.search_sanpham, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: >>> "+response);
                List<Sanpham> ee= new ArrayList();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0 ; i<jsonArray.length();i++){
                        try {
                            JSONObject jsonObject= jsonArray.getJSONObject(i);
                            int masanpham=jsonObject.getInt("masanpham");
                            int madanhmuc= jsonObject.getInt("madanhmuc");
                            String tensanpham= jsonObject.getString("tensanpham");
                            String img = jsonObject.getString("img");
                            String nhasanxuat=jsonObject.getString("nhasanxuat");
                            int soluong=jsonObject.getInt("soluong");
                            int giaban= jsonObject.getInt("giaban");
                            String chitiet= jsonObject.getString("chitiet");
                            int id=jsonObject.getInt("id");
                            String tendanhmuc= jsonObject.getString("tendanhmuc");
                            String khuvuc= jsonObject.getString("khuvuc");

                            ee.add(new Sanpham(masanpham, new Danhmuc(madanhmuc,tendanhmuc,khuvuc,"hinh"),tensanpham,img,nhasanxuat,soluong,giaban,chitiet,id));

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

                    mResultCallback.notifySuccess("sanpham", ee);
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

                stringStringMap.put("id", String.valueOf(id));
                stringStringMap.put("chucvu", String.valueOf(chucvu));
                stringStringMap.put("tendanhmuctuongtu", tendanhmuc);
                stringStringMap.put("tensanphamtuongtu", tensanpham);
                stringStringMap.put("YEUCAUGEDATAALL", "XEMCHUNG");
                return stringStringMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}