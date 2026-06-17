package com.example.milestone1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// ============================================================
// ABM Jewelry Shop — Milestones_Final Project (CSS-403)
// Author: Takhmina Ulukmanova
// Based on the CSS 303/304 medium-fidelity prototype (Moqups)
// Single-file Compose project with 6 screens
// ============================================================

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(color = Color.White) {
                    ABMApp()
                }
            }
        }
    }
}

// Navigation routes from the original CSS 304 nav bar
object Routes {
    const val HOME = "home"          // NEW IN
    const val JEWELRY = "jewelry"
    const val DETAIL = "detail"
    const val COLLECTIONS = "collections"
    const val STORE_LOCATOR = "store_locator"
    const val ABOUT = "about"
}

@Composable
fun ABMApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) { HomeScreen(navController) }
        composable(Routes.JEWELRY) { JewelryScreen(navController) }
        composable(Routes.DETAIL) { DetailScreen(navController) }
        composable(Routes.COLLECTIONS) { CollectionsScreen(navController) }
        composable(Routes.STORE_LOCATOR) { StoreLocatorScreen(navController) }
        composable(Routes.ABOUT) { AboutScreen(navController) }
    }
}

// ============================================================
// Reusable: TopBar with ABM jewelry shop title
// Layout: Row containing Box (logo) + Box (title) + Box (icons)
// ============================================================
@Composable
fun ABMTopBar(
    showBack: Boolean = false,
    onBack: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Box: Back arrow or Logo
        Box(
            modifier = Modifier.size(44.dp),
            contentAlignment = Alignment.Center
        ) {
            if (showBack) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier.clickable { onBack() }
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.Black, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Logo", fontSize = 9.sp, color = Color.Black)
                }
            }
        }

        // Box: Title (weight 1f to center it)
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "ABM jewelry shop",
                fontSize = 20.sp,
                fontStyle = FontStyle.Italic,
                color = Color.Black
            )
        }

        // Row inside: action icons
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(28.dp), contentAlignment = Alignment.Center) {
                Icon(Icons.Default.Person, "Account", tint = Color.Black,
                    modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier.size(28.dp), contentAlignment = Alignment.Center) {
                Icon(Icons.Default.Search, "Search", tint = Color.Black,
                    modifier = Modifier.size(20.dp))
            }
        }
    }
}

// ============================================================
// Reusable: Navigation Bar (horizontal nav from CSS 304)
// Layout: Row containing 6 Box items
// ============================================================
@Composable
fun ABMNavBar(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    val navItems = listOf(
        "NEW IN" to Routes.HOME,
        "JEWELRY" to Routes.JEWELRY,
        "COLLECTIONS" to Routes.COLLECTIONS,
        "GIFTS" to Routes.JEWELRY, // GIFTS reuses jewelry list for milestone 1
        "STORE LOCATOR" to Routes.STORE_LOCATOR,
        "ABOUT ABM" to Routes.ABOUT
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        navItems.forEach { (label, route) ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onNavigate(route) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = label,
                    fontSize = 8.sp,
                    color = Color.White,
                    fontWeight = if (currentRoute == route) FontWeight.Bold
                    else FontWeight.Normal
                )
            }
        }
    }
}

// ============================================================
// Reusable: Product Card
// Layout: Column containing Box (image placeholder) + 2 Text Boxes
// ============================================================
@Composable
fun ProductCard(
    name: String = "NAME OF ITEM",
    price: String = "Price",
    imageRes: Int = R.drawable.bracelet_gold,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE6E6E6))
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = name,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            contentScale = ContentScale.Crop
        )
        }
        Spacer(modifier = Modifier.height(8.dp))
        // Box: name
        Text(
            text = name,
            fontSize = 12.sp,
            fontStyle = FontStyle.Italic,
            color = Color.Black
        )
        // Box: price
        Text(
            text = price,
            fontSize = 11.sp,
            fontStyle = FontStyle.Italic,
            color = Color.Black
        )
    }

// ============================================================
// SCREEN 1: HOME (NEW IN)
// Layout structure:
//   Column (root)
//   ├─ Row (TopBar)
//   ├─ Row (NavBar)
//   ├─ LazyVerticalGrid (product cards)
//   ├─ Column (Collection banner)
//   └─ Box (DISCOVER button)
// ============================================================
@Composable
fun HomeScreen(nav: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ABMTopBar()
        ABMNavBar(currentRoute = Routes.HOME) { route -> nav.navigate(route) }

        // LazyVerticalGrid showing products in 2 columns
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(4) {
                ProductCard { nav.navigate(Routes.DETAIL) }
            }
        }

        // Column: Collection banner
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("NEW IN", fontSize = 12.sp, fontWeight = FontWeight.SemiBold,
                color = Color.Black)
            Text("Collection Automne", fontSize = 12.sp, fontStyle = FontStyle.Italic,
                color = Color.Black)
        }

        // Box: DISCOVER button
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
                .background(Color.Black)
                .clickable { nav.navigate(Routes.COLLECTIONS) }
                .padding(horizontal = 32.dp, vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("DISCOVER", color = Color.White, fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold, letterSpacing = 2.sp)
        }
    }
}

