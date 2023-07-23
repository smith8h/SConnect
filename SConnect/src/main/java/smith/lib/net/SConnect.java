package smith.lib.net;

import android.app.Activity;
import android.net.*;
import java.util.*;
import android.content.Context;
import androidx.annotation.NonNull;

/**
 * The SConnect class helps you create connections
 * to APIs and websites easily with simple and fast codes.
 */
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
	
    private Context context;
    private SConnectCallBack callback;
	private int paramsType = 0;
    private String url;
    private String tag = TAG;

    /**
     * Create new Instance of SConnect.
     * @param context Current Activity or FragmentActivity.
     * @return A new Instance of SConnect.
     */
	@NonNull
    public static SConnect with(Context context) {
        SConnect sc = new SConnect();
        sc.context = context;
        return sc;
	}

    /**
     * Set the SConnect CallBack Interface.
     * @param callback A SConnectCallBack interface.
     * @return The same instance used to set the callback to.
     */
    public SConnect callback(SConnectCallBack callback) {
        this.callback = callback;
        return this;
    }

    /**
     * Set the headers to your connection.
     * @param headers A Map of Stings as Key, and Objects as Value
     * @return The same instance used to set the headers to.
     */
    public SConnect headers(Map<String, Object> headers) {
		this.headers = headers;
        return this;
	}

    /**
     * Set the params to your connection.
     * @param params A Map of Stings as Key, and Objects as Value
     * @return The same instance used to set the params to.
     */
	public SConnect params(Map<String, Object> params, int type) {
		this.params = params;
		this.paramsType = type;
        return this;
	}

    /**
     * Set the target URL to your connection.
     * @param url A string URL (The target URL to connect with).
     * @return The same instance used to set the url to.
     */
    public SConnect url(String url) {
    	this.url = url;
        return this;
    }

    /**
     * Set the unique tag to your connection.
     * @param tag A string tag to distinguish multiple connections in same interface.
     * @return The same instance used to set the tag to.
     */
    public SConnect tag(String tag) {
    	this.tag = tag;
        return this;
    }

    /**
     * Create a connection with GET method.
     */
    public void get() {
    	connect(GET, url, tag);
    }

    /**
     * Create a connection with POST method.
     */
    public void post() {
    	connect(POST, url, tag);
    }

    /**
     * Create a connection with POST method.
     */
    public void put() {
    	connect(PUT, url, tag);
    }

    /**
     * Create a connection with DELETE method.
     */
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
        return (Activity) context;
    }
    
    private void connect(String method, String url, String tag) {
		SConnectController.getInstance().connect(this, method, url, tag, callback);
	}

    /**
     * Check internet connection validity.
     * @param context Current context or Activity.
     * @return True if the device is connected to the internet.
     */
	public static boolean isDeviceConnected(@NonNull Context context) {
		var connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        var capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI));
    }
}