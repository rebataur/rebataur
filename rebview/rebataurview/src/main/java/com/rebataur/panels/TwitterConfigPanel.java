/*
 * Copyright 2015 ranjan.
 * MPL
 */
package com.rebataur.panels;

import com.rebataur.WicketApplication;
import com.rebataur.services.RebServices;
import com.rebataur.utils.TwitterConfig;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.ComponentPropertyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

/**
 *
 * @author ranjan
 */
public class TwitterConfigPanel extends Panel{
    
    public TwitterConfigPanel(String id) {
        super(id);        
        try {
            TextField<String> accessToken = new TextField<>("accessToken");
            TextField<String> accessTokenSecret = new TextField<>("accessTokenSecret");
            TextField<String> consumerKey = new TextField<>("consumerKey");
            TextField<String> consumerSecret = new TextField<>("consumerSecret");
            
            TwitterConfig twitterConfig = getRS().getTwitterConfig() == null ? new TwitterConfig() : getRS().getTwitterConfig();
            Form<TwitterConfig> twitterCfgForm = new Form<TwitterConfig>("twitter_config_form",new CompoundPropertyModel<TwitterConfig>(twitterConfig)){
                @Override
                protected void onSubmit() {
                    super.onSubmit();
                    try {
                        getRS().saveAndInitTwitterConfig(getModelObject());
                    } catch (SQLException ex) {
                        Logger.getLogger(TwitterConfigPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                
            };
            twitterCfgForm.add(accessToken).add(accessTokenSecret).add(consumerKey).add(consumerSecret);
            add(twitterCfgForm);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TwitterConfigPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TwitterConfigPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public RebServices getRS() {
        return ((WicketApplication) getApplication()).getRebServices();
    }
    
}
