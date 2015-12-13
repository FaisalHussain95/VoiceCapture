package fr.product.slf.calls;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private boolean detectEnabled;

    private TextView textViewDetectState;
    private Button buttonToggleDetect;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewDetectState = (TextView) findViewById(R.id.textViewDetectState);

        buttonToggleDetect = (Button) findViewById(R.id.buttonDetectToggle);
        buttonToggleDetect.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setDetectEnabled(!detectEnabled);
            }
        });

        Button buttonExit = (Button) findViewById(R.id.buttonExit);
        buttonExit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setDetectEnabled(false);
                MainActivity.this.finish();
            }
        });
    }


    private void setDetectEnabled(boolean enable) {
        detectEnabled = enable;

        Intent intent = new Intent(this, CallDetectService.class);
        if (enable) {
            // start detect service
            startService(intent);

            buttonToggleDetect.setText(R.string.turnOffService);
            textViewDetectState.setText(R.string.ServiceOn_msg);
        }
        else {
            // stop detect service
            stopService(intent);

            buttonToggleDetect.setText(R.string.turnOnService);
            textViewDetectState.setText(R.string.ServiceOff_msg);
        }
    }

}
