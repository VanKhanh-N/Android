package com.example.ontap_sqlite_filter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class VeTauAdapter extends BaseAdapter implements Filterable {
    Context context;
    ArrayList<VeTau> listSV = new ArrayList<VeTau>();

    CustomFilter filter;
    ArrayList<VeTau> filterlist = new ArrayList<VeTau>();

    public VeTauAdapter(Context context, ArrayList<VeTau> listSV) {
        this.context = context;
        this.listSV = listSV;
        this.filterlist = listSV;
    }

    @Override
    public int getCount() {
        return listSV.size();
    }

    @Override
    public Object getItem(int position) {
        return listSV.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
//        cuộn thanh cuộn xuống sẽ tạo thêm một dòng list với layout tự tạo
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.activity__ve_tau, null);
        }
        VeTau p = (VeTau) getItem(position);
        if (p != null) {
            String loai="";
            float dongia = p.getDongia();
            TextView gadi = (TextView) view.findViewById(R.id.textViewGaDi);
            TextView gaden = (TextView) view.findViewById(R.id.textViewGaDen);
            TextView khuhoi = (TextView) view.findViewById(R.id.textViewKhuHoi);
            TextView gia = (TextView) view.findViewById(R.id.textViewGia);

            if(p.isKhuhoi()==true){
                dongia = (float) (dongia * 2 * 0.95);
                loai = "Khứ Hồi";
            }
            else{
                loai="Một chiều";
            }


            gadi.setText(p.getGadi());
            gaden.setText(p.getGaden());
            khuhoi.setText(loai);
            gia.setText(dongia+"");
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomFilter();
        }
        return filter;
    }

    private class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults resuilt = new FilterResults();
            //kiem tra dieu kien o filter
            if (constraint != null && constraint.length() > 0) {
                constraint = constraint.toString().toUpperCase();
                ArrayList<VeTau> filters = new ArrayList<VeTau>();
                for (int i = 0; i < filterlist.size(); i++) {
                    if (filterlist.get(i).getGaden().toUpperCase().contains(constraint)) {
                        VeTau p = new VeTau(filterlist.get(i).getGadi(), filterlist.get(i).getGaden(),
                                filterlist.get(i).getDongia(),filterlist.get(i).isKhuhoi());
                        filters.add(p);
                    }
                }
                resuilt.count = filters.size();
                resuilt.values = filters;

            } else {
                resuilt.count = filterlist.size();
                resuilt.values = filterlist;
            }
            return resuilt;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listSV = (ArrayList<VeTau>) results.values;
            notifyDataSetChanged();

        }
    }
}

