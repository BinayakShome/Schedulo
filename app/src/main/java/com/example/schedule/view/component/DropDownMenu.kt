package com.example.schedule.view.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenu(
    text: String, // Label text
    options: List<String>, // List of options
    selectedOption: String, // Currently selected option
    onOptionSelected: (String) -> Unit // Callback when an option is selected
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            //.padding(8.dp)
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true },
            readOnly = true,
            label = { Text(text) },
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = "Dropdown Icon",
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Cyan,
                unfocusedBorderColor = Color.Gray
            ),
            shape = MaterialTheme.shapes.large
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center // Center align text inside Box
                        ) {
                            Text(text = option)
                        }
                    },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DropDownMenuPreview() {
//    var selectedOption by remember { mutableStateOf("Select an option") }
//
//    DropDownMenu(
//        text = "Choose an item",
//        options = listOf("Option 1", "Option 2", "Option 3"),
//        selectedOption = selectedOption,
//        onOptionSelected = { selectedOption = it }
//    )
//}
