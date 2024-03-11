package by.brsucd.mybrsu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import by.brsucd.mybrsu.ui.theme.MyBrSUTheme

class LoadingActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyBrSUTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Loading()
                }
            }
        }
    }

    @Composable
    fun loadingText(text : String, modifier: Modifier){
        Text(text = text, modifier = modifier)
        
    }
    
    @Preview(showBackground = true)
    @Composable
    fun Loading(){
        loadingText(text ="MyBRSU", modifier = Modifier)
    }


}