package com.memzchrome.cloudgame.pages

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.memzchrome.cloudgame.BuildConfig
import com.memzchrome.cloudgame.Configuration
import com.memzchrome.cloudgame.ContentActivity
import com.memzchrome.cloudgame.R

@Composable
fun MenuItem(text: String, onClick: () -> Unit, hideArrow: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text, fontSize = 18.sp)

        if (!hideArrow) {
            Icon(
                Icons.Default.ArrowForward,
                contentDescription = text,
                Modifier.size(16.dp).alpha(0.9f)
            )
        }
    }
}

@Composable
fun AboutPage(paddingValues: PaddingValues, onCheckUpdates: () -> Unit) {
    val context = LocalContext.current
    var clickCount by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize().padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(40.dp))
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "App Icon",
            modifier = Modifier
                .size(80.dp)
                .clip(shape = androidx.compose.foundation.shape.CircleShape)
                .background(Color.Gray)
        )
        Spacer(modifier = Modifier.width(12.dp).height(4.dp))
        Text(
            text = "云游戏PC ${BuildConfig.VERSION_NAME} (Build ${BuildConfig.VERSION_CODE})", 
            fontSize = 16.sp,
            modifier = Modifier.clickable {
                clickCount++
                if (clickCount >= 5) {
                    Configuration.getConfiguration().setBooleanValue("developer_mode", true)
                    clickCount = 0
                }
            }
        )
        Text(
            "作者: MEMZ-Chrome",
            fontSize = 14.sp,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://space.bilibili.com/1353783215"))
                context.startActivity(intent)
            }
        )
        Text(
            text = "云游戏PC客户端，支持云原神和云星铁",
            fontSize = 12.sp,
            modifier = Modifier.fillMaxWidth(0.8f)
        )
        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .padding(PaddingValues(15.dp, 0.dp))
                .shadow(3.dp, shape = RoundedCornerShape(5.dp))
                .clip(RoundedCornerShape(5.dp))
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Menu items
            MenuItem("设置", onClick = {
                val intent = Intent(context, ContentActivity::class.java)
                intent.putExtra("page", "settings")
                context.startActivity(intent)
            })
        }
    }
}

