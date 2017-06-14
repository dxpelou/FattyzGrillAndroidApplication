package com.louanimashaun.fattyzgrill;

/**
 * Created by louanimashaun on 14/06/2017.
 *
 * defines how the view and presenter communicates
 */

public interface MenuContract {

    interface View {

        void showMenuItems();

        void showNoMenuItems();

        void setPresenter(Presenter presenter);
    }

    interface Presenter{

        void loadMenuItems();

        void start();
    }
}