// ============================================================
// SCREEN 2: JEWELRY (Product Catalog)
// Layout structure:
//   Column (root)
//   ├─ Row (TopBar)
//   ├─ Row (NavBar)
//   ├─ Box (Section Title)
//   ├─ Row (Filter chips)
//   └─ LazyVerticalGrid (product cards)
// ============================================================
@Composable
fun JewelryScreen(nav: NavHostController) {
    var selectedFilter by remember { mutableStateOf("ALL") }
    val filters = listOf("ALL", "RINGS", "NECKLACES", "EARRINGS")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ABMTopBar(showBack = true) { nav.popBackStack() }
        ABMNavBar(currentRoute = Routes.JEWELRY) { route -> nav.navigate(route) }

        // Box: section title
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("Jewelry Collection", fontSize = 16.sp,
                fontStyle = FontStyle.Italic, color = Color.Black)
        }

        // Row: filter chips (each chip is a Box)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            filters.forEach { filter ->
                FilterChip(
                    label = filter,
                    selected = selectedFilter == filter,
                    modifier = Modifier.weight(1f)
                ) { selectedFilter = filter }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // LazyVerticalGrid showing products
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(8) {
                ProductCard { nav.navigate(Routes.DETAIL) }
            }
        }
    }
}

@Composable
fun FilterChip(
    label: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    // Box: filter chip
    Box(
        modifier = modifier
            .background(if (selected) Color.Black else Color.White)
            .border(0.5.dp, Color.Black)
            .clickable { onClick() }
            .padding(vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = 9.sp,
            color = if (selected) Color.White else Color.Black
        )
    }
}

