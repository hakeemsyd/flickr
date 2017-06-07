package com.sample.flickr;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;


/**
 * Created by Hakeem on 6/6/17.
 */

@Root(name= "rsp", strict = false)
public class SearchResult {

    @ElementList(name="photo", inline = true)
    @Path("photos")
    public List<Photo> mList;

    public String mSearchStr;

    @Path("photos")
    @Attribute(name="pages")
    public String mPages;

    @Path("photos")
    @Attribute(name="page")
    public String mPage;

    @Path("photos")
    @Attribute(name="total")
    public String mTotal;

    @Path("photos")
    @Attribute(name="perpage")
    public String mPerPage;
}
