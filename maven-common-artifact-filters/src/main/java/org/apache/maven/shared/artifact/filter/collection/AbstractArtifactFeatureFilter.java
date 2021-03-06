package org.apache.maven.shared.artifact.filter.collection;

/* 
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.    
 */

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.codehaus.plexus.util.StringUtils;

/**
 * This is the common base class of ClassifierFilter and TypeFilter
 * 
 * @author <a href="richardv@mxtelecom.com">Richard van der Hoff</a>
 * @version $Id$
 */
public abstract class AbstractArtifactFeatureFilter
    extends AbstractArtifactsFilter
{
    /** The list of types or classifiers to include */
    private List includes;

    /**
     * The list of types or classifiers to exclude (ignored if includes != null)
     */
    private List excludes;

    public AbstractArtifactFeatureFilter( String include, String exclude)
    {
        setExcludes( exclude );
        setIncludes( include );
    }

    /**
     * This function determines if filtering needs to be performed. Includes are processed before Excludes.
     * 
     * @param dependencies the set of dependencies to filter.
     * @return a Set of filtered dependencies.
     */
    public Set filter( Set artifacts )
    {
        Set results = artifacts;

        if ( this.includes != null && !this.includes.isEmpty() )
        {
            results = filterIncludes( results, this.includes );
        }

        if ( this.excludes != null && !this.excludes.isEmpty() )
        {
            results = filterExcludes( results, this.excludes );
        }

        return results;
    }

    /**
     * Processes the dependencies list and includes the dependencies that match a filter in the list.
     * 
     * @param depends List of dependencies.
     * @param includes List of types or classifiers to include.
     * @return a set of filtered artifacts.
     */
    private Set filterIncludes( Set artifacts, List theIncludes )
    {
        Set result = new HashSet();

        Iterator includeIter = theIncludes.iterator();
        while ( includeIter.hasNext() )
        {
            String include = (String) includeIter.next();
            Iterator iter = artifacts.iterator();
            while ( iter.hasNext() )
            {
                Artifact artifact = (Artifact) iter.next();

                // if the classifier or type of the artifact
                // matches the feature
                // to include, add to the
                // results
                if ( compareFeatures( getArtifactFeature( artifact ), include ) )
                {
                    result.add( artifact );
                }
            }
        }
        return result;
    }

    /**
     * Processes the dependencies list and excludes the dependencies that match a filter in the list.
     * 
     * @param depends List of dependencies.
     * @param excludes List of types or classifiers to exclude.
     * @return a set of filtered artifacts.
     */
    private Set filterExcludes( Set artifacts, List theExcludes )
    {
        Set result = new HashSet();

        Iterator iter = artifacts.iterator();
        while ( iter.hasNext() )
        {
            boolean exclude = false;
            Artifact artifact = (Artifact) iter.next();
            String artifactFeature = getArtifactFeature( artifact );

            // look through all types or classifiers. If no
            // matches are found
            // then it can be added to the results.
            Iterator excludeIter = theExcludes.iterator();
            while ( excludeIter.hasNext() )
            {
                String excludeFeature = (String) excludeIter.next();
                if ( compareFeatures( artifactFeature, excludeFeature ) )
                {
                    exclude = true;
                    break;
                }
            }

            if ( !exclude )
            {
                result.add( artifact );
            }
        }

        return result;
    }

    /**
     * Should return the type or classifier of the given artifact, so that we can filter it
     * 
     * @param artifact artifact to return type or classifier of
     * @return type or classifier
     */
    protected abstract String getArtifactFeature( Artifact artifact );

    public void setExcludes( String excludeString )
    {
        if ( StringUtils.isNotEmpty( excludeString ) )
        {
            this.excludes = Arrays.asList( StringUtils.split( excludeString, "," ) );
        }
    }

    public void setIncludes( String includeString )
    {
        if ( StringUtils.isNotEmpty( includeString ) )
        {
            this.includes = Arrays.asList( StringUtils.split( includeString, "," ) );
        }
    }

    /**
     * @return Returns the excludes.
     */
    public List getExcludes()
    {
        return this.excludes;
    }

    /**
     * @return Returns the includes.
     */
    public List getIncludes()
    {
        return this.includes;
    }

    /**
     * Allows Feature comparison to be customized
     * 
     * @param lhs String artifact's feature
     * @param rhs String feature from exclude or include list
     * @return boolean true if features match
     */
    protected boolean compareFeatures( String lhs, String rhs )
    {
        // If lhs is null, check that rhs is null. Otherwise check if strings are equal.
        return ( lhs == null ? rhs == null : lhs.equals( rhs ) );
    }
}
