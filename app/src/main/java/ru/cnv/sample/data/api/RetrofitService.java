package ru.cnv.sample.data.api;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.cnv.sample.data.api.entity.PersonsResponse;

public interface RetrofitService {

    String BASE_URL = "https://gitlab.65apps.com";
    String DATE_FORMAT = "yyyy-MM-dd";

    @GET("https://gitlab.65apps.com/65gb/static/raw/master/testTask.json")
    Call<PersonsResponse> getPersons();
}
