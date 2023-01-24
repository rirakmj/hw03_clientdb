package com.example.hw03_clientdb.Network;

import android.os.AsyncTask;
import android.util.Log;

import com.example.hw03_clientdb.Custom_Adapter;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkInsert extends AsyncTask<String,Void,String> {

    private URL Url;
    private String URL_Adress = "http://172.30.1.58:8888/testDB/insertDB.jsp";
    private Custom_Adapter adapter;

    public NetworkInsert(Custom_Adapter adapter){
        this.adapter = adapter;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        String res = "";
        try{
            Url = new URL(URL_Adress);
            HttpURLConnection conn = (HttpURLConnection)Url.openConnection();

            //전송모드 설정
            conn.setDefaultUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");

            //content-type 설정
            conn.setRequestProperty("Content-type","application/x-www-form-urlencoded; charset=utf-8");
            //전송값 설정
            StringBuffer buffer = new StringBuffer();
            buffer.append("user_id").append("=").append(strings[0]);
            buffer.append("&user_name").append("=").append(strings[1]);
            buffer.append("&user_phone").append("=").append(strings[2]);
            buffer.append("&user_grade").append("=").append(strings[3]);


            //서버로 전송
            OutputStreamWriter outStream = new OutputStreamWriter(conn.getOutputStream(),"utf-8");
            PrintWriter writer = new PrintWriter(outStream);
            writer.write(buffer.toString());
            writer.flush();

            StringBuilder builder = new StringBuilder();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line;
            while((line = in.readLine()) != null){
                builder.append(line + "\n");
            }
            res = builder.toString();
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        Log.i("Get result",res);
        return res;
    }

    @Override
    protected void onPostExecute(String s){
        super.onPostExecute(s);
        int res = 0;
        try{
            res = JsonParser.getResultJson(s);
        } catch (JSONException e){
            e.printStackTrace();
        }
        if(res==0){

        }
        else{
            new NetworkGet(adapter).execute("");
        }

    }
}
