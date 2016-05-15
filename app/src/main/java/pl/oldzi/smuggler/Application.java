package pl.oldzi.smuggler;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

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
