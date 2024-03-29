package by.brsucd.mybrsu

import android.annotation.SuppressLint
import android.os.Bundle
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
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

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
                    route = "topics/?title={title}&mainText={mainText}&resource={resource}",
                    arguments = listOf(
                        navArgument("title"){
                            type = NavType.StringType
                            defaultValue = "Новости"
                        },
                        navArgument("mainText"){
                            type = NavType.StringType
                            defaultValue = "Text"
                        },
                        navArgument("resource"){
                            type = NavType.StringType
                            defaultValue = "home"
                        }
                    )
                ){backStackEntry ->
                    TopicsActivity(
                        navController = navController,
                        title = backStackEntry.arguments?.getString("title").toString(),
                        mainText = backStackEntry.arguments?.getString("mainText").toString(),
                        header = backStackEntry.arguments?.getString("title").toString(),
                        resource = backStackEntry.arguments?.getString("resource").toString()
                    )
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
        UniversityInfo(navController, "title", "text")
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
        NewsListItem(topic = "Something", navController, text = "TextText")
        NewsListItem(topic = "Something", navController, text = "TextText")
    }
}

@ExperimentalMaterial3Api
@Composable
fun NewsListItem(topic: String, navController: NavController, text: String){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        onClick = {
            navController.navigate("topics/?title=$topic&mainText=$text&resource=home")
        }
    ) {
        Box {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.university),
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
                    Text(text = topic, fontSize = 20.sp)
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun UniversityEvents(navController: NavController){
    Column (
        modifier = Modifier.padding(10.dp)
    ){
        Text(text = "События Университета", fontSize = 20.sp)
        EventsListItem(title = "New", description = "Something", date ="30.13.1999", navController, "text")
        EventsListItem(title = "New", description = "Something", date ="30.13.1999", navController, "text")
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
            SalesListItem(title = "Оптика Prosvet", navController = navController, text = "text")
            SalesListItem(title = "Burger King", navController = navController, text = "text")
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun EventsListItem(title: String, description: String, date: String, navController: NavController, text: String){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        onClick = {
            navController.navigate("topics/?title=$title&mainText=$text&resource=home")
        }
    ) {
        Box {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.university),
                    contentDescription = "uni",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(68.dp)
                )
                Column(
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    Text(text = title, fontSize = 20.sp)
                    Text(text = description, fontSize = 15.sp)
                    Text(text = date, fontSize = 10.sp)
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun SalesListItem(title: String, navController: NavController, text: String){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        onClick = {
            navController.navigate("topics/?title=$title&mainText=$text&resource=home")
        }
    ) {
        Box {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Image(
                    painter = painterResource(id = R.drawable.university),
                    contentDescription = "uni",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .fillMaxWidth()
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
                    navController.navigate("topics/?title=$title&mainText=$text&resource=home")
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
@Preview(showBackground = true)
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
        StructureItem()
    }
}

@Composable
fun StructureItem(){
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
            Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(5.dp)) {
                Text(text = "факультеты")
            }
            Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(5.dp)) {
                Text(text = "организации")
            }
        }
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Card(
                    modifier = Modifier.size(100.dp, 150.dp),
                    elevation = CardDefaults.cardElevation(10.dp)
                ) {
                    Image(painter = painterResource(id = R.drawable.univer2), contentDescription = "")
                    Text(text = "физико-математический")
                }
                Card(
                    modifier = Modifier.size(100.dp, 150.dp),
                    elevation = CardDefaults.cardElevation(10.dp)
                ) {
                    Image(painter = painterResource(id = R.drawable.univer2), contentDescription = "")
                    Text(text = "физико-математический")
                }
                Card(
                    modifier = Modifier.size(100.dp, 150.dp),
                    elevation = CardDefaults.cardElevation(10.dp)
                ) {
                    Image(painter = painterResource(id = R.drawable.univer2), contentDescription = "")
                    Text(text = "физико-математический")
                }
            }
        }
    }
}

@Composable
fun ShowTopics(title: String, mainText: String){
    Column(
        modifier = Modifier.fillMaxSize().padding(10.dp, top = 55.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.univer2),
            contentDescription = "image topic",
            modifier = Modifier.padding(bottom = 10.dp).clip(RoundedCornerShape(15.dp)))
        Text(text = title, style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Bold))
        Text(text = mainText, style = TextStyle(fontSize = 20.sp))
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun TopicsActivity(navController: NavController, title: String,
                   mainText: String, header: String,
                   resource: String){
    Scaffold (
        topBar = {
                 Row {
                     IconButton(onClick = {
                         navController.navigate(resource){
                             popUpTo(resource)
                         }
                     }, modifier = Modifier.size(50.dp).background(
                         Color(230, 238, 250))) {
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
        ShowTopics(title = title, mainText = mainText)
    }
}