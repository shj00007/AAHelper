package shj00007.aa.adapter;

import java.util.List;

import shj00007.aa.R;
import shj00007.aa.adapter.base.AdapterBase;
import shj00007.aa.controls.SlideMenuItem;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AdapterSlideMenu extends AdapterBase {

	private class Holder {
	
		TextView tvMenuName;
	}

	private Context mContext = null;

	public AdapterSlideMenu(Context pContext, List pList) {
		super(pContext, pList);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder _Holder;
		if (convertView == null) {
			
			convertView = getLayoutInflater()
					.inflate(R.layout.slidemenu_list_item, null);
			_Holder = new Holder();
			_Holder.tvMenuName = (TextView) convertView.findViewById(R.id.tvMenuName);
			convertView.setTag(_Holder);
		} else {
			_Holder = (Holder) convertView.getTag();
		}
		SlideMenuItem _Item=(SlideMenuItem) getList().get(position);
		

		_Holder.tvMenuName.setText(_Item.getmTitle());
		return convertView;
	}

}
