package com.example.nexguard.feature.home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.VideocamOff
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nexguard.R

private val AccentBlue = Color(0xFF3B9EFF)
private val AccentGreen = Color(0xFF2BD9A0)
private val TextMuted = Color(0xFF6B7688)
private val TextSubtle = Color(0xFF8B95A5)
private val CardBorder = Color(0xFF1C2432)


@Composable
fun HomeScreen(
    onCameraClick: () -> Unit,
    onMonitorClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        // Background image (kept as-is, per your existing asset)
        Image(
            painter = painterResource(R.drawable.homescreen_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 20.dp)
        ) {

            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "NEX",
                            color = AccentBlue,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "GUARD",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = "Your world, secured.",
                        color = TextMuted,
                        fontSize = 12.sp
                    )
                }

                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Profile",
                        tint = AccentBlue,
                        modifier = Modifier.size(34.dp)
                    )
                }
            }

            // Welcome text
            Text(
                text = "Welcome back,",
                color = Color.White,
                fontSize = 19.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Stay safe. Stay connected.",
                color = TextMuted,
                fontSize = 12.sp,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            // Security status card
            SecurityStatusCard(
                camerasOnline = 0,
                statusMessage = "All clear at the moment."
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Action cards
            HomeActionCard(
                title = "Use this device as camera",
                subtitle = "Turn this device into a security camera.",
                icon = Icons.Default.Videocam,
                accentColor = AccentBlue,
                onClick = onCameraClick
            )

            Spacer(modifier = Modifier.height(14.dp))

            HomeActionCard(
                title = "Monitor camera",
                subtitle = "Watch your connected cameras live.",
                icon = Icons.Default.Visibility,
                accentColor = AccentGreen,
                onClick = onMonitorClick
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Your cameras section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Your cameras",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "See all",
                    color = AccentBlue,
                    fontSize = 12.sp,
                    modifier = Modifier.clickable { }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            EmptyCamerasCard()
        }

        // Bottom nav
        BottomNavBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}

@Composable
fun SecurityStatusCard(
    camerasOnline: Int,
    statusMessage: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(AccentBlue.copy(alpha = 0.08f))
            .border(
                width = 0.5.dp,
                color = AccentBlue.copy(alpha = 0.3f),
                shape = RoundedCornerShape(14.dp)
            )
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .background(AccentBlue.copy(alpha = 0.15f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Shield,
                contentDescription = null,
                tint = AccentBlue,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = "Security status", color = AccentBlue, fontSize = 11.sp)
            Text(
                text = "$camerasOnline cameras online",
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(text = statusMessage, color = TextMuted, fontSize = 12.sp)
        }

        Box(
            modifier = Modifier
                .size(8.dp)
                .background(AccentGreen, CircleShape)
        )
    }
}

@Composable
fun HomeActionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    accentColor: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(accentColor.copy(alpha = 0.1f))
            .border(
                width = 0.5.dp,
                color = accentColor,
                shape = RoundedCornerShape(14.dp)
            )
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(accentColor.copy(alpha = 0.18f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = accentColor,
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = subtitle,
                color = TextSubtle,
                fontSize = 12.sp,
                lineHeight = 16.sp
            )
        }

        Box(
            modifier = Modifier
                .size(30.dp)
                .background(accentColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Open",
                tint = Color.Black,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
fun EmptyCamerasCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White.copy(alpha = 0.02f))
            .border(
                width = 0.5.dp,
                color = CardBorder,
                shape = RoundedCornerShape(14.dp)
            )
            .padding(vertical = 28.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.VideocamOff,
            contentDescription = null,
            tint = Color(0xFF3A4152),
            modifier = Modifier.size(26.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "No cameras connected yet",
            color = Color(0xFFD6DAE2),
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = "Pair a device or add a camera to get started.",
            color = TextMuted,
            fontSize = 11.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun BottomNavBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(Color(0xFF05070C))
            .border(
                width = 0.5.dp,
                color = CardBorder
            )
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        NavItem(icon = Icons.Default.Videocam, label = "Home", selected = true)
        NavItem(icon = Icons.Default.Videocam, label = "Cameras", selected = false)
        NavItem(icon = Icons.Default.NotificationsNone, label = "Alerts", selected = false)
        NavItem(icon = Icons.Default.Person, label = "Profile", selected = false)
    }
}

@Composable
private fun NavItem(icon: ImageVector, label: String, selected: Boolean) {
    val color = if (selected) AccentBlue else TextMuted
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = color,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(text = label, color = color, fontSize = 10.sp)
    }
}