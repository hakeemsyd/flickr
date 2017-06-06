package com.sample.flickr;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;


/**
 * Created by Hakeem on 6/6/17.
 */

@Root(name = "photos")
public class SearchResult {

    @ElementList( name = "photos")
    public List<Photo> mList;

    @Attribute(name = "stat")
    public String mStat;
}
