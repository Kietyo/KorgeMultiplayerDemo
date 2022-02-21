import com.kietyo.multiplayer.gamelogic.model.Packet
import com.kietyo.multiplayer.gamelogic.model.PacketType
import com.kietyo.multiplayer.gamelogic.model.Player
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

//fun main() {
//    val packet = Packet(PacketType.PLAYER_UPDATE, Player(1, 15.0, 18.0))
//
//    val str = Json.encodeToString(packet)
//
//    println(str)
//
//    val data = Json.decodeFromString<Packet>(str)
//
//    println(data)
//}