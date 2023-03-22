package smith.test;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import com.itsaky.androidide.logsender.LogSender;
import java.util.HashMap;
import smith.lib.net.SConnect;
import smith.lib.net.SConnectCallBack;
import smith.lib.net.SResponse;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogSender.startLogging(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void check(View v) {
        
        String tag = "someTag"; 
        String url = "https://smith8h.t.me"; // follow me there 😐
        // just for edit
        SConnect connect = new SConnect(this);
        
        connect.setCallBack(new SConnectCallBack() {
            @Override
            public void onFailure(SResponse response, String tag) {}
                
            @Override
            public void onSuccess(SResponse response, String tag, HashMap<String, Object> responseHeaders) {
                if (response.isJSON() && response.isArray()) {
                    SResponse.Array a = response.getArray();
                    SResponse.Map m = a.getMap(0);
                    m.toMap();
                    Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "response is not json", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        // add params to the request
        // connect.setParams(new HashMap<String, Object>(), SConnect.REQUEST_BODY);
        // also REQUEST_PARAM
        
        // add headers to the request
        // connect.setHeaders(new HashMap<String, Object>());
        
        // getters
        // getHeaders(), getParams(), getActivity(), getConnectType()
        if (SConnect.isDeviceConnected(this))
            connect.connect(SConnect.GET, url, tag);
    }
}
