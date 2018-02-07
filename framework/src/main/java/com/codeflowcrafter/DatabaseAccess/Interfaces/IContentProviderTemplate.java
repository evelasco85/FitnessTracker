package com.codeflowcrafter.DatabaseAccess.Interfaces;

import android.content.UriMatcher;
import android.net.Uri;

import com.codeflowcrafter.DatabaseAccess.BaseTable;

/**
 * Created by aiko on 4/29/17.
 */

public interface IContentProviderTemplate
{
    UriMatcher GetUriMatcher();
    Uri GetContentUri();
    BaseTable GetUnderlyingTable();
}
