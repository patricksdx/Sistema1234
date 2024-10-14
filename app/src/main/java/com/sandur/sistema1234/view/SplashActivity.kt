package com.sandur.sistema1234.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.sandur.sistema1234.R
import com.sandur.sistema1234.ui.theme.Sistema1234Theme
import com.sandur.sistema1234.utils.UserStore
import com.sandur.sistema1234.view.labelCard.user.ProfileActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SplashActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UseOfNonLambdaOffsetOverload")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var startAnimation by remember { mutableStateOf(false) }
            val offsety by animateDpAsState(
                targetValue = if(startAnimation) 0.dp else 1500.dp,
                animationSpec = tween(durationMillis = 1000)
            )
            val alpha by animateFloatAsState(
                targetValue = if(startAnimation) 1f else 0f,
                animationSpec = tween(durationMillis = 3000)
            )
            val scale by animateFloatAsState(
                targetValue = if(startAnimation) 1.5f else 3f,
                animationSpec = tween(durationMillis = 2000)
            )
            Sistema1234Theme {
                LaunchedEffect(key1 = true) {
                    startAnimation = true
                    delay(4000)
                    lifecycleScope.launch {
                        val userStore = UserStore(this@SplashActivity)
                        val user = userStore.geUser.first()

                        if (user != ""){
                            startActivity(Intent(this@SplashActivity, ProfileActivity::class.java))
                        } else {
                            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                        }
                    }
                }

                Scaffold(
                ) { paddingValues ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = null,
                            modifier = Modifier
                                .offset(y = offsety)
                                .graphicsLayer(
                                    alpha = alpha,
                                    scaleX = scale,
                                    scaleY = scale
                                )
                                .height(200.dp)
                        )
                    }
                }
            }
        }
    }
}
