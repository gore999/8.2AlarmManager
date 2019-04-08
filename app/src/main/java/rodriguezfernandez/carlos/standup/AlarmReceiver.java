package rodriguezfernandez.carlos.standup;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {
    //Crear variable para almacenar un NotificationManager
    private NotificationManager mNotificationManager;
    // Entero que hará de ID.
    private static final int NOTIFICATION_ID = 0;
    // Cadena que hara de ID del Canal de notificacion.
    private static final String PRIMARY_CHANNEL_ID ="primary_notification_channel";
    @Override
    public void onReceive(Context context, Intent intent) {
        mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        // Enviar la notificación.
        deliverNotification(context);
    }

    private void deliverNotification(Context context) {
        // Creamos un intent que lanzará a esta misma aplicacion.
        Intent contentIntent = new Intent(context, MainActivity.class);
        //La metemos en un Pending intent
        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (context, NOTIFICATION_ID, contentIntent, PendingIntent
                        .FLAG_UPDATE_CURRENT);
        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder
                (context, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stand_up)
                .setContentTitle(context.getString(R.string.stand_alert))
                .setContentText(context.getString(R.string.levantate))
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        // Deliver the notification
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
