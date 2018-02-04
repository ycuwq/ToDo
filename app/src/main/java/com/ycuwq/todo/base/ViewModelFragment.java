package com.ycuwq.todo.base;

import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.ycuwq.common.util.SnackBarUtil;


/**
 * 基础的Fragment
 * Created by 杨晨 on 2017/5/10.
 */
public class ViewModelFragment extends Fragment {

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

    protected void registerSnackbarText(BaseViewModel viewModel) {
        Observable.OnPropertyChangedCallback snakeBarCallback = new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                SnackBarUtil.showSnackbar(getView(), viewModel.getSnackbarText());
            }
        };
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {

                @Override
                public void onFragmentActivityCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
                    super.onFragmentActivityCreated(fm, f, savedInstanceState);
                    viewModel.snackbarText.addOnPropertyChangedCallback(snakeBarCallback);

                }

                @Override
                public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
                    super.onFragmentDestroyed(fm, f);
                    viewModel.snackbarText.removeOnPropertyChangedCallback(snakeBarCallback);
                }
            }, false);
        }
    }
}
