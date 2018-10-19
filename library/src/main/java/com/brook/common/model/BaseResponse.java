package com.brook.common.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 */
public class BaseResponse extends KeepClass implements Parcelable {
    public int code;
    public String desc = "";

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeString(this.desc);
    }

    public BaseResponse() {
    }

    public BaseResponse(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    protected BaseResponse(Parcel in) {
        this.code = in.readInt();
        this.desc = in.readString();
    }

    public static final Creator<BaseResponse> CREATOR = new Creator<BaseResponse>() {
        @Override
        public BaseResponse createFromParcel(Parcel source) {
            return new BaseResponse(source);
        }

        @Override
        public BaseResponse[] newArray(int size) {
            return new BaseResponse[size];
        }
    };
}
