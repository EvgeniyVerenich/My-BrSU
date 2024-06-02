package by.brsucd.mybrsu

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MovableContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import by.brsucd.mybrsu.dbproperties.FeedReaderContract
import by.brsucd.mybrsu.dbproperties.FeedReaderDbHelper
import by.brsucd.mybrsu.dbproperties.FirebaseProp
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "home"){
                composable("home"){
                    HomeActivity(navController = navController)
                }
                composable("timeTable"){
                    TimeTableActivity(navController = navController)
                }
                composable("structure"){
                    StructureActivity(navController = navController)
                }
                composable(
                    route = "topics/?title={title}&mainText={mainText}&img={img}&resource={resource}",
                    arguments = listOf(
                        navArgument("title"){
                            type = NavType.StringType
                            defaultValue = "Новости"
                        },
                        navArgument("mainText"){
                            type = NavType.StringType
                            defaultValue = "Text"
                        },
                        navArgument("img"){
                            type = NavType.IntType
                            defaultValue = R.drawable.university
                        },
                        navArgument("resource"){
                            type = NavType.StringType
                            defaultValue = "home"
                        }
                    )
                ){backStackEntry ->
                    backStackEntry.arguments?.getInt("img")?.let {
                        TopicsActivity(
                            navController = navController,
                            title = backStackEntry.arguments?.getString("title").toString(),
                            mainText = backStackEntry.arguments?.getString("mainText").toString(),
                            header = backStackEntry.arguments?.getString("title").toString(),
                            imgId = it,
                            resource = backStackEntry.arguments?.getString("resource").toString()
                        )
                    }
                }
            }

        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun ScrollingContent(navController: NavController){
    Column (
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(top = 50.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.SpaceBetween
        ) {
        UniversityInfo(navController, "Общие сведения", stringResource(id = R.string.university))
        UniversityEvents(navController)
        StudentsSales(navController)
        UniversityNews(navController)
    }
}

@ExperimentalMaterial3Api
@Composable
fun UniversityNews(navController: NavController){
    Column (
        modifier = Modifier.padding(10.dp)
    ){
        Text(text = "Новости Университета", fontSize = 20.sp)
        NewsListItem(topic = "Информационная встреча с прокурором города Бреста Андреем Чеславовичем Володько", navController, text = stringResource(id = R.string.news1), R.drawable.news1)
        NewsListItem(topic = "Путешествие в историю", navController, text = stringResource(R.string.news2), R.drawable.news2)
    }
}

@ExperimentalMaterial3Api
@Composable
fun NewsListItem(topic: String, navController: NavController, text: String, imgId: Int){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        onClick = {
            navController.navigate("topics/?title=$topic&mainText=$text&img=$imgId&resource=home")
        }
    ) {
        Box {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = imgId),
                    contentDescription = "uni",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RectangleShape)
                        .padding(10.dp)
                )
                Column(
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text(text = shortening(topic), fontSize = 20.sp)
                }
            }
        }
    }
}

fun shortening(str: String): String{
    return if (str.length > 32){
        str.substring(0, 32) + "..."
    }else{
        str
    }
}

@ExperimentalMaterial3Api
@Composable
fun UniversityEvents(navController: NavController){
    Column (
        modifier = Modifier.padding(10.dp)
    ){
        Text(text = "События Университета", fontSize = 20.sp)
        EventsListItem(title = "Встреча", description = "Ректор Юрий Петрович Голубев встретился с творческими коллективами университета и их руководителями", date ="30.05.2024", navController, stringResource(
            id = R.string.meet
        ), R.drawable.meet)
        EventsListItem(title = "Конкурс", description = "Итоги конкурса профессионального мастерства «Мастер практических занятий» – 2024", date ="31.05.2024", navController, stringResource(
            id = R.string.concurs
        ), R.drawable.concurs)
    }
}

