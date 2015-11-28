/*
 //   MPL
 */
package com.rebataur.panels;

import com.rebataur.WicketApplication;
import com.rebataur.services.RebServices;
import com.rebataur.utils.TwitterConfig;
import com.rebataur.utils.WeatherConfig;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;

/**
 *
 * @author ranjan
 */
public class WeatherConfigPanel extends Panel {

    public WeatherConfigPanel(String id) {
        super(id);
        try {
            TextField<String> key = new TextField<>("key");
            WeatherConfig weatherConfig = getRS().getWeatherConfig() == null ? new WeatherConfig() : getRS().getWeatherConfig();
            Form<WeatherConfig> weatherConfigForm = new Form<WeatherConfig>("weather_config_form", new CompoundPropertyModel<WeatherConfig>(weatherConfig)) {
                @Override
                protected void onSubmit() {
                    super.onSubmit();
                    try {
                        getRS().saveAndInitWeatherConfig(getModelObject());
                    } catch (SQLException ex) {
                        Logger.getLogger(WeatherConfigPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            };
            weatherConfigForm.add(key);
            add(weatherConfigForm);
        } catch (Exception ex) {
            Logger.getLogger(TwitterConfigPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public RebServices getRS() {
        return ((WicketApplication) getApplication()).getRebServices();
    }
}
