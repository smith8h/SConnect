/*
 * Copyright (C) 2023, Hussein Shakir (Dev. Smith)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package smith.lib.net;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

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
     * Default SConnect tag used in connections that does not have a tag to distinguish them.
     */
    public static final String SCONNECT_TAG = "DefaultSConnectTag";
    /**
     * The type of request param, used to set params type for {@link SConnect#params(Map)} using
     * {@link SConnect#paramsType(int)}.
     */
    public static final int PARAM = 0;
    /**
     * The type of request body, used to set body type for {@link SConnect#params(Map)} using
     * {@link SConnect#paramsType(int)}.
     */
    public static final int BODY = 1;
    
    private Map<String, Object> params = new HashMap<>();
	private Map<String, Object> headers = new HashMap<>();
	
    private Context context;
    private SConnectCallBack callback;
	private int paramsType = PARAM;
    private String url;
    private String tag = SCONNECT_TAG;
    private String mediaType;

    /**
     * Create new Instance of SConnect.
     * @param context Current Activity or FragmentActivity.
     * @return A new Instance of SConnect.
     */
	@NonNull
    public static SConnect init(Context context) {
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
     * Add header to the connection headers.
     * @param key A String as Key.
     * @param value A value as any.
     */
    public SConnect addHeader(String key, Object value) {
        this.headers.put(key, value);
        return this;
    }

    /**
     * Set the request params or body to your connection.
     * @param params A Map of Strings as Key, and Objects as Value.
     */
	public SConnect params(Map<String, Object> params) {
		this.params = params;
        return this;
	}

    /**
     * Add param to the connection params.
     * @param key A String as Key.
     * @param value A value as any.
     */
    public SConnect addParam(String key, Object value) {
        this.params.put(key, value);
        return this;
    }

    /**
     * Set the media type if required by some APIs.
     * @param type the required media type as needed as json type, text, images or else! <br/>
     *             <b>Example Type:</b> a json encoded with url encoding media type
     *             {@code "application/x-www-form-urlencoded"}
     */
    public SConnect mediaType(String type) {
        this.mediaType = type;
        return this;
    }

    /**
     * Set the type as params or body to your connection.
     * @param type the request body type, either {@link SConnect#PARAM} or {@link SConnect#BODY}
     */
    public SConnect paramsType(int type) {
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
     * Create a connection with GET method, use it to request a representation of the specified
     * resource.
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
     * Requires a request {@link SConnect#BODY}, roll back to {@link SConnect#params(Map)}.
     */
    public void patch() {
        if (params.isEmpty()) {
            if (callback != null)
                callback.onFailure(
                        new SResponse("PATCH request method does not contain any request body or params!")
                        , tag
                );
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
                callback.onFailure(
                        new SResponse("OPTIONS request method does not contain any request headers!")
                        , tag
                );
        }
        else connect(OPTIONS);
    }

    /**
     * Create a connection with OPTIONS method, use it to ask for a response identical to that of a
     * GET request,
     * but without the response body.
     * <p>
     *     This is useful for retrieving meta-information written in response headers,
     *     without having to transport the entire content.
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
    
    protected String getMediaType() {
    	return mediaType;
    }
    
    protected Context getContext() {
        return context;
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
		var connectivityManager = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE
        );
        var capabilities = connectivityManager.getNetworkCapabilities(
                connectivityManager.getActiveNetwork()
        );
        return capabilities != null &&
                (capabilities.hasTransport(
                        NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                );
    }
}
