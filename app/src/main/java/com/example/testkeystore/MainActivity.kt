package com.example.testkeystore

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.testkeystore.ui.theme.TestKeyStoreTheme
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : ComponentActivity() {

    private lateinit var cameraExecutor: ExecutorService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestKeyStoreTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Greeting("Android")
                        Spacer(modifier = Modifier.height(16.dp))
                        MyButton()
                    }
                }
            }
        }
    }

//    private fun startCamera() {
//        val context = this
//        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
//        cameraProviderFuture.addListener({
//            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
//            val preview = androidx.camera.core.Preview.Builder().build().also {
//                it.setSurfaceProvider(findViewById<PreviewView>(R.id.previewView).surfaceProvider)
//            }
//
//            try {
//                cameraProvider.unbindAll()
//                cameraProvider.bindToLifecycle(this, androidx.camera.core.CameraSelector.DEFAULT_BACK_CAMERA, preview)
//            } catch (exc: Exception) {
//                Toast.makeText(this, "无法启动摄像头: ${exc.message}", Toast.LENGTH_SHORT).show()
//            }
//        }, ContextCompat.getMainExecutor(context))
//    }

    // 按钮的点击事件处理
    private fun onButtonClick() {
//        Toast.makeText(this, "按钮已点击!", Toast.LENGTH_SHORT).show()

        // 创建 testkey 类的实例并调用 getkey 方法

        val testKeyInstance = testkey()
        val isExist = testKeyInstance.hasKeyStoreEntry()
        if (isExist) {
            Toast.makeText(this, "密钥已成功检索!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "未能检索到密钥!", Toast.LENGTH_SHORT).show()
        }

//        val secretKey = testKeyInstance.getkey()
//
//        // 可以在此处使用 secretKey 做进一步的处理
//        if (secretKey != null) {
//            Toast.makeText(this, "密钥已成功检索!", Toast.LENGTH_SHORT).show()
//        } else {
//            Toast.makeText(this, "未能检索到密钥!", Toast.LENGTH_SHORT).show()
//        }
    }

    @Composable
    fun MyButton() {
        Button(
            onClick = {
                onButtonClick() // 调用点击处理函数
            }
        ) {
            Text(text = "android tee key test")
        }
    }


//    @Composable
//    fun CameraButton() {
//        val context = LocalContext.current
//        Button(
//            onClick = {
//                startCamera() // 点击时调用打开摄像头的函数
//            }
//        ) {
//            Text(text = "打开摄像头")
//        }
//    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TestKeyStoreTheme {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Greeting("Android")
            Spacer(modifier = Modifier.height(16.dp))
            MainActivity().MyButton() // 在预览中显示按钮
        }
    }
}



