
package com.fazaol.edgeglow

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Background = Color(0xFF090B16)
private val CardColor = Color(0xFF151A2B)
private val NeonBlue = Color(0xFF29C7FF)
private val NeonPurple = Color(0xFF9B5CFF)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EdgeGlowScreen(
                onNotificationPermission = {
                    startActivity(
                        Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
                    )
                },
                onOverlayPermission = {
                    startActivity(
                        Intent(
                            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:$packageName")
                        )
                    )
                }
            )
        }
    }
}

@Composable
fun EdgeGlowScreen(
    onNotificationPermission: () -> Unit,
    onOverlayPermission: () -> Unit
) {
    var selectedEffect by remember { mutableStateOf("Glow") }

    val effects = listOf(
        "Glow", "Pulse", "Rainbow", "Wave",
        "Neon", "Breathing", "Dual Color"
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(22.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "EDGE GLOW",
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )

            Text(
                "SMART NOTIFICATIONS",
                fontSize = 11.sp,
                letterSpacing = 3.sp,
                color = NeonBlue
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .background(
                        Brush.linearGradient(
                            listOf(
                                Color(0xFF152A50),
                                Color(0xFF301957),
                                Color(0xFF101426)
                            )
                        ),
                        RoundedCornerShape(24.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("✦", fontSize = 48.sp, color = NeonBlue)
                    Text(
                        "Seu celular, com mais luz.",
                        color = Color.White,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        "Personalize suas notificações",
                        color = Color.LightGray,
                        fontSize = 12.sp
                    )
                }
            }

            Text(
                "CONFIGURAÇÕES NECESSÁRIAS",
                color = NeonBlue,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )

            Button(
                onClick = onNotificationPermission,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF263A68)
                ),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Autorizar acesso às notificações")
            }

            Button(
                onClick = onOverlayPermission,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF493078)
                ),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Autorizar iluminação sobre outros apps")
            }

            Text(
                "EFEITOS DE ILUMINAÇÃO",
                color = NeonBlue,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp
            )

            effects.forEach { effect ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            if (selectedEffect == effect)
                                Color(0xFF242448)
                            else CardColor,
                            RoundedCornerShape(12.dp)
                        )
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        effect,
                        color = Color.White,
                        modifier = Modifier.weight(1f)
                    )
                    RadioButton(
                        selected = selectedEffect == effect,
                        onClick = { selectedEffect = effect },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = NeonPurple
                        )
                    )
                }
            }

            Text(
                "EDGE GLOW • VERSION 1.0",
                color = Color.Gray,
                fontSize = 10.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}
