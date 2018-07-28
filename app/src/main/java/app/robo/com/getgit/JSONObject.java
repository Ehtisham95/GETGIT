package app.robo.com.getgit;

import android.os.Parcel;
import android.os.Parcelable;

public class JSONObject implements Parcelable {

    private String full_name;
    private String size;
    private String watchers_count;
    private  String forks_count;
    private String language;
    private  String commits_url;
    private String contributors_url;
    private   String star;
    private String pic;
    private String url;

    @Override
    public int describeContents() {
        return 0;
    }

    public String getUrl() {
        return url;
    }

    public JSONObject(String full_name, String pic, String size, String stars, String watchers_count, String forks_count, String language, String commits_url, String contributors_url,String url){

        this.full_name = full_name;
        this.size = size;
        this.star=stars;
        this.watchers_count=watchers_count;
        this.forks_count=forks_count;
        this.language=language;
        this.commits_url=commits_url;
        this.contributors_url=contributors_url;
        this.pic=pic;
        this.url=url;

    }


    public String getFull_name() {
        return full_name;
    }


    public String getSize() {
        return size;
    }



    public String getWatchers_count() {
        return watchers_count;
    }



    public String getForks_count() {
        return forks_count;
    }


    public String getLanguage() {
        return language;
    }


    public String getCommits_url() {
        return commits_url;
    }


    public String getContributors_url() {
        return contributors_url;
    }

    public String getStar() {
        return star;
    }

    public String getPic() {
        return pic;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(full_name);
        dest.writeString(pic);
        dest.writeString(size);
        dest.writeString(star);
        dest.writeString(watchers_count);
        dest.writeString(forks_count);
        dest.writeString(language);
        dest.writeString(commits_url);
        dest.writeString(contributors_url);
        dest.writeString(url);



    }

    public JSONObject(Parcel in){
        full_name = in.readString();
        pic = in.readString();
        size = in.readString();
        star = in.readString();
        watchers_count = in.readString();
        forks_count = in.readString();
        language = in.readString();
        commits_url = in.readString();
        contributors_url = in.readString();
        url = in.readString();

    }


    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public JSONObject createFromParcel(Parcel in) {
            return new JSONObject(in);


        }

        public JSONObject[] newArray(int size) {
            return new JSONObject[size];
        }
    };


}

