package com.lvh.phan6;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lvh.phan6.model.City;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DemoJson extends AppCompatActivity {

    private TextView txtJson;
    String externalStoragePrivateFileStr = "[\n" +
            "  {\n" +
            "    \"country\": \"ES\",\n" +
            "    \"name\": \"Madrid\",\n" +
            "    \"lat\": 40.5,\n" +
            "    \"lng\": -3.666667\n" +
            "  },\n" +
            "  {\n" +
            "    \"country\": \"ES\",\n" +
            "    \"name\": \"Valencia\",\n" +
            "    \"lat\": 39.466667,\n" +
            "    \"lng\": -0.366667\n" +
            "  },\n" +
            "  {\n" +
            "    \"country\": \"ES\",\n" +
            "    \"name\": \"Barcelona\",\n" +
            "    \"lat\": 41.398371,\n" +
            "    \"lng\": 2.1741\n" +
            "  }\n" +
            "]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_json);
        txtJson = (TextView) findViewById(R.id.txtJson);
    }

    public static void writeToFile(File f, String str) throws IOException {
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(str.getBytes());
        fos.close();
    }

    public void readJson(View view) {

        if (isExternalStorageReadable()) {
            File dirPics = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
            File f = new File(dirPics, "cityJson.json");
            String str = null;
            try {
                str = IOHelper.stringFromFile(f);
                JSONArray cities = new JSONArray(str);
                String result = "";
                for (int i = 0; i < cities.length(); i++) {
                    JSONObject city = cities.getJSONObject(i);
                    //new Gson().fromJson(city.toString(), City.class);
                    result += "Country : " + city.getString("country") + "\n" +
                            "Name : " + city.getString("name") + "\n" +
                            "Latitude,Longitud :" + city.getDouble("lat") + ", " + city.getString("lng");
                }
                txtJson.setText(result);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public void writeJson(View view) {
        File dirPics = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS); //deleted when app uninstalls
        File f = new File(dirPics, "cityJson.json");
        try {
            IOHelper.writeToFile(f, externalStoragePrivateFileStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
            return true;

        return false;
    }
}
