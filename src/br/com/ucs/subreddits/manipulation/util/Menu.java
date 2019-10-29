package br.com.ucs.subreddits.manipulation.util;

import br.com.ucs.subreddits.manipulation.Application;

public class Menu {
    public static void main(String[] args) {
        Application app = new Application();
        app.startApplication();
        while(true) {
            int menu = app.menu();
            boolean b = app.switchApplication(menu);

            if(!b) break;
        }

    }

}
