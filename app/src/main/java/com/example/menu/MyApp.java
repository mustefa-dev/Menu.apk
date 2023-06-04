package com.example.menu;

import android.app.Application;
import android.content.Context;
import com.example.menu.Permissions.PermissionManager;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.android.scopes.ActivityScoped;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class MyApp extends Application {

    @Provides
    @ActivityScoped
    public PermissionManager providePermissionManager(@ApplicationContext Context context) {
        return new PermissionManager(context);
    }
}
