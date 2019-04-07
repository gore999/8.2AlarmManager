package rodriguezfernandez.carlos.standup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToggleButton alarmToggle=findViewById(R.id.alarmToggle);
        alarmToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String toastMessage;
                if(isChecked){
                    toastMessage="Stand Up Alarm On!";
                }else{
                    toastMessage="Stand Up Alarm Off";
                }
                Toast.makeText(MainActivity.this, toastMessage,Toast.LENGTH_LONG).show();
            }
        });
    }
}
