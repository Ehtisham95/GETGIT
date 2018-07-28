package app.robo.com.getgit;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


class JSONObjRequest {

    public static void getData(int method, Context context, String url,
                               Response.Listener<JSONObject> listenerResponse, Response.ErrorListener listenerError) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, listenerResponse,
                listenerError);
        Volley.newRequestQueue(context).add(request);
    }
}