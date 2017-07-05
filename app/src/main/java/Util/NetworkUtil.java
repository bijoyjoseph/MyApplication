package Util;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by talentbridge on 5/7/17.
 */

public class NetworkUtil {

    private final String URL = "";

    public interface AsyncResponse {

        void processFinish(String output);

    }

    public class APIutil extends AsyncTask<String, String, String> {

        public AsyncResponse delegate = null;

        public APIutil(AsyncResponse delegate){
            this.delegate = delegate;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            String JsonResponse = null;
            String JsonData = params[0];
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {

                URL url = new URL(URL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(1500);
                connection.setConnectTimeout(1500);
                connection.setDoOutput(true);
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Language", "en-US");
                connection.connect();

                //Log.e("connection", "" + connection);

                Writer writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                writer.write(JsonData);
                writer.flush();
                writer.close();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                Log.e("result", "" + buffer.toString());

                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } /*catch (JSONException e) {
                e.printStackTrace();
            }*/ finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //resultData.setText(result);
        }
    }
}
