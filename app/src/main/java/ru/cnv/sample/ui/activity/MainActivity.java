package ru.cnv.sample.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import ru.cnv.sample.R;
import ru.cnv.sample.ui.activity.base.BaseActivity;
import ru.cnv.sample.ui.fragment.PersonDetailsFragment;
import ru.cnv.sample.ui.fragment.PersonsBySpecFragment;
import ru.cnv.sample.ui.fragment.PersonsFragment;
import ru.cnv.sample.ui.fragment.SpecsFragment;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        PersonsBySpecFragment.Listener, PersonsFragment.Listener, SpecsFragment.Listener {

    @BindView(R.id.navigation_nv)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        bottomNavigationView.setSelectedItemId(R.id.navigation_persons);
    }

    public void switchToSpecsFragment() {
        SpecsFragment fragment = (SpecsFragment) getSupportFragmentManager()
                .findFragmentByTag(SpecsFragment.class.getSimpleName());
        if (fragment == null) {
            fragment = new SpecsFragment();
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_vg, fragment)
                .commit();
    }

    public void switchToPersonsFragment() {
        PersonsFragment fragment = (PersonsFragment) getSupportFragmentManager()
                .findFragmentByTag(PersonsFragment.class.getSimpleName());
        if (fragment == null) {
            fragment = new PersonsFragment();
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_vg, fragment)
                .commit();
    }

    public void openPersonDetailsFragment(int id) {
        PersonDetailsFragment fragment = (PersonDetailsFragment) getSupportFragmentManager()
                .findFragmentByTag(PersonDetailsFragment.class.getSimpleName());
        if (fragment == null) {
            fragment = new PersonDetailsFragment();
            Bundle args = new Bundle();
            args.putInt(PersonDetailsFragment.ARG_ID, id);
            fragment.setArguments(args);
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_vg, fragment)
                .addToBackStack(PersonDetailsFragment.class.getSimpleName())
                .commit();
    }

    public void openPersonsBySpecFragment(int id) {
        PersonsBySpecFragment fragment = (PersonsBySpecFragment) getSupportFragmentManager()
                .findFragmentByTag(PersonsBySpecFragment.class.getSimpleName());
        if (fragment == null) {
            fragment = new PersonsBySpecFragment();
            Bundle args = new Bundle();
            args.putInt(PersonsBySpecFragment.ARG_ID, id);
            fragment.setArguments(args);
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_vg, fragment)
                .addToBackStack(PersonDetailsFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void onPersonsBySpecFragmentPersonSelected(int id) {
        openPersonDetailsFragment(id);
    }

    @Override
    public void onPersonsFragmentPersonSelected(int id) {
        openPersonDetailsFragment(id);
    }

    @Override
    public void onSpecsFragmentSpecSelected(int id) {
        openPersonsBySpecFragment(id);
    }

    @Override
    public void onClose() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_specs:
                switchToSpecsFragment();
                return true;
            case R.id.navigation_persons:
                switchToPersonsFragment();
                return true;
        }
        return false;
    }
}
