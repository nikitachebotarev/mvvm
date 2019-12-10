package ru.cnv.sample.vm.utils;

import android.text.TextUtils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import ru.cnv.sample.data.api.RetrofitService;
import ru.cnv.sample.data.api.entity.PersonsResponse;
import ru.cnv.sample.data.provider.entity.Person;
import ru.cnv.sample.data.provider.entity.Spec;

public class VmUtils {

    public static Person personResponseToPerson(PersonsResponse.Item response) throws ParseException {
        Person person = new Person();
        person.setId(response.getId());
        if (response.getFirstName() != null) {
            person.setFirstName(StringUtils.capitalize(response.getFirstName().toLowerCase()));
        }
        if (response.getLastName() != null) {
            person.setLastName(StringUtils.capitalize(response.getLastName().toLowerCase()));
        }
        person.setAvatar(response.getAvatar());
        if (!TextUtils.isEmpty(response.getBirthday())) {
            person.setBirthday(dateInServerFormatToUnix(response.getBirthday()));
        }
        return person;
    }

    public static Spec responseToSpec(PersonsResponse.Item.Speciality response) throws ParseException {
        Spec speciality = new Spec();
        speciality.setId(response.getId());
        speciality.setName(response.getName());
        return speciality;
    }

    public static long dateInServerFormatToUnix(String date) throws ParseException {
        return new SimpleDateFormat(RetrofitService.DATE_FORMAT, Locale.getDefault()).parse(date).getTime();
    }
}
