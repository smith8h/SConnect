package smith.lib.net;

import java.util.HashMap;

public interface SConnectCallBack {
	public void onSuccess(SResponse response, String tag, HashMap<String, Object> responseHeaders);
	public void onFailure(SResponse response, String tag);
}