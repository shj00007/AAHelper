package shj00007.aa.adapter;

import java.util.List;

import shj00007.aa.R;
import shj00007.aa.adapter.base.AdapterBase;
import shj00007.aa.business.BusinessUser;
import shj00007.aa.model.ModelUser;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterUser extends AdapterBase {

	private class Holder {

		ImageView ivUserIcon;
		TextView tvUserName;
	}

	public AdapterUser(Context pContext) {
		super(pContext, null);
		BusinessUser _BusinessUser = new BusinessUser(pContext);
		List _List = _BusinessUser.getNotHideUser();
		setList(_List);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder _Holder;
		if (convertView == null) {

			convertView = getLayoutInflater().inflate(R.layout.user_list_item,
					null);
			_Holder = new Holder();
			_Holder.ivUserIcon = (ImageView) convertView
					.findViewById(R.id.ivUserIcon);
			_Holder.tvUserName = (TextView) convertView
					.findViewById(R.id.tvUserName);
			convertView.setTag(_Holder);
		} else {
			_Holder = (Holder) convertView.getTag();
		}
		ModelUser _Info = (ModelUser) getList().get(position);
		_Holder.ivUserIcon.setImageResource(R.drawable.user_big_icon);
		_Holder.tvUserName.setText(_Info.getUserName());
		return convertView;
	}

}
