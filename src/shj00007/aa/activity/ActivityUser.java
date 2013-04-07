package shj00007.aa.activity;

import shj00007.aa.R;
import shj00007.aa.activity.base.ActivityFrame;
import shj00007.aa.adapter.AdapterUser;
import shj00007.aa.business.BusinessUser;
import shj00007.aa.controls.SlideMenuItem;
import shj00007.aa.controls.SlideMenuView.onSlideMenuListener;
import shj00007.aa.model.ModelUser;
import shj00007.aa.utility.RegexTools;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ActivityUser extends ActivityFrame implements onSlideMenuListener {
	private ListView lvUserList = null;
	private AdapterUser mAdapterUser = null;
	private BusinessUser mBusinessUser = null;
	private ModelUser mSelectModelUser = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppendMainBody(R.layout.user);
		InitVariable();
		InitView();
		InitListeners();
		BindData();
		CreateSlideMenu(R.array.SlideMenuUser);
	}

	public void InitVariable() {

		mBusinessUser = new BusinessUser(this);
	}

	public void InitView() {
		lvUserList = (ListView) findViewById(R.id.lvUserList);
	}

	public void InitListeners() {
		registerForContextMenu(lvUserList);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		AdapterContextMenuInfo _AdapterContextMenuInfo = (AdapterContextMenuInfo) menuInfo;
		ListAdapter _ListAdapter = lvUserList.getAdapter();

		mSelectModelUser = (ModelUser) _ListAdapter
				.getItem(_AdapterContextMenuInfo.position);
		menu.setHeaderIcon(R.drawable.user_small_icon);
		menu.setHeaderTitle(mSelectModelUser.getUserName());
		int _Item[] = { 1, 2 };
		CreateMenu(menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case 1:
			showUserAddOrEditDialog(mSelectModelUser);
			break;
		case 2:
			delete();
			break;
		default:
			break;
		}
		
		return super.onContextItemSelected(item);
	}
	
	private void setTitle() {
		int _Count = mAdapterUser.getCount();
		String _Titel = getString(R.string.ActivityTitleUser,
				new Object[] { _Count });
		SetTopBarTitle(_Titel);
	}

	public void BindData() {
		mAdapterUser = new AdapterUser(this);
		lvUserList.setAdapter(mAdapterUser);
		setTitle();
	}

	@Override
	public void onSlideMenuItemClick(View pView, SlideMenuItem pSlideMenuItem) {
		// TODO Auto-generated method stub
		slideMenuToggle();
		if (pSlideMenuItem.getItemID() == 0) {
			showUserAddOrEditDialog(null);
		}

	}

	private void showUserAddOrEditDialog(ModelUser pModelUser) {

		View _View = GetLayouInflater()
				.inflate(R.layout.user_add_or_edit, null);

		EditText _etUserName = (EditText) _View.findViewById(R.id.etUserName);

		if (pModelUser != null) {
			_etUserName.setText(pModelUser.getUserName());
		}

		String _Title;

		if (pModelUser == null) {
			_Title = getString(R.string.DialogTitleUser,
					new Object[] { getString(R.string.TitleAdd) });
		} else {
			_Title = getString(R.string.DialogTitleUser,
					new Object[] { getString(R.string.TitleEdit) });

		}

		AlertDialog.Builder _Builder = new AlertDialog.Builder(this);
		_Builder.setTitle(_Title)
				.setView(_View)
				.setIcon(R.drawable.user_big_icon)
				.setNeutralButton(
						getString(R.string.ButtonTextSave),
						new onAddOrEditUserListener(pModelUser, _etUserName,
								true))
				.setNegativeButton(getString(R.string.ButtonTextCancel),
						new onAddOrEditUserListener(null, null, false)).show();
	}

	private class onAddOrEditUserListener implements
			DialogInterface.OnClickListener {

		private ModelUser mModelUser;
		private EditText etUserName;
		private boolean mIsSaveButton;

		public onAddOrEditUserListener(ModelUser pModelUser,
				EditText petuserName, Boolean pIsSaveButton) {
			this.mModelUser = pModelUser;
			this.etUserName = petuserName;
			this.mIsSaveButton = pIsSaveButton;
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			if (false == mIsSaveButton) {
				setAlertDialogIsClose(dialog, true);
				return;
			}

			if (mModelUser == null) {
				mModelUser = new ModelUser();
			}

			String _UserName = etUserName.getText().toString().trim();

			boolean _CheckResult = RegexTools.IsChineseEnglishNum(_UserName);
			if (!_CheckResult) {

				Toast.makeText(
						getApplicationContext(),
						getString(R.string.CheckDataTextChineseEnglishNum,
								new Object[] { etUserName.getHint() }),
						SHOW_TIME).show();
				setAlertDialogIsClose(dialog, false);
				return;
			} else {
				setAlertDialogIsClose(dialog, true);
			}

			_CheckResult = mBusinessUser.IsExistUserByUserName(_UserName,
					mModelUser.getUserID());

			if (_CheckResult) {
				Toast.makeText(getApplicationContext(),
						getString(R.string.CheckDataTextUserExist), SHOW_TIME)
						.show();
				setAlertDialogIsClose(dialog, false);
				return;
			} else {
				setAlertDialogIsClose(dialog, true);
			}

			mModelUser.setUserName(etUserName.getText().toString());

			boolean _Result = false;

			if (mModelUser.getUserID() == 0) {

				_Result = mBusinessUser.insertUser(mModelUser);
			} else {
				_Result = mBusinessUser.UpdateUserByUserID(mModelUser);

			}
			if (_Result) {
				BindData();
			} else {
				Toast.makeText(ActivityUser.this,
						getString(R.string.TipsAddFail), SHOW_TIME).show();
			}
		}

	}

	private void delete(){
		String _Message = getString(R.string.DialogMessageUserDelete,
				new Object[] { mSelectModelUser.getUserName() });
		ShowAlertDialog(R.string.DialogTitleDelete, _Message,
				new onDeleteClickListener());
	}
	
	private class onDeleteClickListener implements DialogInterface.OnClickListener{

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			boolean _Result=mBusinessUser.HideUserByUserID(mSelectModelUser.getUserID());
		
			if(_Result == true){
				BindData();
			}
		}
		
	}
}
