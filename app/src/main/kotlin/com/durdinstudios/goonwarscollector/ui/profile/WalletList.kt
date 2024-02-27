package com.durdinstudios.goonwarscollector.ui.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.durdinstudios.goonwarscollector.R
import com.durdinstudios.goonwarscollector.ui.components.AddIcon
import com.durdinstudios.goonwarscollector.ui.components.BodyMediumEmphasisLeft
import com.durdinstudios.goonwarscollector.ui.components.BodySmallMediumEmphasisLeft
import com.durdinstudios.goonwarscollector.ui.components.CollapsedCheveronIcon
import com.durdinstudios.goonwarscollector.ui.components.ExpandedCheveronIcon

@Composable
fun WalletList(items: List<String>, onAddClick: () -> Unit) {
    val isExpanded = remember {
        mutableStateOf(false)
    }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_wallet),
                contentDescription = null,
                Modifier.size(24.dp),
                tint = Color.White
            )
            WalletHeader(text = "My Wallets", isExpanded = isExpanded.value) {
                isExpanded.value = !isExpanded.value
            }
        }
        AnimatedVisibility(isExpanded.value) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items.forEach {
                    WalletItem(it)
                }
                Row(modifier = Modifier
                    .clickable { onAddClick() }
                    .padding(vertical = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    AddIcon(tint = Color.White, modifier = Modifier.size(24.dp))
                    BodyMediumEmphasisLeft(
                        text = "Add wallet",
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun WalletHeader(text: String, isExpanded: Boolean, onHeaderClicked: () -> Unit) {
    Row(modifier = Modifier
        .clickable { onHeaderClicked() }
        .padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically) {
        BodyMediumEmphasisLeft(
            text = text,
            modifier = Modifier.weight(1.0f)
        )
        if (isExpanded) {
            CollapsedCheveronIcon()
        } else {
            ExpandedCheveronIcon()
        }
    }
}

@Composable
fun WalletItem(text: String) {
    BodySmallMediumEmphasisLeft(
        text = text,
        modifier = Modifier.fillMaxWidth()
    )
}