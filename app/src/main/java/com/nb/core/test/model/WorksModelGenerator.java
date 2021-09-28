package com.nb.core.test.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class WorksModelGenerator {

    private static final int COLOR = 0xFF000000;
    private static final Random RANDOM = new Random();

    private static int ID;

    private WorksModelGenerator() {
    }

    @NonNull
    public static List<WorksModel> createWorksModels(int count) {
        List<WorksModel> worksModels = new ArrayList<>();
        for (int i = 0; i < count; ++i) {
            WorksModel model = new WorksModel(ID++, RANDOM.nextInt() | COLOR, Math.min(Math.max(RANDOM.nextFloat(), 9.0f / 16), 3.0f / 4), "坐标快手，交友加微信：13600360342，找工作简历请发送：huanghaihua@kuaishou.com");

            worksModels.add(model);
        }
        return worksModels;
    }

    public static int getID() {
        return ID;
    }
}
