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
@SuppressWarnings({"unused"})
public class SConnect {
    
	private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String DELETE = "DELETE";
    private static final String PATCH = "PATCH";
    private static final String OPTIONS = "OPTIONS";
    private static final String HEAD = "HEAD";

    /**
     * Default SConnect tag used in connections doesn't have tag to identify.
     */
    public static final String SCONNECT_TAG = "DefaultSConnectTag";
    /**
     * The type of request param, used to set params type in {@link SConnect#params(Map, int)}.
     */
    public static final int PARAM = 0;
    /**
     * The type of request body, used to set body type in {@link SConnect#params(Map, int)}.
     */
    public static final int BODY = 1;
    
    private Map<String, Object> params = new HashMap<>();
	private Map<String, Object> headers = new HashMap<>();
	
    private Context context;
    private SConnectCallBack callback;
	private int paramsType = PARAM;
    private String url;
    private String tag = SCONNECT_TAG;

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
     */
    public SConnect callback(SConnectCallBack callback) {
        this.callback = callback;
        return this;
    }

    /**
     * Set the headers to your connection.
     * @param headers A Map of Stings as Key, and Objects as Value
     */
    public SConnect headers(Map<String, Object> headers) {
		this.headers = headers;
        return this;
	}

    /**
     * Set the request params or body to your connection.
     * @param params A Map of Stings as Key, and Objects as Value.
     * @param type the request body type, either {@link SConnect#PARAM} or {@link SConnect#BODY}
     */
	public SConnect params(Map<String, Object> params, int type) {
		this.params = params;
		this.paramsType = type;
        return this;
	}

    /**
     * Set the target URL to your connection.
     * @param url A string URL (The target URL to connect with).
     */
    public SConnect url(String url) {
    	this.url = url;
        return this;
    }

    /**
     * Set the unique tag to your connection.
     * @param tag A string tag to distinguish multiple connections in same interface.
     */
    public SConnect tag(String tag) {
    	this.tag = tag;
        return this;
    }

    /**
     * Create a connection with GET method, use it to request a representation of the specified resource.
     * <p>
     *     Requests using GET should only be used to request data (they shouldn't include data).
     * </p>
     */
    public void get() {
    	connect(GET);
    }

    /**
     * Create a connection with POST method, use it to send data to a Specified server in order to
     * create or rewrite a particular resource/data.
     */
    public void post() {
    	connect(POST);
    }

    /**
     * Create a connection with POST method, use it for full replacement of an OSLC resource.
     * <p>
     *     The PUT method updates both literal properties and local resource properties,
     *     and it deletes any local resource properties that are not included in the request.
     * </p>
     */
    public void put() {
    	connect(PUT);
    }

    /**
     * Create a connection with DELETE method, use it for requests that the origin server remove
     * the association between the target resource and its current functionality.
     */
    public void delete() {
    	connect(DELETE);
    }

    /**
     * Create a connection with PATCH method, use it to modify the values of the resource properties.
     * Requires a request {@link SConnect#BODY}, roll back to {@link SConnect#params(Map, int)}.
     */
    public void patch() {
        if (params.isEmpty()) {
            if (callback != null)
                callback.onFailure(new SResponse("PATCH request method does not contain any request body params!"), tag);
        }
        else connect(PATCH);
    }

    /**
     * Create a connection with OPTIONS method, use it to request information about the communication
     * options available for the target resource.
     * <p>
     *     The response may include an Allow header indicating allowed HTTP methods on the resource,
     *     or various Cross Origin Resource Sharing headers.
     * </p>
     * Requires to set a headers to the request, roll back to {@link SConnect#headers(Map)}.
     */
    public void options() {
        if (headers.isEmpty()) {
            if (callback != null)
                callback.onFailure(new SResponse("OPTIONS request method does not contain any request headers!"), tag);
        }
        else connect(OPTIONS);
    }

    /**
     * Create a connection with OPTIONS method, use it to ask for a response identical to that of a GET request,
     * but without the response body.
     * <p>
     *     This is useful for retrieving meta-information written in response headers, without having to transport the entire content.
     * </p>
     */
    public void head() {
        connect(HEAD);
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
    
    private void connect(String method) {
		SConnectController.getInstance().connect(this, method, url, tag, callback);
	}

    /**
     * Check internet availability on the device, useful when checking before making connections.
     * @param context current {@link Context} or {@link Activity}.
     * @return true if the device is connected to the internet.
     */
	public static boolean isDeviceConnected(@NonNull Context context) {
		var connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        var capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI));
    }
}