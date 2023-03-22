package smith.lib.net;

import java.util.HashMap;

public interface SConnectCallBack {
	public void onSeccess(SResponse response, String tag, HashMap<String, Object> responseHeaders);
	public void onFailure(SResponse response, String tag);
}