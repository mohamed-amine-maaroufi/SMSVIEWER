package com.amine.smsviewer;

import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<Integer> expandableListTitle;
    private HashMap<Integer, List<Message>> expandableListDetail;

    public CustomExpandableListAdapter(Context context, List<Integer> expandableListTitle,
                                       HashMap<Integer, List<Message>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Message getChild(int listPosition, int expandedListPosition) {

        return (!this.expandableListDetail.get(this.expandableListTitle.get(listPosition)).isEmpty() ?
                this.expandableListDetail.get(this.expandableListTitle.get(listPosition)).get(0)  :
                new Message("", "", "", 0.0f));

    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = getChild(listPosition, expandedListPosition).getMessageContent();
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.expandedListItem);
        expandedListTextView.setText(expandedListText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Message getGroup(int listPosition) {

        return (!this.expandableListDetail.get(this.expandableListTitle.get(listPosition)).isEmpty() ?
                this.expandableListDetail.get(this.expandableListTitle.get(listPosition)).get(0)  :
                new Message("", "", "", 0.0f));
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition).getMessageNumber();
        String listDate = (String) getGroup(listPosition).getDate();

        float fee = getGroup(listPosition).getFee();;
        String listPrice = "ADE " + String.valueOf(fee);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);

        TextView listDateTextView = (TextView) convertView
                .findViewById(R.id.listDate);
        listDateTextView.setText(listDate);

        TextView listPriceTextView = (TextView) convertView
                .findViewById(R.id.listPrice);
        listPriceTextView.setText(listPrice);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}