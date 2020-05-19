package classes;

import android.app.ProgressDialog;
import android.content.Context;

public class ShowProgressDialog {

	ProgressDialog progressDialog;

	public ShowProgressDialog(Context context, String MSG) {
		progressDialog = new ProgressDialog(context);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setMessage(MSG);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setCancelable(false);
	}

	public ShowProgressDialog(Context context, String MSG, String Tital) {
		progressDialog = new ProgressDialog(context);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setMessage(MSG);
		progressDialog.setTitle(Tital);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setCancelable(false);
	}

	public ShowProgressDialog(Context context, String Title, String MSG,
			int ImgeDrawable) {
		progressDialog = new ProgressDialog(context);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setTitle(Title);
		progressDialog.setMessage(MSG);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setCancelable(false);
		if (ImgeDrawable != 0) {
			progressDialog.setIcon(ImgeDrawable);
		}

	}

	public void ShowDialog() {
		progressDialog.show();
	}

	public void EndDialog() {
		progressDialog.dismiss();
	}
}
