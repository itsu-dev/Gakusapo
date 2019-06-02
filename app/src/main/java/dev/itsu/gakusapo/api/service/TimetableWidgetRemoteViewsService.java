package dev.itsu.gakusapo.api.service;

import android.content.Intent;
import android.widget.RemoteViewsService;
import dev.itsu.gakusapo.ui.adapter.TimetableWidgetRemoteViewsFactory;

public class TimetableWidgetRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new TimetableWidgetRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
