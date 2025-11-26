package com.example.joshua_desroches_comp304_002_lab01_ex3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.joshua_desroches_comp304_002_lab01_ex3.ui.theme.JoshuaDesroches_COMP304_002_Lab01_Ex3Theme
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.sp
import kotlin.random.Random

// Add this class before MainActivity
class OrderState {
    var orderID: String by mutableStateOf("")
    var customerName: String by mutableStateOf("")
    var customerEmail: String by mutableStateOf("")
    var selectedSize: String by mutableStateOf("No size selected yet")
    var selectedToppings: List<PizzaToppings> by mutableStateOf(emptyList())
    var selectedDeliveryOption: String by mutableStateOf("Pick-Up")
    var submittedOrder: Order? by mutableStateOf(null)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JoshuaDesroches_COMP304_002_Lab01_Ex3Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   // SimpleCheckboxScreen()
                    val orderState = remember { OrderState() }
                    PizzaOrderForm(orderState)
                }
            }
        }
    }
} // end class MainActivity

// Data class to represent a pizza topping
data class PizzaToppings(
    val id: Int,
    val name: String,
    var isSelected: Boolean = false
)

// Data class to represent an order
data class Order(
    val orderID: Int,
    val customerName: String,
    val size: PizzaSize,
    val toppings: PizzaToppings,
    val modeOfDelivery: String
)

// Data class to represent a pizza topping
data class PizzaSize(
    val name: String,
    val cost: Double,
    var isSelected: Boolean = false
)


@Composable
fun CustomerInfoScreen(orderState: OrderState) {
    val orderID by remember { mutableStateOf((Random.nextInt(1, 999) + 1000).toString()) }
    var customerName by remember { mutableStateOf("") }
    var customerEmail by remember { mutableStateOf("") }

    // Update orderState with the current values
    orderState.orderID = orderID
    orderState.customerName = customerName
    orderState.customerEmail = customerEmail

        Text("Order ID: ${orderID}", fontSize = 24.sp, modifier = Modifier.padding(bottom = 24.dp))
        OutlinedTextField(
            value = customerName,
            onValueChange = { newName ->
                customerName = newName // Update the state with what the user types
            },
            label = {Text("Please enter your name")},
            singleLine = true,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        OutlinedTextField(
            value = customerEmail,
            onValueChange = { newEmail ->
                customerEmail = newEmail // Update the state with what the user types
            },
            label = {Text("Please enter your email")},
            singleLine = true,
            modifier = Modifier.padding(bottom = 12.dp)
        )
}

@Composable
fun PizzaSizeDropdown(orderState: OrderState) {
    val sizes = remember {
        listOf(
            PizzaSize("Small", 8.99),
            PizzaSize("Medium", 11.99),
            PizzaSize("Large", 15.99),
            PizzaSize("Extra Large", 19.99),
        )
    }
    var expanded by remember { mutableStateOf(false) }

    Text(
        text = "Selected: ${orderState.selectedSize}",
        style = MaterialTheme.typography.headlineSmall,
        fontSize = 18.sp,
    )

    // Box is used to anchor the DropdownMenu to the Button
    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopStart)
            .padding(bottom = 12.dp)
    ) {
        Button(onClick = { expanded = true }) { // Open the dropdown on button click
            Text("Select your Size")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false } // Close the dropdown when dismissed
        ) {
            sizes.forEach { size ->
                DropdownMenuItem(
                    text = { Text(size.name) },
                    onClick = {
                        orderState.selectedSize = size.name // Update selected course
                        expanded = false         // Close the dropdown
                    }
                )
            }
        }
    }
}

