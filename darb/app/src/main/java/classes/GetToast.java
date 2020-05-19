package classes;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class GetToast {
	Context context;

	public static void Toast(Context context, String MSG) {
		Toast toast = Toast.makeText(context, MSG, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

}
