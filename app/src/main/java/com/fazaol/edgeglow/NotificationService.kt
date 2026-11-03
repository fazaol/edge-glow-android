
package com.fazaol.edgeglow

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.provider.Settings
import android.util.Log

class NotificationService : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        super.onNotificationPosted(sbn)

        val packageName = sbn.packageName

        // Ignora as notificações do próprio EDGE GLOW
        if (packageName == this.packageName) return

        val title = sbn.notification.extras
            .getCharSequence("android.title")?.toString()

        val message = sbn.notification.extras
            .getCharSequence("android.text")?.toString()

        Log.d(
            "EDGE_GLOW",
            "Notificação: $packageName | $title | $message"
        )

        if (Settings.canDrawOverlays(this)) {
            GlowService.show(this)
        }
    }
}
