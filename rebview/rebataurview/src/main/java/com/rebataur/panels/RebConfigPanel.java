/*
 //   MPL
 */
package com.rebataur.panels;

import com.rebataur.WicketApplication;
import com.rebataur.services.RebServices;
import com.rebataur.utils.RebConfig;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;

/**
 *
 * @author ranjan
 */
public class RebConfigPanel extends Panel {

    private String pgConnTestStr = "Connection Unsuccessful";
    private static String pgConnTestFailureStyle = "label label-danger";
    private static String pgConnTestSucssesStyle = "label label-success";

    public RebConfigPanel(String id) {
        super(id);
        try {

            final Label pgConnTest = new Label("reb_conn_test", new PropertyModel<String>(this, "pgConnTestStr"));
            if (getRS().testPGConn()) {
                pgConnTest.add(new AttributeModifier("class", pgConnTestSucssesStyle));
            } else {
                pgConnTest.add(new AttributeModifier("class", pgConnTestFailureStyle));
            }

            add(pgConnTest);
            TextField<String> hostname = new TextField<>("hostname");
            TextField<String> port = new TextField<>("port");
            TextField<String> database = new TextField<>("database");
            TextField<String> username = new TextField<>("username");
            TextField<String> password = new TextField<>("password");
            RebConfig rebConfig = getRS().getRebConfig() == null ? new RebConfig() : getRS().getRebConfig();
            Form<RebConfig> rebConfigForm = new Form<RebConfig>("reb_config_form", new CompoundPropertyModel<>(rebConfig)) {
                @Override
                protected void onSubmit() {
                    super.onSubmit();
                    RebConfig config = getModelObject();
                    try {
                        getRS().saveAndInitRebConfig(config);
                        if (getRS().testPGConn()) {
                            pgConnTestStr = "Connection Successful !";
                            pgConnTest.add(new AttributeModifier("class", pgConnTestSucssesStyle));
                        } else {
                            pgConnTestStr = "Connection Failue ";
                            pgConnTest.add(new AttributeModifier("class", pgConnTestFailureStyle));
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(RebConfigPanel.class.getName()).log(Level.SEVERE, null, ex);

                    }
                }

            };

            rebConfigForm.add(hostname).add(port).add(database).add(username).add(password);
            add(rebConfigForm);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(RebConfigPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public RebServices getRS() {
        return ((WicketApplication) getApplication()).getRebServices();
    }

}
