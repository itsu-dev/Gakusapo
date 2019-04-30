package gakusapo.android.itsu.presenter;

import gakusapo.android.itsu.presenter.contract.AlertDialogContract;

public class AlertDialogPresenter implements AlertDialogContract.Presenter {

    private AlertDialogContract.View view;

    public AlertDialogPresenter(AlertDialogContract.View view) {
        this.view = view;
    }

    @Override
    public void onNegativeButtonClicked() {
        view.onNegativeButtonClicked();
        view.dismiss();
    }

    @Override
    public void onPositiveButtonClicked() {
        view.onPositiveButtonClicked();
        view.dismiss();
    }

}
