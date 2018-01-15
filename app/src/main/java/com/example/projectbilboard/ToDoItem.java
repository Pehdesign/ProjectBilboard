package com.example.projectbilboard;

/**
 * Represents an item in a ToDo list
 */
public class ToDoItem {

    @com.google.gson.annotations.SerializedName("id")
    private String mId;

    @com.google.gson.annotations.SerializedName("text")
    private String mText;

    @com.google.gson.annotations.SerializedName("owner")
    private String mOwner;

    @com.google.gson.annotations.SerializedName("description")
    private String mDescription;

    @com.google.gson.annotations.SerializedName("image")
    private String mImage;

    @com.google.gson.annotations.SerializedName("tag")
    private String mTag;

    @com.google.gson.annotations.SerializedName("region")
    private String mRegion;

    @com.google.gson.annotations.SerializedName("adress")
    private String mAdress;

    @com.google.gson.annotations.SerializedName("email")
    private String mEmail;

    @com.google.gson.annotations.SerializedName("phone")
    private String mPhone;

    @com.google.gson.annotations.SerializedName("data")
    private String mData;

    @com.google.gson.annotations.SerializedName("extra")
    private String mExtra;

    @com.google.gson.annotations.SerializedName("complete")
    private boolean mComplete;


    public ToDoItem() {

    }

    @Override
    public String toString() {
        return getText();
    }

    /**
     * Initializes a new ToDoItem
 //    * @param id   The item id
     * @param text The item text
     * @param owner   The item owner
     * @param description   The item description
     */
    public ToDoItem(String id, String text, String owner, String description, String image, String tag, String region, String adress, String email, String phone, String data, String extra, boolean complete) {
        this.setId(id);
        this.setText(text);
        this.setOwner(owner);
        this.setDescription(description);
        this.setImage(image);
        this.setTags(tag);
        this.setRegion(region);
        this.setAdress(adress);
        this.setEmail(email);
        this.setPhone(phone);
        this.setData(data);
        this.setExtra(extra);
        this.setComplete(complete);
    }

    public String getId() {
        return mId;
    }

    public final void setId(String id) {
        mId = id;
    }


    public String getText() {
        return mText;
    }

    public final void setText(String text) {
        mText = text;
    }


    public String getOwner() { return mOwner; }

    public final void setOwner(String owner) { mOwner = owner; }


    public String getDescription() {
        return mDescription;
    }

    public final void setDescription(String description) {
        mDescription = description;
    }


    public String getImage() { return mImage; }

    public final void setImage(String image) { mImage = image; }


    public String getTag() { return mTag; }

    public final void setTags(String tag) { mTag = tag; }


    public String getRegion() { return mRegion; }

    public final void setRegion(String region) { mRegion = region; }


    public String getAdress() { return mAdress; }

    public final void setAdress(String adress) { mAdress = adress; }


    public String getEmail() { return mEmail; }

    public final void setEmail(String email) { mEmail = email; }


    public String getPhone() { return mPhone; }

    public final void setPhone(String phone) { mPhone = phone; }


    public String getData() { return mData; }

    public final void setData(String data) { mData = data; }


    public String getExtra() { return mExtra; }

    public final void setExtra(String extra) { mExtra = extra; }



    public boolean isComplete() {
        return mComplete;
    }

    public void setComplete(boolean complete) {
        mComplete = complete;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ToDoItem && ((ToDoItem) o).mId == mId;
    }
}