@Composable
fun PizzaToppingSelectionScreen(orderState: OrderState) {
    // Sample list of selectable items (e.g., pizza toppings)
    val toppings = remember {
        listOf(
            PizzaToppings(1, "Pepperoni"),
            PizzaToppings(2, "Mushrooms"),
            PizzaToppings(3, "Onions"),
            PizzaToppings(4, "Sausage"),
            PizzaToppings(5, "Olives"),
            PizzaToppings(6, "Extra Cheese")
        )
    }

    var items by remember { mutableStateOf(toppings.toList()) }

    fun onItemCheckedChanged(item: PizzaToppings, newCheckedState: Boolean) {
        items = items.map {
            if (it.id == item.id) {
                it.copy(isSelected = newCheckedState)
            } else {
                it
            }
        }
        // Update the orderState with the currently selected toppings
        orderState.selectedToppings = items.filter { it.isSelected }
    }

    Column {
        Text(
            "Choose your favorite toppings:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Display checkboxes without LazyColumn since we're already in one
        items.forEach { item ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Checkbox(
                    checked = item.isSelected,
                    onCheckedChange = { newCheckedState ->
                        onItemCheckedChanged(item, newCheckedState)
                    }
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = item.name, style = MaterialTheme.typography.bodyLarge)
            }
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

        DeliveryOptions(orderState)

        // Display the selected items
        Text(
            "You selected:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp, top = 16.dp)
        )

        val selectedNames = items.filter { it.isSelected }.joinToString { it.name }
        Text(
            text = if (selectedNames.isNotEmpty()) selectedNames else "None",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliveryOptions(orderState: OrderState) {
    val radioOptions = listOf("Pick-Up", "Home Delivery")

    // State to hold the currently selected option.
    // We store the *text* of the selected option.
    var selectedOption by remember { mutableStateOf(orderState.selectedDeliveryOption) } // Default selection
    Text(
        "Please select one option:",
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(bottom = 8.dp)
    )


    radioOptions.forEach { text ->
        Row(
            Modifier
                .fillMaxWidth()
                .selectable(
                    selected = (text == selectedOption), // Check if this option is selected
                    onClick = {
                        selectedOption = text
                        orderState.selectedDeliveryOption = text // Update orderState
                    }, // Update state on click
                    role = Role.RadioButton // For accessibility
                )
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = (text == selectedOption),
                onClick = null // null recommended for accessibility with selectable parent
                // The Row's selectable handles the click.
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
    HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PizzaOrderForm(orderState: OrderState) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Build your Pizza - Joshua Desroches") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            item {
                CustomerInfoScreen(orderState)
            }
            item {
                PizzaSizeDropdown(orderState)
            }
            item {
                PizzaToppingSelectionScreen(orderState)
            }
            item {
                Button(onClick = {
                    val order = Order(
                        orderID = orderState.orderID.toInt(),
                        customerName = orderState.customerName,
                        size = PizzaSize(orderState.selectedSize, 0.0), // Size cost will be 0.0 for now
                        toppings = if (orderState.selectedToppings.isNotEmpty())
                            PizzaToppings(
                                id = 0,
                                name = orderState.selectedToppings.joinToString(", ") { it.name },
                                isSelected = true
                            )
                        else
                            PizzaToppings(0, "No Toppings Selected"),
                        modeOfDelivery = orderState.selectedDeliveryOption
                    )
                    orderState.submittedOrder = order // Update the submitted order in the order state
                    println("Order submitted: $order")
                    //Display the order

                }) {
                    Text("Submit Order")
                }
            }

            // Display the submitted order details
            orderState.submittedOrder?.let { order ->
                item {
                    Text(
                        "Submitted Order:",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }
                item {
                    Text("Order ID: ${order.orderID}", style = MaterialTheme.typography.bodyLarge)
                }
                item {
                    Text("Customer Name: ${order.customerName}", style = MaterialTheme.typography.bodyLarge)
                }
                item {
                    Text("Size: ${order.size.name}", style = MaterialTheme.typography.bodyLarge)
                }
                item {
                    Text("Toppings: ${order.toppings.name}", style = MaterialTheme.typography.bodyLarge)
                }
                item {
                    Text("Mode of Delivery: ${order.modeOfDelivery}", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}