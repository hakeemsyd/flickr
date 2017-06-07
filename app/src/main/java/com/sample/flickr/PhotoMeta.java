package com.sample.flickr;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 * Created by shabbas on 6/6/17.
 */

@Element(name="photos")
public class PhotoMeta {

    @Attribute(name="page")
    public String mPage;

    @Attribute(name="pages")
    public String mPages;

    @Attribute(name="perpage")
    public String mPerPage;

    @Attribute(name="total")
    public String mTotal;
}
