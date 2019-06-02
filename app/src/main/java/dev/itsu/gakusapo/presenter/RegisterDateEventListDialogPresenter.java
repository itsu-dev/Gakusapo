package dev.itsu.gakusapo.presenter;

import dev.itsu.gakusapo.presenter.contract.RegisterDateEventListDialogContract;
import dev.itsu.gakusapo.presenter.contract.TodayAndTomorrowContract;
import dev.itsu.gakusapo.ui.adapter.DateEventContentAdapter;

import java.util.List;

public class RegisterDateEventListDialogPresenter implements RegisterDateEventListDialogContract.Presenter {

    private RegisterDateEventListDialogContract.View view;
    private TodayAndTomorrowContract.Presenter presenter;
    private int type;
    private List<String> data;

    public RegisterDateEventListDialogPresenter(RegisterDateEventListDialogContract.View view, TodayAndTomorrowContract.Presenter presenter, int type, List<String> data) {
        this.view = view;
        this.presenter = presenter;
        this.type = type;
        this.data = data;
    }

    @Override
    public void onAddContentButtonClicked() {
        data.add(view.getContentNameField());
        view.setContentNameField("");
        view.setListContents(new DateEventContentAdapter(view.getActivity(), data, this));
    }

    @Override
    public void onListCloseButtonClicked() {
        presenter.onDateEventDialogCallback(data, type);
    }

    @Override
    public void onContentDeleteButtonClicked(int position) {
        data.remove(position);
        view.setListContents(new DateEventContentAdapter(view.getActivity(), data, this));
    }
}
