package org.apache.maven.shared.runtime;

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

import java.net.MalformedURLException;
import java.net.URL;

import org.codehaus.plexus.util.StringUtils;

/**
 * Provides various utility methods for working with classes.
 * 
 * @author <a href="mailto:markh@apache.org">Mark Hobson</a>
 * @version $Id$
 */
final class ClassUtils
{
    // constructors -----------------------------------------------------------

    /**
     * {@code ClassUtils} is not intended to be instantiated.
     */
    private ClassUtils()
    {
        throw new AssertionError();
    }

    // public methods ---------------------------------------------------------

    /**
     * Gets a URL to the specified class's default package. For example, if the class {@code foo.Bar} is supplied, then
     * a URL to the directory above {@code foo} is returned.
     * 
     * @param klass
     *            the class to obtain the base URL for
     * @return a URL to the class's default package
     * @throws MalformedURLException
     *             if the base URL cannot be determined
     */
    public static URL getBaseURL( Class<?> klass )
        throws MalformedURLException
    {
        URL url = getURL( klass );

        String className = klass.getName();

        int n = StringUtils.countMatches( className, "." );
        String relativePath = StringUtils.repeat( "../", n );

        return new URL( url, relativePath );
    }

    /**
     * Gets a URL to the specified class.
     * 
     * @param klass
     *            the class to obtain the URL for
     * @return a URL to the class, or {@code null} if it cannot be found
     */
    public static URL getURL( Class<?> klass )
    {
        ClassLoader classLoader = klass.getClassLoader();

        String path = klass.getName().replace( '.', '/' ) + ".class";

        return classLoader.getResource( path );
    }
}
