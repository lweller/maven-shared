package org.apache.maven.shared.ant.components;

/*
 * Copyright 2004-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.tools.ant.ComponentHelper;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.apache.tools.ant.RuntimeConfigurable;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.UnknownElement;
import org.codehaus.plexus.component.configurator.ComponentConfigurationException;
import org.codehaus.plexus.component.configurator.ConfigurationListener;
import org.codehaus.plexus.component.configurator.converters.AbstractConfigurationConverter;
import org.codehaus.plexus.component.configurator.converters.ConfigurationConverter;
import org.codehaus.plexus.component.configurator.converters.lookup.ConverterLookup;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluator;
import org.codehaus.plexus.configuration.PlexusConfiguration;
import org.codehaus.plexus.configuration.PlexusConfigurationException;
import org.codehaus.plexus.classworlds.realm.ClassRealm;

/**
 * Plexus ConfigurationConverter to set up Ant Target component fields.
 *
 * @author <a href="mailto:kenney@apache.org">Kenney Westerhof</a>
 */
public class AntTargetConverter
    extends AbstractConfigurationConverter
{
    public static final String MAVEN_EXPRESSION_EVALUATOR_ID = "maven.expressionEvaluator";

    public static final String ROLE = ConfigurationConverter.class.getName();

    public boolean canConvert( Class type )
    {
        return Target.class.isAssignableFrom( type );
    }

    // This is needed so that this will work in Maven 2.0.x
    public Object fromConfiguration( ConverterLookup converterLookup, PlexusConfiguration configuration, Class type,
                                     Class baseType, ClassLoader classLoader, ExpressionEvaluator expressionEvaluator,
                                     ConfigurationListener listener )
        throws ComponentConfigurationException
    {
        Object retValue = fromExpression( configuration, expressionEvaluator, type );
        if ( retValue != null )
        {
            return retValue;
        }

        Class implementation = getClassForImplementationHint( type, configuration, classLoader );

        retValue = instantiateObject( implementation );

        processConfiguration( (Target) retValue, configuration, expressionEvaluator );

        return retValue;
    }

    public Object fromConfiguration( ConverterLookup converterLookup, PlexusConfiguration configuration, Class type,
                                     Class baseType, ClassRealm realm, ExpressionEvaluator expressionEvaluator,
                                     ConfigurationListener listener )
        throws ComponentConfigurationException
    {
        Object retValue = fromExpression( configuration, expressionEvaluator, type );
        if ( retValue != null )
        {
            return retValue;
        }

        Class implementation = getClassForImplementationHint( type, configuration, realm );

        retValue = instantiateObject( implementation );

        processConfiguration( (Target) retValue, configuration, expressionEvaluator );

        return retValue;
    }


    private void processConfiguration( Target target, PlexusConfiguration configuration,
                                       ExpressionEvaluator expressionEvaluator )
        throws ComponentConfigurationException
    {
        Project project = new Project();
        project.setName( "DummyProject" );

        target.setName( "" );
        target.setProject( project );
        project.addTarget( target );

        project.addReference(MAVEN_EXPRESSION_EVALUATOR_ID , expressionEvaluator);
        
        initDefinitions( project, target );

        processConfiguration( null, project, target, configuration );

        project.init();
    }


    private void processConfiguration( RuntimeConfigurable parentWrapper, Project project, Target target,
                                       PlexusConfiguration configuration )
        throws ComponentConfigurationException
    {
        int items = configuration.getChildCount();

        Object parent = parentWrapper == null ? null : parentWrapper.getProxy();

        for ( int i = 0; i < items; i++ )
        {
            PlexusConfiguration childConfiguration = configuration.getChild( i );
            UnknownElement task = new UnknownElement( childConfiguration.getName() );
            task.setProject( project );
            task.setNamespace( "" );
            task.setQName( childConfiguration.getName() );
            task.setTaskType( ProjectHelper.genComponentName( task.getNamespace(), childConfiguration.getName() ) );
            task.setTaskName( childConfiguration.getName() );
            task.setOwningTarget( target );
            task.init();

            if ( parent != null )
            {
                ( (UnknownElement) parent ).addChild( task );
            }
            else
            {
                target.addTask( task );
            }

            RuntimeConfigurable wrapper = new RuntimeConfigurable( task, task.getTaskName() );

            try
            {
                if ( childConfiguration.getValue() != null )
                {
                    wrapper.addText( childConfiguration.getValue() );
                }
            }
            catch ( PlexusConfigurationException e )
            {
                throw new ComponentConfigurationException(
                    "Error reading text value from element '" + childConfiguration.getName() + "'", e );
            }

            String [] attrNames = childConfiguration.getAttributeNames();

            for ( int a = 0; a < attrNames.length; a++ )
            {
                try
                {
                    String v = childConfiguration.getAttribute( attrNames[a] );
                    wrapper.setAttribute( attrNames[a], v );
                }
                catch ( PlexusConfigurationException e )
                {
                    throw new ComponentConfigurationException(
                        "Error getting attribute '" + attrNames[a] + "' of tag '" + childConfiguration.getName() + "'",
                        e );
                }
            }

            if ( parentWrapper != null )
            {
                parentWrapper.addChild( wrapper );
            }

            processConfiguration( wrapper, project, target, childConfiguration );
        }
    }


    protected void initDefinitions( Project project, Target unused )
    {
        ComponentHelper componentHelper = ComponentHelper.getComponentHelper( project );

        componentHelper.initDefaultDefinitions();
    }
}
