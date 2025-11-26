package com.example.lab4_ex1

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.google.android.gms.maps.model.LatLng

object ListOfLocations {

    fun items(category: String): List<String> = when (category) {
        "Shopping" -> listOf("Harwood Plaza", "Westney Heights", "Riocan Durham Centre", "Pickering Town Centre")
        "Restaurants" -> listOf("Sunset Grill", "Stacked", "Portly Piper", "Mumbay Grill")
        "Parks" -> listOf("Rotary Park", "Millers Creek", "Paulynn Park", "Hermitage Park")
        "Conservation Areas" -> listOf("Greenwood Conservation", "Heber Down Conservation", "Lynde Shores", "Petticoat Creek")
        else -> listOf()
    }

    @Composable
    fun painterFor(category: String, item: String): Painter = when (category) {
        "Shopping" -> when (item) {
            "Harwood Plaza" -> painterResource(id = R.drawable.harwoodplaza)
            "Westney Heights" -> painterResource(id = R.drawable.westney)
            "Riocan Durham Centre" -> painterResource(id = R.drawable.riocan)
            "Pickering Town Centre" -> painterResource(id = R.drawable.pickeringtowncentre)
            else -> painterResource(id = R.drawable.harwoodplaza)
        }
        "Restaurants" -> when (item) {
            "Sunset Grill" -> painterResource(id = R.drawable.sunset)
            "Stacked" -> painterResource(id = R.drawable.stacked)
            "Portly Piper" -> painterResource(id = R.drawable.portlypiper)
            "Mumbay Grill" -> painterResource(id = R.drawable.mumbay)
            else -> painterResource(id = R.drawable.restaurant)
        }
        "Parks" -> when (item) {
            "Rotary Park" -> painterResource(id = R.drawable.rotarypark)
            "Millers Creek" -> painterResource(id = R.drawable.millercreek)
            "Paulynn Park" -> painterResource(id = R.drawable.paulynn)
            "Hermitage Park" -> painterResource(id = R.drawable.hermitage)
            else -> painterResource(id = R.drawable.rotarypark)
        }
        "Conservation Areas" -> when (item) {
            "Greenwood Conservation" -> painterResource(id = R.drawable.greenwoodconservation)
            "Heber Down Conservation" -> painterResource(id = R.drawable.heber)
            "Lynde Shores" -> painterResource(id = R.drawable.lynde)
            "Petticoat Creek" -> painterResource(id = R.drawable.petticoat)
            else -> painterResource(id = R.drawable.greenwoodconservation)
        }
        else -> painterResource(id = R.drawable.noimg)
    }

    val itemCoordinates: Map<String, LatLng> = mapOf(
        // Shopping
        "Harwood Plaza" to LatLng(43.84563386810027, -79.0203999615049),
        "Westney Heights" to LatLng(43.85952724607064, -79.03911734596261),
        "Riocan Durham Centre" to LatLng(43.86412655377805, -79.01934174596232),
        "Pickering Town Centre" to LatLng(43.83494778845792, -79.08521915945572),
        // Restaurants
        "Sunset Grill" to LatLng(43.842862161253095, -79.02646757331419),
        "Stacked" to LatLng(43.84591843661017, -79.02076817479939),
        "Portly Piper" to LatLng(43.841320522167564, -79.02655654596362),
        "Mumbay Grill" to LatLng(43.85416016590481, -79.05649192379124),
        // Parks
        "Rotary Park" to LatLng(43.8192039046471, -79.03303961378282),
        "Millers Creek" to LatLng(43.86346973788588, -79.03917273247063),
        "Paulynn Park" to LatLng(43.885081153232235, -79.05987137959885),
        "Hermitage Park" to LatLng(43.85810862127339, -79.05240595945445),
        // Conservation Areas
        "Greenwood Conservation" to LatLng(43.90040789180967, -79.06499068643578),
        "Heber Down Conservation" to LatLng(43.94093188306376, -78.97968407960053),
        "Lynde Shores" to LatLng(43.85498214096549, -78.9669729176366),
        "Petticoat Creek" to LatLng(43.803817273488896, -79.11278656613861)
    )

    fun itemAddress(): Map<String, String> = mapOf(
        // Shopping
        "Harwood Plaza" to "300 Harwood Ave S, Ajax, ON L1S 2J1",
        "Westney Heights" to "15 Westney Rd N, Ajax, ON L1T 1P4",
        "Riocan Durham Centre" to "40 Kingston Rd E, Ajax, ON L1Z 1E9",
        "Pickering Town Centre" to "1355 Kingston Rd, Pickering, ON L1V 1B8",
        // Restaurants
        "Sunset Grill" to "300 Rossland Rd E Unit 8, Ajax, ON L1Z 0M1",
        "Stacked" to "280 Harwood Ave S Unit 2, Ajax, ON L1S 2J1",
        "Portly Piper" to "235 Bayly St W, Ajax, ON L1S 3K3",
        "Mumbay Grill" to "619 Kingston Rd W, Ajax, ON L1S 6B3",
        // Parks
        "Rotary Park" to "177 Lake Driveway W, Ajax, ON L1S 7J1",
        "Millers Creek" to "275 Westney Rd N, Ajax, ON L1T 2H9",
        "Paulynn Park" to "1571 Ravenscroft Rd, Ajax, ON L1T 0K4",
        "Hermitage Park" to "95 Church St N, Ajax, ON L1T 2W4",
        // Conservation Areas
        "Greenwood Conservation" to "2290 Greenwood Rd, Ajax, ON L1T 4S4",
        "Heber Down Conservation" to "5000 Cochrane St, Whitby, ON L1P 2A3",
        "Lynde Shores" to "623 Halls Rd S, Whitby, ON L1P 2B3",
        "Petticoat Creek" to "1100 Whites Rd S, Pickering, ON L1V 6K7"
    )

}