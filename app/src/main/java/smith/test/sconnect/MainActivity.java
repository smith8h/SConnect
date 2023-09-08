package smith.test.sconnect;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import java.util.HashMap;
import java.util.Map;
import smith.lib.net.*;

/** @noinspection UnnecessaryLocalVariable*/
@SuppressWarnings({"unused"})
public class MainActivity extends AppCompatActivity implements SConnectCallBack {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // simple example of how to use SConnect library.
    // easy codes, and fast connections.
    public void check(View v) {
        
        String tag = "someTag"; // optional.
        String url = "https://smithdev.t.me"; // required.
        
        if (SConnect.isDeviceConnected(this)) { // check internet availability.
            SConnect.with(this) // passing current context.
                    .callback(this) // required to get responses.
                    .url(url) // required.
                    .tag(tag) // optional.
                    .addParams(new HashMap<>()) // optional, but required for some methods.
                    .paramsType(SConnect.BODY) // optional, but required for some methods.
                    .addHeaders(new HashMap<>()) // optional, but required for some methods.
                    .get(); // post, delete, put, head, options and patch.
        }
    }

    @Override
    public void onSuccess(SResponse response, @NonNull String tag, Map<String, Object> responseHeaders) {
        if (tag.equals("someTag")) // check tag
            Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show(); // get whole response as string
        else
            Toast.makeText(MainActivity.this, "response of another request with another tag", Toast.LENGTH_SHORT).show();

        // a workaround of SResponse class
        SResponse.Array array = response.getArray(); // get response if it was a json array
        array.forEach(item -> { // iterate through its items
            int o = (int) item;
        });

        SResponse.Map map = array.getMap(0); // get an object (map) from that array
        map.forEach((key, value) -> { // iterate through its content by key and value for each data.

            String k = key;
            boolean v = (boolean) value;
        });
    }

    @Override
    public void onFailure(@NonNull SResponse response, String tag) {
        Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
    }
}
