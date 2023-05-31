/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class IpAddress {

    public static String getIP() {
        String ip = null;
        try (java.util.Scanner s = new java.util.Scanner(new java.net.URL("https://api.ipify.org").openStream(), "UTF-8").useDelimiter("\\A")) {
            ip = s.next();
            System.out.println("My current IP address is " + ip);
        } catch (java.io.IOException e) {
            e.printStackTrace();
            try (java.util.Scanner s = new java.util.Scanner(new java.net.URL("https://api64.ipify.org?format=json").openStream(), "UTF-8").useDelimiter("\\A")) {
                System.out.println("My current IP address is " + s.next());
            } catch (java.io.IOException e2) {
                e2.printStackTrace();
            }
        }
        return ip;
    }
}

