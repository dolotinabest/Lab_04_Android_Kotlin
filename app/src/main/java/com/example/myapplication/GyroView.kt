import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.ImageView

class GyroView: androidx.appcompat.widget.AppCompatImageView {

    private var scaleX: Double = 0.toDouble()
    private var scaleY: Double = 0.toDouble()
    private var distX: Float = 0.toFloat()
    private var distY: Float = 0.toFloat()
    var angleX: Double = 0.toDouble()
    var angleY: Double = 0.toDouble()

    var gapX: Float = 0.toFloat()
        private set
    var gapY: Float = 0.toFloat()
        private set

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        scaleType = ImageView.ScaleType.CENTER
    }

    override fun setScaleType(scaleType: ImageView.ScaleType) {
        super.setScaleType(ImageView.ScaleType.CENTER)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        gyroscopeManager!!.addView(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        gyroscopeManager!!.removeView(this)
    }

    fun update(x: Double, y: Double) {
        scaleX = x
        scaleY = y
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec) - paddingLeft - paddingRight
        val height = MeasureSpec.getSize(heightMeasureSpec) - paddingTop - paddingBottom
        if (drawable != null) {
            val drawableWidth = drawable.intrinsicWidth
            val drawableHeight = drawable.intrinsicHeight
            distX = Math.abs((drawableWidth - width) * 0.5f)
            distY = Math.abs((drawableHeight - height) * 0.5f)
        }
    }

    override fun onDraw(canvas: Canvas) {
        if (drawable == null || isInEditMode) {
            super.onDraw(canvas)
            return
        }
        gapX = (distX * scaleX).toFloat()
        gapY = (distY * scaleY).toFloat()
        canvas.translate(gapX, gapY)
        super.onDraw(canvas)
        canvas.restore()
    }

    companion object {
        val gyroscopeManager = GyroListener.instance
    }
}
