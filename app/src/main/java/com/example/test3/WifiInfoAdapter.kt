package com.example.test3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.realm.OrderedRealmCollection
import io.realm.RealmBaseAdapter

class WifiInfoAdapter(data: OrderedRealmCollection<WifiInfo>?) : RealmBaseAdapter<WifiInfo>(data) {

    inner class ViewHolder(cell: View){
        val title = cell.findViewById<TextView>(android.R.id.text1)
        val level = cell.findViewById<TextView>(android.R.id.text2)
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder

        when(convertView) {
            null->{
                val inflater = LayoutInflater.from(parent?.context)
                view = inflater.inflate(android.R.layout.simple_list_item_2, parent, false)
                viewHolder = ViewHolder(view)
                view.tag = viewHolder
            }
            else->{
                view = convertView
                viewHolder = view.tag as ViewHolder
            }
        }

        adapterData?.run {
            val schedule = get(position)
            viewHolder.title.text = schedule.name
            //viewHolder.level.text = schedule.level.toString()
        }
        return view
        //TODO("not implemented")
        // To change body of created functions use File | Settings | File Templates.
    }

}