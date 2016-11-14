package ua.anironglass.template.ui.activities.main;


import java.util.List;

import ua.anironglass.template.data.model.Photo;
import ua.anironglass.template.ui.activities.base.MvpView;

interface MainMvpView extends MvpView {

    void showPhotos(List<Photo> photos);

}