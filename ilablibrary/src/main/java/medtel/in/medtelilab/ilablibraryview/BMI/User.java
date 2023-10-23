package medtel.in.medtelilab.ilablibraryview.BMI;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class User implements Parcelable {
    private String userId;
    private int height;
    private String gender;
    private Date birthDay;
    private int athleteType;
    private int choseShape;
    private int choseGoal;
    private double clothesWeight;
    public static final Creator<User> CREATOR = new Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDay() {
        return this.birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public User() {
    }

    public int getAthleteType() {
        return this.athleteType;
    }

    public void setAthleteType(int athleteType) {
        this.athleteType = athleteType;
    }

    public int getChoseShape() {
        return this.choseShape;
    }

    public void setChoseShape(int choseShape) {
        this.choseShape = choseShape;
    }

    public int getChoseGoal() {
        return this.choseGoal;
    }

    public void setChoseGoal(int choseGoal) {
        this.choseGoal = choseGoal;
    }

    public double getClothesWeight() {
        return this.clothesWeight;
    }

    public void setClothesWeight(double clothesWeight) {
        this.clothesWeight = clothesWeight;
    }

    public String toString() {
        return "User{userId='" + this.userId + '\'' + ", height=" + this.height + ", gender='" + this.gender + '\'' + ", birthDay=" + this.birthDay + ", athleteType=" + this.athleteType + ", choseShape=" + this.choseShape + ", choseGoal=" + this.choseGoal + ", clothesWeight=" + this.clothesWeight + '}';
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeInt(this.height);
        dest.writeString(this.gender);
        dest.writeLong(this.birthDay != null ? this.birthDay.getTime() : -1L);
        dest.writeInt(this.athleteType);
        dest.writeInt(this.choseShape);
        dest.writeInt(this.choseGoal);
        dest.writeDouble(this.clothesWeight);
    }

    protected User(Parcel in) {
        this.userId = in.readString();
        this.height = in.readInt();
        this.gender = in.readString();
        long tmpBirthDay = in.readLong();
        this.birthDay = tmpBirthDay == -1L ? null : new Date(tmpBirthDay);
        this.athleteType = in.readInt();
        this.choseShape = in.readInt();
        this.choseGoal = in.readInt();
        this.clothesWeight = in.readDouble();
    }
}
