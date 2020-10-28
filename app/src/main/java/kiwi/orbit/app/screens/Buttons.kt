package kiwi.orbit.app.screens

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import kiwi.orbit.OrbitTheme
import kiwi.orbit.controls.ButtonLink
import kiwi.orbit.controls.ButtonPrimary
import kiwi.orbit.controls.ButtonPrimarySubtle
import kiwi.orbit.controls.ButtonSecondary

@Preview
@Composable
fun ButtonsScreen() {
    Surface {
        Column {
            val maxWidth = Modifier.fillMaxWidth()

            Block("Primary Button") {
                ButtonPrimary(onClick = {}, maxWidth) { Text("Primary Button") }
                Spacer(Modifier.height(8.dp))
                ButtonPrimary(onClick = {}, maxWidth, enabled = false) { Text("Primary Button") }
            }

            Block("Primary Subtle Button") {
                ButtonPrimarySubtle(onClick = {}, maxWidth) { Text("Primary Subtle Button") }
                Spacer(Modifier.height(8.dp))
                ButtonPrimarySubtle(onClick = {}, maxWidth, enabled = false) { Text("Primary Subtle Button") }
            }

            Block("Secondary Button") {
                ButtonSecondary(onClick = {}, maxWidth) { Text("Secondary Button") }
                Spacer(Modifier.height(8.dp))
                ButtonSecondary(onClick = {}, maxWidth, enabled = false) { Text("Secondary Button") }
            }

            Block("Link Button") {
                ButtonLink(onClick = {}, maxWidth) { Text("Link Button") }
                Spacer(Modifier.height(8.dp))
                ButtonLink(onClick = {}, maxWidth, enabled = false) { Text("Link Button") }
            }
        }
    }
}

@Composable
fun Block(title: String, content: @Composable () -> Unit) {
    Column(Modifier.padding(16.dp)) {
        Text(text = title, style = OrbitTheme.typography.title4)
        Spacer(modifier = Modifier.height(12.dp))
        content()
    }
}