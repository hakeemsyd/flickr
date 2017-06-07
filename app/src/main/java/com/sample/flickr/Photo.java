package com.sample.flickr;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Hakeem on 6/6/17.
 */

@Root(name="photo")
public class Photo {
    @Attribute( name = "id")
    public long mId;
    @Attribute(name = "owner")
    public String mOwner;
    @Attribute(name = "secret")
    public String mSecret;
    @Attribute(name = "server")
    public int mServer;

    @Attribute(name = "farm")
    public long mFarm;

    @Attribute(name = "title")
    public String mTitle;

    @Attribute(name = "ispublic")
    public boolean mIsPublic;

    @Attribute(name = "isfriend")
    public boolean mIsFriend;

    @Attribute(name = "isfamily")
    public boolean mIsFamily;

/*    public Photo(long id, String owner, String secret, int server, long farm, String title,
                 boolean ispublic, boolean isfriend, boolean isfamily){
        mId = id;
        mOwner = owner;
        mSecret = secret;
        mServer = server;
        mFarm = farm;
        mTitle = title;
        mIsFamily = isfamily;
        mIsPublic = ispublic;
        mIsFriend = isfriend;
    }*/

}
