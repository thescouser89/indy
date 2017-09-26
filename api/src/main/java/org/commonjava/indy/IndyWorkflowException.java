/**
 * Copyright (C) 2011 Red Hat, Inc. (jdcasey@commonjava.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.commonjava.indy;

import java.io.NotSerializableException;
import java.io.Serializable;
import java.text.MessageFormat;

import org.commonjava.indy.content.DownloadManager;
import org.commonjava.maven.galley.TransferLocationException;

/**
 * Signals an error between the REST-resources layer and the next layer down (except for {@link DownloadManager}, which is normally two layers down thanks 
 * to binding controllers). Workflow exceptions are intended to carry with them some notion of what response to send to the user (even if it's 
 * the default: HTTP 500).
 */
public class IndyWorkflowException
    extends Exception
{
    private Object[] params;

    private transient String formattedMessage;

    private int status;

    public IndyWorkflowException( final String message, final Object... params )
    {
        super( message );
        this.params = params;
    }

    public IndyWorkflowException( final String message, final Throwable cause, final Object... params )
    {
        super( message, cause );
        this.params = params;
    }

    public IndyWorkflowException( final int status, final String message, final Object... params )
    {
        super( message );
        this.params = params;
        this.status = status;
    }

    public IndyWorkflowException( final int status, final String message, Throwable cause, final Object... params )
    {
        super( message, cause );
        this.params = params;
        this.status = status;
    }

    private static final long serialVersionUID = 1L;

    @Override
    public synchronized String getMessage()
    {
        if ( formattedMessage == null )
        {
            final String format = super.getMessage();
            if ( params == null || params.length < 1 )
            {
                formattedMessage = format;
            }
            else
            {
                for(int i=0; i<params.length; i++)
                {
                    if ( params[i] == null )
                    {
                        params[i] = "null";
                    }
                }

                final String original = formattedMessage;
                try
                {
                    formattedMessage = String.format( format.replaceAll( "\\{\\}", "%s" ), params );
                }
                catch ( final Error e )
                {
                }
                catch ( final RuntimeException e )
                {
                }
                catch ( final Exception e )
                {
                }

                if ( formattedMessage == null || original == formattedMessage )
                {
                    try
                    {
                        formattedMessage = MessageFormat.format( format, params );
                    }
                    catch ( IllegalArgumentException e )
                    {
                        formattedMessage = format;
                    }
                    catch ( final Error e )
                    {
                        formattedMessage = format;
//                        throw e;
                    }
                    catch ( final RuntimeException e )
                    {
                        formattedMessage = format;
//                        throw e;
                    }
                    catch ( final Exception e )
                    {
                        formattedMessage = format;
                    }
                }

                if ( formattedMessage == null )
                {
                    formattedMessage = format;
                }
            }
        }

        return formattedMessage;
    }

    public int getStatus()
    {
        return status < 1 ? 500 : status;
    }

    /**
     * Stringify all parameters pre-emptively on serialization, to prevent {@link NotSerializableException}.
     * Since all parameters are used in {@link String#format} or {@link MessageFormat#format}, flattening them
     * to strings is an acceptable way to provide this functionality without making the use of {@link Serializable}
     * viral.
     */
    private Object writeReplace()
    {
        final Object[] newParams = new Object[params.length];
        int i = 0;
        for ( final Object object : params )
        {
            newParams[i] = String.valueOf( object );
            i++;
        }

        this.params = newParams;
        return this;
    }

    public void filterLocationErrors()
            throws IndyWorkflowException
    {
        Throwable cause = getCause();
        if ( !( cause instanceof TransferLocationException ) )
        {
            throw this;
        }
    }

}
