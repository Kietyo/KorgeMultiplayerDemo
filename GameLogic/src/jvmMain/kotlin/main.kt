import com.kietyo.multiplayer.gamelogic.model.Packet
import com.kietyo.multiplayer.gamelogic.model.Player
import com.soywiz.klock.DateTime
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
//
//    val currTime = DateTime.now()
//
//    println(DateTime.now().na)
//    println(DateTime.nowUnix())
//    println(DateTime.nowUnixLong())
//}