package ru.cnv.sample.data.api.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PersonsResponse {

    @SerializedName("response")
    ArrayList<Item> items;

    public ArrayList<Item> getItems() {
        return items;
    }

    public class Item {

        private int id;

        @SerializedName("f_name")
        private String firstName;

        @SerializedName("l_name")
        private String lastName;

        @SerializedName("birthday")
        private String birthday;

        @SerializedName("avatr_url")
        private String avatar;

        @SerializedName("specialty")
        private ArrayList<Speciality> spec;

        public String getAvatar() {
            return avatar;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getBirthday() {
            return birthday;
        }

        public ArrayList<Speciality> getSpec() {
            return spec;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public class Speciality {

            @SerializedName("specialty_id")
            private int id;

            @SerializedName("name")
            private String name;

            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }
        }
    }
}
