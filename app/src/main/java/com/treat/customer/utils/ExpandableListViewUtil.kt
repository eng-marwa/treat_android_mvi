package com.treat.customer.utils

import android.view.View
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView


class ExpandableListViewUtil {

    companion object{
         fun setListViewHeight(
             listView: ExpandableListView,
             group: Int,
        ) {
            val listAdapter = listView.expandableListAdapter as ExpandableListAdapter
            var totalHeight = 0
            val desiredWidth: Int = View.MeasureSpec.makeMeasureSpec(
                listView.width,
                View.MeasureSpec.EXACTLY
            )
            for (i in 0 until listAdapter.groupCount) {
                val groupItem: View = listAdapter.getGroupView(i, false, null, listView)
                groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
                totalHeight += groupItem.measuredHeight

                if (listView.isGroupExpanded(i) && i != group
                    || !listView.isGroupExpanded(i) && i == group
                ) {

                    for (j in 0 until listAdapter.getChildrenCount(i)) {
                        val listItem: View = listAdapter.getChildView(
                            i, j, false, null,
                            listView
                        )
                        listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
                        totalHeight += listItem.measuredHeight
                    }
                }

            }
            val params = listView.layoutParams
            var height = (totalHeight
                    + listView.dividerHeight * (listAdapter.groupCount - 1))
            if (height < 10) height = 200
            params.height = height
            listView.layoutParams = params
            listView.requestLayout()
        }
    }
}