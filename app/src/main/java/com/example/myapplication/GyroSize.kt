import android.graphics.Bitmap
import com.squareup.picasso.Transformation

class GyroSizer(private val width: Int, private val height: Int) : Transformation {

    private var outWidth: Double = 0.toDouble()
    private var outHeight: Double = 0.toDouble()

    override fun transform(source: Bitmap): Bitmap {
        if (source.width == 0 || source.height == 0) {
            return source
        }

        outWidth = source.width.toDouble()
        outHeight = source.height.toDouble()

        val ratio = outWidth / outHeight
        val diff: Int

        if (height <= width) {
            diff = height / 8
            outWidth = (width + 2 * diff).toDouble()
            outHeight = outWidth / ratio
        } else {
            diff = width / 8
            outHeight = (height + 2 * diff).toDouble()
            outWidth = outHeight * ratio
        }

        val result = Bitmap.createScaledBitmap(source, outWidth.toInt(), outHeight.toInt(), false)

        if (result != source) {
            // Return sane Bitmap - Size is equal
            source.recycle()
        }

        return result
    }

    override fun key(): String {
        return ""
    }
}
