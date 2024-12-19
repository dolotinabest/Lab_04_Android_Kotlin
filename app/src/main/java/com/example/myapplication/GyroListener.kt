import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.view.View


class GyroListener : SensorEventListener {

    private val fraction = 1.0f / 1000000000.0f
    private val mViewsMap = HashMap<GyroView, Boolean>(9)
    private val activityList = ArrayList<Activity>()

    private var sensorManager: SensorManager? = null
    private var lastUpdated: Long = 0
    private val maxDegree = Math.PI / 2

    fun addView(gyroView: GyroView) {
        mViewsMap[gyroView] = activityList.contains(contextToActivity(gyroView))
    }

    fun removeView(gyroView: GyroView) {
        mViewsMap.remove(gyroView)
    }

    fun attachListener(activity: Activity) {
        activityList.add(activity)
        for (view in mViewsMap.keys) {
            if (contextToActivity(view) == activity) {
                mViewsMap[view] = true
            }
        }
        if (sensorManager == null) {
            sensorManager = activity.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            val sensor = sensorManager?.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
            sensor?.let {
                sensorManager?.registerListener(this, it, SensorManager.SENSOR_DELAY_FASTEST)
            }
            lastUpdated = 0
        }
    }

    fun detachListener(activity: Activity) {
        activityList.remove(activity)
        for (view in mViewsMap.keys) {
            if (contextToActivity(view) == activity) {
                mViewsMap[view] = false
            }
        }
        sensorManager?.unregisterListener(this)
        sensorManager = null
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_GYROSCOPE) {
            if (lastUpdated != 0L) {
                for ((key, value) in mViewsMap) {
                    if (value) {
                        key.angleX += event.values[0] * (event.timestamp - lastUpdated) * fraction * 2.0F
                        key.angleY += event.values[1] * (event.timestamp - lastUpdated) * fraction * 2.0F

                        key.angleX = key.angleX.coerceIn(-maxDegree, maxDegree)
                        key.angleY = key.angleY.coerceIn(-maxDegree, maxDegree)
                    }
                }
            }
            lastUpdated = event.timestamp
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // To be implemented if needed
    }

    private fun contextToActivity(view: View): Activity {
        return view.context as Activity
    }

    companion object {
        val instance: GyroListener by lazy { GyroListener() }
    }
}



