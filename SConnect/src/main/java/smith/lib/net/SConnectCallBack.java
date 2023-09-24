package smith.lib.net;

import java.util.Map;

/**
 * Use it to interact with SConnect connections.
 */
public interface SConnectCallBack {
	/**
	 * Triggered when connection is done successfully.
	 * @param response The response got from the connection result.
	 * @param tag The unique tag that passed to the connection to distinguish it.
	 * @param responseHeaders The headers passed to the connection.
	 */
	void onSuccess(SResponse response, String tag, Map<String, Object> responseHeaders);

	/**
	 * Triggered when connection is not done or some errors happened.
	 * @param response The response got from the connection result.
	 * @param tag The unique tag that passed to the connection to distinguish it.
	 */
	void onFailure(SResponse response, String tag);
}
