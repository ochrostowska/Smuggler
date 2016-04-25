package pl.oldzi.smuggler;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Oldzi on 01.04.2016.
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        customFontInit();
    }

    private void customFontInit() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/segoeuil.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }
}
