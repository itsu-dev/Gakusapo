package dev.itsu.gakusapo.presenter;

import dev.itsu.gakusapo.R;
import dev.itsu.gakusapo.presenter.contract.RegisterSubjectDialogContract;
import dev.itsu.gakusapo.presenter.contract.TimetableContract;

public class RegisterSubjectDialogPresenter implements RegisterSubjectDialogContract.Presenter {

    private RegisterSubjectDialogContract.View view;
    private TimetableContract.Presenter presenter;

    private int colorId;

    public RegisterSubjectDialogPresenter(RegisterSubjectDialogContract.View view, TimetableContract.Presenter presenter) {
        this.view = view;
        this.presenter = presenter;
        this.colorId = R.color.subjectBrown;
    }

    @Override
    public void onColorButtonClicked(int colorId) {
        this.colorId = colorId;
        view.setBackgroundColor(colorId);
    }

    @Override
    public void onRegisterButtonClicked() {
        presenter.onRegisterSubjectDialogCallback(view.getName(), view.getClassName(), view.getDescription(), colorId);
    }
}
