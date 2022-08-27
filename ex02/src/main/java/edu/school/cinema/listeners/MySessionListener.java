package edu.school.cinema.listeners;

import edu.school.cinema.repositories.AuthenticationDao;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

//@WebListener
public class MySessionListener implements HttpSessionListener {

    AuthenticationDao dao;

    public MySessionListener(AuthenticationDao dao) {
        this.dao = dao;
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("Session Created:: ID=" + se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        dao.deleteAuthenticationBySession(se.getSession().getId());
        System.out.println("Session Destroyed:: ID=" + se.getSession().getId());
    }
}
