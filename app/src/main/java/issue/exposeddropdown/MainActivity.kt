@file:OptIn(ExperimentalMaterial3Api::class)

package issue.exposeddropdown

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import issue.exposeddropdown.ui.theme.ExposedDropDownIssueTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      ExposedDropDownIssueTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

          var text by rememberSaveable { mutableStateOf("") }
          val autoCompleteOptions by remember(text) { derivedStateOf { items.filter { it.contains(text) } } }
          var showAutoComplete by rememberSaveable(text, autoCompleteOptions) { mutableStateOf(text.isNotBlank() && autoCompleteOptions.isNotEmpty()) }

          ExposedDropdownMenuBox(expanded = showAutoComplete, onExpandedChange = { showAutoComplete = !showAutoComplete && text.isNotBlank() }) {

            OutlinedTextField(
              value = text,
              onValueChange = { text = it },
              maxLines = 1,
              singleLine = true,
              modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(expanded = showAutoComplete, onDismissRequest = { showAutoComplete = false }, modifier = Modifier.exposedDropdownSize()) {
              autoCompleteOptions.forEach {
                DropdownMenuItem(
                  text = { Text(text = it) },
                  onClick = { text = it; showAutoComplete = false }
                )
              }
            }
          }
        }
      }
    }
  }
}

val items = listOf(
  "a",
  "ab",
  "abc",
  "abcd",
  "abcde",
  "abcdef",
  "abcdefg",
  "abcdefgh",
  "abcdefghi",
  "abcdefghij",
  "1",
  "11",
  "111",
  "1111",
  "11111",
  "111111"
)

