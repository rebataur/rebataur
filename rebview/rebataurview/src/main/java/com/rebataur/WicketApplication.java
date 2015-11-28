package com.rebataur;

import com.rebataur.pages.Configure;
import com.rebataur.pages.Analytics;
import com.rebataur.pages.Contact;
import com.rebataur.pages.HomePage;
import com.rebataur.services.RebServices;
import com.rebataur.utils.Constants;
import com.rebataur.utils.DBConn;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 *
 * @see com.rebataur.Start#main(String[])
 */
public class WicketApplication extends WebApplication {

    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    @Override
    public Class<? extends WebPage> getHomePage() {
        return HomePage.class;
    }

    /**
     * @see org.apache.wicket.Application#init()
     */
    @Override
    public void init() {
        super.init();
        mountPage("home", HomePage.class);
        mountPage("configure", Configure.class);
        mountPage("analytics", Analytics.class);
        mountPage("contact", Contact.class);

    }
    
    
    public RebServices getRebServices(){
        return new RebServices();
    }
 
   
}
