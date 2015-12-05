/*
 //   MPL
 */
package com.rebataur.panels;

import com.rebataur.WicketApplication;
import com.rebataur.services.RebServices;
import com.rebataur.utils.AWSPGConfig;
import com.rebataur.utils.RebConfig;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.wicket.AttributeModifier;
import static org.apache.wicket.ThreadContext.getApplication;
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
public class AwsConfigPanel extends Panel {

    private String awsConnTestStr = "Connection Unsuccessful";
    private static String awsConnTestFailureStyle = "label label-danger";
    private static String awsConnTestSucssesStyle = "label label-success";

    public AwsConfigPanel(String id) {
        super(id);

        final Label awsConnTest = new Label("aws_conn_test", new PropertyModel<String>(this, "awsConnTestStr"));
        if (getRS().testAWSPGConn()) {
            awsConnTestStr = "Connection Successful !";
            awsConnTest.add(new AttributeModifier("class", awsConnTestSucssesStyle));
        } else {
            awsConnTestStr = "Connection Failue ";
            awsConnTest.add(new AttributeModifier("class", awsConnTestFailureStyle));
        }
        
        
        add(awsConnTest);
        TextField<String> hostname = new TextField<>("hostname");
        TextField<String> port = new TextField<>("port");
        TextField<String> database = new TextField<>("database");
        TextField<String> username = new TextField<>("username");
        TextField<String> password = new TextField<>("password");
        AWSPGConfig awsConfig = getRS().getAWSPGConfig() == null ? new AWSPGConfig() : getRS().getAWSPGConfig();
        Form<AWSPGConfig> awsConfigForm = new Form<AWSPGConfig>("aws_pg_config_form", new CompoundPropertyModel<AWSPGConfig>(awsConfig)) {
            @Override
            protected void onSubmit() {
                try {
                    super.onSubmit();

                    getRS().saveAndInitAWSPGConfig(getModelObject());
                    if (getRS().testAWSPGConn()) {
                        awsConnTestStr = "Connection Successful !";
                        awsConnTest.add(new AttributeModifier("class", awsConnTestSucssesStyle));
                    } else {
                        awsConnTestStr = "Connection Failue ";
                        awsConnTest.add(new AttributeModifier("class", awsConnTestFailureStyle));
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(AwsConfigPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        };
        awsConfigForm.add(hostname).add(port).add(database).add(username).add(password);
        add(awsConfigForm);

    }

    public RebServices getRS() {
        return ((WicketApplication) getApplication()).getRebServices();
    }

}
