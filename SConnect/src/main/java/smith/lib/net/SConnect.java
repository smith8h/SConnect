package smith.lib.net;

import android.app.Activity;
import android.net.*;
import android.os.Build;
import java.util.*;
import org.json.*;
import android.content.Context;

public class SConnect {
    
	private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String DELETE = "DELETE";
	
    private static final String TAG = "DefaultTag";
    
    public static final int PARAM = 0;
    public static final int BODY = 1;
    
    private Map<String, String> params = new HashMap<>();
	private Map<String, String> headers = new HashMap<>();
	
    private Context context;
    private SConnectCallBack callback;
	private int paramsType = 0;
    
	public static SConnect with(Context context) {
        SConnect sc = new SConnect();
        sc.context = context;
		return sc;
	}
    
    public SConnect callback(SConnectCallBack callback) {
        this.callback = callback;
        return this;
    }
    
    public SConnect headers(Map<String, String> headers) {
		this.headers = headers;
        return this;
	}
	
	public SConnect params(Map<String, String> params, int type) {
		this.params = params;
		this.paramsType = type;
        return this;
	}
    
    public Map<String, String> getHeaders() {
		return headers;
	}
    
    public Map<String, String> getParams() {
		return params;
	}
	
	public int getParamsType() {
		return paramsType;
	}
    
    public Activity getActivity() {
        return (Activity) context;
    }
    
    public void get(String url) {
    	connect(GET, url, TAG);
    }
    
    public void get(String url, String tag) {
    	connect(GET, url, tag);
    }
    
    public void post(String url) {
    	connect(POST, url, TAG);
    }
    
    public void post(String url, String tag) {
    	connect(POST, url, tag);
    }
    
    public void put(String url) {
    	connect(PUT, url, TAG);
    }
    
    public void put(String url, String tag) {
    	connect(PUT, url, tag);
    }
    
    public void delete(String url) {
    	connect(DELETE, url, TAG);
    }
    
    public void delete(String url, String tag) {
    	connect(DELETE, url, tag);
    }
    
    private void connect(String method, String url, String tag) {
		SConnectController.getInstance().connect(this, method, url, tag, callback);
	}

	public static boolean isDeviceConnected(Context context) {
		var connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI));
        } else {
            var networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
	}
}