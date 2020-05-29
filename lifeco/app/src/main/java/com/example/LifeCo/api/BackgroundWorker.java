package com.example.LifeCo.api;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


public class BackgroundWorker extends AsyncTask<String, Void, String> {


    AlertDialog alertDialog;


    Context context;

    public BackgroundWorker(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {

        String activity_type = params[0];
        String insertUserInfo_url = "http://iamtinara.com/lifeco/insertuserinfo.php";

        if(activity_type.equals("DaftarAkun")){
            try {
                String userId = "1";
                String userNama = params[1];
                String userEmail = params[2];
                String userPassword = params[3];
                String userAlamat = params[4];
                String userNoHP = params[5];
                String userNoBPJS = params[6];
                String userNoKTP = params[7];
                String userTekananDarah = params[8];
                String userGulaDarah = params[9];
                String userGolDarah = params[10];
                String userJenisKelamin = params[11];
                String userPenyakitSendiri = params[12];
                String userPenyakitKeluarga = params[13];
                String userKeluhanUtama = params[14];
                String userObat = params[15];
                String userAlergiObat = params[16];
                String userAlergiMakanan = params[17];
                String userTanggalLahir = params[18];
                String userNoAsuransi = params[19];

                URL url = new URL(insertUserInfo_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("userid", "UTF-8")+"="+URLEncoder.encode(userId, "UTF-8")+"&"
                        +URLEncoder.encode("nama", "UTF-8")+"="+URLEncoder.encode(userNama, "UTF-8")+"&"
                        +URLEncoder.encode("email", "UTF-8")+"="+URLEncoder.encode(userEmail, "UTF-8")+"&"
                        +URLEncoder.encode("password", "UTF-8")+"="+URLEncoder.encode(userPassword, "UTF-8")+"&"
                        +URLEncoder.encode("jeniskelamin", "UTF-8")+"="+URLEncoder.encode(userJenisKelamin, "UTF-8")+"&"
                        +URLEncoder.encode("golongandarah", "UTF-8")+"="+URLEncoder.encode(userGolDarah, "UTF-8")+"&"
                        +URLEncoder.encode("alamat", "UTF-8")+"="+URLEncoder.encode(userAlamat, "UTF-8")+"&"
                        +URLEncoder.encode("notelp", "UTF-8")+"="+URLEncoder.encode(userNoHP, "UTF-8")+"&"
                        +URLEncoder.encode("tanggallahir", "UTF-8")+"="+URLEncoder.encode(userTanggalLahir, "UTF-8")+"&"
                        +URLEncoder.encode("nobpjs", "UTF-8")+"="+URLEncoder.encode(userNoBPJS, "UTF-8")+"&"
                        +URLEncoder.encode("noktp", "UTF-8")+"="+URLEncoder.encode(userNoKTP, "UTF-8")+"&"
                        +URLEncoder.encode("noasuransi", "UTF-8")+"="+URLEncoder.encode(userNoAsuransi, "UTF-8")+"&"
                        +URLEncoder.encode("riwayatpenyakitsendiri", "UTF-8")+"="+URLEncoder.encode(userPenyakitSendiri, "UTF-8")+"&"
                        +URLEncoder.encode("riwayatpenyakitkeluarga", "UTF-8")+"="+URLEncoder.encode(userPenyakitKeluarga, "UTF-8")+"&"
                        +URLEncoder.encode("keluhan", "UTF-8")+"="+URLEncoder.encode(userKeluhanUtama, "UTF-8")+"&"
                        +URLEncoder.encode("obatkonsumsi", "UTF-8")+"="+URLEncoder.encode(userObat, "UTF-8")+"&"
                        +URLEncoder.encode("alergiobat", "UTF-8")+"="+URLEncoder.encode(userAlergiObat, "UTF-8")+"&"
                        +URLEncoder.encode("alergimakanan", "UTF-8")+"="+URLEncoder.encode(userAlergiMakanan, "UTF-8")+"&"
                        +URLEncoder.encode("tekanandarah", "UTF-8")+"="+URLEncoder.encode(userTekananDarah, "UTF-8")+"&"
                        +URLEncoder.encode("guladarah", "UTF-8")+"="+URLEncoder.encode(userGulaDarah, "UTF-8");
                bufferedWriter.write(post_data);
                Log.d("Data sent", post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!=null){
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                Log.d("Data sent", post_data);
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("New Data Added");
    }

    @Override
    protected void onPostExecute(String result) {
        alertDialog.setMessage(result);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}


