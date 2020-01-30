package in.nitjsr.cognitio.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import in.nitjsr.cognitio.Modals.ExpandableListModal;
import in.nitjsr.cognitio.R;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private ArrayList<ExpandableListModal> _listData;

    public ExpandableListAdapter(Context context, ArrayList<ExpandableListModal> listData) {
        this._context = context;
        this._listData = listData;
    }

    @Override
    public int getGroupCount() {
        return this._listData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
        //this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listData.get(_listData.size() - 1 - groupPosition).getHeader();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this._listData.get(_listData.size() - 1 - groupPosition).getDescription();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_faq_view_group, null);
        }

        TextView lblListHeader = convertView
                .findViewById(R.id.group_expand_list);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_faq_view_child, null);
        }

        TextView txtListChild = convertView
                .findViewById(R.id.child_expand_list);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
