package org.apache.maven.transaction;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.codehaus.plexus.PlexusTestCase;
import org.codehaus.plexus.digest.Digester;
import org.codehaus.plexus.util.IOUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * 
 * @author <a href="mailto:carlos@apache.org">Carlos Sanchez</a>
 * @version $Id$
 */
public abstract class AbstractFileEventTest
    extends PlexusTestCase
{
    protected List digesters;

    public void setUp()
        throws Exception
    {
        super.setUp();

        digesters = getContainer().lookupList( Digester.class.getName() );
    }

    protected void assertChecksumExists( File file, String algorithm )
    {
        assertChecksum( file, algorithm, true );
    }

    protected void assertChecksumDoesNotExist( File file, String algorithm )
    {
        assertChecksum( file, algorithm, false );
    }

    private void assertChecksum( File file, String algorithm, boolean exist )
    {
        String msg = exist ? "exists" : "does not exist";
        File checksumFile = new File( file.getPath() + "." + algorithm );
        assertEquals( "Test file " + algorithm + " checksum " + msg, exist, checksumFile.exists() );
    }

    protected void assertChecksumCommit( File file )
        throws IOException
    {
        assertChecksumExists( file, "md5" );
        assertChecksumExists( file, "sha1" );
    }

    protected void assertChecksumRollback( File file )
        throws IOException
    {
        assertChecksumDoesNotExist( file, "md5" );
        assertChecksumDoesNotExist( file, "sha1" );
    }

    protected String readFile( File file )
        throws IOException
    {
        FileInputStream in = null;
        try
        {
            in = new FileInputStream( file );
            return IOUtil.toString( in );
        }
        finally
        {
            IOUtil.close( in );
        }
    }

    protected void writeFile( File file, String content )
        throws IOException
    {
        FileOutputStream out = null;
        try
        {
            out = new FileOutputStream( file );
            IOUtil.copy( content, out );
        }
        finally
        {
            IOUtil.close( out );
        }
    }
}