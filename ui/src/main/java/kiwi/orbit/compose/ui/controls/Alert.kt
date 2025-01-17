package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.foundation.LocalAlertScope
import kiwi.orbit.compose.ui.foundation.LocalColors
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle
import kiwi.orbit.compose.ui.foundation.asCriticalTheme
import kiwi.orbit.compose.ui.foundation.asInteractiveTheme
import kiwi.orbit.compose.ui.foundation.asSuccessTheme
import kiwi.orbit.compose.ui.foundation.asWarningTheme
import kotlin.math.roundToInt

@Composable
public fun AlertInfo(
    title: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    icon: Painter? = Icons.InformationCircle,
    actions: (@Composable () -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.asInteractiveTheme(),
    ) {
        Alert(
            icon = icon,
            title = title,
            modifier = modifier,
            actions = actions,
            content = content,
        )
    }
}

@Composable
public fun AlertSuccess(
    title: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    icon: Painter? = Icons.CheckCircle,
    actions: (@Composable () -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.asSuccessTheme(),
    ) {
        Alert(
            icon = icon,
            title = title,
            modifier = modifier,
            actions = actions,
            content = content,
        )
    }
}

@Composable
public fun AlertWarning(
    title: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    icon: Painter? = Icons.Visa,
    actions: (@Composable () -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.asWarningTheme(),
    ) {
        Alert(
            icon = icon,
            title = title,
            modifier = modifier,
            actions = actions,
            content = content,
        )
    }
}

@Composable
public fun AlertCritical(
    title: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    icon: Painter? = Icons.AlertCircle,
    actions: (@Composable () -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.asCriticalTheme(),
    ) {
        Alert(
            icon = icon,
            title = title,
            modifier = modifier,
            actions = actions,
            content = content,
        )
    }
}

@Composable
private fun Alert(
    icon: Painter?,
    title: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    actions: (@Composable () -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    ThemedSurface(
        subtle = true,
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(12.dp),
    ) {
        if (icon != null) {
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 2.dp)
                    .size(16.dp),
            )
            Spacer(Modifier.width(8.dp))
        }

        AlertContent(
            title = title,
            actions = actions,
            content = content,
        )
    }
}

@Composable
private fun AlertContent(
    title: @Composable ColumnScope.() -> Unit,
    actions: (@Composable () -> Unit)?,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        CompositionLocalProvider(
            LocalAlertScope provides true
        ) {
            ProvideMergedTextStyle(OrbitTheme.typography.bodyNormal.copy(fontWeight = FontWeight.Bold)) {
                title()
            }

            ProvideMergedTextStyle(OrbitTheme.typography.bodyNormal) {
                content()
            }

            if (actions != null) {
                AlertButtons(Modifier.padding(top = 8.dp), actions)
            }
        }
    }
}

@Composable
private fun AlertButtons(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Layout(
        content = content,
        modifier = modifier,
    ) { measurables, incomingConstraints ->
        val buttonPadding = 12.dp.toPx().roundToInt()
        val buttonsCount = measurables.size
        val buttonSize = ((incomingConstraints.maxWidth - (buttonPadding * (buttonsCount - 1))) / buttonsCount)
        val buttonConstraint = incomingConstraints.copy(minWidth = buttonSize, maxWidth = buttonSize)

        val placeables = measurables.map {
            it.measure(buttonConstraint)
        }

        val maxHeight = placeables.maxOfOrNull { it.height } ?: 0
        layout(incomingConstraints.maxWidth, maxHeight) {
            var x = 0
            for (placeable in placeables) {
                placeable.place(x, y = 0)
                x += buttonSize + buttonPadding
            }
        }
    }
}
