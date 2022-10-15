package com.example.themoviedb.ui.tvShows

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.themoviedb.R
import com.example.themoviedb.databinding.FragmentProfileBinding
import com.example.themoviedb.ui.LoginScreenActivity
import com.example.themoviedb.ui.base.BaseFragment
import com.example.themoviedb.ui.composables.FavoriteTVShowItem
import com.example.themoviedb.ui.theme.AppTheme
import com.example.themoviedb.ui.tvShows.viewmodel.ProfileViewModel
import com.example.themoviedb.utils.Constants
import com.example.themoviedb.utils.PreferencesManager
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment : BaseFragment() {
    private val viewModel: ProfileViewModel by viewModels()
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!


    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                AppTheme(darkTheme = isSystemInDarkTheme()) {
                    MainContent()
                }
            }
        }
        return view
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Preview
    @Composable
    fun MainContent() {
        Scaffold(
            topBar = { TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { findNavController().navigateUp() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back", tint = Color.White)
                    }
                },
                title = { Text(text = "Profile", style = TextStyle(color = Color.White)) },
            )},
            content = {
                Content()
            }
        )

    }
    @Composable
    private fun Content(){
        val favoriteTVShows by viewModel.favoriteTVShows.collectAsState()
        val mContext : Context = LocalContext.current
        Column(modifier = Modifier.fillMaxSize()) {
            ConstraintLayout() {
                val (profilePicture,name,user,favoritesText,favoriteList,logoutButton) = createRefs()
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(profilePicture) {
                        top.linkTo(parent.top, margin = 16.dp)
                    }, horizontalArrangement = Arrangement.Center){
                    ProfilePicture()
                }

                Text("Olivia W. Skinner", modifier = Modifier.constrainAs(name){
                    top.linkTo(profilePicture.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })

                Text("@OliviaW_Skinner", modifier = Modifier.constrainAs(user){
                    top.linkTo(name.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
                Text(text = "My Favorites",
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 32.dp)
                        .constrainAs(favoritesText) {
                            top.linkTo(user.bottom, margin = 16.dp)
                        },
                    style = TextStyle(fontWeight = FontWeight.Bold,
                        fontSize = 18.sp)
                )
                Box(modifier = Modifier
                    .fillMaxWidth().
                        height(260.dp)
                    .constrainAs(favoriteList) {
                        bottom.linkTo(logoutButton.top, margin = 16.dp)
                        top.linkTo(favoritesText.bottom, margin = 16.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }){
                    LazyRow(){
                        items(favoriteTVShows){ tvShow ->
                            FavoriteTVShowItem(tvShowData = tvShow) {
                            }
                        }
                    }
                }
                Button(modifier = Modifier
                    .constrainAs(logoutButton) {
                        bottom.linkTo(parent.bottom, margin = 16.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth()
                    .padding(16.dp)
                    , onClick = {
                        PreferencesManager.getInstance().putBoolean(Constants.LOGGED_IN,false)
                        requireContext().startActivity(Intent(mContext, LoginScreenActivity::class.java))
                        requireActivity().finish()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                    ) {
                    Text("LOG OUT", modifier = Modifier.padding(8.dp), style = TextStyle(color = Color.White))
                }
            }
        }
    }

    @Composable
    fun ProfilePicture(){
        Box(
            modifier = Modifier
                .size(120.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Image(
                painter = painterResource(R.drawable.profile_pic),
                contentDescription = "avatar",
                contentScale = ContentScale.Crop,            // crop the image if it's not a square
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .clip(CircleShape)                       // clip to the circle shape
                    .border(2.dp, Color.Gray, CircleShape)   // add a border (optional)
            )
            Box(            modifier = Modifier
                .size(40.dp)
                .align(Alignment.BottomEnd)
                .clip(RoundedCornerShape(50.dp))
                .background(MaterialTheme.colors.primary), contentAlignment = Alignment.Center
            ){
                Icon( imageVector = Icons.Filled.Edit, contentDescription = "edit", tint = Color.White,)
            }

        }
    }

}