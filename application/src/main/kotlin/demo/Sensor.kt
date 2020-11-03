package demo

data class Sensor(
        val name: String,
        val value_type: Int,
        val unit: String,
        var value: Float,
        var last_updated: Int,
)