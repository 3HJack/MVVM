package com.hhh.mvvm.multi;

import androidx.annotation.NonNull;

public interface DataBind<T> {

    void onDataBind(@NonNull T t);
}
