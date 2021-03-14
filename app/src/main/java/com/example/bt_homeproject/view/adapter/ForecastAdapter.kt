package com.example.bt_homeproject.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bt_homeproject.R
import com.example.bt_homeproject.db.data_model.log.ForecastLog
import com.example.bt_homeproject.utils.Utils
import kotlinx.android.synthetic.main.card_view.view.*

class ForecastAdapter(
        private val context: Context,
        private val forecastLogList: ArrayList<ForecastLog>
) : RecyclerView.Adapter<ForecastAdapter.CardViewHolder>() {

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindUI(position: Int){

            //Date
            itemView.textViewDayAndMonth.text = Utils.getDayAndMonth(forecastLogList[position].date)

            //Icon & IconPhrase
            if(Utils.getCurrentTimePeriod()=="Night"){
                val iconId: String = Utils.getIconName(forecastLogList[position].iconIdNight)
                itemView.imageViewWeatherIcon.setImageResource(context.resources.getIdentifier(iconId, "drawable", context.packageName))
                itemView.textViewPhrase.text = forecastLogList[position].iconPhraseNight
            }else{
                val iconId: String = Utils.getIconName(forecastLogList[position].iconIdDay)
                itemView.imageViewWeatherIcon.setImageResource(context.resources.getIdentifier(iconId, "drawable", context.packageName))
                itemView.textViewPhrase.text = forecastLogList[position].iconPhraseDay
            }

            //DayName
            itemView.textViewDayName.text = Utils.getDayName(forecastLogList[position].date)

            //Temperatures
            itemView.textViewMaxTemperature.text = Utils.fahrenheitToCelsius(forecastLogList[position].maxTemperature).toString()
            val minTemp = "/${Utils.fahrenheitToCelsius(forecastLogList[position].minTemperature)}"
            itemView.textViewMinTemperature.text = minTemp
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.card_view, null)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bindUI(position)
    }

    override fun getItemCount(): Int = forecastLogList.size
}