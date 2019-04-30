package gakusapo.android.itsu.presenter;

import gakusapo.android.itsu.R;
import gakusapo.android.itsu.api.service.TimetableDBService;
import gakusapo.android.itsu.presenter.contract.RegisterTimetableDialogContract;
import gakusapo.android.itsu.presenter.contract.TimetableContract;
import gakusapo.android.itsu.utils.TimetableUtils;

public class RegisterTimetableDialogPresenter implements RegisterTimetableDialogContract.Presenter {

    private RegisterTimetableDialogContract.View view;
    private TimetableContract.Presenter presenter;

    public RegisterTimetableDialogPresenter(RegisterTimetableDialogContract.View view, TimetableContract.Presenter presenter) {
        this.view = view;
        this.presenter = presenter;
    }

    @Override
    public void onCreateButtonClicked() {
        TimetableDBService service = new TimetableDBService(view.getActivity());
        if (view.getTimetableName().isEmpty()) {
            view.showErrorText(R.string.timetable_name_notinputed);

        } else if (service.getTimetables().containsKey(view.getTimetableName())) {
            view.showErrorText(R.string.timetable_already_exists);

        } else {
            presenter.onCreateTimetableDialogCallback(view.getTimetableName(), TimetableUtils.dayTypeStringToInt(view.getDayType()), TimetableUtils.timeTypeStringToInt(view.getTimeType()));
            view.dismiss();
        }
    }

}
