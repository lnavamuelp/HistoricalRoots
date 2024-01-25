package com.openuax.myhistoricalrootsapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.openuax.myhistoricalrootsapplication.interfaces.LoginForm
import com.openuax.myhistoricalrootsapplication.ui.theme.MyHistoricalRootsApplicationTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            MyHistoricalRootsApplicationTheme {
                LoginForm()
            }
        }
    }
}