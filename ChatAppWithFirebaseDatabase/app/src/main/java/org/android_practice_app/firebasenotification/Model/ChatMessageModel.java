package org.android_practice_app.firebasenotification.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class ChatMessageModel implements Parcelable {

    String name;
    String msg;
    Long date_time;
    String _id;

    public ChatMessageModel(String name, String msg) {
        this.name = name;
        this.msg = msg;
        this.date_time = new Date().getTime();
    }

    public Long getDate_time() {
        return date_time;
    }

    public void setDate_time(Long date_time) {
        this.date_time = date_time;
    }

    public ChatMessageModel() {
    }

    protected ChatMessageModel(Parcel in) {
        name = in.readString();
        msg = in.readString();
        if (in.readByte() == 0) {
            date_time = null;
        } else {
            date_time = in.readLong();
        }
        _id = in.readString();
    }

    public static final Creator<ChatMessageModel> CREATOR = new Creator<ChatMessageModel>() {
        @Override
        public ChatMessageModel createFromParcel(Parcel in) {
            return new ChatMessageModel(in);
        }

        @Override
        public ChatMessageModel[] newArray(int size) {
            return new ChatMessageModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(msg);
        if (date_time == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(date_time);
        }
        dest.writeString(_id);
    }
}