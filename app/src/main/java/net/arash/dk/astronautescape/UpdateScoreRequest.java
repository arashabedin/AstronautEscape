package net.arash.dk.astronautescape;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Arash on 1/3/2018.
 */

public class UpdateScoreRequest extends StringRequest{

    private static final String UpdateScore_REQUEST_URL = "http://arash.dk/astronaut/UpdateScore.php";
    private Map<String, String> params;



    public UpdateScoreRequest( String username, int score, Response.Listener<String> listener){

        super(Method.POST, UpdateScore_REQUEST_URL, listener,null);
        params = new HashMap<>();

        params.put("username",username);
        params.put("score",score + "");

    }

    @Override
    public Map<String,String> getParams() {
        return params;
    }
}
