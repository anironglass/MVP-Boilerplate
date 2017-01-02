package ua.anironglass.boilerplate.ui.main;


import java.util.List;

import ua.anironglass.boilerplate.data.model.Photo;
import ua.anironglass.boilerplate.ui.base.MvpView;

public interface MainMvpView extends MvpView {

    void showError();
    void showNoInternetConnection();
    void showPhotosEmpty();
    void showPhotos(List<Photo> photos);

}