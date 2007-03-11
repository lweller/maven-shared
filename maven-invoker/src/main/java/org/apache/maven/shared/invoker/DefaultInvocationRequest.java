package org.apache.maven.shared.invoker;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class DefaultInvocationRequest
    implements InvocationRequest
{

    private File basedir;
    private boolean debug;
    private InvocationOutputHandler errorHandler;
    private String failureBehavior;
    private List goals;
    private InputStream inputStream;
    private boolean interactive;
    private File localRepository;
    private boolean offline;
    private InvocationOutputHandler outputHandler;
    private File pomFile;
    private Properties properties;
    private boolean showErrors;
    private boolean updateSnapshots;
    private boolean shellEnvironmentInherited = true;
    private File userSettings;
    private String globalChecksumPolicy;
    private String pomFilename;

    public InvocationRequest activateReactor( String[] includes, String[] excludes )
    {
        throw new UnsupportedOperationException( "Not implemented yet." );
    }

    public File getBaseDirectory()
    {
        return basedir;
    }

    public File getBaseDirectory( File defaultDirectory )
    {
        return basedir == null ? defaultDirectory : basedir;
    }

    public InvocationOutputHandler getErrorHandler( InvocationOutputHandler defaultHandler )
    {
        return errorHandler == null ? defaultHandler : errorHandler;
    }

    public String getFailureBehavior()
    {
        return failureBehavior;
    }

    public List getGoals()
    {
        return goals;
    }

    public InputStream getInputStream( InputStream defaultStream )
    {
        return inputStream == null ? defaultStream : inputStream;
    }

    public File getLocalRepositoryDirectory( File defaultDirectory )
    {
        return localRepository == null ? defaultDirectory : localRepository;
    }

    public InvocationOutputHandler getOutputHandler( InvocationOutputHandler defaultHandler )
    {
        return outputHandler == null ? defaultHandler : outputHandler;
    }

    public File getPomFile()
    {
        return pomFile;
    }

    public Properties getProperties()
    {
        return properties;
    }

    public boolean isDebug()
    {
        return debug;
    }

    public boolean isInteractive()
    {
        return interactive;
    }

    public boolean isOffline()
    {
        return offline;
    }

    public boolean isShowErrors()
    {
        return showErrors;
    }

    public boolean isUpdateSnapshots()
    {
        return updateSnapshots;
    }

    public InvocationRequest setBaseDirectory( File basedir )
    {
        this.basedir = basedir;
        return this;
    }

    public InvocationRequest setDebug( boolean debug )
    {
        this.debug = debug;
        return this;
    }

    public InvocationRequest setErrorHandler( InvocationOutputHandler errorHandler )
    {
        this.errorHandler = errorHandler;
        return this;
    }

    public InvocationRequest setFailureBehavior( String failureBehavior )
    {
        this.failureBehavior = failureBehavior;
        return this;
    }

    public InvocationRequest setGoals( List goals )
    {
        this.goals = goals;
        return this;
    }

    public InvocationRequest setInputStream( InputStream inputStream )
    {
        this.inputStream = inputStream;
        return this;
    }

    public InvocationRequest setInteractive( boolean interactive )
    {
        this.interactive = interactive;
        return this;
    }

    public InvocationRequest setLocalRepositoryDirectory( File localRepository )
    {
        this.localRepository = localRepository;
        return this;
    }

    public InvocationRequest setOffline( boolean offline )
    {
        this.offline = offline;
        return this;
    }

    public InvocationRequest setOutputHandler( InvocationOutputHandler outputHandler )
    {
        this.outputHandler = outputHandler;
        return this;
    }

    public InvocationRequest setPomFile( File pomFile )
    {
        this.pomFile = pomFile;
        return this;
    }

    public InvocationRequest setProperties( Properties properties )
    {
        this.properties = properties;
        return this;
    }

    public InvocationRequest setShowErrors( boolean showErrors )
    {
        this.showErrors = showErrors;
        return this;
    }

    public InvocationRequest setUpdateSnapshots( boolean updateSnapshots )
    {
        this.updateSnapshots = updateSnapshots;
        return this;
    }

    public boolean isShellEnvironmentInherited()
    {
        return shellEnvironmentInherited;
    }

    public InvocationRequest setShellEnvironmentInherited( boolean shellEnvironmentInherited )
    {
        this.shellEnvironmentInherited  = shellEnvironmentInherited;
        return this;
    }

    public File getUserSettingsFile()
    {
        return userSettings;
    }

    public InvocationRequest setUserSettingsFile( File userSettings )
    {
        this.userSettings = userSettings;
        return this;
    }

    public String getGlobalChecksumPolicy()
    {
        return globalChecksumPolicy;
    }

    public InvocationRequest setGlobalChecksumPolicy( String globalChecksumPolicy )
    {
        this.globalChecksumPolicy = globalChecksumPolicy;
        return this;
    }

    public String getPomFileName()
    {
        return pomFilename;
    }

    public InvocationRequest setPomFileName( String pomFilename )
    {
        this.pomFilename = pomFilename;
        return this;
    }

}