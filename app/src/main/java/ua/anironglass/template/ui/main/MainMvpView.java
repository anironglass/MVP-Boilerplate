package ua.anironglass.template.ui.main;


import java.util.List;

import ua.anironglass.template.data.model.Photo;
import ua.anironglass.template.ui.base.MvpView;

public interface MainMvpView extends MvpView {

    void showError();
    void showNoInternetConnection();
    void showPhotosEmpty();
    void showPhotos(List<Photo> photos);

}