// ============================================================
// SCREEN 3: DETAIL (Product Detail)
// Layout structure:
//   Column (root, scrollable)
//   ├─ Row (TopBar)
//   ├─ Row (NavBar)
//   ├─ Box (Hero image)
//   ├─ Column (Product info: name, price, rating)
//   ├─ Column (Size selector with Row of size chips)
//   ├─ Column (Description)
//   └─ Box (ADD TO CART button)
// ============================================================
@Composable
fun DetailScreen(nav: NavHostController) {
    var selectedSize by remember { mutableStateOf(7) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
    ) {
        ABMTopBar(showBack = true) { nav.popBackStack() }
        ABMNavBar(currentRoute = "") { route -> nav.navigate(route) }

        // Box: hero image
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(280.dp)
                .background(Color(0xFFE6E6E6)),
            contentAlignment = Alignment.Center
        ) {
            Text("[product image]", color = Color.Gray, fontSize = 12.sp)
        }

        // Column: product info
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text("NAME OF ITEM", fontSize = 18.sp,
                fontStyle = FontStyle.Italic, color = Color.Black)
            Text("Price", fontSize = 14.sp,
                fontStyle = FontStyle.Italic, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            Text("★ ★ ★ ★ ★  (124 reviews)", fontSize = 11.sp, color = Color.Black)
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Column: size selector
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text("SIZE", fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            // Row: size options (each is a Box)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(5, 6, 7, 8, 9).forEach { size ->
                    Box(
                        modifier = Modifier
                            .size(40.dp, 32.dp)
                            .background(if (size == selectedSize) Color.Black else Color.White)
                            .border(0.5.dp, Color.Black)
                            .clickable { selectedSize = size },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$size",
                            fontSize = 12.sp,
                            color = if (size == selectedSize) Color.White else Color.Black,
                            fontWeight = if (size == selectedSize) FontWeight.SemiBold
                            else FontWeight.Normal
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Column: description
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text("DESCRIPTION", fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "A classic piece handcrafted in our atelier. Premium materials, " +
                        "timeless elegance.",
                fontSize = 10.sp,
                fontStyle = FontStyle.Italic,
                color = Color.DarkGray
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Box: ADD TO CART button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .background(Color.Black)
                .clickable { /* add to cart */ }
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("ADD TO CART", color = Color.White, fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold, letterSpacing = 2.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

// ============================================================
// SCREEN 4: COLLECTIONS
// Layout structure:
//   Column (root, scrollable)
//   ├─ Row (TopBar)
//   ├─ Row (NavBar)
//   ├─ Box (Section title)
//   ├─ Box (Collection Card 1) — contains Image Box + Label Column
//   ├─ Box (Collection Card 2)
//   ├─ Box (Collection Card 3)
//   └─ Box (DISCOVER button)
// ============================================================
@Composable
fun CollectionsScreen(nav: NavHostController) {
    val collections = listOf(
        "Collection Automne" to "NEW IN",
        "Collection Hiver" to "CLASSIC LINE",
        "Bridal Collection" to "FOREVER YOURS"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        ABMTopBar(showBack = true) { nav.popBackStack() }
        ABMNavBar(currentRoute = Routes.COLLECTIONS) { route -> nav.navigate(route) }

        // Box: section title
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("Our Collections", fontSize = 16.sp,
                fontStyle = FontStyle.Italic, color = Color.Black)
        }

        // Each Collection is a Box with image + label
        collections.forEach { (name, tag) ->
            CollectionCard(name = name, tag = tag) {
                nav.navigate(Routes.JEWELRY)
            }
        }

        // Box: DISCOVER button
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
                .background(Color.Black)
                .clickable { nav.navigate(Routes.JEWELRY) }
                .padding(horizontal = 32.dp, vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("DISCOVER", color = Color.White, fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold, letterSpacing = 2.sp)
        }
    }
}

@Composable
fun CollectionCard(name: String, tag: String, onClick: () -> Unit) {
    // Box: collection card containing Image Box + Label Column
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onClick() }
    ) {
        // Box: image
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .background(Color(0xFFE6E6E6)),
            contentAlignment = Alignment.Center
        ) {
            Text("[collection image]", color = Color.Gray, fontSize = 10.sp)
        }
        // Column: label overlay (placed beneath in this simpler version)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.8f))
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(name, color = Color.White, fontSize = 13.sp,
                fontStyle = FontStyle.Italic)
            Text(tag, color = Color.LightGray, fontSize = 8.sp,
                letterSpacing = 2.sp)
        }
    }
}

// ============================================================
// SCREEN 5: STORE LOCATOR
// Layout structure:
//   Column (root)
//   ├─ Row (TopBar)
//   ├─ Row (NavBar)
//   ├─ Box (Section title)
//   ├─ Row (Search bar)
//   ├─ Box (Map placeholder)
//   ├─ Box (Section header "NEAREST STORES")
//   └─ Column (Store list: each Row has Pin Box + Info Column + Distance Box)
// ============================================================
@Composable
fun StoreLocatorScreen(nav: NavHostController) {
    val stores = listOf(
        Triple("ABM Chicago Flagship", "123 N Michigan Ave, Chicago IL", "0.8 mi"),
        Triple("ABM Lincoln Park", "2401 N Halsted St, Chicago IL", "2.3 mi"),
        Triple("ABM Oak Park", "1100 Lake St, Oak Park IL", "8.4 mi")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ABMTopBar(showBack = true) { nav.popBackStack() }
        ABMNavBar(currentRoute = Routes.STORE_LOCATOR) { route -> nav.navigate(route) }

        // Box: section title
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("Find a Store", fontSize = 16.sp,
                fontStyle = FontStyle.Italic, color = Color.Black)
        }

        // Row: search bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(40.dp)
                .border(0.5.dp, Color.Black)
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Search, null, tint = Color.Gray,
                modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text("Enter city or ZIP code...", color = Color.Gray, fontSize = 11.sp,
                fontStyle = FontStyle.Italic)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Box: map placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(180.dp)
                .background(Color(0xFFE6E6E6)),
            contentAlignment = Alignment.Center
        ) {
            Text("[map view]", color = Color.Gray, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Box: section header
        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text("NEAREST STORES", fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold, color = Color.Black)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Column: store list
        stores.forEach { (name, address, distance) ->
            StoreRow(name, address, distance)
        }
    }
}

@Composable
fun StoreRow(name: String, address: String, distance: String) {
    // Row: store item (Pin Box + Info Column + Distance Box)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .border(0.3.dp, Color.Black)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Box: pin icon
        Box(
            modifier = Modifier.size(28.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.LocationOn, null, tint = Color.Black,
                modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(8.dp))
        // Column: store info (weight 1f)
        Column(modifier = Modifier.weight(1f)) {
            Text(name, fontSize = 12.sp, fontStyle = FontStyle.Italic,
                color = Color.Black)
            Text(address, fontSize = 10.sp, fontStyle = FontStyle.Italic,
                color = Color.DarkGray)
            Text("Open today · 10:00 - 21:00", fontSize = 10.sp,
                fontStyle = FontStyle.Italic, color = Color.DarkGray)
        }
        // Box: distance
        Box {
            Text(distance, fontSize = 10.sp, color = Color.Black)
        }
    }
}

// ============================================================
// SCREEN 6: ABOUT ABM
// Layout structure:
//   Column (root, scrollable)
//   ├─ Row (TopBar)
//   ├─ Row (NavBar)
//   ├─ Column (Brand header)
//   ├─ Box (Hero image)
//   ├─ Column (Our Story)
//   ├─ Column (Contact)
//   │   ├─ Box (header)
//   │   ├─ Row (Email: Icon Box + Info Column)
//   │   ├─ Row (Phone: Icon Box + Info Column)
//   │   └─ Row (Address: Icon Box + Info Column)
//   └─ Row (Social icons)
// ============================================================
@Composable
fun AboutScreen(nav: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        ABMTopBar(showBack = true) { nav.popBackStack() }
        ABMNavBar(currentRoute = Routes.ABOUT) { route -> nav.navigate(route) }

        // Column: brand header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("About ABM", fontSize = 20.sp,
                fontStyle = FontStyle.Italic, color = Color.Black)
            Text("EST. 2026", fontSize = 9.sp,
                letterSpacing = 3.sp, color = Color.DarkGray)
        }

        // Box: hero image
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(160.dp)
                .background(Color(0xFFE6E6E6)),
            contentAlignment = Alignment.Center
        ) {
            Text("[brand image]", color = Color.Gray, fontSize = 11.sp)
        }

        // Column: Our Story
        Column(modifier = Modifier.padding(16.dp)) {
            Text("OUR STORY", fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "ABM Jewelry was founded with a single vision: to craft timeless " +
                        "pieces that celebrate life's most precious moments. Every item " +
                        "is designed with passion and crafted from the finest materials.",
                fontSize = 11.sp,
                fontStyle = FontStyle.Italic,
                color = Color.Black
            )
        }

        // Column: Contact
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text("CONTACT", fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            // Each contact row: Row (Icon Box + Info Column)
            ContactRow("EMAIL", "hello@abmjewelry.com")
            ContactRow("PHONE", "+1 (312) 555-0188")
            ContactRow("ADDRESS", "123 N Michigan Ave, Chicago IL")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Row: social icons (3 Box items)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            listOf("IG", "f", "in").forEach { icon ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .size(32.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.Black, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(icon, fontSize = 11.sp,
                        fontWeight = FontWeight.Bold, color = Color.Black)
                }
            }
        }
    }
}

