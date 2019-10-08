package com.example.listview;

public class Metodo {

    public void executeDeleteRequest(String stringUrl, JSONObject jsonObject, String reqContentType, String resContentType, int timeout) throws Exception {
        URL url = new URL(stringUrl);
        HttpURLConnection connection = null;
        String urlParameters = jsonObject.toString();
        try {
            connection = (HttpURLConnection) url.openConnection();

            //Setting the request properties and header
            connection.setRequestProperty("X-HTTP-Method-Override", "DELETE");
            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", USER_AGENT);
            connection.setRequestProperty(CONTENT_TYPE_KEY, reqContentType);
            connection.setRequestProperty(ACCEPT_KEY, resContentType);


            connection.setReadTimeout(timeout);
            connection.setConnectTimeout(defaultTimeOut);

            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            // Send request
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();
            responseCode = connection.getResponseCode();
            // To handle web services which server responds with response code
            // only
            try {
                response = convertStreamToString(connection.getInputStream());
            } catch (Exception e) {
                Log.e(Log.TAG_REST_CLIENT, "Cannot convert the input stream to string for the url= " + stringUrl + ", Code response=" + responseCode + "for the JsonObject: " + jsonObject.toString(), context);
            }
        } catch (
                Exception e
        )

        {
            if (!BController.isInternetAvailable(context)) {
                IntentSender.getInstance().sendIntent(context, Constants.BC_NO_INTERNET_CONNECTION);
                Log.e(Log.TAG_REST_CLIENT, "No internet connection", context);
            }
            Log.e(Log.TAG_REST_CLIENT, "Cannot perform the POST request successfully for the following URL: " + stringUrl + ", Code response=" + responseCode + "for the JsonObject: " + jsonObject.toString(), context);
            throw e;
        } finally{

            if (connection != null) {
                connection.disconnect();
            }
        }

    }
}
