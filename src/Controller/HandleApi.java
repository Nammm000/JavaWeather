package Controller;

import data.JsonResult;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HandleApi {

    public static JsonResult getJsonData(String value) {
        boolean letter = true;
        int n = value.length();
        for (int i = 0; i < n; i++) {
            // checks whether the character is not a letter
            // if it is not a letter ,it will return false
            if ((Character.isLetter(value.charAt(i)) == true)) {
                break;
            } 
            if ((Character.isDigit(value.charAt(i)) == true)) {
                letter = false;
                break;
            }
        }
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        JsonResult data = null;
        // Tạo một request đến API 
        Request res = null;
        if (letter == false) {
            String[] arrOfStr = value.split(" ", 0);
            String loc = "lat=" + arrOfStr[0] +"&lon="+ arrOfStr[1];
            res = new Request.Builder().url("https://api.openweathermap.org/data/2.5/forecast?" + loc + "&APPID=0d36263009fb8a992c8f8e3e5fee20b1&lang=vi").build();
        } else {
            res = new Request.Builder().url("https://api.openweathermap.org/data/2.5/forecast?q=" + value + "&APPID=0d36263009fb8a992c8f8e3e5fee20b1&lang=vi").build();
        }
        try {
            // Gửi Request đến API và nhận về Response  
            Response response = client.newCall(res).execute();
            //Một Response sẽ có Header và Body, ở đây mình chỉ lấy Body
            ResponseBody body = response.body();
            // Chuyển đổi Body tu chuỗi Json thành Object
            data = gson.fromJson(body.string(), JsonResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
