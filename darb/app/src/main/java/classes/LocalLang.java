package classes;

import android.content.Context;
import android.content.res.Configuration;

import java.util.Locale;

public class LocalLang {
    public static void setLocale(final Context ctx, final String lang) {
        final Locale loc = new Locale(lang);
        Locale.setDefault(loc);
        final Configuration cfg = new Configuration();
        cfg.locale = loc;
        ctx.getResources().updateConfiguration(cfg, null);
    }
}
