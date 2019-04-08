package rodriguezfernandez.carlos.standup;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    //Constantes para el uso de notificaciones
    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID="primary_notification_channel";
    private NotificationManager mNotificationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //iniciar mNorification
        mNotificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        ToggleButton alarmToggle=findViewById(R.id.alarmToggle);//Creo el boton
        //Task 3 : inicializar el alarmmanager.
        Intent intent=new Intent(this,AlarmReceiver.class);
        boolean alarmUp=(PendingIntent.getBroadcast(this,NOTIFICATION_ID,intent,PendingIntent.FLAG_NO_CREATE)!=null);

        final PendingIntent notifyPendinfIntent=PendingIntent.getActivity(this,NOTIFICATION_ID,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        final AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);

        alarmToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String toastMessage;
                if(isChecked){
                    long repeatInterval=AlarmManager.INTERVAL_FIFTEEN_MINUTES;
                    long triggerTime=SystemClock.elapsedRealtime()+repeatInterval;//Intervalo mas momento actual.
                    if(alarmManager!=null){
                        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerTime,repeatInterval,notifyPendinfIntent);
                    }

                    toastMessage=getString(R.string.alarma_toast);
                }else{
                    mNotificationManager.cancelAll();
                    if(alarmManager!=null){
                        alarmManager.cancel(notifyPendinfIntent);
                    }
                    toastMessage=getString(R.string.alarma_toast_off);
                }
                Toast.makeText(MainActivity.this, toastMessage,Toast.LENGTH_LONG).show();
            }
        });
        //Crear el canal de notificacion:
        createNotificationChannel();
    }
    //Metodo para crear el Notification channel
    public void createNotificationChannel(){
        mNotificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        //Como los canales de notificacion solo están disponibles desde OREO, hacemos un check de la version
        if(Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.O){
            //Creamos el canal. Necesita su id, el texto y la importancia.
            NotificationChannel notificationChannel=new NotificationChannel(PRIMARY_CHANNEL_ID,getString(R.string.stand_notif),NotificationManager.IMPORTANCE_HIGH);
            //Añadirle propiedades.
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription(getString(R.string.notificationChannel_description));
            //Finalmente, creamos el canal en el manager, pasandoselo como parametro.
            mNotificationManager.createNotificationChannel(notificationChannel);

        }
    }

}
