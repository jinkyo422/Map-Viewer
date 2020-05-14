package mapviewer.parser.shp

import mapviewer.parser.domain.BBox
import mapviewer.parser.domain.MultiPointRecord
import mapviewer.parser.domain.Point
import mapviewer.parser.domain.ShapeType
import java.nio.ByteBuffer

class MultiPointRecordReader {

    fun readMultiPoint(buf: ByteBuffer): MultiPointRecord {

        val shapeType = ShapeType.fromType(buf.int)
        val minX = buf.double
        val minY = buf.double
        val maxX = buf.double
        val maxY = buf.double
        val recordBBox = BBox(minX, minY, maxX, maxY)
        val numParts = 1
        val numPoints = buf.int
        val parts = mutableListOf<Int>(0, numPoints)
        val points = mutableListOf<MutableList<Point>>()
        for (i in 0 until numParts) {
            val pointsOfOnePart = mutableListOf<Point>()
            for(j in parts[i] until parts[i+1]){
                pointsOfOnePart.add(Point(buf.double, buf.double))
            }
            points.add(pointsOfOnePart)
        }

        return MultiPointRecord(shapeType, recordBBox, numParts, numPoints, parts, points)
    }
}