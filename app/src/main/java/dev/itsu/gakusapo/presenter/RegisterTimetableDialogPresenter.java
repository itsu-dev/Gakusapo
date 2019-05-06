package dev.itsu.gakusapo.presenter;

import dev.itsu.gakusapo.R;
import dev.itsu.gakusapo.db.DatabaseDAO;
import dev.itsu.gakusapo.presenter.contract.RegisterTimetableDialogContract;
import dev.itsu.gakusapo.presenter.contract.TimetableContract;
import dev.itsu.gakusapo.utils.TimetableUtils;

public class RegisterTimetableDialogPresenter implements RegisterTimetableDialogContract.Presenter {

    private RegisterTimetableDialogContract.View view;
    private TimetableContract.Presenter presenter;

    public RegisterTimetableDialogPresenter(RegisterTimetableDialogContract.View view, TimetableContract.Presenter presenter) {
        this.view = view;
        this.presenter = presenter;
    }

    @Override
    public void onCreateButtonClicked() {
        if (view.getTimetableName().isEmpty()) {
            view.showErrorText(R.string.timetable_name_notinputed);

        } else if (DatabaseDAO.existsTimetable(view.getTimetableName())) {
            view.showErrorText(R.string.timetable_already_exists);

        } else {
            presenter.onCreateTimetableDialogCallback(view.getTimetableName(), TimetableUtils.dayTypeStringToInt(view.getDayType()), TimetableUtils.timeTypeStringToInt(view.getTimeType()));
            view.dismiss();
        }
    }

}