@ExperimentalMaterial3Api
@Composable
fun StudentsSales(navController: NavController){
    Column(
        modifier = Modifier.padding(10.dp)
    ) {
        Text(text = "Скидки для студентов", fontSize = 20.sp)
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState())
        ) {
            SalesListItem(title = "Оптика Prosvet", navController = navController, text = stringResource(
                id = R.string.prosvet
            ), R.drawable.logoprosvet_xxl)
            SalesListItem(title = "Burger King", navController = navController, text = stringResource(
                id = R.string.bk
            ), R.drawable.bk)
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun EventsListItem(title: String, description: String, date: String, navController: NavController, text: String, imgId: Int){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        onClick = {
            navController.navigate("topics/?title=$description&mainText=$text&img=$imgId&resource=home")
        }
    ) {
        Box {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = imgId),
                    contentDescription = "uni",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(68.dp)
                )
                Column(
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    Text(text = title, fontSize = 20.sp)
                    Text(text = shortening(description), fontSize = 15.sp)
                    Text(text = date, fontSize = 10.sp)
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun SalesListItem(title: String, navController: NavController, text: String, imgId: Int){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .size(300.dp, 240.dp)
            .padding(10.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        onClick = {
            navController.navigate("topics/?title=$title&mainText=$text&img=$imgId&resource=home")
        }
    ) {
        Box {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Image(
                    painter = painterResource(id = imgId),
                    contentDescription = "uni",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(200.dp, 160.dp)
                        .height(150.dp)
                )
                Column(
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text(text = title, fontSize = 20.sp)
                }
            }
        }
    }
}

@Composable
fun UniversityInfo(navController: NavController, title: String, text: String){
    Card (
        modifier = Modifier
            .padding(10.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ){
        Box(
            contentAlignment = Alignment.BottomCenter
        ) {
            Image( painter = BitmapPainter(ImageBitmap.imageResource(R.drawable.univer2))
                , contentDescription = "university", modifier = Modifier.fillMaxWidth())
            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                contentAlignment = Alignment.BottomCenter
            ){
                Button(onClick = {
                    navController.navigate("topics/?title=$title&mainText=$text&img=${R.drawable.university}&resource=home")
                }, colors = ButtonDefaults.buttonColors(
                    Color(35, 141, 158)
                ), modifier = Modifier.width(300.dp)) {
                    Text(text = "О нас")
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@ExperimentalMaterial3Api
@Composable
fun HomeActivity(navController: NavController){
    val headerTitle = remember {
        mutableStateOf("Мой университет")
    }
    Scaffold (
        topBar = { AppHeader(headerTitle.value)},
        bottomBar = {
            BottomAppBar (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(230, 238, 250)),
                actions = {
                    Row (
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ){
                        IconButton(onClick = {
                            navController.navigate("home")
                        }) {
                            Icon(Icons.Outlined.Home, contentDescription = "Home")
                        }
                        IconButton(onClick = {
                            navController.navigate("timeTable")
                        }) {
                            Icon(Icons.Outlined.DateRange, contentDescription = "TimeTable")
                        }
                        IconButton(onClick = {
                            navController.navigate("structure")
                        }) {
                            Icon(Icons.Outlined.Menu, contentDescription = "Menu")
                        }
                    }
                }
            )
        }
    ){
        ScrollingContent(navController)
    }
}

@Composable
fun AppHeader(title: String){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(230, 238, 250))
            .height(50.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp), contentAlignment = Alignment.CenterStart){
            Text(text = title, fontSize = 20.sp)
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun TimeTable(){
    val expendedCourse = remember {
        mutableStateOf(false)
    }
    val expendedFaculty = remember {
        mutableStateOf(false)
    }
    val course = remember {
        mutableStateOf("1 курс")
    }
    val faculty = remember {
        mutableStateOf("Физико-математический факультет")
    }
    Column (
        modifier = Modifier.padding(top = 50.dp, bottom = 80.dp)
    ){
        TextField(value = "", onValueChange = {}, modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Column (
                verticalArrangement = Arrangement.Center
            ){
                TextButton(onClick = {expendedCourse.value = true}) {
                    Text(text = course.value)
                }
                DropdownMenu(expanded = expendedCourse.value, onDismissRequest = { expendedCourse.value = false }) {
                    Text(text = "1 курс",
                        Modifier
                            .padding(5.dp)
                            .clickable {
                                course.value = "1 курс"
                            })
                    Text(text = "2 курс",
                        Modifier
                            .padding(5.dp)
                            .clickable { course.value = "2 курс" })
                    Text(text = "3 курс",
                        Modifier
                            .padding(5.dp)
                            .clickable { course.value = "3 курс" })
                    Text(text = "4 курс",
                        Modifier
                            .padding(5.dp)
                            .clickable { course.value = "4 курс" })
                }
            }
            Column {
                TextButton(onClick = {expendedFaculty.value = true}) {
                    Text(text = faculty.value)
                }
                DropdownMenu(expanded = expendedFaculty.value, onDismissRequest = { expendedFaculty.value = false }) {
                    Text(text = "Физико-Математический Факультет",
                        Modifier
                            .padding(5.dp)
                            .clickable { faculty.value = "Физико-Математический Факультет" })
                    Text(text = "Факультет естествознания",
                        Modifier
                            .padding(5.dp)
                            .clickable { faculty.value = "Факультет естествознания" })
                    Text(text = "Юредический факультет",
                        Modifier
                            .padding(5.dp)
                            .clickable { faculty.value = "Юредический факультет" })
                }
            }
        }
        TextField(value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth())
    }


}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun TimeTableActivity(navController: NavController){
    Scaffold (
        topBar = { AppHeader("Расписание")},
        bottomBar = {
            BottomAppBar (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(230, 238, 250)),
                actions = {
                    Row (
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ){
                        IconButton(onClick = {
                            navController.navigate("home")
                        }) {
                            Icon(Icons.Outlined.Home, contentDescription = "Home")
                        }
                        IconButton(onClick = {
                            navController.navigate("timeTable")
                        }) {
                            Icon(Icons.Outlined.DateRange, contentDescription = "TimeTable")
                        }
                        IconButton(onClick = { navController.navigate("structure") }) {
                            Icon(Icons.Outlined.Menu, contentDescription = "Menu")
                        }
                    }
                }
            )
        }
    ){
        TimeTable()
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun StructureActivity(navController: NavController){
    Scaffold (
        topBar = { AppHeader("Структура")},
        bottomBar = {
            BottomAppBar (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(230, 238, 250)),
                actions = {
                    Row (
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ){
                        IconButton(onClick = {
                            navController.navigate("home")
                        }) {
                            Icon(Icons.Outlined.Home, contentDescription = "Home")
                        }
                        IconButton(onClick = {
                            navController.navigate("timeTable")
                        }) {
                            Icon(Icons.Outlined.DateRange, contentDescription = "TimeTable")
                        }
                        IconButton(onClick = { navController.navigate("structure") }) {
                            Icon(Icons.Outlined.Menu, contentDescription = "Menu")
                        }
                    }
                }
            )
        }
    ){
        StructureItem(navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoCard(mainText: String, imgId: Int, navController: NavController){
    Card(
        modifier = Modifier.size(100.dp, 150.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        onClick = {
            navController.navigate("topics/?title=$mainText&mainText=text&img=$imgId&resource=structure")
        }
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .size(80.dp, 80.dp)
                .padding(10.dp),
            alignment = Alignment.Center,
            painter = painterResource(id = imgId),
            contentDescription = ""
        )
        Text(modifier = Modifier
            .padding(1.dp)
            .fillMaxWidth(), text = mainText, style = TextStyle(textAlign = TextAlign.Center))
    }
}

@Composable
fun StructureItem(navController: NavController){
    var showFaculties by remember {
        mutableStateOf(true)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp, bottom = 80.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Button(onClick = { showFaculties = true }, modifier = Modifier.padding(5.dp)) {
                Text(text = "факультеты")
            }
            Button(onClick = { showFaculties = false}, modifier = Modifier.padding(5.dp)) {
                Text(text = "организации")
            }
        }
        Column {
            if (showFaculties){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    InfoCard(mainText = "Физико-математический факультет", imgId = R.drawable.physm, navController)
                    InfoCard(mainText = "Юридический факультет", imgId = R.drawable.law, navController)
                    InfoCard(mainText = "Факультет естествознания", imgId = R.drawable.natsci, navController)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    InfoCard(mainText = "Исторический факультет", imgId = R.drawable.hist, navController)
                    InfoCard(mainText = "Социально-педагогический факультет", imgId = R.drawable.socp, navController)
                    InfoCard(mainText = "Факультет иностранных языков", imgId = R.drawable.language, navController)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    InfoCard(mainText = "Факультет физического воспитания и спорта", imgId = R.drawable.sport, navController)
                    InfoCard(mainText = "Филологический факультет", imgId = R.drawable.phil, navController)
                    InfoCard(mainText = "Психолого-педагогический факультет", imgId = R.drawable.psychp, navController)
                }
            }else{
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    InfoCard(mainText = "БРСМ", imgId = R.drawable.brsm, navController)
                    InfoCard(mainText = "Студенческий профсоюз", imgId = R.drawable.studsoviet, navController)
                    InfoCard(mainText = "Студенческий совет", imgId = R.drawable.studsoviet, navController)
                }
            }

        }
    }
}

@Composable
fun ShowTopics(title: String, mainText: String, imgId: Int){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = 55.dp)
    ) {
        Image(
            painter = painterResource(id = imgId),
            contentDescription = "image topic",
            modifier = Modifier
                .fillMaxWidth()
                .size(260.dp, 240.dp)
                .padding(bottom = 10.dp)
                .clip(RoundedCornerShape(15.dp)))
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 80.dp)
        ){
            Text(text = title, style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Justify))
            Text(text = mainText, style = TextStyle(fontSize = 20.sp, textAlign = TextAlign.Justify))
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun TopicsActivity(navController: NavController, title: String,
                   mainText: String, header: String, imgId: Int,
                   resource: String){
    Scaffold (
        topBar = {
                 Row {
                     IconButton(onClick = {
                         navController.navigate(resource){
                             popUpTo(resource)
                         }
                     }, modifier = Modifier
                         .size(50.dp)
                         .background(
                             Color(230, 238, 250)
                         )) {
                         Icon(Icons.Outlined.ArrowBack, contentDescription = "goBack")
                     }
                     AppHeader(header)
                 }
        },
        bottomBar = {
            BottomAppBar (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(230, 238, 250)),
                actions = {
                    Row (
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ){
                        IconButton(onClick = {
                            navController.navigate("home")
                        }) {
                            Icon(Icons.Outlined.Home, contentDescription = "Home")
                        }
                        IconButton(onClick = {
                            navController.navigate("timeTable")
                        }) {
                            Icon(Icons.Outlined.DateRange, contentDescription = "TimeTable")
                        }
                        IconButton(onClick = { navController.navigate("structure") }) {
                            Icon(Icons.Outlined.Menu, contentDescription = "Menu")
                        }
                    }
                }
            )
        }
    ){
        ShowTopics(title = title, mainText = mainText, imgId)
    }
}