package com.blog.common.base.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebListener
public class SessionListener implements HttpSessionListener {

    private static Map sessionmap = new ConcurrentHashMap();

    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    public void sessionDestroyed(HttpSessionEvent se) {
        sessionmap.remove(se.getSession().getId());
    }

    /*
	 * map做修改操作时，不能直接remove,否则会出异常，应用Iterator的remove来做
	 */
    public static boolean isAlreadyLogon(HttpSession session, String sUserName) {
        boolean flag = false;
        if (sessionmap.containsValue(sUserName)) {
            flag = true;

            Iterator it = sessionmap.entrySet().iterator();
            while ( it.hasNext() ) {
                Map.Entry item = (Map.Entry)it.next();
                Object key = item.getKey();
                Object val = item.getValue();
                if( ( (String)val ).equals(sUserName) ) {
                    it.remove();
                    sessionmap.remove(key);
                }
            }
            sessionmap.put( session.getId(),sUserName );
        }else{
            flag = false;
            sessionmap.put( session.getId(),sUserName );
        }
        return flag;
    }
}
