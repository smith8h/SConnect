package smith.test;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
//import com.itsaky.androidide.logsender.LogSender;
import java.util.Map;
import smith.lib.net.SConnect;
import smith.lib.net.SConnectCallBack;
import smith.lib.net.SResponse;

public class MainActivity extends AppCompatActivity implements SConnectCallBack {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //LogSender.startLogging(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void check(View v) {
        
        String tag = "someTag"; 
        String url = "https://smithdev.t.me";
        
        if (SConnect.isDeviceConnected(this)) {
            SConnect.with(this)
                .callback(this)
                .url(url)
                .tag(tag)
                .get();
        }
    }
    
    @Override
    public void onSuccess(SResponse response, String tag, Map<String, Object> responseHeaders) {
        if (tag.equals("someTag"))
            Toast.makeText(this, response.toString(), Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "response of another request with another tag", Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void onFailure(SResponse response, String tag) {
        Toast.makeText(this, response.toString(), Toast.LENGTH_SHORT).show();
    }
}
