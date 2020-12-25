package com.udacity.asteroidradar

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.udacity.asteroidradar.main.AsteroidApiStatus

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("statusIcon2")
fun ImageView.setImageResource(item : Asteroid?) {
    item?.let {
        if (item.isPotentiallyHazardous) {
            setImageResource(R.drawable.ic_status_potentially_hazardous)
        } else {
            setImageResource(R.drawable.ic_status_normal)
        }
    }
}

@BindingAdapter("codeNameText")
fun TextView.setMainAsteroidText(item: Asteroid?) {
    item?.let {
        text = item.codename
    }
}

@BindingAdapter("asteroidDateText")
fun TextView.setAsteroidDate(item: Asteroid?) {
    item?.let {
        text = item.closeApproachDate
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}


@BindingAdapter("bindContentDescription")
fun bindContentDescription(iv: ImageView, isHazardous: Boolean){
    if (isHazardous) {
        iv.contentDescription = iv.context.getString(R.string.potentially_hazardous_asteroid_image)
    } else {
        iv.contentDescription = iv.context.getString(R.string.not_hazardous_asteroid_image)
    }
}


@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("asteroidApiStatus")
fun bindStatus(progressbar: ProgressBar, status: AsteroidApiStatus?) {
    when (status) {
        AsteroidApiStatus.LOADING -> {
            progressbar.visibility = View.VISIBLE
        }
        AsteroidApiStatus.DONE -> {
            progressbar.visibility = View.GONE
        }
    }
}

