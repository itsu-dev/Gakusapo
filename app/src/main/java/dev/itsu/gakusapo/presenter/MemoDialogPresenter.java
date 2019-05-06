package dev.itsu.gakusapo.presenter;

import dev.itsu.gakusapo.presenter.contract.MemoDialogContract;
import dev.itsu.gakusapo.presenter.contract.onDestroy;

public class MemoDialogPresenter implements MemoDialogContract.Presenter {

    private MemoDialogContract.View view;
    private onDestroy.Presenter presenter;

    public MemoDialogPresenter(MemoDialogContract.View view, onDestroy.Presenter presenter) {
        this.view = view;
        this.presenter = presenter;
    }

    @Override
    public void onCloseButtonClicked() {
        presenter.onMemoDialogCallback(view.getMemo());
    }

}
