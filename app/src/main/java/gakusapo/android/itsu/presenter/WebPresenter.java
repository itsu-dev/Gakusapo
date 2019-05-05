package gakusapo.android.itsu.presenter;

import gakusapo.android.itsu.presenter.contract.WebContract;

public class WebPresenter implements WebContract.Presenter {

    private WebContract.View view;

    public WebPresenter(WebContract.View view) {
        this.view = view;
    }

    @Override
    public void onCloseButtonClicked() {
        view.getActivity().finish();
    }

}
