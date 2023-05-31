/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import data.JsonLocationResult;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class LocationAPI {

    public static JsonLocationResult getJsonLocationData(String value) {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        JsonLocationResult data = null;
        // Tạo một request đến API 
        Request res = null;
        res = new Request.Builder().url("http://ip-api.com/json/" + value).build();
        try {
            // Gửi Request đến API và nhận về Response  
            Response response = client.newCall(res).execute();
            //Một Response sẽ có Header và Body, ở đây mình chỉ lấy Body
            ResponseBody body = response.body();
            // Chuyển đổi Body tu chuỗi Json thành Object
            data = gson.fromJson(body.string(), JsonLocationResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}

