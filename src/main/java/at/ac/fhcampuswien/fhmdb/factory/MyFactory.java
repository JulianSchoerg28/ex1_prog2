package at.ac.fhcampuswien.fhmdb.factory;

import at.ac.fhcampuswien.fhmdb.HomeController;
import javafx.util.Callback;

public class MyFactory implements Callback<Class<?>, Object> {

    @Override
    public Object call(Class<?> aClass) {
    if (aClass == HomeController.class){
        return HomeController.getInstance();
    }
        try{
            return aClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}