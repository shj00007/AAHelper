package shj00007.aa.activity;

import shj00007.aa.R;
import shj00007.aa.activity.base.ActivityFrame;
import shj00007.aa.adapter.AdapterAccountBook;
import shj00007.aa.business.BusinessAccountBook;
import shj00007.aa.controls.SlideMenuItem;
import shj00007.aa.controls.SlideMenuView.onSlideMenuListener;
import shj00007.aa.model.ModelAccountBook;
import shj00007.aa.utility.RegexTools;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ActivityAccountBook extends ActivityFrame implements
		onSlideMenuListener {

	private ListView lvAccountBookList;

	private AdapterAccountBook mAdapterAccountBook;
	private BusinessAccountBook mBusinessAccountBook;
	private ModelAccountBook mSelectModlAccountBook;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppendMainBody(R.layout.account_book);
		InitVariable();
		InitView();
		InitListeners();
		BindData();
		CreateSlideMenu(R.array.SlideMenuAccountBook);
	}

	public void InitVariable() {
		mBusinessAccountBook = new BusinessAccountBook(this);
	}

	public void InitView() {
		lvAccountBookList = (ListView) findViewById(R.id.lvAccountBookList);
	}

	public void InitListeners() {
		registerForContextMenu(lvAccountBookList);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		AdapterContextMenuInfo _AdapterContextMenuInfo = (AdapterContextMenuInfo) menuInfo;
		ListAdapter _ListAdapter = lvAccountBookList.getAdapter();

		mSelectModlAccountBook = (ModelAccountBook) _ListAdapter
				.getItem(_AdapterContextMenuInfo.position);

		menu.setHeaderIcon(R.drawable.account_book_small_icon);
		menu.setHeaderTitle(mSelectModlAccountBook.getAccountBookName());

		CreateMenu(menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case 1:
			ShowAccountBookAddOrEditDialog(mSelectModlAccountBook);
			break;
		case 2:
			Delete();
			break;
		default:
			break;
		}

		return super.onContextItemSelected(item);
	}

	public void BindData() {
		mAdapterAccountBook = new AdapterAccountBook(this);
		lvAccountBookList.setAdapter(mAdapterAccountBook);
		setTitle();
	}

	@Override
	public void onSlideMenuItemClick(View pView, SlideMenuItem pSlideMenuItem) {
		slideMenuToggle();
		if (pSlideMenuItem.getItemID() == 0) {
			ShowAccountBookAddOrEditDialog(null);
		}
	}

	private void setTitle() {
		int _Count = mAdapterAccountBook.getCount();
		String _Titel = getString(R.string.ActivityTitleAccountBook,
				new Object[] { _Count });
		SetTopBarTitle(_Titel);
	}
 
	private void ShowAccountBookAddOrEditDialog(
			ModelAccountBook pModelAccountBook) {
		View _View = GetLayouInflater().inflate(
				R.layout.account_book_add_or_edit, null);

		EditText _etAccountBookName = (EditText) _View
				.findViewById(R.id.etAccountBookName);
		CheckBox _chkIsDefault = (CheckBox) _View
				.findViewById(R.id.chkIsDefault);

		if (pModelAccountBook != null) {
			_etAccountBookName.setText(pModelAccountBook.getAccountBookName());
		}

		String _Title;

		if (pModelAccountBook == null) {
			_Title = getString(R.string.DialogTitleAccountBook,
					new Object[] { getString(R.string.TitleAdd) });
		} else {
			_Title = getString(R.string.DialogTitleAccountBook,
					new Object[] { getString(R.string.TitleEdit) });
		}

		AlertDialog.Builder _Builder = new AlertDialog.Builder(this);
		_Builder.setTitle(_Title)
				.setView(_View)
				.setIcon(R.drawable.account_book_big_icon)
				.setNeutralButton(
						getString(R.string.ButtonTextSave),
						new OnAddOrEditAccountBookListener(pModelAccountBook,
								_etAccountBookName, _chkIsDefault, true))
				.setNegativeButton(
						getString(R.string.ButtonTextCancel),
						new OnAddOrEditAccountBookListener(null, null, null,
								false)).show();
	}

	private class OnAddOrEditAccountBookListener implements
			DialogInterface.OnClickListener {
		private ModelAccountBook mModelAccountBook;
		private EditText etAccountBookName;
		private boolean mIsSaveButton;
		private CheckBox chkIsDefault;

		public OnAddOrEditAccountBookListener(
				ModelAccountBook pModelAccountBook,
				EditText petAccountBookName, CheckBox pchkIsDefault,
				Boolean pIsSaveButton) {
			mModelAccountBook = pModelAccountBook;
			etAccountBookName = petAccountBookName;
			mIsSaveButton = pIsSaveButton;
			chkIsDefault = pchkIsDefault;
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			if (mIsSaveButton == false) {
				SetAlertDialogIsClose(dialog, true);
				return;
			}

			if (mModelAccountBook == null) {
				mModelAccountBook = new ModelAccountBook();
			}

			String _AccountBookName = etAccountBookName.getText().toString()
					.trim();

			boolean _CheckResult = RegexTools
					.IsChineseEnglishNum(_AccountBookName);

			if (!_CheckResult) {
				Toast.makeText(
						getApplicationContext(),
						getString(R.string.CheckDataTextChineseEnglishNum,
								new Object[] { etAccountBookName.getHint() }),
						SHOW_TIME).show();
				setAlertDialogIsClose(dialog, false);
				return;
			} else {
				setAlertDialogIsClose(dialog, true);
			}

			_CheckResult = mBusinessAccountBook
					.IsExistAccountBookByAccountBookName(_AccountBookName,
							mModelAccountBook.getAccountBookID());

			if (_CheckResult) {
				Toast.makeText(getApplicationContext(),
						getString(R.string.CheckDataTextAccountBookExist),
						SHOW_TIME).show();

				setAlertDialogIsClose(dialog, false);
				return;
			} else {
				setAlertDialogIsClose(dialog, true);
			}

			mModelAccountBook.setAccountBookName(etAccountBookName.getText()
					.toString());

			mModelAccountBook.setAccountBookName(String
					.valueOf(etAccountBookName.getText().toString().trim()));
			if (chkIsDefault.isChecked()) {
				mModelAccountBook.setIsDefault(1);
			} else {
				mModelAccountBook.setIsDefault(0);
			}

			if (mModelAccountBook.getAccountBookID() > 0) {
				mModelAccountBook.setIsDefault(1);
			}

			boolean _Result = false;

			if (mModelAccountBook.getAccountBookID() == 0) {
				_Result = mBusinessAccountBook
						.InsertAccountBook(mModelAccountBook);
			} else {
				_Result = mBusinessAccountBook
						.UpdateAccountBookByAccountBookID(mModelAccountBook);
			}

			if (_Result) {
				BindData();
			} else {
				Toast.makeText(ActivityAccountBook.this,
						getString(R.string.TipsAddFail), 1).show();
			}
		}

	}

	private void Delete() {
		String _Message = getString(R.string.DialogMessageAccountBookDelete,
				new Object[] { mSelectModlAccountBook.getAccountBookName() });
		ShowAlertDialog(R.string.DialogTitleDelete, _Message,
				new OnDeleteClickListener());
	}

	private class OnDeleteClickListener implements
			DialogInterface.OnClickListener {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			boolean _Result = mBusinessAccountBook
					.DeleteAccountBookByAccountBookID(mSelectModlAccountBook
							.getAccountBookID());

			if (_Result == true) {
				BindData();
			}
		}
	}
}