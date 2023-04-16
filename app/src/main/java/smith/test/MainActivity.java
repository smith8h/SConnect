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
import java.util.Map;
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
        String url = "https://smithdev.t.me"; // follow me there 😐
        
        if (SConnect.isDeviceConnected(this))
        SConnect.with(this)
                .callback(new SConnectCallBack() {
                    @Override
                    public void onSuccess(SResponse response, String tag, Map<String, String> responseHeaders) {
                        // use (response, tag, headers)
                        Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(SResponse response, String tag) {
                        // use (response, tag)
                        Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
                .headers(new HashMap<String, String>() {{
                    put("key", "value");
                    // ...
                }})
                .params(new HashMap<String, String>() {{
                    put("key", "value");
                    // ...
                }}, SConnect.PARAM) // BODY
                .get(url); // get(url, tag);
                // get, post, put, delete
    }
}
