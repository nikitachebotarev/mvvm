package ru.cnv.sample.ui.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ru.cnv.sample.data.provider.entity.Person;
import ru.cnv.sample.data.provider.entity.Spec;
import ru.cnv.sample.ui.entity.PersonItem;
import ru.cnv.sample.ui.entity.SpecItem;

public class UiUtils {

    public static final long CLICKS_THROTTLE_MILLIS = 1000;
    public static final long CLICKS_DELAY_MILLIS = 200;

    public static String unixTimeToFormattedTime(long time) {
        Date date = new Date();
        date.setTime(time);
        return new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(date);
    }

    public static String toAge(long dateLong) throws Exception {
        Calendar date = Calendar.getInstance();
        date.setTime(new Date(dateLong));
        Calendar today = Calendar.getInstance();

        int curYear = today.get(Calendar.YEAR);
        int dobYear = date.get(Calendar.YEAR);

        int age = curYear - dobYear;

        int curMonth = today.get(Calendar.MONTH);
        int dobMonth = date.get(Calendar.MONTH);
        if (dobMonth > curMonth) {
            age--;
        } else if (dobMonth == curMonth) {
            int curDay = today.get(Calendar.DAY_OF_MONTH);
            int dobDay = date.get(Calendar.DAY_OF_MONTH);
            if (dobDay > curDay) {
                age--;
            }
        }

        return String.valueOf(age);
    }

    public static PersonItem personToAdapterItem(Person person) {
        PersonItem item = new PersonItem();
        item.setId(person.getId());
        item.setFirstName(person.getFirstName());
        item.setLastName(person.getLastName());
        item.setBirthday(person.getBirthday());
        item.setAvatar(person.getAvatar());
        return item;
    }

    public static SpecItem specToAdapterItem(Spec spec) {
        SpecItem specItem = new SpecItem();
        specItem.setId(spec.getId());
        specItem.setName(spec.getName());
        return specItem;
    }
}
