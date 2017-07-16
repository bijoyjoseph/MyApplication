package Util;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

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

    private static final String URL = "https://api.sandbox.paypal.com/v1/oauth2/token";
    private static final String REQUEST_METHOD ="POST";

    public interface AsyncResponse {
        void processFinish(String output);
    }

    public static class APIutil extends AsyncTask<String, String, String> {

        public AsyncResponse delegate = null;

        public APIutil(AsyncResponse delegate) {
            this.delegate = delegate;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            String JsonResponse = null;
            //String JsonData = params[0];
            JSONObject returnObject = new JSONObject(); //
            JSONObject postData = new JSONObject();
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String _authHeader = "";

            try {

                URL url = new URL(URL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(1500);
                connection.setConnectTimeout(1500);
                connection.setDoOutput(true);
                connection.setDoOutput(true);

                JSONObject paypalJsonObject = new JSONObject(); //

                paypalJsonObject.put("Username","AfaQMJCFV5lpEkOZBVw4hbv-LLUFmvy"); //
                paypalJsonObject.put("Password", "F2fHAZoEdcO54I-DXES9OlQpQZ-gCwpPeTgkyNC"); //

                if(_authHeader != null) {
                    connection.addRequestProperty("Content-Type", "x-www-form-urlencoded");
                    connection.addRequestProperty("Authorization", paypalJsonObject.toString()); //
                    connection.addRequestProperty("Content-Language", "en-US");
                }
                /*connection.setRequestProperty ("Content-Type", "application/json");
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Language", "en-US");*/
                connection.setRequestMethod(REQUEST_METHOD);

                int responseCode = connection.getResponseCode();
                connection.connect();

                Log.e("response code:", ""+responseCode);

                returnObject.put("key", "grant_type"); //
                returnObject.put("value", "client_credentials"); //

                //Log.e("connection", "" + connection);
                if(postData != null) {
                    Writer writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                    writer.write(returnObject.toString()); //
                    writer.flush();
                    writer.close();
                }

                if(responseCode == connection.HTTP_OK) {

                    InputStream stream = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();

                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                        if (buffer.length() == 0) {
                            // Stream was empty. No point in parsing.
                            return null;
                        }
                    }
                    JsonResponse = buffer.toString();
                } else {
                    InputStream stream = connection.getErrorStream();
                }

                Log.e("result", "" + JsonResponse);

                return JsonResponse;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } /*catch (JSONException e) {
                e.printStackTrace();
            }*/ catch (JSONException e) {
                e.printStackTrace();
            } finally {
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
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            delegate.processFinish(result);
        }
    }
}
