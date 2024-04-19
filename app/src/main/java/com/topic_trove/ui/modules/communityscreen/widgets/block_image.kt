package com.topic_trove.ui.modules.communityscreen.widgets

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun ImageBlock(){
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var context = LocalContext.current
    var bitmap by remember{
        mutableStateOf<Bitmap?>(null)
    }
    
    var launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {uri: Uri?->
        imageUri = uri
        println(imageUri)
    }
    Column {
        AddImageRow {
            launcher.launch("image/*")
        }
        Spacer(modifier = Modifier.height(16.dp))
        imageUri?.let{
            if(Build.VERSION.SDK_INT < 28){
                bitmap = MediaStore.Images
                    .Media.getBitmap(context.contentResolver,it)
            }else{
                val source = ImageDecoder.createSource(context.contentResolver,it)
                bitmap = ImageDecoder.decodeBitmap(source)
            }

            bitmap?.let{
                Image(bitmap = it.asImageBitmap(), contentDescription = null)
            }
        }
    }
}