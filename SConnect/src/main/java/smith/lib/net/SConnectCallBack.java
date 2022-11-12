package smith.lib.net;

import java.util.HashMap;

public interface SConnectCallBack {
	public void response(String tag, String response, HashMap<String, Object> responseHeaders);
	public void responseError(String tag, String message);
}