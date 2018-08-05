package com.example.sirisha.bakeguide;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sirisha on 15-06-2018.
 */

public class StepsModel implements Parcelable{

    public static final Creator<StepsModel> CREATOR = new Creator<StepsModel>() {
        @Override
        public StepsModel createFromParcel(Parcel in) {
            return new StepsModel(in);
        }

        @Override
        public StepsModel[] newArray(int size) {
            return new StepsModel[size];
        }
    };
    int id;
    String shortdescription, description, videourl, thumbnailurl;

    public StepsModel(int fid, String fshortdescription, String fdescription, String fvideourl, String fthumbnailurl) {
        this.id = fid;
        this.shortdescription = fshortdescription;
        this.description = fdescription;
        this.videourl = fvideourl;
        this.thumbnailurl = fthumbnailurl;
    }

    protected StepsModel(Parcel in) {
        id=in.readInt();
        shortdescription=in.readString();
        description=in.readString();
        videourl=in.readString();
        thumbnailurl=in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortdescription() {
        return shortdescription;
    }

    public void setShortdescription(String shortdescription) {
        this.shortdescription = shortdescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideourl() {
        return videourl;
    }

    public void setVideourl(String videourl) {
        this.videourl = videourl;
    }

    public String getThumbnailurl() {
        return thumbnailurl;
    }

    public void setThumbnailurl(String thumbnailurl) {
        this.thumbnailurl = thumbnailurl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getId());
        dest.writeString(getShortdescription());
        dest.writeString(getDescription());
        dest.writeString(getVideourl());
        dest.writeString(getThumbnailurl());
    }
}
