package smith.lib.net;

import android.app.Activity;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;
import android.net.NetworkInfo;
import android.net.ConnectivityManager;
import android.content.Context;

public class SConnect {
    
	public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";
	
    public static final int PARAM = 0;
    public static final int BODY = 1;
    
    private HashMap<String, Object> params = new HashMap<>();
	private HashMap<String, Object> headers = new HashMap<>();
	
    private Context context;
    private SConnectCallBack callback;
	private int connectType = 0;
    
    
	public SConnect(Context context) {
		this.context = context;
	}
    
    public void setCallBack(SConnectCallBack callback) {
        this.callback = callback;
    }
    
    public void setHeaders(HashMap<String, Object> headers) {
		this.headers = headers;
	}
	
	public void setParams(HashMap<String, Object> params, int connectType) {
		this.params = params;
		this.connectType = connectType;
	}
    
    public HashMap<String, Object> getHeaders() {
		return headers;
	}
    
    public HashMap<String, Object> getParams() {
		return params;
	}
	
	public int getConnectType() {
		return connectType;
	}
    
    public Activity getActivity() {
        return (Activity)context;
    }
    
    public void connect(String method, String url, String tag) {
		SConnectController.getInstance().connect(this, method, url, tag, callback);
	}

	public static boolean isDeviceConnected(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		return networkInfo != null && networkInfo.isConnected();
	}

	public static boolean isResponseValidJson(String response) {
		try { new JSONObject(response); }
        catch (JSONException ex) {
			try { new JSONArray(response); }
            catch (JSONException ex1) { return false; }
		}
		return true;
	}
}