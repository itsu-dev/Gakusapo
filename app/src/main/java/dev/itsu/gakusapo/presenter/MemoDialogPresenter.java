package dev.itsu.gakusapo.presenter;

import dev.itsu.gakusapo.presenter.contract.MemoDialogContract;
import dev.itsu.gakusapo.presenter.contract.TodayAndTomorrowContract;

public class MemoDialogPresenter implements MemoDialogContract.Presenter {

    private MemoDialogContract.View view;
    private TodayAndTomorrowContract.Presenter presenter;

    public MemoDialogPresenter(MemoDialogContract.View view, TodayAndTomorrowContract.Presenter presenter) {
        this.view = view;
        this.presenter = presenter;
    }

    @Override
    public void onCloseButtonClicked() {
        presenter.onMemoDialogCallback(view.getMemo());
    }

}
