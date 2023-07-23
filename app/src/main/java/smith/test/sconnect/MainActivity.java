package smith.test.sconnect;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import java.util.Map;
import smith.lib.net.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void check(View v) {
        
        String tag = "someTag"; 
        String url = "https://smithdev.t.me";
        
        if (SConnect.isDeviceConnected(this)) {
            SConnect.with(this)
                .callback(new SConnectCallBack() {
                    @Override public void onSuccess(SResponse response, @NonNull String tag, Map<String, Object> responseHeaders) {
                        if (tag.equals("someTag"))
                            Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this, "response of another request with another tag", Toast.LENGTH_SHORT).show();

                        // a workaround of SResponse class
                        SResponse.Array array = response.getArray();
                        array.forEach(item -> {
                            Object o = item;
                        });

                        SResponse.Map map = array.getMap(0);
                        map.forEach((key, value) -> {
                            String k = key;
                            Object v = value;
                        });
                    }

                    @Override public void onFailure(@NonNull SResponse response, String tag) {
                        Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
                .url(url)
                .tag(tag)
                .get();
        }
    }
}
