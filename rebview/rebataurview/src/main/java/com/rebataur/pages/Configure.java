package com.rebataur.pages;

import com.rebataur.panels.AwsConfigPanel;
import com.rebataur.panels.RebConfigPanel;
import com.rebataur.panels.TwitterConfigPanel;
import com.rebataur.panels.WeatherConfigPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.Panel;

public class Configure extends WebPage {

    private static final long serialVersionUID = 1L;
    Panel configPanel;
    public Configure(final PageParameters parameters) {
        super(parameters);
        configPanel = new RebConfigPanel("config_panel");
        configPanel.setOutputMarkupPlaceholderTag(true);
        add(configPanel);
        
        AjaxLink initRebLink = new AjaxLink("init_reb_link") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                Panel rebConfigPanel = new RebConfigPanel("config_panel");                
                rebConfigPanel.setOutputMarkupId(true);
                configPanel.replaceWith(rebConfigPanel);
                configPanel = rebConfigPanel;
                target.add(configPanel);
            }
        };
        add(initRebLink);  
        
        AjaxLink twitterLink = new AjaxLink("twitter_link") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                Panel twitterCfgPanel = new TwitterConfigPanel("config_panel");                
                twitterCfgPanel.setOutputMarkupId(true);
                configPanel.replaceWith(twitterCfgPanel);
                configPanel = twitterCfgPanel;
                target.add(configPanel);
            }
        };
        add(twitterLink);  
        
        AjaxLink weatherLink = new AjaxLink("weather_link") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                Panel weatherCfgPanel = new WeatherConfigPanel("config_panel");                
                weatherCfgPanel.setOutputMarkupId(true);
                configPanel.replaceWith(weatherCfgPanel);
                configPanel = weatherCfgPanel;
                target.add(configPanel);
            }
        };
        add(weatherLink);  
         AjaxLink awsLink = new AjaxLink("aws_link") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                Panel awsCfgPanel = new AwsConfigPanel("config_panel");                
                awsCfgPanel.setOutputMarkupId(true);
                configPanel.replaceWith(awsCfgPanel);
                configPanel = awsCfgPanel;
                target.add(configPanel);
            }
        };
        add(awsLink);  
        
    }
}