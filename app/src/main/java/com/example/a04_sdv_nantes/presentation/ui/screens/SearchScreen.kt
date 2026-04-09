package com.example.a04_sdv_nantes.presentation.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.a04_sdv_nantes.R
import com.example.a04_sdv_nantes.data.remote.WeatherEntity
import com.example.a04_sdv_nantes.presentation.ui.theme.A04_sdv_nantesTheme
import com.example.a04_sdv_nantes.presentation.viewmodel.MainViewModel

@Preview(showBackground = true, showSystemUi = true, locale = "fr")
@Preview(
    showBackground = true, showSystemUi = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES or android.content.res.Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun SearchScreenPreview() {
    //Il faut remplacer NomVotreAppliTheme par le thème de votre application
    //Utilisé par exemple dans MainActivity.kt sous setContent {...}
    A04_sdv_nantesTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SearchScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun SearchScreen(modifier: Modifier = Modifier, viewModel: MainViewModel = MainViewModel()) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val searchText : MutableState<String> = remember { mutableStateOf("") }

        SearchBar(searchText= searchText)

        val list = viewModel.dataList.collectAsStateWithLifecycle().value.filter {
            it.name.contains(searchText.value, true)
        }
        //Permet de remplacer très facilement le RecyclerView. LazyRow existe aussi
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(list.size) {
                PictureRowItem(data = list[it])
            }
        }

        Row() {

            Button(
                onClick = { searchText.value = "" },
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding
            ) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Localized description",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Clear")
            }

            Button(
                onClick = { /* Do something! */ },
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding
            ) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Localized description",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(stringResource(R.string.bt_load))
            }
        }


    }
}


@Composable
fun SearchBar(modifier: Modifier = Modifier, searchText : MutableState<String>) {



    TextField(
        value = searchText.value, //Valeur affichée
        onValueChange = { newValue: String -> searchText.value = newValue }, //Nouveau texte entrée
        leadingIcon = { //Image d'icône
            Icon(
                imageVector = Icons.Default.Search,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null
            )
        },
        singleLine = true,
        label = { //Texte d'aide qui se déplace
            Text("Enter text")
            //Pour aller le chercher dans string.xml, R de votre package com.nom.projet
            //Text(stringResource(R.string.placeholder_search))
        },
        //placeholder = { //Texte d'aide qui disparait
        //Text("Recherche")
        //},

        //keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search), // Définir le bouton "Entrée" comme action de recherche
        //keyboardActions = KeyboardActions(onSearch = {onSearchAction()}), // Déclenche l'action définie
        //Comment le composant doit se placer
        modifier = modifier
            .fillMaxWidth() // Prend toute la largeur
            .heightIn(min = 56.dp) //Hauteur minimum
    )
}

@Composable //Composable affichant 1 élément
fun PictureRowItem(modifier: Modifier = Modifier, data: WeatherEntity) {

    var expended by remember { mutableStateOf(false) }


    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(4.dp)
    ) {
        //Permission Internet nécessaire
        AsyncImage(
            model = data.weather.firstOrNull()?.icon ?: "",
            //Pour aller le chercher dans string.xml R de votre package com.nom.projet
            //contentDescription = getString(R.string.picture_of_cat),
            //En dur
            contentDescription = "une photo de chat",
            contentScale = ContentScale.FillWidth,

            //Pour toto.png. Si besoin de choisir l'import pour la classe R, c'est celle de votre package
            //Image d'échec de chargement qui sera utilisé par la preview
            error = painterResource(R.drawable.error),
            //Image d'attente.
            //placeholder = painterResource(R.drawable.toto),

            onError = { println(it) },
            modifier = Modifier
                .heightIn(max = 100.dp)
                .widthIn(max = 100.dp)
        )

        Column(modifier = Modifier.padding(start = 4.dp).fillMaxWidth().clickable{
            expended = !expended
        }) {
            Text(text = data.name, fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
            Text(
                text = if(expended) data.getResume() else ( data.getResume().take(10) + "..."), fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.animateContentSize()
            )
        }
    }
}