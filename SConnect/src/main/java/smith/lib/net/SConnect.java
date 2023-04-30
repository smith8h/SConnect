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
    
    private Map<String, Object> params = new HashMap<>();
	private Map<String, Object> headers = new HashMap<>();
	
    private Activity activity;
    private SConnectCallBack callback;
	private int paramsType = 0;
    private String url;
    private String tag = TAG;
    
	public static SConnect with(Activity activity) {
        SConnect sc = new SConnect();
        sc.activity = activity;
		return sc;
	}
    
    public SConnect callback(SConnectCallBack callback) {
        this.callback = callback;
        return this;
    }
    
    public SConnect headers(Map<String, Object> headers) {
		this.headers = headers;
        return this;
	}
	
	public SConnect params(Map<String, Object> params, int type) {
		this.params = params;
		this.paramsType = type;
        return this;
	}
    
    public SConnect url(String url) {
    	this.url = url;
        return this;
    }
    
    public SConnect tag(String tag) {
    	this.tag = tag;
        return this;
    }
    
    public void get() {
    	connect(GET, url, tag);
    }
    
    public void post() {
    	connect(POST, url, tag);
    }
    
    public void put() {
    	connect(PUT, url, tag);
    }
    
    public void delete() {
    	connect(DELETE, url, tag);
    }
    
    protected Map<String, Object> getHeaders() {
		return headers;
	}
    
    protected Map<String, Object> getParams() {
		return params;
	}
	
	protected int getParamsType() {
		return paramsType;
	}
    
    protected Activity getActivity() {
        return activity;
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