@Composable
fun ContactRow(label: String, value: String) {
    // Row: contact item
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .border(0.3.dp, Color.Black)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Box: icon
        Box(
            modifier = Modifier.size(28.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.FavoriteBorder, null, tint = Color.Black,
                modifier = Modifier.size(16.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        // Column: label + value
        Column {
            Text(label, fontSize = 9.sp, letterSpacing = 1.sp, color = Color.Gray)
            Text(value, fontSize = 11.sp,
                fontStyle = FontStyle.Italic, color = Color.Black)
        }
    }
}

// ============================================================
// PREVIEWS — These render in Android Studio's Preview panel.
// Each preview can be exported as an image: right-click → "Copy Image"
// or use the camera icon in the Preview pane to export PNG.
// ============================================================

@Preview(name = "1. Home Screen", showBackground = true,
    widthDp = 360, heightDp = 740)
@Composable
fun PreviewHome() {
    MaterialTheme { HomeScreen(rememberNavController()) }
}

@Preview(name = "2. Jewelry Screen", showBackground = true,
    widthDp = 360, heightDp = 740)
@Composable
fun PreviewJewelry() {
    MaterialTheme { JewelryScreen(rememberNavController()) }
}

@Preview(name = "3. Detail Screen", showBackground = true,
    widthDp = 360, heightDp = 740)
@Composable
fun PreviewDetail() {
    MaterialTheme { DetailScreen(rememberNavController()) }
}

@Preview(name = "4. Collections Screen", showBackground = true,
    widthDp = 360, heightDp = 740)
@Composable
fun PreviewCollections() {
    MaterialTheme { CollectionsScreen(rememberNavController()) }
}

@Preview(name = "5. Store Locator Screen", showBackground = true,
    widthDp = 360, heightDp = 740)
@Composable
fun PreviewStoreLocator() {
    MaterialTheme { StoreLocatorScreen(rememberNavController()) }
}

@Preview(name = "6. About Screen", showBackground = true,
    widthDp = 360, heightDp = 740)
@Composable
fun PreviewAbout() {
    MaterialTheme { AboutScreen(rememberNavController()) }
}
