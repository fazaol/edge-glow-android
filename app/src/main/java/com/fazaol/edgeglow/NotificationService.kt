
package com.fazaol.edgeglow

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log

class NotificationService : NotificationListenerService() {

    override fun onNotificationPosted(
        sbn: StatusBarNotification
    ) {
        super.onNotificationPosted(sbn)

        val appName = sbn.packageName
        val notification = sbn.notification
        val extras = notification.extras

        val title = extras.getCharSequence("android.title")?.toString()
        val message = extras.getCharSequence("android.text")?.toString()

        Log.d(
            "EDGE_GLOW",
            "Notificação recebida: $appName | $title | $message"
        )
    }

    override fun onNotificationRemoved(
        sbn: StatusBarNotification
    ) {
        super.onNotificationRemoved(sbn)

        Log.d(
            "EDGE_GLOW",
            "Notificação removida: ${sbn.packageName}"
        )
    }
}
